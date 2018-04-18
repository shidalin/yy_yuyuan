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
	// 新增
	public AggYyMaterContrastVO[] pubinsertBills(AggYyMaterContrastVO[] vos)
			throws BusinessException {
//		try {
			// 数据库中数据和前台传递过来的差异VO合并后的结果
			BillTransferTool<AggYyMaterContrastVO> transferTool = new BillTransferTool<AggYyMaterContrastVO>(
					vos);
			AggYyMaterContrastVO[] mergedVO = transferTool.getClientFullInfoBill();

			// 调用BP
			AceYy_matercontrastInsertBP action = new AceYy_matercontrastInsertBP();
			AggYyMaterContrastVO[] retvos = action.insert(mergedVO);
			// 构造返回数据
			return transferTool.getBillForToClient(retvos);
//		} catch (Exception e) {
//			//ExceptionUtils.marsh(e);
//			ExceptionUtils.wrappBusinessException(e.getMessage());
//		}
//		return null;
	}

	// 删除
	public void pubdeleteBills(AggYyMaterContrastVO[] vos) throws BusinessException {
		try {
			// 加锁 比较ts
			BillTransferTool<AggYyMaterContrastVO> transferTool = new BillTransferTool<AggYyMaterContrastVO>(
					vos);
			AggYyMaterContrastVO[] fullBills = transferTool.getClientFullInfoBill();
			AceYy_matercontrastDeleteBP deleteBP = new AceYy_matercontrastDeleteBP();
			deleteBP.delete(fullBills);
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
	}

	// 修改
	public AggYyMaterContrastVO[] pubupdateBills(AggYyMaterContrastVO[] vos)
			throws BusinessException {
		try {
			// 加锁 + 检查ts
			BillTransferTool<AggYyMaterContrastVO> transTool = new BillTransferTool<AggYyMaterContrastVO>(
					vos);
			// 补全前台VO
			AggYyMaterContrastVO[] fullBills = transTool.getClientFullInfoBill();
			// 获得修改前vo
			AggYyMaterContrastVO[] originBills = transTool.getOriginBills();
			// 调用BP
			AceYy_matercontrastUpdateBP bp = new AceYy_matercontrastUpdateBP();
			AggYyMaterContrastVO[] retBills = bp.update(fullBills, originBills);
			// 构造返回数据
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
	 * 由子类实现，查询之前对queryScheme进行加工，加入自己的逻辑
	 * 
	 * @param queryScheme
	 */
	protected void preQuery(IQueryScheme queryScheme) {
		// 查询之前对queryScheme进行加工，加入自己的逻辑
	}

}