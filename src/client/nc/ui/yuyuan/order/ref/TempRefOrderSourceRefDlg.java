package nc.ui.yuyuan.order.ref;

import java.awt.Container;

import nc.ui.pub.pf.BillSourceVar;
import nc.ui.pubapp.billref.src.view.SourceRefDlg;

/**
 ** ��Դ������ʾ�� ���ܣ���ѯ���ѡ������ ��д����getRefBillInfoBeanPath(), �����������������ļ�
 * 
 * @author shidl
 * 
 */
public class TempRefOrderSourceRefDlg extends SourceRefDlg {

	public TempRefOrderSourceRefDlg(Container parent, BillSourceVar bsVar) {
		super(parent, bsVar);
		// TODO �Զ����ɵĹ��캯�����
	}

	private static final long serialVersionUID = 1L;

	/**
	 * ���������ļ�
	 */
	@Override
	public String getRefBillInfoBeanPath() {
		return "nc/ui/yuyuan/order/ref/TempRefOrderInfo.xml";
	}
}
