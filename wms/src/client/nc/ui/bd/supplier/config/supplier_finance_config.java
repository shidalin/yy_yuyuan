package nc.ui.bd.supplier.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class supplier_finance_config extends AbstractJavaBeanDefinition{
	private Map<String, Object> context = new HashMap();
public nc.ui.bd.uitabextend.DefaultUIExtComponent getSupplier_finance(){
 if(context.get("supplier_finance")!=null)
 return (nc.ui.bd.uitabextend.DefaultUIExtComponent)context.get("supplier_finance");
  nc.ui.bd.uitabextend.DefaultUIExtComponent bean = new nc.ui.bd.uitabextend.DefaultUIExtComponent();
  context.put("supplier_finance",bean);
  bean.setActions(getManagedList0());
  bean.setExComponent(getSupFinanceListView());
  bean.setClosingListener(getSupFinanceClosingListener());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList0(){  List list = new ArrayList();  list.add(getSupFinanceEditAction());  list.add(getSupFinanceDeleteAction());  list.add(getFinanceseparatorAction());  list.add(getList_PayFreezeActionGroup());  list.add(getList_MakeOutFreezeActionGroup());  list.add(getFinanceseparatorAction());  list.add(getSupFinanceRefreshAction());  list.add(getFinanceseparatorAction());  list.add(getSupFiListPrintActionGroup());  return list;}

public nc.ui.uif2.editor.UserdefitemUIPreprocessor getUserdefitemFinancePreprocessor(){
 if(context.get("userdefitemFinancePreprocessor")!=null)
 return (nc.ui.uif2.editor.UserdefitemUIPreprocessor)context.get("userdefitemFinancePreprocessor");
  nc.ui.uif2.editor.UserdefitemUIPreprocessor bean = new nc.ui.uif2.editor.UserdefitemUIPreprocessor();
  context.put("userdefitemFinancePreprocessor",bean);
  bean.setContainer((nc.ui.uif2.userdefitem.UserDefItemContainer)findBeanInUIF2BeanFactory("userdefitemContainer"));
  bean.setParams(getManagedList1());
  bean.init();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList1(){  List list = new ArrayList();  list.add(getQueryParam_51f4ab());  return list;}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_51f4ab(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#51f4ab")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#51f4ab");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#51f4ab",bean);
  bean.setMdfullname("uap.supfinance");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.finance.model.SupFinanceAppModelService getSupFinanceService(){
 if(context.get("supFinanceService")!=null)
 return (nc.ui.bd.supplier.finance.model.SupFinanceAppModelService)context.get("supFinanceService");
  nc.ui.bd.supplier.finance.model.SupFinanceAppModelService bean = new nc.ui.bd.supplier.finance.model.SupFinanceAppModelService();
  context.put("supFinanceService",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.orginfo.model.OrgInfoBillManageModel getSupFinanceAppModel(){
 if(context.get("supFinanceAppModel")!=null)
 return (nc.ui.bd.pub.orginfo.model.OrgInfoBillManageModel)context.get("supFinanceAppModel");
  nc.ui.bd.pub.orginfo.model.OrgInfoBillManageModel bean = new nc.ui.bd.pub.orginfo.model.OrgInfoBillManageModel();
  context.put("supFinanceAppModel",bean);
  bean.setService(getSupFinanceService());
  bean.setContext((nc.vo.uif2.LoginContext)findBeanInUIF2BeanFactory("context"));
  bean.setBusinessObjectAdapterFactory((nc.vo.bd.meta.IBDObjectAdapterFactory)findBeanInUIF2BeanFactory("boadapterfacotry"));
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.finance.model.SupFinanceAppModelDataManager getSupFinanceModelDataManager(){
 if(context.get("supFinanceModelDataManager")!=null)
 return (nc.ui.bd.supplier.finance.model.SupFinanceAppModelDataManager)context.get("supFinanceModelDataManager");
  nc.ui.bd.supplier.finance.model.SupFinanceAppModelDataManager bean = new nc.ui.bd.supplier.finance.model.SupFinanceAppModelDataManager();
  context.put("supFinanceModelDataManager",bean);
  bean.setModel(getSupFinanceAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.orginfo.model.OrgInfoMediator getSupFinanceMediator(){
 if(context.get("supFinanceMediator")!=null)
 return (nc.ui.bd.pub.orginfo.model.OrgInfoMediator)context.get("supFinanceMediator");
  nc.ui.bd.pub.orginfo.model.OrgInfoMediator bean = new nc.ui.bd.pub.orginfo.model.OrgInfoMediator();
  context.put("supFinanceMediator",bean);
  bean.setBaseModel((nc.ui.uif2.model.AbstractUIAppModel)findBeanInUIF2BeanFactory("baseinfoModel"));
  bean.setModelDataManager(getSupFinanceModelDataManager());
  bean.setOrgInfoModel(getSupFinanceAppModel());
  bean.setOrgInfoPanel(getSupFinanceListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.finance.view.SupFinanceListView getSupFinanceListView(){
 if(context.get("supFinanceListView")!=null)
 return (nc.ui.bd.supplier.finance.view.SupFinanceListView)context.get("supFinanceListView");
  nc.ui.bd.supplier.finance.view.SupFinanceListView bean = new nc.ui.bd.supplier.finance.view.SupFinanceListView();
  context.put("supFinanceListView",bean);
  bean.setTemplateContainer((nc.ui.uif2.editor.TemplateContainer)findBeanInUIF2BeanFactory("templateContainer"));
  bean.setModel(getSupFinanceAppModel());
  bean.setNodekey("finance");
  bean.setName(getI18nFB_6114d5());
  bean.setUserdefitemListPreparator(getUserdefitemContainerListPreparator_71db37());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_6114d5(){
 if(context.get("nc.ui.uif2.I18nFB#6114d5")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#6114d5");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#6114d5",bean);  bean.setResDir("10140sub");
  bean.setDefaultValue("财务信息");
  bean.setResId("110140sub0011");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#6114d5",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.editor.UserdefitemContainerListPreparator getUserdefitemContainerListPreparator_71db37(){
 if(context.get("nc.ui.uif2.editor.UserdefitemContainerListPreparator#71db37")!=null)
 return (nc.ui.uif2.editor.UserdefitemContainerListPreparator)context.get("nc.ui.uif2.editor.UserdefitemContainerListPreparator#71db37");
  nc.ui.uif2.editor.UserdefitemContainerListPreparator bean = new nc.ui.uif2.editor.UserdefitemContainerListPreparator();
  context.put("nc.ui.uif2.editor.UserdefitemContainerListPreparator#71db37",bean);
  bean.setContainer((nc.ui.uif2.userdefitem.UserDefItemContainer)findBeanInUIF2BeanFactory("userdefitemContainer"));
  bean.setParams(getManagedList2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList2(){  List list = new ArrayList();  list.add(getUserdefQueryParam_b9e479());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_b9e479(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#b9e479")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#b9e479");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#b9e479",bean);
  bean.setMdfullname("uap.supfinance");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.finance.view.SupplierFinanceView getSupFinanceView(){
 if(context.get("supFinanceView")!=null)
 return (nc.ui.bd.supplier.finance.view.SupplierFinanceView)context.get("supFinanceView");
  nc.ui.bd.supplier.finance.view.SupplierFinanceView bean = new nc.ui.bd.supplier.finance.view.SupplierFinanceView();
  context.put("supFinanceView",bean);
  bean.setModel(getSupFinanceAppModel());
  bean.setTemplateContainer((nc.ui.uif2.editor.TemplateContainer)findBeanInUIF2BeanFactory("templateContainer"));
  bean.setNodekey("finance");
  bean.setUserdefitemPreparator(getUserdefitemContainerPreparator_13df867());
  bean.setActions(getManagedList4());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefitemContainerPreparator getUserdefitemContainerPreparator_13df867(){
 if(context.get("nc.ui.uif2.editor.UserdefitemContainerPreparator#13df867")!=null)
 return (nc.ui.uif2.editor.UserdefitemContainerPreparator)context.get("nc.ui.uif2.editor.UserdefitemContainerPreparator#13df867");
  nc.ui.uif2.editor.UserdefitemContainerPreparator bean = new nc.ui.uif2.editor.UserdefitemContainerPreparator();
  context.put("nc.ui.uif2.editor.UserdefitemContainerPreparator#13df867",bean);
  bean.setContainer((nc.ui.uif2.userdefitem.UserDefItemContainer)findBeanInUIF2BeanFactory("userdefitemContainer"));
  bean.setParams(getManagedList3());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList3(){  List list = new ArrayList();  list.add(getUserdefQueryParam_18483fe());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_18483fe(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#18483fe")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#18483fe");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#18483fe",bean);
  bean.setMdfullname("uap.supfinance");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList4(){  List list = new ArrayList();  list.add(getSupFinanceFirstLineAction());  list.add(getSupFinancePreLineAction());  list.add(getSupFinanceNextLineAction());  list.add(getSupFinanceLastLineAction());  return list;}

public nc.ui.bd.pub.orginfo.model.OrgInfoCardDialogMediator getSupFinanceDialogMediator(){
 if(context.get("supFinanceDialogMediator")!=null)
 return (nc.ui.bd.pub.orginfo.model.OrgInfoCardDialogMediator)context.get("supFinanceDialogMediator");
  nc.ui.bd.pub.orginfo.model.OrgInfoCardDialogMediator bean = new nc.ui.bd.pub.orginfo.model.OrgInfoCardDialogMediator();
  context.put("supFinanceDialogMediator",bean);
  bean.setModel(getSupFinanceAppModel());
  bean.setDialogContainer(getSupFinanceDialogContainer());
  bean.setName(getI18nFB_ba4115());
  bean.setClosingListener(getSupFinanceClosingListener());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_ba4115(){
 if(context.get("nc.ui.uif2.I18nFB#ba4115")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#ba4115");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#ba4115",bean);  bean.setResDir("10140sub");
  bean.setDefaultValue("财务信息");
  bean.setResId("110140sub0011");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#ba4115",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.uif2.TangramContainer getSupFinanceDialogContainer(){
 if(context.get("supFinanceDialogContainer")!=null)
 return (nc.ui.uif2.TangramContainer)context.get("supFinanceDialogContainer");
  nc.ui.uif2.TangramContainer bean = new nc.ui.uif2.TangramContainer();
  context.put("supFinanceDialogContainer",bean);
  bean.setConstraints(getManagedList5());
  bean.setActions(getManagedList6());
  bean.setEditActions(getManagedList7());
  bean.setModel(getSupFinanceAppModel());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList5(){  List list = new ArrayList();  list.add(getTangramLayoutConstraint_12273c8());  return list;}

private nc.ui.uif2.tangramlayout.TangramLayoutConstraint getTangramLayoutConstraint_12273c8(){
 if(context.get("nc.ui.uif2.tangramlayout.TangramLayoutConstraint#12273c8")!=null)
 return (nc.ui.uif2.tangramlayout.TangramLayoutConstraint)context.get("nc.ui.uif2.tangramlayout.TangramLayoutConstraint#12273c8");
  nc.ui.uif2.tangramlayout.TangramLayoutConstraint bean = new nc.ui.uif2.tangramlayout.TangramLayoutConstraint();
  context.put("nc.ui.uif2.tangramlayout.TangramLayoutConstraint#12273c8",bean);
  bean.setNewComponent(getSupFinanceView());
  bean.setNewComponentName(getI18nFB_9a86d6());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_9a86d6(){
 if(context.get("nc.ui.uif2.I18nFB#9a86d6")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#9a86d6");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#9a86d6",bean);  bean.setResDir("10140sub");
  bean.setDefaultValue("财务信息");
  bean.setResId("110140sub0011");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#9a86d6",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList6(){  List list = new ArrayList();  list.add(getSupFinanceEditAction());  list.add(getSupFinanceDeleteAction());  list.add(getFinanceseparatorAction());  list.add(getSupFinanceRefreshSingleAction());  list.add(getFinanceseparatorAction());  list.add(getSupFiPrintActionGroup());  return list;}

private List getManagedList7(){  List list = new ArrayList();  list.add(getSupFinanceSaveAction());  list.add(getFinanceseparatorAction());  list.add(getSupFinanceCancelAction());  return list;}

public nc.funcnode.ui.action.SeparatorAction getFinanceseparatorAction(){
 if(context.get("financeseparatorAction")!=null)
 return (nc.funcnode.ui.action.SeparatorAction)context.get("financeseparatorAction");
  nc.funcnode.ui.action.SeparatorAction bean = new nc.funcnode.ui.action.SeparatorAction();
  context.put("financeseparatorAction",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.EditAction getSupFinanceEditAction(){
 if(context.get("supFinanceEditAction")!=null)
 return (nc.ui.uif2.actions.EditAction)context.get("supFinanceEditAction");
  nc.ui.uif2.actions.EditAction bean = new nc.ui.uif2.actions.EditAction();
  context.put("supFinanceEditAction",bean);
  bean.setModel(getSupFinanceAppModel());
  bean.setCode("FiEdit");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.DeleteAction getSupFinanceDeleteAction(){
 if(context.get("supFinanceDeleteAction")!=null)
 return (nc.ui.uif2.actions.DeleteAction)context.get("supFinanceDeleteAction");
  nc.ui.uif2.actions.DeleteAction bean = new nc.ui.uif2.actions.DeleteAction();
  context.put("supFinanceDeleteAction",bean);
  bean.setModel(getSupFinanceAppModel());
  bean.setCode("FiDel");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getList_PayFreezeActionGroup(){
 if(context.get("list_PayFreezeActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("list_PayFreezeActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("list_PayFreezeActionGroup",bean);
  bean.setActions(getManagedList8());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList8(){  List list = new ArrayList();  list.add(getList_payFreezeAction());  list.add(getList_unPayFreezeAction());  return list;}

public nc.funcnode.ui.action.GroupAction getList_MakeOutFreezeActionGroup(){
 if(context.get("list_MakeOutFreezeActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("list_MakeOutFreezeActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("list_MakeOutFreezeActionGroup",bean);
  bean.setActions(getManagedList9());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList9(){  List list = new ArrayList();  list.add(getList_makeoutfreezeAction());  list.add(getList_unmakeoutfreezeAction());  return list;}

public nc.ui.bd.supplier.finance.action.SupBatchPayFreezeAction getList_payFreezeAction(){
 if(context.get("list_payFreezeAction")!=null)
 return (nc.ui.bd.supplier.finance.action.SupBatchPayFreezeAction)context.get("list_payFreezeAction");
  nc.ui.bd.supplier.finance.action.SupBatchPayFreezeAction bean = new nc.ui.bd.supplier.finance.action.SupBatchPayFreezeAction();
  context.put("list_payFreezeAction",bean);
  bean.setModel(getSupFinanceAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.finance.action.SupBatchUnPayFreezeAction getList_unPayFreezeAction(){
 if(context.get("list_unPayFreezeAction")!=null)
 return (nc.ui.bd.supplier.finance.action.SupBatchUnPayFreezeAction)context.get("list_unPayFreezeAction");
  nc.ui.bd.supplier.finance.action.SupBatchUnPayFreezeAction bean = new nc.ui.bd.supplier.finance.action.SupBatchUnPayFreezeAction();
  context.put("list_unPayFreezeAction",bean);
  bean.setModel(getSupFinanceAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.finance.action.SupBatchMakeOutFreezeAction getList_makeoutfreezeAction(){
 if(context.get("list_makeoutfreezeAction")!=null)
 return (nc.ui.bd.supplier.finance.action.SupBatchMakeOutFreezeAction)context.get("list_makeoutfreezeAction");
  nc.ui.bd.supplier.finance.action.SupBatchMakeOutFreezeAction bean = new nc.ui.bd.supplier.finance.action.SupBatchMakeOutFreezeAction();
  context.put("list_makeoutfreezeAction",bean);
  bean.setModel(getSupFinanceAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.finance.action.SupBatchUnMakeOutFreezeAction getList_unmakeoutfreezeAction(){
 if(context.get("list_unmakeoutfreezeAction")!=null)
 return (nc.ui.bd.supplier.finance.action.SupBatchUnMakeOutFreezeAction)context.get("list_unmakeoutfreezeAction");
  nc.ui.bd.supplier.finance.action.SupBatchUnMakeOutFreezeAction bean = new nc.ui.bd.supplier.finance.action.SupBatchUnMakeOutFreezeAction();
  context.put("list_unmakeoutfreezeAction",bean);
  bean.setModel(getSupFinanceAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.RefreshAllAction getSupFinanceRefreshAction(){
 if(context.get("supFinanceRefreshAction")!=null)
 return (nc.ui.uif2.actions.RefreshAllAction)context.get("supFinanceRefreshAction");
  nc.ui.uif2.actions.RefreshAllAction bean = new nc.ui.uif2.actions.RefreshAllAction();
  context.put("supFinanceRefreshAction",bean);
  bean.setModel(getSupFinanceAppModel());
  bean.setManager(getSupFinanceModelDataManager());
  bean.setCode("FiBatchRefresh");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.RefreshSingleAction getSupFinanceRefreshSingleAction(){
 if(context.get("supFinanceRefreshSingleAction")!=null)
 return (nc.ui.uif2.actions.RefreshSingleAction)context.get("supFinanceRefreshSingleAction");
  nc.ui.uif2.actions.RefreshSingleAction bean = new nc.ui.uif2.actions.RefreshSingleAction();
  context.put("supFinanceRefreshSingleAction",bean);
  bean.setModel(getSupFinanceAppModel());
  bean.setCode("FiRefresh");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.SaveAction getSupFinanceSaveAction(){
 if(context.get("supFinanceSaveAction")!=null)
 return (nc.ui.uif2.actions.SaveAction)context.get("supFinanceSaveAction");
  nc.ui.uif2.actions.SaveAction bean = new nc.ui.uif2.actions.SaveAction();
  context.put("supFinanceSaveAction",bean);
  bean.setModel(getSupFinanceAppModel());
  bean.setEditor(getSupFinanceView());
  bean.setCode("FiSave");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.CancelAction getSupFinanceCancelAction(){
 if(context.get("supFinanceCancelAction")!=null)
 return (nc.ui.uif2.actions.CancelAction)context.get("supFinanceCancelAction");
  nc.ui.uif2.actions.CancelAction bean = new nc.ui.uif2.actions.CancelAction();
  context.put("supFinanceCancelAction",bean);
  bean.setModel(getSupFinanceAppModel());
  bean.setCode("FiCancel");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getSupFiListPrintActionGroup(){
 if(context.get("supFiListPrintActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("supFiListPrintActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("supFiListPrintActionGroup",bean);
  bean.setCode("printgroup");
  bean.setActions(getManagedList10());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList10(){  List list = new ArrayList();  list.add(getSupFiListTempletprintaction());  list.add(getSupFiListTempletprintpreviewaction());  list.add(getSupFiListOutputAction());  return list;}

public nc.ui.uif2.actions.TemplatePreviewAction getSupFiListTempletprintpreviewaction(){
 if(context.get("supFiListTempletprintpreviewaction")!=null)
 return (nc.ui.uif2.actions.TemplatePreviewAction)context.get("supFiListTempletprintpreviewaction");
  nc.ui.uif2.actions.TemplatePreviewAction bean = new nc.ui.uif2.actions.TemplatePreviewAction();
  context.put("supFiListTempletprintpreviewaction",bean);
  bean.setCode("FiTempPreview");
  bean.setModel(getSupFinanceAppModel());
  bean.setDatasource(getSupFiListDataSource());
  bean.setNodeKey("supfinancelist");
  bean.setPrintDlgParentConatiner(getSupFinanceListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.TemplatePrintAction getSupFiListTempletprintaction(){
 if(context.get("supFiListTempletprintaction")!=null)
 return (nc.ui.uif2.actions.TemplatePrintAction)context.get("supFiListTempletprintaction");
  nc.ui.uif2.actions.TemplatePrintAction bean = new nc.ui.uif2.actions.TemplatePrintAction();
  context.put("supFiListTempletprintaction",bean);
  bean.setCode("FiTempPrint");
  bean.setModel(getSupFinanceAppModel());
  bean.setDatasource(getSupFiListDataSource());
  bean.setNodeKey("supfinancelist");
  bean.setPrintDlgParentConatiner(getSupFinanceListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.OutputAction getSupFiListOutputAction(){
 if(context.get("supFiListOutputAction")!=null)
 return (nc.ui.uif2.actions.OutputAction)context.get("supFiListOutputAction");
  nc.ui.uif2.actions.OutputAction bean = new nc.ui.uif2.actions.OutputAction();
  context.put("supFiListOutputAction",bean);
  bean.setCode("FiOutput");
  bean.setModel(getSupFinanceAppModel());
  bean.setDatasource(getSupFiListDataSource());
  bean.setNodeKey("supfinancelist");
  bean.setPrintDlgParentConatiner(getSupFinanceListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.print.MetaDataAllDatasSource getSupFiListDataSource(){
 if(context.get("supFiListDataSource")!=null)
 return (nc.ui.bd.pub.actions.print.MetaDataAllDatasSource)context.get("supFiListDataSource");
  nc.ui.bd.pub.actions.print.MetaDataAllDatasSource bean = new nc.ui.bd.pub.actions.print.MetaDataAllDatasSource();
  context.put("supFiListDataSource",bean);
  bean.setModel(getSupFinanceAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getSupFiPrintActionGroup(){
 if(context.get("supFiPrintActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("supFiPrintActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("supFiPrintActionGroup",bean);
  bean.setCode("printgroup");
  bean.setActions(getManagedList11());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList11(){  List list = new ArrayList();  list.add(getSupFiTempletprintaction());  list.add(getSupFiTempletprintpreviewaction());  list.add(getSupFiOutputAction());  return list;}

public nc.ui.uif2.actions.TemplatePreviewAction getSupFiTempletprintpreviewaction(){
 if(context.get("supFiTempletprintpreviewaction")!=null)
 return (nc.ui.uif2.actions.TemplatePreviewAction)context.get("supFiTempletprintpreviewaction");
  nc.ui.uif2.actions.TemplatePreviewAction bean = new nc.ui.uif2.actions.TemplatePreviewAction();
  context.put("supFiTempletprintpreviewaction",bean);
  bean.setCode("FiTempPreview");
  bean.setModel(getSupFinanceAppModel());
  bean.setDatasource(getSupFiDataSource());
  bean.setNodeKey("supfinance");
  bean.setPrintDlgParentConatiner(getSupFinanceView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.TemplatePrintAction getSupFiTempletprintaction(){
 if(context.get("supFiTempletprintaction")!=null)
 return (nc.ui.uif2.actions.TemplatePrintAction)context.get("supFiTempletprintaction");
  nc.ui.uif2.actions.TemplatePrintAction bean = new nc.ui.uif2.actions.TemplatePrintAction();
  context.put("supFiTempletprintaction",bean);
  bean.setCode("FiTempPrint");
  bean.setModel(getSupFinanceAppModel());
  bean.setDatasource(getSupFiDataSource());
  bean.setNodeKey("supfinance");
  bean.setPrintDlgParentConatiner(getSupFinanceView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.OutputAction getSupFiOutputAction(){
 if(context.get("supFiOutputAction")!=null)
 return (nc.ui.uif2.actions.OutputAction)context.get("supFiOutputAction");
  nc.ui.uif2.actions.OutputAction bean = new nc.ui.uif2.actions.OutputAction();
  context.put("supFiOutputAction",bean);
  bean.setCode("FiOutput");
  bean.setModel(getSupFinanceAppModel());
  bean.setDatasource(getSupFiDataSource());
  bean.setNodeKey("supfinance");
  bean.setPrintDlgParentConatiner(getSupFinanceView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.print.MetaDataSingleSelectDataSource getSupFiDataSource(){
 if(context.get("supFiDataSource")!=null)
 return (nc.ui.bd.pub.actions.print.MetaDataSingleSelectDataSource)context.get("supFiDataSource");
  nc.ui.bd.pub.actions.print.MetaDataSingleSelectDataSource bean = new nc.ui.bd.pub.actions.print.MetaDataSingleSelectDataSource();
  context.put("supFiDataSource",bean);
  bean.setModel(getSupFinanceAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.FirstLineAction getSupFinanceFirstLineAction(){
 if(context.get("supFinanceFirstLineAction")!=null)
 return (nc.ui.uif2.actions.FirstLineAction)context.get("supFinanceFirstLineAction");
  nc.ui.uif2.actions.FirstLineAction bean = new nc.ui.uif2.actions.FirstLineAction();
  context.put("supFinanceFirstLineAction",bean);
  bean.setModel(getSupFinanceAppModel());
  bean.setCode("FiFirstLine");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.NextLineAction getSupFinanceNextLineAction(){
 if(context.get("supFinanceNextLineAction")!=null)
 return (nc.ui.uif2.actions.NextLineAction)context.get("supFinanceNextLineAction");
  nc.ui.uif2.actions.NextLineAction bean = new nc.ui.uif2.actions.NextLineAction();
  context.put("supFinanceNextLineAction",bean);
  bean.setModel(getSupFinanceAppModel());
  bean.setCode("FiNextLine");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.PreLineAction getSupFinancePreLineAction(){
 if(context.get("supFinancePreLineAction")!=null)
 return (nc.ui.uif2.actions.PreLineAction)context.get("supFinancePreLineAction");
  nc.ui.uif2.actions.PreLineAction bean = new nc.ui.uif2.actions.PreLineAction();
  context.put("supFinancePreLineAction",bean);
  bean.setModel(getSupFinanceAppModel());
  bean.setCode("FiPreLine");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.LastLineAction getSupFinanceLastLineAction(){
 if(context.get("supFinanceLastLineAction")!=null)
 return (nc.ui.uif2.actions.LastLineAction)context.get("supFinanceLastLineAction");
  nc.ui.uif2.actions.LastLineAction bean = new nc.ui.uif2.actions.LastLineAction();
  context.put("supFinanceLastLineAction",bean);
  bean.setModel(getSupFinanceAppModel());
  bean.setCode("FiLastLine");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.FunNodeClosingHandler getSupFinanceClosingListener(){
 if(context.get("supFinanceClosingListener")!=null)
 return (nc.ui.uif2.FunNodeClosingHandler)context.get("supFinanceClosingListener");
  nc.ui.uif2.FunNodeClosingHandler bean = new nc.ui.uif2.FunNodeClosingHandler();
  context.put("supFinanceClosingListener",bean);
  bean.setModel(getSupFinanceAppModel());
  bean.setSaveaction(getSupFinanceSaveAction());
  bean.setCancelaction(getSupFinanceCancelAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

}
