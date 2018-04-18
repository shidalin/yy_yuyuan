package nc.ui.bd.supplier.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class supplier_ob_finance_config extends AbstractJavaBeanDefinition{
	private Map<String, Object> context = new HashMap();
public nc.ui.bd.uitabextend.DefaultUIExtComponent getSupplier_ob_finance(){
 if(context.get("supplier_ob_finance")!=null)
 return (nc.ui.bd.uitabextend.DefaultUIExtComponent)context.get("supplier_ob_finance");
  nc.ui.bd.uitabextend.DefaultUIExtComponent bean = new nc.ui.bd.uitabextend.DefaultUIExtComponent();
  context.put("supplier_ob_finance",bean);
  bean.setActions(getManagedList0());
  bean.setExComponent(getFinanceEntry());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList0(){  List list = new ArrayList();  list.add(getObFinanceRefreshAction());  list.add((javax.swing.Action)findBeanInUIF2BeanFactory("separatorAction"));  list.add(getObFinancePrintGroupAction());  return list;}

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

private List getManagedList1(){  List list = new ArrayList();  list.add(getQueryParam_10d9a8());  return list;}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_10d9a8(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#10d9a8")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#10d9a8");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#10d9a8",bean);
  bean.setMdfullname("uap.supfinance");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.query.OrgQueryRefPanel getFinanceEntry(){
 if(context.get("financeEntry")!=null)
 return (nc.ui.bd.pub.query.OrgQueryRefPanel)context.get("financeEntry");
  nc.ui.bd.pub.query.OrgQueryRefPanel bean = new nc.ui.bd.pub.query.OrgQueryRefPanel();
  context.put("financeEntry",bean);
  bean.setModel(getObSupFinanceAppModel());
  bean.setDataManager(getFinanceModelDataManager());
  bean.setRefNodeName("财务组织");
  bean.setLabelName(getI18nFB_1272ddc());
  bean.setPageCode("finance_info");
  bean.setListView(getObSupFinanceListView());
  bean.setName(getI18nFB_b7bee3());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1272ddc(){
 if(context.get("nc.ui.uif2.I18nFB#1272ddc")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1272ddc");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1272ddc",bean);  bean.setResDir("10140sub");
  bean.setDefaultValue("财务组织");
  bean.setResId("110140sub0014");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1272ddc",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private java.lang.String getI18nFB_b7bee3(){
 if(context.get("nc.ui.uif2.I18nFB#b7bee3")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#b7bee3");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#b7bee3",bean);  bean.setResDir("10140sub");
  bean.setDefaultValue("财务信息");
  bean.setResId("110140sub0011");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#b7bee3",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.bd.supplier.finance.view.SupFinanceListView getObSupFinanceListView(){
 if(context.get("obSupFinanceListView")!=null)
 return (nc.ui.bd.supplier.finance.view.SupFinanceListView)context.get("obSupFinanceListView");
  nc.ui.bd.supplier.finance.view.SupFinanceListView bean = new nc.ui.bd.supplier.finance.view.SupFinanceListView();
  context.put("obSupFinanceListView",bean);
  bean.setTemplateContainer((nc.ui.uif2.editor.TemplateContainer)findBeanInUIF2BeanFactory("templateContainer"));
  bean.setModel(getObSupFinanceAppModel());
  bean.setNodekey("ob_finance");
  bean.setName(getI18nFB_66d46a());
  bean.setBillListPanelValueSetter(getBillListView_VOBillListPanelValueSetter_f78d17());
  bean.setUserdefitemListPreparator(getUserdefitemContainerListPreparator_1ede6c3());
  bean.setSouth(getFinancePaginationBar());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_66d46a(){
 if(context.get("nc.ui.uif2.I18nFB#66d46a")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#66d46a");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#66d46a",bean);  bean.setResDir("10140sub");
  bean.setDefaultValue("财务信息");
  bean.setResId("110140sub0011");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#66d46a",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.editor.BillListView.VOBillListPanelValueSetter getBillListView_VOBillListPanelValueSetter_f78d17(){
 if(context.get("nc.ui.uif2.editor.BillListView.VOBillListPanelValueSetter#f78d17")!=null)
 return (nc.ui.uif2.editor.BillListView.VOBillListPanelValueSetter)context.get("nc.ui.uif2.editor.BillListView.VOBillListPanelValueSetter#f78d17");
  nc.ui.uif2.editor.BillListView.VOBillListPanelValueSetter bean = new nc.ui.uif2.editor.BillListView.VOBillListPanelValueSetter();
  context.put("nc.ui.uif2.editor.BillListView.VOBillListPanelValueSetter#f78d17",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefitemContainerListPreparator getUserdefitemContainerListPreparator_1ede6c3(){
 if(context.get("nc.ui.uif2.editor.UserdefitemContainerListPreparator#1ede6c3")!=null)
 return (nc.ui.uif2.editor.UserdefitemContainerListPreparator)context.get("nc.ui.uif2.editor.UserdefitemContainerListPreparator#1ede6c3");
  nc.ui.uif2.editor.UserdefitemContainerListPreparator bean = new nc.ui.uif2.editor.UserdefitemContainerListPreparator();
  context.put("nc.ui.uif2.editor.UserdefitemContainerListPreparator#1ede6c3",bean);
  bean.setContainer((nc.ui.uif2.userdefitem.UserDefItemContainer)findBeanInUIF2BeanFactory("userdefitemContainer"));
  bean.setParams(getManagedList2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList2(){  List list = new ArrayList();  list.add(getUserdefQueryParam_f21dd8());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_f21dd8(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#f21dd8")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#f21dd8");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#f21dd8",bean);
  bean.setMdfullname("uap.supfinance");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.components.pagination.PaginationBar getFinancePaginationBar(){
 if(context.get("financePaginationBar")!=null)
 return (nc.ui.uif2.components.pagination.PaginationBar)context.get("financePaginationBar");
  nc.ui.uif2.components.pagination.PaginationBar bean = new nc.ui.uif2.components.pagination.PaginationBar();
  context.put("financePaginationBar",bean);
  bean.setPaginationModel(getFinancePaginationModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.components.pagination.PaginationModel getFinancePaginationModel(){
 if(context.get("financePaginationModel")!=null)
 return (nc.ui.uif2.components.pagination.PaginationModel)context.get("financePaginationModel");
  nc.ui.uif2.components.pagination.PaginationModel bean = new nc.ui.uif2.components.pagination.PaginationModel();
  context.put("financePaginationModel",bean);
  bean.setPaginationQueryService(getFinanceService());
  bean.init();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.components.pagination.BillManagePaginationDelegator getFinancePaginationDelegator(){
 if(context.get("financePaginationDelegator")!=null)
 return (nc.ui.uif2.components.pagination.BillManagePaginationDelegator)context.get("financePaginationDelegator");
  nc.ui.uif2.components.pagination.BillManagePaginationDelegator bean = new nc.ui.uif2.components.pagination.BillManagePaginationDelegator();
  context.put("financePaginationDelegator",bean);
  bean.setPaginationModel(getFinancePaginationModel());
  bean.setBillModel(getObSupFinanceAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.model.BillManageModel getObSupFinanceAppModel(){
 if(context.get("obSupFinanceAppModel")!=null)
 return (nc.ui.uif2.model.BillManageModel)context.get("obSupFinanceAppModel");
  nc.ui.uif2.model.BillManageModel bean = new nc.ui.uif2.model.BillManageModel();
  context.put("obSupFinanceAppModel",bean);
  bean.setContext((nc.vo.uif2.LoginContext)findBeanInUIF2BeanFactory("context"));
  bean.setBusinessObjectAdapterFactory(getGeneralBDObjectAdapterFactory_1f4b190());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.vo.bd.meta.GeneralBDObjectAdapterFactory getGeneralBDObjectAdapterFactory_1f4b190(){
 if(context.get("nc.vo.bd.meta.GeneralBDObjectAdapterFactory#1f4b190")!=null)
 return (nc.vo.bd.meta.GeneralBDObjectAdapterFactory)context.get("nc.vo.bd.meta.GeneralBDObjectAdapterFactory#1f4b190");
  nc.vo.bd.meta.GeneralBDObjectAdapterFactory bean = new nc.vo.bd.meta.GeneralBDObjectAdapterFactory();
  context.put("nc.vo.bd.meta.GeneralBDObjectAdapterFactory#1f4b190",bean);
  bean.setMode("VO");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.finance.model.SupFinanceOBDataManager getFinanceModelDataManager(){
 if(context.get("financeModelDataManager")!=null)
 return (nc.ui.bd.supplier.finance.model.SupFinanceOBDataManager)context.get("financeModelDataManager");
  nc.ui.bd.supplier.finance.model.SupFinanceOBDataManager bean = new nc.ui.bd.supplier.finance.model.SupFinanceOBDataManager();
  context.put("financeModelDataManager",bean);
  bean.setModel(getObSupFinanceAppModel());
  bean.setPaginationModel(getFinancePaginationModel());
  bean.setDelegator(getFinancePaginationDelegator());
  bean.setService(getFinanceService());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.finance.model.SupFinanceAppModelService getFinanceService(){
 if(context.get("financeService")!=null)
 return (nc.ui.bd.supplier.finance.model.SupFinanceAppModelService)context.get("financeService");
  nc.ui.bd.supplier.finance.model.SupFinanceAppModelService bean = new nc.ui.bd.supplier.finance.model.SupFinanceAppModelService();
  context.put("financeService",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.RefreshAllAction getObFinanceRefreshAction(){
 if(context.get("obFinanceRefreshAction")!=null)
 return (nc.ui.uif2.actions.RefreshAllAction)context.get("obFinanceRefreshAction");
  nc.ui.uif2.actions.RefreshAllAction bean = new nc.ui.uif2.actions.RefreshAllAction();
  context.put("obFinanceRefreshAction",bean);
  bean.setModel(getObSupFinanceAppModel());
  bean.setManager(getFinanceModelDataManager());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getObFinancePrintGroupAction(){
 if(context.get("obFinancePrintGroupAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("obFinancePrintGroupAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("obFinancePrintGroupAction",bean);
  bean.setCode("Print");
  bean.setActions(getManagedList3());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList3(){  List list = new ArrayList();  list.add(getObFinancePrintAction());  list.add(getObFinancePreviewPrintAction());  list.add(getObFinanceOutputAction());  return list;}

public nc.ui.bd.pub.actions.print.BDTemplatePaginationPrintAction getObFinancePrintAction(){
 if(context.get("obFinancePrintAction")!=null)
 return (nc.ui.bd.pub.actions.print.BDTemplatePaginationPrintAction)context.get("obFinancePrintAction");
  nc.ui.bd.pub.actions.print.BDTemplatePaginationPrintAction bean = new nc.ui.bd.pub.actions.print.BDTemplatePaginationPrintAction();
  context.put("obFinancePrintAction",bean);
  bean.setModel(getObSupFinanceAppModel());
  bean.setNodeKey("financequery");
  bean.setPaginationModel(getFinancePaginationModel());
  bean.setPrintFactory(getObFinancePrintFactory());
  bean.setPrintDlgParentConatiner(getObSupFinanceListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.print.BDTemplatePaginationPreviewAction getObFinancePreviewPrintAction(){
 if(context.get("obFinancePreviewPrintAction")!=null)
 return (nc.ui.bd.pub.actions.print.BDTemplatePaginationPreviewAction)context.get("obFinancePreviewPrintAction");
  nc.ui.bd.pub.actions.print.BDTemplatePaginationPreviewAction bean = new nc.ui.bd.pub.actions.print.BDTemplatePaginationPreviewAction();
  context.put("obFinancePreviewPrintAction",bean);
  bean.setPrintAction(getObFinancePrintAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.print.BDPaginationOutputAction getObFinanceOutputAction(){
 if(context.get("obFinanceOutputAction")!=null)
 return (nc.ui.bd.pub.actions.print.BDPaginationOutputAction)context.get("obFinanceOutputAction");
  nc.ui.bd.pub.actions.print.BDPaginationOutputAction bean = new nc.ui.bd.pub.actions.print.BDPaginationOutputAction();
  context.put("obFinanceOutputAction",bean);
  bean.setModel(getObSupFinanceAppModel());
  bean.setNodeKey("financequery");
  bean.setPaginationModel(getFinancePaginationModel());
  bean.setPrintFactory(getObFinancePrintFactory());
  bean.setPrintDlgParentConatiner(getObSupFinanceListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.print.DefaultTemplatePagePrintFactory getObFinancePrintFactory(){
 if(context.get("obFinancePrintFactory")!=null)
 return (nc.ui.bd.pub.actions.print.DefaultTemplatePagePrintFactory)context.get("obFinancePrintFactory");
  nc.ui.bd.pub.actions.print.DefaultTemplatePagePrintFactory bean = new nc.ui.bd.pub.actions.print.DefaultTemplatePagePrintFactory();
  context.put("obFinancePrintFactory",bean);
  bean.setMdId("3b6d7504-95e9-4557-94c5-f6b562509420");
  bean.setQryService(getOrgInfoPrintDataQryService_171168c());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.bd.pub.orginfo.model.OrgInfoPrintDataQryService getOrgInfoPrintDataQryService_171168c(){
 if(context.get("nc.ui.bd.pub.orginfo.model.OrgInfoPrintDataQryService#171168c")!=null)
 return (nc.ui.bd.pub.orginfo.model.OrgInfoPrintDataQryService)context.get("nc.ui.bd.pub.orginfo.model.OrgInfoPrintDataQryService#171168c");
  nc.ui.bd.pub.orginfo.model.OrgInfoPrintDataQryService bean = new nc.ui.bd.pub.orginfo.model.OrgInfoPrintDataQryService("3b6d7504-95e9-4557-94c5-f6b562509420");  context.put("nc.ui.bd.pub.orginfo.model.OrgInfoPrintDataQryService#171168c",bean);
  bean.setPaths(getManagedList4());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList4(){  List list = new ArrayList();  list.add("pk_supplier.pk_org.name");  list.add("pk_supplier.code");  list.add("pk_supplier.name");  return list;}

}
