package nc.bs.pub.action;

import nc.bs.framework.common.NCLocator;
import nc.bs.pubapp.pf.action.AbstractPfAction;
import nc.bs.pubapp.pub.rule.UncommitStatusCheckRule;
import nc.bs.yuyuan.yy_ordertemp.plugin.bpplugin.Yy_ordertempPluginPoint;
import nc.impl.pubapp.pattern.rule.processer.CompareAroundProcesser;
import nc.itf.yuyuan.IYy_ordertempMaintain;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;

public class N_YY01_UNSAVEBILL extends AbstractPfAction<AggYyOrderTempVO> {

	@Override
	protected CompareAroundProcesser<AggYyOrderTempVO> getCompareAroundProcesserWithRules(
			Object userObj) {
		CompareAroundProcesser<AggYyOrderTempVO> processor = new CompareAroundProcesser<AggYyOrderTempVO>(
				Yy_ordertempPluginPoint.UNSEND_APPROVE);
		// TODO 在此处添加前后规则
		processor.addBeforeRule(new UncommitStatusCheckRule());

		return processor;
	}

	@Override
	protected AggYyOrderTempVO[] processBP(Object userObj,
			AggYyOrderTempVO[] clientFullVOs, AggYyOrderTempVO[] originBills) {
		IYy_ordertempMaintain operator = NCLocator.getInstance().lookup(
				IYy_ordertempMaintain.class);
		AggYyOrderTempVO[] bills = null;
		try {
			bills = operator.unsave(clientFullVOs, originBills);
		} catch (BusinessException e) {
			ExceptionUtils.wrappBusinessException(e.getMessage());
		}
		return bills;
	}

}
