package nc.ui.bd.supplier.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class supplier_stock_config extends AbstractJavaBeanDefinition{
	private Map<String, Object> context = new HashMap();
public nc.ui.bd.uitabextend.DefaultUIExtComponent getSupplier_stock(){
 if(context.get("supplier_stock")!=null)
 return (nc.ui.bd.uitabextend.DefaultUIExtComponent)context.get("supplier_stock");
  nc.ui.bd.uitabextend.DefaultUIExtComponent bean = new nc.ui.bd.uitabextend.DefaultUIExtComponent();
  context.put("supplier_stock",bean);
  bean.setActions(getManagedList0());
  bean.setExComponent(getSupStockListView());
  bean.setClosingListener(getSupStockClosingListener());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList0(){  List list = new ArrayList();  list.add(getSupStockEditAction());  list.add(getSupStockDeleteAction());  list.add(getStockSeparatorAction());  list.add(getSupaddressAction());  list.add(getList_stockFreezeActionGroup());  list.add(getStockSeparatorAction());  list.add(getSupStockRefreshAction());  list.add(getStockSeparatorAction());  list.add(getSupStListPrintActionGroup());  return list;}

public nc.ui.uif2.editor.UserdefitemUIPreprocessor getUserdefitemSalePreprocessor(){
 if(context.get("userdefitemSalePreprocessor")!=null)
 return (nc.ui.uif2.editor.UserdefitemUIPreprocessor)context.get("userdefitemSalePreprocessor");
  nc.ui.uif2.editor.UserdefitemUIPreprocessor bean = new nc.ui.uif2.editor.UserdefitemUIPreprocessor();
  context.put("userdefitemSalePreprocessor",bean);
  bean.setContainer((nc.ui.uif2.userdefitem.UserDefItemContainer)findBeanInUIF2BeanFactory("userdefitemContainer"));
  bean.setParams(getManagedList1());
  bean.init();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList1(){  List list = new ArrayList();  list.add(getQueryParam_d6849c());  return list;}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_d6849c(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#d6849c")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#d6849c");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#d6849c",bean);
  bean.setMdfullname("uap.supstock");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.stock.model.SupStockAppModelService getSupStockAppModelService(){
 if(context.get("SupStockAppModelService")!=null)
 return (nc.ui.bd.supplier.stock.model.SupStockAppModelService)context.get("SupStockAppModelService");
  nc.ui.bd.supplier.stock.model.SupStockAppModelService bean = new nc.ui.bd.supplier.stock.model.SupStockAppModelService();
  context.put("SupStockAppModelService",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.orginfo.model.OrgInfoBillManageModel getSupStockAppModel(){
 if(context.get("supStockAppModel")!=null)
 return (nc.ui.bd.pub.orginfo.model.OrgInfoBillManageModel)context.get("supStockAppModel");
  nc.ui.bd.pub.orginfo.model.OrgInfoBillManageModel bean = new nc.ui.bd.pub.orginfo.model.OrgInfoBillManageModel();
  context.put("supStockAppModel",bean);
  bean.setService(getSupStockAppModelService());
  bean.setBusinessObjectAdapterFactory((nc.vo.bd.meta.IBDObjectAdapterFactory)findBeanInUIF2BeanFactory("boadapterfacotry"));
  bean.setContext((nc.vo.uif2.LoginContext)findBeanInUIF2BeanFactory("context"));
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.stock.model.SupStockAppModelDataManager getSupStockModelDataManager(){
 if(context.get("SupStockModelDataManager")!=null)
 return (nc.ui.bd.supplier.stock.model.SupStockAppModelDataManager)context.get("SupStockModelDataManager");
  nc.ui.bd.supplier.stock.model.SupStockAppModelDataManager bean = new nc.ui.bd.supplier.stock.model.SupStockAppModelDataManager();
  context.put("SupStockModelDataManager",bean);
  bean.setModel(getSupStockAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.orginfo.model.OrgInfoMediator getSupStockMediator(){
 if(context.get("supStockMediator")!=null)
 return (nc.ui.bd.pub.orginfo.model.OrgInfoMediator)context.get("supStockMediator");
  nc.ui.bd.pub.orginfo.model.OrgInfoMediator bean = new nc.ui.bd.pub.orginfo.model.OrgInfoMediator();
  context.put("supStockMediator",bean);
  bean.setBaseModel((nc.ui.uif2.model.AbstractUIAppModel)findBeanInUIF2BeanFactory("baseinfoModel"));
  bean.setModelDataManager(getSupStockModelDataManager());
  bean.setOrgInfoModel(getSupStockAppModel());
  bean.setOrgInfoPanel(getSupStockListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.stock.view.SupStockListView getSupStockListView(){
 if(context.get("supStockListView")!=null)
 return (nc.ui.bd.supplier.stock.view.SupStockListView)context.get("supStockListView");
  nc.ui.bd.supplier.stock.view.SupStockListView bean = new nc.ui.bd.supplier.stock.view.SupStockListView();
  context.put("supStockListView",bean);
  bean.setTemplateContainer((nc.ui.uif2.editor.TemplateContainer)findBeanInUIF2BeanFactory("templateContainer"));
  bean.setModel(getSupStockAppModel());
  bean.setNodekey("stock");
  bean.setName(getI18nFB_a2cda8());
  bean.setUserdefitemListPreparator(getUserdefitemContainerListPreparator_576af());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_a2cda8(){
 if(context.get("nc.ui.uif2.I18nFB#a2cda8")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#a2cda8");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#a2cda8",bean);  bean.setResDir("10140sub");
  bean.setDefaultValue("采购信息");
  bean.setResId("110140sub0008");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#a2cda8",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.editor.UserdefitemContainerListPreparator getUserdefitemContainerListPreparator_576af(){
 if(context.get("nc.ui.uif2.editor.UserdefitemContainerListPreparator#576af")!=null)
 return (nc.ui.uif2.editor.UserdefitemContainerListPreparator)context.get("nc.ui.uif2.editor.UserdefitemContainerListPreparator#576af");
  nc.ui.uif2.editor.UserdefitemContainerListPreparator bean = new nc.ui.uif2.editor.UserdefitemContainerListPreparator();
  context.put("nc.ui.uif2.editor.UserdefitemContainerListPreparator#576af",bean);
  bean.setContainer((nc.ui.uif2.userdefitem.UserDefItemContainer)findBeanInUIF2BeanFactory("userdefitemContainer"));
  bean.setParams(getManagedList2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList2(){  List list = new ArrayList();  list.add(getUserdefQueryParam_16160e2());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_16160e2(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#16160e2")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#16160e2");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#16160e2",bean);
  bean.setMdfullname("uap.supstock");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.stock.view.SupplierStockView getSupStockInfoView(){
 if(context.get("SupStockInfoView")!=null)
 return (nc.ui.bd.supplier.stock.view.SupplierStockView)context.get("SupStockInfoView");
  nc.ui.bd.supplier.stock.view.SupplierStockView bean = new nc.ui.bd.supplier.stock.view.SupplierStockView();
  context.put("SupStockInfoView",bean);
  bean.setModel(getSupStockAppModel());
  bean.setTemplateContainer((nc.ui.uif2.editor.TemplateContainer)findBeanInUIF2BeanFactory("templateContainer"));
  bean.setNodekey("stock");
  bean.setUserdefitemPreparator(getUserdefitemContainerPreparator_86bf71());
  bean.setBodyActionMap(getManagedMap0());
  bean.setActions(getManagedList5());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefitemContainerPreparator getUserdefitemContainerPreparator_86bf71(){
 if(context.get("nc.ui.uif2.editor.UserdefitemContainerPreparator#86bf71")!=null)
 return (nc.ui.uif2.editor.UserdefitemContainerPreparator)context.get("nc.ui.uif2.editor.UserdefitemContainerPreparator#86bf71");
  nc.ui.uif2.editor.UserdefitemContainerPreparator bean = new nc.ui.uif2.editor.UserdefitemContainerPreparator();
  context.put("nc.ui.uif2.editor.UserdefitemContainerPreparator#86bf71",bean);
  bean.setContainer((nc.ui.uif2.userdefitem.UserDefItemContainer)findBeanInUIF2BeanFactory("userdefitemContainer"));
  bean.setParams(getManagedList3());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList3(){  List list = new ArrayList();  list.add(getUserdefQueryParam_1fef97());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_1fef97(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#1fef97")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#1fef97");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#1fef97",bean);
  bean.setMdfullname("uap.supstock");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap0(){  Map map = new HashMap();  map.put("supaddress",getManagedList4());  return map;}

private List getManagedList4(){  List list = new ArrayList();  list.add(getStockAddlineAction());  list.add(getStockDellineAction());  return list;}

private List getManagedList5(){  List list = new ArrayList();  list.add(getSupStockFirstLineAction());  list.add(getSupStockPreLineAction());  list.add(getSupStockNextLineAction());  list.add(getSupStockLastLineAction());  return list;}

public nc.ui.bd.pub.orginfo.model.OrgInfoCardDialogMediator getSupStockDialogMediator(){
 if(context.get("supStockDialogMediator")!=null)
 return (nc.ui.bd.pub.orginfo.model.OrgInfoCardDialogMediator)context.get("supStockDialogMediator");
  nc.ui.bd.pub.orginfo.model.OrgInfoCardDialogMediator bean = new nc.ui.bd.pub.orginfo.model.OrgInfoCardDialogMediator();
  context.put("supStockDialogMediator",bean);
  bean.setModel(getSupStockAppModel());
  bean.setDialogContainer(getSupStockDialogContainer());
  bean.setName(getI18nFB_a0b1f3());
  bean.setClosingListener(getSupStockClosingListener());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_a0b1f3(){
 if(context.get("nc.ui.uif2.I18nFB#a0b1f3")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#a0b1f3");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#a0b1f3",bean);  bean.setResDir("10140sub");
  bean.setDefaultValue("采购信息");
  bean.setResId("110140sub0008");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#a0b1f3",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.uif2.TangramContainer getSupStockDialogContainer(){
 if(context.get("supStockDialogContainer")!=null)
 return (nc.ui.uif2.TangramContainer)context.get("supStockDialogContainer");
  nc.ui.uif2.TangramContainer bean = new nc.ui.uif2.TangramContainer();
  context.put("supStockDialogContainer",bean);
  bean.setConstraints(getManagedList6());
  bean.setActions(getManagedList7());
  bean.setEditActions(getManagedList8());
  bean.setModel(getSupStockAppModel());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList6(){  List list = new ArrayList();  list.add(getTangramLayoutConstraint_4f312e());  return list;}

private nc.ui.uif2.tangramlayout.TangramLayoutConstraint getTangramLayoutConstraint_4f312e(){
 if(context.get("nc.ui.uif2.tangramlayout.TangramLayoutConstraint#4f312e")!=null)
 return (nc.ui.uif2.tangramlayout.TangramLayoutConstraint)context.get("nc.ui.uif2.tangramlayout.TangramLayoutConstraint#4f312e");
  nc.ui.uif2.tangramlayout.TangramLayoutConstraint bean = new nc.ui.uif2.tangramlayout.TangramLayoutConstraint();
  context.put("nc.ui.uif2.tangramlayout.TangramLayoutConstraint#4f312e",bean);
  bean.setNewComponent(getSupStockInfoView());
  bean.setNewComponentName(getI18nFB_1451f46());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1451f46(){
 if(context.get("nc.ui.uif2.I18nFB#1451f46")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1451f46");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1451f46",bean);  bean.setResDir("10140sub");
  bean.setDefaultValue("采购信息");
  bean.setResId("110140sub0008");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1451f46",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList7(){  List list = new ArrayList();  list.add(getSupStockEditAction());  list.add(getSupStockDeleteAction());  list.add(getStockSeparatorAction());  list.add(getSupStockRefreshSingleAction());  list.add(getStockSeparatorAction());  list.add(getSupStPrintActionGroup());  return list;}

private List getManagedList8(){  List list = new ArrayList();  list.add(getSupStockSaveAction());  list.add(getStockSeparatorAction());  list.add(getSupStockCancelAction());  return list;}

public nc.funcnode.ui.action.SeparatorAction getStockSeparatorAction(){
 if(context.get("stockSeparatorAction")!=null)
 return (nc.funcnode.ui.action.SeparatorAction)context.get("stockSeparatorAction");
  nc.funcnode.ui.action.SeparatorAction bean = new nc.funcnode.ui.action.SeparatorAction();
  context.put("stockSeparatorAction",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.EditAction getSupStockEditAction(){
 if(context.get("SupStockEditAction")!=null)
 return (nc.ui.uif2.actions.EditAction)context.get("SupStockEditAction");
  nc.ui.uif2.actions.EditAction bean = new nc.ui.uif2.actions.EditAction();
  context.put("SupStockEditAction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setCode("StEdit");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.DeleteAction getSupStockDeleteAction(){
 if(context.get("SupStockDeleteAction")!=null)
 return (nc.ui.uif2.actions.DeleteAction)context.get("SupStockDeleteAction");
  nc.ui.uif2.actions.DeleteAction bean = new nc.ui.uif2.actions.DeleteAction();
  context.put("SupStockDeleteAction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setCode("StDel");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.SaveAction getSupStockSaveAction(){
 if(context.get("SupStockSaveAction")!=null)
 return (nc.ui.uif2.actions.SaveAction)context.get("SupStockSaveAction");
  nc.ui.uif2.actions.SaveAction bean = new nc.ui.uif2.actions.SaveAction();
  context.put("SupStockSaveAction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setEditor(getSupStockInfoView());
  bean.setCode("StSave");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.CancelAction getSupStockCancelAction(){
 if(context.get("SupStockCancelAction")!=null)
 return (nc.ui.uif2.actions.CancelAction)context.get("SupStockCancelAction");
  nc.ui.uif2.actions.CancelAction bean = new nc.ui.uif2.actions.CancelAction();
  context.put("SupStockCancelAction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setCode("StCancel");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.baseinfo.action.SupAddressAction getSupaddressAction(){
 if(context.get("supaddressAction")!=null)
 return (nc.ui.bd.supplier.baseinfo.action.SupAddressAction)context.get("supaddressAction");
  nc.ui.bd.supplier.baseinfo.action.SupAddressAction bean = new nc.ui.bd.supplier.baseinfo.action.SupAddressAction();
  context.put("supaddressAction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setFunnode("10140SAD");
  bean.setRefreshSingleAction(getSupStockRefreshSingleAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getList_stockFreezeActionGroup(){
 if(context.get("list_stockFreezeActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("list_stockFreezeActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("list_stockFreezeActionGroup",bean);
  bean.setActions(getManagedList9());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList9(){  List list = new ArrayList();  list.add(getList_orderFreezeAction());  list.add(getList_unOrderFreezeAction());  return list;}

public nc.ui.bd.supplier.stock.action.SupBatchOrderFreezeAction getList_orderFreezeAction(){
 if(context.get("list_orderFreezeAction")!=null)
 return (nc.ui.bd.supplier.stock.action.SupBatchOrderFreezeAction)context.get("list_orderFreezeAction");
  nc.ui.bd.supplier.stock.action.SupBatchOrderFreezeAction bean = new nc.ui.bd.supplier.stock.action.SupBatchOrderFreezeAction();
  context.put("list_orderFreezeAction",bean);
  bean.setModel(getSupStockAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.stock.action.SupBatchUnOrderFreezeAction getList_unOrderFreezeAction(){
 if(context.get("list_unOrderFreezeAction")!=null)
 return (nc.ui.bd.supplier.stock.action.SupBatchUnOrderFreezeAction)context.get("list_unOrderFreezeAction");
  nc.ui.bd.supplier.stock.action.SupBatchUnOrderFreezeAction bean = new nc.ui.bd.supplier.stock.action.SupBatchUnOrderFreezeAction();
  context.put("list_unOrderFreezeAction",bean);
  bean.setModel(getSupStockAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.RefreshAllAction getSupStockRefreshAction(){
 if(context.get("SupStockRefreshAction")!=null)
 return (nc.ui.uif2.actions.RefreshAllAction)context.get("SupStockRefreshAction");
  nc.ui.uif2.actions.RefreshAllAction bean = new nc.ui.uif2.actions.RefreshAllAction();
  context.put("SupStockRefreshAction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setManager(getSupStockModelDataManager());
  bean.setCode("StBatchRefresh");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.RefreshSingleAction getSupStockRefreshSingleAction(){
 if(context.get("SupStockRefreshSingleAction")!=null)
 return (nc.ui.uif2.actions.RefreshSingleAction)context.get("SupStockRefreshSingleAction");
  nc.ui.uif2.actions.RefreshSingleAction bean = new nc.ui.uif2.actions.RefreshSingleAction();
  context.put("SupStockRefreshSingleAction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setCode("StRefresh");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getSupStListPrintActionGroup(){
 if(context.get("SupStListPrintActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("SupStListPrintActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("SupStListPrintActionGroup",bean);
  bean.setCode("printgroup");
  bean.setActions(getManagedList10());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList10(){  List list = new ArrayList();  list.add(getSupStListTempletprintaction());  list.add(getSupStListTempletprintpreviewaction());  list.add(getSupStListOutputAction());  return list;}

public nc.ui.uif2.actions.TemplatePreviewAction getSupStListTempletprintpreviewaction(){
 if(context.get("SupStListTempletprintpreviewaction")!=null)
 return (nc.ui.uif2.actions.TemplatePreviewAction)context.get("SupStListTempletprintpreviewaction");
  nc.ui.uif2.actions.TemplatePreviewAction bean = new nc.ui.uif2.actions.TemplatePreviewAction();
  context.put("SupStListTempletprintpreviewaction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setCode("StTempPreview");
  bean.setNodeKey("supstocklist");
  bean.setDatasource(getStockListDataSource());
  bean.setPrintDlgParentConatiner(getSupStockListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.TemplatePrintAction getSupStListTempletprintaction(){
 if(context.get("SupStListTempletprintaction")!=null)
 return (nc.ui.uif2.actions.TemplatePrintAction)context.get("SupStListTempletprintaction");
  nc.ui.uif2.actions.TemplatePrintAction bean = new nc.ui.uif2.actions.TemplatePrintAction();
  context.put("SupStListTempletprintaction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setCode("StTempPrint");
  bean.setNodeKey("supstocklist");
  bean.setDatasource(getStockListDataSource());
  bean.setPrintDlgParentConatiner(getSupStockListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.OutputAction getSupStListOutputAction(){
 if(context.get("SupStListOutputAction")!=null)
 return (nc.ui.uif2.actions.OutputAction)context.get("SupStListOutputAction");
  nc.ui.uif2.actions.OutputAction bean = new nc.ui.uif2.actions.OutputAction();
  context.put("SupStListOutputAction",bean);
  bean.setCode("StOutput");
  bean.setModel(getSupStockAppModel());
  bean.setNodeKey("supstocklist");
  bean.setDatasource(getStockListDataSource());
  bean.setPrintDlgParentConatiner(getSupStockListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.print.MetaDataAllDatasSource getStockListDataSource(){
 if(context.get("StockListDataSource")!=null)
 return (nc.ui.bd.pub.actions.print.MetaDataAllDatasSource)context.get("StockListDataSource");
  nc.ui.bd.pub.actions.print.MetaDataAllDatasSource bean = new nc.ui.bd.pub.actions.print.MetaDataAllDatasSource();
  context.put("StockListDataSource",bean);
  bean.setModel(getSupStockAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getSupStPrintActionGroup(){
 if(context.get("SupStPrintActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("SupStPrintActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("SupStPrintActionGroup",bean);
  bean.setCode("printgroup");
  bean.setActions(getManagedList11());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList11(){  List list = new ArrayList();  list.add(getSupStTempletprintaction());  list.add(getSupStTempletprintpreviewaction());  list.add(getSupStOutputAction());  return list;}

public nc.ui.uif2.actions.TemplatePreviewAction getSupStTempletprintpreviewaction(){
 if(context.get("SupStTempletprintpreviewaction")!=null)
 return (nc.ui.uif2.actions.TemplatePreviewAction)context.get("SupStTempletprintpreviewaction");
  nc.ui.uif2.actions.TemplatePreviewAction bean = new nc.ui.uif2.actions.TemplatePreviewAction();
  context.put("SupStTempletprintpreviewaction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setCode("StTempPreview");
  bean.setNodeKey("supstock");
  bean.setDatasource(getSupStDataSource());
  bean.setPrintDlgParentConatiner(getSupStockInfoView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.TemplatePrintAction getSupStTempletprintaction(){
 if(context.get("SupStTempletprintaction")!=null)
 return (nc.ui.uif2.actions.TemplatePrintAction)context.get("SupStTempletprintaction");
  nc.ui.uif2.actions.TemplatePrintAction bean = new nc.ui.uif2.actions.TemplatePrintAction();
  context.put("SupStTempletprintaction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setCode("StTempPrint");
  bean.setNodeKey("supstock");
  bean.setDatasource(getSupStDataSource());
  bean.setPrintDlgParentConatiner(getSupStockInfoView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.OutputAction getSupStOutputAction(){
 if(context.get("SupStOutputAction")!=null)
 return (nc.ui.uif2.actions.OutputAction)context.get("SupStOutputAction");
  nc.ui.uif2.actions.OutputAction bean = new nc.ui.uif2.actions.OutputAction();
  context.put("SupStOutputAction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setNodeKey("supstock");
  bean.setDatasource(getSupStDataSource());
  bean.setPrintDlgParentConatiner(getSupStockInfoView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.print.MetaDataSingleSelectDataSource getSupStDataSource(){
 if(context.get("SupStDataSource")!=null)
 return (nc.ui.bd.pub.actions.print.MetaDataSingleSelectDataSource)context.get("SupStDataSource");
  nc.ui.bd.pub.actions.print.MetaDataSingleSelectDataSource bean = new nc.ui.bd.pub.actions.print.MetaDataSingleSelectDataSource();
  context.put("SupStDataSource",bean);
  bean.setModel(getSupStockAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.FirstLineAction getSupStockFirstLineAction(){
 if(context.get("SupStockFirstLineAction")!=null)
 return (nc.ui.uif2.actions.FirstLineAction)context.get("SupStockFirstLineAction");
  nc.ui.uif2.actions.FirstLineAction bean = new nc.ui.uif2.actions.FirstLineAction();
  context.put("SupStockFirstLineAction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setCode("StFirstLine");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.NextLineAction getSupStockNextLineAction(){
 if(context.get("SupStockNextLineAction")!=null)
 return (nc.ui.uif2.actions.NextLineAction)context.get("SupStockNextLineAction");
  nc.ui.uif2.actions.NextLineAction bean = new nc.ui.uif2.actions.NextLineAction();
  context.put("SupStockNextLineAction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setCode("StNextLine");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.PreLineAction getSupStockPreLineAction(){
 if(context.get("SupStockPreLineAction")!=null)
 return (nc.ui.uif2.actions.PreLineAction)context.get("SupStockPreLineAction");
  nc.ui.uif2.actions.PreLineAction bean = new nc.ui.uif2.actions.PreLineAction();
  context.put("SupStockPreLineAction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setCode("StPreLine");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.LastLineAction getSupStockLastLineAction(){
 if(context.get("SupStockLastLineAction")!=null)
 return (nc.ui.uif2.actions.LastLineAction)context.get("SupStockLastLineAction");
  nc.ui.uif2.actions.LastLineAction bean = new nc.ui.uif2.actions.LastLineAction();
  context.put("SupStockLastLineAction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setCode("StLastLine");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.AddLineAction getStockAddlineAction(){
 if(context.get("stockAddlineAction")!=null)
 return (nc.ui.uif2.actions.AddLineAction)context.get("stockAddlineAction");
  nc.ui.uif2.actions.AddLineAction bean = new nc.ui.uif2.actions.AddLineAction();
  context.put("stockAddlineAction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setCardpanel(getSupStockInfoView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.DelLineAction getStockDellineAction(){
 if(context.get("stockDellineAction")!=null)
 return (nc.ui.uif2.actions.DelLineAction)context.get("stockDellineAction");
  nc.ui.uif2.actions.DelLineAction bean = new nc.ui.uif2.actions.DelLineAction();
  context.put("stockDellineAction",bean);
  bean.setModel(getSupStockAppModel());
  bean.setCardpanel(getSupStockInfoView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.FunNodeClosingHandler getSupStockClosingListener(){
 if(context.get("SupStockClosingListener")!=null)
 return (nc.ui.uif2.FunNodeClosingHandler)context.get("SupStockClosingListener");
  nc.ui.uif2.FunNodeClosingHandler bean = new nc.ui.uif2.FunNodeClosingHandler();
  context.put("SupStockClosingListener",bean);
  bean.setModel(getSupStockAppModel());
  bean.setSaveaction(getSupStockSaveAction());
  bean.setCancelaction(getSupStockCancelAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

}
