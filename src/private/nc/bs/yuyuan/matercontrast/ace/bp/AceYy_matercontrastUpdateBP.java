package nc.bs.yuyuan.matercontrast.ace.bp;

import nc.bs.yuyuan.matercontrast.ace.rule.CheckMaterialRule;
import nc.bs.yuyuan.matercontrast.plugin.bpplugin.Yy_matercontrastPluginPoint;
import nc.impl.pubapp.pattern.data.bill.template.UpdateBPTemplate;
import nc.impl.pubapp.pattern.rule.processer.CompareAroundProcesser;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.vo.yuyuan.yy_matercontrast.AggYyMaterContrastVO;

/**
 * 修改保存的BP
 * 
 */
public class AceYy_matercontrastUpdateBP {

	public AggYyMaterContrastVO[] update(AggYyMaterContrastVO[] bills,
			AggYyMaterContrastVO[] originBills) {
		// 调用修改模板
		UpdateBPTemplate<AggYyMaterContrastVO> bp = new UpdateBPTemplate<AggYyMaterContrastVO>(
				Yy_matercontrastPluginPoint.UPDATE);
		// 执行前规则
		this.addBeforeRule(bp.getAroundProcesser());
		// 执行后规则
		this.addAfterRule(bp.getAroundProcesser());
		return bp.update(bills, originBills);
	}

	private void addAfterRule(
			CompareAroundProcesser<AggYyMaterContrastVO> processer) {
		// TODO 后规则
		IRule<AggYyMaterContrastVO> rule = null;
		// 物料校验规则
		rule = new CheckMaterialRule();
		processer.addAfterRule(rule);
	}

	private void addBeforeRule(
			CompareAroundProcesser<AggYyMaterContrastVO> processer) {
		// TODO 前规则
		IRule<AggYyMaterContrastVO> rule = null;
		rule = new nc.bs.pubapp.pub.rule.FillUpdateDataRule();
		processer.addBeforeRule(rule);
	}

}
