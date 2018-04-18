package nc.ui.yuyuan.matercontrast.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.itf.yuyuan.IYy_matercontrastMaintain;
import nc.ui.pubapp.excel.DocCellInfo;
import nc.ui.pubapp.excel.IExcelImportProcess;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pubapp.AppContext;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.pattern.pub.SqlBuilder;
import nc.vo.yuyuan.yy_matercontrast.AggYyMaterContrastVO;
import nc.vo.yuyuan.yy_matercontrast.YyMaterContrastBVO;
import nc.vo.yuyuan.yy_matercontrast.YyMaterContrastVO;
import nc.vo.bd.material.MaterialVO;

/**
 * Excel解析結果处理类
 * 
 * @author shidl
 * 
 */
public class ExcelImportProcessor implements IExcelImportProcess {

	private IUAPQueryBS querySrv = null;

	@Override
	public Map<String, DocCellInfo> getVoDocInfoMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 解析导入文件后调用后台保存接口，进行数据库持久化操作
	 */
	@Override
	public void processAfterImport(CircularlyAccessibleValueObject[] importVOs,
			CircularlyAccessibleValueObject headVO) throws Exception {
		// 调用后台接口进行数据持久化操作
		List<AggYyMaterContrastVO> list = translatorData(importVOs);
		NCLocator.getInstance()
				.lookup(nc.itf.yuyuan.IYy_matercontrastMaintain.class)
				.insert(list.toArray(new AggYyMaterContrastVO[list.size()]));
	}

	// 转换数据格式
	private List<AggYyMaterContrastVO> translatorData(
			CircularlyAccessibleValueObject[] importVOs) throws BusinessException {
		List<AggYyMaterContrastVO> aggList = new ArrayList<AggYyMaterContrastVO>();
		Map<String, List<CircularlyAccessibleValueObject>> map = new HashMap<String, List<CircularlyAccessibleValueObject>>();
		for (CircularlyAccessibleValueObject vo : importVOs) {
			String key = vo.getAttributeValue("pk_org") + "_"
					+ vo.getAttributeValue("pk_supplier");
			List<CircularlyAccessibleValueObject> value = null;
			if (map.keySet().contains(key)) {
				value = map.get(key);
				value.add(vo);
			} else {
				value = new ArrayList<CircularlyAccessibleValueObject>();
				value.add(vo);
			}
			map.put(key, value);
		}
		for (String key : map.keySet()) {
			List<CircularlyAccessibleValueObject> list = map.get(key);
			AggYyMaterContrastVO agg = new AggYyMaterContrastVO();
			YyMaterContrastVO hvo = translateForHVO(list.get(0));
			List<YyMaterContrastBVO> bVOList = translateForBVO(list);
			agg.setParentVO(hvo);
			agg.setChildrenVO(bVOList.toArray(new YyMaterContrastBVO[bVOList
					.size()]));
			aggList.add(agg);
		}
		return aggList;
	}

	private YyMaterContrastVO translateForHVO(CircularlyAccessibleValueObject vo) {
		YyMaterContrastVO hvo = new YyMaterContrastVO();
		AppContext context = AppContext.getInstance();
		hvo.setPk_org(vo.getAttributeValue("pk_org") == null ? "" : vo
				.getAttributeValue("pk_org").toString());
		if (vo.getAttributeValue("pk_supplier") != null) {
			try {
				Collection vos = getUapQuerySrv().retrieveByClause(
						SupplierVO.class,
						"pk_org='"
								+ context.getPkGroup()
								+ "' and code = '"
								+ vo.getAttributeValue("pk_supplier")
										.toString() + "' ",
						new String[] { "pk_supplier" }, null);
				Object value = ((SupplierVO) vos.toArray()[0])
						.getAttributeValue("pk_supplier");
				hvo.setPk_supplier(value.toString());
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				ExceptionUtils.wrappBusinessException(e.getMessage());
			}
		} else {
			hvo.setPk_supplier("");
		}
		hvo.setPk_billtypecode("YY03");
		hvo.setPk_billtypeid(nc.vo.am.common.util.BillTypeUtils
				.getPKByCode("YY03"));
		hvo.setPk_group(context.getPkGroup());
		hvo.setBillmaker(context.getPkUser());
		hvo.setDmakedate(context.getBusiDate());
		hvo.setVmemo(vo.getAttributeValue("vmemo_h") == null ? "" : vo
				.getAttributeValue("vmemo_h").toString());
		return hvo;
	}

	private List<YyMaterContrastBVO> translateForBVO(
			List<CircularlyAccessibleValueObject> list)
			throws BusinessException {
		// 查询自定义档案
		Map<String, String> queryDispatchstyale = NCLocator.getInstance()
				.lookup(nc.itf.yuyuan.IYy_matercontrastMaintain.class)
				.queryDispatchstyale();
		List<YyMaterContrastBVO> bVOList = new ArrayList<YyMaterContrastBVO>();
		for (int i = 0; i < list.size(); i++) {
			CircularlyAccessibleValueObject vo = list.get(i);
			YyMaterContrastBVO bvo = new YyMaterContrastBVO();
			AppContext context = AppContext.getInstance();
			bvo.setCrowno(((Integer) ((i + 1) * 10)).toString());
			bvo.setPk_org(vo.getAttributeValue("pk_org") == null ? "" : vo
					.getAttributeValue("pk_org").toString());
			bvo.setPk_group(context.getPkGroup());
			if (vo.getAttributeValue("cmaterialvid") != null) {
				try {
					Collection vos = getUapQuerySrv().retrieveByClause(
							MaterialVO.class,
							"pk_org='"
									+ context.getPkGroup()
									+ "' and code = '"
									+ vo.getAttributeValue("cmaterialvid")
											.toString() + "' ",
							new String[] { "pk_material" }, null);
					Object value = ((MaterialVO) vos.toArray()[0])
							.getAttributeValue("pk_material");
					bvo.setCmaterialvid(value.toString());
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					ExceptionUtils.wrappBusinessException(e.getMessage());
				}
			} else {
				bvo.setCmaterialvid("");
			}
			
			String dispatchstyle = vo.getAttributeValue("dispatchstyle") == null ? ""
					: vo.getAttributeValue("dispatchstyle").toString();
			String dispatchstyle_id="";
			if(queryDispatchstyale.containsKey(dispatchstyle)){
				dispatchstyle_id = queryDispatchstyale.get(dispatchstyle);
			}
			bvo.setDispatchstyle(dispatchstyle_id);
			bvo.setVdef1(dispatchstyle_id);
			bvo.setVmemo(vo.getAttributeValue("vmemo_b") == null ? "" : vo
					.getAttributeValue("vmemo_b").toString());
			bVOList.add(bvo);
		}
		return bVOList;
	}

	@Override
	public void processBeforeImport(String keyWord) throws Exception {
		// TODO Auto-generated method stub

	}

	private IUAPQueryBS getUapQuerySrv() {
		if (querySrv == null) {
			querySrv = (IUAPQueryBS) NCLocator.getInstance().lookup(
					IUAPQueryBS.class);
		}

		return querySrv;
	}
}
