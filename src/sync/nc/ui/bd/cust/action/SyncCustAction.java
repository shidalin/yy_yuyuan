package nc.ui.bd.cust.action;

import java.awt.Cursor;
import java.awt.event.ActionEvent;

import nc.bs.framework.common.NCLocator;
import nc.ui.bd.cust.baseinfo.model.CustBaseInfoModel;
import nc.ui.pub.tools.BannerDialog;
import nc.ui.uif2.NCAction;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

/**
 * �ͻ�-ҵ��Ԫ-��ť��չ-ͬ�����̵���(57)
 * 
 * @author shidl
 * 
 */
public class SyncCustAction extends NCAction {

	public SyncCustAction() {
		this.setBtnName("ͬ�����̵���");
		this.setCode("syncCustAction");
	}

	private CustBaseInfoModel model;

	public CustBaseInfoModel getModel() {
		return model;
	}

	public void setModel(CustBaseInfoModel model) {
		this.model = model;
	}

	@Override
	public void doAction(ActionEvent e) throws Exception {
		// �����̣߳���ִ̨��ͬ��
		Runnable run = new Runnable() {
			@Override
			public void run() {
				// ����糵
				BannerDialog banner = new BannerDialog(model.getContext()
						.getEntranceUI());
				// ����������
				model.getContext().getEntranceUI()
						.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				banner.start();
				try {
					// ��ִ̨��
					NCLocator
							.getInstance()
							.lookup(nc.itf.bd.cust.baseinfo.ICustSupplierService.class)
							.syncCustProcess();
					nc.ui.uif2.ShowStatusBarMsgUtil.showStatusBarMsg(
							"���̵���ͬ���ɹ�,��ˢ�½���", model.getContext());
				} catch (BusinessException e) {
					nc.ui.uif2.ShowStatusBarMsgUtil.showErrorMsg("���̵���ͬ������",
							e.getMessage(), model.getContext());
					ExceptionUtils.wrappException(e);
					e.printStackTrace();
				} finally {
					banner.end();
					model.getContext().getEntranceUI()
							.setCursor(Cursor.getDefaultCursor());
				}
			}
		};
		// �����߳�
		new Thread(run).start();
	}

}
