package nc.ui.yuyuan.order.ref;

import nc.bs.framework.common.NCLocator;
import nc.itf.yuyuan.IYy_ordertempMaintain;
import nc.ui.pubapp.uif2app.query2.model.IRefQueryService;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;
/**
 * 来源单据查询服务 功能：调用来源单据查询服务接口，实现来源单据查询
 * @author shidl
 *
 */
public class TempQueryServiceForOrder implements IRefQueryService {
	@Override
	public Object[] queryByQueryScheme(IQueryScheme queryScheme)
			throws Exception {
		AggYyOrderTempVO[] retbills = null;
		IYy_ordertempMaintain service = NCLocator.getInstance().lookup(
				IYy_ordertempMaintain.class);
		try {
			retbills = service.queryTempForOrder(queryScheme);
		} catch (BusinessException e) {
			ExceptionUtils.wrappException(e);
		}
		return retbills;
	}

	@Override
	public Object[] queryByWhereSql(String arg0) throws Exception {
		// TODO 自动生成的方法存根
		return null;
	}
}
