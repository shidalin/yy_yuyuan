package nc.bs.gl.pfxx.nc57;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.bs.uap.sf.facility.SFServiceFacility;
import nc.itf.bd.pub.IBDMetaDataIDConst;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.pubitf.bd.accessor.GeneralAccessorFactory;
import nc.pubitf.bd.accessor.IGeneralAccessor;
import nc.vo.bd.accessor.IBDData;
import nc.vo.bd.accessor.bankaccsub.BankaccsubBDDataVO;
import nc.vo.bd.account.AccountVO;
import nc.vo.fipub.utils.StrTools;
import nc.vo.gateway60.accountbook.AccountBookUtil;
import nc.vo.gateway60.itfs.AccountUtilGL;
import nc.vo.gl.pubvoucher.DetailVO;
import nc.vo.gl.pubvoucher.VoucherVO;
import nc.vo.jcom.xml.XMLUtil;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * 此处插入类型说明。 创建日期：(2002-4-11 11:54:06)
 * 
 * @author：郭宝华
 */
public class XML_VoucherTranslator57 {
	/** 对含有子结点的结点分行显示 */
	private static boolean m_lastIsAString = false;

	/** 对只含有属性的结点显示 */
	private static boolean m_hasOnlyAtrr = false;

	/**
	 * XML_VoucherTranslator 构造子注解。
	 */
	public XML_VoucherTranslator57() {
		super();
	}

	/**
	 * entry_id 分录号 account_code 科目编码 abstract 摘要 settlement 结算方式 document_id
	 * 票据号 document_date 票据日期 currency 币种 unit_price 单价 exchange_rate1 汇率1
	 * 主辅币核算时使用 折辅汇率 原币*汇率1=辅币 exchange_rate2 汇率2 折主汇率 折本汇率 本币*汇率2=主币
	 * debit_quantity 借方数量 primary_debit_amount 原币借方发生额 secondary_debit_amount
	 * 辅币借方发生额 natural_debit_currency 本币借方发生额 credit_quantity 贷方数量
	 * primary_credit_amount 原币贷方发生额 secondary_credit_amount 辅币贷方发生额
	 * natural_credit_currency 本币贷方发生额 bill_type 原始单据类型 bill_id 原始单据编号 bill_date
	 * 原始单据日期 auxiliary_accounting 辅助核算 正象样例中写的，有多少就有多少个item detail 明细
	 */
	private static BaseDAO dao;

	private static BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}

	private static Node getDetailNode(Document document, DetailVO detailVO,
			VoucherVO voucherVo) {
		Element entry = document.createElement("entry");
		Text text = null;
		Element entry_id = document.createElement("entry_id");
		text = document.createTextNode("entry_id");
		text.setData(detailVO.getDetailindex() == null ? "0" : detailVO
				.getDetailindex().toString());
		entry_id.appendChild(text);
		entry.appendChild(entry_id);
		Element account_code = document.createElement("account_code");
		text = document.createTextNode("account_code");
		if (detailVO.getAccsubjcode() == null) {
			try {
				AccountVO accountVo = AccountUtilGL.findByPrimaryKey(detailVO
						.getPk_accasoa(), voucherVo.getPrepareddate()
						.toStdString());
				text.setData(accountVo.getCode());
			} catch (BusinessException e) {
				Logger.error(e.getMessage(), e);
			}

		} else {
			text.setData(detailVO.getAccsubjcode());
		}
		account_code.appendChild(text);
		entry.appendChild(account_code);
		Element _abstract = document.createElement("abstract");
		text = document.createTextNode("abstract");
		text.setData(detailVO.getExplanation() == null ? "" : detailVO
				.getExplanation());
		_abstract.appendChild(text);
		entry.appendChild(_abstract);
		// 结算方式存储的是结算方式（bd_balastyle-pk_balatype）主键
		// 修改为查询结算方式编码code
		Element settlement = document.createElement("settlement");
		text = document.createTextNode("settlement");
		// getDao
		String checkstyle = detailVO.getCheckstyle();
		if (checkstyle != null && !"".equals(checkstyle)) {
			String querySql = "select t.code from bd_balatype t where t.pk_balatype = '"
					+ checkstyle + "' ";
			try {
				Object executeQuery = getDao().executeQuery(querySql,
						new ColumnProcessor());
				// text.setData(detailVO.getCheckstylename() == null ? "" :
				// detailVO
				// .getCheckstylename());
				text.setData(executeQuery == null ? "" : executeQuery
						.toString());
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ExceptionUtils.wrappBusinessException("NC65凭证传递查询结算方式编码错误");
			}
		} else {
			text.setData("");
		}
		settlement.appendChild(text);
		entry.appendChild(settlement);
		/** 银行帐号 */
		Element bankcode = document.createElement("bankcode");
		text = document.createTextNode("");
		text = document.createTextNode("bankaccount");
		text.setData(getNameValue(detailVO.getBankaccount(),
				IBDMetaDataIDConst.BANKACCSUB));
		bankcode.appendChild(text);
		entry.appendChild(bankcode);

		/** 票据类型 */
		Element notetype = document.createElement("notetype");
		text = document.createTextNode("");
		text.setData(getNameValue(detailVO.getBilltype(),
				IBDMetaDataIDConst.BANKACCSUB));

		notetype.appendChild(text);
		entry.appendChild(notetype);
		Element document_id = document.createElement("document_id");
		text = document.createTextNode("document_id");
		text.setData(detailVO.getCheckno() == null ? "" : detailVO.getCheckno());
		document_id.appendChild(text);
		entry.appendChild(document_id);
		Element document_date = document.createElement("document_date");
		text = document.createTextNode("document_date");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		text.setData(detailVO.getCheckdate() == null ? "" : formatter
				.format(detailVO.getCheckdate().toDate()));
		document_date.appendChild(text);
		entry.appendChild(document_date);
		Element currency = document.createElement("currency");
		text = document.createTextNode("currency");
		if (detailVO.getCurrtypename() == null) {
			try {
				IBDData docByPk = GeneralAccessorFactory.getAccessor(
						IBDMetaDataIDConst.CURRTYPE).getDocByPk(
						detailVO.getPk_currtype());
				text.setData(docByPk.getCode());
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
			}
		} else {
			text.setData(detailVO.getCurrtypename());
		}
		currency.appendChild(text);
		entry.appendChild(currency);
		Element unit_price = document.createElement("unit_price");
		text = document.createTextNode("unit_price");
		text.setData(detailVO.getPrice() == null ? "0" : detailVO.getPrice()
				.toString());
		unit_price.appendChild(text);
		entry.appendChild(unit_price);
		Element exchange_rate1 = document.createElement("exchange_rate1");
		text = document.createTextNode("exchange_rate1");
		text.setData(detailVO.getExcrate1() == null ? "0" : detailVO
				.getExcrate1().toString());
		exchange_rate1.appendChild(text);
		entry.appendChild(exchange_rate1);
		Element exchange_rate2 = document.createElement("exchange_rate2");
		text = document.createTextNode("exchange_rate2");
		text.setData(detailVO.getExcrate2() == null ? "0" : detailVO
				.getExcrate2().toString());
		exchange_rate2.appendChild(text);
		entry.appendChild(exchange_rate2);
		Element debit_quantity = document.createElement("debit_quantity");
		text = document.createTextNode("debit_quantity");
		text.setData(detailVO.getDebitquantity() == null ? "0" : detailVO
				.getDebitquantity().toString());
		debit_quantity.appendChild(text);
		entry.appendChild(debit_quantity);
		Element primary_debit_amount = document
				.createElement("primary_debit_amount");
		text = document.createTextNode("primary_debit_amount");
		text.setData(detailVO.getDebitamount() == null ? "0" : detailVO
				.getDebitamount().toString());
		primary_debit_amount.appendChild(text);
		entry.appendChild(primary_debit_amount);
		Element secondary_debit_amount = document
				.createElement("secondary_debit_amount");
		text = document.createTextNode("secondary_debit_amount");
		text.setData(detailVO.getFracdebitamount() == null ? "0" : detailVO
				.getFracdebitamount().toString());
		secondary_debit_amount.appendChild(text);
		entry.appendChild(secondary_debit_amount);
		Element natural_debit_currency = document
				.createElement("natural_debit_currency");
		text = document.createTextNode("natural_debit_currency");
		text.setData(detailVO.getLocaldebitamount() == null ? "0" : detailVO
				.getLocaldebitamount().toString());
		natural_debit_currency.appendChild(text);
		entry.appendChild(natural_debit_currency);
		Element credit_quantity = document.createElement("credit_quantity");
		text = document.createTextNode("credit_quantity");
		text.setData(detailVO.getCreditquantity() == null ? "0" : detailVO
				.getCreditquantity().toString());
		credit_quantity.appendChild(text);
		entry.appendChild(credit_quantity);
		Element primary_credit_amount = document
				.createElement("primary_credit_amount");
		text = document.createTextNode("primary_credit_amount");
		text.setData(detailVO.getCreditamount() == null ? "0" : detailVO
				.getCreditamount().toString());
		primary_credit_amount.appendChild(text);
		entry.appendChild(primary_credit_amount);
		Element secondary_credit_amount = document
				.createElement("secondary_credit_amount");
		text = document.createTextNode("secondary_credit_amount");
		text.setData(detailVO.getFraccreditamount() == null ? "0" : detailVO
				.getFraccreditamount().toString());
		secondary_credit_amount.appendChild(text);
		entry.appendChild(secondary_credit_amount);
		Element natural_credit_currency = document
				.createElement("natural_credit_currency");
		text = document.createTextNode("natural_credit_currency");
		text.setData(detailVO.getLocalcreditamount() == null ? "0" : detailVO
				.getLocalcreditamount().toString());
		natural_credit_currency.appendChild(text);
		entry.appendChild(natural_credit_currency);
		Element bill_type = document.createElement("bill_type");
		text = document.createTextNode("bill_type");
		text.setData("");
		bill_type.appendChild(text);
		entry.appendChild(bill_type);
		Element bill_id = document.createElement("bill_id");
		text = document.createTextNode("bill_id");
		text.setData("");
		bill_id.appendChild(text);
		entry.appendChild(bill_id);
		Element bill_date = document.createElement("bill_date");
		text = document.createTextNode("bill_date");
		text.setData("");
		bill_date.appendChild(text);
		entry.appendChild(bill_date);

		if (detailVO.getAss() != null) {
			Element auxiliary_accounting = document
					.createElement("auxiliary_accounting");
			for (int i = 0; i < detailVO.getAss().length; i++) {
				Element item = document.createElement("item");
				item.setAttribute("name",
						detailVO.getAss()[i].getChecktypename());
				text = document.createTextNode("item");
				text.setData(detailVO.getAss()[i].getCheckvaluecode() == null ? ""
						: detailVO.getAss()[i].getCheckvaluecode());
				item.appendChild(text);
				auxiliary_accounting.appendChild(item);
			}
			entry.appendChild(auxiliary_accounting);
		}

		if (detailVO.getCashFlow() != null && detailVO.getCashFlow().length > 0) {
			Element otheruserdata = document.createElement("otheruserdata");
			for (int i = 0; i < detailVO.getCashFlow().length; i++) {
				Element cashflowcase = document.createElement("cashflowcase");

				Element money = document.createElement("money");
				text = document.createTextNode("money");
				text.setData(detailVO.getCashFlow()[i].getMoney() == null ? ""
						: detailVO.getCashFlow()[i].getMoney().toString());
				money.appendChild(text);
				cashflowcase.appendChild(money);

				Element moneyass = document.createElement("moneyass");
				text = document.createTextNode("moneyass");
				text.setData(detailVO.getCashFlow()[i].getMoneyass() == null ? ""
						: detailVO.getCashFlow()[i].getMoneyass().toString());
				moneyass.appendChild(text);
				cashflowcase.appendChild(moneyass);

				Element moneymain = document.createElement("moneymain");
				text = document.createTextNode("moneymain");
				text.setData(detailVO.getCashFlow()[i].getMoneymain() == null ? ""
						: detailVO.getCashFlow()[i].getMoneymain().toString());
				moneymain.appendChild(text);
				cashflowcase.appendChild(moneymain);

				Element cashflow = document.createElement("pk_cashflow");
				text = document.createTextNode("pk_cashflow");
				text.setData(detailVO.getCashFlow()[i].getCashflowName() == null ? ""
						: detailVO.getCashFlow()[i].getCashflowName()
								.toString());
				cashflow.appendChild(text);
				cashflowcase.appendChild(cashflow);

				otheruserdata.appendChild(cashflowcase);
			}
			entry.appendChild(otheruserdata);
		}

		Element detail = document.createElement("detail");
		text = document.createTextNode("detail");
		text.setData("");
		detail.appendChild(text);
		entry.appendChild(detail);
		return entry;
	}

	public static Document getDocument(String filename) {

		DOMParser parser = new DOMParser();

		File file = new File(filename);
		if (file.exists()) {
			try {
				Document doc = XMLUtil.getDocument(file);
				file.exists();
				return doc;
			} catch (Exception e) {
				nc.bs.logging.Logger.error(e.getMessage());
			}
		} else {
			return null;
		}

		// 从文档中分析得到一个文档
		return null;
	}

	private static String getSpace(int depth) {
		char[] ca = new char[depth];
		for (int i = 0; i < ca.length; i++) {
			ca[i] = '\t';
		}
		return new String(ca);
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-4-11 11:47:19)
	 * 
	 * @return Node
	 */
	private static Node getVoucherBodyNode(Document document,
			VoucherVO voucherVO) {
		Element voucherbody = document.createElement("voucher_body");
		for (int i = 0; i < voucherVO.getNumDetails(); i++) {
			Node detail = getDetailNode(document, voucherVO.getDetail(i),
					voucherVO);
			voucherbody.appendChild(detail);
		}
		return voucherbody;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-4-11 11:47:19)
	 * 
	 * @return Node
	 */
	private static Node getVoucherHeadNode(Document document,
			VoucherVO voucherVO) {
		Text text = null;
		Element voucherhead = document.createElement("voucher_head");
		// <!-- 基础数据"公司目录"，可通过基础数据配置界面配置，-->
		Element company = document.createElement("company");
		text = document.createTextNode("company");
		String orgCode = "";
		if (!StrTools.isEmptyStr(voucherVO.getPk_accountingbook())) {
			IGeneralAccessor accessor = GeneralAccessorFactory
					.getAccessor(IBDMetaDataIDConst.ORG_ORGS);
			IBDData bdData = accessor.getDocByPk(AccountBookUtil
					.getAccountingBookVOByPrimaryKey(
							voucherVO.getPk_accountingbook()).getPk_relorg());
			orgCode = bdData.getCode();
		}
		text.setData(orgCode);
		// text.setData("test");
		company.appendChild(text);
		voucherhead.appendChild(company);
		// <!-- 基础数据"凭证类型"，可通过基础数据配置界面配置，-->
		Element voucher_type = document.createElement("voucher_type");
		text = document.createTextNode("voucher_type");
		text.setData(voucherVO.getVouchertypename() == null ? "" : voucherVO
				.getVouchertypename());
		voucher_type.appendChild(text);
		voucherhead.appendChild(voucher_type);
		// <!-- 会计期间应与下面的制单日期一致 -->
		Element fiscal_year = document.createElement("fiscal_year");
		text = document.createTextNode("fiscal_year");
		text.setData(voucherVO.getYear() == null ? "" : voucherVO.getYear());
		fiscal_year.appendChild(text);
		voucherhead.appendChild(fiscal_year);
		Element accounting_period = document.createElement("accounting_period");
		text = document.createTextNode("accounting_period");
		text.setData(voucherVO.getPeriod() == null ? "" : voucherVO.getPeriod());
		accounting_period.appendChild(text);
		voucherhead.appendChild(accounting_period);
		Element voucher_id = document.createElement("voucher_id");
		text = document.createTextNode("voucher_id");
		text.setData("");
		voucher_id.appendChild(text);
		voucherhead.appendChild(voucher_id);
		Element attachment_number = document.createElement("attachment_number");
		text = document.createTextNode("attachment_number");
		text.setData(voucherVO.getAttachment() == null ? "0" : voucherVO
				.getAttachment().toString());
		attachment_number.appendChild(text);
		voucherhead.appendChild(attachment_number);
		// <!-- 制单日期 -->
		Element date = document.createElement("prepareddate");
		text = document.createTextNode("prepareddate");
		text.setData(voucherVO.getPrepareddate() == null ? "" : voucherVO
				.getPrepareddate().toStdString());
		date.appendChild(text);
		voucherhead.appendChild(date);
		Element enter = document.createElement("enter");
		text = document.createTextNode("enter");
		// text.setData(voucherVO.getPreparedname() == null ? "" :
		// voucherVO.getPreparedname());
		try {
			// TODO 将写死的用户修改过来
			text.setData(voucherVO.getPk_prepared() == null ? ""
					: SFServiceFacility.getIUserManageQuery()
							.getUser(voucherVO.getPk_prepared()).getUser_code());
			// text.setData("tbadmin");
			enter.appendChild(text);
			voucherhead.appendChild(enter);
			// <!-- 基础数据"操作员"，可通过基础数据配置界面配置，-->
			Element cashier = document.createElement("cashier");
			text = document.createTextNode("cashier");
			// text.setData(voucherVO.getCashername() == null ? "" :
			// voucherVO.getCashername());
			text.setData(voucherVO.getPk_casher() == null ? ""
					: SFServiceFacility.getIUserManageQuery()
							.getUser(voucherVO.getPk_casher()).getUser_code());
			cashier.appendChild(text);
			voucherhead.appendChild(cashier);
			// <!-- 基础数据"操作员"，可通过基础数据配置界面配置，-->
			Element signature = document.createElement("signature");
			text = document.createTextNode("signature");
			text.setData(voucherVO.getSignflag() == null ? "N" : voucherVO
					.getSignflag().toString());
			signature.appendChild(text);
			voucherhead.appendChild(signature);
			// <!-- 基础数据"操作员"，可通过基础数据配置界面配置，-->
			Element checker = document.createElement("checker");
			text = document.createTextNode("checker");
			// text.setData(voucherVO.getCheckedname() == null ? "" :
			// voucherVO.getCheckedname());
			text.setData(voucherVO.getPk_checked() == null ? ""
					: SFServiceFacility.getIUserManageQuery()
							.getUser(voucherVO.getPk_checked()).getUser_code());
			checker.appendChild(text);
			voucherhead.appendChild(checker);
			Element posting_date = document.createElement("posting_date");
			text = document.createTextNode("posting_date");
			text.setData(voucherVO.getTallydate() == null ? "" : voucherVO
					.getTallydate().toString());
			posting_date.appendChild(text);
			voucherhead.appendChild(posting_date);
			Element posting_person = document.createElement("posting_person");
			text = document.createTextNode("posting_person");
			// text.setData(voucherVO.getManagername() == null ? "" :
			// voucherVO.getManagername());
			text.setData(voucherVO.getPk_manager() == null ? ""
					: SFServiceFacility.getIUserManageQuery()
							.getUser(voucherVO.getPk_manager()).getUser_code());
			posting_person.appendChild(text);
			voucherhead.appendChild(posting_person);
		} catch (DOMException e) {
			Logger.error(e.getMessage(), e);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
		}
		Element voucher_making_system = document
				.createElement("voucher_making_system");
		text = document.createTextNode("voucher_making_system");
		text.setData(voucherVO.getSystemname() == null ? nc.vo.ml.NCLangRes4VoTransl
				.getNCLangRes().getStrByID("20021005", "UPP20021005-000244")/*
																			 * @res
																			 * "总账"
																			 */
		: voucherVO.getSystemname());
		voucher_making_system.appendChild(text);
		voucherhead.appendChild(voucher_making_system);
		Element memo1 = document.createElement("memo1");
		text = document.createTextNode("memo1");
		text.setData("");
		memo1.appendChild(text);
		voucherhead.appendChild(memo1);
		Element memo2 = document.createElement("memo2");
		text = document.createTextNode("memo2");
		text.setData("");
		memo2.appendChild(text);
		voucherhead.appendChild(memo2);
		Element reserve1 = document.createElement("reserve1");
		text = document.createTextNode("reserve1");
		text.setData("");
		reserve1.appendChild(text);
		voucherhead.appendChild(reserve1);
		Element reserve2 = document.createElement("reserve2");
		text = document.createTextNode("reserve2");
		text.setData("");
		reserve2.appendChild(text);
		voucherhead.appendChild(reserve2);

		Element revokeflag = document.createElement("revokeflag");
		text = document.createTextNode("revokeflag");
		text.setData(voucherVO.getDiscardflag() == null ? "" : voucherVO
				.getDiscardflag().toString());
		revokeflag.appendChild(text);
		voucherhead.appendChild(revokeflag);

		Element free10 = document.createElement("free5");
		text = document.createTextNode("free5");
		text.setData(voucherVO.getNo() == null ? "" : voucherVO.getNo()
				.toString());
		free10.appendChild(text);
		voucherhead.appendChild(free10);

		return voucherhead;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-4-11 11:47:19)
	 * 
	 * @return Node
	 * @throws NamingException
	 */
	public static Node getVoucherNode(Document document, VoucherVO voucherVO)
			throws NamingException {
		if (voucherVO == null)
			return null;
		Element voucher = document.createElement("voucher");
		// PfxxVoucherDMO dmo = new PfxxVoucherDMO();
		String strPk = voucherVO.getPk_voucher();

		voucher.setAttribute("id", strPk);
		voucherVO.setPk_voucher(strPk);
		Node voucherhead = getVoucherHeadNode(document, voucherVO);
		Node voucherbody = getVoucherBodyNode(document, voucherVO);

		voucher.appendChild(voucherhead);
		voucher.appendChild(voucherbody);
		return voucher;
	}

	public static void saveToFile(String fileName, StringBuffer fileContent)
			throws Exception {
		OutputStreamWriter out = null;
		if (fileName != null && fileContent != null) {
			try {
				// nc.vo.pub.util.EnvironmentUtil.createDirectoryAsNeeded(fileName,
				// null);
				nc.vo.jcom.io.FileUtil.createDirectoryAsNeeded(fileName, null);
				out = new OutputStreamWriter(new FileOutputStream(fileName,
						false), "UTF-8");
				out.write(fileContent.toString());
			} catch (Exception e) {
				nc.bs.logging.Logger.error(e.getMessage());
			} finally {
				if (out != null)
					out.close();
			}
		}
	}

	public static void writeXMLFormatString(StringBuilder fileBuilder,
			Node node, int depth) {
		depth++;
		int type = node.getNodeType();
		switch (type) {
		case Node.DOCUMENT_NODE:
			fileBuilder.append("<?xml version=\"1.0\" encoding='UTF-8'?>");
			writeXMLFormatString(fileBuilder,
					((Document) node).getDocumentElement(), depth);
			break;
		case Node.ELEMENT_NODE:
			fileBuilder.append("\r\n");
			fileBuilder.append(getSpace(depth) + "<");
			fileBuilder.append(node.getNodeName());
			org.w3c.dom.NamedNodeMap attrs = node.getAttributes();
			for (int i = 0; i < attrs.getLength(); i++) {
				Node attr = attrs.item(i);
				fileBuilder.append(" " + attr.getNodeName() + "=\""
						+ attr.getNodeValue() + "\"");
				// if (attr.getNodeValue().equals("null"))
				// m_lastIsAString = true;
			}
			org.w3c.dom.NodeList children = node.getChildNodes();
			// fileBuffer.append(">");
			if (children != null) {
				int len = children.getLength();
				if (len > 0)
					fileBuilder.append(">");
				else {
					fileBuilder.append(" />");
					m_hasOnlyAtrr = true;
				}
				for (int i = 0; i < len; i++)
					writeXMLFormatString(fileBuilder, children.item(i), depth);
			}

			break;
		case Node.ENTITY_REFERENCE_NODE:
			fileBuilder.append("&");
			fileBuilder.append(node.getNodeName());
			fileBuilder.append(";");
			break;
		case Node.CDATA_SECTION_NODE:
			fileBuilder.append(getSpace(depth) + "<![CDATA[");
			fileBuilder.append(node.getNodeValue());
			fileBuilder.append("]]>");
			break;
		case Node.TEXT_NODE:
			fileBuilder.append(node.getNodeValue());
			m_lastIsAString = true;
			break;
		case Node.PROCESSING_INSTRUCTION_NODE:
			fileBuilder.append(getSpace(depth) + "<?");
			fileBuilder.append(node.getNodeName());
			String data = node.getNodeValue();
			{
				fileBuilder.append("");
				fileBuilder.append(data);
			}
			fileBuilder.append("?>");
			break;
		}
		if (type == Node.ELEMENT_NODE) {
			if (!m_hasOnlyAtrr) {
				if (!m_lastIsAString) {
					fileBuilder.append("\r\n");
					fileBuilder.append(getSpace(depth) + "</");
				} else
					fileBuilder.append("</");
				fileBuilder.append(node.getNodeName());
				fileBuilder.append('>');
				m_lastIsAString = false;
			} else {
				m_hasOnlyAtrr = false;
			}
		}
	}

	private static String getNameValue(String pk, String mid) {
		IGeneralAccessor accesssor = GeneralAccessorFactory.getAccessor(mid);
		IBDData accor = accesssor.getDocByPk(pk);
		// 导出子户

		if (accor != null) {
			if (IBDMetaDataIDConst.BANKACCSUB.equals(mid)) {
				return ((BankaccsubBDDataVO) accor).getSubname().toString();
			}
			return accesssor.getDocByPk(pk).getName().getText();
		}

		return "";
	}

	public static void writeXMLFormatString(StringBuilder fileBuilder,
			Node node, int depth, String encode) {
		depth++;
		int type = node.getNodeType();
		switch (type) {
		case Node.DOCUMENT_NODE:
			fileBuilder.append("<?xml version=\"1.0\" encoding='" + encode
					+ "'?>");
			writeXMLFormatString(fileBuilder,
					((Document) node).getDocumentElement(), depth);
			break;
		case Node.ELEMENT_NODE:
			fileBuilder.append("\r\n");
			fileBuilder.append(getSpace(depth) + "<");
			fileBuilder.append(node.getNodeName());
			org.w3c.dom.NamedNodeMap attrs = node.getAttributes();
			for (int i = 0; i < attrs.getLength(); i++) {
				Node attr = attrs.item(i);
				fileBuilder.append(" " + attr.getNodeName() + "=\""
						+ attr.getNodeValue() + "\"");
				// if (attr.getNodeValue().equals("null"))
				// m_lastIsAString = true;
			}
			org.w3c.dom.NodeList children = node.getChildNodes();
			// fileBuffer.append(">");
			if (children != null) {
				int len = children.getLength();
				if (len > 0)
					fileBuilder.append(">");
				else {
					fileBuilder.append(" />");
					m_hasOnlyAtrr = true;
				}
				for (int i = 0; i < len; i++)
					writeXMLFormatString(fileBuilder, children.item(i), depth);
			}

			break;
		case Node.ENTITY_REFERENCE_NODE:
			fileBuilder.append("&");
			fileBuilder.append(node.getNodeName());
			fileBuilder.append(";");
			break;
		case Node.CDATA_SECTION_NODE:
			fileBuilder.append(getSpace(depth) + "<![CDATA[");
			fileBuilder.append(node.getNodeValue());
			fileBuilder.append("]]>");
			break;
		case Node.TEXT_NODE:
			fileBuilder.append(node.getNodeValue());
			m_lastIsAString = true;
			break;
		case Node.PROCESSING_INSTRUCTION_NODE:
			fileBuilder.append(getSpace(depth) + "<?");
			fileBuilder.append(node.getNodeName());
			String data = node.getNodeValue();
			{
				fileBuilder.append("");
				fileBuilder.append(data);
			}
			fileBuilder.append("?>");
			break;
		}
		if (type == Node.ELEMENT_NODE) {
			if (!m_hasOnlyAtrr) {
				if (!m_lastIsAString) {
					fileBuilder.append("\r\n");
					fileBuilder.append(getSpace(depth) + "</");
				} else
					fileBuilder.append("</");
				fileBuilder.append(node.getNodeName());
				fileBuilder.append('>');
				m_lastIsAString = false;
			} else {
				m_hasOnlyAtrr = false;
			}
		}
	}
}