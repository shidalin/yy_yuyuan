package nc.bs.yuyuan.order.ace.rule;

import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;

public class checkPuOrderRule implements IRule<AggYyOrderVO> {

	@Override
	public void process(AggYyOrderVO[] agg) {
		List<nc.vo.pu.m21.entity.OrderHeaderVO> list;
		try {
			list = NCLocator.getInstance()
					.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
					.queryPOOrder(agg[0]);
			if (list != null && list.size() > 0) {
				StringBuffer buffer = new StringBuffer("下游采购订单单据号为:");
				for (nc.vo.pu.m21.entity.OrderHeaderVO order : list) {
					buffer.append(order.getVbillcode() + ",");
				}
				String codemessage = buffer.toString();
				codemessage = codemessage
						.substring(0, codemessage.length() - 1);
				ExceptionUtils.wrappBusinessException("弃审失败!该单据已存在下游采购订单数据;\n"
						+ codemessage);
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			ExceptionUtils.wrappBusinessException(e.getMessage());
		}

	}

}
