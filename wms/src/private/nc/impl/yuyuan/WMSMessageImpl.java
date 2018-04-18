package nc.impl.yuyuan;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.framework.common.NCLocator;
import nc.impl.yuyuan.bp.MaterialBPForWMS;
import nc.impl.yuyuan.bp.PoOrderBPForWMS;
import nc.impl.yuyuan.bp.SupplierBPForWMS;
import nc.itf.yunyuan.IWMSMessage;
import nc.pub.yuyuan.util.AES;
import nc.pub.yuyuan.util.FileUtil;
import nc.util.mmpub.dpub.db.SqlInUtil;
import nc.vo.bd.material.MaterialVO;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.pu.m21.entity.OrderHeaderVO;
import nc.vo.pu.m21.entity.OrderVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pubapp.AppContext;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.MaterialDTOForWMS;
import nc.vo.yuyuan.PoOrderDTOForWMS;
import nc.vo.yuyuan.SupplierDTOForWMS;
import nc.vo.yuyuan.WMSMessageVO;
import nc.vo.yuyuan.yy_errorlog.YyErrorLogVO;

import com.alibaba.fastjson.JSONObject;

/**
 * WMS�ӿڻش���Ϣ����
 * 
 * @author shidalin
 * 
 */
public class WMSMessageImpl implements IWMSMessage {
	String billTypeForMaterial = "bd_material";
	String billTypeForSupplier = "bd_supplier";
	String billTypeForOrder = "po_order";

	@Override
	public String messageProcessor(String message) throws BusinessException {
		if (message != null && !"".equals(message)) {
			FileUtil fileUtil = new FileUtil();
			fileUtil.outputFile(message, "��ִ��������" + new UFDate().getMillis(),"/dev");
			message = AES.decode(message);
			fileUtil.outputFile(message, "��ִ��������" + new UFDate().getMillis(),"");
			// ����ת��
			List<WMSMessageVO> wmlist = JSONObject.parseArray(message,
					WMSMessageVO.class);
			if (wmlist != null && wmlist.size() > 0) {
				ArrayList<YyErrorLogVO> errorLogList = new ArrayList<YyErrorLogVO>();
				ArrayList<String> listForMaterial = new ArrayList<String>();
				ArrayList<String> listForSupplier = new ArrayList<String>();
				ArrayList<String> listForOrder = new ArrayList<String>();
				for (WMSMessageVO wmVO : wmlist) {
					if (wmVO.getStatus()) {
						// ���ͳɹ���д�ѷ��ͱ�־
						String type = wmVO.getType() == null ? "" : wmVO
								.getType();
						String billno = wmVO.getBillno() == null ? "" : wmVO
								.getBillno();
						if (billTypeForMaterial.equals(type)) {
							// ����
							listForMaterial.add(billno);
						}
						if (billTypeForSupplier.equals(type)) {
							// ��Ӧ��
							listForSupplier.add(billno);
						}
						if (billTypeForOrder.equals(type)) {
							// �ɹ�����
							listForOrder.add(billno);
						}
					} else {
						YyErrorLogVO yyErrorLogVO = createErrorLogVO(wmVO);
						errorLogList.add(yyErrorLogVO);
					}
				}
				writeBack(listForMaterial, billTypeForMaterial);
				writeBack(listForSupplier, billTypeForSupplier);
				writeBack(listForOrder, billTypeForOrder);
				// ���ú�̨�ӿڴ洢������Ϣ,��������ӿ�
				if (errorLogList.size() > 0) {
					NCLocator.getInstance()
							.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
							.insertLogVO_RequiresNew(errorLogList);
				}

			}

		}
		return "�ɹ�����";
	}

	/**
	 * ��д��־
	 * 
	 * @param billno
	 * @throws BusinessException
	 */
	private void writeBack(ArrayList<String> list, String billType)
			throws BusinessException {
		String flagForMeterial = "def20";
		String flagForSupplier = "def20";
		String flagForOrder = "vdef20";
		BaseDAO baseDAO = new BaseDAO();
		UFDate nowDate = new UFDate();

		if (billTypeForMaterial.equals(billType)) {
			if (list != null && list.size() > 0) {
				String updateSql = "update bd_material t set t."
						+ flagForMeterial + "='Y' , t.def18 = '"
						+ nowDate.toString() + "' where t.code  ";
				SqlInUtil sqlInUtil = new SqlInUtil(list.toArray(new String[0]));
				String inSql = sqlInUtil.getInSql();
				updateSql += inSql;
				baseDAO.executeUpdate(updateSql);
			}
		}
		if (billTypeForSupplier.equals(billType)) {
			if (list != null && list.size() > 0) {
				String updateSql = "update bd_supplier t set t."
						+ flagForSupplier + "='Y' , t.def18 = '"
						+ nowDate.toString() + "' where t.code  ";
				SqlInUtil sqlInUtil = new SqlInUtil(list.toArray(new String[0]));
				String inSql = sqlInUtil.getInSql();
				updateSql += inSql;
				baseDAO.executeUpdate(updateSql);
			}
		}
		if (billTypeForOrder.equals(billType)) {
			if (list != null && list.size() > 0) {
				ArrayList<String> paramList = new ArrayList<String>();
				for (String param : list) {
					String ordercode = param.split("_")[0];
					paramList.add(ordercode);
				}
				String updateSql = "update po_order t set t." + flagForOrder
						+ "='Y' , t.vdef18 = '" + nowDate.toString()
						+ "' where t.vbillcode  ";
				SqlInUtil sqlInUtil = new SqlInUtil(
						paramList.toArray(new String[0]));
				String inSql = sqlInUtil.getInSql();
				updateSql += inSql;
				baseDAO.executeUpdate(updateSql);
			}
		}
	}

	/**
	 * ����������־��Ϣ
	 * 
	 * @param wmVO
	 * @return
	 */
	private YyErrorLogVO createErrorLogVO(WMSMessageVO wmVO) {
		YyErrorLogVO yyErrorLogVO = new YyErrorLogVO();
		yyErrorLogVO.setBill(wmVO.getType());
		if (billTypeForOrder.equals(wmVO.getType())) {
			yyErrorLogVO.setBillno(wmVO.getBillno().split("_")[0]);
			yyErrorLogVO.setBillbno(wmVO.getBillno().split("_")[1]);
		} else {
			yyErrorLogVO.setBillno(wmVO.getBillno());
		}
		yyErrorLogVO.setBilldate(AppContext.getInstance().getBusiDate());
		yyErrorLogVO.setBillmaker("WMS");
		yyErrorLogVO.setErrorinfo(wmVO.getData());
		yyErrorLogVO.setErrortyle(wmVO.getMessage());
		return yyErrorLogVO;
	}

	@Override
	public void sendToWMSForMaterial(ArrayList<MaterialVO> arrayList)
			throws BusinessException {
		// TODO Auto-generated method stub
		// ����ѡ�����ϲ�ѯ������Ϣ
		StringBuffer stringBuffer = new StringBuffer();
		// ƴ������������ѯ����
		for (MaterialVO materialVO : arrayList) {
			stringBuffer.append("'" + materialVO.getPk_material() + "',");
		}
		// ȥ�����һ������
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);

		// ����MaterialBPForWMS��ѯ������Ϣ
		MaterialBPForWMS materialBPForWMS = new MaterialBPForWMS();
		List<MaterialDTOForWMS> list = materialBPForWMS
				.queryMaterialForWMS(stringBuffer.toString());
		try {
			if (list==null || list.size() == 0) {
				throw new BusinessException("δ��ѯ�����㷢������������");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			ExceptionUtils.marsh(e1);
		}
		// ����MaterialBackGroundTaskForWMS��������
		MaterialBackGroundTaskForWMS materialBackGroundTaskForWMS = new MaterialBackGroundTaskForWMS();
		try {
			materialBackGroundTaskForWMS
					.BatchSendingWMS(materialBPForWMS, list);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ExceptionUtils.marsh(e);
		}

	}

	@Override
	public void sendToWMSForSupplier(ArrayList<SupplierVO> arrayList)
			throws BusinessException {
		// TODO Auto-generated method stub
		// ����ѡ�й�Ӧ�̲�ѯ������Ϣ
		StringBuffer stringBuffer = new StringBuffer();
		// ƴ�ӹ�Ӧ��������ѯ����
		for (SupplierVO supplierVO : arrayList) {
			stringBuffer.append("'" + supplierVO.getPk_supplier() + "',");
		}
		// ȥ�����һ������
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);

		// ����SupplierBPForWMS��ѯ������Ϣ
		SupplierBPForWMS supplierBPForWMS = new SupplierBPForWMS();
		List<SupplierDTOForWMS> list = supplierBPForWMS
				.querySupplierForWMS(stringBuffer.toString());
		try {
			if (list==null || list.size() == 0) {
				throw new BusinessException("δ��ѯ�����㷢������������");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			ExceptionUtils.marsh(e1);
		}
		// ����SupplierBackGroundTaskForWMS��������
		SupplierBackGroundTaskForWMS supplierBackGroundTaskForWMS = new SupplierBackGroundTaskForWMS();
		try {
			supplierBackGroundTaskForWMS
					.BatchSendingWMS(supplierBPForWMS, list);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ExceptionUtils.marsh(e);
		}

	}

	@Override
	public void sendToWMSForPoOrder(ArrayList<OrderVO> arrayList)
			throws BusinessException, IllegalAccessException,
			InvocationTargetException {
		// TODO Auto-generated method stub
		// ����ѡ�й�Ӧ�̲�ѯ������Ϣ
		StringBuffer stringBuffer = new StringBuffer();
		// ƴ�ӹ�Ӧ��������ѯ����
		for (OrderVO orderVO : arrayList) {
			OrderHeaderVO hvo = orderVO.getHVO();
			stringBuffer.append("'" + hvo.getPk_order() + "',");
		}
		// ȥ�����һ������
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);

		// ����PoOrderBPForWMS��ѯ�ɹ�������Ϣ
		PoOrderBPForWMS poOrderBPForWMS = new PoOrderBPForWMS();
		List<PoOrderDTOForWMS> list = poOrderBPForWMS
				.queryPoOrderForWMS(stringBuffer.toString());
		try {
			if (list==null || list.size() == 0) {
				throw new BusinessException("δ��ѯ�����㷢������������");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			ExceptionUtils.marsh(e1);
		}
		// ����PoOrderBackGroundTaskForWMS��������
		PoOrderBackGroundTaskForWMS poOrderBackGroundTaskForWMS = new PoOrderBackGroundTaskForWMS();
		try {
			poOrderBackGroundTaskForWMS.BatchSendingWMS(poOrderBPForWMS, list);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ExceptionUtils.marsh(e);
		}

	}
}
