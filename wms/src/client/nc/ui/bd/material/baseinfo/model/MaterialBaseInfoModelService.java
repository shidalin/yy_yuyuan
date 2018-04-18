/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package nc.ui.bd.material.baseinfo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import nc.bs.framework.common.NCLocator;
import nc.itf.bd.material.baseinfo.IMaterialBaseInfoExtendService;
import nc.itf.bd.material.baseinfo.IMaterialBaseInfoService;
import nc.ui.bd.errorlog.ErrorLogShowUtil;
import nc.ui.bd.material.baseinfo.model.MaterialBaseInfoModel;
import nc.ui.bd.pub.actions.IAnsyDelService;
import nc.ui.bd.pub.extend.ExtendContext;
import nc.ui.bd.pub.extend.ExtendInfo;
import nc.ui.bd.pub.extend.PrivateServiceContext;
import nc.ui.bd.pub.extend.PrivateServiceInfo;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.model.IAppModelService;
import nc.vo.bd.errorlog.ErrLogReturnValue;
import nc.vo.bd.material.MaterialVO;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.uif2.LoginContext;

public class MaterialBaseInfoModelService implements IAppModelService,
		IAnsyDelService {
	private MaterialBaseInfoModel model = null;
	private IMaterialBaseInfoService service = null;
	private IMaterialBaseInfoExtendService extendSerivce = null;
	private ExtendContext extendContext;

	public void setExtendContext(ExtendContext extendContext) {
		this.extendContext = extendContext;
	}

	private PrivateServiceContext generatePrivateSerContext() {
		PrivateServiceContext privateSerContext = new PrivateServiceContext();
		if (this.extendContext != null) {
			List extendInfos = this.extendContext.getExtendInfoList();
			Iterator i$ = extendInfos.iterator();

			while (i$.hasNext()) {
				ExtendInfo info = (ExtendInfo) i$.next();
				PrivateServiceInfo newInfo = new PrivateServiceInfo();
				newInfo.setIdentifier(info.getIdentifier());
				newInfo.setPersistenceService(info.getPersistenceService());
				newInfo.setQueryService(info.getQueryService());
				privateSerContext.addExtendInfo(newInfo);
			}
		}

		return privateSerContext;
	}

	private Map<String, Object> getExtendObjMap() {
		HashMap extendObjMap = new HashMap();
		if (this.extendContext != null) {
			List extendInfos = this.extendContext.getExtendInfoList();
			Iterator i$ = extendInfos.iterator();

			while (i$.hasNext()) {
				ExtendInfo info = (ExtendInfo) i$.next();
				extendObjMap.put(info.getIdentifier(), info.getExtendeditor()
						.getValue());
			}
		}

		return extendObjMap;
	}

	public void delete(Object object) throws Exception {
		deleteCheck();
		this.getBaseInfoExtendService().deleteMaterialWithExtendVO(
				(MaterialVO) object, this.getExtendObjMap(),
				this.generatePrivateSerContext());
	}

	private void deleteCheck() throws BusinessException{

		// TODO Auto-generated method stub
		Object[] selectedOperaDatas = model.getSelectedOperaDatas();
		if (selectedOperaDatas == null || selectedOperaDatas.length == 0) {
//			ExceptionUtils.wrappBusinessException("请选择有效操作数据");
			throw new BusinessException("删除失败, 请选择有效操作数据");
//			ShowStatusBarMsgUtil.showErrorMsg("删除失败", "请选择有效操作数据",
//					model.getContext());
		}
		ArrayList<MaterialVO> arrayList = new ArrayList<MaterialVO>();
		for (Object selectData : selectedOperaDatas) {
			MaterialVO vo = (MaterialVO) selectData;
			if (!"Y".equals(vo.getDef20())) {
				continue;
			} else {
				// 调用UAP接口进行查询
				arrayList.add(vo);
			}
		}
		if (arrayList.size() > 0) {
			StringBuffer stringBuffer = new StringBuffer();
			for (MaterialVO vo : arrayList) {
				stringBuffer.append(vo.getCode());
			}
			String message = "已传送WMS档案不可删除，错误档案编码：" + stringBuffer.toString();
			throw new BusinessException(message);
//			ShowStatusBarMsgUtil.showErrorMsg("删除失败", message,
//					model.getContext());
		}
	}
	public Object update(Object object) throws Exception {
		MaterialVO[] vos = this.getBaseInfoExtendService()
				.updateMaterialWithExtendVO((MaterialVO) object,
						this.getExtendObjMap(),
						this.generatePrivateSerContext());
		this.directUpdateExistsVOs(vos);
		return null;
	}

	public Object[] queryByDataVisibilitySetting(LoginContext context)
			throws Exception {
		return null;
	}

	public Object insert(Object object) throws Exception {
		long begin = System.currentTimeMillis();
		ErrLogReturnValue result;
		if (this.getModel().getCreateVersionPk() != null) {
			result = this.getBaseInfoExtendService()
					.createMaterialVersionWithExtendVO((MaterialVO) object,
							this.getModel().getCreateVersionPk(),
							this.getExtendObjMap(),
							this.generatePrivateSerContext());
			Object[] objs = (Object[]) ((Object[]) this.showErrorMsg(result,
					begin));
			this.directUpdateExistsVOs((MaterialVO[]) ((MaterialVO[]) objs[1]));
			return objs[0];
		} else if (this.getModel().getCopyPk() != null) {
			result = this.getBaseInfoExtendService()
					.copyInsertMaterialWithExtendVO((MaterialVO) object,
							this.getModel().getCopyPk(),
							this.getExtendObjMap(),
							this.generatePrivateSerContext());
			return this.showErrorMsg(result, begin);
		} else {
			return this.getBaseInfoExtendService().insertMaterialWithExtendVO(
					(MaterialVO) object, this.getExtendObjMap(),
					this.generatePrivateSerContext());
		}
	}

	private void directUpdateExistsVOs(MaterialVO[] vos) {
		if (vos != null && vos.length != 0) {
			ArrayList list = new ArrayList();
			MaterialVO[] arr$ = vos;
			int len$ = vos.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				MaterialVO materialVO = arr$[i$];
				int row = this.getModel().findBusinessData(materialVO);
				if (row >= 0) {
					list.add(materialVO);
				}
			}

			this.getModel().directlyUpdate(list.toArray(new MaterialVO[0]));
		}
	}

	private Object showErrorMsg(ErrLogReturnValue value, long begin) {
		return value.getErrLogResult().getErrorMessagegNum() > 0 ? ErrorLogShowUtil
				.showErrorDialog(this.getModel().getContext().getEntranceUI(),
						System.currentTimeMillis() - begin, value) : value
				.getReturnValue();
	}

	public IMaterialBaseInfoService getService() {
		if (this.service == null) {
			this.service = (IMaterialBaseInfoService) NCLocator.getInstance()
					.lookup(IMaterialBaseInfoService.class);
		}

		return this.service;
	}

	public IMaterialBaseInfoExtendService getBaseInfoExtendService() {
		if (this.extendSerivce == null) {
			this.extendSerivce = (IMaterialBaseInfoExtendService) NCLocator
					.getInstance().lookup(IMaterialBaseInfoExtendService.class);
		}

		return this.extendSerivce;
	}

	public void setModel(MaterialBaseInfoModel model) {
		this.model = model;
	}

	public MaterialBaseInfoModel getModel() {
		return this.model;
	}

	public ErrLogReturnValue checkBeforeUpdateMabasclass(MaterialVO material)
			throws BusinessException {
		return this.getService().checkBeforeUpdateMabasclass(material);
	}

	public void ansyDelteVos(Object obj) throws BusinessException {
		if (obj instanceof Object[]) {
			Object[] objs = (Object[]) ((Object[]) obj);
			this.getService().ansyDeleteMaterial(this.convertToVOArray(objs));
		} else {
			this.getService().ansyDeleteMaterial(
					new MaterialVO[] { (MaterialVO) obj });
		}

	}

	private MaterialVO[] convertToVOArray(Object[] object) {
		MaterialVO[] materialvos = new MaterialVO[object.length];

		for (int i = 0; i < object.length; ++i) {
			materialvos[i] = (MaterialVO) object[i];
		}

		return materialvos;
	}
}