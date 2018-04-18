package com.yonyou.test;
//import org.testng.annotations.Test;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.framework.comn.NetStreamContext;
import nc.bs.framework.core.service.IFwLogin;
import nc.bs.framework.test.AbstractTestCase;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.vo.sm.UserVO;


public class queryUserTest extends AbstractTestCase {
	@Override
    protected void setUp() throws Exception {
            super.setUp();
            String username = "yy01	";
            String password = "1qaz@WSX";
            IFwLogin loginService = (IFwLogin) NCLocator.getInstance().lookup(IFwLogin.class);
            byte[] token = loginService.login(username, password, null);
            NetStreamContext.setToken(token);
            InvocationInfoProxy.getInstance().setUserCode(username);
    }
    
    @Override
    protected void tearDown() throws Exception {
            super.tearDown();
            NetStreamContext.resetAll();
    }
    
//    @org.junit.Test
    public void queryUserByCode()throws Exception{
    	String querySql="select * from sm_user where nvl(dr,0)=0 and code = ?";
    	SQLParameter sqlParameter = new SQLParameter();
    	sqlParameter.addParam("yy01");
    	Object user = NCLocator.getInstance().lookup(IUAPQueryBS.class).executeQuery(querySql, sqlParameter,new BeanProcessor(UserVO.class));
    	System.out.println(user.toString());
    }
}
