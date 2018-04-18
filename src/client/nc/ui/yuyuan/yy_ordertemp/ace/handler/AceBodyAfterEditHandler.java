package nc.ui.yuyuan.yy_ordertemp.ace.handler;

import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterEditEvent;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.yy_ordertemp.YyOrderTempBVO;

/**
 * 单据表体字段编辑后事件
 * 
 * @since 6.0
 * @version 2011-7-12 下午08:17:33
 * @author duy
 */
public class AceBodyAfterEditHandler implements
		IAppEventHandler<CardBodyAfterEditEvent> {

	@Override
	public void handleAppEvent(CardBodyAfterEditEvent e) {
		String tabcode = e.getTableCode();
		if (null != tabcode
				&& !tabcode.equals(e.getBillCardPanel()
						.getCurrentBodyTableCode())) {
			e.getBillCardPanel().setBodyValueAt(e.getOldValue(), e.getRow(),
					e.getKey(), tabcode);
		} else {
			/**
			 * 校验界面数据是否有相同物料
			 */
			if ("cmaterialvid".equals(e.getKey())) {
				String value = e.getValue().toString();
				int count = e.getBillCardPanel().getBillTable(tabcode)
						.getRowCount();
				for (int i = 0; i < count; i++) {
					if (i == e.getRow()) {
						continue;
					}
					Object obj = e.getBillCardPanel().getBodyValueAt(i,
							"cmaterialvid");
					String param = obj == null ? "" : obj.toString();
					if (value.equals(param)) {
						Object obj1 = e.getBillCardPanel().getBodyValueAt(i,
								"crowno");
						String repeat_crowno = obj1 == null ? "" : obj1
								.toString();
						// 排查到重复物料，清空行数据值-保留pk_org,pk_group,crowno
						String pk_group = e.getBillCardPanel().getBodyValueAt(
								e.getRow(), "pk_group") == null ? "" : e
								.getBillCardPanel()
								.getBodyValueAt(e.getRow(), "pk_group")
								.toString();
						String pk_org = e.getBillCardPanel().getBodyValueAt(
								e.getRow(), "pk_org") == null ? "" : e
								.getBillCardPanel()
								.getBodyValueAt(e.getRow(), "pk_org")
								.toString();
						String crowno = e.getBillCardPanel().getBodyValueAt(
								e.getRow(), "crowno") == null ? "" : e
								.getBillCardPanel()
								.getBodyValueAt(e.getRow(), "crowno")
								.toString();
						YyOrderTempBVO bvo = new YyOrderTempBVO();
						bvo.setPk_group(pk_group);
						bvo.setPk_org(pk_org);
						bvo.setCrowno(crowno);
						e.getBillCardPanel().getBillModel()
								.setBodyRowVO(bvo, e.getRow());
						ExceptionUtils
								.wrappBusinessException("表体物料不能重复,重复数据行号:"
										+ repeat_crowno);
						return;
					}

				}
				MaterialHandler handler = new MaterialHandler();
				handler.afterEdit(e);
			}
		}
	}

}
