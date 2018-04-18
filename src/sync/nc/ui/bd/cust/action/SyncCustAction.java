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
 * 客户-业务单元-按钮扩展-同步客商档案(57)
 * 
 * @author shidl
 * 
 */
public class SyncCustAction extends NCAction {

	public SyncCustAction() {
		this.setBtnName("同步客商档案");
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
		// 另起线程，后台执行同步
		Runnable run = new Runnable() {
			@Override
			public void run() {
				// 界面风车
				BannerDialog banner = new BannerDialog(model.getContext()
						.getEntranceUI());
				// 启动进度条
				model.getContext().getEntranceUI()
						.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				banner.start();
				try {
					// 后台执行
					NCLocator
							.getInstance()
							.lookup(nc.itf.bd.cust.baseinfo.ICustSupplierService.class)
							.syncCustProcess();
					nc.ui.uif2.ShowStatusBarMsgUtil.showStatusBarMsg(
							"客商档案同步成功,请刷新界面", model.getContext());
				} catch (BusinessException e) {
					nc.ui.uif2.ShowStatusBarMsgUtil.showErrorMsg("客商档案同步错误",
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
		// 启动线程
		new Thread(run).start();
	}

}
