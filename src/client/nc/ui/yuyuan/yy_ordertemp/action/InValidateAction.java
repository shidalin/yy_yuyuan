package nc.ui.yuyuan.yy_ordertemp.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import nc.bs.framework.common.NCLocator;
import nc.ui.ml.NCLangRes;
import nc.ui.uif2.IShowMsgConstant;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;
import nc.ui.pubapp.uif2app.query2.model.ModelDataManager;

/**
 * 失效按钮
 * 
 * @author shidl
 * 
 */
public class InValidateAction extends NCAction {

	public ModelDataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(ModelDataManager dataManager) {
		this.dataManager = dataManager;
	}

	private BillManageModel model;
	private ModelDataManager dataManager;

	public InValidateAction() {
		this.setBtnName("模板失效");
		this.setCode("inValidateAction");
	}

	@Override
	public void doAction(ActionEvent e) throws Exception {
		Object[] datas = model.getSelectedOperaDatas();
		ArrayList<String> list = new ArrayList<String>();
		for (Object obj : datas) {
			AggYyOrderTempVO agg = (AggYyOrderTempVO) obj;
			list.add(agg.getParentVO().getPk_ordertemp());
		}
		int count = NCLocator.getInstance()
				.lookup(nc.itf.yuyuan.IYy_ordertempMaintain.class)
				.FProduceFlagProcess(list, "N");
		this.getDataManager().refresh();
		ShowStatusBarMsgUtil.showStatusBarMsg("模板失效操作成功,成功操作条数:" + count, this
				.getModel().getContext());
	}

	@Override
	protected boolean isActionEnable() {
		Object[] datas = model.getSelectedOperaDatas();
		if (datas != null && datas.length > 1) {
			return true;
		} else if (datas != null && datas.length == 1) {
			AggYyOrderTempVO agg = (AggYyOrderTempVO) datas[0];
			if (agg.getParentVO().getFproduceflag() != null
					&& UFBoolean.TRUE.equals(agg.getParentVO()
							.getFproduceflag())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public BillManageModel getModel() {
		return model;
	}

	public void setModel(BillManageModel model) {
		this.model = model;
		this.model.addAppEventListener(this);
	}

}
