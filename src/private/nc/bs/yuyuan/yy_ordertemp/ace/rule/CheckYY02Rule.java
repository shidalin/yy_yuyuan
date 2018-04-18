package nc.bs.yuyuan.yy_ordertemp.ace.rule;

import java.util.ArrayList;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.yy_order.YyOrderVO;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;

/**
 * 弃审前校验规则：检查下游单据信息
 * 
 * @author shidl
 * 
 */
public class CheckYY02Rule implements IRule<AggYyOrderTempVO> {

	@Override
	public void process(AggYyOrderTempVO[] aggs) {
		if (aggs != null && aggs.length > 0) {
			List<String> pklist = new ArrayList<String>();
			for (AggYyOrderTempVO agg : aggs) {
				pklist.add(agg.getParentVO().getPk_ordertemp());
			}
			StringBuffer buffer = new StringBuffer();
			try {
				buffer.append("	select t1.*	")
						.append("	  from yy_order t1	")
						.append("	 inner join yy_order_b t2	")
						.append("	    on t1.pk_order = t2.pk_order	")
						.append("	 where nvl(t1.dr, 0) = 0	")
						.append("	   and nvl(t2.dr, 0) = 0 and ")
						.append(nc.pubitf.bd.reportitem.pub.SqlUtils.getInStr(
								"t2.csourceid", pklist, true));
				String sql = buffer.toString();
				BaseDAO dao = new BaseDAO();
				List<YyOrderVO> query = (List<YyOrderVO>) dao.executeQuery(sql,
						new BeanListProcessor(YyOrderVO.class));
				if (query.size() > 0) {
					buffer.setLength(0);
					buffer.append("弃审失败,存在下游订货单数据,订货单单据号:");
					for (YyOrderVO order : query) {
						buffer.append(order.getVbillcode()).append(",");
					}
					ExceptionUtils.wrappBusinessException(buffer.toString().substring(0, buffer.toString().length()-1));
				}
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				ExceptionUtils.wrappBusinessException(e.getMessage());
			}
		}
	}
}
