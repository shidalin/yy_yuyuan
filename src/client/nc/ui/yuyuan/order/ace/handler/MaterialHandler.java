package nc.ui.yuyuan.order.ace.handler;

import java.util.Arrays;
import java.util.List;

import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITextField;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterEditEvent;
import nc.ui.pubapp.uif2app.event.card.CardBodyBeforeEditEvent;
import nc.ui.pubapp.uif2app.view.util.RefMoreSelectedUtils;
import nc.ui.uif2.components.RefPanel;

/**
 * 物料的编辑事件处理类
 * 
 * @since 6.0
 * @version 2011-7-7 下午02:44:20
 * @author duy
 */
public class MaterialHandler {
	/**
	 * 物料的编辑后事件处理
	 * 
	 * @param e
	 *            编辑后事件
	 */
	public void afterEdit(CardBodyAfterEditEvent e) {
		// 为了效率，先关闭合计
		boolean nc = e.getBillCardPanel().getBillModel().isNeedCalculate();
		e.getBillCardPanel().getBillModel().setNeedCalculate(false);
		// 物料的多选处理
		RefMoreSelectedUtils utils = new RefMoreSelectedUtils(
				e.getBillCardPanel());
		utils.refMoreSelected(e.getRow(), "cmaterialvid", true);
	}

	/**
	 * 物料的编辑前事件处理
	 * 
	 * @param e
	 *            编辑前事件处理
	 */
	public void beforeEdit(CardBodyBeforeEditEvent e) {
		BillCardPanel panel = e.getBillCardPanel();
		// 设置物料的参照允许多选
		UIRefPane refPane = (UIRefPane) panel.getBodyItem(e.getKey())
				.getComponent();
		refPane.setMultiSelectedEnabled(true);
		e.setReturnValue(Boolean.TRUE);
	}

}
