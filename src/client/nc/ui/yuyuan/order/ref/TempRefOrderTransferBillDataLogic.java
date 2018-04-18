package nc.ui.yuyuan.order.ref;

import nc.ui.pubapp.billref.dest.DefaultBillDataLogic;
import nc.vo.pubapp.AppContext;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;

public class TempRefOrderTransferBillDataLogic extends DefaultBillDataLogic {
	@Override
	public void doTransferAddLogic(Object selectedData) {
		// 1.填充单据类型信息
		AggYyOrderVO agg = (AggYyOrderVO) selectedData;
		agg.getParentVO().setPk_billtypecode("YY02");
		agg.getParentVO().setPk_billtypeid(
				nc.vo.am.common.util.BillTypeUtils.getPKByCode("YY02"));
		agg.getParentVO().setDmakedate(AppContext.getInstance().getBusiDate());
		// 把数据设置在界面上
		super.doTransferAddLogic(selectedData);
	}
}
