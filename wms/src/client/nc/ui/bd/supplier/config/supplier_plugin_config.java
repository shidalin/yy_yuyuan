package nc.ui.bd.supplier.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class supplier_plugin_config extends AbstractJavaBeanDefinition{
	private Map<String, Object> context = new HashMap();
public nc.ui.bd.supplier.baseinfo.model.SupplierExtComponent getBodyActionMediator(){
 if(context.get("bodyActionMediator")!=null)
 return (nc.ui.bd.supplier.baseinfo.model.SupplierExtComponent)context.get("bodyActionMediator");
  nc.ui.bd.supplier.baseinfo.model.SupplierExtComponent bean = new nc.ui.bd.supplier.baseinfo.model.SupplierExtComponent();
  context.put("bodyActionMediator",bean);
  bean.setContainer((nc.ui.uif2.TangramContainer)findBeanInUIF2BeanFactory("container"));
  bean.setTargetComponent((javax.swing.JComponent)findBeanInUIF2BeanFactory("baseinfoCardView"));
  bean.setInsertPosition("body");
  bean.setBodyTabName("suplinkman");
  bean.setActions(getManagedList0());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList0(){  List list = new ArrayList();  list.add(getBatchAddLineAction_11b8d79());  list.add(getBatchCopyLineAction_5c598e());  list.add(getBatchDelLineAction_29bdf5());  list.add(getBatchInsLineAction_1135b83());  list.add(getBatchPasteLineAction_7e6b5d());  return list;}

private nc.ui.uif2.actions.batch.BatchAddLineAction getBatchAddLineAction_11b8d79(){
 if(context.get("nc.ui.uif2.actions.batch.BatchAddLineAction#11b8d79")!=null)
 return (nc.ui.uif2.actions.batch.BatchAddLineAction)context.get("nc.ui.uif2.actions.batch.BatchAddLineAction#11b8d79");
  nc.ui.uif2.actions.batch.BatchAddLineAction bean = new nc.ui.uif2.actions.batch.BatchAddLineAction();
  context.put("nc.ui.uif2.actions.batch.BatchAddLineAction#11b8d79",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.batch.BatchCopyLineAction getBatchCopyLineAction_5c598e(){
 if(context.get("nc.ui.uif2.actions.batch.BatchCopyLineAction#5c598e")!=null)
 return (nc.ui.uif2.actions.batch.BatchCopyLineAction)context.get("nc.ui.uif2.actions.batch.BatchCopyLineAction#5c598e");
  nc.ui.uif2.actions.batch.BatchCopyLineAction bean = new nc.ui.uif2.actions.batch.BatchCopyLineAction();
  context.put("nc.ui.uif2.actions.batch.BatchCopyLineAction#5c598e",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.batch.BatchDelLineAction getBatchDelLineAction_29bdf5(){
 if(context.get("nc.ui.uif2.actions.batch.BatchDelLineAction#29bdf5")!=null)
 return (nc.ui.uif2.actions.batch.BatchDelLineAction)context.get("nc.ui.uif2.actions.batch.BatchDelLineAction#29bdf5");
  nc.ui.uif2.actions.batch.BatchDelLineAction bean = new nc.ui.uif2.actions.batch.BatchDelLineAction();
  context.put("nc.ui.uif2.actions.batch.BatchDelLineAction#29bdf5",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.batch.BatchInsLineAction getBatchInsLineAction_1135b83(){
 if(context.get("nc.ui.uif2.actions.batch.BatchInsLineAction#1135b83")!=null)
 return (nc.ui.uif2.actions.batch.BatchInsLineAction)context.get("nc.ui.uif2.actions.batch.BatchInsLineAction#1135b83");
  nc.ui.uif2.actions.batch.BatchInsLineAction bean = new nc.ui.uif2.actions.batch.BatchInsLineAction();
  context.put("nc.ui.uif2.actions.batch.BatchInsLineAction#1135b83",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.actions.batch.BatchPasteLineAction getBatchPasteLineAction_7e6b5d(){
 if(context.get("nc.ui.uif2.actions.batch.BatchPasteLineAction#7e6b5d")!=null)
 return (nc.ui.uif2.actions.batch.BatchPasteLineAction)context.get("nc.ui.uif2.actions.batch.BatchPasteLineAction#7e6b5d");
  nc.ui.uif2.actions.batch.BatchPasteLineAction bean = new nc.ui.uif2.actions.batch.BatchPasteLineAction();
  context.put("nc.ui.uif2.actions.batch.BatchPasteLineAction#7e6b5d",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

}
