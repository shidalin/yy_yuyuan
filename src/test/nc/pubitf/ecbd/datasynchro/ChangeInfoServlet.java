package nc.pubitf.ecbd.datasynchro;

import java.lang.reflect.Type;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.pubitf.ecbd.datasynchro.ECBDAbstractServlet;
import nc.pubitf.ecbd.datasynchro.JsonData;
import nc.vo.pub.BusinessException;
import nc.vo.sm.UserVO;

/**
 * ±¸×¢£ºnc.bs.framework.server.ISecurityTokenCallback
 * 
 * @author shidalin
 * 
 */
public class ChangeInfoServlet extends ECBDAbstractServlet {

	@Override
	protected Object doBusiness(JsonData jsonData) throws BusinessException {
		// TODO Auto-generated method stub
		String querySql = "select * from sm_user where nvl(dr,0)=0 and user_code = ?";
		SQLParameter sqlParameter = new SQLParameter();
		sqlParameter.addParam("yy01");
		Object user = NCLocator
				.getInstance()
				.lookup(IUAPQueryBS.class)
				.executeQuery(querySql, sqlParameter,
						new BeanProcessor(UserVO.class));
		System.out.println(user.toString());
		return user;
	}

	@Override
	protected Type getParameterType() {
		// TODO Auto-generated method stub
		return null;
	}

}
