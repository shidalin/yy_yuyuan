package nc.vo.ct.purdaily.entity;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;

/**
 * �ɹ���ͬ���ݴ������
 * 
 * @author shidl
 * 
 */
public class CTDto extends SuperVO {
	private String pk_org;
	// ��Ӧ��
	private String cvendorid;
	// �ƻ���Ч����
	private UFDate valdate;
	// �ƻ�ʧЧ����
	private UFDate invallidate;
	// ���ݺ�
	private String vbillcode;
	// ����
	private String pk_material;
	// �ӱ�����
	private String pk_ct_pu_b;
	// ��������
	private String pk_ct_pu;
	// ���ϱ���
	private String material_code;
	private String supplier_code;
	private String org_code;
	//��ͬ״̬
	

	public String getPk_org() {
		return pk_org;
	}

	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
	}

	public String getCvendorid() {
		return cvendorid;
	}

	public void setCvendorid(String cvendorid) {
		this.cvendorid = cvendorid;
	}

	public UFDate getValdate() {
		return valdate;
	}

	public void setValdate(UFDate valdate) {
		this.valdate = valdate;
	}

	public UFDate getInvallidate() {
		return invallidate;
	}

	public void setInvallidate(UFDate invallidate) {
		this.invallidate = invallidate;
	}

	public String getVbillcode() {
		return vbillcode;
	}

	public void setVbillcode(String vbillcode) {
		this.vbillcode = vbillcode;
	}

	public String getPk_material() {
		return pk_material;
	}

	public void setPk_material(String pk_material) {
		this.pk_material = pk_material;
	}

	public String getPk_ct_pu_b() {
		return pk_ct_pu_b;
	}

	public void setPk_ct_pu_b(String pk_ct_pu_b) {
		this.pk_ct_pu_b = pk_ct_pu_b;
	}

	public String getPk_ct_pu() {
		return pk_ct_pu;
	}

	public void setPk_ct_pu(String pk_ct_pu) {
		this.pk_ct_pu = pk_ct_pu;
	}

	public String getMaterial_code() {
		return material_code;
	}

	public void setMaterial_code(String material_code) {
		this.material_code = material_code;
	}

	public String getSupplier_code() {
		return supplier_code;
	}

	public void setSupplier_code(String supplier_code) {
		this.supplier_code = supplier_code;
	}

	public String getOrg_code() {
		return org_code;
	}

	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}

}