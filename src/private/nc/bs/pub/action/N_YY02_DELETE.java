package nc.bs.pub.action;

import nc.bs.framework.common.NCLocator;
import nc.bs.pubapp.pf.action.AbstractPfAction;
import nc.impl.pubapp.pattern.rule.processer.CompareAroundProcesser;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

import nc.bs.yuyuan.order.plugin.bpplugin.Yy_orderPluginPoint;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;
import nc.itf.yuyuan.IYy_orderMaintain;

public class N_YY02_DELETE extends AbstractPfAction<AggYyOrderVO> {

	@Override
	protected CompareAroundProcesser<AggYyOrderVO> getCompareAroundProcesserWithRules(
			Object userObj) {
		CompareAroundProcesser<AggYyOrderVO> processor = new CompareAroundProcesser<AggYyOrderVO>(
				Yy_orderPluginPoint.SCRIPT_DELETE);
		// TODO 在此处添加前后规则
		return processor;
	}

	@Override
	protected AggYyOrderVO[] processBP(Object userObj,
			AggYyOrderVO[] clientFullVOs, AggYyOrderVO[] originBills) {
		IYy_orderMaintain operator = NCLocator.getInstance().lookup(
				IYy_orderMaintain.class);
		try {
			operator.delete(clientFullVOs, originBills);
		} catch (BusinessException e) {
			ExceptionUtils.wrappBusinessException(e.getMessage());
		}
		return clientFullVOs;
	}

}
