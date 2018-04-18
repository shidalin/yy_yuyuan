package nc.vo.yuyuan.yy_order;

import nc.vo.pub.SuperVO;
/**
 * 
 * @author shidl
 *
 */
public class PushCTDto extends SuperVO {
	private String pk_order;
	private String cvendorid;
	private String material_code;
	private String cmaterialvid;
	private String org_code;
	private String crowno;

	public String getCrowno() {
		return crowno;
	}

	public void setCrowno(String crowno) {
		this.crowno = crowno;
	}

	public String getPk_order() {
		return pk_order;
	}

	public void setPk_order(String pk_order) {
		this.pk_order = pk_order;
	}

	public String getCvendorid() {
		return cvendorid;
	}

	public void setCvendorid(String cvendorid) {
		this.cvendorid = cvendorid;
	}

	public String getMaterial_code() {
		return material_code;
	}

	public void setMaterial_code(String material_code) {
		this.material_code = material_code;
	}

	public String getCmaterialvid() {
		return cmaterialvid;
	}

	public void setCmaterialvid(String cmaterialvid) {
		this.cmaterialvid = cmaterialvid;
	}

	public String getOrg_code() {
		return org_code;
	}

	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}

}