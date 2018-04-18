package nc.ui.yuyuan.order.action;

import java.awt.event.ActionEvent;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.ui.pubapp.uif2app.actions.pflow.ScriptPFlowAction;
import nc.vo.pub.pf.BillStatusEnum;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;

/**
 * 订货单-生成采购订单按钮
 * 
 * @author shidl
 * 
 */
public class ToPoOrderAction extends ScriptPFlowAction {

	public ToPoOrderAction() {
		this.setBtnName("生成采购订单");
		this.setCode("PUSHPO");
	}

	@Override
	public void doAction(ActionEvent e) throws Exception {
		checkData();
		super.doAction(e);

	}

	/**
	 * 按钮权限：过滤审批数据
	 */
	@Override
	protected boolean isActionEnable() {
		AggYyOrderVO agg = (AggYyOrderVO) this.getModel().getSelectedData();
		if (agg != null
				&& BillStatusEnum.APPROVED.value().equals(
						agg.getParentVO().getVbillstatus())) {
			return true;
		}
		return false;
	}

	/**
	 * 数据校验
	 * 
	 * @throws Exception
	 */
	private void checkData() throws Exception {
		AggYyOrderVO agg = (AggYyOrderVO) this.getModel().getSelectedData();
		List<nc.vo.pu.m21.entity.OrderHeaderVO> list = NCLocator.getInstance()
				.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
				.queryPOOrder(agg);
		if (list != null && list.size() > 0) {
			StringBuffer buffer = new StringBuffer("下游采购订单单据号为:");
			for (nc.vo.pu.m21.entity.OrderHeaderVO order : list) {
				buffer.append(order.getVbillcode() + ",");
			}
			String codemessage = buffer.toString();
			codemessage = codemessage.substring(0, codemessage.length() - 1);
			ExceptionUtils.wrappBusinessException("推送数据失败!该单据已存在下游采购订单数据;\n"
					+ codemessage);
		}
	}

}
