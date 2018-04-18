package nc.bs.gl.pfxx;

import nc.itf.gl.voucher.pfxx.VoucherPfxxLogVO;
import nc.vo.fip.pfxxsysconf.PfxxSysConfVO;
import nc.vo.gl.pubvoucher.VoucherVO;
import nc.vo.pub.BusinessException;
import org.w3c.dom.Document;

public interface IVoucherPfxxSysInfo {
	Document getDocumentByVoucherVOs(VoucherVO[] arg0, PfxxSysConfVO arg1)
			throws BusinessException;

	String getXmlFilePath(VoucherVO[] arg0) throws BusinessException;

	String getXmlBackFilePath() throws BusinessException;

	String getSystemName() throws BusinessException;

	Document getDelDocumentByLogVOs(VoucherPfxxLogVO[] arg0)
			throws BusinessException;
	

	String getDelXmlFilePath(VoucherVO[] arg0) throws BusinessException;

	public Document getDelDocumentByVoucherVOs(VoucherVO[] voucherVOs,
			PfxxSysConfVO desSysVo) throws BusinessException;
}