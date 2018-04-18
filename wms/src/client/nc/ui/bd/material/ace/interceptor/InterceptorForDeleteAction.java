package nc.ui.bd.material.ace.interceptor;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.Action;

import nc.ui.bd.material.baseinfo.model.MaterialBaseInfoModel;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.actions.ActionInterceptor;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.bd.material.MaterialVO;

/**
 * 删除按钮拦截器，删除前校验当前删除档案是否被引用
 * 
 * @author shidalin
 * 
 */
public class InterceptorForDeleteAction implements ActionInterceptor {

	protected MaterialBaseInfoModel model;

	public InterceptorForDeleteAction() {
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
		ArrayList<MaterialVO> arrayList = new ArrayList<MaterialVO>();
		for (Object selectData : selectedOperaDatas) {
			MaterialVO vo = (MaterialVO) selectData;
			if (!"Y".equals(vo.getDef20())) {
				continue;
			} else {
				// 调用UAP接口进行查询
				arrayList.add(vo);
			}
		}
		if (arrayList.size() > 0) {
			StringBuffer stringBuffer = new StringBuffer();
			for (MaterialVO vo : arrayList) {
				stringBuffer.append(vo.getCode());
			}
			String message = "已传送WMS档案不可删除，错误档案编码：" + stringBuffer.toString();
			ShowStatusBarMsgUtil.showErrorMsg("删除失败", message,
					model.getContext());
			return false;
		}
		return true;
	}

	public MaterialBaseInfoModel getModel() {
		return model;
	}

	public void setModel(MaterialBaseInfoModel model) {
		this.model = model;
	}

}
