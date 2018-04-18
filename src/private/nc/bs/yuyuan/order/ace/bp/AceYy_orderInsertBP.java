package nc.bs.yuyuan.order.ace.bp;

import nc.bs.yuyuan.order.plugin.bpplugin.Yy_orderPluginPoint;
import nc.impl.pubapp.pattern.data.bill.template.InsertBPTemplate;
import nc.impl.pubapp.pattern.rule.processer.AroundProcesser;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;

/**
 * ��׼��������BP
 */
public class AceYy_orderInsertBP {

	public AggYyOrderVO[] insert(AggYyOrderVO[] bills) {

		InsertBPTemplate<AggYyOrderVO> bp = new InsertBPTemplate<AggYyOrderVO>(
				Yy_orderPluginPoint.INSERT);
		this.addBeforeRule(bp.getAroundProcesser());
		this.addAfterRule(bp.getAroundProcesser());
		return bp.insert(bills);

	}

	/**
	 * ���������
	 * 
	 * @param processor
	 */
	private void addAfterRule(AroundProcesser<AggYyOrderVO> processor) {
		// TODO ���������
		IRule<AggYyOrderVO> rule = null;
		
	}

	/**
	 * ����ǰ����
	 * 
	 * @param processor
	 */
	private void addBeforeRule(AroundProcesser<AggYyOrderVO> processer) {
		// TODO ����ǰ����
		IRule<AggYyOrderVO> rule = null;
		//�����֯��Ϣ����
		rule = new nc.bs.pubapp.pub.rule.FillInsertDataRule();
		processer.addBeforeRule(rule);
		//���ݺŹ���
		rule = new nc.bs.pubapp.pub.rule.CreateBillCodeRule();
		((nc.bs.pubapp.pub.rule.CreateBillCodeRule) rule).setCbilltype("YY02");
		((nc.bs.pubapp.pub.rule.CreateBillCodeRule) rule)
				.setCodeItem("vbillcode");
		((nc.bs.pubapp.pub.rule.CreateBillCodeRule) rule)
				.setGroupItem("pk_group");
		((nc.bs.pubapp.pub.rule.CreateBillCodeRule) rule).setOrgItem("pk_org");
		processer.addBeforeRule(rule);
		//���岻��Ϊ��У��
		rule = new nc.bs.pubapp.pub.rule.CheckNotNullRule();
		processer.addAfterRule(rule);
	}
}
