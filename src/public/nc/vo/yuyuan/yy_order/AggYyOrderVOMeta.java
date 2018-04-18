package nc.vo.yuyuan.yy_order;

import nc.vo.pubapp.pattern.model.meta.entity.bill.AbstractBillMeta;

public class AggYyOrderVOMeta extends AbstractBillMeta{
	
	public AggYyOrderVOMeta(){
		this.init();
	}
	
	private void init() {
		this.setParent(nc.vo.yuyuan.yy_order.YyOrderVO.class);
		this.addChildren(nc.vo.yuyuan.yy_order.YyOrderBVO.class);
	}
}