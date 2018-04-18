package nc.ui.bd.supplier.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class supplier_associate_pf extends AbstractJavaBeanDefinition{
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

private List getManagedList1(){  List list = new ArrayList();  list.add("base");  list.add("approve");  return list;}

public nc.ui.bd.supplier.pf.model.SupplierPfApproveModel getApprovePfModel(){
 if(context.get("approvePfModel")!=null)
 return (nc.ui.bd.supplier.pf.model.SupplierPfApproveModel)context.get("approvePfModel");
  nc.ui.bd.supplier.pf.model.SupplierPfApproveModel bean = new nc.ui.bd.supplier.pf.model.SupplierPfApproveModel();
  context.put("approvePfModel",bean);
  bean.setContext((nc.vo.uif2.LoginContext)findBeanInUIF2BeanFactory("context"));
  bean.setBusinessObjectAdapterFactory((nc.vo.bd.meta.IBDObjectAdapterFactory)findBeanInUIF2BeanFactory("boadapterfacotry"));
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.PlugableTangramContainer getDialogContainer(){
 if(context.get("dialogContainer")!=null)
 return (nc.ui.uif2.PlugableTangramContainer)context.get("dialogContainer");
  nc.ui.uif2.PlugableTangramContainer bean = new nc.ui.uif2.PlugableTangramContainer();
  context.put("dialogContainer",bean);
  bean.setTangramLayoutRoot(getVSNode_be0a1c());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_be0a1c(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#be0a1c")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#be0a1c");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#be0a1c",bean);
  bean.setUp(getCNode_1cb25ab());
  bean.setDown(getCNode_11af015());
  bean.setDividerLocation(0.3f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1cb25ab(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1cb25ab")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1cb25ab");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1cb25ab",bean);
  bean.setComponent(getApproveEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_11af015(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#11af015")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#11af015");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#11af015",bean);
  bean.setComponent(getPfBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.pf.view.SupplierPfApproveCardPanel getApproveEditor(){
 if(context.get("approveEditor")!=null)
 return (nc.ui.bd.supplier.pf.view.SupplierPfApproveCardPanel)context.get("approveEditor");
  nc.ui.bd.supplier.pf.view.SupplierPfApproveCardPanel bean = new nc.ui.bd.supplier.pf.view.SupplierPfApproveCardPanel();
  context.put("approveEditor",bean);
  bean.setModel(getApprovePfModel());
  bean.setTemplateContainer(getAssociateTemplateContainer());
  bean.setNodekey("approve");
  bean.setSupBaseInfoView(getPfBaseinfoEditor());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.pf.view.SupplierPfBaseInfoCardPanel getPfBaseinfoEditor(){
 if(context.get("pfBaseinfoEditor")!=null)
 return (nc.ui.bd.supplier.pf.view.SupplierPfBaseInfoCardPanel)context.get("pfBaseinfoEditor");
  nc.ui.bd.supplier.pf.view.SupplierPfBaseInfoCardPanel bean = new nc.ui.bd.supplier.pf.view.SupplierPfBaseInfoCardPanel();
  context.put("pfBaseinfoEditor",bean);
  bean.setTemplateContainer((nc.ui.uif2.editor.TemplateContainer)findBeanInUIF2BeanFactory("templateContainer"));
  bean.setModel(getApprovePfModel());
  bean.setUserdefitemPreparator(getUserdefitemContainerPreparator_153dbef());
  bean.setNodekey("base");
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefitemContainerPreparator getUserdefitemContainerPreparator_153dbef(){
 if(context.get("nc.ui.uif2.editor.UserdefitemContainerPreparator#153dbef")!=null)
 return (nc.ui.uif2.editor.UserdefitemContainerPreparator)context.get("nc.ui.uif2.editor.UserdefitemContainerPreparator#153dbef");
  nc.ui.uif2.editor.UserdefitemContainerPreparator bean = new nc.ui.uif2.editor.UserdefitemContainerPreparator();
  context.put("nc.ui.uif2.editor.UserdefitemContainerPreparator#153dbef",bean);
  bean.setContainer((nc.ui.uif2.userdefitem.UserDefItemContainer)findBeanInUIF2BeanFactory("userdefitemContainer"));
  bean.setParams(getManagedList2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList2(){  List list = new ArrayList();  list.add(getUserdefQueryParam_1cb4b40());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_1cb4b40(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#1cb4b40")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#1cb4b40");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#1cb4b40",bean);
  bean.setMdfullname("uap.supplier");
  bean.setPrefix("def");
  bean.setPos(0);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

}
