package nc.vo.yuyuan.yy_ordertemp;

import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

/**
 * <b> �˴���Ҫ�������๦�� </b>
 * <p>
 *   �˴�����۵�������Ϣ
 * </p>
 *  ��������:2017-1-5
 * @author YONYOU NC
 * @version NCPrj ??
 */
 
public class YyOrderTempBVO extends SuperVO {
	
/**
*�ӱ�����
*/
public java.lang.String pk_ordertemp_b;
/**
*����
*/
public java.lang.String pk_group;
/**
*�ɹ���֯���°汾
*/
public java.lang.String pk_org;
/**
*�ɹ���֯
*/
public java.lang.String pk_org_v;
/**
*��Դ��������
*/
public java.lang.String csourcetypecode;
/**
*��Դ���ݺ�
*/
public java.lang.String vsourcecode;
/**
*��Դ����
*/
public java.lang.String csourceid;
/**
*��Դ������ϸ
*/
public java.lang.String csourcebid;
/**
*��Դ�����к�
*/
public java.lang.String vsourcerowno;
/**
*Դͷ��������
*/
public java.lang.String cfirsttypecode;
/**
*Դͷ���ݺ�
*/
public java.lang.String vfirstcode;
/**
*Դͷ������������
*/
public java.lang.String cfirstid;
/**
*Դͷ������ϸ
*/
public java.lang.String cfirstbid;
/**
*Դͷ�����к�
*/
public java.lang.String vfirstrowno;
/**
*��ע
*/
public java.lang.String vmemo;
/**
*�к�
*/
public java.lang.String crowno;
/**
*�Զ�����1
*/
public java.lang.String vdef1;
/**
*�Զ�����2
*/
public java.lang.String vdef2;
/**
*�Զ�����3
*/
public java.lang.String vdef3;
/**
*�Զ�����4
*/
public java.lang.String vdef4;
/**
*�Զ�����5
*/
public java.lang.String vdef5;
/**
*�Զ�����6
*/
public java.lang.String vdef6;
/**
*�Զ�����7
*/
public java.lang.String vdef7;
/**
*�Զ�����10
*/
public java.lang.String vdef10;
/**
*�Զ�����11
*/
public java.lang.String vdef11;
/**
*�Զ�����12
*/
public java.lang.String vdef12;
/**
*�Զ�����13
*/
public java.lang.String vdef13;
/**
*�Զ�����14
*/
public java.lang.String vdef14;
/**
*�Զ�����15
*/
public java.lang.String vdef15;
/**
*�Զ�����16
*/
public java.lang.String vdef16;
/**
*�Զ�����17
*/
public java.lang.String vdef17;
/**
*�Զ�����18
*/
public java.lang.String vdef18;
/**
*�Զ�����19
*/
public java.lang.String vdef19;
/**
*�Զ�����20
*/
public java.lang.String vdef20;
/**
*�Զ�����21
*/
public java.lang.String vdef21;
/**
*�Զ�����22
*/
public java.lang.String vdef22;
/**
*�Զ�����23
*/
public java.lang.String vdef23;
/**
*�Զ�����24
*/
public java.lang.String vdef24;
/**
*�Զ�����25
*/
public java.lang.String vdef25;
/**
*�Զ�����26
*/
public java.lang.String vdef26;
/**
*�Զ�����27
*/
public java.lang.String vdef27;
/**
*�Զ�����28
*/
public java.lang.String vdef28;
/**
*�Զ�����29
*/
public java.lang.String vdef29;
/**
*�Զ�����30
*/
public java.lang.String vdef30;
/**
*����
*/
public java.lang.String cmaterialoid;
/**
*���϶�汾
*/
public java.lang.String cmaterialvid;
/**
*�ϲ㵥������
*/
public String pk_ordertemp;
/**
*ʱ���
*/
public UFDateTime ts;
    
    
/**
* ���� pk_ordertemp_b��Getter����.���������ӱ�����
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getPk_ordertemp_b() {
return this.pk_ordertemp_b;
} 

/**
* ����pk_ordertemp_b��Setter����.���������ӱ�����
* ��������:2017-1-5
* @param newPk_ordertemp_b java.lang.String
*/
public void setPk_ordertemp_b ( java.lang.String pk_ordertemp_b) {
this.pk_ordertemp_b=pk_ordertemp_b;
} 
 
/**
* ���� pk_group��Getter����.������������
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getPk_group() {
return this.pk_group;
} 

/**
* ����pk_group��Setter����.������������
* ��������:2017-1-5
* @param newPk_group java.lang.String
*/
public void setPk_group ( java.lang.String pk_group) {
this.pk_group=pk_group;
} 
 
/**
* ���� pk_org��Getter����.���������ɹ���֯���°汾
*  ��������:2017-1-5
* @return nc.vo.org.PurchaseOrgVO
*/
public java.lang.String getPk_org() {
return this.pk_org;
} 

/**
* ����pk_org��Setter����.���������ɹ���֯���°汾
* ��������:2017-1-5
* @param newPk_org nc.vo.org.PurchaseOrgVO
*/
public void setPk_org ( java.lang.String pk_org) {
this.pk_org=pk_org;
} 
 
/**
* ���� pk_org_v��Getter����.���������ɹ���֯
*  ��������:2017-1-5
* @return nc.vo.vorg.PurchaseOrgVersionVO
*/
public java.lang.String getPk_org_v() {
return this.pk_org_v;
} 

/**
* ����pk_org_v��Setter����.���������ɹ���֯
* ��������:2017-1-5
* @param newPk_org_v nc.vo.vorg.PurchaseOrgVersionVO
*/
public void setPk_org_v ( java.lang.String pk_org_v) {
this.pk_org_v=pk_org_v;
} 
 
/**
* ���� csourcetypecode��Getter����.����������Դ��������
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getCsourcetypecode() {
return this.csourcetypecode;
} 

/**
* ����csourcetypecode��Setter����.����������Դ��������
* ��������:2017-1-5
* @param newCsourcetypecode java.lang.String
*/
public void setCsourcetypecode ( java.lang.String csourcetypecode) {
this.csourcetypecode=csourcetypecode;
} 
 
/**
* ���� vsourcecode��Getter����.����������Դ���ݺ�
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVsourcecode() {
return this.vsourcecode;
} 

/**
* ����vsourcecode��Setter����.����������Դ���ݺ�
* ��������:2017-1-5
* @param newVsourcecode java.lang.String
*/
public void setVsourcecode ( java.lang.String vsourcecode) {
this.vsourcecode=vsourcecode;
} 
 
/**
* ���� csourceid��Getter����.����������Դ����
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getCsourceid() {
return this.csourceid;
} 

/**
* ����csourceid��Setter����.����������Դ����
* ��������:2017-1-5
* @param newCsourceid java.lang.String
*/
public void setCsourceid ( java.lang.String csourceid) {
this.csourceid=csourceid;
} 
 
/**
* ���� csourcebid��Getter����.����������Դ������ϸ
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getCsourcebid() {
return this.csourcebid;
} 

/**
* ����csourcebid��Setter����.����������Դ������ϸ
* ��������:2017-1-5
* @param newCsourcebid java.lang.String
*/
public void setCsourcebid ( java.lang.String csourcebid) {
this.csourcebid=csourcebid;
} 
 
/**
* ���� vsourcerowno��Getter����.����������Դ�����к�
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVsourcerowno() {
return this.vsourcerowno;
} 

/**
* ����vsourcerowno��Setter����.����������Դ�����к�
* ��������:2017-1-5
* @param newVsourcerowno java.lang.String
*/
public void setVsourcerowno ( java.lang.String vsourcerowno) {
this.vsourcerowno=vsourcerowno;
} 
 
/**
* ���� cfirsttypecode��Getter����.��������Դͷ��������
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getCfirsttypecode() {
return this.cfirsttypecode;
} 

/**
* ����cfirsttypecode��Setter����.��������Դͷ��������
* ��������:2017-1-5
* @param newCfirsttypecode java.lang.String
*/
public void setCfirsttypecode ( java.lang.String cfirsttypecode) {
this.cfirsttypecode=cfirsttypecode;
} 
 
/**
* ���� vfirstcode��Getter����.��������Դͷ���ݺ�
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVfirstcode() {
return this.vfirstcode;
} 

/**
* ����vfirstcode��Setter����.��������Դͷ���ݺ�
* ��������:2017-1-5
* @param newVfirstcode java.lang.String
*/
public void setVfirstcode ( java.lang.String vfirstcode) {
this.vfirstcode=vfirstcode;
} 
 
/**
* ���� cfirstid��Getter����.��������Դͷ������������
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getCfirstid() {
return this.cfirstid;
} 

/**
* ����cfirstid��Setter����.��������Դͷ������������
* ��������:2017-1-5
* @param newCfirstid java.lang.String
*/
public void setCfirstid ( java.lang.String cfirstid) {
this.cfirstid=cfirstid;
} 
 
/**
* ���� cfirstbid��Getter����.��������Դͷ������ϸ
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getCfirstbid() {
return this.cfirstbid;
} 

/**
* ����cfirstbid��Setter����.��������Դͷ������ϸ
* ��������:2017-1-5
* @param newCfirstbid java.lang.String
*/
public void setCfirstbid ( java.lang.String cfirstbid) {
this.cfirstbid=cfirstbid;
} 
 
/**
* ���� vfirstrowno��Getter����.��������Դͷ�����к�
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVfirstrowno() {
return this.vfirstrowno;
} 

/**
* ����vfirstrowno��Setter����.��������Դͷ�����к�
* ��������:2017-1-5
* @param newVfirstrowno java.lang.String
*/
public void setVfirstrowno ( java.lang.String vfirstrowno) {
this.vfirstrowno=vfirstrowno;
} 
 
/**
* ���� vmemo��Getter����.����������ע
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVmemo() {
return this.vmemo;
} 

/**
* ����vmemo��Setter����.����������ע
* ��������:2017-1-5
* @param newVmemo java.lang.String
*/
public void setVmemo ( java.lang.String vmemo) {
this.vmemo=vmemo;
} 
 
/**
* ���� crowno��Getter����.���������к�
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getCrowno() {
return this.crowno;
} 

/**
* ����crowno��Setter����.���������к�
* ��������:2017-1-5
* @param newCrowno java.lang.String
*/
public void setCrowno ( java.lang.String crowno) {
this.crowno=crowno;
} 
 
/**
* ���� vdef1��Getter����.���������Զ�����1
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef1() {
return this.vdef1;
} 

/**
* ����vdef1��Setter����.���������Զ�����1
* ��������:2017-1-5
* @param newVdef1 java.lang.String
*/
public void setVdef1 ( java.lang.String vdef1) {
this.vdef1=vdef1;
} 
 
/**
* ���� vdef2��Getter����.���������Զ�����2
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef2() {
return this.vdef2;
} 

/**
* ����vdef2��Setter����.���������Զ�����2
* ��������:2017-1-5
* @param newVdef2 java.lang.String
*/
public void setVdef2 ( java.lang.String vdef2) {
this.vdef2=vdef2;
} 
 
/**
* ���� vdef3��Getter����.���������Զ�����3
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef3() {
return this.vdef3;
} 

/**
* ����vdef3��Setter����.���������Զ�����3
* ��������:2017-1-5
* @param newVdef3 java.lang.String
*/
public void setVdef3 ( java.lang.String vdef3) {
this.vdef3=vdef3;
} 
 
/**
* ���� vdef4��Getter����.���������Զ�����4
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef4() {
return this.vdef4;
} 

/**
* ����vdef4��Setter����.���������Զ�����4
* ��������:2017-1-5
* @param newVdef4 java.lang.String
*/
public void setVdef4 ( java.lang.String vdef4) {
this.vdef4=vdef4;
} 
 
/**
* ���� vdef5��Getter����.���������Զ�����5
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef5() {
return this.vdef5;
} 

/**
* ����vdef5��Setter����.���������Զ�����5
* ��������:2017-1-5
* @param newVdef5 java.lang.String
*/
public void setVdef5 ( java.lang.String vdef5) {
this.vdef5=vdef5;
} 
 
/**
* ���� vdef6��Getter����.���������Զ�����6
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef6() {
return this.vdef6;
} 

/**
* ����vdef6��Setter����.���������Զ�����6
* ��������:2017-1-5
* @param newVdef6 java.lang.String
*/
public void setVdef6 ( java.lang.String vdef6) {
this.vdef6=vdef6;
} 
 
/**
* ���� vdef7��Getter����.���������Զ�����7
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef7() {
return this.vdef7;
} 

/**
* ����vdef7��Setter����.���������Զ�����7
* ��������:2017-1-5
* @param newVdef7 java.lang.String
*/
public void setVdef7 ( java.lang.String vdef7) {
this.vdef7=vdef7;
} 
 
/**
* ���� vdef10��Getter����.���������Զ�����10
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef10() {
return this.vdef10;
} 

/**
* ����vdef10��Setter����.���������Զ�����10
* ��������:2017-1-5
* @param newVdef10 java.lang.String
*/
public void setVdef10 ( java.lang.String vdef10) {
this.vdef10=vdef10;
} 
 
/**
* ���� vdef11��Getter����.���������Զ�����11
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef11() {
return this.vdef11;
} 

/**
* ����vdef11��Setter����.���������Զ�����11
* ��������:2017-1-5
* @param newVdef11 java.lang.String
*/
public void setVdef11 ( java.lang.String vdef11) {
this.vdef11=vdef11;
} 
 
/**
* ���� vdef12��Getter����.���������Զ�����12
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef12() {
return this.vdef12;
} 

/**
* ����vdef12��Setter����.���������Զ�����12
* ��������:2017-1-5
* @param newVdef12 java.lang.String
*/
public void setVdef12 ( java.lang.String vdef12) {
this.vdef12=vdef12;
} 
 
/**
* ���� vdef13��Getter����.���������Զ�����13
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef13() {
return this.vdef13;
} 

/**
* ����vdef13��Setter����.���������Զ�����13
* ��������:2017-1-5
* @param newVdef13 java.lang.String
*/
public void setVdef13 ( java.lang.String vdef13) {
this.vdef13=vdef13;
} 
 
/**
* ���� vdef14��Getter����.���������Զ�����14
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef14() {
return this.vdef14;
} 

/**
* ����vdef14��Setter����.���������Զ�����14
* ��������:2017-1-5
* @param newVdef14 java.lang.String
*/
public void setVdef14 ( java.lang.String vdef14) {
this.vdef14=vdef14;
} 
 
/**
* ���� vdef15��Getter����.���������Զ�����15
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef15() {
return this.vdef15;
} 

/**
* ����vdef15��Setter����.���������Զ�����15
* ��������:2017-1-5
* @param newVdef15 java.lang.String
*/
public void setVdef15 ( java.lang.String vdef15) {
this.vdef15=vdef15;
} 
 
/**
* ���� vdef16��Getter����.���������Զ�����16
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef16() {
return this.vdef16;
} 

/**
* ����vdef16��Setter����.���������Զ�����16
* ��������:2017-1-5
* @param newVdef16 java.lang.String
*/
public void setVdef16 ( java.lang.String vdef16) {
this.vdef16=vdef16;
} 
 
/**
* ���� vdef17��Getter����.���������Զ�����17
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef17() {
return this.vdef17;
} 

/**
* ����vdef17��Setter����.���������Զ�����17
* ��������:2017-1-5
* @param newVdef17 java.lang.String
*/
public void setVdef17 ( java.lang.String vdef17) {
this.vdef17=vdef17;
} 
 
/**
* ���� vdef18��Getter����.���������Զ�����18
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef18() {
return this.vdef18;
} 

/**
* ����vdef18��Setter����.���������Զ�����18
* ��������:2017-1-5
* @param newVdef18 java.lang.String
*/
public void setVdef18 ( java.lang.String vdef18) {
this.vdef18=vdef18;
} 
 
/**
* ���� vdef19��Getter����.���������Զ�����19
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef19() {
return this.vdef19;
} 

/**
* ����vdef19��Setter����.���������Զ�����19
* ��������:2017-1-5
* @param newVdef19 java.lang.String
*/
public void setVdef19 ( java.lang.String vdef19) {
this.vdef19=vdef19;
} 
 
/**
* ���� vdef20��Getter����.���������Զ�����20
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef20() {
return this.vdef20;
} 

/**
* ����vdef20��Setter����.���������Զ�����20
* ��������:2017-1-5
* @param newVdef20 java.lang.String
*/
public void setVdef20 ( java.lang.String vdef20) {
this.vdef20=vdef20;
} 
 
/**
* ���� vdef21��Getter����.���������Զ�����21
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef21() {
return this.vdef21;
} 

/**
* ����vdef21��Setter����.���������Զ�����21
* ��������:2017-1-5
* @param newVdef21 java.lang.String
*/
public void setVdef21 ( java.lang.String vdef21) {
this.vdef21=vdef21;
} 
 
/**
* ���� vdef22��Getter����.���������Զ�����22
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef22() {
return this.vdef22;
} 

/**
* ����vdef22��Setter����.���������Զ�����22
* ��������:2017-1-5
* @param newVdef22 java.lang.String
*/
public void setVdef22 ( java.lang.String vdef22) {
this.vdef22=vdef22;
} 
 
/**
* ���� vdef23��Getter����.���������Զ�����23
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef23() {
return this.vdef23;
} 

/**
* ����vdef23��Setter����.���������Զ�����23
* ��������:2017-1-5
* @param newVdef23 java.lang.String
*/
public void setVdef23 ( java.lang.String vdef23) {
this.vdef23=vdef23;
} 
 
/**
* ���� vdef24��Getter����.���������Զ�����24
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef24() {
return this.vdef24;
} 

/**
* ����vdef24��Setter����.���������Զ�����24
* ��������:2017-1-5
* @param newVdef24 java.lang.String
*/
public void setVdef24 ( java.lang.String vdef24) {
this.vdef24=vdef24;
} 
 
/**
* ���� vdef25��Getter����.���������Զ�����25
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef25() {
return this.vdef25;
} 

/**
* ����vdef25��Setter����.���������Զ�����25
* ��������:2017-1-5
* @param newVdef25 java.lang.String
*/
public void setVdef25 ( java.lang.String vdef25) {
this.vdef25=vdef25;
} 
 
/**
* ���� vdef26��Getter����.���������Զ�����26
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef26() {
return this.vdef26;
} 

/**
* ����vdef26��Setter����.���������Զ�����26
* ��������:2017-1-5
* @param newVdef26 java.lang.String
*/
public void setVdef26 ( java.lang.String vdef26) {
this.vdef26=vdef26;
} 
 
/**
* ���� vdef27��Getter����.���������Զ�����27
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef27() {
return this.vdef27;
} 

/**
* ����vdef27��Setter����.���������Զ�����27
* ��������:2017-1-5
* @param newVdef27 java.lang.String
*/
public void setVdef27 ( java.lang.String vdef27) {
this.vdef27=vdef27;
} 
 
/**
* ���� vdef28��Getter����.���������Զ�����28
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef28() {
return this.vdef28;
} 

/**
* ����vdef28��Setter����.���������Զ�����28
* ��������:2017-1-5
* @param newVdef28 java.lang.String
*/
public void setVdef28 ( java.lang.String vdef28) {
this.vdef28=vdef28;
} 
 
/**
* ���� vdef29��Getter����.���������Զ�����29
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef29() {
return this.vdef29;
} 

/**
* ����vdef29��Setter����.���������Զ�����29
* ��������:2017-1-5
* @param newVdef29 java.lang.String
*/
public void setVdef29 ( java.lang.String vdef29) {
this.vdef29=vdef29;
} 
 
/**
* ���� vdef30��Getter����.���������Զ�����30
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getVdef30() {
return this.vdef30;
} 

/**
* ����vdef30��Setter����.���������Զ�����30
* ��������:2017-1-5
* @param newVdef30 java.lang.String
*/
public void setVdef30 ( java.lang.String vdef30) {
this.vdef30=vdef30;
} 
 
/**
* ���� cmaterialoid��Getter����.������������
*  ��������:2017-1-5
* @return nc.vo.bd.material.MaterialVersionVO
*/
public java.lang.String getCmaterialoid() {
return this.cmaterialoid;
} 

/**
* ����cmaterialoid��Setter����.������������
* ��������:2017-1-5
* @param newCmaterialoid nc.vo.bd.material.MaterialVersionVO
*/
public void setCmaterialoid ( java.lang.String cmaterialoid) {
this.cmaterialoid=cmaterialoid;
} 
 
/**
* ���� cmaterialvid��Getter����.�����������϶�汾
*  ��������:2017-1-5
* @return nc.vo.bd.material.MaterialVO
*/
public java.lang.String getCmaterialvid() {
return this.cmaterialvid;
} 

/**
* ����cmaterialvid��Setter����.�����������϶�汾
* ��������:2017-1-5
* @param newCmaterialvid nc.vo.bd.material.MaterialVO
*/
public void setCmaterialvid ( java.lang.String cmaterialvid) {
this.cmaterialvid=cmaterialvid;
} 
 
/**
* ���� �����ϲ�������Getter����.���������ϲ�����
*  ��������:2017-1-5
* @return String
*/
public String getPk_ordertemp(){
return this.pk_ordertemp;
}
/**
* ���������ϲ�������Setter����.���������ϲ�����
* ��������:2017-1-5
* @param newPk_ordertemp String
*/
public void setPk_ordertemp(String pk_ordertemp){
this.pk_ordertemp=pk_ordertemp;
} 
/**
* ���� ����ʱ�����Getter����.��������ʱ���
*  ��������:2017-1-5
* @return nc.vo.pub.lang.UFDateTime
*/
public UFDateTime getTs() {
return this.ts;
}
/**
* ��������ʱ�����Setter����.��������ʱ���
* ��������:2017-1-5
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
    