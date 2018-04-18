package nc.ui.ct.purdaily.handler;

import nc.bs.uif2.validation.ValidationException;
import nc.ui.pubapp.pub.power.PowerValidateService;
import nc.vo.ct.purdaily.entity.AggCtPuVO;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

/**
 * �ɹ���ͬУ�鴦���������ǡ���Ч����ȡ����ֹ�����ⶳ����ť����(�޸�xml�ļ����)
 * 
 * @author shidl
 * 
 */
public class CTMaterialValidateService extends PowerValidateService {

	@Override
	public void validate(Object value) throws ValidationException {
		checherProcess(value);
		super.validate(value);
	}

	/**
	 * 
	 * @param value
	 * @throws ValidationException
	 */
	private void checherProcess(Object value) throws ValidationException {
		try {
			AggCtPuVO[] aggs = (AggCtPuVO[]) value;
			if (aggs != null && aggs.length > 0) {
				for (AggCtPuVO agg : aggs) {
					new CTMaterialChecker().process((AggCtPuVO) agg, null);
				}
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			ExceptionUtils.wrappBusinessException(e.getMessage());
		}
	}
}
