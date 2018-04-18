package nc.bs.yuyuan.yy_ordertemp.ace.bp;

import nc.bs.yuyuan.order.plugin.bpplugin.Yy_orderPluginPoint;
import nc.impl.pubapp.pattern.data.bill.template.UpdateBPTemplate;
import nc.impl.pubapp.pattern.rule.ICompareRule;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.impl.pubapp.pattern.rule.processer.CompareAroundProcesser;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;

/**
 * �޸ı����BP
 * 
 */
public class AceYy_ordertempUpdateBP {

	public AggYyOrderTempVO[] update(AggYyOrderTempVO[] bills,
			AggYyOrderTempVO[] originBills) {
		// �����޸�ģ��
		UpdateBPTemplate<AggYyOrderTempVO> bp = new UpdateBPTemplate<AggYyOrderTempVO>(
				Yy_orderPluginPoint.UPDATE);
		// ִ��ǰ����
		this.addBeforeRule(bp.getAroundProcesser());
		// ִ�к����
		this.addAfterRule(bp.getAroundProcesser());
		return bp.update(bills, originBills);
	}

	private void addAfterRule(CompareAroundProcesser<AggYyOrderTempVO> processer) {
		// TODO �����
		IRule<AggYyOrderTempVO> rule = null;
		rule = new nc.bs.pubapp.pub.rule.BillCodeCheckRule();
		((nc.bs.pubapp.pub.rule.BillCodeCheckRule) rule).setCbilltype("YY01");
		((nc.bs.pubapp.pub.rule.BillCodeCheckRule) rule)
				.setCodeItem("vbillcode");
		((nc.bs.pubapp.pub.rule.BillCodeCheckRule) rule)
				.setGroupItem("pk_group");
		((nc.bs.pubapp.pub.rule.BillCodeCheckRule) rule).setOrgItem("pk_org");
		processer.addAfterRule(rule);
	
	}

	private void addBeforeRule(
			CompareAroundProcesser<AggYyOrderTempVO> processer) {
		// TODO ǰ����
		IRule<AggYyOrderTempVO> rule = null;
		rule = new nc.bs.pubapp.pub.rule.FillUpdateDataRule();
		processer.addBeforeRule(rule);
		ICompareRule<AggYyOrderTempVO> ruleCom = new nc.bs.pubapp.pub.rule.UpdateBillCodeRule();
		((nc.bs.pubapp.pub.rule.UpdateBillCodeRule) ruleCom)
				.setCbilltype("YY01");
		((nc.bs.pubapp.pub.rule.UpdateBillCodeRule) ruleCom)
				.setCodeItem("vbillcode");
		((nc.bs.pubapp.pub.rule.UpdateBillCodeRule) ruleCom)
				.setGroupItem("pk_group");
		((nc.bs.pubapp.pub.rule.UpdateBillCodeRule) ruleCom)
				.setOrgItem("pk_org");
		processer.addBeforeRule(ruleCom);
		rule = new nc.bs.pubapp.pub.rule.FieldLengthCheckRule();
		processer.addBeforeRule(rule);
		rule = new nc.bs.yuyuan.yy_ordertemp.ace.rule.FillChildrenInfoRule();
		processer.addBeforeRule(rule);
	}

}
