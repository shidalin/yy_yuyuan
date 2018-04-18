package nc.bs.yuyuan.matercontrast.ace.bp;

import nc.bs.yuyuan.matercontrast.plugin.bpplugin.Yy_matercontrastPluginPoint;
import nc.vo.yuyuan.yy_matercontrast.AggYyMaterContrastVO;

import nc.impl.pubapp.pattern.data.bill.template.DeleteBPTemplate;
import nc.impl.pubapp.pattern.rule.processer.AroundProcesser;
import nc.impl.pubapp.pattern.rule.IRule;


/**
 * 标准单据删除BP
 */
public class AceYy_matercontrastDeleteBP {

	public void delete(AggYyMaterContrastVO[] bills) {

		DeleteBPTemplate<AggYyMaterContrastVO> bp = new DeleteBPTemplate<AggYyMaterContrastVO>(
				Yy_matercontrastPluginPoint.DELETE);
		// 增加执行前规则
		this.addBeforeRule(bp.getAroundProcesser());
		// 增加执行后业务规则
		this.addAfterRule(bp.getAroundProcesser());
		bp.delete(bills);
	}

	private void addBeforeRule(AroundProcesser<AggYyMaterContrastVO> processer) {
		// TODO 前规则
		IRule<AggYyMaterContrastVO> rule = null;
//		rule = new nc.bs.pubapp.pub.rule.BillDeleteStatusCheckRule();
//		processer.addBeforeRule(rule);
	}

	/**
	 * 删除后业务规则
	 * 
	 * @param processer
	 */
	private void addAfterRule(AroundProcesser<AggYyMaterContrastVO> processer) {
		// TODO 后规则

	}
}
