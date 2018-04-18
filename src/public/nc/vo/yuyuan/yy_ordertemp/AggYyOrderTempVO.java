package nc.vo.yuyuan.yy_ordertemp;

import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.pubapp.pattern.model.meta.entity.bill.BillMetaFactory;
import nc.vo.pubapp.pattern.model.meta.entity.bill.IBillMeta;

@nc.vo.annotation.AggVoInfo(parentVO = "nc.vo.yuyuan.yy_ordertemp.YyOrderTempVO")

public class AggYyOrderTempVO extends AbstractBill {
	
	  @Override
	  public IBillMeta getMetaData() {
	  	IBillMeta billMeta =BillMetaFactory.getInstance().getBillMeta(AggYyOrderTempVOMeta.class);
	  	return billMeta;
	  }
	    
	  @Override
	  public YyOrderTempVO getParentVO(){
	  	return (YyOrderTempVO)this.getParent();
	  }

	@Override
	public void setChildrenVO(CircularlyAccessibleValueObject[] vos) {
		// TODO Auto-generated method stub
		super.setChildrenVO(vos);
	}
	  
	  
}