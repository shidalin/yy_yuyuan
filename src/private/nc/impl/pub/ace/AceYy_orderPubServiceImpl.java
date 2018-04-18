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
	// ����
	public AggYyOrderVO[] pubinsertBills(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		try {
			// ���ݿ������ݺ�ǰ̨���ݹ����Ĳ���VO�ϲ���Ľ��
			BillTransferTool<AggYyOrderVO> transferTool = new BillTransferTool<AggYyOrderVO>(
					clientFullVOs);
			// ����BP
			AceYy_orderInsertBP action = new AceYy_orderInsertBP();
			AggYyOrderVO[] retvos = action.insert(clientFullVOs);
			// ���췵������
			return transferTool.getBillForToClient(retvos);
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
		return null;
	}

	// ɾ��
	public void pubdeleteBills(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		try {
			// ����BP
			new AceYy_orderDeleteBP().delete(clientFullVOs);
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
	}

	// �޸�
	public AggYyOrderVO[] pubupdateBills(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		try {
			// ���� + ���ts
			BillTransferTool<AggYyOrderVO> transferTool = new BillTransferTool<AggYyOrderVO>(
					clientFullVOs);
			AceYy_orderUpdateBP bp = new AceYy_orderUpdateBP();
			AggYyOrderVO[] retvos = bp.update(clientFullVOs, originBills);
			// ���췵������
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
	 * ������ʵ�֣���ѯ֮ǰ��queryScheme���мӹ��������Լ����߼�
	 * 
	 * @param queryScheme
	 */
	protected void preQuery(IQueryScheme queryScheme) {
		// ��ѯ֮ǰ��queryScheme���мӹ��������Լ����߼�
	}

	// �ύ
	public AggYyOrderVO[] pubsendapprovebills(
			AggYyOrderVO[] clientFullVOs, AggYyOrderVO[] originBills)
			throws BusinessException {
		AceYy_orderSendApproveBP bp = new AceYy_orderSendApproveBP();
		AggYyOrderVO[] retvos = bp.sendApprove(clientFullVOs, originBills);
		return retvos;
	}

	// �ջ�
	public AggYyOrderVO[] pubunsendapprovebills(
			AggYyOrderVO[] clientFullVOs, AggYyOrderVO[] originBills)
			throws BusinessException {
		AceYy_orderUnSendApproveBP bp = new AceYy_orderUnSendApproveBP();
		AggYyOrderVO[] retvos = bp.unSend(clientFullVOs, originBills);
		return retvos;
	};

	// ����
	public AggYyOrderVO[] pubapprovebills(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		for (int i = 0; clientFullVOs != null && i < clientFullVOs.length; i++) {
			clientFullVOs[i].getParentVO().setStatus(VOStatus.UPDATED);
		}
		AceYy_orderApproveBP bp = new AceYy_orderApproveBP();
		if(clientFullVOs==null||clientFullVOs.length==0||originBills==null||originBills.length==0){
			ExceptionUtils.wrappBusinessException("����ʧ�ܣ�������������Ϊ��!");
		}
		AggYyOrderVO[] retvos = bp.approve(clientFullVOs, originBills);
		return retvos;
	}

	// ����

	public AggYyOrderVO[] pubunapprovebills(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		for (int i = 0; clientFullVOs != null && i < clientFullVOs.length; i++) {
			clientFullVOs[i].getParentVO().setStatus(VOStatus.UPDATED);
		}
		AceYy_orderUnApproveBP bp = new AceYy_orderUnApproveBP();
		if(clientFullVOs==null||clientFullVOs.length==0||originBills==null||originBills.length==0){
			ExceptionUtils.wrappBusinessException("����ʧ�ܣ�������������Ϊ��!");
		}
		AggYyOrderVO[] retvos = bp.unApprove(clientFullVOs, originBills);
		return retvos;
	}

}