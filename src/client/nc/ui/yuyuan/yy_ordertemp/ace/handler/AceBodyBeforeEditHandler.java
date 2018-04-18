package nc.ui.yuyuan.yy_ordertemp.ace.handler;

import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardBodyBeforeEditEvent;

/**
 * 表体字段编辑前事件处理类
 * 
 * @since 6.0
 * @version 2011-7-7 下午02:52:57
 * @author duy
 */
public class AceBodyBeforeEditHandler implements
		IAppEventHandler<CardBodyBeforeEditEvent> {

	@Override
	public void handleAppEvent(CardBodyBeforeEditEvent e) {
		e.setReturnValue(Boolean.TRUE);
		// 物料
		String key = e.getKey();
		if (key.equals("cmaterialvid")) {
			MaterialHandler handler = new MaterialHandler();
			handler.beforeEdit(e);
		}

	}
}