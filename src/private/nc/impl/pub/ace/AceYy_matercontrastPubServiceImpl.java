package nc.impl.pub.ace;

import nc.bs.yuyuan.matercontrast.ace.bp.AceYy_matercontrastInsertBP;
import nc.bs.yuyuan.matercontrast.ace.bp.AceYy_matercontrastUpdateBP;
import nc.bs.yuyuan.matercontrast.ace.bp.AceYy_matercontrastDeleteBP;
import nc.impl.pubapp.pattern.data.bill.BillLazyQuery;
import nc.impl.pubapp.pattern.data.bill.tool.BillTransferTool;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.yy_matercontrast.AggYyMaterContrastVO;

public abstract class AceYy_matercontrastPubServiceImpl {
	// ����
	public AggYyMaterContrastVO[] pubinsertBills(AggYyMaterContrastVO[] vos)
			throws BusinessException {
//		try {
			// ���ݿ������ݺ�ǰ̨���ݹ����Ĳ���VO�ϲ���Ľ��
			BillTransferTool<AggYyMaterContrastVO> transferTool = new BillTransferTool<AggYyMaterContrastVO>(
					vos);
			AggYyMaterContrastVO[] mergedVO = transferTool.getClientFullInfoBill();

			// ����BP
			AceYy_matercontrastInsertBP action = new AceYy_matercontrastInsertBP();
			AggYyMaterContrastVO[] retvos = action.insert(mergedVO);
			// ���췵������
			return transferTool.getBillForToClient(retvos);
//		} catch (Exception e) {
//			//ExceptionUtils.marsh(e);
//			ExceptionUtils.wrappBusinessException(e.getMessage());
//		}
//		return null;
	}

	// ɾ��
	public void pubdeleteBills(AggYyMaterContrastVO[] vos) throws BusinessException {
		try {
			// ���� �Ƚ�ts
			BillTransferTool<AggYyMaterContrastVO> transferTool = new BillTransferTool<AggYyMaterContrastVO>(
					vos);
			AggYyMaterContrastVO[] fullBills = transferTool.getClientFullInfoBill();
			AceYy_matercontrastDeleteBP deleteBP = new AceYy_matercontrastDeleteBP();
			deleteBP.delete(fullBills);
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
	}

	// �޸�
	public AggYyMaterContrastVO[] pubupdateBills(AggYyMaterContrastVO[] vos)
			throws BusinessException {
		try {
			// ���� + ���ts
			BillTransferTool<AggYyMaterContrastVO> transTool = new BillTransferTool<AggYyMaterContrastVO>(
					vos);
			// ��ȫǰ̨VO
			AggYyMaterContrastVO[] fullBills = transTool.getClientFullInfoBill();
			// ����޸�ǰvo
			AggYyMaterContrastVO[] originBills = transTool.getOriginBills();
			// ����BP
			AceYy_matercontrastUpdateBP bp = new AceYy_matercontrastUpdateBP();
			AggYyMaterContrastVO[] retBills = bp.update(fullBills, originBills);
			// ���췵������
			retBills = transTool.getBillForToClient(retBills);
			return retBills;
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
		return null;
	}

	public AggYyMaterContrastVO[] pubquerybills(IQueryScheme queryScheme)
			throws BusinessException {
		AggYyMaterContrastVO[] bills = null;
		try {
			this.preQuery(queryScheme);
			BillLazyQuery<AggYyMaterContrastVO> query = new BillLazyQuery<AggYyMaterContrastVO>(
					AggYyMaterContrastVO.class);
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

}