package nc.bs.bd.pfxx.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nc.bs.bd.material.marorg.IMarOrgService;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.pfxx.ISwapContext;
import nc.itf.bd.material.baseinfo.IMaterialBaseInfoService;
import nc.itf.org.IBasicOrgUnitQryService;
import nc.itf.org.IOrgUnitQryService;
import nc.vo.bd.errorlog.ErrLogReturnValue;
import nc.vo.bd.errorlog.ErrorMsgVO;
import nc.vo.bd.material.MaterialConvertVO;
import nc.vo.bd.material.MaterialTaxTypeVO;
import nc.vo.bd.material.MaterialVO;
import nc.vo.bd.pub.IPubEnumConst;
import nc.vo.org.OrgVO;
import nc.vo.pfxx.auxiliary.AggxsysregisterVO;
import nc.vo.pfxx.util.PfxxPluginUtils;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.pubapp.AppContext;

import org.apache.commons.lang.StringUtils;

/**
 * 物料基本信息外部交互平台导入
 * 
 * @author jiangjuna
 * @since NC6.0
 */
public class MaterialPfxxPlugin extends nc.bs.pfxx.plugin.AbstractPfxxPlugin {

	private BaseDAO baseDAO = null;

	private IMaterialBaseInfoService service = null;

	@Override
	protected Object processBill(Object vo, ISwapContext swapContext,
			AggxsysregisterVO aggxsysvo) throws BusinessException {
		String pk = null;
		try {
			MaterialVO materialVO = (MaterialVO) vo;
			pk = PfxxPluginUtils.queryBillPKBeforeSaveOrUpdate(
					swapContext.getBilltype(), swapContext.getDocID());
			if (StringUtils.isBlank(pk)) {
				materialVO = this.insertMaterialVO(materialVO);
				PfxxPluginUtils.addDocIDVsPKContrast(swapContext.getBilltype(),
						swapContext.getDocID(), materialVO.getPrimaryKey());
				// 2018-02-05-shidl-增加组织自动分配,默认分配到所有组织
				if (materialVO.getDef8() != null
						&& "pos".equals(materialVO.getDef8())) {
					// 1.查询所有的组织id
					OrgVO[] orgVOs = ((IBasicOrgUnitQryService) NCLocator
							.getInstance().lookup(
									IOrgUnitQryService.class.getName()))
							.getOrgVOSByOrgUnitTypeCanWithGlobalAndGroupVO(
									AppContext.getInstance().getPkGroup(),
									false, true);
					if (orgVOs != null && orgVOs.length > 0) {
						ArrayList<String> pkorgs = new ArrayList<String>();
						for (OrgVO orgVO : orgVOs) {
							pkorgs.add(orgVO.getPk_org());
						}
						// 2.物料全组织分配
						ErrLogReturnValue errorValues = NCLocator
								.getInstance()
								.lookup(IMarOrgService.class)
								.assignMaterialByPks(
										new String[] { materialVO
												.getPk_material() },
										pkorgs.toArray(new String[0]), null);
						if (errorValues != null
								&& errorValues.getTotalNum() > 0) {
							ErrorMsgVO[] errorMsgs = errorValues
									.getErrLogResult().getErrorMsgs();
							if (errorMsgs != null && errorMsgs.length > 0) {
								String errorMessage = errorMsgs.toString();
								throw new BusinessException(errorMessage);
							}
						}
					}
				}
				return materialVO.getPrimaryKey();
			}
			this.getService().updateMaterial(this.getUpdateVO(materialVO, pk));

		} catch (Exception ex) {
			Logger.error(ex.getMessage(), ex.getCause());
			throw new BusinessException(ex.getMessage(), ex.getCause());
		}
		return pk;
	}

	private BaseDAO getBaseDAO() {
		if (this.baseDAO == null) {
			this.baseDAO = new BaseDAO();
		}
		return this.baseDAO;
	}

	private MaterialVO getInsertVO(MaterialVO materialVO) throws DAOException {
		materialVO.setEnablestate(IPubEnumConst.ENABLESTATE_ENABLE);
		materialVO.setStatus(VOStatus.NEW);
		materialVO.setMaterialconvert(this.getMaterialConvertVOs(materialVO));
		materialVO.setMaterialtaxtype(this.getMaterialTaxTypeVOs(materialVO));
		return materialVO;
	}

	@SuppressWarnings("unchecked")
	private MaterialConvertVO[] getMaterialConvertVOs(MaterialVO MaterialVO)
			throws DAOException {
		List<MaterialConvertVO> newConverts = new ArrayList<MaterialConvertVO>();
		if (StringUtils.isNotBlank(MaterialVO.getPrimaryKey())) {
			Collection<MaterialConvertVO> oldAgentStores = this
					.getBaseDAO()
					.retrieveByClause(
							MaterialConvertVO.class,
							MaterialConvertVO.PK_MATERIAL + " = '"
									+ MaterialVO.getPrimaryKey() + "'",
							new String[] { MaterialConvertVO.PK_MATERIALCONVERT });
			for (MaterialConvertVO agentstore : oldAgentStores) {
				agentstore.setStatus(VOStatus.DELETED);
				newConverts.add(agentstore);
			}
		}
		if (MaterialVO.getMaterialconvert() != null
				&& MaterialVO.getMaterialconvert().length > 0) {
			for (MaterialConvertVO agentstore : MaterialVO.getMaterialconvert()) {
				agentstore.setStatus(VOStatus.NEW);
				newConverts.add(agentstore);
			}
		}
		return newConverts.toArray(new MaterialConvertVO[0]);
	}

	private MaterialTaxTypeVO[] getMaterialTaxTypeVOs(MaterialVO MaterialVO)
			throws DAOException {
		List<MaterialTaxTypeVO> newConverts = new ArrayList<MaterialTaxTypeVO>();
		if (StringUtils.isNotBlank(MaterialVO.getPrimaryKey())) {
			Collection<MaterialTaxTypeVO> oldAgentStores = this
					.getBaseDAO()
					.retrieveByClause(
							MaterialTaxTypeVO.class,
							MaterialTaxTypeVO.PK_MATERIAL + " = '"
									+ MaterialVO.getPrimaryKey() + "'",
							new String[] { MaterialTaxTypeVO.PK_MATERIALTAXTYPE });
			for (MaterialTaxTypeVO agentstore : oldAgentStores) {
				agentstore.setStatus(VOStatus.DELETED);
				newConverts.add(agentstore);
			}
		}
		if (MaterialVO.getMaterialtaxtype() != null
				&& MaterialVO.getMaterialtaxtype().length > 0) {
			for (MaterialTaxTypeVO agentstore : MaterialVO.getMaterialtaxtype()) {
				agentstore.setStatus(VOStatus.NEW);
				newConverts.add(agentstore);
			}
		}
		return newConverts.toArray(new MaterialTaxTypeVO[0]);
	}

	private IMaterialBaseInfoService getService() {
		if (this.service == null) {
			this.service = NCLocator.getInstance().lookup(
					IMaterialBaseInfoService.class);
		}
		return this.service;
	}

	private MaterialVO getUpdateVO(MaterialVO materialVO, String pk)
			throws BusinessException {
		MaterialVO oldVO = (MaterialVO) this.getBaseDAO().retrieveByPK(
				MaterialVO.class,
				pk,
				new String[] { MaterialVO.CREATOR, MaterialVO.CREATIONTIME,
						MaterialVO.PK_SOURCE, MaterialVO.VERSION,
						MaterialVO.LATEST, MaterialVO.ENABLESTATE });
		if (oldVO == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("bdpub", "0bdpub0057")
			/* @res "该数据已被删除" */);
		}
		materialVO.setPrimaryKey(pk);
		materialVO.setCreator(oldVO.getCreator());
		materialVO.setCreationtime(oldVO.getCreationtime());
		materialVO.setPk_source(oldVO.getPk_source());
		materialVO.setVersion(oldVO.getVersion());
		materialVO.setLatest(oldVO.getLatest());
		materialVO.setEnablestate(oldVO.getEnablestate());
		materialVO.setStatus(VOStatus.UPDATED);
		materialVO.setMaterialconvert(this.getMaterialConvertVOs(materialVO));
		materialVO.setMaterialtaxtype(this.getMaterialTaxTypeVOs(materialVO));
		return materialVO;
	}

	private MaterialVO insertMaterialVO(MaterialVO materialVO)
			throws BusinessException, DAOException {
		if (StringUtils.isBlank(materialVO.getPk_source())) {
			materialVO = this.getService().insertMaterial(
					this.getInsertVO(materialVO));
		} else {
			ErrLogReturnValue value = this.getService().createMaterialVersion(
					materialVO, materialVO.getPk_source());
			if (value.getReturnValue() == null
					|| !value.getReturnValue().getClass().isArray()) {
				return materialVO;
			}
			materialVO = (MaterialVO) ((Object[]) value.getReturnValue())[0];
			this.LogErrorMessage(value);
		}
		return materialVO;
	}

	private void LogErrorMessage(ErrLogReturnValue value) {
		ErrorMsgVO[] vos = value.getErrLogResult().getErrorMsgs();
		if (vos != null && vos.length > 0) {
			String message = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
					.getStrByID(
							"10140mag",
							"010140mag0200",
							null,
							new String[] {
									Integer.toString(value.getTotalNum()),
									Integer.toString(value.getErrLogResult()
											.getErrorMessagegNum()) })
					/* @res "外部交换平台导入物料新版本数据时，分配操作部分成功，共处理了{0}条记录，其中有{1}条处理失败：" */+ "\n";
			for (int i = 0; i < vos.length; i++) {
				message += vos[i].getErrormsg() + "\n";
			}
			Logger.debug(message);
		}
	}

}
