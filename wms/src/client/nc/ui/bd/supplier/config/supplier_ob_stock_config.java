package nc.ui.bd.supplier.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class supplier_ob_stock_config extends AbstractJavaBeanDefinition{
	private Map<String, Object> context = new HashMap();
public nc.ui.bd.uitabextend.DefaultUIExtComponent getSupplier_ob_stock(){
 if(context.get("supplier_ob_stock")!=null)
 return (nc.ui.bd.uitabextend.DefaultUIExtComponent)context.get("supplier_ob_stock");
  nc.ui.bd.uitabextend.DefaultUIExtComponent bean = new nc.ui.bd.uitabextend.DefaultUIExtComponent();
  context.put("supplier_ob_stock",bean);
  bean.setActions(getManagedList0());
  bean.setExComponent(getStockEntry());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList0(){  List list = new ArrayList();  list.add(getObStockRefreshAction());  list.add((javax.swing.Action)findBeanInUIF2BeanFactory("separatorAction"));  list.add(getObStockPrintGroupAction());  return list;}

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

private List getManagedList1(){  List list = new ArrayList();  list.add(getQueryParam_1cfdef9());  return list;}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_1cfdef9(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#1cfdef9")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#1cfdef9");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#1cfdef9",bean);
  bean.setMdfullname("uap.supstock");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.query.OrgQueryRefPanel getStockEntry(){
 if(context.get("stockEntry")!=null)
 return (nc.ui.bd.pub.query.OrgQueryRefPanel)context.get("stockEntry");
  nc.ui.bd.pub.query.OrgQueryRefPanel bean = new nc.ui.bd.pub.query.OrgQueryRefPanel();
  context.put("stockEntry",bean);
  bean.setModel(getObSupStockAppModel());
  bean.setDataManager(getStockModelDataManager());
  bean.setRefNodeName("采购组织");
  bean.setLabelName(getI18nFB_712ccf());
  bean.setPageCode("stock_info");
  bean.setListView(getObSupStockListView());
  bean.setName(getI18nFB_363a02());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_712ccf(){
 if(context.get("nc.ui.uif2.I18nFB#712ccf")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#712ccf");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#712ccf",bean);  bean.setResDir("10140sub");
  bean.setDefaultValue("采购组织");
  bean.setResId("110140sub0015");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#712ccf",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private java.lang.String getI18nFB_363a02(){
 if(context.get("nc.ui.uif2.I18nFB#363a02")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#363a02");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#363a02",bean);  bean.setResDir("10140sub");
  bean.setDefaultValue("采购信息");
  bean.setResId("110140sub0008");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#363a02",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.bd.supplier.stock.view.SupStockListView getObSupStockListView(){
 if(context.get("obSupStockListView")!=null)
 return (nc.ui.bd.supplier.stock.view.SupStockListView)context.get("obSupStockListView");
  nc.ui.bd.supplier.stock.view.SupStockListView bean = new nc.ui.bd.supplier.stock.view.SupStockListView();
  context.put("obSupStockListView",bean);
  bean.setTemplateContainer((nc.ui.uif2.editor.TemplateContainer)findBeanInUIF2BeanFactory("templateContainer"));
  bean.setModel(getObSupStockAppModel());
  bean.setNodekey("ob_stock");
  bean.setName(getI18nFB_f1f0e2());
  bean.setBillListPanelValueSetter(getBillListView_VOBillListPanelValueSetter_154df93());
  bean.setUserdefitemListPreparator(getUserdefitemContainerListPreparator_19b41d3());
  bean.setSouth(getStockPaginationBar());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_f1f0e2(){
 if(context.get("nc.ui.uif2.I18nFB#f1f0e2")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#f1f0e2");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#f1f0e2",bean);  bean.setResDir("10140sub");
  bean.setDefaultValue("采购信息");
  bean.setResId("110140sub0008");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#f1f0e2",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.uif2.editor.BillListView.VOBillListPanelValueSetter getBillListView_VOBillListPanelValueSetter_154df93(){
 if(context.get("nc.ui.uif2.editor.BillListView.VOBillListPanelValueSetter#154df93")!=null)
 return (nc.ui.uif2.editor.BillListView.VOBillListPanelValueSetter)context.get("nc.ui.uif2.editor.BillListView.VOBillListPanelValueSetter#154df93");
  nc.ui.uif2.editor.BillListView.VOBillListPanelValueSetter bean = new nc.ui.uif2.editor.BillListView.VOBillListPanelValueSetter();
  context.put("nc.ui.uif2.editor.BillListView.VOBillListPanelValueSetter#154df93",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefitemContainerListPreparator getUserdefitemContainerListPreparator_19b41d3(){
 if(context.get("nc.ui.uif2.editor.UserdefitemContainerListPreparator#19b41d3")!=null)
 return (nc.ui.uif2.editor.UserdefitemContainerListPreparator)context.get("nc.ui.uif2.editor.UserdefitemContainerListPreparator#19b41d3");
  nc.ui.uif2.editor.UserdefitemContainerListPreparator bean = new nc.ui.uif2.editor.UserdefitemContainerListPreparator();
  context.put("nc.ui.uif2.editor.UserdefitemContainerListPreparator#19b41d3",bean);
  bean.setContainer((nc.ui.uif2.userdefitem.UserDefItemContainer)findBeanInUIF2BeanFactory("userdefitemContainer"));
  bean.setParams(getManagedList2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList2(){  List list = new ArrayList();  list.add(getUserdefQueryParam_cc8e22());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getUserdefQueryParam_cc8e22(){
 if(context.get("nc.ui.uif2.editor.UserdefQueryParam#cc8e22")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("nc.ui.uif2.editor.UserdefQueryParam#cc8e22");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("nc.ui.uif2.editor.UserdefQueryParam#cc8e22",bean);
  bean.setMdfullname("uap.supstock");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.model.BillManageModel getObSupStockAppModel(){
 if(context.get("obSupStockAppModel")!=null)
 return (nc.ui.uif2.model.BillManageModel)context.get("obSupStockAppModel");
  nc.ui.uif2.model.BillManageModel bean = new nc.ui.uif2.model.BillManageModel();
  context.put("obSupStockAppModel",bean);
  bean.setContext((nc.vo.uif2.LoginContext)findBeanInUIF2BeanFactory("context"));
  bean.setBusinessObjectAdapterFactory(getGeneralBDObjectAdapterFactory_6ec222());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.vo.bd.meta.GeneralBDObjectAdapterFactory getGeneralBDObjectAdapterFactory_6ec222(){
 if(context.get("nc.vo.bd.meta.GeneralBDObjectAdapterFactory#6ec222")!=null)
 return (nc.vo.bd.meta.GeneralBDObjectAdapterFactory)context.get("nc.vo.bd.meta.GeneralBDObjectAdapterFactory#6ec222");
  nc.vo.bd.meta.GeneralBDObjectAdapterFactory bean = new nc.vo.bd.meta.GeneralBDObjectAdapterFactory();
  context.put("nc.vo.bd.meta.GeneralBDObjectAdapterFactory#6ec222",bean);
  bean.setMode("VO");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.stock.model.SupStockOBDataManager getStockModelDataManager(){
 if(context.get("stockModelDataManager")!=null)
 return (nc.ui.bd.supplier.stock.model.SupStockOBDataManager)context.get("stockModelDataManager");
  nc.ui.bd.supplier.stock.model.SupStockOBDataManager bean = new nc.ui.bd.supplier.stock.model.SupStockOBDataManager();
  context.put("stockModelDataManager",bean);
  bean.setModel(getObSupStockAppModel());
  bean.setPaginationModel(getStockPaginationModel());
  bean.setDelegator(getStockPaginationDelegator());
  bean.setService(getStockService());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.supplier.stock.model.SupStockAppModelService getStockService(){
 if(context.get("stockService")!=null)
 return (nc.ui.bd.supplier.stock.model.SupStockAppModelService)context.get("stockService");
  nc.ui.bd.supplier.stock.model.SupStockAppModelService bean = new nc.ui.bd.supplier.stock.model.SupStockAppModelService();
  context.put("stockService",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.components.pagination.PaginationBar getStockPaginationBar(){
 if(context.get("stockPaginationBar")!=null)
 return (nc.ui.uif2.components.pagination.PaginationBar)context.get("stockPaginationBar");
  nc.ui.uif2.components.pagination.PaginationBar bean = new nc.ui.uif2.components.pagination.PaginationBar();
  context.put("stockPaginationBar",bean);
  bean.setPaginationModel(getStockPaginationModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.components.pagination.PaginationModel getStockPaginationModel(){
 if(context.get("stockPaginationModel")!=null)
 return (nc.ui.uif2.components.pagination.PaginationModel)context.get("stockPaginationModel");
  nc.ui.uif2.components.pagination.PaginationModel bean = new nc.ui.uif2.components.pagination.PaginationModel();
  context.put("stockPaginationModel",bean);
  bean.setPaginationQueryService(getStockService());
  bean.init();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.components.pagination.BillManagePaginationDelegator getStockPaginationDelegator(){
 if(context.get("stockPaginationDelegator")!=null)
 return (nc.ui.uif2.components.pagination.BillManagePaginationDelegator)context.get("stockPaginationDelegator");
  nc.ui.uif2.components.pagination.BillManagePaginationDelegator bean = new nc.ui.uif2.components.pagination.BillManagePaginationDelegator();
  context.put("stockPaginationDelegator",bean);
  bean.setPaginationModel(getStockPaginationModel());
  bean.setBillModel(getObSupStockAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.RefreshAllAction getObStockRefreshAction(){
 if(context.get("obStockRefreshAction")!=null)
 return (nc.ui.uif2.actions.RefreshAllAction)context.get("obStockRefreshAction");
  nc.ui.uif2.actions.RefreshAllAction bean = new nc.ui.uif2.actions.RefreshAllAction();
  context.put("obStockRefreshAction",bean);
  bean.setModel(getObSupStockAppModel());
  bean.setManager(getStockModelDataManager());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getObStockPrintGroupAction(){
 if(context.get("obStockPrintGroupAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("obStockPrintGroupAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("obStockPrintGroupAction",bean);
  bean.setCode("Print");
  bean.setActions(getManagedList3());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList3(){  List list = new ArrayList();  list.add(getObStockPrintAction());  list.add(getObStockPreviewAction());  list.add(getObStockOutputAction());  return list;}

public nc.ui.bd.pub.actions.print.BDTemplatePaginationPrintAction getObStockPrintAction(){
 if(context.get("obStockPrintAction")!=null)
 return (nc.ui.bd.pub.actions.print.BDTemplatePaginationPrintAction)context.get("obStockPrintAction");
  nc.ui.bd.pub.actions.print.BDTemplatePaginationPrintAction bean = new nc.ui.bd.pub.actions.print.BDTemplatePaginationPrintAction();
  context.put("obStockPrintAction",bean);
  bean.setModel(getObSupStockAppModel());
  bean.setNodeKey("purchasequery");
  bean.setPaginationModel(getStockPaginationModel());
  bean.setPrintFactory(getStockFactory());
  bean.setPrintDlgParentConatiner(getObSupStockListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.print.BDTemplatePaginationPreviewAction getObStockPreviewAction(){
 if(context.get("obStockPreviewAction")!=null)
 return (nc.ui.bd.pub.actions.print.BDTemplatePaginationPreviewAction)context.get("obStockPreviewAction");
  nc.ui.bd.pub.actions.print.BDTemplatePaginationPreviewAction bean = new nc.ui.bd.pub.actions.print.BDTemplatePaginationPreviewAction();
  context.put("obStockPreviewAction",bean);
  bean.setPrintAction(getObStockPrintAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.print.BDPaginationOutputAction getObStockOutputAction(){
 if(context.get("obStockOutputAction")!=null)
 return (nc.ui.bd.pub.actions.print.BDPaginationOutputAction)context.get("obStockOutputAction");
  nc.ui.bd.pub.actions.print.BDPaginationOutputAction bean = new nc.ui.bd.pub.actions.print.BDPaginationOutputAction();
  context.put("obStockOutputAction",bean);
  bean.setModel(getObSupStockAppModel());
  bean.setNodeKey("purchasequery");
  bean.setPaginationModel(getStockPaginationModel());
  bean.setPrintFactory(getStockFactory());
  bean.setPrintDlgParentConatiner(getObSupStockListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.print.DefaultTemplatePagePrintFactory getStockFactory(){
 if(context.get("stockFactory")!=null)
 return (nc.ui.bd.pub.actions.print.DefaultTemplatePagePrintFactory)context.get("stockFactory");
  nc.ui.bd.pub.actions.print.DefaultTemplatePagePrintFactory bean = new nc.ui.bd.pub.actions.print.DefaultTemplatePagePrintFactory();
  context.put("stockFactory",bean);
  bean.setMdId("92eed375-f4bb-4852-b537-e31c5269bab8");
  bean.setQryService(getOrgInfoPrintDataQryService_1f1287b());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.bd.pub.orginfo.model.OrgInfoPrintDataQryService getOrgInfoPrintDataQryService_1f1287b(){
 if(context.get("nc.ui.bd.pub.orginfo.model.OrgInfoPrintDataQryService#1f1287b")!=null)
 return (nc.ui.bd.pub.orginfo.model.OrgInfoPrintDataQryService)context.get("nc.ui.bd.pub.orginfo.model.OrgInfoPrintDataQryService#1f1287b");
  nc.ui.bd.pub.orginfo.model.OrgInfoPrintDataQryService bean = new nc.ui.bd.pub.orginfo.model.OrgInfoPrintDataQryService("92eed375-f4bb-4852-b537-e31c5269bab8");  context.put("nc.ui.bd.pub.orginfo.model.OrgInfoPrintDataQryService#1f1287b",bean);
  bean.setPaths(getManagedList4());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList4(){  List list = new ArrayList();  list.add("pk_supplier.pk_org.name");  list.add("pk_supplier.code");  list.add("pk_supplier.name");  return list;}

}
