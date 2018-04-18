package nc.bs.gl.pfxx;

import java.io.File;
import java.util.Collection;

import nc.bs.dao.BaseDAO;
import nc.bs.framework.common.NCLocator;
import nc.bs.framework.core.util.ObjectCreator;
import nc.bs.logging.Logger;
import nc.itf.businessevent.IEventListenerQryService;
import nc.itf.gl.voucher.pfxx.IVoucherPfxxService;
import nc.itf.gl.voucher.pfxx.VoucherPfxxLogVO;
import nc.pubitf.fip.pfxxsysconf.IPfxxSysConfQueryService;
import nc.vo.bd.pub.IPubEnumConst;
import nc.vo.fip.pfxxsysconf.PfxxSysConfOrgVO;
import nc.vo.fip.pfxxsysconf.PfxxSysConfVO;
import nc.vo.gl.event.VoucherBSEvent;
import nc.vo.gl.pubvoucher.OperationResultVO;
import nc.vo.gl.pubvoucher.VoucherKey;
import nc.vo.gl.pubvoucher.VoucherVO;
import nc.vo.gl.vouchertools.XML_VoucherTranslator;
import nc.vo.jcom.xml.XMLUtil;
import nc.vo.pfxx.pub.PostFile;
import nc.vo.pfxx.pub.SendResult;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.uap.businessevent.EventListenerVO;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class VoucherPfxxServiceImpl implements IVoucherPfxxService {

	@Override
	public OperationResultVO[] saveVoucher(VoucherVO voucherVo)
			throws BusinessException {

		if (voucherVo == null)
			return null;
		PfxxSysConfVO[] sysConfVOs = NCLocator
				.getInstance()
				.lookup(IPfxxSysConfQueryService.class)
				.queryPfxxSysConfVOs(
						PfxxSysConfVO.ENABLESTATE + "='"
								+ IPubEnumConst.ENABLESTATE_ENABLE + "'");

		if (sysConfVOs == null || sysConfVOs.length == 0)
			return null;

		PfxxSysConfVO desSysVo = null;

		for (PfxxSysConfVO sysConfVo : sysConfVOs) {
			PfxxSysConfOrgVO[] confOrgVOs = sysConfVo.getPfxxsysconforg();
			if (confOrgVOs == null || confOrgVOs.length == 0)
				continue;
			for (PfxxSysConfOrgVO confOrgVo : confOrgVOs) {
				String pk_financeorg = confOrgVo.getPk_financeorg();
				if (pk_financeorg.equals(voucherVo.getPk_org())) {
					desSysVo = sysConfVo;
					break;
				}
			}
			if (desSysVo != null)
				break;
		}

		if (desSysVo == null)
			throw new BusinessException("无法找到该组织对应的目的系统！");

		EventListenerVO[] eventListenerVOs = NCLocator
				.getInstance()
				.lookup(IEventListenerQryService.class)
				.getListenersBy(VoucherKey.MD_VOUCHER_ID,
						VoucherBSEvent.TYPE_VOUCHER_SYSTEM);
		// 没有注册外系统实现类
		if (eventListenerVOs == null || eventListenerVOs.length == 0)
			throw new BusinessException("没有注册外系统实现类！");

		EventListenerVO desListenerVo = null;
		for (EventListenerVO listenerVo : eventListenerVOs) {
			if (listenerVo.getName().equals(desSysVo.getDes_sysname())) {
				desListenerVo = listenerVo;
				break;
			}
		}

		if (desListenerVo == null) {
			throw new BusinessException("没有注册外系统实现类！");
		}

		// 在发送之前先把垃圾数据删除
		sendVoucher(desListenerVo, voucherVo, desSysVo);

		OperationResultVO r_result = new OperationResultVO();
		r_result.m_intSuccess = 0;
		r_result.m_strDescription = null;
		r_result.m_strPK = voucherVo.getPk_voucher();
		r_result.m_userIdentical = voucherVo.clone();

		return new OperationResultVO[] { r_result };
	}

	public void delVoucher(VoucherVO voucherVo) throws BusinessException {

		if (voucherVo == null)
			return;
		PfxxSysConfVO[] sysConfVOs = NCLocator
				.getInstance()
				.lookup(IPfxxSysConfQueryService.class)
				.queryPfxxSysConfVOs(
						PfxxSysConfVO.ENABLESTATE + "='"
								+ IPubEnumConst.ENABLESTATE_ENABLE + "'");

		if (sysConfVOs == null || sysConfVOs.length == 0)
			return;

		PfxxSysConfVO desSysVo = null;

		for (PfxxSysConfVO sysConfVo : sysConfVOs) {
			PfxxSysConfOrgVO[] confOrgVOs = sysConfVo.getPfxxsysconforg();
			if (confOrgVOs == null || confOrgVOs.length == 0)
				continue;
			for (PfxxSysConfOrgVO confOrgVo : confOrgVOs) {
				String pk_financeorg = confOrgVo.getPk_financeorg();
				if (pk_financeorg.equals(voucherVo.getPk_org())) {
					desSysVo = sysConfVo;
					break;
				}
			}
			if (desSysVo != null)
				break;
		}

		if (desSysVo == null)
			throw new BusinessException("无法找到该组织对应的目的系统！");

		EventListenerVO[] eventListenerVOs = NCLocator
				.getInstance()
				.lookup(IEventListenerQryService.class)
				.getListenersBy(VoucherKey.MD_VOUCHER_ID,
						VoucherBSEvent.TYPE_VOUCHER_SYSTEM);
		// 没有注册外系统实现类
		if (eventListenerVOs == null || eventListenerVOs.length == 0)
			throw new BusinessException("没有注册外系统实现类！");

		EventListenerVO desListenerVo = null;
		for (EventListenerVO listenerVo : eventListenerVOs) {
			if (listenerVo.getName().equals(desSysVo.getDes_sysname())) {
				desListenerVo = listenerVo;
				break;
			}
		}

		if (desListenerVo == null) {
			throw new BusinessException("没有注册外系统实现类！");
		}

		// 在发送之前先把垃圾数据删除
		sendDelVoucher(desListenerVo, voucherVo, desSysVo);

		OperationResultVO r_result = new OperationResultVO();
		r_result.m_intSuccess = 0;
		r_result.m_strDescription = null;
		r_result.m_strPK = voucherVo.getPk_voucher();
		r_result.m_userIdentical = voucherVo.clone();

	}

	private void sendDelVoucher(EventListenerVO linstenerVo,
			VoucherVO voucherVo, PfxxSysConfVO desSysVo)
			throws BusinessException {

		String className = linstenerVo.getImplclassname();

		String systemName = linstenerVo.getName();

		IVoucherPfxxSysInfo sysInfo = createInstance(className);

		Document document = sysInfo.getDelDocumentByVoucherVOs(
				new VoucherVO[] { voucherVo }, desSysVo);

		String xmlBackFilePath = sysInfo.getXmlBackFilePath();

		String xmlFilePath = sysInfo
				.getXmlFilePath(new VoucherVO[] { voucherVo });

		// 写文件
		StringBuffer strBuff = new StringBuffer();
		XML_VoucherTranslator.writeXMLFormatString(strBuff, document, -2);
		try {
			XML_VoucherTranslator.saveToFile(xmlFilePath, strBuff);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e);
		}

		String url = getURL(desSysVo);

		VoucherPfxxLogVO pfxxLogVo = new VoucherPfxxLogVO();
		pfxxLogVo.setPk_org(voucherVo.getPk_org());
		pfxxLogVo.setSystemname(systemName);
		pfxxLogVo.setPk_group(voucherVo.getPk_group());

		// TODO 需要将单据主键传过来
		pfxxLogVo.setBillid(voucherVo.getFree8());

		SendResult sendToPfxx = null;
		// 发送到外部交换平台
		try {
			sendToPfxx = sendToPfxx(new File(xmlFilePath), url,
					xmlBackFilePath, null, false);
			// 处理回执
			dealWithRt(new VoucherVO[] { voucherVo }, sendToPfxx, systemName);
			// pfxxLogVo = NCLocator
			// .getInstance()
			// .lookup(IVoucherPfxxService.class)
			// .savePfxxLogs_RequiresNew(
			// new VoucherPfxxLogVO[] { pfxxLogVo })[0];
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}

		/**
		 * 删除日志，如果后续单据抛异常导致日志删除不成功说明有其他系统中会多凭证， 在后续操作中需要将多余数据删除掉
		 */
		// this.delPfxxLogs(new VoucherPfxxLogVO[] { pfxxLogVo });
	}

	private void sendVoucher(EventListenerVO linstenerVo, VoucherVO voucherVo,
			PfxxSysConfVO desSysVo) throws BusinessException {
		String className = linstenerVo.getImplclassname();

		String systemName = linstenerVo.getName();

		IVoucherPfxxSysInfo sysInfo = createInstance(className);

		Document document = sysInfo.getDocumentByVoucherVOs(
				new VoucherVO[] { voucherVo }, desSysVo);

		String xmlBackFilePath = sysInfo.getXmlBackFilePath();

		String xmlFilePath = sysInfo
				.getXmlFilePath(new VoucherVO[] { voucherVo });

		// 写文件
		StringBuffer strBuff = new StringBuffer();
		XML_VoucherTranslator.writeXMLFormatString(strBuff, document, -2);
		try {
			XML_VoucherTranslator.saveToFile(xmlFilePath, strBuff);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e);
		}

		String url = getURL(desSysVo);

		VoucherPfxxLogVO pfxxLogVo = new VoucherPfxxLogVO();
		pfxxLogVo.setPk_org(voucherVo.getPk_org());
		pfxxLogVo.setSystemname(systemName);
		pfxxLogVo.setPk_group(voucherVo.getPk_group());

		// TODO 需要将单据主键传过来
		pfxxLogVo.setBillid(voucherVo.getFree8());

		SendResult sendToPfxx = null;
		// 发送到外部交换平台
		try {
			sendToPfxx = sendToPfxx(new File(xmlFilePath), url,
					xmlBackFilePath, null, false);
			// 处理回执
			dealWithRt(new VoucherVO[] { voucherVo }, sendToPfxx, systemName);
			// pfxxLogVo = NCLocator
			// .getInstance()
			// .lookup(IVoucherPfxxService.class)
			// .savePfxxLogs_RequiresNew(
			// new VoucherPfxxLogVO[] { pfxxLogVo })[0];
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}

		/**
		 * 删除日志，如果后续单据抛异常导致日志删除不成功说明有其他系统中会多凭证， 在后续操作中需要将多余数据删除掉
		 */
		// this.delPfxxLogs(new VoucherPfxxLogVO[] { pfxxLogVo });

	}

	/**
	 * 发送到pfxx
	 * 
	 * @param file
	 * @param url
	 * @param backdir
	 * @param movedir
	 * @param bcompress
	 * @return
	 * @throws BusinessException
	 */
	private SendResult sendToPfxx(File file, String url, String backdir,
			String movedir, boolean bcompress) throws BusinessException {
		SendResult sendResult = PostFile.sendFileWithResults(file, url,
				backdir, movedir, bcompress, null);
		return sendResult;

	}

	/**
	 * 处理回执信息，记录日志
	 * 
	 * @param voucherVOs
	 * @param sendToPfxx
	 * @throws BusinessException
	 */
	private void dealWithRt(VoucherVO[] voucherVOs, SendResult sendToPfxx,
			String systemName) throws BusinessException {

		if (voucherVOs != null && voucherVOs.length > 0 && sendToPfxx != null) {

			Element root = sendToPfxx.getBackDoc().getDocumentElement();

			// 成功
			if ("Y".equalsIgnoreCase(root.getAttribute("successful"))) {
				// addSuccLogs(voucherVOs, qryVo);
			} else {// 失败
				throw new BusinessException(getFailLogMeg(sendToPfxx,
						systemName));
				// addFailLogs(voucherVOs, qryVo, sendToPfxx,filePath);
			}
		}
	}

	/**
	 * 记录错误凭证日志
	 * 
	 * @param voucherVOs
	 * @param qryVo
	 * @param sendToPfxx
	 * @throws BusinessException
	 */
	private String getFailLogMeg(SendResult sendToPfxx, String systemName)
			throws BusinessException {

		StringBuilder log = new StringBuilder();
		Document backDoc = sendToPfxx.getBackDoc();
		if (backDoc != null) {
			// 单据根节点
			NodeList sendresultNodeList = backDoc
					.getElementsByTagName("sendresult");

			if (sendresultNodeList != null
					&& sendresultNodeList.getLength() > 0) {
				for (int i = 0; i < sendresultNodeList.getLength(); i++) {

					Node sendresultNode = sendresultNodeList.item(i);
					Node resultDesc = XMLUtil.getChildNodeOf(sendresultNode,
							"resultdescription");
					if (resultDesc != null) {
						String valueOf = XMLUtil.getValueOf(resultDesc);
						log.append(valueOf);
					}
				}
			}
		}
		log.append(" 目标系统：" + systemName);
		return log.toString();
	}

	private String getURL(PfxxSysConfVO desSysVo) throws BusinessException {

		return desSysVo.getDes_url();

	}

	private IVoucherPfxxSysInfo createInstance(String className) {

		IVoucherPfxxSysInfo instance = null;
		try {
			instance = (IVoucherPfxxSysInfo) ObjectCreator
					.newInstance(className);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessRuntimeException(
					"cann't create instance. ClassName: "
							+ className
							+ ". Please check register info in table pub_eventlistener");
		}
		return instance;
	}

	@Override
	public VoucherPfxxLogVO[] savePfxxLogs_RequiresNew(VoucherPfxxLogVO[] logVOs)
			throws BusinessException {
		try {
			if (logVOs != null && logVOs.length > 0) {
				String[] pks;
				pks = new VoucherpfxxlogBO().insertArray(logVOs);
				for (int i = 0; i < logVOs.length; i++) {
					logVOs[i].setPrimaryKey(pks[i]);
				}
			}
			return logVOs;

		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}

	@Override
	public void delPfxxLogs(VoucherPfxxLogVO[] logVOs) throws BusinessException {

		try {
			if (logVOs != null && logVOs.length > 0) {
				new VoucherpfxxlogBO().deleteArray(logVOs);
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e);
		}

	}

	@Override
	public VoucherPfxxLogVO[] getVoucherPfxxLogVOsBySysName(String systemName)
			throws BusinessException {
		if (StringUtils.isEmpty(systemName))
			return null;

		Collection<VoucherPfxxLogVO> c = new BaseDAO().retrieveByClause(
				VoucherPfxxLogVO.class, VoucherPfxxLogVO.SYSTEMNAME + "='"
						+ systemName + "'");
		if (c != null && c.size() > 0) {
			return c.toArray(new VoucherPfxxLogVO[0]);
		}
		return null;
	}

	@Override
	public boolean isSendOtherSys(String pk_org) throws BusinessException {
		String condition = PfxxSysConfVO.ENABLESTATE + "='"
				+ IPubEnumConst.ENABLESTATE_ENABLE + "'";
		PfxxSysConfVO[] sysConfVOs = NCLocator.getInstance()
				.lookup(IPfxxSysConfQueryService.class)
				.queryPfxxSysConfVOs(condition);
		if (sysConfVOs != null && sysConfVOs.length > 0) {
			for (PfxxSysConfVO confVO : sysConfVOs) {
				if (confVO.getPfxxsysconforg() != null) {
					for (PfxxSysConfOrgVO pfxxSysConfOrgVO : confVO
							.getPfxxsysconforg()) {
						if (pfxxSysConfOrgVO.getPk_financeorg().equals(pk_org))
							return true;
					}
				}
			}
		}
		return false;
	}

}
