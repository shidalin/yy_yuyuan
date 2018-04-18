package nc.ui.bd.material.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class Material_Config_Base extends AbstractJavaBeanDefinition{
	private Map<String, Object> context = new HashMap();
public nc.vo.uif2.LoginContext getContext(){
 if(context.get("context")!=null)
 return (nc.vo.uif2.LoginContext)context.get("context");
  nc.vo.uif2.LoginContext bean = new nc.vo.uif2.LoginContext();
  context.put("context",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.uitabextend.ExinfoLoader getExinfoloader(){
 if(context.get("exinfoloader")!=null)
 return (nc.ui.bd.uitabextend.ExinfoLoader)context.get("exinfoloader");
  nc.ui.bd.uitabextend.ExinfoLoader bean = new nc.ui.bd.uitabextend.ExinfoLoader();
  context.put("exinfoloader",bean);
  bean.setCurrent_md_ID("10140MAG");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.uitabextend.UITabExtManager getUiTabExtMnger(){
 if(context.get("uiTabExtMnger")!=null)
 return (nc.ui.bd.uitabextend.UITabExtManager)context.get("uiTabExtMnger");
  nc.ui.bd.uitabextend.UITabExtManager bean = new nc.ui.bd.uitabextend.UITabExtManager();
  context.put("uiTabExtMnger",bean);
  bean.setTargetComponent(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

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

private List getManagedList0(){  List list = new ArrayList();  list.add(getTemplateContainer());  list.add(getUserdefitemContainer());  list.add(getQueryTemplateContainer());  return list;}

public nc.ui.uif2.editor.TemplateContainer getTemplateContainer(){
 if(context.get("templateContainer")!=null)
 return (nc.ui.uif2.editor.TemplateContainer)context.get("templateContainer");
  nc.ui.uif2.editor.TemplateContainer bean = new nc.ui.uif2.editor.TemplateContainer();
  context.put("templateContainer",bean);
  bean.setContext(getContext());
  bean.setNodeKeies(getManagedList1());
  bean.load();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList1(){  List list = new ArrayList();  list.add("base");  list.add("share");  list.add("asstframe");  list.add("assistant");  list.add("asstdefine");  list.add("approve");  return list;}

public nc.ui.uif2.userdefitem.UserDefItemContainer getUserdefitemContainer(){
 if(context.get("userdefitemContainer")!=null)
 return (nc.ui.uif2.userdefitem.UserDefItemContainer)context.get("userdefitemContainer");
  nc.ui.uif2.userdefitem.UserDefItemContainer bean = new nc.ui.uif2.userdefitem.UserDefItemContainer();
  context.put("userdefitemContainer",bean);
  bean.setContext(getContext());
  bean.setParams(getManagedList2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList2(){  List list = new ArrayList();  list.add(getQueryParam_14fa851());  list.add(getQueryParam_1d39e36());  list.add(getQueryParam_1346fef());  list.add(getQueryParam_14a59b0());  list.add(getQueryParam_11b68e8());  list.add(getQueryParam_847353());  list.add(getQueryParam_652fbb());  list.add(getQueryParam_151649e());  list.add(getQueryParam_16b1374());  list.add(getQueryParam_56e921());  list.add(getQueryParam_1cd0de7());  list.add(getQueryParam_12af714());  list.add(getQueryParam_fdbd76());  list.add(getQueryParam_1094ee9());  list.add(getQueryParam_1cc91a());  list.add(getQueryParam_940a9a());  list.add(getQueryParam_1051473());  list.add(getQueryParam_b079fc());  return list;}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_14fa851(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#14fa851")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#14fa851");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#14fa851",bean);
  bean.setMdfullname("uap.material");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_1d39e36(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#1d39e36")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#1d39e36");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#1d39e36",bean);
  bean.setRulecode("materialassistant");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_1346fef(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#1346fef")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#1346fef");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#1346fef",bean);
  bean.setMdfullname("uap.materialconvert");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_14a59b0(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#14a59b0")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#14a59b0");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#14a59b0",bean);
  bean.setMdfullname("uap.materialtaxtype");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_11b68e8(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#11b68e8")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#11b68e8");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#11b68e8",bean);
  bean.setMdfullname("uap.materialcost");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_847353(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#847353")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#847353");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#847353",bean);
  bean.setMdfullname("uap.materialcostmode");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_652fbb(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#652fbb")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#652fbb");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#652fbb",bean);
  bean.setMdfullname("uap.materialfi");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_151649e(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#151649e")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#151649e");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#151649e",bean);
  bean.setMdfullname("uap.materialsale");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_16b1374(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#16b1374")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#16b1374");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#16b1374",bean);
  bean.setMdfullname("uap.materialbindle");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_56e921(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#56e921")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#56e921");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#56e921",bean);
  bean.setMdfullname("uap.materialstock");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_1cd0de7(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#1cd0de7")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#1cd0de7");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#1cd0de7",bean);
  bean.setMdfullname("uap.materialwarh");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_12af714(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#12af714")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#12af714");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#12af714",bean);
  bean.setMdfullname("uap.materialpu");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_fdbd76(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#fdbd76")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#fdbd76");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#fdbd76",bean);
  bean.setMdfullname("uap.materialprod");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_1094ee9(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#1094ee9")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#1094ee9");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#1094ee9",bean);
  bean.setMdfullname("uap.materialplan");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_1cc91a(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#1cc91a")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#1cc91a");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#1cc91a",bean);
  bean.setMdfullname("uap.materialrepl");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_940a9a(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#940a9a")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#940a9a");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#940a9a",bean);
  bean.setMdfullname("uapbd.materialpfcc");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_1051473(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#1051473")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#1051473");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#1051473",bean);
  bean.setMdfullname("uapbd.materialpfc");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.userdefitem.QueryParam getQueryParam_b079fc(){
 if(context.get("nc.ui.uif2.userdefitem.QueryParam#b079fc")!=null)
 return (nc.ui.uif2.userdefitem.QueryParam)context.get("nc.ui.uif2.userdefitem.QueryParam#b079fc");
  nc.ui.uif2.userdefitem.QueryParam bean = new nc.ui.uif2.userdefitem.QueryParam();
  context.put("nc.ui.uif2.userdefitem.QueryParam#b079fc",bean);
  bean.setMdfullname("uapbd.materialpfcsub");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.editor.QueryTemplateContainer getQueryTemplateContainer(){
 if(context.get("queryTemplateContainer")!=null)
 return (nc.ui.uif2.editor.QueryTemplateContainer)context.get("queryTemplateContainer");
  nc.ui.uif2.editor.QueryTemplateContainer bean = new nc.ui.uif2.editor.QueryTemplateContainer();
  context.put("queryTemplateContainer",bean);
  bean.setContext(getContext());
  bean.setNodeKey("");
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

public nc.ui.bd.material.baseinfo.model.MaterialBaseInfoModel getBaseinfoModel(){
 if(context.get("baseinfoModel")!=null)
 return (nc.ui.bd.material.baseinfo.model.MaterialBaseInfoModel)context.get("baseinfoModel");
  nc.ui.bd.material.baseinfo.model.MaterialBaseInfoModel bean = new nc.ui.bd.material.baseinfo.model.MaterialBaseInfoModel();
  context.put("baseinfoModel",bean);
  bean.setBusinessObjectAdapterFactory(getBoadapterfacotry());
  bean.setService(getService());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.model.MaterialBaseInfoModelService getService(){
 if(context.get("service")!=null)
 return (nc.ui.bd.material.baseinfo.model.MaterialBaseInfoModelService)context.get("service");
  nc.ui.bd.material.baseinfo.model.MaterialBaseInfoModelService bean = new nc.ui.bd.material.baseinfo.model.MaterialBaseInfoModelService();
  context.put("service",bean);
  bean.setModel(getBaseinfoModel());
  bean.setExtendContext(getBaseExtendContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.model.MaterialFuncNodeInitDataListener getInitDataListener(){
 if(context.get("InitDataListener")!=null)
 return (nc.ui.bd.material.baseinfo.model.MaterialFuncNodeInitDataListener)context.get("InitDataListener");
  nc.ui.bd.material.baseinfo.model.MaterialFuncNodeInitDataListener bean = new nc.ui.bd.material.baseinfo.model.MaterialFuncNodeInitDataListener();
  context.put("InitDataListener",bean);
  bean.setModelDataManager(getModelDataManager());
  bean.setQueryAction(getQueryAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.model.MaterialListModelDataManager getModelDataManager(){
 if(context.get("modelDataManager")!=null)
 return (nc.ui.bd.material.baseinfo.model.MaterialListModelDataManager)context.get("modelDataManager");
  nc.ui.bd.material.baseinfo.model.MaterialListModelDataManager bean = new nc.ui.bd.material.baseinfo.model.MaterialListModelDataManager();
  context.put("modelDataManager",bean);
  bean.setPaginationModel(getPaginationModel());
  bean.setListModel(getBaseinfoModel());
  bean.setQueryAssignStatusService(getAssignService());
  bean.setAssignContext(getAssignContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.components.pagination.PaginationModel getPaginationModel(){
 if(context.get("paginationModel")!=null)
 return (nc.ui.uif2.components.pagination.PaginationModel)context.get("paginationModel");
  nc.ui.uif2.components.pagination.PaginationModel bean = new nc.ui.uif2.components.pagination.PaginationModel();
  context.put("paginationModel",bean);
  bean.init();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.ActionContributors getToftpanelActionContributors(){
 if(context.get("toftpanelActionContributors")!=null)
 return (nc.ui.uif2.actions.ActionContributors)context.get("toftpanelActionContributors");
  nc.ui.uif2.actions.ActionContributors bean = new nc.ui.uif2.actions.ActionContributors();
  context.put("toftpanelActionContributors",bean);
  bean.setContributors(getManagedList3());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList3(){  List list = new ArrayList();  list.add((nc.ui.uif2.actions.IActionContributor)findBeanInUIF2BeanFactory("baseinfoListViewActions"));  list.add((nc.ui.uif2.actions.IActionContributor)findBeanInUIF2BeanFactory("baseinfoEditorActions"));  return list;}

public nc.ui.uif2.components.pagination.PaginationBar getPaginationBar(){
 if(context.get("paginationBar")!=null)
 return (nc.ui.uif2.components.pagination.PaginationBar)context.get("paginationBar");
  nc.ui.uif2.components.pagination.PaginationBar bean = new nc.ui.uif2.components.pagination.PaginationBar();
  context.put("paginationBar",bean);
  bean.setPaginationModel(getPaginationModel());
  bean.setContext(getContext());
  bean.registeCallbak();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.editor.MaterialBaseInfoListView getBaseinfoListView(){
 if(context.get("baseinfoListView")!=null)
 return (nc.ui.bd.material.baseinfo.editor.MaterialBaseInfoListView)context.get("baseinfoListView");
  nc.ui.bd.material.baseinfo.editor.MaterialBaseInfoListView bean = new nc.ui.bd.material.baseinfo.editor.MaterialBaseInfoListView();
  context.put("baseinfoListView",bean);
  bean.setModel(getBaseinfoModel());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setNodekey("base");
  bean.setPos("head");
  bean.setMultiSelectionEnable(true);
  bean.setUserdefitemListPreparator(getUserdefitemListPreparator());
  bean.setSouth(getPaginationBar());
  bean.setNorth(getListInfoPnl());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefitemContainerListPreparator getUserdefitemListPreparator(){
 if(context.get("userdefitemListPreparator")!=null)
 return (nc.ui.uif2.editor.UserdefitemContainerListPreparator)context.get("userdefitemListPreparator");
  nc.ui.uif2.editor.UserdefitemContainerListPreparator bean = new nc.ui.uif2.editor.UserdefitemContainerListPreparator();
  context.put("userdefitemListPreparator",bean);
  bean.setContainer(getUserdefitemContainer());
  bean.setParams(getManagedList4());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList4(){  List list = new ArrayList();  list.add(getListUserdefitemQueryParam());  list.add(getListBodyUserdefitemQueryParam());  list.add(getListBodyTaxtypeUserdefitemQueryParam());  return list;}

private nc.ui.uif2.editor.UserdefQueryParam getListUserdefitemQueryParam(){
 if(context.get("listUserdefitemQueryParam")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("listUserdefitemQueryParam");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("listUserdefitemQueryParam",bean);
  bean.setMdfullname("uap.material");
  bean.setPos(0);
  bean.setPrefix("def");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefQueryParam getListBodyUserdefitemQueryParam(){
 if(context.get("listBodyUserdefitemQueryParam")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("listBodyUserdefitemQueryParam");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("listBodyUserdefitemQueryParam",bean);
  bean.setMdfullname("uap.materialconvert");
  bean.setPos(1);
  bean.setPrefix("def");
  bean.setTabcode("materialconvert");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefQueryParam getListBodyTaxtypeUserdefitemQueryParam(){
 if(context.get("listBodyTaxtypeUserdefitemQueryParam")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("listBodyTaxtypeUserdefitemQueryParam");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("listBodyTaxtypeUserdefitemQueryParam",bean);
  bean.setMdfullname("uap.materialtaxtype");
  bean.setPos(1);
  bean.setPrefix("def");
  bean.setTabcode("materialtaxtype");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel getListInfoPnl(){
 if(context.get("listInfoPnl")!=null)
 return (nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel)context.get("listInfoPnl");
  nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel bean = new nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel();
  context.put("listInfoPnl",bean);
  bean.setModel(getBaseinfoModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.AccessoryAction getAccessoryAction(){
 if(context.get("accessoryAction")!=null)
 return (nc.ui.bd.pub.actions.AccessoryAction)context.get("accessoryAction");
  nc.ui.bd.pub.actions.AccessoryAction bean = new nc.ui.bd.pub.actions.AccessoryAction();
  context.put("accessoryAction",bean);
  bean.setWindowListener(getAccessoryShowAction());
  bean.setModel(getBaseinfoModel());
  bean.setMetaDataID("c7dc0ccd-8872-4eee-8882-160e8f49dfad");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.attach.AttachAction getAccessoryShowAction(){
 if(context.get("accessoryShowAction")!=null)
 return (nc.ui.bd.attach.AttachAction)context.get("accessoryShowAction");
  nc.ui.bd.attach.AttachAction bean = new nc.ui.bd.attach.AttachAction();
  context.put("accessoryShowAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setView(getBaseinfoListView());
  bean.setPrefix("uapbd/c7dc0ccd-8872-4eee-8882-160e8f49dfad");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel getCardInfoPnl(){
 if(context.get("cardInfoPnl")!=null)
 return (nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel)context.get("cardInfoPnl");
  nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel bean = new nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel();
  context.put("cardInfoPnl",bean);
  bean.setActions(getManagedList5());
  bean.setTitleAction(getReturnaction());
  bean.setModel(getBaseinfoModel());
  bean.setDataTemplateAction(getDataTempletAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList5(){  List list = new ArrayList();  list.add(getAccessoryShowAction());  list.add(getActionsBarSeparator());  list.add(getFirstLineAction());  list.add(getPreLineAction());  list.add(getNextLineAction());  list.add(getLastLineAction());  return list;}

private nc.ui.pub.beans.ActionsBar.ActionsBarSeparator getActionsBarSeparator(){
 if(context.get("actionsBarSeparator")!=null)
 return (nc.ui.pub.beans.ActionsBar.ActionsBarSeparator)context.get("actionsBarSeparator");
  nc.ui.pub.beans.ActionsBar.ActionsBarSeparator bean = new nc.ui.pub.beans.ActionsBar.ActionsBarSeparator();
  context.put("actionsBarSeparator",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.FirstLineAction getFirstLineAction(){
 if(context.get("firstLineAction")!=null)
 return (nc.ui.uif2.actions.FirstLineAction)context.get("firstLineAction");
  nc.ui.uif2.actions.FirstLineAction bean = new nc.ui.uif2.actions.FirstLineAction();
  context.put("firstLineAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setExceptionHandler(getExceptionhandler());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.PreLineAction getPreLineAction(){
 if(context.get("preLineAction")!=null)
 return (nc.ui.uif2.actions.PreLineAction)context.get("preLineAction");
  nc.ui.uif2.actions.PreLineAction bean = new nc.ui.uif2.actions.PreLineAction();
  context.put("preLineAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setExceptionHandler(getExceptionhandler());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.NextLineAction getNextLineAction(){
 if(context.get("nextLineAction")!=null)
 return (nc.ui.uif2.actions.NextLineAction)context.get("nextLineAction");
  nc.ui.uif2.actions.NextLineAction bean = new nc.ui.uif2.actions.NextLineAction();
  context.put("nextLineAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setExceptionHandler(getExceptionhandler());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.LastLineAction getLastLineAction(){
 if(context.get("lastLineAction")!=null)
 return (nc.ui.uif2.actions.LastLineAction)context.get("lastLineAction");
  nc.ui.uif2.actions.LastLineAction bean = new nc.ui.uif2.actions.LastLineAction();
  context.put("lastLineAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setExceptionHandler(getExceptionhandler());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.bd.attach.UpdateAccAction getReturnaction(){
 if(context.get("returnaction")!=null)
 return (nc.ui.bd.attach.UpdateAccAction)context.get("returnaction");
  nc.ui.bd.attach.UpdateAccAction bean = new nc.ui.bd.attach.UpdateAccAction();
  context.put("returnaction",bean);
  bean.setGoComponent(getBaseinfoListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pub.datatemplet.action.DataTempletAction getDataTempletAction(){
 if(context.get("dataTempletAction")!=null)
 return (nc.ui.pub.datatemplet.action.DataTempletAction)context.get("dataTempletAction");
  nc.ui.pub.datatemplet.action.DataTempletAction bean = new nc.ui.pub.datatemplet.action.DataTempletAction();
  context.put("dataTempletAction",bean);
  bean.setTemplet(getDataTemplet());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.assign.multiorg.MultiOrgAssignVisitAction getMarorgvisitAction(){
 if(context.get("marorgvisitAction")!=null)
 return (nc.ui.bd.pub.assign.multiorg.MultiOrgAssignVisitAction)context.get("marorgvisitAction");
  nc.ui.bd.pub.assign.multiorg.MultiOrgAssignVisitAction bean = new nc.ui.bd.pub.assign.multiorg.MultiOrgAssignVisitAction();
  context.put("marorgvisitAction",bean);
  bean.setCode("marorgvisitaction");
  bean.setModel(getBaseinfoModel());
  bean.setFunnode("10140MORG");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pub.datatemplet.DataTemplet getDataTemplet(){
 if(context.get("dataTemplet")!=null)
 return (nc.ui.pub.datatemplet.DataTemplet)context.get("dataTemplet");
  nc.ui.pub.datatemplet.DataTemplet bean = new nc.ui.pub.datatemplet.DataTemplet();
  context.put("dataTemplet",bean);
  bean.setContext(getContext());
  bean.setConvert(getDataTempletConvert());
  bean.setValidation(getDataTempletValidation());
  bean.setDataTempletOperator(getDataTemplateOperator());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.validator.DataTemplateSaveValidator getDataTemplateOperator(){
 if(context.get("dataTemplateOperator")!=null)
 return (nc.ui.bd.material.baseinfo.validator.DataTemplateSaveValidator)context.get("dataTemplateOperator");
  nc.ui.bd.material.baseinfo.validator.DataTemplateSaveValidator bean = new nc.ui.bd.material.baseinfo.validator.DataTemplateSaveValidator();
  context.put("dataTemplateOperator",bean);
  bean.setEditor(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pub.datatemplet.DataTempletConvert getDataTempletConvert(){
 if(context.get("dataTempletConvert")!=null)
 return (nc.ui.pub.datatemplet.DataTempletConvert)context.get("dataTempletConvert");
  nc.ui.pub.datatemplet.DataTempletConvert bean = new nc.ui.pub.datatemplet.DataTempletConvert();
  context.put("dataTempletConvert",bean);
  bean.setEditor(getBaseinfoEditor());
  bean.setSetValueStrategy("one_to_many");
  bean.setAfterImportHandlers(getManagedList6());
  bean.setBeforeImportHandlers(getManagedList7());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList6(){  List list = new ArrayList();  list.add(getAfterImportHandler());  return list;}

private List getManagedList7(){  List list = new ArrayList();  list.add(getBeforeImportHandler());  return list;}

public nc.ui.bd.pub.datatemplet.SharePanelBeforeImport getBeforeImportHandler(){
 if(context.get("beforeImportHandler")!=null)
 return (nc.ui.bd.pub.datatemplet.SharePanelBeforeImport)context.get("beforeImportHandler");
  nc.ui.bd.pub.datatemplet.SharePanelBeforeImport bean = new nc.ui.bd.pub.datatemplet.SharePanelBeforeImport();
  context.put("beforeImportHandler",bean);
  bean.setEditor(getBaseinfoEditor());
  bean.setImportModel(getBaseinfoModel());
  bean.setContext(getContext());
  bean.setSharePanel(getSharepanel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.validator.MarAsstAfterImport getAfterImportHandler(){
 if(context.get("afterImportHandler")!=null)
 return (nc.ui.bd.material.baseinfo.validator.MarAsstAfterImport)context.get("afterImportHandler");
  nc.ui.bd.material.baseinfo.validator.MarAsstAfterImport bean = new nc.ui.bd.material.baseinfo.validator.MarAsstAfterImport();
  context.put("afterImportHandler",bean);
  bean.setEditor(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pub.datatemplet.DataTempletValidation getDataTempletValidation(){
 if(context.get("dataTempletValidation")!=null)
 return (nc.ui.pub.datatemplet.DataTempletValidation)context.get("dataTempletValidation");
  nc.ui.pub.datatemplet.DataTempletValidation bean = new nc.ui.pub.datatemplet.DataTempletValidation();
  context.put("dataTempletValidation",bean);
  bean.setEditor(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.editor.MaterialShareInfoEditor getSharepanel(){
 if(context.get("sharepanel")!=null)
 return (nc.ui.bd.material.baseinfo.editor.MaterialShareInfoEditor)context.get("sharepanel");
  nc.ui.bd.material.baseinfo.editor.MaterialShareInfoEditor bean = new nc.ui.bd.material.baseinfo.editor.MaterialShareInfoEditor();
  context.put("sharepanel",bean);
  bean.setModel(getBaseinfoModel());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setNodekey("share");
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.editor.MaterialBaseInfoEditor getBaseinfoEditor(){
 if(context.get("baseinfoEditor")!=null)
 return (nc.ui.bd.material.baseinfo.editor.MaterialBaseInfoEditor)context.get("baseinfoEditor");
  nc.ui.bd.material.baseinfo.editor.MaterialBaseInfoEditor bean = new nc.ui.bd.material.baseinfo.editor.MaterialBaseInfoEditor();
  context.put("baseinfoEditor",bean);
  bean.setModel(getBaseinfoModel());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setNodekey("base");
  bean.setShareInfoEditor(getSharepanel());
  bean.setClosingListener(getClosingListener());
  bean.setExceptionHandler(getExceptionhandler());
  bean.setAutoExecLoadRelationItem(false);
  bean.setUserdefitemPreparator(getUserdefitemContainerPreparator_3438e9());
  bean.setBodyActionMap(getManagedMap0());
  bean.setMarAsstPanel(getMarAsstPanel());
  bean.setRequestFocus(false);
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefitemContainerPreparator getUserdefitemContainerPreparator_3438e9(){
 if(context.get("nc.ui.uif2.editor.UserdefitemContainerPreparator#3438e9")!=null)
 return (nc.ui.uif2.editor.UserdefitemContainerPreparator)context.get("nc.ui.uif2.editor.UserdefitemContainerPreparator#3438e9");
  nc.ui.uif2.editor.UserdefitemContainerPreparator bean = new nc.ui.uif2.editor.UserdefitemContainerPreparator();
  context.put("nc.ui.uif2.editor.UserdefitemContainerPreparator#3438e9",bean);
  bean.setContainer(getUserdefitemContainer());
  bean.setParams(getManagedList8());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList8(){  List list = new ArrayList();  list.add(getCardUserdefitemQueryParam());  list.add(getCardBodyUserdefitemQueryParam());  list.add(getCardBodytaxtypeUserdefitemQueryParam());  return list;}

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

private nc.ui.uif2.editor.UserdefQueryParam getCardBodyUserdefitemQueryParam(){
 if(context.get("cardBodyUserdefitemQueryParam")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("cardBodyUserdefitemQueryParam");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("cardBodyUserdefitemQueryParam",bean);
  bean.setMdfullname("uap.materialconvert");
  bean.setPos(1);
  bean.setPrefix("def");
  bean.setTabcode("materialconvert");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.UserdefQueryParam getCardBodytaxtypeUserdefitemQueryParam(){
 if(context.get("cardBodytaxtypeUserdefitemQueryParam")!=null)
 return (nc.ui.uif2.editor.UserdefQueryParam)context.get("cardBodytaxtypeUserdefitemQueryParam");
  nc.ui.uif2.editor.UserdefQueryParam bean = new nc.ui.uif2.editor.UserdefQueryParam();
  context.put("cardBodytaxtypeUserdefitemQueryParam",bean);
  bean.setMdfullname("uap.materialtaxtype");
  bean.setPos(1);
  bean.setPrefix("def");
  bean.setTabcode("materialtaxtype");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private Map getManagedMap0(){  Map map = new HashMap();  map.put("materialconvert",getManagedList9());  map.put("materialtaxtype",getManagedList10());  map.put("materialassistant",getManagedList11());  return map;}

private List getManagedList9(){  List list = new ArrayList();  list.add(getAddLineAction_c1e851());  list.add(getInsertLineAction_1236050());  list.add(getDelLineAction_3f817());  list.add(getCopyLineAction_1c79c29());  list.add(getPasteLineAction_1763e0());  return list;}

private nc.ui.uif2.actions.AddLineAction getAddLineAction_c1e851(){
 if(context.get("nc.ui.uif2.actions.AddLineAction#c1e851")!=null)
 return (nc.ui.uif2.actions.AddLineAction)context.get("nc.ui.uif2.actions.AddLineAction#c1e851");
  nc.ui.uif2.actions.AddLineAction bean = new nc.ui.uif2.actions.AddLineAction();
  context.put("nc.ui.uif2.actions.AddLineAction#c1e851",bean);
  bean.setModel(getBaseinfoModel());
  bean.setCardpanel(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.InsertLineAction getInsertLineAction_1236050(){
 if(context.get("nc.ui.uif2.actions.InsertLineAction#1236050")!=null)
 return (nc.ui.uif2.actions.InsertLineAction)context.get("nc.ui.uif2.actions.InsertLineAction#1236050");
  nc.ui.uif2.actions.InsertLineAction bean = new nc.ui.uif2.actions.InsertLineAction();
  context.put("nc.ui.uif2.actions.InsertLineAction#1236050",bean);
  bean.setModel(getBaseinfoModel());
  bean.setCardpanel(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.DelLineAction getDelLineAction_3f817(){
 if(context.get("nc.ui.uif2.actions.DelLineAction#3f817")!=null)
 return (nc.ui.uif2.actions.DelLineAction)context.get("nc.ui.uif2.actions.DelLineAction#3f817");
  nc.ui.uif2.actions.DelLineAction bean = new nc.ui.uif2.actions.DelLineAction();
  context.put("nc.ui.uif2.actions.DelLineAction#3f817",bean);
  bean.setModel(getBaseinfoModel());
  bean.setCardpanel(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.CopyLineAction getCopyLineAction_1c79c29(){
 if(context.get("nc.ui.uif2.actions.CopyLineAction#1c79c29")!=null)
 return (nc.ui.uif2.actions.CopyLineAction)context.get("nc.ui.uif2.actions.CopyLineAction#1c79c29");
  nc.ui.uif2.actions.CopyLineAction bean = new nc.ui.uif2.actions.CopyLineAction();
  context.put("nc.ui.uif2.actions.CopyLineAction#1c79c29",bean);
  bean.setModel(getBaseinfoModel());
  bean.setCardpanel(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.PasteLineAction getPasteLineAction_1763e0(){
 if(context.get("nc.ui.uif2.actions.PasteLineAction#1763e0")!=null)
 return (nc.ui.uif2.actions.PasteLineAction)context.get("nc.ui.uif2.actions.PasteLineAction#1763e0");
  nc.ui.uif2.actions.PasteLineAction bean = new nc.ui.uif2.actions.PasteLineAction();
  context.put("nc.ui.uif2.actions.PasteLineAction#1763e0",bean);
  bean.setModel(getBaseinfoModel());
  bean.setCardpanel(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList10(){  List list = new ArrayList();  list.add(getAddLineAction_60b4d2());  list.add(getInsertLineAction_10c2bc9());  list.add(getDelLineAction_f1d095());  list.add(getCopyLineAction_1f6a9c8());  list.add(getPasteLineAction_d75800());  return list;}

private nc.ui.uif2.actions.AddLineAction getAddLineAction_60b4d2(){
 if(context.get("nc.ui.uif2.actions.AddLineAction#60b4d2")!=null)
 return (nc.ui.uif2.actions.AddLineAction)context.get("nc.ui.uif2.actions.AddLineAction#60b4d2");
  nc.ui.uif2.actions.AddLineAction bean = new nc.ui.uif2.actions.AddLineAction();
  context.put("nc.ui.uif2.actions.AddLineAction#60b4d2",bean);
  bean.setModel(getBaseinfoModel());
  bean.setCardpanel(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.InsertLineAction getInsertLineAction_10c2bc9(){
 if(context.get("nc.ui.uif2.actions.InsertLineAction#10c2bc9")!=null)
 return (nc.ui.uif2.actions.InsertLineAction)context.get("nc.ui.uif2.actions.InsertLineAction#10c2bc9");
  nc.ui.uif2.actions.InsertLineAction bean = new nc.ui.uif2.actions.InsertLineAction();
  context.put("nc.ui.uif2.actions.InsertLineAction#10c2bc9",bean);
  bean.setModel(getBaseinfoModel());
  bean.setCardpanel(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.DelLineAction getDelLineAction_f1d095(){
 if(context.get("nc.ui.uif2.actions.DelLineAction#f1d095")!=null)
 return (nc.ui.uif2.actions.DelLineAction)context.get("nc.ui.uif2.actions.DelLineAction#f1d095");
  nc.ui.uif2.actions.DelLineAction bean = new nc.ui.uif2.actions.DelLineAction();
  context.put("nc.ui.uif2.actions.DelLineAction#f1d095",bean);
  bean.setModel(getBaseinfoModel());
  bean.setCardpanel(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.CopyLineAction getCopyLineAction_1f6a9c8(){
 if(context.get("nc.ui.uif2.actions.CopyLineAction#1f6a9c8")!=null)
 return (nc.ui.uif2.actions.CopyLineAction)context.get("nc.ui.uif2.actions.CopyLineAction#1f6a9c8");
  nc.ui.uif2.actions.CopyLineAction bean = new nc.ui.uif2.actions.CopyLineAction();
  context.put("nc.ui.uif2.actions.CopyLineAction#1f6a9c8",bean);
  bean.setModel(getBaseinfoModel());
  bean.setCardpanel(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.PasteLineAction getPasteLineAction_d75800(){
 if(context.get("nc.ui.uif2.actions.PasteLineAction#d75800")!=null)
 return (nc.ui.uif2.actions.PasteLineAction)context.get("nc.ui.uif2.actions.PasteLineAction#d75800");
  nc.ui.uif2.actions.PasteLineAction bean = new nc.ui.uif2.actions.PasteLineAction();
  context.put("nc.ui.uif2.actions.PasteLineAction#d75800",bean);
  bean.setModel(getBaseinfoModel());
  bean.setCardpanel(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList11(){  List list = new ArrayList();  list.add(getMarAsstDefineAction_a96670());  return list;}

private nc.ui.bd.material.assistant.action.MarAsstDefineAction getMarAsstDefineAction_a96670(){
 if(context.get("nc.ui.bd.material.assistant.action.MarAsstDefineAction#a96670")!=null)
 return (nc.ui.bd.material.assistant.action.MarAsstDefineAction)context.get("nc.ui.bd.material.assistant.action.MarAsstDefineAction#a96670");
  nc.ui.bd.material.assistant.action.MarAsstDefineAction bean = new nc.ui.bd.material.assistant.action.MarAsstDefineAction();
  context.put("nc.ui.bd.material.assistant.action.MarAsstDefineAction#a96670",bean);
  bean.setModel(getBaseinfoModel());
  bean.setDialogMediator(getAsstDlgBaseMediator());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.view.BDEditorOkCancelDialogMediator getAsstDlgBaseMediator(){
 if(context.get("asstDlgBaseMediator")!=null)
 return (nc.ui.bd.pub.view.BDEditorOkCancelDialogMediator)context.get("asstDlgBaseMediator");
  nc.ui.bd.pub.view.BDEditorOkCancelDialogMediator bean = new nc.ui.bd.pub.view.BDEditorOkCancelDialogMediator();
  context.put("asstDlgBaseMediator",bean);
  bean.setName(getI18nFB_c461c8());
  bean.setModel(getMarAsstModel());
  bean.setEditor(getMarAsstEditor());
  bean.setSaveAction(getMarAsstDefineSaveAction_10012e5());
  bean.setCancelAction(getCancelAction_a3ee6c());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_c461c8(){
 if(context.get("nc.ui.uif2.I18nFB#c461c8")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#c461c8");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#c461c8",bean);  bean.setResDir("10140mag");
  bean.setDefaultValue("�������Զ���");
  bean.setResId("010140mag0191");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#c461c8",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private nc.ui.bd.material.assistant.action.MarAsstDefineSaveAction getMarAsstDefineSaveAction_10012e5(){
 if(context.get("nc.ui.bd.material.assistant.action.MarAsstDefineSaveAction#10012e5")!=null)
 return (nc.ui.bd.material.assistant.action.MarAsstDefineSaveAction)context.get("nc.ui.bd.material.assistant.action.MarAsstDefineSaveAction#10012e5");
  nc.ui.bd.material.assistant.action.MarAsstDefineSaveAction bean = new nc.ui.bd.material.assistant.action.MarAsstDefineSaveAction();
  context.put("nc.ui.bd.material.assistant.action.MarAsstDefineSaveAction#10012e5",bean);
  bean.setModel(getMarAsstModel());
  bean.setBaseInfoEditor(getBaseinfoEditor());
  bean.setMarAsstPanel(getMarAsstPanel());
  bean.setEditor(getMarAsstEditor());
  bean.setFrameEditor(getBillForm_837a23());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.editor.BillForm getBillForm_837a23(){
 if(context.get("nc.ui.uif2.editor.BillForm#837a23")!=null)
 return (nc.ui.uif2.editor.BillForm)context.get("nc.ui.uif2.editor.BillForm#837a23");
  nc.ui.uif2.editor.BillForm bean = new nc.ui.uif2.editor.BillForm();
  context.put("nc.ui.uif2.editor.BillForm#837a23",bean);
  bean.setModel(getBillManageModel_5039b1());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setNodekey("asstframe");
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.model.BillManageModel getBillManageModel_5039b1(){
 if(context.get("nc.ui.uif2.model.BillManageModel#5039b1")!=null)
 return (nc.ui.uif2.model.BillManageModel)context.get("nc.ui.uif2.model.BillManageModel#5039b1");
  nc.ui.uif2.model.BillManageModel bean = new nc.ui.uif2.model.BillManageModel();
  context.put("nc.ui.uif2.model.BillManageModel#5039b1",bean);
  bean.setBusinessObjectAdapterFactory(getBoadapterfacotry());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.CancelAction getCancelAction_a3ee6c(){
 if(context.get("nc.ui.uif2.actions.CancelAction#a3ee6c")!=null)
 return (nc.ui.uif2.actions.CancelAction)context.get("nc.ui.uif2.actions.CancelAction#a3ee6c");
  nc.ui.uif2.actions.CancelAction bean = new nc.ui.uif2.actions.CancelAction();
  context.put("nc.ui.uif2.actions.CancelAction#a3ee6c",bean);
  bean.setModel(getMarAsstModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.assistant.view.MarAsstQuickDefineEditor getMarAsstEditor(){
 if(context.get("marAsstEditor")!=null)
 return (nc.ui.bd.material.assistant.view.MarAsstQuickDefineEditor)context.get("marAsstEditor");
  nc.ui.bd.material.assistant.view.MarAsstQuickDefineEditor bean = new nc.ui.bd.material.assistant.view.MarAsstQuickDefineEditor();
  context.put("marAsstEditor",bean);
  bean.setModel(getMarAsstModel());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setNodekey("asstdefine");
  bean.setUserDefItemContainer(getUserdefitemContainer());
  bean.setMarAsstPanel(getMarAsstPanel());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.model.MaterialBaseInfoModel getMarAsstModel(){
 if(context.get("marAsstModel")!=null)
 return (nc.ui.bd.material.baseinfo.model.MaterialBaseInfoModel)context.get("marAsstModel");
  nc.ui.bd.material.baseinfo.model.MaterialBaseInfoModel bean = new nc.ui.bd.material.baseinfo.model.MaterialBaseInfoModel();
  context.put("marAsstModel",bean);
  bean.setBusinessObjectAdapterFactory(getBoadapterfacotry());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.assistant.view.MarAsstPanel getMarAsstPanel(){
 if(context.get("marAsstPanel")!=null)
 return (nc.ui.bd.material.assistant.view.MarAsstPanel)context.get("marAsstPanel");
  nc.ui.bd.material.assistant.view.MarAsstPanel bean = new nc.ui.bd.material.assistant.view.MarAsstPanel();
  context.put("marAsstPanel",bean);
  bean.setModel(getBaseinfoModel());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setNodekey("assistant");
  bean.setUserDefItemContainer(getUserdefitemContainer());
  bean.setRequestFocus(false);
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.SeparatorAction getSeparatorAction(){
 if(context.get("separatorAction")!=null)
 return (nc.funcnode.ui.action.SeparatorAction)context.get("separatorAction");
  nc.funcnode.ui.action.SeparatorAction bean = new nc.funcnode.ui.action.SeparatorAction();
  context.put("separatorAction",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.SwitchAction getSwitchBaseMediator(){
 if(context.get("switchBaseMediator")!=null)
 return (nc.ui.uif2.actions.SwitchAction)context.get("switchBaseMediator");
  nc.ui.uif2.actions.SwitchAction bean = new nc.ui.uif2.actions.SwitchAction();
  context.put("switchBaseMediator",bean);
  bean.setContext(getContext());
  bean.setComponent1(getBaseinfoListView());
  bean.setComponent2(getBaseinfoEditor());
  bean.init();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.action.MaterialAddAction getAddAction(){
 if(context.get("addAction")!=null)
 return (nc.ui.bd.material.baseinfo.action.MaterialAddAction)context.get("addAction");
  nc.ui.bd.material.baseinfo.action.MaterialAddAction bean = new nc.ui.bd.material.baseinfo.action.MaterialAddAction();
  context.put("addAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setDataTempletAction(getDataTempletAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.BDEditAction getEditAction(){
 if(context.get("editAction")!=null)
 return (nc.ui.bd.pub.actions.BDEditAction)context.get("editAction");
  nc.ui.bd.pub.actions.BDEditAction bean = new nc.ui.bd.pub.actions.BDEditAction();
  context.put("editAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setResourceCode("material");
  bean.setMdOperateCode("edit");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.BDAnsyDelAction getDeleteAction(){
 if(context.get("deleteAction")!=null)
 return (nc.ui.bd.pub.actions.BDAnsyDelAction)context.get("deleteAction");
  nc.ui.bd.pub.actions.BDAnsyDelAction bean = new nc.ui.bd.pub.actions.BDAnsyDelAction();
  context.put("deleteAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setResourceCode("material");
  bean.setMdOperateCode("delete");
  bean.setService(getService());
  bean.setRefreshaction(getRefreshAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.action.MaterialCopyAddAction getCopyaddAction(){
 if(context.get("copyaddAction")!=null)
 return (nc.ui.bd.material.baseinfo.action.MaterialCopyAddAction)context.get("copyaddAction");
  nc.ui.bd.material.baseinfo.action.MaterialCopyAddAction bean = new nc.ui.bd.material.baseinfo.action.MaterialCopyAddAction();
  context.put("copyaddAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setEditor(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getBatchUpdateGroupAction(){
 if(context.get("batchUpdateGroupAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("batchUpdateGroupAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("batchUpdateGroupAction",bean);
  bean.setCode("BatchUpdateGroup");
  bean.setActions(getManagedList12());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList12(){  List list = new ArrayList();  list.add(getBatchUpdateAction());  list.add(getBatchUpdateWizardAction());  list.add(getBatchUPdateRuleAction());  return list;}

public nc.ui.bd.pub.actions.BDBatchUpdateWizardAction getBatchUpdateWizardAction(){
 if(context.get("batchUpdateWizardAction")!=null)
 return (nc.ui.bd.pub.actions.BDBatchUpdateWizardAction)context.get("batchUpdateWizardAction");
  nc.ui.bd.pub.actions.BDBatchUpdateWizardAction bean = new nc.ui.bd.pub.actions.BDBatchUpdateWizardAction();
  context.put("batchUpdateWizardAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setMdId("c7dc0ccd-8872-4eee-8882-160e8f49dfad");
  bean.setQryTempNodeKey("");
  bean.setBillTempNodekey("assign");
  bean.setBillTemplatePkField("pk_material");
  bean.setQryService(getMaterialBatchQueryService_1d9d838());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.bd.material.baseinfo.model.MaterialBatchQueryService getMaterialBatchQueryService_1d9d838(){
 if(context.get("nc.ui.bd.material.baseinfo.model.MaterialBatchQueryService#1d9d838")!=null)
 return (nc.ui.bd.material.baseinfo.model.MaterialBatchQueryService)context.get("nc.ui.bd.material.baseinfo.model.MaterialBatchQueryService#1d9d838");
  nc.ui.bd.material.baseinfo.model.MaterialBatchQueryService bean = new nc.ui.bd.material.baseinfo.model.MaterialBatchQueryService();
  context.put("nc.ui.bd.material.baseinfo.model.MaterialBatchQueryService#1d9d838",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.BDBatchUpdateAction getBatchUpdateAction(){
 if(context.get("batchUpdateAction")!=null)
 return (nc.ui.bd.pub.actions.BDBatchUpdateAction)context.get("batchUpdateAction");
  nc.ui.bd.pub.actions.BDBatchUpdateAction bean = new nc.ui.bd.pub.actions.BDBatchUpdateAction();
  context.put("batchUpdateAction",bean);
  bean.setWizardAction(getBatchUpdateWizardAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.batchupdaterule.acitons.BatchUpdateRuleAction getBatchUPdateRuleAction(){
 if(context.get("batchUPdateRuleAction")!=null)
 return (nc.ui.bd.pub.batchupdaterule.acitons.BatchUpdateRuleAction)context.get("batchUPdateRuleAction");
  nc.ui.bd.pub.batchupdaterule.acitons.BatchUpdateRuleAction bean = new nc.ui.bd.pub.batchupdaterule.acitons.BatchUpdateRuleAction();
  context.put("batchUPdateRuleAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setContext(getContext());
  bean.setMdId("c7dc0ccd-8872-4eee-8882-160e8f49dfad");
  bean.setQryTempNodeKey("");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.action.MaterialSaveAction getSaveAction(){
 if(context.get("saveAction")!=null)
 return (nc.ui.bd.material.baseinfo.action.MaterialSaveAction)context.get("saveAction");
  nc.ui.bd.material.baseinfo.action.MaterialSaveAction bean = new nc.ui.bd.material.baseinfo.action.MaterialSaveAction();
  context.put("saveAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setEditor(getBaseinfoEditor());
  bean.setValidationService(getValidationService());
  bean.setInterceptor(getCompositeActionInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.action.MaterialSaveAddAction getSaveAddAction(){
 if(context.get("saveAddAction")!=null)
 return (nc.ui.bd.material.baseinfo.action.MaterialSaveAddAction)context.get("saveAddAction");
  nc.ui.bd.material.baseinfo.action.MaterialSaveAddAction bean = new nc.ui.bd.material.baseinfo.action.MaterialSaveAddAction();
  context.put("saveAddAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setEditor(getBaseinfoEditor());
  bean.setValidationService(getValidationService());
  bean.setAddAction(getAddAction());
  bean.setInterceptor(getCompositeActionInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.CompositeActionInterceptor getCompositeActionInterceptor(){
 if(context.get("compositeActionInterceptor")!=null)
 return (nc.ui.bd.pub.actions.CompositeActionInterceptor)context.get("compositeActionInterceptor");
  nc.ui.bd.pub.actions.CompositeActionInterceptor bean = new nc.ui.bd.pub.actions.CompositeActionInterceptor();
  context.put("compositeActionInterceptor",bean);
  bean.setInterceptors(getManagedList13());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList13(){  List list = new ArrayList();  list.add(getManageModeActionInterceptor());  list.add(getBdSaveActionInterceptor());  return list;}

public nc.ui.bd.pub.actions.BDSaveActionInterceptor getBdSaveActionInterceptor(){
 if(context.get("bdSaveActionInterceptor")!=null)
 return (nc.ui.bd.pub.actions.BDSaveActionInterceptor)context.get("bdSaveActionInterceptor");
  nc.ui.bd.pub.actions.BDSaveActionInterceptor bean = new nc.ui.bd.pub.actions.BDSaveActionInterceptor();
  context.put("bdSaveActionInterceptor",bean);
  bean.setEditor(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.ManageModeActionInterceptor getManageModeActionInterceptor(){
 if(context.get("manageModeActionInterceptor")!=null)
 return (nc.ui.bd.pub.actions.ManageModeActionInterceptor)context.get("manageModeActionInterceptor");
  nc.ui.bd.pub.actions.ManageModeActionInterceptor bean = new nc.ui.bd.pub.actions.ManageModeActionInterceptor();
  context.put("manageModeActionInterceptor",bean);
  bean.setModel(getBaseinfoModel());
  bean.setEditor(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.bs.uif2.validation.DefaultValidationService getValidationService(){
 if(context.get("validationService")!=null)
 return (nc.bs.uif2.validation.DefaultValidationService)context.get("validationService");
  nc.bs.uif2.validation.DefaultValidationService bean = new nc.bs.uif2.validation.DefaultValidationService();
  context.put("validationService",bean);
  bean.setValidators(getManagedList14());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList14(){  List list = new ArrayList();  list.add(getMaterialConvertValidatorForClient_32eec2());  list.add(getMaterialTaxtypeUniqueValidatorForClient_11e529b());  list.add(getMaterialMeasRateValidator_30a9a());  return list;}

private nc.ui.bd.material.baseinfo.validator.MaterialConvertValidatorForClient getMaterialConvertValidatorForClient_32eec2(){
 if(context.get("nc.ui.bd.material.baseinfo.validator.MaterialConvertValidatorForClient#32eec2")!=null)
 return (nc.ui.bd.material.baseinfo.validator.MaterialConvertValidatorForClient)context.get("nc.ui.bd.material.baseinfo.validator.MaterialConvertValidatorForClient#32eec2");
  nc.ui.bd.material.baseinfo.validator.MaterialConvertValidatorForClient bean = new nc.ui.bd.material.baseinfo.validator.MaterialConvertValidatorForClient();
  context.put("nc.ui.bd.material.baseinfo.validator.MaterialConvertValidatorForClient#32eec2",bean);
  bean.setModel(getBaseinfoModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.bd.material.baseinfo.validator.MaterialTaxtypeUniqueValidatorForClient getMaterialTaxtypeUniqueValidatorForClient_11e529b(){
 if(context.get("nc.ui.bd.material.baseinfo.validator.MaterialTaxtypeUniqueValidatorForClient#11e529b")!=null)
 return (nc.ui.bd.material.baseinfo.validator.MaterialTaxtypeUniqueValidatorForClient)context.get("nc.ui.bd.material.baseinfo.validator.MaterialTaxtypeUniqueValidatorForClient#11e529b");
  nc.ui.bd.material.baseinfo.validator.MaterialTaxtypeUniqueValidatorForClient bean = new nc.ui.bd.material.baseinfo.validator.MaterialTaxtypeUniqueValidatorForClient();
  context.put("nc.ui.bd.material.baseinfo.validator.MaterialTaxtypeUniqueValidatorForClient#11e529b",bean);
  bean.setModel(getBaseinfoModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.bs.bd.material.baseinfo.validator.MaterialMeasRateValidator getMaterialMeasRateValidator_30a9a(){
 if(context.get("nc.bs.bd.material.baseinfo.validator.MaterialMeasRateValidator#30a9a")!=null)
 return (nc.bs.bd.material.baseinfo.validator.MaterialMeasRateValidator)context.get("nc.bs.bd.material.baseinfo.validator.MaterialMeasRateValidator#30a9a");
  nc.bs.bd.material.baseinfo.validator.MaterialMeasRateValidator bean = new nc.bs.bd.material.baseinfo.validator.MaterialMeasRateValidator();
  context.put("nc.bs.bd.material.baseinfo.validator.MaterialMeasRateValidator#30a9a",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.action.MaterialCancelAction getCancelAction(){
 if(context.get("cancelAction")!=null)
 return (nc.ui.bd.material.baseinfo.action.MaterialCancelAction)context.get("cancelAction");
  nc.ui.bd.material.baseinfo.action.MaterialCancelAction bean = new nc.ui.bd.material.baseinfo.action.MaterialCancelAction();
  context.put("cancelAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setEditor(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.QueryAction getQueryAction(){
 if(context.get("queryAction")!=null)
 return (nc.ui.uif2.actions.QueryAction)context.get("queryAction");
  nc.ui.uif2.actions.QueryAction bean = new nc.ui.uif2.actions.QueryAction();
  context.put("queryAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setDataManager(getModelDataManager());
  bean.setTemplateContainer(getQueryTemplateContainer());
  bean.setQueryDelegator(getAutoShowDisableDataQueryDelagator_1e015fa());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.bd.pub.query.AutoShowDisableDataQueryDelagator getAutoShowDisableDataQueryDelagator_1e015fa(){
 if(context.get("nc.ui.bd.pub.query.AutoShowDisableDataQueryDelagator#1e015fa")!=null)
 return (nc.ui.bd.pub.query.AutoShowDisableDataQueryDelagator)context.get("nc.ui.bd.pub.query.AutoShowDisableDataQueryDelagator#1e015fa");
  nc.ui.bd.pub.query.AutoShowDisableDataQueryDelagator bean = new nc.ui.bd.pub.query.AutoShowDisableDataQueryDelagator();
  context.put("nc.ui.bd.pub.query.AutoShowDisableDataQueryDelagator#1e015fa",bean);
  bean.setContext(getContext());
  bean.setAction(getShowDisableAction());
  bean.setDatamanager(getModelDataManager());
  bean.setTemplateContainer(getQueryTemplateContainer());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.RefreshAction getRefreshAction(){
 if(context.get("refreshAction")!=null)
 return (nc.ui.uif2.actions.RefreshAction)context.get("refreshAction");
  nc.ui.uif2.actions.RefreshAction bean = new nc.ui.uif2.actions.RefreshAction();
  context.put("refreshAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setDataManager(getModelDataManager());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.MenuAction getFilterAction(){
 if(context.get("filterAction")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("filterAction");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("filterAction",bean);
  bean.setCode("ShowDisable");
  bean.setName(getI18nFB_bfaffa());
  bean.setActions(getManagedList15());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_bfaffa(){
 if(context.get("nc.ui.uif2.I18nFB#bfaffa")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#bfaffa");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#bfaffa",bean);  bean.setResDir("common");
  bean.setDefaultValue("����");
  bean.setResId("UCH069");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#bfaffa",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList15(){  List list = new ArrayList();  list.add(getShowDisableAction());  list.add(getFilterAssignOrgDataAction());  return list;}

public nc.ui.bd.pub.actions.BDFilterAssignOrgDataAction getFilterAssignOrgDataAction(){
 if(context.get("filterAssignOrgDataAction")!=null)
 return (nc.ui.bd.pub.actions.BDFilterAssignOrgDataAction)context.get("filterAssignOrgDataAction");
  nc.ui.bd.pub.actions.BDFilterAssignOrgDataAction bean = new nc.ui.bd.pub.actions.BDFilterAssignOrgDataAction();
  context.put("filterAssignOrgDataAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setQueryService(getModelDataManager());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.ShowDisableDataAction getShowDisableAction(){
 if(context.get("showDisableAction")!=null)
 return (nc.ui.uif2.actions.ShowDisableDataAction)context.get("showDisableAction");
  nc.ui.uif2.actions.ShowDisableDataAction bean = new nc.ui.uif2.actions.ShowDisableDataAction();
  context.put("showDisableAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setDataManager(getModelDataManager());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.RefreshSingleAction getRefreshCardAction(){
 if(context.get("refreshCardAction")!=null)
 return (nc.ui.uif2.actions.RefreshSingleAction)context.get("refreshCardAction");
  nc.ui.uif2.actions.RefreshSingleAction bean = new nc.ui.uif2.actions.RefreshSingleAction();
  context.put("refreshCardAction",bean);
  bean.setModel(getBaseinfoModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getAssignActionGroup(){
 if(context.get("assignActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("assignActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("assignActionGroup",bean);
  bean.setCode("AssignGroup");
  bean.setActions(getManagedList16());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList16(){  List list = new ArrayList();  list.add(getAssignAction());  list.add(getCancelAssignAction());  list.add(getAssignWizardAction());  list.add(getAssignStatusAction());  return list;}

public nc.ui.bd.pub.actions.BDAssignAction getAssignAction(){
 if(context.get("assignAction")!=null)
 return (nc.ui.bd.pub.actions.BDAssignAction)context.get("assignAction");
  nc.ui.bd.pub.actions.BDAssignAction bean = new nc.ui.bd.pub.actions.BDAssignAction();
  context.put("assignAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setAssignContext(getAssignContext());
  bean.setInterceptor(getManageModeActionInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.BDCancelAssignAction getCancelAssignAction(){
 if(context.get("CancelAssignAction")!=null)
 return (nc.ui.bd.pub.actions.BDCancelAssignAction)context.get("CancelAssignAction");
  nc.ui.bd.pub.actions.BDCancelAssignAction bean = new nc.ui.bd.pub.actions.BDCancelAssignAction();
  context.put("CancelAssignAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setAssignContext(getAssignContext());
  bean.setInterceptor(getManageModeActionInterceptor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.BDAssignWizardAction getAssignWizardAction(){
 if(context.get("AssignWizardAction")!=null)
 return (nc.ui.bd.pub.actions.BDAssignWizardAction)context.get("AssignWizardAction");
  nc.ui.bd.pub.actions.BDAssignWizardAction bean = new nc.ui.bd.pub.actions.BDAssignWizardAction();
  context.put("AssignWizardAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setAssignContext(getAssignContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.BDAssignStatusAction getAssignStatusAction(){
 if(context.get("AssignStatusAction")!=null)
 return (nc.ui.bd.pub.actions.BDAssignStatusAction)context.get("AssignStatusAction");
  nc.ui.bd.pub.actions.BDAssignStatusAction bean = new nc.ui.bd.pub.actions.BDAssignStatusAction();
  context.put("AssignStatusAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setQueryService(getAssignService());
  bean.setFunnode("10140MASTAT");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.assign.AssignContext getAssignContext(){
 if(context.get("assignContext")!=null)
 return (nc.ui.bd.pub.assign.AssignContext)context.get("assignContext");
  nc.ui.bd.pub.assign.AssignContext bean = new nc.ui.bd.pub.assign.AssignContext();
  context.put("assignContext",bean);
  bean.setAssignService(getAssignService());
  bean.setLogincontext(getContext());
  bean.setBillTempNodekey("assign");
  bean.setBillTemplatePkItemkey("pk_material");
  bean.setOrgTypeIDs(getManagedList17());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList17(){  List list = new ArrayList();  list.add("BUSINESSUNIT00000000");  list.add("COSTREGION0000000000");  list.add("LIACTCOSTRG000000000");  return list;}

public nc.ui.bd.material.baseinfo.model.MaterialAssignService getAssignService(){
 if(context.get("assignService")!=null)
 return (nc.ui.bd.material.baseinfo.model.MaterialAssignService)context.get("assignService");
  nc.ui.bd.material.baseinfo.model.MaterialAssignService bean = new nc.ui.bd.material.baseinfo.model.MaterialAssignService();
  context.put("assignService",bean);
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getList_enableGroupAction(){
 if(context.get("list_enableGroupAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("list_enableGroupAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("list_enableGroupAction",bean);
  bean.setCode("EnableGroup");
  bean.setActions(getManagedList18());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList18(){  List list = new ArrayList();  list.add(getList_enableAction());  list.add(getList_disableAction());  return list;}

public nc.ui.bd.material.baseinfo.action.MaterialBatchDisableAction getList_disableAction(){
 if(context.get("list_disableAction")!=null)
 return (nc.ui.bd.material.baseinfo.action.MaterialBatchDisableAction)context.get("list_disableAction");
  nc.ui.bd.material.baseinfo.action.MaterialBatchDisableAction bean = new nc.ui.bd.material.baseinfo.action.MaterialBatchDisableAction();
  context.put("list_disableAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setResourceCode("material");
  bean.setMdOperateCode("disable");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.action.MaterialBatchEnableAction getList_enableAction(){
 if(context.get("list_enableAction")!=null)
 return (nc.ui.bd.material.baseinfo.action.MaterialBatchEnableAction)context.get("list_enableAction");
  nc.ui.bd.material.baseinfo.action.MaterialBatchEnableAction bean = new nc.ui.bd.material.baseinfo.action.MaterialBatchEnableAction();
  context.put("list_enableAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setResourceCode("material");
  bean.setMdOperateCode("enable");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getCard_enableGroupAction(){
 if(context.get("card_enableGroupAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("card_enableGroupAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("card_enableGroupAction",bean);
  bean.setCode("EnableGroup");
  bean.setActions(getManagedList19());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList19(){  List list = new ArrayList();  list.add(getCard_enableAction());  list.add(getCard_disableAction());  return list;}

public nc.ui.bd.material.baseinfo.action.MaterialDisableAction getCard_disableAction(){
 if(context.get("card_disableAction")!=null)
 return (nc.ui.bd.material.baseinfo.action.MaterialDisableAction)context.get("card_disableAction");
  nc.ui.bd.material.baseinfo.action.MaterialDisableAction bean = new nc.ui.bd.material.baseinfo.action.MaterialDisableAction();
  context.put("card_disableAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setResourceCode("material");
  bean.setMdOperateCode("disable");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.action.MaterialEnableAction getCard_enableAction(){
 if(context.get("card_enableAction")!=null)
 return (nc.ui.bd.material.baseinfo.action.MaterialEnableAction)context.get("card_enableAction");
  nc.ui.bd.material.baseinfo.action.MaterialEnableAction bean = new nc.ui.bd.material.baseinfo.action.MaterialEnableAction();
  context.put("card_enableAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setResourceCode("material");
  bean.setMdOperateCode("enable");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.query.OrgBrowseAction getOrgBrowseAction(){
 if(context.get("orgBrowseAction")!=null)
 return (nc.ui.bd.pub.query.OrgBrowseAction)context.get("orgBrowseAction");
  nc.ui.bd.pub.query.OrgBrowseAction bean = new nc.ui.bd.pub.query.OrgBrowseAction();
  context.put("orgBrowseAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setFunnode("10140MAQ");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.action.MaterialAssociatePfAction getAssociatePfAction(){
 if(context.get("associatePfAction")!=null)
 return (nc.ui.bd.material.baseinfo.action.MaterialAssociatePfAction)context.get("associatePfAction");
  nc.ui.bd.material.baseinfo.action.MaterialAssociatePfAction bean = new nc.ui.bd.material.baseinfo.action.MaterialAssociatePfAction();
  context.put("associatePfAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setApproveModelName("approveModel");
  bean.setApproveXmlFilePath("nc/ui/bd/material/config/Material_Associate_pf.xml");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getListPrintActionGroup(){
 if(context.get("listPrintActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("listPrintActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("listPrintActionGroup",bean);
  bean.setActions(getManagedList20());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList20(){  List list = new ArrayList();  list.add(getListTempletprintAction());  list.add(getListTempletPreviewAction());  list.add(getListoutputAction());  return list;}

public nc.ui.bd.pub.actions.print.BDTemplatePaginationPreviewAction getListTempletPreviewAction(){
 if(context.get("listTempletPreviewAction")!=null)
 return (nc.ui.bd.pub.actions.print.BDTemplatePaginationPreviewAction)context.get("listTempletPreviewAction");
  nc.ui.bd.pub.actions.print.BDTemplatePaginationPreviewAction bean = new nc.ui.bd.pub.actions.print.BDTemplatePaginationPreviewAction();
  context.put("listTempletPreviewAction",bean);
  bean.setPrintAction(getListTempletprintAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.print.BDTemplatePaginationPrintAction getListTempletprintAction(){
 if(context.get("listTempletprintAction")!=null)
 return (nc.ui.bd.pub.actions.print.BDTemplatePaginationPrintAction)context.get("listTempletprintAction");
  nc.ui.bd.pub.actions.print.BDTemplatePaginationPrintAction bean = new nc.ui.bd.pub.actions.print.BDTemplatePaginationPrintAction();
  context.put("listTempletprintAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setNodeKey("baselist");
  bean.setPaginationModel(getPaginationModel());
  bean.setPrintFactory(getPrintFactory());
  bean.setPrintDlgParentConatiner(getBaseinfoListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.print.BDPaginationOutputAction getListoutputAction(){
 if(context.get("listoutputAction")!=null)
 return (nc.ui.bd.pub.actions.print.BDPaginationOutputAction)context.get("listoutputAction");
  nc.ui.bd.pub.actions.print.BDPaginationOutputAction bean = new nc.ui.bd.pub.actions.print.BDPaginationOutputAction();
  context.put("listoutputAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setNodeKey("baselist");
  bean.setPaginationModel(getPaginationModel());
  bean.setPrintFactory(getPrintFactory());
  bean.setPrintDlgParentConatiner(getBaseinfoListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.print.DefaultTemplatePagePrintFactory getPrintFactory(){
 if(context.get("printFactory")!=null)
 return (nc.ui.bd.pub.actions.print.DefaultTemplatePagePrintFactory)context.get("printFactory");
  nc.ui.bd.pub.actions.print.DefaultTemplatePagePrintFactory bean = new nc.ui.bd.pub.actions.print.DefaultTemplatePagePrintFactory();
  context.put("printFactory",bean);
  bean.setMdId("c7dc0ccd-8872-4eee-8882-160e8f49dfad");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getPrintActionGroup(){
 if(context.get("printActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("printActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("printActionGroup",bean);
  bean.setCode("PrintMenu");
  bean.setActions(getManagedList21());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList21(){  List list = new ArrayList();  list.add(getTempletPrintAction());  list.add(getTempletPreviewAction());  list.add(getOutputAction());  return list;}

public nc.ui.bd.pub.actions.print.BDTemplatePreviewAction getTempletPreviewAction(){
 if(context.get("templetPreviewAction")!=null)
 return (nc.ui.bd.pub.actions.print.BDTemplatePreviewAction)context.get("templetPreviewAction");
  nc.ui.bd.pub.actions.print.BDTemplatePreviewAction bean = new nc.ui.bd.pub.actions.print.BDTemplatePreviewAction();
  context.put("templetPreviewAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setNodeKey("basecard");
  bean.setPrintDlgParentConatiner(getBaseinfoEditor());
  bean.setDatasource(getDatasource());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.print.BDTemplatePrintAction getTempletPrintAction(){
 if(context.get("templetPrintAction")!=null)
 return (nc.ui.bd.pub.actions.print.BDTemplatePrintAction)context.get("templetPrintAction");
  nc.ui.bd.pub.actions.print.BDTemplatePrintAction bean = new nc.ui.bd.pub.actions.print.BDTemplatePrintAction();
  context.put("templetPrintAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setNodeKey("basecard");
  bean.setPrintDlgParentConatiner(getBaseinfoEditor());
  bean.setDatasource(getDatasource());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.OutputAction getOutputAction(){
 if(context.get("outputAction")!=null)
 return (nc.ui.uif2.actions.OutputAction)context.get("outputAction");
  nc.ui.uif2.actions.OutputAction bean = new nc.ui.uif2.actions.OutputAction();
  context.put("outputAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setNodeKey("basecard");
  bean.setPrintDlgParentConatiner(getBaseinfoEditor());
  bean.setDatasource(getDatasource());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.actions.print.MetaDataSingleSelectDataSource getDatasource(){
 if(context.get("datasource")!=null)
 return (nc.ui.bd.pub.actions.print.MetaDataSingleSelectDataSource)context.get("datasource");
  nc.ui.bd.pub.actions.print.MetaDataSingleSelectDataSource bean = new nc.ui.bd.pub.actions.print.MetaDataSingleSelectDataSource();
  context.put("datasource",bean);
  bean.setModel(getBaseinfoModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.action.MaterialUpgradeAction getUpgradeAction(){
 if(context.get("upgradeAction")!=null)
 return (nc.ui.bd.material.baseinfo.action.MaterialUpgradeAction)context.get("upgradeAction");
  nc.ui.bd.material.baseinfo.action.MaterialUpgradeAction bean = new nc.ui.bd.material.baseinfo.action.MaterialUpgradeAction();
  context.put("upgradeAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setPaginationModel(getPaginationModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.material.baseinfo.action.MaterialCreateVersionAction getCreateVersionAction(){
 if(context.get("createVersionAction")!=null)
 return (nc.ui.bd.material.baseinfo.action.MaterialCreateVersionAction)context.get("createVersionAction");
  nc.ui.bd.material.baseinfo.action.MaterialCreateVersionAction bean = new nc.ui.bd.material.baseinfo.action.MaterialCreateVersionAction();
  context.put("createVersionAction",bean);
  bean.setModel(getBaseinfoModel());
  bean.setEditor(getBaseinfoEditor());
  bean.setCardPanel(getCardInfoPnl());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.TangramContainer getContainer(){
 if(context.get("container")!=null)
 return (nc.ui.uif2.TangramContainer)context.get("container");
  nc.ui.uif2.TangramContainer bean = new nc.ui.uif2.TangramContainer();
  context.put("container",bean);
  bean.setTangramLayoutRoot(getTBNode_bff44f());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.TBNode getTBNode_bff44f(){
 if(context.get("nc.ui.uif2.tangramlayout.node.TBNode#bff44f")!=null)
 return (nc.ui.uif2.tangramlayout.node.TBNode)context.get("nc.ui.uif2.tangramlayout.node.TBNode#bff44f");
  nc.ui.uif2.tangramlayout.node.TBNode bean = new nc.ui.uif2.tangramlayout.node.TBNode();
  context.put("nc.ui.uif2.tangramlayout.node.TBNode#bff44f",bean);
  bean.setTabs(getManagedList22());
  bean.setShowMode("CardLayout");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList22(){  List list = new ArrayList();  list.add(getHSNode_bb795());  list.add(getVSNode_4b7eb4());  return list;}

private nc.ui.uif2.tangramlayout.node.HSNode getHSNode_bb795(){
 if(context.get("nc.ui.uif2.tangramlayout.node.HSNode#bb795")!=null)
 return (nc.ui.uif2.tangramlayout.node.HSNode)context.get("nc.ui.uif2.tangramlayout.node.HSNode#bb795");
  nc.ui.uif2.tangramlayout.node.HSNode bean = new nc.ui.uif2.tangramlayout.node.HSNode();
  context.put("nc.ui.uif2.tangramlayout.node.HSNode#bb795",bean);
  bean.setLeft(getCNode_1f0acbc());
  bean.setRight(getCNode_1453a02());
  bean.setDividerLocation(0.2f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1f0acbc(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1f0acbc")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1f0acbc");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1f0acbc",bean);
  bean.setComponent(getQueryAreaShell());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1453a02(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1453a02")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1453a02");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1453a02",bean);
  bean.setComponent(getBaseinfoListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_4b7eb4(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#4b7eb4")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#4b7eb4");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#4b7eb4",bean);
  bean.setUp(getCNode_12c0f91());
  bean.setDown(getVSNode_15f11b6());
  bean.setDividerLocation(30f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_12c0f91(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#12c0f91")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#12c0f91");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#12c0f91",bean);
  bean.setComponent(getCardInfoPnl());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_15f11b6(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#15f11b6")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#15f11b6");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#15f11b6",bean);
  bean.setUp(getCNode_1e3cd04());
  bean.setDown(getId());
  bean.setDividerLocation(30f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_1e3cd04(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#1e3cd04")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#1e3cd04");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#1e3cd04",bean);
  bean.setComponent(getSharepanel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.TBNode getId(){
 if(context.get("id")!=null)
 return (nc.ui.uif2.tangramlayout.node.TBNode)context.get("id");
  nc.ui.uif2.tangramlayout.node.TBNode bean = new nc.ui.uif2.tangramlayout.node.TBNode();
  context.put("id",bean);
  bean.setTabs(getManagedList23());
  bean.setTabbedPaneFetcher(getTabbedPaneFetcher());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList23(){  List list = new ArrayList();  list.add(getCNode_b4d836());  return list;}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_b4d836(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#b4d836")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#b4d836");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#b4d836",bean);
  bean.setName(getI18nFB_1626210());
  bean.setComponent(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1626210(){
 if(context.get("nc.ui.uif2.I18nFB#1626210")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1626210");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1626210",bean);  bean.setResDir("common");
  bean.setDefaultValue("������Ϣ");
  bean.setResId("UC000-0004309");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1626210",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.bd.pub.DefaultTabbedPaneEditableControl getTabbedPaneFetcher(){
 if(context.get("tabbedPaneFetcher")!=null)
 return (nc.ui.bd.pub.DefaultTabbedPaneEditableControl)context.get("tabbedPaneFetcher");
  nc.ui.bd.pub.DefaultTabbedPaneEditableControl bean = new nc.ui.bd.pub.DefaultTabbedPaneEditableControl();
  context.put("tabbedPaneFetcher",bean);
  bean.setModel(getBaseinfoModel());
  bean.setTargetComponent(getBaseinfoEditor());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.QueryAreaShell getQueryAreaShell(){
 if(context.get("queryAreaShell")!=null)
 return (nc.ui.uif2.actions.QueryAreaShell)context.get("queryAreaShell");
  nc.ui.uif2.actions.QueryAreaShell bean = new nc.ui.uif2.actions.QueryAreaShell();
  context.put("queryAreaShell",bean);
  bean.setQueryArea(getBdqueryActionBaseMediator_created_98e3c6());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.queryarea.QueryArea getBdqueryActionBaseMediator_created_98e3c6(){
 if(context.get("bdqueryActionBaseMediator.created#98e3c6")!=null)
 return (nc.ui.queryarea.QueryArea)context.get("bdqueryActionBaseMediator.created#98e3c6");
  nc.ui.queryarea.QueryArea bean = getBdqueryActionBaseMediator().createQueryArea();
  context.put("bdqueryActionBaseMediator.created#98e3c6",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.FunNodeClosingHandler getClosingListener(){
 if(context.get("ClosingListener")!=null)
 return (nc.ui.uif2.FunNodeClosingHandler)context.get("ClosingListener");
  nc.ui.uif2.FunNodeClosingHandler bean = new nc.ui.uif2.FunNodeClosingHandler();
  context.put("ClosingListener",bean);
  bean.setModel(getBaseinfoModel());
  bean.setSaveaction(getSaveAction());
  bean.setCancelaction(getCancelAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.DefaultExceptionHanler getExceptionhandler(){
 if(context.get("exceptionhandler")!=null)
 return (nc.ui.uif2.DefaultExceptionHanler)context.get("exceptionhandler");
  nc.ui.uif2.DefaultExceptionHanler bean = new nc.ui.uif2.DefaultExceptionHanler();
  context.put("exceptionhandler",bean);
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.extend.ExtendContext getBaseExtendContext(){
 if(context.get("baseExtendContext")!=null)
 return (nc.ui.bd.pub.extend.ExtendContext)context.get("baseExtendContext");
  nc.ui.bd.pub.extend.ExtendContext bean = new nc.ui.bd.pub.extend.ExtendContext();
  context.put("baseExtendContext",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.bd.pub.extend.ExtendOrgBaseUIProcessMediator getBaseExtendUIPrcBaseMediator(){
 if(context.get("baseExtendUIPrcBaseMediator")!=null)
 return (nc.ui.bd.pub.extend.ExtendOrgBaseUIProcessMediator)context.get("baseExtendUIPrcBaseMediator");
  nc.ui.bd.pub.extend.ExtendOrgBaseUIProcessMediator bean = new nc.ui.bd.pub.extend.ExtendOrgBaseUIProcessMediator();
  context.put("baseExtendUIPrcBaseMediator",bean);
  bean.setBaseEditor(getBaseinfoEditor());
  bean.setExtendContext(getBaseExtendContext());
  bean.setBaseTabName(getI18nFB_1fd9042());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1fd9042(){
 if(context.get("nc.ui.uif2.I18nFB#1fd9042")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1fd9042");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1fd9042",bean);  bean.setResDir("10140cub");
  bean.setDefaultValue("������Ϣ");
  bean.setResId("010140cub0037");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1fd9042",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

public nc.ui.bd.pub.tools.BDPubQueryActionMediator getBdqueryActionBaseMediator(){
 if(context.get("bdqueryActionBaseMediator")!=null)
 return (nc.ui.bd.pub.tools.BDPubQueryActionMediator)context.get("bdqueryActionBaseMediator");
  nc.ui.bd.pub.tools.BDPubQueryActionMediator bean = new nc.ui.bd.pub.tools.BDPubQueryActionMediator();
  context.put("bdqueryActionBaseMediator",bean);
  bean.setQueryAction(getQueryAction());
  bean.setOrgFieldCode(getManagedList24());
  bean.process();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList24(){  List list = new ArrayList();  list.add("pk_org");  return list;}

}
