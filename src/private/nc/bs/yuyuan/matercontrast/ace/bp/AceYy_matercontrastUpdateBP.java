package nc.bs.yuyuan.matercontrast.ace.bp;

import nc.bs.yuyuan.matercontrast.ace.rule.CheckMaterialRule;
import nc.bs.yuyuan.matercontrast.plugin.bpplugin.Yy_matercontrastPluginPoint;
import nc.impl.pubapp.pattern.data.bill.template.UpdateBPTemplate;
import nc.impl.pubapp.pattern.rule.processer.CompareAroundProcesser;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.vo.yuyuan.yy_matercontrast.AggYyMaterContrastVO;

/**
 * �޸ı����BP
 * 
 */
public class AceYy_matercontrastUpdateBP {

	public AggYyMaterContrastVO[] update(AggYyMaterContrastVO[] bills,
			AggYyMaterContrastVO[] originBills) {
		// �����޸�ģ��
		UpdateBPTemplate<AggYyMaterContrastVO> bp = new UpdateBPTemplate<AggYyMaterContrastVO>(
				Yy_matercontrastPluginPoint.UPDATE);
		// ִ��ǰ����
		this.addBeforeRule(bp.getAroundProcesser());
		// ִ�к����
		this.addAfterRule(bp.getAroundProcesser());
		return bp.update(bills, originBills);
	}

	private void addAfterRule(
			CompareAroundProcesser<AggYyMaterContrastVO> processer) {
		// TODO �����
		IRule<AggYyMaterContrastVO> rule = null;
		// ����У�����
		rule = new CheckMaterialRule();
		processer.addAfterRule(rule);
	}

	private void addBeforeRule(
			CompareAroundProcesser<AggYyMaterContrastVO> processer) {
		// TODO ǰ����
		IRule<AggYyMaterContrastVO> rule = null;
		rule = new nc.bs.pubapp.pub.rule.FillUpdateDataRule();
		processer.addBeforeRule(rule);
	}

}
