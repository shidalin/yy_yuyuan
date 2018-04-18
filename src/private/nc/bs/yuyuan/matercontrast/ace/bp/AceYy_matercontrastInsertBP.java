package nc.bs.yuyuan.matercontrast.ace.bp;

import nc.bs.yuyuan.matercontrast.plugin.bpplugin.Yy_matercontrastPluginPoint;
import nc.impl.pubapp.pattern.data.bill.template.InsertBPTemplate;
import nc.impl.pubapp.pattern.rule.processer.AroundProcesser;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.vo.yuyuan.yy_matercontrast.AggYyMaterContrastVO;
import nc.bs.yuyuan.matercontrast.ace.rule.CheckMaterialRule;

/**
 * 标准单据新增BP
 */
public class AceYy_matercontrastInsertBP {

	public AggYyMaterContrastVO[] insert(AggYyMaterContrastVO[] bills) {

		InsertBPTemplate<AggYyMaterContrastVO> bp = new InsertBPTemplate<AggYyMaterContrastVO>(
				Yy_matercontrastPluginPoint.INSERT);
		this.addBeforeRule(bp.getAroundProcesser());
		this.addAfterRule(bp.getAroundProcesser());
		return bp.insert(bills);

	}

	/**
	 * 新增后规则
	 * 
	 * @param processor
	 */
	private void addAfterRule(AroundProcesser<AggYyMaterContrastVO> processor) {
		// TODO 新增后规则
		IRule<AggYyMaterContrastVO> rule = null;
		// 物料校验规则
		rule = new CheckMaterialRule();
		processor.addAfterRule(rule);
	}

	/**
	 * 新增前规则
	 * 
	 * @param processor
	 */
	private void addBeforeRule(AroundProcesser<AggYyMaterContrastVO> processer) {
		// TODO 新增前规则
		IRule<AggYyMaterContrastVO> rule = null;
		rule = new nc.bs.pubapp.pub.rule.FillInsertDataRule();
		processer.addBeforeRule(rule);
		rule = new nc.bs.pubapp.pub.rule.CreateBillCodeRule();
		((nc.bs.pubapp.pub.rule.CreateBillCodeRule) rule).setCbilltype("YY03");
		((nc.bs.pubapp.pub.rule.CreateBillCodeRule) rule)
				.setCodeItem("vbillcode");
		((nc.bs.pubapp.pub.rule.CreateBillCodeRule) rule)
				.setGroupItem("pk_group");
		((nc.bs.pubapp.pub.rule.CreateBillCodeRule) rule).setOrgItem("pk_org");
		processer.addBeforeRule(rule);
		// 表体不能为空校验
		rule = new nc.bs.pubapp.pub.rule.CheckNotNullRule();
		processer.addBeforeRule(rule);
	}
}
