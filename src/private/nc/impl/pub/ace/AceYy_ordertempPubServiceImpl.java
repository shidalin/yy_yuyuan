package nc.impl.pub.ace;

import nc.bs.yuyuan.yy_ordertemp.ace.bp.AceYy_ordertempApproveBP;
import nc.bs.yuyuan.yy_ordertemp.ace.bp.AceYy_ordertempDeleteBP;
import nc.bs.yuyuan.yy_ordertemp.ace.bp.AceYy_ordertempInsertBP;
import nc.bs.yuyuan.yy_ordertemp.ace.bp.AceYy_ordertempSendApproveBP;
import nc.bs.yuyuan.yy_ordertemp.ace.bp.AceYy_ordertempUnApproveBP;
import nc.bs.yuyuan.yy_ordertemp.ace.bp.AceYy_ordertempUnSendApproveBP;
import nc.bs.yuyuan.yy_ordertemp.ace.bp.AceYy_ordertempUpdateBP;
import nc.impl.pubapp.pattern.data.bill.BillLazyQuery;
import nc.impl.pubapp.pattern.data.bill.tool.BillTransferTool;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;

public abstract class AceYy_ordertempPubServiceImpl {
	// 新增
	public AggYyOrderTempVO[] pubinsertBills(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		try {
			// 数据库中数据和前台传递过来的差异VO合并后的结果
			BillTransferTool<AggYyOrderTempVO> transferTool = new BillTransferTool<AggYyOrderTempVO>(
					clientFullVOs);
			// 调用BP
			AceYy_ordertempInsertBP action = new AceYy_ordertempInsertBP();
			AggYyOrderTempVO[] retvos = action.insert(clientFullVOs);
			// 构造返回数据
			return transferTool.getBillForToClient(retvos);
		} catch (Exception e) {
			//ExceptionUtils.marsh(e);
			ExceptionUtils.wrappException(e);
		}
		return null;
	}

	// 删除
	public void pubdeleteBills(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		try {
			// 调用BP
			new AceYy_ordertempDeleteBP().delete(clientFullVOs);
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
	}

	// 修改
	public AggYyOrderTempVO[] pubupdateBills(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		try {
			// 加锁 + 检查ts
			BillTransferTool<AggYyOrderTempVO> transferTool = new BillTransferTool<AggYyOrderTempVO>(
					clientFullVOs);
			AceYy_ordertempUpdateBP bp = new AceYy_ordertempUpdateBP();
			AggYyOrderTempVO[] retvos = bp.update(clientFullVOs, originBills);
			// 构造返回数据
			return transferTool.getBillForToClient(retvos);
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
		return null;
	}

	public AggYyOrderTempVO[] pubquerybills(IQueryScheme queryScheme)
			throws BusinessException {
		AggYyOrderTempVO[] bills = null;
		try {
			this.preQuery(queryScheme);
			BillLazyQuery<AggYyOrderTempVO> query = new BillLazyQuery<AggYyOrderTempVO>(
					AggYyOrderTempVO.class);
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
	public AggYyOrderTempVO[] pubsendapprovebills(
			AggYyOrderTempVO[] clientFullVOs, AggYyOrderTempVO[] originBills)
			throws BusinessException {
		AceYy_ordertempSendApproveBP bp = new AceYy_ordertempSendApproveBP();
		AggYyOrderTempVO[] retvos = bp.sendApprove(clientFullVOs, originBills);
		return retvos;
	}

	// 收回
	public AggYyOrderTempVO[] pubunsendapprovebills(
			AggYyOrderTempVO[] clientFullVOs, AggYyOrderTempVO[] originBills)
			throws BusinessException {
		AceYy_ordertempUnSendApproveBP bp = new AceYy_ordertempUnSendApproveBP();
		AggYyOrderTempVO[] retvos = bp.unSend(clientFullVOs, originBills);
		return retvos;
	};

	// 审批
	public AggYyOrderTempVO[] pubapprovebills(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		for (int i = 0; clientFullVOs != null && i < clientFullVOs.length; i++) {
			clientFullVOs[i].getParentVO().setStatus(VOStatus.UPDATED);
		}
		AceYy_ordertempApproveBP bp = new AceYy_ordertempApproveBP();
		if(clientFullVOs==null||clientFullVOs.length==0||originBills==null||originBills.length==0){
			ExceptionUtils.wrappBusinessException("审批失败，方法参数不能为空!");
		}
		AggYyOrderTempVO[] retvos = bp.approve(clientFullVOs, originBills);
		return retvos;
	}

	// 弃审

	public AggYyOrderTempVO[] pubunapprovebills(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		for (int i = 0; clientFullVOs != null && i < clientFullVOs.length; i++) {
			clientFullVOs[i].getParentVO().setStatus(VOStatus.UPDATED);
		}
		AceYy_ordertempUnApproveBP bp = new AceYy_ordertempUnApproveBP();
		if(clientFullVOs==null||clientFullVOs.length==0||originBills==null||originBills.length==0){
			ExceptionUtils.wrappBusinessException("弃审失败，方法参数不能为空!");
		}
		AggYyOrderTempVO[] retvos = bp.unApprove(clientFullVOs, originBills);
		return retvos;
	}

}