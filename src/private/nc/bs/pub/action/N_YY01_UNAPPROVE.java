package nc.bs.pub.action;

import nc.bs.framework.common.NCLocator;
import nc.bs.pubapp.pf.action.AbstractPfAction;
import nc.bs.pubapp.pub.rule.UnapproveStatusCheckRule;
import nc.bs.yuyuan.yy_ordertemp.plugin.bpplugin.Yy_ordertempPluginPoint;
import nc.impl.pubapp.pattern.rule.processer.CompareAroundProcesser;
import nc.itf.yuyuan.IYy_ordertempMaintain;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;

public class N_YY01_UNAPPROVE extends AbstractPfAction<AggYyOrderTempVO> {

	@Override
	protected CompareAroundProcesser<AggYyOrderTempVO> getCompareAroundProcesserWithRules(
			Object userObj) {
		CompareAroundProcesser<AggYyOrderTempVO> processor = new CompareAroundProcesser<AggYyOrderTempVO>(
				Yy_ordertempPluginPoint.UNAPPROVE);
		// TODO 在此处添加前后规则
		processor.addBeforeRule(new UnapproveStatusCheckRule());

		return processor;
	}

	@Override
	protected AggYyOrderTempVO[] processBP(Object userObj,
			AggYyOrderTempVO[] clientFullVOs, AggYyOrderTempVO[] originBills) {
		for (int i = 0; clientFullVOs != null && i < clientFullVOs.length; i++) {
			clientFullVOs[i].getParentVO().setStatus(VOStatus.UPDATED);
		}
		AggYyOrderTempVO[] bills = null;
		try {
			IYy_ordertempMaintain operator = NCLocator.getInstance()
					.lookup(IYy_ordertempMaintain.class);
			bills = operator.unapprove(clientFullVOs, originBills);
		} catch (BusinessException e) {
			ExceptionUtils.wrappBusinessException(e.getMessage());
		}
		return bills;
	}

}
