package nc.vo.yuyuan.yy_errorlog;

import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

/**
 * <b> 此处简要描述此类功能 </b>
 * <p>
 *   此处添加累的描述信息
 * </p>
 *  创建日期:2017-1-5
 * @author YONYOU NC
 * @version NCPrj ??
 */
 
public class YyErrorLogVO extends SuperVO {
	
/**
*主键
*/
public java.lang.String pk_errorlog;
/**
*报错类型
*/
public java.lang.String errortyle;
/**
*报错日期
*/
public UFDate errordate;
/**
*报错信息
*/
public java.lang.String errorinfo;
/**
*报错单据组织
*/
public java.lang.String pk_org;
/**
*报错单据号
*/
public java.lang.String billno;
/**
*报错单据类型
*/
public java.lang.String bill;
/**
*报错单据制单人
*/
public java.lang.String billmaker;
/**
*报错单据日期
*/
public UFDate billdate;
/**
*报错子表行号
*/
public java.lang.String billbno;
/**
*报错子表物料
*/
public java.lang.String billbmaterial;
/**
*时间戳
*/
public UFDateTime ts;
    
    
/**
* 属性 pk_errorlog的Getter方法.属性名：主键
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getPk_errorlog() {
return this.pk_errorlog;
} 

/**
* 属性pk_errorlog的Setter方法.属性名：主键
* 创建日期:2017-1-5
* @param newPk_errorlog java.lang.String
*/
public void setPk_errorlog ( java.lang.String pk_errorlog) {
this.pk_errorlog=pk_errorlog;
} 
 
/**
* 属性 errortyle的Getter方法.属性名：报错类型
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getErrortyle() {
return this.errortyle;
} 

/**
* 属性errortyle的Setter方法.属性名：报错类型
* 创建日期:2017-1-5
* @param newErrortyle java.lang.String
*/
public void setErrortyle ( java.lang.String errortyle) {
this.errortyle=errortyle;
} 
 
/**
* 属性 errordate的Getter方法.属性名：报错日期
*  创建日期:2017-1-5
* @return nc.vo.pub.lang.UFDate
*/
public UFDate getErrordate() {
return this.errordate;
} 

/**
* 属性errordate的Setter方法.属性名：报错日期
* 创建日期:2017-1-5
* @param newErrordate nc.vo.pub.lang.UFDate
*/
public void setErrordate ( UFDate errordate) {
this.errordate=errordate;
} 
 
/**
* 属性 errorinfo的Getter方法.属性名：报错信息
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getErrorinfo() {
return this.errorinfo;
} 

/**
* 属性errorinfo的Setter方法.属性名：报错信息
* 创建日期:2017-1-5
* @param newErrorinfo java.lang.String
*/
public void setErrorinfo ( java.lang.String errorinfo) {
this.errorinfo=errorinfo;
} 
 
/**
* 属性 pk_org的Getter方法.属性名：报错单据组织
*  创建日期:2017-1-5
* @return nc.vo.org.PurchaseOrgVO
*/
public java.lang.String getPk_org() {
return this.pk_org;
} 

/**
* 属性pk_org的Setter方法.属性名：报错单据组织
* 创建日期:2017-1-5
* @param newPk_org nc.vo.org.PurchaseOrgVO
*/
public void setPk_org ( java.lang.String pk_org) {
this.pk_org=pk_org;
} 
 
/**
* 属性 billno的Getter方法.属性名：报错单据号
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getBillno() {
return this.billno;
} 

/**
* 属性billno的Setter方法.属性名：报错单据号
* 创建日期:2017-1-5
* @param newBillno java.lang.String
*/
public void setBillno ( java.lang.String billno) {
this.billno=billno;
} 
 
/**
* 属性 bill的Getter方法.属性名：报错单据类型
*  创建日期:2017-1-5
* @return nc.vo.pub.billtype.BilltypeVO
*/
public java.lang.String getBill() {
return this.bill;
} 

/**
* 属性bill的Setter方法.属性名：报错单据类型
* 创建日期:2017-1-5
* @param newBill nc.vo.pub.billtype.BilltypeVO
*/
public void setBill ( java.lang.String bill) {
this.bill=bill;
} 
 
/**
* 属性 billmaker的Getter方法.属性名：报错单据制单人
*  创建日期:2017-1-5
* @return nc.vo.sm.UserVO
*/
public java.lang.String getBillmaker() {
return this.billmaker;
} 

/**
* 属性billmaker的Setter方法.属性名：报错单据制单人
* 创建日期:2017-1-5
* @param newBillmaker nc.vo.sm.UserVO
*/
public void setBillmaker ( java.lang.String billmaker) {
this.billmaker=billmaker;
} 
 
/**
* 属性 billdate的Getter方法.属性名：报错单据日期
*  创建日期:2017-1-5
* @return nc.vo.pub.lang.UFDate
*/
public UFDate getBilldate() {
return this.billdate;
} 

/**
* 属性billdate的Setter方法.属性名：报错单据日期
* 创建日期:2017-1-5
* @param newBilldate nc.vo.pub.lang.UFDate
*/
public void setBilldate ( UFDate billdate) {
this.billdate=billdate;
} 
 
/**
* 属性 billbno的Getter方法.属性名：报错子表行号
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getBillbno() {
return this.billbno;
} 

/**
* 属性billbno的Setter方法.属性名：报错子表行号
* 创建日期:2017-1-5
* @param newBillbno java.lang.String
*/
public void setBillbno ( java.lang.String billbno) {
this.billbno=billbno;
} 
 
/**
* 属性 billbmaterial的Getter方法.属性名：报错子表物料
*  创建日期:2017-1-5
* @return nc.vo.bd.material.MaterialVO
*/
public java.lang.String getBillbmaterial() {
return this.billbmaterial;
} 

/**
* 属性billbmaterial的Setter方法.属性名：报错子表物料
* 创建日期:2017-1-5
* @param newBillbmaterial nc.vo.bd.material.MaterialVO
*/
public void setBillbmaterial ( java.lang.String billbmaterial) {
this.billbmaterial=billbmaterial;
} 
 
/**
* 属性 生成时间戳的Getter方法.属性名：时间戳
*  创建日期:2017-1-5
* @return nc.vo.pub.lang.UFDateTime
*/
public UFDateTime getTs() {
return this.ts;
}
/**
* 属性生成时间戳的Setter方法.属性名：时间戳
* 创建日期:2017-1-5
* @param newts nc.vo.pub.lang.UFDateTime
*/
public void setTs(UFDateTime ts){
this.ts=ts;
} 
     
    @Override
    public IVOMeta getMetaData() {
    return VOMetaFactory.getInstance().getVOMeta("yuyuan.YyErrorLogVO");
    }
   }
    