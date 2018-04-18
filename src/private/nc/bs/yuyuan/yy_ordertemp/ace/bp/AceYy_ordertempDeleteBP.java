package nc.bs.yuyuan.yy_ordertemp.ace.bp;

import nc.bs.yuyuan.order.plugin.bpplugin.Yy_orderPluginPoint;
import nc.impl.pubapp.pattern.data.bill.template.DeleteBPTemplate;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.impl.pubapp.pattern.rule.processer.AroundProcesser;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;


/**
 * 标准单据删除BP
 */
public class AceYy_ordertempDeleteBP {

	public void delete(AggYyOrderTempVO[] bills) {

		DeleteBPTemplate<AggYyOrderTempVO> bp = new DeleteBPTemplate<AggYyOrderTempVO>(
				Yy_orderPluginPoint.DELETE);
		// 增加执行前规则
		this.addBeforeRule(bp.getAroundProcesser());
		// 增加执行后业务规则
		this.addAfterRule(bp.getAroundProcesser());
		bp.delete(bills);
	}

	private void addBeforeRule(AroundProcesser<AggYyOrderTempVO> processer) {
		// TODO 前规则
		IRule<AggYyOrderTempVO> rule = null;
		rule = new nc.bs.pubapp.pub.rule.BillDeleteStatusCheckRule();
		processer.addBeforeRule(rule);
	}

	/**
	 * 删除后业务规则
	 * 
	 * @param processer
	 */
	private void addAfterRule(AroundProcesser<AggYyOrderTempVO> processer) {
		// TODO 后规则

	}
}
