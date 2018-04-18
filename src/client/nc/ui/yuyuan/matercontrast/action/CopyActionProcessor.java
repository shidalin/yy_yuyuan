package nc.ui.yuyuan.matercontrast.action;

import nc.ui.pubapp.uif2app.actions.intf.ICopyActionProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.yuyuan.yy_matercontrast.AggYyMaterContrastVO;
import nc.vo.uif2.LoginContext;
import nc.vo.pubapp.AppContext;
import nc.vo.pub.pf.BillStatusEnum;

public class CopyActionProcessor implements
		ICopyActionProcessor<AggYyMaterContrastVO> {

	@Override
	public void processVOAfterCopy(AggYyMaterContrastVO paramT,
			LoginContext paramLoginContext) {
		paramT.getParentVO().setPrimaryKey(null);	
		
		paramT.getParentVO().setAttributeValue("dmakedate", AppContext.getInstance().getBusiDate());
	   	paramT.getParentVO().setAttributeValue("creator", null);
	   	paramT.getParentVO().setAttributeValue("creationtime", null);
	   	paramT.getParentVO().setAttributeValue("modifier", null);
	   	paramT.getParentVO().setAttributeValue("modifiedtime", null);
	   	paramT.getParentVO().setAttributeValue("vbillcode", null);
	   	
	  

		// TODO 根据需要业务自己补充处理清空
		String[] codes =paramT.getTableCodes();
		if (codes != null && codes.length>0) {
			for (int i = 0; i < codes.length; i++) {
				String tableCode = codes[i];
				 CircularlyAccessibleValueObject[] childVOs = 	paramT.getTableVO(tableCode);
				 for (CircularlyAccessibleValueObject childVO : childVOs) {
					 try {
						childVO.setPrimaryKey(null);
					} catch (BusinessException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			}
		}
	}
}
