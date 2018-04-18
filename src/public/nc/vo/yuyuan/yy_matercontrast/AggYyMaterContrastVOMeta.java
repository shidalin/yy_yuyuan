package nc.vo.yuyuan.yy_matercontrast;

import nc.vo.pubapp.pattern.model.meta.entity.bill.AbstractBillMeta;

public class AggYyMaterContrastVOMeta extends AbstractBillMeta{
	
	public AggYyMaterContrastVOMeta(){
		this.init();
	}
	
	private void init() {
		this.setParent(nc.vo.yuyuan.yy_matercontrast.YyMaterContrastVO.class);
		this.addChildren(nc.vo.yuyuan.yy_matercontrast.YyMaterContrastBVO.class);
	}
}