package nc.vo.yuyuan.yy_order;

import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.pubapp.pattern.model.meta.entity.bill.BillMetaFactory;
import nc.vo.pubapp.pattern.model.meta.entity.bill.IBillMeta;

@nc.vo.annotation.AggVoInfo(parentVO = "nc.vo.yuyuan.yy_order.YyOrderVO")

public class AggYyOrderVO extends AbstractBill {
	
	  @Override
	  public IBillMeta getMetaData() {
	  	IBillMeta billMeta =BillMetaFactory.getInstance().getBillMeta(AggYyOrderVOMeta.class);
	  	return billMeta;
	  }
	    
	  @Override
	  public YyOrderVO getParentVO(){
	  	return (YyOrderVO)this.getParent();
	  }
	  
}