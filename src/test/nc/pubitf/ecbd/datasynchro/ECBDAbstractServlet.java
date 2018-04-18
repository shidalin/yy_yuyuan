package nc.pubitf.ecbd.datasynchro;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.bs.framework.adaptor.IHttpServletAdaptor;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.framework.comn.NetStreamContext;
import nc.bs.framework.core.service.IFwLogin;
import nc.bs.uap.lock.PKLock;
import nc.login.bs.INCUserQueryService;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.pattern.log.Log;
import nc.vo.sm.UserVO;
//import nc.web.arap.json.JsonData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.google.gson.Gson;

/**
 * ��Ҫְ���ǽ�����������JSON���ݡ����ش�������<br />
 * ��������ݴ���Ͳ�������������������ɡ�
 * 
 */
@SuppressWarnings("restriction")
public abstract class ECBDAbstractServlet implements IHttpServletAdaptor {

	@Override
	public void doAction(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ResultPojo resultPojo = new ResultPojo();
		JsonData jsonData = null;
		try {
			jsonData = this.parseJsonData(request);

			// ��½
			this.login(jsonData);

			// ���о����ҵ���������ؽ����
			Object result = this.doBusiness(jsonData);
			if (result != null) {
				String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
				SerializeConfig serializeConfig = new SerializeConfig();
				serializeConfig.put(Date.class, new SimpleDateFormatSerializer(
						DATE_FORMAT));
				serializeConfig.put(Timestamp.class,
						new SimpleDateFormatSerializer(DATE_FORMAT));
				result = JSON.toJSONString(result, serializeConfig);
				// result = JSONUtils.toJSONString(result);
			}
			if (result == null) {
				result = "";
			}
			resultPojo.setResult(result);
		} catch (Exception e) {
			Log.error(e);
			if (e.getMessage() == null || "".equals(e.getMessage())) {
				resultPojo.setErrorMsg("δ֪����:" + e.getCause().toString());
			} else {
				resultPojo.setErrorMsg(e.getMessage());
			}
		} finally {
			// �ڲ����з��ֽӿڵ��ó������û�б��ͷš������ڵ��õ����ͳһ�ͷ�һ�¡�
			PKLock.getInstance().releaseDynamicLocks();

			// ���ش�������
			this.returnResult(response, resultPojo);

			// �ǳ���
			this.logout(jsonData);
		}
	}

	/**
	 * ���ش�������
	 * 
	 * @param response
	 *            HTTP��Ӧ��
	 * @param result
	 *            ��Ӧ��������POJO��
	 */
	private void returnResult(HttpServletResponse response, ResultPojo result) {
		OutputStream out = null;
		ObjectOutputStream oos = null;
		try {
			// ��ȡ����
			out = response.getOutputStream();
			oos = new ObjectOutputStream(out);
			// ��������
			String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
			SerializeConfig serializeConfig = new SerializeConfig();
			serializeConfig.put(Date.class, new SimpleDateFormatSerializer(
					DATE_FORMAT));
			serializeConfig.put(Timestamp.class,
					new SimpleDateFormatSerializer(DATE_FORMAT));
			oos.writeObject(JSON.toJSONString(result, serializeConfig));
			oos.flush();
		} catch (Exception e) {
			Log.error(e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (oos != null) {
					oos.close();
				}
			} catch (IOException e) {
				Log.error(e);
			}
		}
	}

	/**
	 * ��HTTP�����л�ȡҵ������������
	 * 
	 * @param request
	 *            HTTP����
	 * @return JSON���ݷ�װ����
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked" })
	private JsonData parseJsonData(HttpServletRequest request) throws Exception {
		// ObjectInputStream in = null;
		Gson gson = new Gson();
		try {
			/*
			 * String jodata = request.getParameter("data"); String ss =
			 * (String) SerializeUtil.unserialize(jodata.getBytes());
			 */
			// in = new ObjectInputStream(request.getInputStream());

			// String r = (String) in.readObject();
			Map<String, String[]> parameterMap = request.getParameterMap();
			// Map<String, String> data = gson.fromJson(r, HashMap.class);

			JsonData jsonData = new JsonData();
			if (parameterMap.get("usercode") != null
					&& parameterMap.get("usercode").length != 0) {
				jsonData.setUsercode(parameterMap.get("usercode")[0]);
			}
			if (parameterMap.get("pwd") != null
					&& parameterMap.get("pwd").length != 0) {
				jsonData.setPsw(parameterMap.get("pwd")[0]);
			}
			if (parameterMap.get("datasource") != null
					&& parameterMap.get("datasource").length != 0) {
				jsonData.setDatasource(parameterMap.get("datasource")[0]);
			} else {
				jsonData.setDatasource("design");
			}
			if (parameterMap.get("data") != null
					&& parameterMap.get("data").length != 0) {
				jsonData.setDatasource(parameterMap.get("data")[0]);
			}

			return jsonData;
		} finally {
			// if (in != null) {
			// try {
			// in.close();
			// } catch (IOException e) {
			// Log.error(e);
			// }
			// }
		}
	}

	/**
	 * ����ҵ�������
	 * 
	 * @param o
	 *            ҵ��������������
	 * @return ����󷵻ؽ����
	 */
	abstract protected Object doBusiness(JsonData jsonData)
			throws BusinessException;

	/**
	 * ����JSON����ϣ��ת���ľ������͡�
	 * 
	 * @return JSON����ϣ��ת���ľ������͡�
	 */
	abstract protected Type getParameterType();

	/**
	 * ��½��
	 * 
	 * @param userId
	 *            �û�ID
	 */
	private void login(JsonData jsonData) {
		try {
			String usercode = jsonData.getUsercode();
			String psw = jsonData.getPsw();
			String ds = jsonData.getDatasource();

			INCUserQueryService service = NCLocator.getInstance().lookup(
					INCUserQueryService.class);
			UserVO user = service.findUserVO(ds, usercode);

			InvocationInfoProxy.getInstance().setUserDataSource(ds);
			InvocationInfoProxy.getInstance().setGroupId(user.getPk_group());
			InvocationInfoProxy.getInstance().setUserCode(user.getUser_code());
			InvocationInfoProxy.getInstance().setUserId(user.getCuserid());
			IFwLogin ls = NCLocator.getInstance().lookup(IFwLogin.class);

			byte[] token = ls.login(usercode, psw, null);
			NetStreamContext.setToken(token);
		} catch (BusinessException e) {
			ExceptionUtils.wrappException(e);
		}
	}

	/**
	 * �ǳ���
	 */
	private void logout(JsonData jsonData) {
		if (jsonData != null) {
			IFwLogin ls = NCLocator.getInstance().lookup(IFwLogin.class);
			ls.logout(jsonData.getUsercode());
		}
	}

}