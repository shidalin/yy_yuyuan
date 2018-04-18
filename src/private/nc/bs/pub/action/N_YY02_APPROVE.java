package nc.bs.pub.action;

import nc.bs.framework.common.NCLocator;
import nc.bs.pubapp.pf.action.AbstractPfAction;
import nc.bs.pubapp.pub.rule.ApproveStatusCheckRule;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.impl.pubapp.pattern.rule.processer.CompareAroundProcesser;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

import nc.bs.yuyuan.order.plugin.bpplugin.Yy_orderPluginPoint;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;
import nc.itf.yuyuan.IYy_orderMaintain;

public class N_YY02_APPROVE extends AbstractPfAction<AggYyOrderVO> {

	public N_YY02_APPROVE() {
		super();
	}

	@Override
	protected CompareAroundProcesser<AggYyOrderVO> getCompareAroundProcesserWithRules(
			Object userObj) {
		CompareAroundProcesser<AggYyOrderVO> processor = new CompareAroundProcesser<AggYyOrderVO>(
				Yy_orderPluginPoint.APPROVE);
		processor.addBeforeRule(new ApproveStatusCheckRule());
		return processor;
	}

	@Override
	protected AggYyOrderVO[] processBP(Object userObj,
			AggYyOrderVO[] clientFullVOs, AggYyOrderVO[] originBills) {
		AggYyOrderVO[] bills = null;
		IYy_orderMaintain operator = NCLocator.getInstance().lookup(
				IYy_orderMaintain.class);
		try {
			bills = operator.approve(clientFullVOs, originBills);
		} catch (BusinessException e) {
			ExceptionUtils.wrappBusinessException(e.getMessage());
		}
		return bills;
	}

}
