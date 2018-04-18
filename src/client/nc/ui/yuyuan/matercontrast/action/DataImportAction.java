package nc.ui.yuyuan.matercontrast.action;

import java.awt.event.ActionEvent;

import nc.bs.uif2.IActionCode;
import nc.ui.pubapp.excel.ExcelImportAction;
import nc.ui.pubapp.excel.ExcelTempletBasedEntry;
import nc.ui.pubapp.excel.IExcelImportProcess;
import nc.ui.pubapp.excel.IVOColumnValueProcess;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.components.IAutoShowUpComponent;

/**
 * 导入按钮
 * 
 * @author shidl
 * 
 */
public class DataImportAction extends NCAction {
	private BillForm billform;

	private String bodyClassName;

	private DataImportExcelEntry excelEntry;

	private IExcelImportProcess excelImportProcessor;

	private String headClassName;

	private String keyWord;

	private String title;

	private IVOColumnValueProcess valueProcess;

	public DataImportAction() {
		this.initActionUI();
	}

	@Override
	public void doAction(ActionEvent e) throws Exception {
		if (this.getBillform() instanceof IAutoShowUpComponent) {
			((IAutoShowUpComponent) this.getBillform()).showMeUp();
		}
		this.getExcelEntry().showEntryUI();
	}

	public BillForm getBillform() {
		return this.billform;
	}

	public String getBodyClassName() {
		return this.bodyClassName;
	}

	public DataImportExcelEntry getExcelEntry() {

		this.excelEntry = new DataImportExcelEntry(this.title,
				DataImportExcelEntry.IMPORT, this.getBillform()
						.getBillCardPanel(), this.getBillform().getModel()
						.getContext());
		this.excelEntry.setExcelImportProcessor(this.getExcelImportProcessor());
		this.excelEntry.setValueProcess(this.getValueProcess());
		this.excelEntry.setHeadClassName(this.getHeadClassName());
		this.excelEntry.setBodyClassName(this.getBodyClassName());
		this.excelEntry.setKeyWord(this.keyWord);

		return this.excelEntry;
	}

	public IExcelImportProcess getExcelImportProcessor() {
		return this.excelImportProcessor;
	}

	public String getHeadClassName() {
		return this.headClassName;
	}

	public String getKeyWord() {
		return this.keyWord;
	}

	public String getTitle() {
		return this.title;
	}

	public IVOColumnValueProcess getValueProcess() {
		return this.valueProcess;
	}

	public void setBillform(BillForm billform) {
		this.billform = billform;
	}

	public void setBodyClassName(String bodyClassName) {
		this.bodyClassName = bodyClassName;
	}

	public void setExcelEntry(DataImportExcelEntry excelEntry) {
		this.excelEntry = excelEntry;
	}

	public void setExcelImportProcessor(IExcelImportProcess excelImportProcessor) {
		this.excelImportProcessor = excelImportProcessor;
	}

	public void setHeadClassName(String headClassName) {
		this.headClassName = headClassName;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public void setTitle(String title) {
		this.title = title;
		this.setBtnName(title);
	}

	public void setValueProcess(IVOColumnValueProcess valueProcess) {
		this.valueProcess = valueProcess;
	}

	protected void initActionUI() {
		this.setBtnName(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
				"pubapp_0", "0pubapp-0050")/* @res "Excel导入" */);
		this.setCode(IActionCode.IMPORTBILL);
	}
}