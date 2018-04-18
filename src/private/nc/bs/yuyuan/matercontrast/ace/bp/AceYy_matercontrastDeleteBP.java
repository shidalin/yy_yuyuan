package nc.bs.yuyuan.matercontrast.ace.bp;

import nc.bs.yuyuan.matercontrast.plugin.bpplugin.Yy_matercontrastPluginPoint;
import nc.vo.yuyuan.yy_matercontrast.AggYyMaterContrastVO;

import nc.impl.pubapp.pattern.data.bill.template.DeleteBPTemplate;
import nc.impl.pubapp.pattern.rule.processer.AroundProcesser;
import nc.impl.pubapp.pattern.rule.IRule;


/**
 * ��׼����ɾ��BP
 */
public class AceYy_matercontrastDeleteBP {

	public void delete(AggYyMaterContrastVO[] bills) {

		DeleteBPTemplate<AggYyMaterContrastVO> bp = new DeleteBPTemplate<AggYyMaterContrastVO>(
				Yy_matercontrastPluginPoint.DELETE);
		// ����ִ��ǰ����
		this.addBeforeRule(bp.getAroundProcesser());
		// ����ִ�к�ҵ�����
		this.addAfterRule(bp.getAroundProcesser());
		bp.delete(bills);
	}

	private void addBeforeRule(AroundProcesser<AggYyMaterContrastVO> processer) {
		// TODO ǰ����
		IRule<AggYyMaterContrastVO> rule = null;
//		rule = new nc.bs.pubapp.pub.rule.BillDeleteStatusCheckRule();
//		processer.addBeforeRule(rule);
	}

	/**
	 * ɾ����ҵ�����
	 * 
	 * @param processer
	 */
	private void addAfterRule(AroundProcesser<AggYyMaterContrastVO> processer) {
		// TODO �����

	}
}
