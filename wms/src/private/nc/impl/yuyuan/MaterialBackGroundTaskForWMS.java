package nc.impl.yuyuan;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import nc.bs.pub.pa.PreAlertObject;
import nc.bs.pub.pa.PreAlertReturnType;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.bs.pub.taskcenter.IBackgroundWorkPlugin;
import nc.impl.yuyuan.bp.MaterialBPForWMS;
import nc.pub.yuyuan.util.AES;
import nc.pub.yuyuan.util.FileUtil;
import nc.vo.mmpps.calc.entity.task.CalcBusiVarCalculater;
import nc.vo.mmpps.calc.entity.task.MsgCreator;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.MultiLangText;
import nc.vo.pub.lang.UFDate;
import nc.vo.yuyuan.MaterialDTOForWMS;

import com.manystar.dms.cxf.incident.DmsIncidentServicePortTypeProxy;

/**
 * 对接WMS,物料档案后台任务
 * 
 * @author shidalin
 * 
 */
public class MaterialBackGroundTaskForWMS implements IBackgroundWorkPlugin {

	// 分批传送
	int pageSize = 200;

	@Override
	public PreAlertObject executeTask(BgWorkingContext arg0)
			throws BusinessException {
		MaterialBPForWMS materialBPForWMS = new MaterialBPForWMS();
		List<MaterialDTOForWMS> list = materialBPForWMS
				.queryMaterialForWMS(null);
		try {
			BatchSendingWMS(materialBPForWMS, list);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return createReturnObject(e.getMessage());
			// ExceptionUtils.marsh(e);
		}

		return createReturnObject("物料档案数据发送成功");
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
	 * 分批传送数据
	 * 
	 * @param materialBPForWMS
	 * @param list
	 * @throws BusinessException
	 * @throws RemoteException
	 */
	public void BatchSendingWMS(MaterialBPForWMS materialBPForWMS,
			List<MaterialDTOForWMS> list) throws BusinessException,
			RemoteException {
		DmsIncidentServicePortTypeProxy dmsIncidentServicePortTypeProxy = new DmsIncidentServicePortTypeProxy();
		if (list != null && list.size() > 0) {
			if (list.size() > pageSize) {
				int page = list.size() / pageSize;
				int lastSize = list.size() % pageSize;
				MaterialDTOForWMS[] srcarray = list
						.toArray(new MaterialDTOForWMS[0]);
				for (int i = 0; i < page; i++) {
					MaterialDTOForWMS[] destarray = new MaterialDTOForWMS[pageSize];
					int srcpos = i * pageSize;
					System.arraycopy(srcarray, srcpos, destarray, 0, pageSize);
					List<MaterialDTOForWMS> paramList = Arrays
							.asList(destarray);
					String sendJson = materialBPForWMS.getSendJson(paramList);
					// 测试文本
					FileUtil fileUtil = new FileUtil();
					fileUtil.outputFile(sendJson, "物料非密数据_" + i, "");
					// 数据加密
					sendJson = AES.encode(sendJson);
					fileUtil.outputFile(sendJson, "物料加密数据_" + i, "/dev");
					// 调用接口发送
					dmsIncidentServicePortTypeProxy
							.dmsProductAnalysis(sendJson);
				}
				if (lastSize > 0) {
					MaterialDTOForWMS[] destarray = new MaterialDTOForWMS[lastSize];
					int srcpos = page * pageSize;
					System.arraycopy(srcarray, srcpos, destarray, 0, lastSize);
					List<MaterialDTOForWMS> paramList = Arrays
							.asList(destarray);
					String sendJson = materialBPForWMS.getSendJson(paramList);
					// 测试文本
					// 测试文本
					FileUtil fileUtil = new FileUtil();
					fileUtil.outputFile(sendJson, "物料非密数据_" + page, "");
					// 数据加密
					sendJson = AES.encode(sendJson);
					fileUtil.outputFile(sendJson, "物料加密数据_" + page, "/dev");
					// 调用接口发送
					dmsIncidentServicePortTypeProxy
							.dmsProductAnalysis(sendJson);
				}

			} else {
				String sendJson = materialBPForWMS.getSendJson(list);
				// 测试文本
				FileUtil fileUtil = new FileUtil();
				fileUtil.outputFile(sendJson, "物料非密数据_", "");
				// 数据加密
				sendJson = AES.encode(sendJson);
				fileUtil.outputFile(sendJson, "物料加密数据_", "/dev");
				// 调用接口发送
				dmsIncidentServicePortTypeProxy.dmsProductAnalysis(sendJson);
			}
		}
	}
}
