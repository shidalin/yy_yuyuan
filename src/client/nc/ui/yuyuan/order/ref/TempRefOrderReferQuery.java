package nc.ui.yuyuan.order.ref;

import java.awt.Container;

import nc.ui.pubapp.billref.src.DefaultBillReferQuery;
import nc.ui.pubapp.uif2app.query2.QueryConditionDLGDelegator;
import nc.vo.querytemplate.TemplateInfo;

/**
 * ��Դ���ݲ�ѯ�� ���ܣ���Դ���ݲ�ѯģ���ʼ�� �趨����
 * 
 * @author shidl
 * 
 */
public class TempRefOrderReferQuery extends DefaultBillReferQuery {

	public TempRefOrderReferQuery(Container c, TemplateInfo info) {
		super(c, info);
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	public void initQueryConditionDLG(QueryConditionDLGDelegator dlgDelegator) {

	}
}