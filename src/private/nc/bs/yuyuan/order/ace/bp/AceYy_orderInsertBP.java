package nc.bs.yuyuan.order.ace.bp;

import nc.bs.yuyuan.order.plugin.bpplugin.Yy_orderPluginPoint;
import nc.impl.pubapp.pattern.data.bill.template.InsertBPTemplate;
import nc.impl.pubapp.pattern.rule.processer.AroundProcesser;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;

/**
 * 标准单据新增BP
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
	 * 新增后规则
	 * 
	 * @param processor
	 */
	private void addAfterRule(AroundProcesser<AggYyOrderVO> processor) {
		// TODO 新增后规则
		IRule<AggYyOrderVO> rule = null;
		
	}

	/**
	 * 新增前规则
	 * 
	 * @param processor
	 */
	private void addBeforeRule(AroundProcesser<AggYyOrderVO> processer) {
		// TODO 新增前规则
		IRule<AggYyOrderVO> rule = null;
		//填充组织信息规则
		rule = new nc.bs.pubapp.pub.rule.FillInsertDataRule();
		processer.addBeforeRule(rule);
		//单据号规则
		rule = new nc.bs.pubapp.pub.rule.CreateBillCodeRule();
		((nc.bs.pubapp.pub.rule.CreateBillCodeRule) rule).setCbilltype("YY02");
		((nc.bs.pubapp.pub.rule.CreateBillCodeRule) rule)
				.setCodeItem("vbillcode");
		((nc.bs.pubapp.pub.rule.CreateBillCodeRule) rule)
				.setGroupItem("pk_group");
		((nc.bs.pubapp.pub.rule.CreateBillCodeRule) rule).setOrgItem("pk_org");
		processer.addBeforeRule(rule);
		//表体不能为空校验
		rule = new nc.bs.pubapp.pub.rule.CheckNotNullRule();
		processer.addAfterRule(rule);
	}
}
