package nc.impl.pub.ace;

import nc.bs.yuyuan.order.ace.bp.AceYy_orderInsertBP;
import nc.bs.yuyuan.order.ace.bp.AceYy_orderUpdateBP;
import nc.bs.yuyuan.order.ace.bp.AceYy_orderDeleteBP;
import nc.bs.yuyuan.order.ace.bp.AceYy_orderSendApproveBP;
import nc.bs.yuyuan.order.ace.bp.AceYy_orderUnSendApproveBP;
import nc.bs.yuyuan.order.ace.bp.AceYy_orderApproveBP;
import nc.bs.yuyuan.order.ace.bp.AceYy_orderUnApproveBP;
import nc.impl.pubapp.pattern.data.bill.BillLazyQuery;
import nc.impl.pubapp.pattern.data.bill.tool.BillTransferTool;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

public abstract class AceYy_orderPubServiceImpl {
	// 新增
	public AggYyOrderVO[] pubinsertBills(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		try {
			// 数据库中数据和前台传递过来的差异VO合并后的结果
			BillTransferTool<AggYyOrderVO> transferTool = new BillTransferTool<AggYyOrderVO>(
					clientFullVOs);
			// 调用BP
			AceYy_orderInsertBP action = new AceYy_orderInsertBP();
			AggYyOrderVO[] retvos = action.insert(clientFullVOs);
			// 构造返回数据
			return transferTool.getBillForToClient(retvos);
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
		return null;
	}

	// 删除
	public void pubdeleteBills(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		try {
			// 调用BP
			new AceYy_orderDeleteBP().delete(clientFullVOs);
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
	}

	// 修改
	public AggYyOrderVO[] pubupdateBills(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		try {
			// 加锁 + 检查ts
			BillTransferTool<AggYyOrderVO> transferTool = new BillTransferTool<AggYyOrderVO>(
					clientFullVOs);
			AceYy_orderUpdateBP bp = new AceYy_orderUpdateBP();
			AggYyOrderVO[] retvos = bp.update(clientFullVOs, originBills);
			// 构造返回数据
			return transferTool.getBillForToClient(retvos);
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
		return null;
	}

	public AggYyOrderVO[] pubquerybills(IQueryScheme queryScheme)
			throws BusinessException {
		AggYyOrderVO[] bills = null;
		try {
			this.preQuery(queryScheme);
			BillLazyQuery<AggYyOrderVO> query = new BillLazyQuery<AggYyOrderVO>(
					AggYyOrderVO.class);
			bills = query.query(queryScheme, null);
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
		return bills;
	}

	/**
	 * 由子类实现，查询之前对queryScheme进行加工，加入自己的逻辑
	 * 
	 * @param queryScheme
	 */
	protected void preQuery(IQueryScheme queryScheme) {
		// 查询之前对queryScheme进行加工，加入自己的逻辑
	}

	// 提交
	public AggYyOrderVO[] pubsendapprovebills(
			AggYyOrderVO[] clientFullVOs, AggYyOrderVO[] originBills)
			throws BusinessException {
		AceYy_orderSendApproveBP bp = new AceYy_orderSendApproveBP();
		AggYyOrderVO[] retvos = bp.sendApprove(clientFullVOs, originBills);
		return retvos;
	}

	// 收回
	public AggYyOrderVO[] pubunsendapprovebills(
			AggYyOrderVO[] clientFullVOs, AggYyOrderVO[] originBills)
			throws BusinessException {
		AceYy_orderUnSendApproveBP bp = new AceYy_orderUnSendApproveBP();
		AggYyOrderVO[] retvos = bp.unSend(clientFullVOs, originBills);
		return retvos;
	};

	// 审批
	public AggYyOrderVO[] pubapprovebills(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		for (int i = 0; clientFullVOs != null && i < clientFullVOs.length; i++) {
			clientFullVOs[i].getParentVO().setStatus(VOStatus.UPDATED);
		}
		AceYy_orderApproveBP bp = new AceYy_orderApproveBP();
		if(clientFullVOs==null||clientFullVOs.length==0||originBills==null||originBills.length==0){
			ExceptionUtils.wrappBusinessException("审批失败，方法参数不能为空!");
		}
		AggYyOrderVO[] retvos = bp.approve(clientFullVOs, originBills);
		return retvos;
	}

	// 弃审

	public AggYyOrderVO[] pubunapprovebills(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		for (int i = 0; clientFullVOs != null && i < clientFullVOs.length; i++) {
			clientFullVOs[i].getParentVO().setStatus(VOStatus.UPDATED);
		}
		AceYy_orderUnApproveBP bp = new AceYy_orderUnApproveBP();
		if(clientFullVOs==null||clientFullVOs.length==0||originBills==null||originBills.length==0){
			ExceptionUtils.wrappBusinessException("弃审失败，方法参数不能为空!");
		}
		AggYyOrderVO[] retvos = bp.unApprove(clientFullVOs, originBills);
		return retvos;
	}

}