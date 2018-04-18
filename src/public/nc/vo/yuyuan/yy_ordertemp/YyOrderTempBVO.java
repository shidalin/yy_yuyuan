package nc.vo.yuyuan.yy_ordertemp;

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
 
public class YyOrderTempBVO extends SuperVO {
	
/**
*子表主键
*/
public java.lang.String pk_ordertemp_b;
/**
*集团
*/
public java.lang.String pk_group;
/**
*采购组织最新版本
*/
public java.lang.String pk_org;
/**
*采购组织
*/
public java.lang.String pk_org_v;
/**
*来源单据类型
*/
public java.lang.String csourcetypecode;
/**
*来源单据号
*/
public java.lang.String vsourcecode;
/**
*来源单据
*/
public java.lang.String csourceid;
/**
*来源单据明细
*/
public java.lang.String csourcebid;
/**
*来源单据行号
*/
public java.lang.String vsourcerowno;
/**
*源头单据类型
*/
public java.lang.String cfirsttypecode;
/**
*源头单据号
*/
public java.lang.String vfirstcode;
/**
*源头单据主表主键
*/
public java.lang.String cfirstid;
/**
*源头单据明细
*/
public java.lang.String cfirstbid;
/**
*源头单据行号
*/
public java.lang.String vfirstrowno;
/**
*备注
*/
public java.lang.String vmemo;
/**
*行号
*/
public java.lang.String crowno;
/**
*自定义项1
*/
public java.lang.String vdef1;
/**
*自定义项2
*/
public java.lang.String vdef2;
/**
*自定义项3
*/
public java.lang.String vdef3;
/**
*自定义项4
*/
public java.lang.String vdef4;
/**
*自定义项5
*/
public java.lang.String vdef5;
/**
*自定义项6
*/
public java.lang.String vdef6;
/**
*自定义项7
*/
public java.lang.String vdef7;
/**
*自定义项10
*/
public java.lang.String vdef10;
/**
*自定义项11
*/
public java.lang.String vdef11;
/**
*自定义项12
*/
public java.lang.String vdef12;
/**
*自定义项13
*/
public java.lang.String vdef13;
/**
*自定义项14
*/
public java.lang.String vdef14;
/**
*自定义项15
*/
public java.lang.String vdef15;
/**
*自定义项16
*/
public java.lang.String vdef16;
/**
*自定义项17
*/
public java.lang.String vdef17;
/**
*自定义项18
*/
public java.lang.String vdef18;
/**
*自定义项19
*/
public java.lang.String vdef19;
/**
*自定义项20
*/
public java.lang.String vdef20;
/**
*自定义项21
*/
public java.lang.String vdef21;
/**
*自定义项22
*/
public java.lang.String vdef22;
/**
*自定义项23
*/
public java.lang.String vdef23;
/**
*自定义项24
*/
public java.lang.String vdef24;
/**
*自定义项25
*/
public java.lang.String vdef25;
/**
*自定义项26
*/
public java.lang.String vdef26;
/**
*自定义项27
*/
public java.lang.String vdef27;
/**
*自定义项28
*/
public java.lang.String vdef28;
/**
*自定义项29
*/
public java.lang.String vdef29;
/**
*自定义项30
*/
public java.lang.String vdef30;
/**
*物料
*/
public java.lang.String cmaterialoid;
/**
*物料多版本
*/
public java.lang.String cmaterialvid;
/**
*上层单据主键
*/
public String pk_ordertemp;
/**
*时间戳
*/
public UFDateTime ts;
    
    
/**
* 属性 pk_ordertemp_b的Getter方法.属性名：子表主键
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getPk_ordertemp_b() {
return this.pk_ordertemp_b;
} 

/**
* 属性pk_ordertemp_b的Setter方法.属性名：子表主键
* 创建日期:2017-1-5
* @param newPk_ordertemp_b java.lang.String
*/
public void setPk_ordertemp_b ( java.lang.String pk_ordertemp_b) {
this.pk_ordertemp_b=pk_ordertemp_b;
} 
 
/**
* 属性 pk_group的Getter方法.属性名：集团
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getPk_group() {
return this.pk_group;
} 

/**
* 属性pk_group的Setter方法.属性名：集团
* 创建日期:2017-1-5
* @param newPk_group java.lang.String
*/
public void setPk_group ( java.lang.String pk_group) {
this.pk_group=pk_group;
} 
 
/**
* 属性 pk_org的Getter方法.属性名：采购组织最新版本
*  创建日期:2017-1-5
* @return nc.vo.org.PurchaseOrgVO
*/
public java.lang.String getPk_org() {
return this.pk_org;
} 

/**
* 属性pk_org的Setter方法.属性名：采购组织最新版本
* 创建日期:2017-1-5
* @param newPk_org nc.vo.org.PurchaseOrgVO
*/
public void setPk_org ( java.lang.String pk_org) {
this.pk_org=pk_org;
} 
 
/**
* 属性 pk_org_v的Getter方法.属性名：采购组织
*  创建日期:2017-1-5
* @return nc.vo.vorg.PurchaseOrgVersionVO
*/
public java.lang.String getPk_org_v() {
return this.pk_org_v;
} 

/**
* 属性pk_org_v的Setter方法.属性名：采购组织
* 创建日期:2017-1-5
* @param newPk_org_v nc.vo.vorg.PurchaseOrgVersionVO
*/
public void setPk_org_v ( java.lang.String pk_org_v) {
this.pk_org_v=pk_org_v;
} 
 
/**
* 属性 csourcetypecode的Getter方法.属性名：来源单据类型
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getCsourcetypecode() {
return this.csourcetypecode;
} 

/**
* 属性csourcetypecode的Setter方法.属性名：来源单据类型
* 创建日期:2017-1-5
* @param newCsourcetypecode java.lang.String
*/
public void setCsourcetypecode ( java.lang.String csourcetypecode) {
this.csourcetypecode=csourcetypecode;
} 
 
/**
* 属性 vsourcecode的Getter方法.属性名：来源单据号
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVsourcecode() {
return this.vsourcecode;
} 

/**
* 属性vsourcecode的Setter方法.属性名：来源单据号
* 创建日期:2017-1-5
* @param newVsourcecode java.lang.String
*/
public void setVsourcecode ( java.lang.String vsourcecode) {
this.vsourcecode=vsourcecode;
} 
 
/**
* 属性 csourceid的Getter方法.属性名：来源单据
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getCsourceid() {
return this.csourceid;
} 

/**
* 属性csourceid的Setter方法.属性名：来源单据
* 创建日期:2017-1-5
* @param newCsourceid java.lang.String
*/
public void setCsourceid ( java.lang.String csourceid) {
this.csourceid=csourceid;
} 
 
/**
* 属性 csourcebid的Getter方法.属性名：来源单据明细
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getCsourcebid() {
return this.csourcebid;
} 

/**
* 属性csourcebid的Setter方法.属性名：来源单据明细
* 创建日期:2017-1-5
* @param newCsourcebid java.lang.String
*/
public void setCsourcebid ( java.lang.String csourcebid) {
this.csourcebid=csourcebid;
} 
 
/**
* 属性 vsourcerowno的Getter方法.属性名：来源单据行号
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVsourcerowno() {
return this.vsourcerowno;
} 

/**
* 属性vsourcerowno的Setter方法.属性名：来源单据行号
* 创建日期:2017-1-5
* @param newVsourcerowno java.lang.String
*/
public void setVsourcerowno ( java.lang.String vsourcerowno) {
this.vsourcerowno=vsourcerowno;
} 
 
/**
* 属性 cfirsttypecode的Getter方法.属性名：源头单据类型
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getCfirsttypecode() {
return this.cfirsttypecode;
} 

/**
* 属性cfirsttypecode的Setter方法.属性名：源头单据类型
* 创建日期:2017-1-5
* @param newCfirsttypecode java.lang.String
*/
public void setCfirsttypecode ( java.lang.String cfirsttypecode) {
this.cfirsttypecode=cfirsttypecode;
} 
 
/**
* 属性 vfirstcode的Getter方法.属性名：源头单据号
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVfirstcode() {
return this.vfirstcode;
} 

/**
* 属性vfirstcode的Setter方法.属性名：源头单据号
* 创建日期:2017-1-5
* @param newVfirstcode java.lang.String
*/
public void setVfirstcode ( java.lang.String vfirstcode) {
this.vfirstcode=vfirstcode;
} 
 
/**
* 属性 cfirstid的Getter方法.属性名：源头单据主表主键
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getCfirstid() {
return this.cfirstid;
} 

/**
* 属性cfirstid的Setter方法.属性名：源头单据主表主键
* 创建日期:2017-1-5
* @param newCfirstid java.lang.String
*/
public void setCfirstid ( java.lang.String cfirstid) {
this.cfirstid=cfirstid;
} 
 
/**
* 属性 cfirstbid的Getter方法.属性名：源头单据明细
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getCfirstbid() {
return this.cfirstbid;
} 

/**
* 属性cfirstbid的Setter方法.属性名：源头单据明细
* 创建日期:2017-1-5
* @param newCfirstbid java.lang.String
*/
public void setCfirstbid ( java.lang.String cfirstbid) {
this.cfirstbid=cfirstbid;
} 
 
/**
* 属性 vfirstrowno的Getter方法.属性名：源头单据行号
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVfirstrowno() {
return this.vfirstrowno;
} 

/**
* 属性vfirstrowno的Setter方法.属性名：源头单据行号
* 创建日期:2017-1-5
* @param newVfirstrowno java.lang.String
*/
public void setVfirstrowno ( java.lang.String vfirstrowno) {
this.vfirstrowno=vfirstrowno;
} 
 
/**
* 属性 vmemo的Getter方法.属性名：备注
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVmemo() {
return this.vmemo;
} 

/**
* 属性vmemo的Setter方法.属性名：备注
* 创建日期:2017-1-5
* @param newVmemo java.lang.String
*/
public void setVmemo ( java.lang.String vmemo) {
this.vmemo=vmemo;
} 
 
/**
* 属性 crowno的Getter方法.属性名：行号
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getCrowno() {
return this.crowno;
} 

/**
* 属性crowno的Setter方法.属性名：行号
* 创建日期:2017-1-5
* @param newCrowno java.lang.String
*/
public void setCrowno ( java.lang.String crowno) {
this.crowno=crowno;
} 
 
/**
* 属性 vdef1的Getter方法.属性名：自定义项1
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef1() {
return this.vdef1;
} 

/**
* 属性vdef1的Setter方法.属性名：自定义项1
* 创建日期:2017-1-5
* @param newVdef1 java.lang.String
*/
public void setVdef1 ( java.lang.String vdef1) {
this.vdef1=vdef1;
} 
 
/**
* 属性 vdef2的Getter方法.属性名：自定义项2
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef2() {
return this.vdef2;
} 

/**
* 属性vdef2的Setter方法.属性名：自定义项2
* 创建日期:2017-1-5
* @param newVdef2 java.lang.String
*/
public void setVdef2 ( java.lang.String vdef2) {
this.vdef2=vdef2;
} 
 
/**
* 属性 vdef3的Getter方法.属性名：自定义项3
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef3() {
return this.vdef3;
} 

/**
* 属性vdef3的Setter方法.属性名：自定义项3
* 创建日期:2017-1-5
* @param newVdef3 java.lang.String
*/
public void setVdef3 ( java.lang.String vdef3) {
this.vdef3=vdef3;
} 
 
/**
* 属性 vdef4的Getter方法.属性名：自定义项4
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef4() {
return this.vdef4;
} 

/**
* 属性vdef4的Setter方法.属性名：自定义项4
* 创建日期:2017-1-5
* @param newVdef4 java.lang.String
*/
public void setVdef4 ( java.lang.String vdef4) {
this.vdef4=vdef4;
} 
 
/**
* 属性 vdef5的Getter方法.属性名：自定义项5
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef5() {
return this.vdef5;
} 

/**
* 属性vdef5的Setter方法.属性名：自定义项5
* 创建日期:2017-1-5
* @param newVdef5 java.lang.String
*/
public void setVdef5 ( java.lang.String vdef5) {
this.vdef5=vdef5;
} 
 
/**
* 属性 vdef6的Getter方法.属性名：自定义项6
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef6() {
return this.vdef6;
} 

/**
* 属性vdef6的Setter方法.属性名：自定义项6
* 创建日期:2017-1-5
* @param newVdef6 java.lang.String
*/
public void setVdef6 ( java.lang.String vdef6) {
this.vdef6=vdef6;
} 
 
/**
* 属性 vdef7的Getter方法.属性名：自定义项7
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef7() {
return this.vdef7;
} 

/**
* 属性vdef7的Setter方法.属性名：自定义项7
* 创建日期:2017-1-5
* @param newVdef7 java.lang.String
*/
public void setVdef7 ( java.lang.String vdef7) {
this.vdef7=vdef7;
} 
 
/**
* 属性 vdef10的Getter方法.属性名：自定义项10
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef10() {
return this.vdef10;
} 

/**
* 属性vdef10的Setter方法.属性名：自定义项10
* 创建日期:2017-1-5
* @param newVdef10 java.lang.String
*/
public void setVdef10 ( java.lang.String vdef10) {
this.vdef10=vdef10;
} 
 
/**
* 属性 vdef11的Getter方法.属性名：自定义项11
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef11() {
return this.vdef11;
} 

/**
* 属性vdef11的Setter方法.属性名：自定义项11
* 创建日期:2017-1-5
* @param newVdef11 java.lang.String
*/
public void setVdef11 ( java.lang.String vdef11) {
this.vdef11=vdef11;
} 
 
/**
* 属性 vdef12的Getter方法.属性名：自定义项12
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef12() {
return this.vdef12;
} 

/**
* 属性vdef12的Setter方法.属性名：自定义项12
* 创建日期:2017-1-5
* @param newVdef12 java.lang.String
*/
public void setVdef12 ( java.lang.String vdef12) {
this.vdef12=vdef12;
} 
 
/**
* 属性 vdef13的Getter方法.属性名：自定义项13
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef13() {
return this.vdef13;
} 

/**
* 属性vdef13的Setter方法.属性名：自定义项13
* 创建日期:2017-1-5
* @param newVdef13 java.lang.String
*/
public void setVdef13 ( java.lang.String vdef13) {
this.vdef13=vdef13;
} 
 
/**
* 属性 vdef14的Getter方法.属性名：自定义项14
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef14() {
return this.vdef14;
} 

/**
* 属性vdef14的Setter方法.属性名：自定义项14
* 创建日期:2017-1-5
* @param newVdef14 java.lang.String
*/
public void setVdef14 ( java.lang.String vdef14) {
this.vdef14=vdef14;
} 
 
/**
* 属性 vdef15的Getter方法.属性名：自定义项15
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef15() {
return this.vdef15;
} 

/**
* 属性vdef15的Setter方法.属性名：自定义项15
* 创建日期:2017-1-5
* @param newVdef15 java.lang.String
*/
public void setVdef15 ( java.lang.String vdef15) {
this.vdef15=vdef15;
} 
 
/**
* 属性 vdef16的Getter方法.属性名：自定义项16
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef16() {
return this.vdef16;
} 

/**
* 属性vdef16的Setter方法.属性名：自定义项16
* 创建日期:2017-1-5
* @param newVdef16 java.lang.String
*/
public void setVdef16 ( java.lang.String vdef16) {
this.vdef16=vdef16;
} 
 
/**
* 属性 vdef17的Getter方法.属性名：自定义项17
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef17() {
return this.vdef17;
} 

/**
* 属性vdef17的Setter方法.属性名：自定义项17
* 创建日期:2017-1-5
* @param newVdef17 java.lang.String
*/
public void setVdef17 ( java.lang.String vdef17) {
this.vdef17=vdef17;
} 
 
/**
* 属性 vdef18的Getter方法.属性名：自定义项18
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef18() {
return this.vdef18;
} 

/**
* 属性vdef18的Setter方法.属性名：自定义项18
* 创建日期:2017-1-5
* @param newVdef18 java.lang.String
*/
public void setVdef18 ( java.lang.String vdef18) {
this.vdef18=vdef18;
} 
 
/**
* 属性 vdef19的Getter方法.属性名：自定义项19
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef19() {
return this.vdef19;
} 

/**
* 属性vdef19的Setter方法.属性名：自定义项19
* 创建日期:2017-1-5
* @param newVdef19 java.lang.String
*/
public void setVdef19 ( java.lang.String vdef19) {
this.vdef19=vdef19;
} 
 
/**
* 属性 vdef20的Getter方法.属性名：自定义项20
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef20() {
return this.vdef20;
} 

/**
* 属性vdef20的Setter方法.属性名：自定义项20
* 创建日期:2017-1-5
* @param newVdef20 java.lang.String
*/
public void setVdef20 ( java.lang.String vdef20) {
this.vdef20=vdef20;
} 
 
/**
* 属性 vdef21的Getter方法.属性名：自定义项21
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef21() {
return this.vdef21;
} 

/**
* 属性vdef21的Setter方法.属性名：自定义项21
* 创建日期:2017-1-5
* @param newVdef21 java.lang.String
*/
public void setVdef21 ( java.lang.String vdef21) {
this.vdef21=vdef21;
} 
 
/**
* 属性 vdef22的Getter方法.属性名：自定义项22
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef22() {
return this.vdef22;
} 

/**
* 属性vdef22的Setter方法.属性名：自定义项22
* 创建日期:2017-1-5
* @param newVdef22 java.lang.String
*/
public void setVdef22 ( java.lang.String vdef22) {
this.vdef22=vdef22;
} 
 
/**
* 属性 vdef23的Getter方法.属性名：自定义项23
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef23() {
return this.vdef23;
} 

/**
* 属性vdef23的Setter方法.属性名：自定义项23
* 创建日期:2017-1-5
* @param newVdef23 java.lang.String
*/
public void setVdef23 ( java.lang.String vdef23) {
this.vdef23=vdef23;
} 
 
/**
* 属性 vdef24的Getter方法.属性名：自定义项24
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef24() {
return this.vdef24;
} 

/**
* 属性vdef24的Setter方法.属性名：自定义项24
* 创建日期:2017-1-5
* @param newVdef24 java.lang.String
*/
public void setVdef24 ( java.lang.String vdef24) {
this.vdef24=vdef24;
} 
 
/**
* 属性 vdef25的Getter方法.属性名：自定义项25
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef25() {
return this.vdef25;
} 

/**
* 属性vdef25的Setter方法.属性名：自定义项25
* 创建日期:2017-1-5
* @param newVdef25 java.lang.String
*/
public void setVdef25 ( java.lang.String vdef25) {
this.vdef25=vdef25;
} 
 
/**
* 属性 vdef26的Getter方法.属性名：自定义项26
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef26() {
return this.vdef26;
} 

/**
* 属性vdef26的Setter方法.属性名：自定义项26
* 创建日期:2017-1-5
* @param newVdef26 java.lang.String
*/
public void setVdef26 ( java.lang.String vdef26) {
this.vdef26=vdef26;
} 
 
/**
* 属性 vdef27的Getter方法.属性名：自定义项27
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef27() {
return this.vdef27;
} 

/**
* 属性vdef27的Setter方法.属性名：自定义项27
* 创建日期:2017-1-5
* @param newVdef27 java.lang.String
*/
public void setVdef27 ( java.lang.String vdef27) {
this.vdef27=vdef27;
} 
 
/**
* 属性 vdef28的Getter方法.属性名：自定义项28
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef28() {
return this.vdef28;
} 

/**
* 属性vdef28的Setter方法.属性名：自定义项28
* 创建日期:2017-1-5
* @param newVdef28 java.lang.String
*/
public void setVdef28 ( java.lang.String vdef28) {
this.vdef28=vdef28;
} 
 
/**
* 属性 vdef29的Getter方法.属性名：自定义项29
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef29() {
return this.vdef29;
} 

/**
* 属性vdef29的Setter方法.属性名：自定义项29
* 创建日期:2017-1-5
* @param newVdef29 java.lang.String
*/
public void setVdef29 ( java.lang.String vdef29) {
this.vdef29=vdef29;
} 
 
/**
* 属性 vdef30的Getter方法.属性名：自定义项30
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef30() {
return this.vdef30;
} 

/**
* 属性vdef30的Setter方法.属性名：自定义项30
* 创建日期:2017-1-5
* @param newVdef30 java.lang.String
*/
public void setVdef30 ( java.lang.String vdef30) {
this.vdef30=vdef30;
} 
 
/**
* 属性 cmaterialoid的Getter方法.属性名：物料
*  创建日期:2017-1-5
* @return nc.vo.bd.material.MaterialVersionVO
*/
public java.lang.String getCmaterialoid() {
return this.cmaterialoid;
} 

/**
* 属性cmaterialoid的Setter方法.属性名：物料
* 创建日期:2017-1-5
* @param newCmaterialoid nc.vo.bd.material.MaterialVersionVO
*/
public void setCmaterialoid ( java.lang.String cmaterialoid) {
this.cmaterialoid=cmaterialoid;
} 
 
/**
* 属性 cmaterialvid的Getter方法.属性名：物料多版本
*  创建日期:2017-1-5
* @return nc.vo.bd.material.MaterialVO
*/
public java.lang.String getCmaterialvid() {
return this.cmaterialvid;
} 

/**
* 属性cmaterialvid的Setter方法.属性名：物料多版本
* 创建日期:2017-1-5
* @param newCmaterialvid nc.vo.bd.material.MaterialVO
*/
public void setCmaterialvid ( java.lang.String cmaterialvid) {
this.cmaterialvid=cmaterialvid;
} 
 
/**
* 属性 生成上层主键的Getter方法.属性名：上层主键
*  创建日期:2017-1-5
* @return String
*/
public String getPk_ordertemp(){
return this.pk_ordertemp;
}
/**
* 属性生成上层主键的Setter方法.属性名：上层主键
* 创建日期:2017-1-5
* @param newPk_ordertemp String
*/
public void setPk_ordertemp(String pk_ordertemp){
this.pk_ordertemp=pk_ordertemp;
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
    return VOMetaFactory.getInstance().getVOMeta("yuyuan.YyOrderTempBVO");
    }
   }
    