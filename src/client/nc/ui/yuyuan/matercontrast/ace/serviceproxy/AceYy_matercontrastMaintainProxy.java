package nc.ui.yuyuan.matercontrast.ace.serviceproxy;

import nc.bs.framework.common.NCLocator;
import nc.ui.pubapp.pub.task.ISingleBillService;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.model.entity.bill.IBill;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.ui.pubapp.uif2app.actions.IDataOperationService;
import nc.ui.pubapp.uif2app.query2.model.IQueryService;
import nc.ui.uif2.components.pagination.IPaginationQueryService;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.yy_matercontrast.AggYyMaterContrastVO;
import nc.itf.yuyuan.IYy_matercontrastMaintain;

/**
 * 示例单据的操作代理
 * 
 * @author author
 * @version tempProject version
 */
public class AceYy_matercontrastMaintainProxy implements IDataOperationService,
		IQueryService ,ISingleBillService<AggYyMaterContrastVO>{
	@Override
	public IBill[] insert(IBill[] value) throws BusinessException {
		IYy_matercontrastMaintain operator = NCLocator.getInstance().lookup(
				IYy_matercontrastMaintain.class);
		AggYyMaterContrastVO[] vos = operator.insert((AggYyMaterContrastVO[]) value);
		return vos;
	}

	@Override
	public IBill[] update(IBill[] value) throws BusinessException {
		IYy_matercontrastMaintain operator = NCLocator.getInstance().lookup(
				IYy_matercontrastMaintain.class);
		AggYyMaterContrastVO[] vos = operator.update((AggYyMaterContrastVO[]) value);
		return vos;
	}

	@Override
	public IBill[] delete(IBill[] value) throws BusinessException {
		// 目前的删除并不是走这个方法，由于pubapp不支持从这个服务中执行删除操作
		// 单据的删除实际上使用的是：ISingleBillService<AggSingleBill>的operateBill
		IYy_matercontrastMaintain operator = NCLocator.getInstance().lookup(
				IYy_matercontrastMaintain.class);
		operator.delete((AggYyMaterContrastVO[]) value);
		return value;
	}
	
	@Override
	public AggYyMaterContrastVO operateBill(AggYyMaterContrastVO bill) throws Exception {
		IYy_matercontrastMaintain operator = NCLocator.getInstance().lookup(
				IYy_matercontrastMaintain.class);
		operator.delete(new AggYyMaterContrastVO[] { bill });
		return bill;
	}

	@Override
	public Object[] queryByQueryScheme(IQueryScheme queryScheme)
			throws Exception {
		IYy_matercontrastMaintain query = NCLocator.getInstance().lookup(
				IYy_matercontrastMaintain.class);
		return query.query(queryScheme);
	}

}
