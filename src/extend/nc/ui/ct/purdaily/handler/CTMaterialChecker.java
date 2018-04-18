package nc.ui.ct.purdaily.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.vo.ct.purdaily.entity.AggCtPuVO;
import nc.vo.ct.purdaily.entity.CTDto;
import nc.vo.ct.purdaily.entity.CtPuBVO;
import nc.vo.ct.purdaily.entity.CtScopeVo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pubapp.AppContext;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.yy_errorlog.YyErrorLogVO;

/**
 * 采购合同校验器：组织+物料在【生效日期，失效日期】范围内不能重叠 (供应商暂不考虑：防止出现同一门店同一物料出现不同供应商)
 * 
 * @author shidl
 * 
 */
public class CTMaterialChecker {
	/**
	 * 
	 * @param agg
	 *            界面数据
	 * @param pkorgList
	 *            新分配的组织
	 * @throws BusinessException
	 */
	public void process(AggCtPuVO agg, List<String> pkorgList)
			throws BusinessException {

		// 2017-03-04-无需考虑采购组织“01-上海老城隍庙餐饮（集团）有限公司”
		String pk_org = NCLocator.getInstance()
				.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
				.queryVOByCode("01");
		// 判断当前数据是否是01
		String pk_org_cur = agg.getParentVO().getPk_org();
		if (pkorgList == null) {
			pkorgList = new ArrayList<String>();
		}
		ArrayList<CtScopeVo> ctscopeList = new ArrayList<CtScopeVo>();
		ctscopeList = NCLocator.getInstance()
				.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
				.queryCTScopeVOByPK(agg.getParentVO().getPk_ct_pu());
		if (ctscopeList != null && ctscopeList.size() > 0) {
			for (CtScopeVo ctScopeVo : ctscopeList) {
				String pk_scopeorg = ctScopeVo.getPk_scopeorg();
				if (!pkorgList.contains(pk_scopeorg)
						&& !pk_scopeorg.equals(pk_org)) {
					pkorgList.add(pk_scopeorg);
				}
			}
		}
		if (pk_org_cur.equals(pk_org) && pkorgList.size() == 0) {
			return;
		}
		if (pkorgList != null && pkorgList.contains(pk_org)) {
			pkorgList.remove(pk_org);
		}

		if (agg.getCtPuBVO() == null) {
			return;
		}
		// 1.界面数据校验-编辑后事件处理
		// 2.数据库数据校验
		Map<String, CtPuBVO> material2bvo = new HashMap<String, CtPuBVO>();
		for (CtPuBVO bvo : agg.getCtPuBVO()) {
			material2bvo.put(bvo.getPk_material(), bvo);
		}
		List<CTDto> list = NCLocator.getInstance()
				.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
				.CTMaterialCheckerProcess(agg, pkorgList);
		// 界面数据
		UFDate st0 = agg.getParentVO().getValdate();
		UFDate et0 = agg.getParentVO().getInvallidate();
		StringBuffer message = new StringBuffer();
		if (list != null && list.size() > 0) {
			ArrayList<YyErrorLogVO> list2 = new ArrayList<YyErrorLogVO>();
			for (CTDto dto : list) {
				UFDate st1 = dto.getValdate();
				UFDate et1 = dto.getInvallidate();
				// 存在重叠情形
				if (!((st0.after(et1)) || (st1.after(et0)))) {
					CtPuBVO bvo = material2bvo.get(dto.getPk_material());
					String msg = "行号:" + bvo.getCrowno() + ",物料编码:"
							+ dto.getMaterial_code() + ",供应商编码:"
							+ dto.getSupplier_code() + ",组织编码:"
							+ dto.getOrg_code() + ",计划生效日期:"
							+ agg.getParentVO().getValdate() + ",计划失效日期:"
							+ agg.getParentVO().getInvallidate();
					message.append(msg + ",存在日期范围重叠数据\n");
					YyErrorLogVO logvo = new YyErrorLogVO();
					logvo.setErrortyle("采购合同生效/失效日期范围校验");
					logvo.setErrordate(AppContext.getInstance().getBusiDate());
					logvo.setErrorinfo(msg);
					logvo.setPk_org(agg.getParentVO().getPk_org());
					logvo.setBillno(agg.getParentVO().getVbillcode());
					logvo.setBill(agg.getParentVO().getPk_ct_pu());
					logvo.setBillmaker(agg.getParentVO().getBillmaker());
					logvo.setBilldate(agg.getParentVO().getDbilldate());
					logvo.setBillbno(bvo.getCrowno());
					logvo.setBillbmaterial(dto.getPk_material());
					list2.add(logvo);
				}
			}
			// 调用后台接口存储错误信息,独立事务接口
			if (list2.size() > 0) {
				NCLocator.getInstance()
						.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
						.insertLogVO_RequiresNew(list2);
			}
			if (message.length() > 0) {
				ExceptionUtils.wrappBusinessException(message.toString());
			}
		}
	}

	/**
	 * 合同控制范围校验
	 * 
	 * @param agg
	 * @param sCtrlScopeNew
	 * @param sCtrlScopeOld
	 * @throws BusinessException
	 */
	public void processContrl(AggCtPuVO agg, String[] sCtrlScopeNew,
			String[] sCtrlScopeOld) throws BusinessException {
		List<String> pkorgListNew = new ArrayList<String>();
		List<String> pkorgOld = Arrays.asList(sCtrlScopeOld);
		// 数组差集，取最新分配的pk_org
		for (String str : sCtrlScopeNew) {
			if (!pkorgOld.contains(str)) {
				pkorgListNew.add(str);
			}
		}
		this.process(agg, pkorgListNew);
	}

	/**
	 * 合同控制范围(批量)校验
	 * 
	 * @param pk2agg
	 * @param pk2List
	 * @throws BusinessException
	 */
	public void processBatchContrl(Map<String, AggCtPuVO> pk2agg,
			Map<String, List<String>> pk2List) throws BusinessException {
		for (String pk_ct_pu : pk2agg.keySet()) {
			AggCtPuVO agg = pk2agg.get(pk_ct_pu);
			List<String> list = pk2List.get(pk_ct_pu);
			this.process(agg, list);
		}
	}
}
