package nc.itf.yuyuan;

import java.util.Map;

import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.pub.BusinessException;
import nc.vo.yuyuan.yy_matercontrast.AggYyMaterContrastVO;

public interface IYy_matercontrastMaintain {

	public void delete(AggYyMaterContrastVO[] vos) throws BusinessException;

	public AggYyMaterContrastVO[] insert(AggYyMaterContrastVO[] vos) throws BusinessException;

	public AggYyMaterContrastVO[] update(AggYyMaterContrastVO[] vos) throws BusinessException;

	public AggYyMaterContrastVO[] query(IQueryScheme queryScheme)
			throws BusinessException;
	
	public Map<String,String> queryDispatchstyale()throws BusinessException;

}