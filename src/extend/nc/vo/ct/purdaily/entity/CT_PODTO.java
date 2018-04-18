package nc.vo.ct.purdaily.entity;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;

/**
 * 采购订单关联采购合同、物料对照表数据传输对象
 * 
 * @author shidl
 * 
 */
public class CT_PODTO extends SuperVO {
	private String pk_mm1;// 物料+组织+供应商
	private String pk_mm2;
	private String cvendorid;// 供应商
	private UFDouble nqtorigprice;// 无税单价
	private UFDouble nqtorigtaxprice;// 含税单价
	private UFDouble nqtprice;// 本币无税单价
	private UFDouble nqttaxprice;// 本币含税单价
	private String pk_ct_pu;// 来源单据主键
	private String pk_ct_pu_b;// 来源单据子表主键
	private String cbilltypecode;// 来源单据单据类型编码
	private String cbilltypeid;// 来源单据类型
	private String dispatchstyle;// 配送方式
	private String vbillcode;
	private UFDouble ntaxrate;// 税率
	private String ccurrencyid;// 本位币
	private UFDouble nexchangerate;// 折本汇率
	private UFDouble ngroupmny;// 本币无税金额
	private UFDouble ngrouptaxmny;// 本币价税合计
	private UFDouble nglobalmny;// 本币无税金额
	private UFDouble nglobaltaxmny;// 本币价税合计
	private String corigcurrencyid;// 币种
	private String csendcountryid;// 发货国家
	private String crececountryid;
	private String ctaxcountryid;
	private Integer fbuysellflag;
	private String btriatradeflag;
	private String ctaxcodeid;
	private UFDouble nnosubtaxrate;
	private UFDouble nnosubtax;
	private UFDouble ncaltaxmny;
	private UFDouble ncalcostmny;
	private String cdevareaid;
	private String cdevaddrid;
	private String pk_receiveaddress;
	private UFDouble ngtaxprice;
	private UFDouble ngprice;
	private UFDouble norigtaxprice;
	private UFDouble norigprice;
	private UFDouble ntax;
	private UFDouble ntaxmny;
	private UFDouble nmny;
	private UFDouble norigtaxmny;
	private UFDouble norigmny;
	private String vchangerate;
	private String vqtunitrate;
	private String cqtunitid;
	private String pk_org;
	private String pk_org_v;
	private String castunitid;
	//存储wms运营中心名称
	private String vbdef7;
	public String getPk_mm1() {
		return pk_mm1;
	}

	public void setPk_mm1(String pk_mm1) {
		this.pk_mm1 = pk_mm1;
	}

	public String getPk_mm2() {
		return pk_mm2;
	}

	public void setPk_mm2(String pk_mm2) {
		this.pk_mm2 = pk_mm2;
	}

	public String getCvendorid() {
		return cvendorid;
	}

	public void setCvendorid(String cvendorid) {
		this.cvendorid = cvendorid;
	}

	public UFDouble getNqtorigprice() {
		return nqtorigprice;
	}

	public void setNqtorigprice(UFDouble nqtorigprice) {
		this.nqtorigprice = nqtorigprice;
	}

	public UFDouble getNqtorigtaxprice() {
		return nqtorigtaxprice;
	}

	public void setNqtorigtaxprice(UFDouble nqtorigtaxprice) {
		this.nqtorigtaxprice = nqtorigtaxprice;
	}

	public UFDouble getNqtprice() {
		return nqtprice;
	}

	public void setNqtprice(UFDouble nqtprice) {
		this.nqtprice = nqtprice;
	}

	public UFDouble getNqttaxprice() {
		return nqttaxprice;
	}

	public void setNqttaxprice(UFDouble nqttaxprice) {
		this.nqttaxprice = nqttaxprice;
	}

	public String getPk_ct_pu() {
		return pk_ct_pu;
	}

	public void setPk_ct_pu(String pk_ct_pu) {
		this.pk_ct_pu = pk_ct_pu;
	}

	public String getPk_ct_pu_b() {
		return pk_ct_pu_b;
	}

	public void setPk_ct_pu_b(String pk_ct_pu_b) {
		this.pk_ct_pu_b = pk_ct_pu_b;
	}

	public String getCbilltypecode() {
		return cbilltypecode;
	}

	public void setCbilltypecode(String cbilltypecode) {
		this.cbilltypecode = cbilltypecode;
	}

	public String getCbilltypeid() {
		return cbilltypeid;
	}

	public void setCbilltypeid(String cbilltypeid) {
		this.cbilltypeid = cbilltypeid;
	}

	public String getDispatchstyle() {
		return dispatchstyle;
	}

	public void setDispatchstyle(String dispatchstyle) {
		this.dispatchstyle = dispatchstyle;
	}

	public String getVbillcode() {
		return vbillcode;
	}

	public void setVbillcode(String vbillcode) {
		this.vbillcode = vbillcode;
	}

	public UFDouble getNtaxrate() {
		return ntaxrate;
	}

	public void setNtaxrate(UFDouble ntaxrate) {
		this.ntaxrate = ntaxrate;
	}

	public String getCcurrencyid() {
		return ccurrencyid;
	}

	public void setCcurrencyid(String ccurrencyid) {
		this.ccurrencyid = ccurrencyid;
	}

	public UFDouble getNexchangerate() {
		return nexchangerate;
	}

	public void setNexchangerate(UFDouble nexchangerate) {
		this.nexchangerate = nexchangerate;
	}

	public UFDouble getNgroupmny() {
		return ngroupmny;
	}

	public void setNgroupmny(UFDouble ngroupmny) {
		this.ngroupmny = ngroupmny;
	}

	public UFDouble getNgrouptaxmny() {
		return ngrouptaxmny;
	}

	public void setNgrouptaxmny(UFDouble ngrouptaxmny) {
		this.ngrouptaxmny = ngrouptaxmny;
	}

	public UFDouble getNglobalmny() {
		return nglobalmny;
	}

	public void setNglobalmny(UFDouble nglobalmny) {
		this.nglobalmny = nglobalmny;
	}

	public UFDouble getNglobaltaxmny() {
		return nglobaltaxmny;
	}

	public void setNglobaltaxmny(UFDouble nglobaltaxmny) {
		this.nglobaltaxmny = nglobaltaxmny;
	}

	public String getCorigcurrencyid() {
		return corigcurrencyid;
	}

	public void setCorigcurrencyid(String corigcurrencyid) {
		this.corigcurrencyid = corigcurrencyid;
	}

	public String getCsendcountryid() {
		return csendcountryid;
	}

	public void setCsendcountryid(String csendcountryid) {
		this.csendcountryid = csendcountryid;
	}

	public String getCrececountryid() {
		return crececountryid;
	}

	public void setCrececountryid(String crececountryid) {
		this.crececountryid = crececountryid;
	}

	public String getCtaxcountryid() {
		return ctaxcountryid;
	}

	public void setCtaxcountryid(String ctaxcountryid) {
		this.ctaxcountryid = ctaxcountryid;
	}

	public Integer getFbuysellflag() {
		return fbuysellflag;
	}

	public void setFbuysellflag(Integer fbuysellflag) {
		this.fbuysellflag = fbuysellflag;
	}

	public String getBtriatradeflag() {
		return btriatradeflag;
	}

	public void setBtriatradeflag(String btriatradeflag) {
		this.btriatradeflag = btriatradeflag;
	}

	public String getCtaxcodeid() {
		return ctaxcodeid;
	}

	public void setCtaxcodeid(String ctaxcodeid) {
		this.ctaxcodeid = ctaxcodeid;
	}

	public UFDouble getNnosubtaxrate() {
		return nnosubtaxrate;
	}

	public void setNnosubtaxrate(UFDouble nnosubtaxrate) {
		this.nnosubtaxrate = nnosubtaxrate;
	}

	public UFDouble getNnosubtax() {
		return nnosubtax;
	}

	public void setNnosubtax(UFDouble nnosubtax) {
		this.nnosubtax = nnosubtax;
	}

	public UFDouble getNcaltaxmny() {
		return ncaltaxmny;
	}

	public void setNcaltaxmny(UFDouble ncaltaxmny) {
		this.ncaltaxmny = ncaltaxmny;
	}

	public UFDouble getNcalcostmny() {
		return ncalcostmny;
	}

	public void setNcalcostmny(UFDouble ncalcostmny) {
		this.ncalcostmny = ncalcostmny;
	}

	public String getCdevareaid() {
		return cdevareaid;
	}

	public void setCdevareaid(String cdevareaid) {
		this.cdevareaid = cdevareaid;
	}

	public String getCdevaddrid() {
		return cdevaddrid;
	}

	public void setCdevaddrid(String cdevaddrid) {
		this.cdevaddrid = cdevaddrid;
	}

	public String getPk_receiveaddress() {
		return pk_receiveaddress;
	}

	public void setPk_receiveaddress(String pk_receiveaddress) {
		this.pk_receiveaddress = pk_receiveaddress;
	}

	public UFDouble getNgtaxprice() {
		return ngtaxprice;
	}

	public void setNgtaxprice(UFDouble ngtaxprice) {
		this.ngtaxprice = ngtaxprice;
	}

	public UFDouble getNgprice() {
		return ngprice;
	}

	public void setNgprice(UFDouble ngprice) {
		this.ngprice = ngprice;
	}

	public UFDouble getNorigtaxprice() {
		return norigtaxprice;
	}

	public void setNorigtaxprice(UFDouble norigtaxprice) {
		this.norigtaxprice = norigtaxprice;
	}

	public UFDouble getNorigprice() {
		return norigprice;
	}

	public void setNorigprice(UFDouble norigprice) {
		this.norigprice = norigprice;
	}

	public UFDouble getNtax() {
		return ntax;
	}

	public void setNtax(UFDouble ntax) {
		this.ntax = ntax;
	}

	public UFDouble getNtaxmny() {
		return ntaxmny;
	}

	public void setNtaxmny(UFDouble ntaxmny) {
		this.ntaxmny = ntaxmny;
	}

	public UFDouble getNmny() {
		return nmny;
	}

	public void setNmny(UFDouble nmny) {
		this.nmny = nmny;
	}

	public UFDouble getNorigtaxmny() {
		return norigtaxmny;
	}

	public void setNorigtaxmny(UFDouble norigtaxmny) {
		this.norigtaxmny = norigtaxmny;
	}

	public UFDouble getNorigmny() {
		return norigmny;
	}

	public void setNorigmny(UFDouble norigmny) {
		this.norigmny = norigmny;
	}

	public String getVchangerate() {
		return vchangerate;
	}

	public void setVchangerate(String vchangerate) {
		this.vchangerate = vchangerate;
	}

	public String getVqtunitrate() {
		return vqtunitrate;
	}

	public void setVqtunitrate(String vqtunitrate) {
		this.vqtunitrate = vqtunitrate;
	}

	public String getCqtunitid() {
		return cqtunitid;
	}

	public void setCqtunitid(String cqtunitid) {
		this.cqtunitid = cqtunitid;
	}

	public String getPk_org() {
		return pk_org;
	}

	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
	}

	public String getPk_org_v() {
		return pk_org_v;
	}

	public void setPk_org_v(String pk_org_v) {
		this.pk_org_v = pk_org_v;
	}

	public String getCastunitid() {
		return castunitid;
	}

	public void setCastunitid(String castunitid) {
		this.castunitid = castunitid;
	}

	public String getVbdef7() {
		return vbdef7;
	}

	public void setVbdef7(String vbdef7) {
		this.vbdef7 = vbdef7;
	}

	
}
