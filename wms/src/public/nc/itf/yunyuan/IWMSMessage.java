package nc.itf.yunyuan;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import nc.vo.bd.material.MaterialVO;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.pu.m21.entity.OrderVO;
import nc.vo.pub.BusinessException;

/**
 * 
 * WMS消息回传接口 访问地址：http://localhost/uapws/service/wms?wsdl
 * 
 * @since 6.0
 * @version 2017-12-1 上午9:27:50
 * @author shidalin
 */

public interface IWMSMessage {

	public String messageProcessor(String message) throws BusinessException;

	public void sendToWMSForMaterial(ArrayList<MaterialVO> arrayList)
			throws BusinessException;

	public void sendToWMSForSupplier(ArrayList<SupplierVO> arrayList)
			throws BusinessException;

	public void sendToWMSForPoOrder(ArrayList<OrderVO> arrayList)
			throws BusinessException, IllegalAccessException, InvocationTargetException;
}
