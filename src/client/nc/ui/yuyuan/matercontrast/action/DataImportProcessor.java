package nc.ui.yuyuan.matercontrast.action;

import java.util.Collection;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pubapp.excel.IVOColumnValueProcess;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pubapp.AppContext;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

/**
 * VO处理类:填充系统字段数据
 * 
 * @author shidl
 * 
 */
public class DataImportProcessor implements IVOColumnValueProcess {

	private IUAPQueryBS querySrv = null;

	@Override
	public void valueProcess(CircularlyAccessibleValueObject billVO, int pos,
			String code, Object value) {

		if (IBillItem.HEAD == pos) {
			try {
				headVOProcess(billVO, code, value);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				ExceptionUtils.wrappException(e);
			}
		}
		if (IBillItem.BODY == pos) {
			bodyVOProcess(billVO, code, value);
		}
	}

	/**
	 * 表头VO处理事件
	 * @throws BusinessException 
	 */
	private void headVOProcess(CircularlyAccessibleValueObject billVO,
			String code, Object value) throws BusinessException {
//		AppContext context = AppContext.getInstance();
//		if ("pk_group".equals(code)) {
//			value = context.getPkGroup();
//		}
//		if ("vbillcode".equals(code)) {
//			value = null;
//		}
//		if ("billmaker".equals(code)) {
//			value = context.getPkUser();
//		}
//		if ("pk_billtypecode".equals(code)) {
//			value = "YY03";
//		}
//		if ("pk_billtypeid".equals(code)) {
//			value = nc.vo.am.common.util.BillTypeUtils.getPKByCode("YY03");
//		}
//		if ("dmakedate".equals(code)) {
//			value = context.getBusiDate();
//		}
		if ("pk_org".equals(code)) {
			String orgcode = "";
			if(value!=null){
				orgcode=value.toString();
			}
			String pk_org = NCLocator.getInstance()
					.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
					.queryVOByCode(orgcode);
			if(pk_org!=null){
				value = pk_org;
			}else{
				value="";
			}
		}
//		if ("pk_supplier".equals(code)) {
//			try {
//				Collection vos = getUapQuerySrv().retrieveByClause(
//						SupplierVO.class,
//						"pk_org='" + context.getPkGroup() + "' and code = '"
//								+ value.toString() + "' ",
//						new String[] { "pk_supplier" }, null);
//				value = ((SupplierVO) vos.toArray()[0])
//						.getAttributeValue("pk_supplier");
//			} catch (BusinessException e) {
//				// TODO Auto-generated catch block
//				ExceptionUtils.wrappBusinessException(e.getMessage());
//			}
//
//		}

		billVO.setAttributeValue(code, value);
	}

	/**
	 * 表替VO处理事件
	 */
	private void bodyVOProcess(CircularlyAccessibleValueObject billVO,
			String code, Object value) {
		billVO.setAttributeValue(code, value);
	}

	private IUAPQueryBS getUapQuerySrv() {
		if (querySrv == null) {
			querySrv = (IUAPQueryBS) NCLocator.getInstance().lookup(
					IUAPQueryBS.class);
		}

		return querySrv;
	}
}
