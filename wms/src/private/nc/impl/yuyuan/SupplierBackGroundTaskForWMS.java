package nc.impl.yuyuan;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import nc.bs.pub.pa.PreAlertObject;
import nc.bs.pub.pa.PreAlertReturnType;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.bs.pub.taskcenter.IBackgroundWorkPlugin;
import nc.impl.yuyuan.bp.SupplierBPForWMS;
import nc.pub.yuyuan.util.AES;
import nc.pub.yuyuan.util.FileUtil;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.MultiLangText;
import nc.vo.pub.lang.UFDate;
import nc.vo.yuyuan.SupplierDTOForWMS;

import com.manystar.dms.cxf.incident.DmsIncidentServicePortTypeProxy;

/**
 * 对接WMS,供应商后台任务
 * 
 * @author shidalin
 * 
 */
public class SupplierBackGroundTaskForWMS implements IBackgroundWorkPlugin {

	// 分批传送
	int pageSize = 200;

	@Override
	public PreAlertObject executeTask(BgWorkingContext arg0)
			throws BusinessException {

		SupplierBPForWMS supplierBPForWMS = new SupplierBPForWMS();
		try {
			List<SupplierDTOForWMS> list = supplierBPForWMS
					.querySupplierForWMS(null); // 分批发送数据
			try {
				BatchSendingWMS(supplierBPForWMS, list);
			} catch (RemoteException e) {
				e.printStackTrace();
				return createReturnObject(e.getMessage());
			}

		} catch (BusinessException e) {
			e.printStackTrace();
			return createReturnObject(e.getMessage());
		}

		return createReturnObject("供应商数据发送成功");
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
	public void BatchSendingWMS(SupplierBPForWMS supplierBPForWMS,
			List<SupplierDTOForWMS> list) throws BusinessException,
			RemoteException {
		DmsIncidentServicePortTypeProxy dmsIncidentServicePortTypeProxy = new DmsIncidentServicePortTypeProxy();
		if (list != null && list.size() > 0) {
			if (list.size() > pageSize) {
				int page = list.size() / pageSize;
				int lastSize = list.size() % pageSize;
				SupplierDTOForWMS[] srcarray = list
						.toArray(new SupplierDTOForWMS[0]);
				for (int i = 0; i < page; i++) {
					SupplierDTOForWMS[] destarray = new SupplierDTOForWMS[pageSize];
					int srcpos = i * pageSize;
					System.arraycopy(srcarray, srcpos, destarray, 0, pageSize);
					List<SupplierDTOForWMS> paramList = Arrays
							.asList(destarray);
					String sendJson = supplierBPForWMS.getSendJson(paramList);
					// 测试文本
					FileUtil fileUtil = new FileUtil();
					fileUtil.outputFile(sendJson, "供应商非密数据_" + i, "");
					// 数据加密
					sendJson = AES.encode(sendJson);
					fileUtil.outputFile(sendJson, "供应商加密数据_" + i, "/dev");
					// 调用接口发送
					dmsIncidentServicePortTypeProxy
							.dmsSupplierAnalysis(sendJson);
				}
				if (lastSize > 0) {
					SupplierDTOForWMS[] destarray = new SupplierDTOForWMS[lastSize];
					int srcpos = page * pageSize;
					System.arraycopy(srcarray, srcpos, destarray, 0, lastSize);
					List<SupplierDTOForWMS> paramList = Arrays
							.asList(destarray);
					String sendJson = supplierBPForWMS.getSendJson(paramList);
					// 测试文本
					FileUtil fileUtil = new FileUtil();
					fileUtil.outputFile(sendJson, "供应商非密数据_" + page, "");
					// 数据加密
					sendJson = AES.encode(sendJson);
					fileUtil.outputFile(sendJson, "供应商加密数据_" + page, "/dev");
					// 调用接口发送
					dmsIncidentServicePortTypeProxy
							.dmsSupplierAnalysis(sendJson);
				}

			} else {
				String sendJson = supplierBPForWMS.getSendJson(list);
				// 测试文本
				FileUtil fileUtil = new FileUtil();
				fileUtil.outputFile(sendJson, "供应商非密数据_", "");
				// 数据加密
				sendJson = AES.encode(sendJson);
				fileUtil.outputFile(sendJson, "供应商加密数据_", "/dev");
				// 调用接口发送
				dmsIncidentServicePortTypeProxy.dmsSupplierAnalysis(sendJson);
			}
		}
	}

}
