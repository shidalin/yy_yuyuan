package nc.ui.bd.material.ace.interceptor;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.itf.yunyuan.IWMSMessage;
import nc.ui.bd.material.baseinfo.model.MaterialBaseInfoModel;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.vo.bd.material.MaterialVO;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Collections;

public class SendToWMSAction extends NCAction {

	protected MaterialBaseInfoModel model;

	public SendToWMSAction() {
		super.setBtnName("����������WMS");
		super.setCode("sendToWMSActionForMaterial");
	}

	@Override
	public void doAction(ActionEvent e) throws Exception {
		Object[] selectedOperaDatas = model.getSelectedOperaDatas();
		if (selectedOperaDatas == null || selectedOperaDatas.length == 0) {
			ExceptionUtils.wrappBusinessException("��ѡ����Ч��������");
			ShowStatusBarMsgUtil.showErrorMsg("���ݷ���ʧ��", "��ѡ����Ч��������",
					model.getContext());
			return;
		}
		// ����Զ�̽ӿ�
		ArrayList<MaterialVO> list = new ArrayList<MaterialVO>();
		Collections.addAll(list, selectedOperaDatas);
		try {
			NCLocator.getInstance().lookup(IWMSMessage.class)
					.sendToWMSForMaterial((ArrayList<MaterialVO>) list);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			ExceptionUtils.wrappBusinessException(e1.getMessage());
			ShowStatusBarMsgUtil.showErrorMsg("���ݷ���ʧ��", e1.getMessage(),
					model.getContext());
		}
		ShowStatusBarMsgUtil.showStatusBarMsg("�����ɹ�����ˢ�½�������",
				model.getContext());
	}

	public MaterialBaseInfoModel getModel() {
		return model;
	}

	public void setModel(MaterialBaseInfoModel model) {
		this.model = model;
	}

}
