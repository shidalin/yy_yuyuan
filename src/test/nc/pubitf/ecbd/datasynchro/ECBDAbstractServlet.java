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
 * 主要职责是解析传过来的JSON数据、返回处理结果。<br />
 * 具体的数据处理和参数类型声明由子类完成。
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

			// 登陆
			this.login(jsonData);

			// 进行具体的业务处理，并返回结果。
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
				resultPojo.setErrorMsg("未知错误:" + e.getCause().toString());
			} else {
				resultPojo.setErrorMsg(e.getMessage());
			}
		} finally {
			// 在测试中发现接口调用出错后，锁没有被释放。所以在调用的最后统一释放一下。
			PKLock.getInstance().releaseDynamicLocks();

			// 返回处理结果。
			this.returnResult(response, resultPojo);

			// 登出。
			this.logout(jsonData);
		}
	}

	/**
	 * 返回处理结果。
	 * 
	 * @param response
	 *            HTTP响应。
	 * @param result
	 *            供应链处理结果POJO。
	 */
	private void returnResult(HttpServletResponse response, ResultPojo result) {
		OutputStream out = null;
		ObjectOutputStream oos = null;
		try {
			// 获取数据
			out = response.getOutputStream();
			oos = new ObjectOutputStream(out);
			// 传输数据
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
	 * 从HTTP请求中获取业务处理的输入对象。
	 * 
	 * @param request
	 *            HTTP请求
	 * @return JSON数据封装对象
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
	 * 具体业务操作。
	 * 
	 * @param o
	 *            业务操作输入参数。
	 * @return 处理后返回结果。
	 */
	abstract protected Object doBusiness(JsonData jsonData)
			throws BusinessException;

	/**
	 * 返回JSON数据希望转换的具体类型。
	 * 
	 * @return JSON数据希望转换的具体类型。
	 */
	abstract protected Type getParameterType();

	/**
	 * 登陆。
	 * 
	 * @param userId
	 *            用户ID
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
	 * 登出。
	 */
	private void logout(JsonData jsonData) {
		if (jsonData != null) {
			IFwLogin ls = NCLocator.getInstance().lookup(IFwLogin.class);
			ls.logout(jsonData.getUsercode());
		}
	}

}