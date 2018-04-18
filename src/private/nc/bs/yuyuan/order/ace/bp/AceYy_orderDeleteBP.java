package nc.bs.yuyuan.order.ace.bp;

import nc.bs.yuyuan.order.plugin.bpplugin.Yy_orderPluginPoint;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;

import nc.impl.pubapp.pattern.data.bill.template.DeleteBPTemplate;
import nc.impl.pubapp.pattern.rule.processer.AroundProcesser;
import nc.impl.pubapp.pattern.rule.IRule;


/**
 * 标准单据删除BP
 */
public class AceYy_orderDeleteBP {

	public void delete(AggYyOrderVO[] bills) {

		DeleteBPTemplate<AggYyOrderVO> bp = new DeleteBPTemplate<AggYyOrderVO>(
				Yy_orderPluginPoint.DELETE);
		// 增加执行前规则
		this.addBeforeRule(bp.getAroundProcesser());
		// 增加执行后业务规则
		this.addAfterRule(bp.getAroundProcesser());
		bp.delete(bills);
	}

	private void addBeforeRule(AroundProcesser<AggYyOrderVO> processer) {
		// TODO 前规则
		IRule<AggYyOrderVO> rule = null;
		rule = new nc.bs.pubapp.pub.rule.BillDeleteStatusCheckRule();
		processer.addBeforeRule(rule);
	}

	/**
	 * 删除后业务规则
	 * 
	 * @param processer
	 */
	private void addAfterRule(AroundProcesser<AggYyOrderVO> processer) {
		// TODO 后规则

	}
}
