package nc.ui.bd.material.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class Material_Associate_pf extends AbstractJavaBeanDefinition{
	private Map<String, Object> context = new HashMap();
public nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller getRemoteCallCombinatorCaller(){
 if(context.get("remoteCallCombinatorCaller")!=null)
 return (nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller)context.get("remoteCallCombinatorCaller");
  nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller bean = new nc.ui.uif2.editor.UIF2RemoteCallCombinatorCaller();
  context.put("remoteCallCombinatorCaller",bean);
  bean.setRemoteCallers(getManagedList0());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList0(){  List list = new ArrayList();  list.add((nc.vo.util.remotecallcombination.IRemoteCallCombinatorUser)findBeanInUIF2BeanFactory("templateContainer"));  return list;}

public nc.ui.uif2.editor.TemplateContainer getAssociateTemplateContainer(){
 if(context.get("associateTemplateContainer")!=null)
 return (nc.ui.uif2.editor.TemplateContainer)context.get("associateTemplateContainer");
  nc.ui.uif2.editor.TemplateContainer bean = new nc.ui.uif2.editor.TemplateContainer();
  context.put("associateTemplateContainer",bean);
  bean.setContext((nc.vo.uif2.LoginContext)findBeanInUIF2BeanFactory("context"));
  bean.setNodeKeies(getManagedList1());
  bean.load();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList1(){  List list = new ArrayList();  list.add("approve");  return list;}

public nc.ui.uif2.PlugableTangramContainer getDialogContainer(){
 if(context.get("dialogContainer")!=null)
 return (nc.ui.uif2.PlugableTangramContainer)context.get("dialogContainer");
  nc.ui.uif2.PlugableTangramContainer bean = new nc.ui.uif2.PlugableTangramContainer();
  context.put("dialogContainer",bean);
  bean.setTangramLayoutRoot(getVSNode_16e80f3());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_16e80f3(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#16e80f3")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#16e80f3");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#16e80f3",bean);
  bean.setUp(getCNode_945a96());
  bean.setDown(getTBNode_10c2fae());
  bean.setDividerLocation(0.3f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_945a96(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#945a96")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#945a96");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#945a96",bean);
  bean.setComponent(getApproveEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.TBNode getTBNode_10c2fae(){
 if(context.get("nc.ui.uif2.tangramlayout.node.TBNode#10c2fae")!=null)
 return (nc.ui.uif2.tangramlayout.node.TBNode)context.get("nc.ui.uif2.tangramlayout.node.TBNode#10c2fae");
  nc.ui.uif2.tangramlayout.node.TBNode bean = new nc.ui.uif2.tangramlayout.node.TBNode();
  context.put("nc.ui.uif2.tangramlayout.node.TBNode#10c2fae",bean);
  bean.setTabs(getManagedList2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList2(){  List list = new ArrayList();  list.add(getCNode_1ccbdea());  return list;}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1ccbdea(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1ccbdea")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1ccbdea");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1ccbdea",bean);
  bean.setName(getI18nFB_15659ce());
  bean.setComponent(getDataEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_15659ce(){
 if(context.get("nc.ui.uif2.I18nFB#15659ce")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#15659ce");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#15659ce",bean);  bean.setResDir("10140mpf");
  bean.setDefaultValue("物料信息");
  bean.setResId("010140mpf0002");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#15659ce",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.uif2.model.BillManageModel getApproveModel(){
 if(context.get("approveModel")!=null)
 return (nc.ui.uif2.model.BillManageModel)context.get("approveModel");
  nc.ui.uif2.model.BillManageModel bean = new nc.ui.uif2.model.BillManageModel();
  context.put("approveModel",bean);
  bean.setBusinessObjectAdapterFactory(getBoadapterfacotry());
  bean.setContext((nc.vo.uif2.LoginContext)findBeanInUIF2BeanFactory("context"));
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.vo.bd.meta.BDObjectAdpaterFactory getBoadapterfacotry(){
 if(context.get("boadapterfacotry")!=null)
 return (nc.vo.bd.meta.BDObjectAdpaterFactory)context.get("boadapterfacotry");
  nc.vo.bd.meta.BDObjectAdpaterFactory bean = new nc.vo.bd.meta.BDObjectAdpaterFactory();
  context.put("boadapterfacotry",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.pf.view.MaterialPfApproveEditor getApproveEditor(){
 if(context.get("approveEditor")!=null)
 return (nc.ui.bd.material.pf.view.MaterialPfApproveEditor)context.get("approveEditor");
  nc.ui.bd.material.pf.view.MaterialPfApproveEditor bean = new nc.ui.bd.material.pf.view.MaterialPfApproveEditor();
  context.put("approveEditor",bean);
  bean.setModel(getApproveModel());
  bean.setTemplateContainer(getAssociateTemplateContainer());
  bean.setNodekey("approve");
  bean.setDataEditor(getDataEditor());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.pf.view.MaterialPfDataEditor getDataEditor(){
 if(context.get("dataEditor")!=null)
 return (nc.ui.bd.material.pf.view.MaterialPfDataEditor)context.get("dataEditor");
  nc.ui.bd.material.pf.view.MaterialPfDataEditor bean = new nc.ui.bd.material.pf.view.MaterialPfDataEditor();
  context.put("dataEditor",bean);
  bean.setModel(getApproveModel());
  bean.setTemplateContainer((nc.ui.uif2.editor.TemplateContainer)findBeanInUIF2BeanFactory("templateContainer"));
  bean.setNodekey("base");
  bean.setUserdefitemPreparator(getUserdefitemContainerPreparator_653079());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefitemContainerPreparator getUserdefitemContainerPreparator_653079(){
 if(context.get("nc.ui.uif2.editor.UserdefitemContainerPreparator#653079")!=null)
 return (nc.ui.uif2.editor.UserdefitemContainerPreparator)context.get("nc.ui.uif2.editor.UserdefitemContainerPreparator#653079");
  nc.ui.uif2.editor.UserdefitemContainerPreparator bean = new nc.ui.uif2.editor.UserdefitemContainerPreparator();
  context.put("nc.ui.uif2.editor.UserdefitemContainerPreparator#653079",bean);
  bean.setContainer((nc.ui.uif2.userdefitem.UserDefItemContainer)findBeanInUIF2BeanFactory("userdefitemContainer"));
  bean.setParams(getManagedList3());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList3(){  List list = new ArrayList();  list.add(getCardUserdefitemQueryParam());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getCardUserdefitemQueryParam(){
 if(context.get("cardUserdefitemQueryParam")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("cardUserdefitemQueryParam");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("cardUserdefitemQueryParam",bean);
  bean.setMdfullname("uap.material");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

}
