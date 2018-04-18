package nc.ui.ct.purdaily.editor.after;

import java.util.Map;

import nc.ui.ct.editor.after.body.ChangeRate;
import nc.ui.ct.editor.after.body.CqtUnitId;
import nc.ui.ct.editor.after.body.CtTaxcode;
import nc.ui.ct.editor.after.body.DelivDate;
import nc.ui.ct.editor.after.body.Term;
import nc.ui.ct.editor.handler.AbstractBodyAfterEditEventHandler;
import nc.ui.ct.editor.listener.AbstractRelationCalculateListener;
import nc.ui.ct.editor.listener.IBodyAfterEditEventListener;
import nc.ui.ct.purdaily.editor.after.body.CTPuArrvstock;
import nc.ui.ct.purdaily.editor.after.body.CTPuCountry;
import nc.ui.ct.purdaily.editor.after.body.CTPuFinanceorg;
import nc.ui.ct.purdaily.editor.after.body.CTPuMaterial;
import nc.ui.ct.purdaily.editor.after.body.PuCastUnitId;
import nc.ui.ct.purdaily.editor.after.body.PuMarBasClass;
import nc.ui.ct.purdaily.editor.after.body.ctpayment.CTCheckDate;
import nc.ui.ct.purdaily.editor.after.body.ctpayment.CTEffectAddMonth;
import nc.ui.ct.purdaily.editor.after.body.ctpayment.CTEffectMonth;
import nc.ui.ct.purdaily.editor.after.body.ctpayment.CTOutAccountDate;
import nc.ui.ct.purdaily.editor.after.body.ctpayment.CTPaymentDay;
import nc.ui.pub.bill.BillItem;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterEditEvent;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.scmpub.res.billtype.CTBillType;

/**
 * 采购合同表体编辑后事件
 * 
 * @author shidl
 * 
 */
public class PuBodyAfterEventHandler extends AbstractBodyAfterEditEventHandler {
	public AbstractRelationCalculateListener getCalculateListener() {
		return null;
	}

	public void handleAppEvent(CardBodyAfterEditEvent e) {
		String tabcode = e.getTableCode();
		String pk_org = e.getBillCardPanel().getHeadItem("pk_org") == null ? ""
				: e.getBillCardPanel().getHeadItem("pk_org").getValue();
		String code = nc.pub.yuyuan.utils.OrgUtil.getCode(pk_org);
		if (null != tabcode
				&& !tabcode.equals(e.getBillCardPanel()
						.getCurrentBodyTableCode())) {
			e.getBillCardPanel().setBodyValueAt(e.getOldValue(), e.getRow(),
					e.getKey(), tabcode);
		} else if (!"01".equals(code)) {
			/**
			 * 校验界面数据是否有相同物料
			 */
			if ("pk_material".equals(e.getKey())) {
				String value = e.getValue().toString();
				int count = e.getBillCardPanel().getBillTable(tabcode)
						.getRowCount();
				for (int i = 0; i < count; i++) {
					if (i != e.getRow()) {
						Object obj = e.getBillCardPanel().getBodyValueAt(i,
								"pk_material");
						String param = obj == null ? "" : obj.toString();
						if (value.equals(param)) {
							Object obj1 = e.getBillCardPanel().getBodyValueAt(
									i, "crowno");
							String crowno = obj1 == null ? "" : obj1.toString();
							ExceptionUtils
									.wrappBusinessException("表体物料不能重复,重复数据行号:"
											+ i);
							return;
						}
					}
				}
			}
		}
		super.handleAppEvent(e);
	}

	// }

	public void registerEventListener(
			Map<String, IBodyAfterEditEventListener> listenerMap) {
		listenerMap.put("delivdate", new DelivDate());
		listenerMap.put("pk_material", new CTPuMaterial());
		listenerMap.put("pk_marbasclass", new PuMarBasClass());
		listenerMap.put("vqtunitrate", new ChangeRate("vqtunitrate"));
		listenerMap.put("vchangerate", new ChangeRate("vchangerate"));
		listenerMap.put("castunitid", new PuCastUnitId());
		listenerMap.put("cqtunitid", new CqtUnitId());
		listenerMap.put("csendcountryid", new CTPuCountry("csendcountryid"));
		listenerMap.put("crececountryid", new CTPuCountry("crececountryid"));
		listenerMap.put("ctaxcountryid", new CTPuCountry("ctaxcountryid"));
		listenerMap.put("ctaxcodeid",
				new CtTaxcode(CTBillType.PurDaily.getCode()));
		listenerMap.put("pk_financeorg_v", new CTPuFinanceorg());
		listenerMap.put("pk_arrvstock_v", new CTPuArrvstock());
		listenerMap.put("outaccountdate", new CTOutAccountDate());
		listenerMap.put("paymentday", new CTPaymentDay());
		listenerMap.put("checkdata", new CTCheckDate());
		listenerMap.put("effectmonth", new CTEffectMonth());
		listenerMap.put("effectaddmonth", new CTEffectAddMonth());
		listenerMap.put("vtermcode", new Term());
	}
}