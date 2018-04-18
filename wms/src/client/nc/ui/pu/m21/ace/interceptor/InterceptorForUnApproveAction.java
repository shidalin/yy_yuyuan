package nc.ui.pu.m21.ace.interceptor;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.Action;

import nc.ui.pu.uif2.PUBillManageModel;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.actions.ActionInterceptor;
import nc.vo.pu.m21.entity.OrderVO;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

/**
 * 删除按钮拦截器，删除前校验当前删除档案是否被引用
 * 
 * @author shidalin
 * 
 */
public class InterceptorForUnApproveAction implements ActionInterceptor {

	protected PUBillManageModel model;

	public InterceptorForUnApproveAction() {
	}

	@Override
	public boolean afterDoActionFailed(Action arg0, ActionEvent arg1,
			Throwable arg2) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void afterDoActionSuccessed(Action arg0, ActionEvent arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean beforeDoAction(Action arg0, ActionEvent arg1) {
		// TODO Auto-generated method stub
		Object[] selectedOperaDatas = model.getSelectedOperaDatas();
		if (selectedOperaDatas == null || selectedOperaDatas.length == 0) {
			ExceptionUtils.wrappBusinessException("请选择有效操作数据");
			ShowStatusBarMsgUtil.showErrorMsg("操作失败", "请选择有效操作数据",
					model.getContext());
			return false;
		}
		ArrayList<OrderVO> arrayList = new ArrayList<OrderVO>();
		for (Object selectData : selectedOperaDatas) {
			OrderVO vo = (OrderVO) selectData;
			if (!"Y".equals(vo.getHVO().getVdef20())) {
				continue;
			} else {
				// 调用UAP接口进行查询
				arrayList.add(vo);
			}
		}
		if (arrayList.size() > 0) {
			StringBuffer stringBuffer = new StringBuffer();
			for (OrderVO vo : arrayList) {
				stringBuffer.append(vo.getHVO().getVbillcode());
			}
			String message = "已传送WMS订单不可弃审，错误订单编码：" + stringBuffer.toString();
			ShowStatusBarMsgUtil.showErrorMsg("弃审失败", message,
					model.getContext());
			return false;
		}
		return true;
	}

	public PUBillManageModel getModel() {
		return model;
	}

	public void setModel(PUBillManageModel model) {
		this.model = model;
	}

}
