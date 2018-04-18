package nc.vo.yuyuan.yy_order;

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
 
public class YyOrderVO extends SuperVO {
	
/**
*主键
*/
public java.lang.String pk_order;
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
*订货单号
*/
public java.lang.String vbillcode;
/**
*单据状态
*/
public java.lang.Integer vbillstatus;
/**
*单据类型编码
*/
public java.lang.String pk_billtypecode;
/**
*单据类型
*/
public java.lang.String pk_billtypeid;
/**
*业务流程
*/
public java.lang.String pk_busitype;
/**
*创建人
*/
public java.lang.String creator;
/**
*创建时间
*/
public UFDateTime creationtime;
/**
*制单人
*/
public java.lang.String billmaker;
/**
*订货日期
*/
public UFDate dmakedate;
/**
*最后修改人
*/
public java.lang.String modifier;
/**
*最后修改时间
*/
public UFDateTime modifiedtime;
/**
*审核人
*/
public java.lang.String approver;
/**
*审核时间
*/
public UFDateTime dapprovetime;
/**
*审核批语
*/
public java.lang.String approvenote;
/**
*备注
*/
public java.lang.String vmemo;
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
*自定义项8
*/
public java.lang.String vdef8;
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
*需求部门最新版本
*/
public java.lang.String cdptid;
/**
*需求部门
*/
public java.lang.String cdptvid;
/**
*时间戳
*/
public UFDateTime ts;
    
    
/**
* 属性 pk_order的Getter方法.属性名：主键
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getPk_order() {
return this.pk_order;
} 

/**
* 属性pk_order的Setter方法.属性名：主键
* 创建日期:2017-1-5
* @param newPk_order java.lang.String
*/
public void setPk_order ( java.lang.String pk_order) {
this.pk_order=pk_order;
} 
 
/**
* 属性 pk_group的Getter方法.属性名：集团
*  创建日期:2017-1-5
* @return nc.vo.org.GroupVO
*/
public java.lang.String getPk_group() {
return this.pk_group;
} 

/**
* 属性pk_group的Setter方法.属性名：集团
* 创建日期:2017-1-5
* @param newPk_group nc.vo.org.GroupVO
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
* 属性 vbillcode的Getter方法.属性名：订货单号
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVbillcode() {
return this.vbillcode;
} 

/**
* 属性vbillcode的Setter方法.属性名：订货单号
* 创建日期:2017-1-5
* @param newVbillcode java.lang.String
*/
public void setVbillcode ( java.lang.String vbillcode) {
this.vbillcode=vbillcode;
} 
 
/**
* 属性 vbillstatus的Getter方法.属性名：单据状态
*  创建日期:2017-1-5
* @return nc.vo.pub.pf.BillStatusEnum
*/
public java.lang.Integer getVbillstatus() {
return this.vbillstatus;
} 

/**
* 属性vbillstatus的Setter方法.属性名：单据状态
* 创建日期:2017-1-5
* @param newVbillstatus nc.vo.pub.pf.BillStatusEnum
*/
public void setVbillstatus ( java.lang.Integer vbillstatus) {
this.vbillstatus=vbillstatus;
} 
 
/**
* 属性 pk_billtypecode的Getter方法.属性名：单据类型编码
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getPk_billtypecode() {
return this.pk_billtypecode;
} 

/**
* 属性pk_billtypecode的Setter方法.属性名：单据类型编码
* 创建日期:2017-1-5
* @param newPk_billtypecode java.lang.String
*/
public void setPk_billtypecode ( java.lang.String pk_billtypecode) {
this.pk_billtypecode=pk_billtypecode;
} 
 
/**
* 属性 pk_billtypeid的Getter方法.属性名：单据类型
*  创建日期:2017-1-5
* @return nc.vo.pub.billtype.BilltypeVO
*/
public java.lang.String getPk_billtypeid() {
return this.pk_billtypeid;
} 

/**
* 属性pk_billtypeid的Setter方法.属性名：单据类型
* 创建日期:2017-1-5
* @param newPk_billtypeid nc.vo.pub.billtype.BilltypeVO
*/
public void setPk_billtypeid ( java.lang.String pk_billtypeid) {
this.pk_billtypeid=pk_billtypeid;
} 
 
/**
* 属性 pk_busitype的Getter方法.属性名：业务流程
*  创建日期:2017-1-5
* @return nc.vo.pf.pub.BusitypeVO
*/
public java.lang.String getPk_busitype() {
return this.pk_busitype;
} 

/**
* 属性pk_busitype的Setter方法.属性名：业务流程
* 创建日期:2017-1-5
* @param newPk_busitype nc.vo.pf.pub.BusitypeVO
*/
public void setPk_busitype ( java.lang.String pk_busitype) {
this.pk_busitype=pk_busitype;
} 
 
/**
* 属性 creator的Getter方法.属性名：创建人
*  创建日期:2017-1-5
* @return nc.vo.sm.UserVO
*/
public java.lang.String getCreator() {
return this.creator;
} 

/**
* 属性creator的Setter方法.属性名：创建人
* 创建日期:2017-1-5
* @param newCreator nc.vo.sm.UserVO
*/
public void setCreator ( java.lang.String creator) {
this.creator=creator;
} 
 
/**
* 属性 creationtime的Getter方法.属性名：创建时间
*  创建日期:2017-1-5
* @return nc.vo.pub.lang.UFDateTime
*/
public UFDateTime getCreationtime() {
return this.creationtime;
} 

/**
* 属性creationtime的Setter方法.属性名：创建时间
* 创建日期:2017-1-5
* @param newCreationtime nc.vo.pub.lang.UFDateTime
*/
public void setCreationtime ( UFDateTime creationtime) {
this.creationtime=creationtime;
} 
 
/**
* 属性 billmaker的Getter方法.属性名：制单人
*  创建日期:2017-1-5
* @return nc.vo.sm.UserVO
*/
public java.lang.String getBillmaker() {
return this.billmaker;
} 

/**
* 属性billmaker的Setter方法.属性名：制单人
* 创建日期:2017-1-5
* @param newBillmaker nc.vo.sm.UserVO
*/
public void setBillmaker ( java.lang.String billmaker) {
this.billmaker=billmaker;
} 
 
/**
* 属性 dmakedate的Getter方法.属性名：订货日期
*  创建日期:2017-1-5
* @return nc.vo.pub.lang.UFDate
*/
public UFDate getDmakedate() {
return this.dmakedate;
} 

/**
* 属性dmakedate的Setter方法.属性名：订货日期
* 创建日期:2017-1-5
* @param newDmakedate nc.vo.pub.lang.UFDate
*/
public void setDmakedate ( UFDate dmakedate) {
this.dmakedate=dmakedate;
} 
 
/**
* 属性 modifier的Getter方法.属性名：最后修改人
*  创建日期:2017-1-5
* @return nc.vo.sm.UserVO
*/
public java.lang.String getModifier() {
return this.modifier;
} 

/**
* 属性modifier的Setter方法.属性名：最后修改人
* 创建日期:2017-1-5
* @param newModifier nc.vo.sm.UserVO
*/
public void setModifier ( java.lang.String modifier) {
this.modifier=modifier;
} 
 
/**
* 属性 modifiedtime的Getter方法.属性名：最后修改时间
*  创建日期:2017-1-5
* @return nc.vo.pub.lang.UFDateTime
*/
public UFDateTime getModifiedtime() {
return this.modifiedtime;
} 

/**
* 属性modifiedtime的Setter方法.属性名：最后修改时间
* 创建日期:2017-1-5
* @param newModifiedtime nc.vo.pub.lang.UFDateTime
*/
public void setModifiedtime ( UFDateTime modifiedtime) {
this.modifiedtime=modifiedtime;
} 
 
/**
* 属性 approver的Getter方法.属性名：审核人
*  创建日期:2017-1-5
* @return nc.vo.sm.UserVO
*/
public java.lang.String getApprover() {
return this.approver;
} 

/**
* 属性approver的Setter方法.属性名：审核人
* 创建日期:2017-1-5
* @param newApprover nc.vo.sm.UserVO
*/
public void setApprover ( java.lang.String approver) {
this.approver=approver;
} 
 
/**
* 属性 dapprovetime的Getter方法.属性名：审核时间
*  创建日期:2017-1-5
* @return nc.vo.pub.lang.UFDateTime
*/
public UFDateTime getDapprovetime() {
return this.dapprovetime;
} 

/**
* 属性dapprovetime的Setter方法.属性名：审核时间
* 创建日期:2017-1-5
* @param newDapprovetime nc.vo.pub.lang.UFDateTime
*/
public void setDapprovetime ( UFDateTime dapprovetime) {
this.dapprovetime=dapprovetime;
} 
 
/**
* 属性 approvenote的Getter方法.属性名：审核批语
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getApprovenote() {
return this.approvenote;
} 

/**
* 属性approvenote的Setter方法.属性名：审核批语
* 创建日期:2017-1-5
* @param newApprovenote java.lang.String
*/
public void setApprovenote ( java.lang.String approvenote) {
this.approvenote=approvenote;
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
* 属性 vdef8的Getter方法.属性名：自定义项8
*  创建日期:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef8() {
return this.vdef8;
} 

/**
* 属性vdef8的Setter方法.属性名：自定义项8
* 创建日期:2017-1-5
* @param newVdef8 java.lang.String
*/
public void setVdef8 ( java.lang.String vdef8) {
this.vdef8=vdef8;
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
* 属性 cdptid的Getter方法.属性名：需求部门最新版本
*  创建日期:2017-1-5
* @return nc.vo.org.DeptVO
*/
public java.lang.String getCdptid() {
return this.cdptid;
} 

/**
* 属性cdptid的Setter方法.属性名：需求部门最新版本
* 创建日期:2017-1-5
* @param newCdptid nc.vo.org.DeptVO
*/
public void setCdptid ( java.lang.String cdptid) {
this.cdptid=cdptid;
} 
 
/**
* 属性 cdptvid的Getter方法.属性名：需求部门
*  创建日期:2017-1-5
* @return nc.vo.vorg.DeptVersionVO
*/
public java.lang.String getCdptvid() {
return this.cdptvid;
} 

/**
* 属性cdptvid的Setter方法.属性名：需求部门
* 创建日期:2017-1-5
* @param newCdptvid nc.vo.vorg.DeptVersionVO
*/
public void setCdptvid ( java.lang.String cdptvid) {
this.cdptvid=cdptvid;
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
    return VOMetaFactory.getInstance().getVOMeta("yuyuan.YyOrderVO");
    }
   }
    