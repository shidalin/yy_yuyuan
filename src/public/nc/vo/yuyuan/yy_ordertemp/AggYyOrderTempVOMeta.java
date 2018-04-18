package nc.vo.yuyuan.yy_ordertemp;

import nc.vo.pubapp.pattern.model.meta.entity.bill.AbstractBillMeta;

public class AggYyOrderTempVOMeta extends AbstractBillMeta{
	
	public AggYyOrderTempVOMeta(){
		this.init();
	}
	
	private void init() {
		this.setParent(nc.vo.yuyuan.yy_ordertemp.YyOrderTempVO.class);
		this.addChildren(nc.vo.yuyuan.yy_ordertemp.YyOrderTempBVO.class);
	}
}