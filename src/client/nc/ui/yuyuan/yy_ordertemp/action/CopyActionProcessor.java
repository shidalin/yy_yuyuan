package nc.ui.yuyuan.yy_ordertemp.action;

import nc.ui.pubapp.uif2app.actions.intf.ICopyActionProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;
import nc.vo.uif2.LoginContext;
import nc.vo.pubapp.AppContext;
import nc.vo.pub.pf.BillStatusEnum;

public class CopyActionProcessor implements
		ICopyActionProcessor<AggYyOrderTempVO> {

	@Override
	public void processVOAfterCopy(AggYyOrderTempVO paramT,
			LoginContext paramLoginContext) {
		paramT.getParentVO().setPrimaryKey(null);	
		
		paramT.getParentVO().setAttributeValue("vbillcode", null);
		paramT.getParentVO().setAttributeValue("vbillstatus", BillStatusEnum.FREE.value());
		paramT.getParentVO().setAttributeValue("dmakedate", AppContext.getInstance().getBusiDate());
		paramT.getParentVO().setAttributeValue("approver", null);
		paramT.getParentVO().setAttributeValue("dapprovetime", null);
	   	paramT.getParentVO().setAttributeValue("creator", null);
	   	paramT.getParentVO().setAttributeValue("creationtime", null);
	   	paramT.getParentVO().setAttributeValue("modifier", null);
	   	paramT.getParentVO().setAttributeValue("modifiedtime", null);
//	   	paramT.getParentVO().setAttributeValue("pk_group", null);
//	   	paramT.getParentVO().setAttributeValue("pk_org", null);
	   	
	   	
	  

		// TODO ������Ҫҵ���Լ����䴦�����
		String[] codes =paramT.getTableCodes();
		if (codes != null && codes.length>0) {
			for (int i = 0; i < codes.length; i++) {
				String tableCode = codes[i];
				 CircularlyAccessibleValueObject[] childVOs = 	paramT.getTableVO(tableCode);
				 for (CircularlyAccessibleValueObject childVO : childVOs) {
					 try {
						childVO.setPrimaryKey(null);
//						childVO.setAttributeValue("pk_group", null);
//						childVO.setAttributeValue("pk_org", null);
					} catch (BusinessException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
				}
			}
		}
	}
}
