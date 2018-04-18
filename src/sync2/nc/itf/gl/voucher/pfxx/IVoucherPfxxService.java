package nc.itf.gl.voucher.pfxx;

import nc.itf.gl.voucher.pfxx.VoucherPfxxLogVO;
import nc.vo.fip.pfxxsysconf.PfxxSysConfVO;
import nc.vo.gl.pubvoucher.OperationResultVO;
import nc.vo.gl.pubvoucher.VoucherVO;
import nc.vo.pub.BusinessException;
import nc.vo.uap.businessevent.EventListenerVO;

public interface IVoucherPfxxService {
	OperationResultVO[] saveVoucher(VoucherVO arg0) throws BusinessException;


	VoucherPfxxLogVO[] savePfxxLogs_RequiresNew(VoucherPfxxLogVO[] arg0)
			throws BusinessException;

	void delPfxxLogs(VoucherPfxxLogVO[] arg0) throws BusinessException;

	VoucherPfxxLogVO[] getVoucherPfxxLogVOsBySysName(String arg0)
			throws BusinessException;

	boolean isSendOtherSys(String arg0) throws BusinessException;

	public void delVoucher(VoucherVO voucherVo) throws BusinessException;
}