package nc.ui.yuyuan.order.ace.serviceproxy;

import nc.bs.framework.common.NCLocator;
import nc.itf.yuyuan.IYy_orderMaintain;
import nc.ui.pubapp.uif2app.query2.model.IQueryService;
import nc.ui.querytemplate.querytree.IQueryScheme;

/**
 * 示例单据的操作代理
 * 
 * @author author
 * @version tempProject version
 */
public class AceYy_orderMaintainProxy implements IQueryService {
	@Override
	public Object[] queryByQueryScheme(IQueryScheme queryScheme)
			throws Exception {
		IYy_orderMaintain query = NCLocator.getInstance().lookup(
				IYy_orderMaintain.class);
		return query.query(queryScheme);
	}

}