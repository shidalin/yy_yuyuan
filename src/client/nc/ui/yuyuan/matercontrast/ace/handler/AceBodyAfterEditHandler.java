package nc.ui.yuyuan.matercontrast.ace.handler;

import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterEditEvent;

/**
 * ���ݱ����ֶα༭���¼�
 * 
 * @since 6.0
 * @version 2011-7-12 ����08:17:33
 * @author duy
 */
public class AceBodyAfterEditHandler implements
		IAppEventHandler<CardBodyAfterEditEvent> {

	@Override
	public void handleAppEvent(CardBodyAfterEditEvent e) {
		String key = e.getKey();
		if("cmaterialvid".equals(key)){
		MaterialHandler handler = new MaterialHandler();
		handler.afterEdit(e);
		}
	}

}
