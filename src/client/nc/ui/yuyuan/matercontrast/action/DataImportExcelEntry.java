package nc.ui.yuyuan.matercontrast.action;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.CellView;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import nc.bs.logging.Logger;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.constenum.IConstEnum;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pubapp.excel.BaseDocAutoTranslator;
import nc.ui.pubapp.excel.CellInfo;
import nc.ui.pubapp.excel.CellInfoVO;
import nc.ui.pubapp.excel.DocCellInfo;
import nc.ui.pubapp.excel.ExcelProcDlg;
import nc.ui.pubapp.excel.ExcelTempletBasedEntry;
import nc.ui.pubapp.excel.IExcelImportProcess;
import nc.ui.pubapp.excel.IExcelProcessor;
import nc.ui.pubapp.excel.IExcelProgress;
import nc.ui.pubapp.excel.IVOColumnValueProcess;
import nc.ui.pubapp.excel.MyFileFilter;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.log.Log;
import nc.vo.trade.checkrule.VOChecker;
import nc.vo.uif2.LoginContext;

import org.apache.commons.lang.StringUtils;

public class DataImportExcelEntry implements IExcelProcessor {
	public static final String EXPORT = nc.vo.ml.NCLangRes4VoTransl
			.getNCLangRes().getStrByID("pubapp_0", "0pubapp-0053")/* @res "导出" */;

	public static final String IMPORT = nc.vo.ml.NCLangRes4VoTransl
			.getNCLangRes().getStrByID("pubapp_0", "0pubapp-0054")/* @res "导入" */;

	public static final int IROWBODYDATA = 4;

	public static final int IROWBODYTITLE = 2;

	public static final int IROWHEAD = 1;

	public static final int IROWTITLE = 2;

	public static final String SHOWNAME = "_name";

	private BaseDocAutoTranslator baseDocAutoTranslator;

	private String bodyClassName;

	private BillCardPanel cardPanel;

	private Map<String, CellInfo> cellInfoMap = new HashMap<String, CellInfo>();

	private LoginContext context;

	private IExcelImportProcess excelImportProcessor;

	private ExcelProcDlg excelProcDlg;

	private javax.swing.JFileChooser fcFileChooser;

	private String headClassName;

	private CircularlyAccessibleValueObject[] importbodyVO;

	private CircularlyAccessibleValueObject importHeadVO;

	private Sheet importws;

	private int keycol = -1;

	private int keyrow = -1;

	private String keyWord;

	private IExcelProgress progressObserve;

	private int startCol_b = 4;

	private int startCol_h = 4;

	private int startIdex = 3;

	private String title;

	private String type = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
			.getStrByID("pubapp_0", "0pubapp-0053")/* @res "导出" */;

	private IVOColumnValueProcess valueProcess;

	private jxl.write.WritableSheet ws;

	private jxl.write.WritableWorkbook wwb;

	// 存储界面格式
	private Map<String, Integer> code2colPost = new HashMap<String, Integer>();
	
	private CircularlyAccessibleValueObject[] importVO;
	private Integer startcol = 3;
	private Integer startrow = 2;
    /**
     * 设置excel格式
     * @return
     */
	public Map<String, Integer> getCode2colPost() {
		if (code2colPost.keySet().size() == 0) {
			code2colPost.put("pk_org", 1);
			code2colPost.put("pk_supplier", 2);
			code2colPost.put("vmemo_h", 3);
			code2colPost.put("cmaterialvid", 4);
			code2colPost.put("dispatchstyle", 5);
			code2colPost.put("vmemo_b", 6);
		}
		return code2colPost;
	}

	public DataImportExcelEntry(String title, String type) {

		this.title = title;
		this.type = type;
		this.init();

	}

	public DataImportExcelEntry(String title, String type,
			BillCardPanel cardPanel, LoginContext context) {

		this.title = title;
		this.type = type;
		this.cardPanel = cardPanel;
		this.context = context;
		this.init();

	}

	@Override
	public void exportTOExcel() throws Exception {
		String filename = this.getUIFileChooser().getSelectedFile()
				.getAbsolutePath()
				+ ".xls";
		this.exportUI(this.getCardPanel(), filename);

	}

	@SuppressWarnings("deprecation")
	public void exportUI(BillCardPanel pcardPanel, String fileName)
			throws Exception {
		this.processCellInfo();
		// 构建Workbook对象, 只读Workbook对象
		this.wwb = Workbook.createWorkbook(new File(fileName));
		// 创建Excel工作表
		this.ws = this.wwb.createSheet(this.title, 0);
		try {
			// 当前行
			int iRow = 0;
			// 标题
			jxl.write.WritableFont wf = new jxl.write.WritableFont(
					WritableFont.TIMES, 15);
			jxl.write.WritableCellFormat wcfF = new jxl.write.WritableCellFormat(
					wf);
			this.ws.mergeCells(0, 0, this.getHeadItemCount() + 1, 0);
			wcfF.setAlignment(jxl.format.Alignment.CENTRE);
			jxl.write.Label labelCF = new jxl.write.Label(0, iRow, this.title,
					wcfF);
			this.ws.addCell(labelCF);
			if (this.keyrow != -1 && this.keycol != -1) {
				this.processKeyPos();

			}

			nc.ui.pub.bill.BillItem[] headitems = pcardPanel.getHeadItems();
			nc.ui.pub.bill.BillItem[] bodyitems = pcardPanel.getBodyItems();
			CellView cellview = new CellView();
			cellview.setHidden(true);
			// 表头
			int iCol = 0;
			if (headitems != null && headitems.length > 0) {
				for (int i = 0; i < headitems.length; i++) {
					if (headitems[i].isShow()
							|| headitems[i].getKey().equals(this.keyWord)) {
						wf = new jxl.write.WritableFont(WritableFont.TIMES, 10);
						wcfF = new jxl.write.WritableCellFormat(wf);
						CellInfo cellinfo = this.cellInfoMap.get(headitems[i]
								.getKey() + "_h");

						String value = headitems[i].getValueObject() == null ? ""
								: headitems[i].getValueObject().toString();
						if (IBillItem.UFREF == headitems[i].getDataType()) {
							value = ((UIRefPane) headitems[i].getComponent())
									.getRefShowName();

						} else if (IBillItem.COMBO == headitems[i]
								.getDataType()) {
							if (value != null) {
								IConstEnum enumValue = (IConstEnum) ((UIComboBox) headitems[i]
										.getComponent()).getSelectedItem();
								value = (String) enumValue.getName();
							}
						}
						String svalue = headitems[i].getName() + ":" + value;
						if (cellinfo != null) {
							labelCF = new jxl.write.Label(cellinfo.getiCol(),
									cellinfo.getiRow(), svalue, wcfF);
							// if(headitems[i].getKey().equals(this.keyWord)){
							// CellView cellviewh = new CellView();
							// cellviewh.setHidden(true);
							// }
							this.ws.addCell(labelCF);

							if (cellinfo.getiCol() < this.startIdex) {
								this.ws.setColumnView(cellinfo.getiCol(),
										cellview);
								this.ws.setColumnView(cellinfo.getiCol() + 1,
										cellview);

							}
							this.ws.mergeCells(cellinfo.getiCol(),
									cellinfo.getiRow(), cellinfo.getiCol()
											+ cellinfo.getiWidth() - 1,
									cellinfo.getiRow());
						}

						iCol++;

					}
				}
			}

			// 表体
			iCol = 0;
			if (bodyitems != null && bodyitems.length > 0) {
				// 表体标题
				this.ws.mergeCells(0, 0, bodyitems.length, 0);
				wf = new jxl.write.WritableFont(WritableFont.TIMES, 10);
				wcfF = new jxl.write.WritableCellFormat(wf);
				wcfF.setBorder(jxl.format.Border.ALL,
						jxl.format.BorderLineStyle.THIN);
				wcfF.setAlignment(jxl.format.Alignment.CENTRE);
				for (int i = 0; i < bodyitems.length; i++) {
					if (bodyitems[i].isShow()) {
						CellInfo cellinfo = this.cellInfoMap.get(bodyitems[i]
								.getKey() + "_b");

						if (cellinfo != null) {
							labelCF = new jxl.write.Label(cellinfo.getiCol(),
									cellinfo.getiRow(), bodyitems[i].getName(),
									wcfF);

							this.ws.addCell(labelCF);

							if (cellinfo.getiCol() < this.startIdex) {
								this.ws.setColumnView(cellinfo.getiCol(),
										cellview);
							}
						}

						iCol++;
					}

				}

				wf = new jxl.write.WritableFont(WritableFont.TIMES, 10);
				int row = 0;
				int col = 0;
				int irow = 3;
				int maxprogress = pcardPanel.getBillModel().getRowCount() == 0 ? 1
						: pcardPanel.getBillModel().getRowCount();
				this.getProgressObserve().setMaxValue(maxprogress);
				int curProgress = 1;
				for (row = 0; row < pcardPanel.getBillModel().getRowCount(); row++) {
					this.getProgressObserve().setCurValue(curProgress);
					for (col = 0; col < bodyitems.length; col++) {
						if (bodyitems[col].isShow()) {
							CellInfo cellinfo = this.cellInfoMap
									.get(bodyitems[col].getKey() + "_b");

							wcfF = new jxl.write.WritableCellFormat(wf);
							wcfF.setBorder(jxl.format.Border.ALL,
									jxl.format.BorderLineStyle.THIN);

							if (pcardPanel.getBodyValueAt(row,
									bodyitems[col].getKey()) != null
									&& pcardPanel
											.getBodyValueAt(row,
													bodyitems[col].getKey())
											.toString().length() > 0
									|| bodyitems[col].getKey().indexOf(
											ExcelTempletBasedEntry.SHOWNAME) >= 0) {
								if (bodyitems[col].getDataType() == IBillItem.DECIMAL
										|| bodyitems[col].getDataType() == IBillItem.INTEGER) {
									if (cellinfo != null) {

										// 数字格式的话为了防住excel对精度的过滤，此处也有文本格式显示 add
										// by hulianga
										WritableCellFormat contentFromart = new WritableCellFormat(
												NumberFormats.TEXT);
										jxl.write.Label labelCFC2 = new jxl.write.Label(
												cellinfo.getiCol(),
												irow,
												pcardPanel
														.getBodyValueAt(
																row,
																bodyitems[col]
																		.getKey())
														.toString(),
												contentFromart);

										// jxl.write.WritableFont iwf =
										// new
										// jxl.write.WritableFont(WritableFont.TIMES,
										// 10);
										//
										// jxl.write.WritableCellFormat iwcfF =
										// new
										// jxl.write.WritableCellFormat(iwf);
										// iwcfF.setBorder(jxl.format.Border.ALL,
										// jxl.format.BorderLineStyle.THIN);
										// jxl.write.Number number =
										// new
										// jxl.write.Number(cellinfo.getiCol(),
										// irow,
										// new
										// UFDouble(pcardPanel.getBodyValueAt(row,
										// bodyitems[col].getKey()).toString())
										// .doubleValue());
										//
										// number.setCellFormat(iwcfF);
										this.ws.addCell(labelCFC2);

										if (cellinfo.getiCol() < this.startIdex) {
											this.ws.setColumnView(
													cellinfo.getiCol(),
													cellview);

										}
									}
								} else if (bodyitems[col].getDataType() == IBillItem.COMBO) {
									String value = bodyitems[col]
											.getValueObject() == null ? ""
											: bodyitems[col].getValueObject()
													.toString();
									if (value != null) {
										IConstEnum enumValue = (IConstEnum) ((UIComboBox) bodyitems[col]
												.getComponent())
												.getSelectedItem();
										value = (String) enumValue.getName();
									}
									labelCF = new jxl.write.Label(
											cellinfo.getiCol(), irow, value);
									this.ws.addCell(labelCF);

									if (cellinfo.getiCol() < this.startIdex) {
										this.ws.setColumnView(
												cellinfo.getiCol(), cellview);

									}
								} else {
									if (cellinfo != null) {
										if (bodyitems[col].getDataType() == IBillItem.UFREF
												|| bodyitems[col]
														.getKey()
														.indexOf(
																ExcelTempletBasedEntry.SHOWNAME) >= 0) {
											labelCF = new jxl.write.Label(
													cellinfo.getiCol(),
													irow,
													((nc.ui.pubapp.bill.BillCardPanel) pcardPanel)
															.getBodyDisplayValueAt(
																	row,
																	bodyitems[col]
																			.getKey())
															.toString());
										} else {
											labelCF = new jxl.write.Label(
													cellinfo.getiCol(), irow,
													pcardPanel.getBodyValueAt(
															row,
															bodyitems[col]
																	.getKey())
															.toString());

										}
										labelCF.setCellFormat(wcfF);
										this.ws.addCell(labelCF);

										if (cellinfo.getiCol() < this.startIdex) {
											this.ws.setColumnView(
													cellinfo.getiCol(),
													cellview);

										}
									}
								}
							}
						}
					}

					labelCF = new jxl.write.Label(0, irow, "ISUSED", wcfF);

					this.ws.addCell(labelCF);
					this.ws.setColumnView(0, cellview);
					curProgress++;
					iRow++;
					irow++;
				}
				this.getProgressObserve().setCurValue(maxprogress);
			}

			this.wwb.setProtected(false);
			this.ws.setProtected(false);
			this.wwb.write();

		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		} finally {
			// 关闭Excel工作薄对象
			this.wwb.close();
		}

	}

	public BaseDocAutoTranslator getBaseDocAutoTranslator() {
		if (this.baseDocAutoTranslator == null) {
			this.baseDocAutoTranslator = new BaseDocAutoTranslator();

		}
		return this.baseDocAutoTranslator;
	}

	public String getBodyClassName() {
		return this.bodyClassName;
	}

	public BillCardPanel getCardPanel() {
		return this.cardPanel;
	}

	public Map<String, CellInfo> getCellInfoMap() {
		return this.cellInfoMap;
	}

	public LoginContext getContext() {
		return this.context;
	}

	public IExcelImportProcess getExcelImportProcessor() {
		return this.excelImportProcessor;
	}

	public ExcelProcDlg getExcelProcDlg() {
		if (this.excelProcDlg == null) {
			this.excelProcDlg = new ExcelProcDlg(this.getContext()
					.getEntranceUI(), this.type, this, this.title);
		}
		return this.excelProcDlg;
	}

	public String getHeadClassName() {
		return this.headClassName;
	}

	public String getKeyWord() {
		return this.keyWord;
	}

	public IExcelProgress getProgressObserve() {
		return this.progressObserve;
	}

	public String getType() {
		return this.type;
	}

	public IVOColumnValueProcess getValueProcess() {
		return this.valueProcess;
	}

	@Override
	public void importExcel() throws Exception {
		// TODO Auto-generated method stub
		String filename = this.getUIFileChooser().getSelectedFile()
				.getAbsolutePath();
		File file = new File(filename);
		// 构建Workbook对象, 只读Workbook对象
		jxl.Workbook workbook = Workbook.getWorkbook(file);
		// 取得Excel工作表
		this.importws = workbook.getSheet(0);

		// 构造格式数据
		this.processCellInfo();
		// 解析数据值
		this.importHeadVO(this.importws);
		// 后台数据操作
		if (this.getExcelImportProcessor() != null) {
			this.getExcelImportProcessor().processAfterImport(this.importVO,
					null);

		}

	}

	public void setBodyClassName(String bodyClassName) {
		this.bodyClassName = bodyClassName;
	}

	public void setCardpanel(BillCardPanel panel) {
		this.cardPanel = panel;
	}

	public void setContext(LoginContext context) {
		this.context = context;
	}

	public void setDocCellInfo(List<DocCellInfo> docCellInfo) {
		this.getBaseDocAutoTranslator().setDocCellInfo(docCellInfo);
	}

	public void setExcelImportProcessor(IExcelImportProcess excelImportProcessor) {
		this.excelImportProcessor = excelImportProcessor;
	}

	public void setExcelProcDlg(ExcelProcDlg excelProcDlg) {
		this.excelProcDlg = excelProcDlg;
	}

	@Override
	public void setExcelProgressObserve(IExcelProgress progress) {
		this.progressObserve = progress;
		this.getBaseDocAutoTranslator()
				.setProgressObserve(this.progressObserve);

	}

	public void setHeadClassName(String headClassName) {
		this.headClassName = headClassName;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public void setPorocessBatched(boolean isBatched) {
		this.getBaseDocAutoTranslator().setBatched(isBatched);

	}

	public void setProgressObserve(IExcelProgress progressObserve) {
		this.progressObserve = progressObserve;

	}

	public void setType(String type) {
		this.type = type;
		this.getExcelProcDlg().setType(type);
	}

	public void setValueProcess(IVOColumnValueProcess valueProcess) {
		this.valueProcess = valueProcess;
	}

	public int showEntryUI() {
		if (this.getUIFileChooser().showDialog(
				this.context.getEntranceUI(),
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
						"pubapp_0", "0pubapp-0019")/* @res "确定" */) == javax.swing.JFileChooser.APPROVE_OPTION) {

			return this.getExcelProcDlg().showModal();

		}
		return UIDialog.ID_CANCEL;
	}

	protected int getHeadItemCount() {
		int count = 0;
		for (CellInfo info : this.cellInfoMap.values()) {
			if (!info.isbBody()) {
				count += 2;
			}

		}
		return count;
	}

	protected void init() {
		this.getExcelProcDlg().setType(this.type);
	}

	protected void processCellInfo() {
		// 清空历史数据
		this.cellInfoMap.clear();
		for (String key : getCode2colPost().keySet()) {
			CellInfoVO infoVO = new CellInfoVO();
			infoVO.setCol(startcol + getCode2colPost().get(key));
			infoVO.setRow(startrow);
			infoVO.setWidth(1);
			infoVO.setCellWidth(15);
			infoVO.setItemkey(key);
			infoVO.setIdcolname(null);
			infoVO.setBexport(true);
			infoVO.setDynamic(false);
			infoVO.setHide(false);
			infoVO.setBody(false);
			CellInfo info = new CellInfo(infoVO);
			this.cellInfoMap.put(key, info);
		}
	}

	protected void processCellInfo(BillItem[] items, int pos) {
		if (VOChecker.isEmpty(items)) {
			return;
		}
		int startcol = -1;
		int startrow = -1;
		int increment = 1;
		int width = 1;
		String subFix = "_h";
		boolean isBody = false;
		if (pos == IBillItem.HEAD) {
			startcol = this.startCol_h;
			startrow = ExcelTempletBasedEntry.IROWHEAD;
			width = 2;
			increment = 2;
			subFix = "_h";
			isBody = false;
		} else if (pos == IBillItem.BODY) {
			startcol = this.startCol_b;
			startrow = ExcelTempletBasedEntry.IROWBODYTITLE;
			width = 1;
			increment = 1;
			subFix = "_b";
			isBody = true;
		}
		for (BillItem item : items) {
			if (item.isShow() || item.getKey().equals(this.keyWord) && !isBody) {
				CellInfoVO infoVO = new CellInfoVO();
				if (item.getKey().equals(this.keyWord) && !isBody) {
					this.keyrow = ExcelTempletBasedEntry.IROWHEAD;
					this.keycol = startcol - 2;
					infoVO.setCol(this.keycol);

				} else {
					infoVO.setCol(startcol);
				}

				// infoVO.setCol(startcol);
				infoVO.setRow(startrow);
				infoVO.setWidth(width);
				infoVO.setCellWidth(15);
				infoVO.setItemkey(item.getKey());
				infoVO.setShowname(item.getName());
				infoVO.setIdcolname(null);
				infoVO.setBexport(true);
				infoVO.setDynamic(false);
				infoVO.setHide(false);
				infoVO.setDataType(item.getDataType());
				infoVO.setBody(isBody);
				CellInfo info = new CellInfo(infoVO);
				this.cellInfoMap.put(item.getKey() + subFix, info);
				if (item.getKey().equals(this.keyWord) && !isBody) {
					continue;
				}
				startcol += increment;
			}
		}

	}

	/**
	 * 对billitem中的showorder排序
	 * 
	 * @param items
	 */
	private void billItemSort(BillItem[] items) {
		Comparator<BillItem> comparator = new Comparator<BillItem>() {
			@Override
			public int compare(BillItem o1, BillItem o2) {
				if (o1.getShowOrder() < o2.getShowOrder()) {
					return -1;
				} else if (o1.getShowOrder() > o2.getShowOrder()) {
					return 1;
				}
				return 0;
			}
		};
		Arrays.sort(items, comparator);
	}

	private boolean checkFormat(Sheet pws) {
		boolean isOK = true;
		for (CellInfo cellInfo : this.cellInfoMap.values()) {
			if (cellInfo.isbBody()) {
				String valueCompare = pws.getCell(cellInfo.getiCol(), 2)
						.getContents().trim();
				if (!cellInfo.getShowname().equals(valueCompare)) {
					isOK = false;
					break;
				}
			}
		}
		return isOK;

	}

	private String getKeyWordFromSheet() throws Exception {
		if (StringUtils.isEmpty(this.importws.getCell(0, 1).getContents())
				|| StringUtils.isEmpty(this.importws.getCell(1, 1)
						.getContents())) {
			return null;
		}
		String value = null;
		try {
			int row = Integer.parseInt(this.importws.getCell(0, 1)
					.getContents().trim());
			int col = Integer.parseInt(this.importws.getCell(1, 1)
					.getContents().trim());
			value = this.importws.getCell(col, row).getContents().trim();
			if (value.indexOf(":") > 0) {
				value = value.substring(value.indexOf(":") + 1, value.length());
			}
		} catch (Exception e) {
			Log.error(e);
			throw new Exception(e);
		}

		return value;

	}

	private javax.swing.JFileChooser getUIFileChooser() {
		if (this.fcFileChooser == null) {
			try {
				this.fcFileChooser = new javax.swing.JFileChooser();
				MyFileFilter filter = new MyFileFilter();

				filter.addExtension("xls");
				filter.setDescription(nc.vo.ml.NCLangRes4VoTransl
						.getNCLangRes().getStrByID("pubapp_0", "0pubapp-0058")/*
																			 * @res
																			 * "Microsoft Excel 文件 "
																			 */);
				this.fcFileChooser.setFileFilter(filter);
				this.fcFileChooser.addChoosableFileFilter(filter);
				this.fcFileChooser.setSelectedFile(new File(this.title));

			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
			}
		}
		return this.fcFileChooser;
	}

	private void importBodyVO(Sheet pws) throws Exception {
		if (!this.checkFormat(pws)) {
			throw new Exception("格式有变动，无法导入。");/* -=notranslate=- */
		}
		List<CircularlyAccessibleValueObject> bodyVOList = new ArrayList<CircularlyAccessibleValueObject>();
		int maxValue = pws.getRows() - 3
				+ this.getBaseDocAutoTranslator().getDocCellInfo().size()
				* BaseDocAutoTranslator.DELAY;
		this.getProgressObserve().setMaxValue(maxValue);
		int curValue = 0;
		for (int row = 3; row < pws.getRows(); row++) {
			CircularlyAccessibleValueObject bodyVO = (CircularlyAccessibleValueObject) Class
					.forName(this.getBodyClassName()).newInstance();

			for (CellInfo cellInfo : this.cellInfoMap.values()) {
				if (cellInfo.isbBody()) {
					Cell cell = pws.getCell(cellInfo.getiCol(), row);
					if (cell.getContents() == null
							|| "".equals(cell.getContents())) {
						continue;
					}

					if (cellInfo.getiDataType() == IBillItem.STRING
							|| cellInfo.getiDataType() == IBillItem.UFREF) {
						String value = cell.getContents();
						if (this.getValueProcess() != null) {
							this.getValueProcess().valueProcess(bodyVO,
									IBillItem.BODY, cellInfo.getItemkey(),
									value);
						} else {
							bodyVO.setAttributeValue(cellInfo.getItemkey(),
									value);
						}
					} else if (cellInfo.getiDataType() == IBillItem.DECIMAL
							|| cellInfo.getiDataType() == IBillItem.INTEGER
							|| cellInfo.getiDataType() == IBillItem.MONEY) {
						UFDouble value;
						if (cell.getType() == CellType.NUMBER
								&& cellInfo.getiDataType() != IBillItem.INTEGER) {
							NumberCell ncell = (NumberCell) cell;
							value = new UFDouble(ncell.getValue());
						} else {
							value = new UFDouble(cell.getContents().trim());
						}
						if (this.getValueProcess() != null) {
							this.getValueProcess().valueProcess(bodyVO,
									IBillItem.BODY, cellInfo.getItemkey(),
									value);
						} else {
							bodyVO.setAttributeValue(cellInfo.getItemkey(),
									value);
						}

					} else if (cellInfo.getiDataType() == IBillItem.DATE) {
						UFDate value = new UFDate(cell.getContents());
						if (this.getValueProcess() != null) {
							this.getValueProcess().valueProcess(bodyVO,
									IBillItem.BODY, cellInfo.getItemkey(),
									value);
						} else {
							bodyVO.setAttributeValue(cellInfo.getItemkey(),
									value);
						}
					}

				}

			}
			bodyVOList.add(bodyVO);
			curValue++;
			this.getProgressObserve().setCurValue(curValue);

		}
		// getBaseDocAutoTranslator().setCurProgressValue(curValue);

		this.importbodyVO = bodyVOList
				.toArray((CircularlyAccessibleValueObject[]) Array.newInstance(
						Class.forName(this.getBodyClassName()),
						bodyVOList.size()));

	}

	private void importHeadVO(Sheet pws) throws Exception {
		Integer rows = pws.getRows();
		List<CircularlyAccessibleValueObject> headVOList = new ArrayList<CircularlyAccessibleValueObject>();
		end: for (int i = startrow; i < rows; i++) {
			CircularlyAccessibleValueObject headVO = (CircularlyAccessibleValueObject) Class
					.forName(this.getHeadClassName()).newInstance();
			for (CellInfo cellInfo : this.cellInfoMap.values()) {
				// 获取页签数据(col,row)
				String value = pws.getCell(cellInfo.getiCol(), i)
						.getContents().trim();
				if ("pk_org".equals(cellInfo.getItemkey())) {
					if (value == null || "".equals(value)) {
						break end;
					}
				}
				if (this.getValueProcess() != null) {
					this.getValueProcess().valueProcess(headVO, IBillItem.HEAD,
							cellInfo.getItemkey(), value);
				} else {
					headVO.setAttributeValue(cellInfo.getItemkey(), value);
				}
			}
			headVOList.add(headVO);
		}

		this.importVO = headVOList
				.toArray((CircularlyAccessibleValueObject[]) Array.newInstance(
						Class.forName(this.getHeadClassName()),
						headVOList.size()));

	}

	private void processKeyPos() throws Exception {
		CellView cellview = new CellView();
		cellview.setHidden(true);
		jxl.write.WritableFont wf = new jxl.write.WritableFont(
				WritableFont.TIMES, 10);
		jxl.write.WritableCellFormat wcfF = new jxl.write.WritableCellFormat(wf);
		jxl.write.Label labelCF = new jxl.write.Label(0, 1,
				String.valueOf(this.keyrow), wcfF);
		this.ws.addCell(labelCF);
		this.ws.setColumnView(0, cellview);
		labelCF = new jxl.write.Label(1, 1, String.valueOf(this.keycol), wcfF);
		this.ws.addCell(labelCF);
		this.ws.setColumnView(1, cellview);

	}
}
