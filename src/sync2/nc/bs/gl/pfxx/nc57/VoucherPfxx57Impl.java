package nc.bs.gl.pfxx.nc57;

import java.io.File;

import javax.naming.NamingException;

import nc.bs.businessevent.IBusinessEvent;
import nc.bs.businessevent.IBusinessListener;
import nc.bs.framework.common.RuntimeEnv;
import nc.bs.gl.pfxx.IVoucherPfxxSysInfo;
import nc.bs.logging.Logger;
import nc.itf.gl.voucher.pfxx.VoucherPfxxLogVO;
import nc.vo.fip.pfxxsysconf.PfxxSysConfVO;
import nc.vo.gl.pubvoucher.VoucherVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class VoucherPfxx57Impl implements IVoucherPfxxSysInfo,
		IBusinessListener {

	public static final String VOUCHER_XML_PATH = RuntimeEnv.getInstance()
			.getNCHome() + File.separator + "voucher6" + File.separator;

	public static final String VOUCHER_XML_PATH_BAK = RuntimeEnv.getInstance()
			.getNCHome() + File.separator + "voucher_bak6" + File.separator;

	@Override
	public Document getDelDocumentByVoucherVOs(VoucherVO[] voucherVOs,
			PfxxSysConfVO desSysVo) throws BusinessException {
		org.w3c.dom.Document document = new DocumentImpl();
		org.w3c.dom.Element root = document.createElement("ufinterface");
		root.setAttribute("roottag", "voucher");
		root.setAttribute("billtype", "gl");
		root.setAttribute("docid", "989898989898");
		root.setAttribute("receiver", "2000");
		root.setAttribute("sender", desSysVo.getCode());
		root.setAttribute("proc", "delete");
		root.setAttribute("codeexchanged", "y");

		// 返回数据库表中数据的结点
		for (VoucherVO voucherVO : voucherVOs) {
			// voucherBO.deleteByPk(voucherVO.getPk_voucher());
			Element voucher = document.createElement("voucher");
			voucher.setAttribute("id",/*
									 * voucherVO.getPk_org() + "_" +
									 */voucherVO.getPk_voucher());
			Element voucherhead = document.createElement("voucher_head");
			voucher.appendChild(voucherhead);

			Element voucherbody = document.createElement("voucher_body");
			Element entry = document.createElement("entry");
			Element explain = document.createElement("abstract");
			Text text = document.createTextNode("abstract");
			text.setData("delete");
			explain.appendChild(text);
			entry.appendChild(explain);
			voucherbody.appendChild(entry);
			voucher.appendChild(voucherbody);
			root.appendChild(voucher);
		}
		document.appendChild(root);
		return document;
	}

	/**
	 * 新增xml获取
	 */
	@Override
	public Document getDocumentByVoucherVOs(VoucherVO[] voucherVOs,
			PfxxSysConfVO desSysVo) throws BusinessException {

		org.w3c.dom.Document document = new DocumentImpl();
		org.w3c.dom.Element root = document.createElement("ufinterface");
		root.setAttribute("roottag", "voucher");
		root.setAttribute("billtype", "gl");
		root.setAttribute("docid", "989898989898");
		// try{
		// IBDData docByPk =
		// GeneralAccessorFactory.getAccessor(IOrgMetaDataIDConst.FINANCEORG).getDocByPk(voucherVOs[0].getPk_org());
		// root.setAttribute("receiver", docByPk.getCode());
		// }catch(Exception e) {
		// Logger.error(e.getMessage(), e);
		// }
		root.setAttribute("sender", desSysVo.getCode());
		root.setAttribute("proc", "add");
		root.setAttribute("codeexchanged", "y");

		// StringBuilder fileBuilder = new StringBuilder();
		// //返回表示数据库表结构的结点
		// Node tableStruct = getTableStructNode(document, tableVO);
		// root.appendChild(tableStruct);
		// 返回数据库表中数据的结点(获取主键)
		try {
//			VoucherBO voucherBO = new VoucherBO();
			for (VoucherVO voucherVo : voucherVOs) {
//				String strPk = voucherVo.getPk_voucher();
//				UFBoolean discardflag = voucherVo.getDiscardflag();
//				if (discardflag == null || !discardflag.booleanValue()) {
//					if (strPk != null && !"".equals(strPk)) {
//						voucherVo = voucherBO.update(voucherVo);
//					} else {
//						Integer no = voucherVo.getNo();
//						if (no == 0) {
//							voucherVo.setNo(1);
//						}
//						voucherVo = voucherBO.insert(voucherVo, null);
//					}
//				}
				org.w3c.dom.Node voucherNode = XML_VoucherTranslator57
						.getVoucherNode(document, voucherVo);
				root.appendChild(voucherNode);
			}
		} catch (Exception e) {
			Logger.error(e.getMessage());
			throw new BusinessException(e);
		}
		//
		document.appendChild(root);
		// XML_VoucherTranslator57.writeXMLFormatString(fileBuilder, document,
		// -2);
		// XML_VoucherTranslator57.saveToFile(filepath, fileBuilder);

		return document;
	}

	@Override
	public String getXmlFilePath(VoucherVO[] voucherVOs)
			throws BusinessException {

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(VOUCHER_XML_PATH);
		strBuilder.append(voucherVOs[0].getYear() + "_");
		strBuilder.append(voucherVOs[0].getPeriod() + "_");
		strBuilder.append(voucherVOs[0].getPk_accountingbook() + "_");
		// strBuilder.append(voucherVOs[0].getFree8());
		// 修改为主键
		strBuilder.append(voucherVOs[0].getPk_voucher());
		strBuilder.append(".xml");
		return strBuilder.toString();
	}

	@Override
	public String getXmlBackFilePath() throws BusinessException {
		return VOUCHER_XML_PATH_BAK;
	}

	@Override
	public String getSystemName() throws BusinessException {
		return "NC57";
	}

	/**
	 * 删除xml获取
	 */
	@Override
	public Document getDelDocumentByLogVOs(VoucherPfxxLogVO[] voucherPfxxLogVOs)
			throws BusinessException {
		org.w3c.dom.Document document = new DocumentImpl();
		org.w3c.dom.Element root = document.createElement("ufinterface");
		root.setAttribute("roottag", "voucher");
		root.setAttribute("billtype", "gl");
		root.setAttribute("docid", "989898989898");
		root.setAttribute("receiver", "2000");
		root.setAttribute("sender", "");
		root.setAttribute("proc", "delete");
		root.setAttribute("codeexchanged", "y");

		// 返回数据库表中数据的结点
		for (VoucherPfxxLogVO logVo : voucherPfxxLogVOs) {
			// voucherBO.deleteByPk(logVo.getBillid());
			Element voucher = document.createElement("voucher");
			voucher.setAttribute("id",
					logVo.getPk_org() + "_" + logVo.getBillid());
			Element voucherhead = document.createElement("voucher_head");
			voucher.appendChild(voucherhead);

			Element voucherbody = document.createElement("voucher_body");
			Element entry = document.createElement("entry");
			Element explain = document.createElement("abstract");
			Text text = document.createTextNode("abstract");
			text.setData("delete");
			explain.appendChild(text);
			entry.appendChild(explain);
			voucherbody.appendChild(entry);
			voucher.appendChild(voucherbody);
			root.appendChild(voucher);
		}
		document.appendChild(root);
		return document;
	}

	@Override
	public String getDelXmlFilePath(VoucherVO[] voucherVOs)
			throws BusinessException {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(VOUCHER_XML_PATH);
		strBuilder.append(voucherVOs[0].getYear() + "_");
		strBuilder.append(voucherVOs[0].getPeriod() + "_");
		strBuilder.append(voucherVOs[0].getPk_accountingbook() + "_");
		strBuilder.append(voucherVOs[0].getPk_voucher() + "_");
		strBuilder.append("del");
		strBuilder.append(".xml");
		return strBuilder.toString();
	}

	@Override
	public void doAction(IBusinessEvent event) throws BusinessException {
		// TODO Auto-generated method stub

	}

}
