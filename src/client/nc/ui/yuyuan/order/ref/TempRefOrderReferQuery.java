package nc.ui.yuyuan.order.ref;

import java.awt.Container;

import nc.ui.pubapp.billref.src.DefaultBillReferQuery;
import nc.ui.pubapp.uif2app.query2.QueryConditionDLGDelegator;
import nc.vo.querytemplate.TemplateInfo;

/**
 * 来源单据查询类 功能：来源单据查询模板初始化 设定过滤
 * 
 * @author shidl
 * 
 */
public class TempRefOrderReferQuery extends DefaultBillReferQuery {

	public TempRefOrderReferQuery(Container c, TemplateInfo info) {
		super(c, info);
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void initQueryConditionDLG(QueryConditionDLGDelegator dlgDelegator) {

	}
}