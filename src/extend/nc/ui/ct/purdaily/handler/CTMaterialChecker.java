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
 * �ɹ���ͬУ��������֯+�����ڡ���Ч���ڣ�ʧЧ���ڡ���Χ�ڲ����ص� (��Ӧ���ݲ����ǣ���ֹ����ͬһ�ŵ�ͬһ���ϳ��ֲ�ͬ��Ӧ��)
 * 
 * @author shidl
 * 
 */
public class CTMaterialChecker {
	/**
	 * 
	 * @param agg
	 *            ��������
	 * @param pkorgList
	 *            �·������֯
	 * @throws BusinessException
	 */
	public void process(AggCtPuVO agg, List<String> pkorgList)
			throws BusinessException {

		// 2017-03-04-���迼�ǲɹ���֯��01-�Ϻ��ϳ�������������ţ����޹�˾��
		String pk_org = NCLocator.getInstance()
				.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
				.queryVOByCode("01");
		// �жϵ�ǰ�����Ƿ���01
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
		// 1.��������У��-�༭���¼�����
		// 2.���ݿ�����У��
		Map<String, CtPuBVO> material2bvo = new HashMap<String, CtPuBVO>();
		for (CtPuBVO bvo : agg.getCtPuBVO()) {
			material2bvo.put(bvo.getPk_material(), bvo);
		}
		List<CTDto> list = NCLocator.getInstance()
				.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
				.CTMaterialCheckerProcess(agg, pkorgList);
		// ��������
		UFDate st0 = agg.getParentVO().getValdate();
		UFDate et0 = agg.getParentVO().getInvallidate();
		StringBuffer message = new StringBuffer();
		if (list != null && list.size() > 0) {
			ArrayList<YyErrorLogVO> list2 = new ArrayList<YyErrorLogVO>();
			for (CTDto dto : list) {
				UFDate st1 = dto.getValdate();
				UFDate et1 = dto.getInvallidate();
				// �����ص�����
				if (!((st0.after(et1)) || (st1.after(et0)))) {
					CtPuBVO bvo = material2bvo.get(dto.getPk_material());
					String msg = "�к�:" + bvo.getCrowno() + ",���ϱ���:"
							+ dto.getMaterial_code() + ",��Ӧ�̱���:"
							+ dto.getSupplier_code() + ",��֯����:"
							+ dto.getOrg_code() + ",�ƻ���Ч����:"
							+ agg.getParentVO().getValdate() + ",�ƻ�ʧЧ����:"
							+ agg.getParentVO().getInvallidate();
					message.append(msg + ",�������ڷ�Χ�ص�����\n");
					YyErrorLogVO logvo = new YyErrorLogVO();
					logvo.setErrortyle("�ɹ���ͬ��Ч/ʧЧ���ڷ�ΧУ��");
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
			// ���ú�̨�ӿڴ洢������Ϣ,��������ӿ�
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
	 * ��ͬ���Ʒ�ΧУ��
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
		// ������ȡ���·����pk_org
		for (String str : sCtrlScopeNew) {
			if (!pkorgOld.contains(str)) {
				pkorgListNew.add(str);
			}
		}
		this.process(agg, pkorgListNew);
	}

	/**
	 * ��ͬ���Ʒ�Χ(����)У��
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
