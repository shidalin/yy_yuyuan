package nc.vo.yuyuan.yy_matercontrast;

import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.pubapp.pattern.model.meta.entity.bill.BillMetaFactory;
import nc.vo.pubapp.pattern.model.meta.entity.bill.IBillMeta;

@nc.vo.annotation.AggVoInfo(parentVO = "nc.vo.yuyuan.yy_matercontrast.YyMaterContrastVO")

public class AggYyMaterContrastVO extends AbstractBill {
	
	  @Override
	  public IBillMeta getMetaData() {
	  	IBillMeta billMeta =BillMetaFactory.getInstance().getBillMeta(AggYyMaterContrastVOMeta.class);
	  	return billMeta;
	  }
	    
	  @Override
	  public YyMaterContrastVO getParentVO(){
	  	return (YyMaterContrastVO)this.getParent();
	  }
	  
}