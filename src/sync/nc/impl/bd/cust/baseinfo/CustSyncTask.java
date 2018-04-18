package nc.impl.bd.cust.baseinfo;

import nc.bs.framework.common.NCLocator;
import nc.bs.pub.pa.PreAlertObject;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.bs.pub.taskcenter.IBackgroundWorkPlugin;
import nc.vo.pub.BusinessException;

/**
 * 客商同步后台任务
 * 
 * @author shidl
 * 
 */
public class CustSyncTask implements IBackgroundWorkPlugin {

	public CustSyncTask() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public PreAlertObject executeTask(BgWorkingContext arg0)
			throws BusinessException {
		// TODO Auto-generated method stub
		NCLocator.getInstance()
				.lookup(nc.itf.bd.cust.baseinfo.ICustSupplierService.class)
				.syncCustProcess();
		return null;
	}

}
