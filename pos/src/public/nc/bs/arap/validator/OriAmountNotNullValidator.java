package nc.bs.arap.validator;

import java.util.Arrays;
import java.util.LinkedList;

import nc.bs.uif2.validation.ValidationFailure;
import nc.bs.uif2.validation.Validator;
import nc.itf.arap.fieldmap.IBillFieldGet;
import nc.vo.arap.pub.BillEnumCollection;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;

public class OriAmountNotNullValidator extends AbstractValidator implements
		Validator {

	private static final long serialVersionUID = 1L;

	@Override
	public ValidationFailure validate(Object obj) {
		if (UFBoolean.FALSE.equals(checkObj(obj)))
			return null;
		LinkedList<CircularlyAccessibleValueObject> childrens = new LinkedList<CircularlyAccessibleValueObject>();
		for (AggregatedValueObject aggvo : (AggregatedValueObject[]) obj) {
			// 20180228-shidl-POS����Ӧ��������ȥ������У��
			if (aggvo instanceof nc.vo.arap.receivable.AggReceivableBillVO) {
				nc.vo.arap.receivable.AggReceivableBillVO aggParam = (nc.vo.arap.receivable.AggReceivableBillVO) aggvo;
				String def1 = aggParam.getHeadVO().getDef1();
				if ("pos".equals(def1)) {
					continue;
				}
			}
			this.setObj(aggvo);
			childrens.addAll(Arrays.asList(aggvo.getChildrenVO()));
		}
		StringBuilder sb = new StringBuilder();
		for (CircularlyAccessibleValueObject child : childrens) {
			if (null == child.getAttributeValue(IBillFieldGet.DIRECTION))
				continue;
			// �и��ܸ�����У��
			if (BillEnumCollection.CommissionPayType.RefuseCommPay.VALUE
					.equals(child.getAttributeValue(IBillFieldGet.COMMPAYTYPE)))
				continue;
			Object money = null;
			Object local_money = null;
			Object local_tax = null;
			if (BillEnumCollection.Direction.CREDIT.VALUE.equals(child
					.getAttributeValue(IBillFieldGet.DIRECTION))) {

				local_tax = child.getAttributeValue(IBillFieldGet.LOCAL_TAX_CR);
				if (local_tax == null
						|| ((UFDouble) local_tax).compareTo(UFDouble.ZERO_DBL) == 0) {
					// ����ԭ��
					money = child.getAttributeValue(IBillFieldGet.MONEY_CR);
					local_money = child
							.getAttributeValue(IBillFieldGet.LOCAL_MONEY_CR);

					if (money == null
							|| ((UFDouble) money).compareTo(UFDouble.ZERO_DBL) == 0) { // ����ԭ�ҽ��Ϊ��
						if (local_money == null
								|| ((UFDouble) local_money)
										.compareTo(UFDouble.ZERO_DBL) == 0) { // �������ҽ��Ϊ��
							sb.append(nc.vo.ml.NCLangRes4VoTransl
									.getNCLangRes().getStrByID("2006v61020_0",
											"02006v61020-0095")/*
																 * @res
																 * "�˵��ݵĴ���ԭ�ҽ��������ҽ��Ϊ�ջ��㡣"
																 */);
						} else {
							sb.append(nc.vo.ml.NCLangRes4VoTransl
									.getNCLangRes().getStrByID("2006v61020_0",
											"02006v61020-0096")/*
																 * @res
																 * "�˵��ݵĴ���ԭ�ҽ��Ϊ�ջ��㡣"
																 */);
						}
					} else if (local_money == null
							|| ((UFDouble) local_money)
									.compareTo(UFDouble.ZERO_DBL) == 0) {
						{
							sb.append(nc.vo.ml.NCLangRes4VoTransl
									.getNCLangRes().getStrByID("2006v61020_0",
											"02006v61020-0097")/*
																 * @res
																 * "�˵��ݵĴ������ҽ��Ϊ�ջ��㡣"
																 */);
						}

					}

				}

			} else {

				local_tax = child.getAttributeValue(IBillFieldGet.LOCAL_TAX_DE);
				if (local_tax == null
						|| ((UFDouble) local_tax).compareTo(UFDouble.ZERO_DBL) == 0) {
					// �跽ԭ��
					money = child.getAttributeValue(IBillFieldGet.MONEY_DE);
					local_money = child
							.getAttributeValue(IBillFieldGet.LOCAL_MONEY_DE);

					if (money == null
							|| ((UFDouble) money).compareTo(UFDouble.ZERO_DBL) == 0) {

						if (local_money == null
								|| ((UFDouble) local_money)
										.compareTo(UFDouble.ZERO_DBL) == 0) {
							sb.append(nc.vo.ml.NCLangRes4VoTransl
									.getNCLangRes().getStrByID("2006v61020_0",
											"02006v61020-0098")/*
																 * @res
																 * "�˵��ݵĽ跽ԭ�ҽ��跽���ҽ��Ϊ�ջ��㡣"
																 */);
						} else {
							sb.append(nc.vo.ml.NCLangRes4VoTransl
									.getNCLangRes().getStrByID("2006v61020_0",
											"02006v61020-0099")/*
																 * @res
																 * "�˵��ݵĽ跽ԭ�ҽ��Ϊ�ջ��㡣"
																 */);
						}

					} else if (local_money == null
							|| ((UFDouble) local_money)
									.compareTo(UFDouble.ZERO_DBL) == 0) {
						sb.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
								.getStrByID("2006v61020_0", "02006v61020-0128")/*
																				 * @
																				 * res
																				 * "�˵��ݵĽ跽���ҽ��Ϊ�ջ��㡣"
																				 */);
					}
				}

			}
			if (sb.length() > 0) {
				break;
			}
		}
		if (sb.length() > 0) {
			return new ValidationFailure(sb.toString());
		}
		return null;
	}

}