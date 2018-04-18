package nc.ui.bd.supplier.ace.interceptor;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import nc.bs.framework.common.NCLocator;
import nc.itf.yunyuan.IWMSMessage;
import nc.ui.bd.supplier.baseinfo.model.SupplierBaseInfoModel;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Collections;

public class SendToWMSAction extends NCAction {

	protected SupplierBaseInfoModel model;

	public SendToWMSAction() {
		super.setBtnName("发送数据至WMS");
		super.setCode("sendToWMSActionForSupplier");
	}

	@Override
	public void doAction(ActionEvent e) throws Exception {
		Object[] selectedOperaDatas = model.getSelectedOperaDatas();
		if (selectedOperaDatas == null || selectedOperaDatas.length == 0) {
			ExceptionUtils.wrappBusinessException("请选择有效操作数据");
			ShowStatusBarMsgUtil.showErrorMsg("数据发送失败", "请选择有效操作数据",
					model.getContext());
			return;
		}
		// 调用远程接口
		ArrayList<SupplierVO> list = new ArrayList<SupplierVO>();
		Collections.addAll(list, selectedOperaDatas);
		try {
			NCLocator.getInstance().lookup(IWMSMessage.class)
					.sendToWMSForSupplier(list);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			ExceptionUtils.wrappBusinessException(e1.getMessage());
			ShowStatusBarMsgUtil.showErrorMsg("数据发送失败", e1.getMessage(),
					model.getContext());
		}
		ShowStatusBarMsgUtil.showStatusBarMsg("操作成功，请刷新界面数据",
				model.getContext());
	}

	public SupplierBaseInfoModel getModel() {
		return model;
	}

	public void setModel(SupplierBaseInfoModel model) {
		this.model = model;
	}

}
