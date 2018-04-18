package nc.bs.pub.action;

import nc.bs.framework.common.NCLocator;
import nc.bs.pubapp.pf.action.AbstractPfAction;
import nc.bs.yuyuan.yy_ordertemp.plugin.bpplugin.Yy_ordertempPluginPoint;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.impl.pubapp.pattern.rule.processer.CompareAroundProcesser;
import nc.itf.yuyuan.IYy_ordertempMaintain;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;

public class N_YY01_SAVEBASE extends AbstractPfAction<AggYyOrderTempVO> {

	@Override
	protected CompareAroundProcesser<AggYyOrderTempVO> getCompareAroundProcesserWithRules(
			Object userObj) {
		CompareAroundProcesser<AggYyOrderTempVO> processor = null;
		AggYyOrderTempVO[] clientFullVOs = (AggYyOrderTempVO[]) this.getVos();
		if (!StringUtil.isEmptyWithTrim(clientFullVOs[0].getParentVO()
				.getPrimaryKey())) {
			processor = new CompareAroundProcesser<AggYyOrderTempVO>(
					Yy_ordertempPluginPoint.SCRIPT_UPDATE);
		} else {
			processor = new CompareAroundProcesser<AggYyOrderTempVO>(
					Yy_ordertempPluginPoint.SCRIPT_INSERT);
		}
		// TODO 在此处添加前后规则
		IRule<AggYyOrderTempVO> rule = null;

		return processor;
	}

	@Override
	protected AggYyOrderTempVO[] processBP(Object userObj,
			AggYyOrderTempVO[] clientFullVOs, AggYyOrderTempVO[] originBills) {

		AggYyOrderTempVO[] bills = null;
		try {
			IYy_ordertempMaintain operator = NCLocator.getInstance()
					.lookup(IYy_ordertempMaintain.class);
			if (!StringUtil.isEmptyWithTrim(clientFullVOs[0].getParentVO()
					.getPrimaryKey())) {
				bills = operator.update(clientFullVOs, originBills);
			} else {
				bills = operator.insert(clientFullVOs, originBills);
			}
		} catch (BusinessException e) {
			ExceptionUtils.wrappBusinessException(e.getMessage());
		}
		return bills;
	}
}
