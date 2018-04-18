package nc.bs.yuyuan.yy_ordertemp.ace.rule;

import nc.impl.pubapp.pattern.rule.IRule;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;

/**
 * 填充表体组织信息
 * 
 * @author shidl
 * 
 */
public class FillChildrenInfoRule implements IRule<AggYyOrderTempVO> {

	@Override
	public void process(AggYyOrderTempVO[] aggs) {
		if (aggs != null && aggs.length > 0) {
			for (AggYyOrderTempVO agg : aggs) {
				String pk_group = agg.getParentVO().getPk_group();
				String pk_org = agg.getParentVO().getPk_org();
				String pk_org_v = agg.getParentVO().getPk_org_v();
				for (CircularlyAccessibleValueObject cvo : agg.getChildrenVO()) {
					cvo.setAttributeValue("pk_group", pk_group);
					cvo.setAttributeValue("pk_org", pk_org);
					cvo.setAttributeValue("pk_org_v", pk_org_v);
				}
			}
		}
	}
}
