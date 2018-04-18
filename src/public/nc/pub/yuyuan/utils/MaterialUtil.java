package nc.pub.yuyuan.utils;

import nc.pubimpl.bdlayer.cache.CacheVOQuery;
import nc.vo.bd.material.MaterialVO;

/**
 * 物料工具类
 * 
 * @author shidl
 * 
 */
public class MaterialUtil {
	/**
	 * 根据物料主键获取物料编码
	 * 
	 * @param pk_mate
	 * @return
	 */
	public static String getCode(String pk_mate) {
		String code = null;
		String[] fields = new String[] { "code" };
		CacheVOQuery cachevoQuery = new CacheVOQuery(MaterialVO.class, fields);
		MaterialVO[] materialvos = (MaterialVO[]) cachevoQuery
				.query(new String[] { pk_mate });
		if (materialvos != null && materialvos.length == 1) {
			code = materialvos[0].getCode();
			return code;
		} else {
			return null;
		}
	}

	/**
	 * 根据物料主键获取物料名称
	 * @param pk_mate
	 * @return
	 */
	public static String getName(String pk_mate) {
		String name = null;
		String[] fields = new String[] { "name" };
		CacheVOQuery cachevoQuery = new CacheVOQuery(MaterialVO.class, fields);
		MaterialVO[] materialvos = (MaterialVO[]) cachevoQuery
				.query(new String[] { pk_mate });
		if (materialvos != null && materialvos.length == 1) {
			name = materialvos[0].getName();
			return name;
		} else {
			return null;
		}
	}
}
