package nc.impl.yuyuan;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import nc.bs.pub.pa.PreAlertObject;
import nc.bs.pub.pa.PreAlertReturnType;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.bs.pub.taskcenter.IBackgroundWorkPlugin;
import nc.impl.yuyuan.bp.PoOrderBPForWMS;
import nc.pub.yuyuan.util.AES;
import nc.pub.yuyuan.util.FileUtil;
import nc.vo.mmpps.calc.entity.task.CalcBusiVarCalculater;
import nc.vo.mmpps.calc.entity.task.MsgCreator;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.MultiLangText;
import nc.vo.pub.lang.UFDate;
import nc.vo.yuyuan.PoOrderDTOForWMS;

import com.manystar.dms.cxf.incident.DmsIncidentServicePortTypeProxy;

/**
 * �Խ�WMS,�ɹ�������̨����
 * 
 * @author shidalin
 * 
 */
public class PoOrderBackGroundTaskForWMS implements IBackgroundWorkPlugin {

	// ��������
	int pageSize = 200;

	@Override
	public PreAlertObject executeTask(BgWorkingContext arg0)
			throws BusinessException {
		PoOrderBPForWMS poOrderBPForWMS = new PoOrderBPForWMS();
		try {
			List<PoOrderDTOForWMS> list = poOrderBPForWMS
					.queryPoOrderForWMS(null);
			// ������������
			try {
				BatchSendingWMS(poOrderBPForWMS, list);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return createReturnObject(e.getMessage());
			}

		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return createReturnObject(e.getMessage());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return createReturnObject(e.getMessage());
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return createReturnObject(e.getMessage());
		}
		return createReturnObject("�ɹ��������ݷ��ͳɹ�");
	}

	private PreAlertObject createReturnObject(String message) {
		PreAlertObject obj = new PreAlertObject();
		MultiLangText text = new MultiLangText();
		text.setText(message + "_" + new UFDate());
		obj.setReturnType(PreAlertReturnType.RETURNMULTILANGTEXT);
		obj.setReturnObj(text);
		return obj;
	}

	/**
	 * ������������
	 * 
	 * @param materialBPForWMS
	 * @param list
	 * @throws BusinessException
	 * @throws RemoteException
	 */
	public void BatchSendingWMS(PoOrderBPForWMS poOrderBPForWMS,
			List<PoOrderDTOForWMS> list) throws BusinessException,
			RemoteException {
		DmsIncidentServicePortTypeProxy dmsIncidentServicePortTypeProxy = new DmsIncidentServicePortTypeProxy();
		if (list != null && list.size() > 0) {
			if (list.size() > pageSize) {
				int page = list.size() / pageSize;
				int lastSize = list.size() % pageSize;
				PoOrderDTOForWMS[] srcarray = list
						.toArray(new PoOrderDTOForWMS[0]);
				for (int i = 0; i < page; i++) {
					PoOrderDTOForWMS[] destarray = new PoOrderDTOForWMS[pageSize];
					int srcpos = i * pageSize;
					System.arraycopy(srcarray, srcpos, destarray, 0, pageSize);
					List<PoOrderDTOForWMS> paramList = Arrays.asList(destarray);
					String sendJson = poOrderBPForWMS.getSendJson(paramList);
					// �����ı�
					FileUtil fileUtil = new FileUtil();
					fileUtil.outputFile(sendJson, "������������_" + i, "");
					// ���ݼ���
					sendJson = AES.encode(sendJson);
					fileUtil.outputFile(sendJson, "������������_" + i, "/dev");
					// ���ýӿڷ���
					dmsIncidentServicePortTypeProxy
							.dmsOmsOrderAnalysis(sendJson);
				}
				if (lastSize > 0) {
					PoOrderDTOForWMS[] destarray = new PoOrderDTOForWMS[lastSize];
					int srcpos = page * pageSize;
					System.arraycopy(srcarray, srcpos, destarray, 0, lastSize);
					List<PoOrderDTOForWMS> paramList = Arrays.asList(destarray);
					String sendJson = poOrderBPForWMS.getSendJson(paramList);
					// �����ı�
					FileUtil fileUtil = new FileUtil();
					fileUtil.outputFile(sendJson, "������������_" + page, "");
					// ���ݼ���
					sendJson = AES.encode(sendJson);
					fileUtil.outputFile(sendJson, "������������_" + page, "/dev");
					// ���ýӿڷ���
					dmsIncidentServicePortTypeProxy
							.dmsOmsOrderAnalysis(sendJson);
				}

			} else {
				String sendJson = poOrderBPForWMS.getSendJson(list);
				// �����ı�
				FileUtil fileUtil = new FileUtil();
				fileUtil.outputFile(sendJson, "������������_", "");
				// ���ݼ���
				sendJson = AES.encode(sendJson);
				fileUtil.outputFile(sendJson, "������������_", "/dev");
				// ���ýӿڷ���
				dmsIncidentServicePortTypeProxy.dmsOmsOrderAnalysis(sendJson);
			}
		}
	}

}
