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
	// ����
	public AggYyOrderTempVO[] pubinsertBills(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		try {
			// ���ݿ������ݺ�ǰ̨���ݹ����Ĳ���VO�ϲ���Ľ��
			BillTransferTool<AggYyOrderTempVO> transferTool = new BillTransferTool<AggYyOrderTempVO>(
					clientFullVOs);
			// ����BP
			AceYy_ordertempInsertBP action = new AceYy_ordertempInsertBP();
			AggYyOrderTempVO[] retvos = action.insert(clientFullVOs);
			// ���췵������
			return transferTool.getBillForToClient(retvos);
		} catch (Exception e) {
			//ExceptionUtils.marsh(e);
			ExceptionUtils.wrappException(e);
		}
		return null;
	}

	// ɾ��
	public void pubdeleteBills(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		try {
			// ����BP
			new AceYy_ordertempDeleteBP().delete(clientFullVOs);
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
	}

	// �޸�
	public AggYyOrderTempVO[] pubupdateBills(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		try {
			// ���� + ���ts
			BillTransferTool<AggYyOrderTempVO> transferTool = new BillTransferTool<AggYyOrderTempVO>(
					clientFullVOs);
			AceYy_ordertempUpdateBP bp = new AceYy_ordertempUpdateBP();
			AggYyOrderTempVO[] retvos = bp.update(clientFullVOs, originBills);
			// ���췵������
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
	 * ������ʵ�֣���ѯ֮ǰ��queryScheme���мӹ��������Լ����߼�
	 * 
	 * @param queryScheme
	 */
	protected void preQuery(IQueryScheme queryScheme) {
		// ��ѯ֮ǰ��queryScheme���мӹ��������Լ����߼�
	}

	// �ύ
	public AggYyOrderTempVO[] pubsendapprovebills(
			AggYyOrderTempVO[] clientFullVOs, AggYyOrderTempVO[] originBills)
			throws BusinessException {
		AceYy_ordertempSendApproveBP bp = new AceYy_ordertempSendApproveBP();
		AggYyOrderTempVO[] retvos = bp.sendApprove(clientFullVOs, originBills);
		return retvos;
	}

	// �ջ�
	public AggYyOrderTempVO[] pubunsendapprovebills(
			AggYyOrderTempVO[] clientFullVOs, AggYyOrderTempVO[] originBills)
			throws BusinessException {
		AceYy_ordertempUnSendApproveBP bp = new AceYy_ordertempUnSendApproveBP();
		AggYyOrderTempVO[] retvos = bp.unSend(clientFullVOs, originBills);
		return retvos;
	};

	// ����
	public AggYyOrderTempVO[] pubapprovebills(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		for (int i = 0; clientFullVOs != null && i < clientFullVOs.length; i++) {
			clientFullVOs[i].getParentVO().setStatus(VOStatus.UPDATED);
		}
		AceYy_ordertempApproveBP bp = new AceYy_ordertempApproveBP();
		if(clientFullVOs==null||clientFullVOs.length==0||originBills==null||originBills.length==0){
			ExceptionUtils.wrappBusinessException("����ʧ�ܣ�������������Ϊ��!");
		}
		AggYyOrderTempVO[] retvos = bp.approve(clientFullVOs, originBills);
		return retvos;
	}

	// ����

	public AggYyOrderTempVO[] pubunapprovebills(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		for (int i = 0; clientFullVOs != null && i < clientFullVOs.length; i++) {
			clientFullVOs[i].getParentVO().setStatus(VOStatus.UPDATED);
		}
		AceYy_ordertempUnApproveBP bp = new AceYy_ordertempUnApproveBP();
		if(clientFullVOs==null||clientFullVOs.length==0||originBills==null||originBills.length==0){
			ExceptionUtils.wrappBusinessException("����ʧ�ܣ�������������Ϊ��!");
		}
		AggYyOrderTempVO[] retvos = bp.unApprove(clientFullVOs, originBills);
		return retvos;
	}

}