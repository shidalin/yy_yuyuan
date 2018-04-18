package nc.vo.yuyuan.yy_errorlog;

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
 
public class YyErrorLogVO extends SuperVO {
	
/**
*����
*/
public java.lang.String pk_errorlog;
/**
*��������
*/
public java.lang.String errortyle;
/**
*��������
*/
public UFDate errordate;
/**
*������Ϣ
*/
public java.lang.String errorinfo;
/**
*��������֯
*/
public java.lang.String pk_org;
/**
*�����ݺ�
*/
public java.lang.String billno;
/**
*����������
*/
public java.lang.String bill;
/**
*�������Ƶ���
*/
public java.lang.String billmaker;
/**
*����������
*/
public UFDate billdate;
/**
*�����ӱ��к�
*/
public java.lang.String billbno;
/**
*�����ӱ�����
*/
public java.lang.String billbmaterial;
/**
*ʱ���
*/
public UFDateTime ts;
    
    
/**
* ���� pk_errorlog��Getter����.������������
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getPk_errorlog() {
return this.pk_errorlog;
} 

/**
* ����pk_errorlog��Setter����.������������
* ��������:2017-1-5
* @param newPk_errorlog java.lang.String
*/
public void setPk_errorlog ( java.lang.String pk_errorlog) {
this.pk_errorlog=pk_errorlog;
} 
 
/**
* ���� errortyle��Getter����.����������������
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getErrortyle() {
return this.errortyle;
} 

/**
* ����errortyle��Setter����.����������������
* ��������:2017-1-5
* @param newErrortyle java.lang.String
*/
public void setErrortyle ( java.lang.String errortyle) {
this.errortyle=errortyle;
} 
 
/**
* ���� errordate��Getter����.����������������
*  ��������:2017-1-5
* @return nc.vo.pub.lang.UFDate
*/
public UFDate getErrordate() {
return this.errordate;
} 

/**
* ����errordate��Setter����.����������������
* ��������:2017-1-5
* @param newErrordate nc.vo.pub.lang.UFDate
*/
public void setErrordate ( UFDate errordate) {
this.errordate=errordate;
} 
 
/**
* ���� errorinfo��Getter����.��������������Ϣ
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getErrorinfo() {
return this.errorinfo;
} 

/**
* ����errorinfo��Setter����.��������������Ϣ
* ��������:2017-1-5
* @param newErrorinfo java.lang.String
*/
public void setErrorinfo ( java.lang.String errorinfo) {
this.errorinfo=errorinfo;
} 
 
/**
* ���� pk_org��Getter����.����������������֯
*  ��������:2017-1-5
* @return nc.vo.org.PurchaseOrgVO
*/
public java.lang.String getPk_org() {
return this.pk_org;
} 

/**
* ����pk_org��Setter����.����������������֯
* ��������:2017-1-5
* @param newPk_org nc.vo.org.PurchaseOrgVO
*/
public void setPk_org ( java.lang.String pk_org) {
this.pk_org=pk_org;
} 
 
/**
* ���� billno��Getter����.�������������ݺ�
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getBillno() {
return this.billno;
} 

/**
* ����billno��Setter����.�������������ݺ�
* ��������:2017-1-5
* @param newBillno java.lang.String
*/
public void setBillno ( java.lang.String billno) {
this.billno=billno;
} 
 
/**
* ���� bill��Getter����.������������������
*  ��������:2017-1-5
* @return nc.vo.pub.billtype.BilltypeVO
*/
public java.lang.String getBill() {
return this.bill;
} 

/**
* ����bill��Setter����.������������������
* ��������:2017-1-5
* @param newBill nc.vo.pub.billtype.BilltypeVO
*/
public void setBill ( java.lang.String bill) {
this.bill=bill;
} 
 
/**
* ���� billmaker��Getter����.���������������Ƶ���
*  ��������:2017-1-5
* @return nc.vo.sm.UserVO
*/
public java.lang.String getBillmaker() {
return this.billmaker;
} 

/**
* ����billmaker��Setter����.���������������Ƶ���
* ��������:2017-1-5
* @param newBillmaker nc.vo.sm.UserVO
*/
public void setBillmaker ( java.lang.String billmaker) {
this.billmaker=billmaker;
} 
 
/**
* ���� billdate��Getter����.������������������
*  ��������:2017-1-5
* @return nc.vo.pub.lang.UFDate
*/
public UFDate getBilldate() {
return this.billdate;
} 

/**
* ����billdate��Setter����.������������������
* ��������:2017-1-5
* @param newBilldate nc.vo.pub.lang.UFDate
*/
public void setBilldate ( UFDate billdate) {
this.billdate=billdate;
} 
 
/**
* ���� billbno��Getter����.�������������ӱ��к�
*  ��������:2017-1-5
* @return java.lang.String
*/
public java.lang.String getBillbno() {
return this.billbno;
} 

/**
* ����billbno��Setter����.�������������ӱ��к�
* ��������:2017-1-5
* @param newBillbno java.lang.String
*/
public void setBillbno ( java.lang.String billbno) {
this.billbno=billbno;
} 
 
/**
* ���� billbmaterial��Getter����.�������������ӱ�����
*  ��������:2017-1-5
* @return nc.vo.bd.material.MaterialVO
*/
public java.lang.String getBillbmaterial() {
return this.billbmaterial;
} 

/**
* ����billbmaterial��Setter����.�������������ӱ�����
* ��������:2017-1-5
* @param newBillbmaterial nc.vo.bd.material.MaterialVO
*/
public void setBillbmaterial ( java.lang.String billbmaterial) {
this.billbmaterial=billbmaterial;
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
    return VOMetaFactory.getInstance().getVOMeta("yuyuan.YyErrorLogVO");
    }
   }
    