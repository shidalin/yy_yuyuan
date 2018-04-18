package nc.bs.yuyuan.yy_ordertemp.ace.bp;

import nc.bs.yuyuan.order.plugin.bpplugin.Yy_orderPluginPoint;
import nc.impl.pubapp.pattern.data.bill.template.DeleteBPTemplate;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.impl.pubapp.pattern.rule.processer.AroundProcesser;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;


/**
 * ��׼����ɾ��BP
 */
public class AceYy_ordertempDeleteBP {

	public void delete(AggYyOrderTempVO[] bills) {

		DeleteBPTemplate<AggYyOrderTempVO> bp = new DeleteBPTemplate<AggYyOrderTempVO>(
				Yy_orderPluginPoint.DELETE);
		// ����ִ��ǰ����
		this.addBeforeRule(bp.getAroundProcesser());
		// ����ִ�к�ҵ�����
		this.addAfterRule(bp.getAroundProcesser());
		bp.delete(bills);
	}

	private void addBeforeRule(AroundProcesser<AggYyOrderTempVO> processer) {
		// TODO ǰ����
		IRule<AggYyOrderTempVO> rule = null;
		rule = new nc.bs.pubapp.pub.rule.BillDeleteStatusCheckRule();
		processer.addBeforeRule(rule);
	}

	/**
	 * ɾ����ҵ�����
	 * 
	 * @param processer
	 */
	private void addAfterRule(AroundProcesser<AggYyOrderTempVO> processer) {
		// TODO �����

	}
}
