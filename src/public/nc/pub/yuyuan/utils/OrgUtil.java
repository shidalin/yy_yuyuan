package nc.pub.yuyuan.utils;

import nc.pubimpl.bdlayer.cache.CacheVOQuery;
import nc.vo.org.OrgVO;
import nc.vo.pub.ISuperVO;

/**
 * 组织信息工具类
 * 
 * @author shidl
 * 
 */
public class OrgUtil {

	/**
	 * 根据组织主键获取组织编码
	 * 
	 * @param pk_org
	 * @return
	 */
	public static String getCode(String pk_org) {
		String code = null;
		String[] fields = new String[] { "code" };
		CacheVOQuery cachevoQuery = new CacheVOQuery(OrgVO.class, fields);
		OrgVO[] orgs = (OrgVO[]) cachevoQuery.query(new String[] { pk_org });
		if (orgs != null && orgs.length == 1) {
			code = orgs[0].getCode();
			return code;
		} else {
			return null;
		}
	}

	public static String getPKBycode(String code) {
		String pk_org = null;
		String[] fields = new String[] { "pk_org" };
		String[] whrFields = new String[] { "code" };
		String[][] whrFdValues = new String[][] { { code } };
		CacheVOQuery cachevoQuery = new CacheVOQuery(OrgVO.class, fields);
		OrgVO[] orgs = (OrgVO[]) cachevoQuery.query(whrFields, whrFdValues);

		if (orgs != null && orgs.length == 1) {
			pk_org = orgs[0].getPk_org();
			return pk_org;
		} else {
			return null;
		}
	}

	public static String getVid(String pk_org) {
		String code = null;
		String[] fields = new String[] { "pk_vid" };
		CacheVOQuery cachevoQuery = new CacheVOQuery(OrgVO.class, fields);
		OrgVO[] orgVos = (OrgVO[]) cachevoQuery.query(new String[] { pk_org });
		if (orgVos != null && orgVos.length == 1) {
			code = orgVos[0].getPk_vid();
			return code;
		} else {
			return null;
		}

	}

	/**
	 * 根据组织主键获取组织名称
	 * 
	 * @param pk_mate
	 * @return
	 */
	public static String getName(String pk_mate) {
		String name = null;
		String[] fields = new String[] { "name" };
		CacheVOQuery cachevoQuery = new CacheVOQuery(OrgVO.class, fields);
		OrgVO[] materialvos = (OrgVO[]) cachevoQuery
				.query(new String[] { pk_mate });
		if (materialvos != null && materialvos.length == 1) {
			name = materialvos[0].getName();
			return name;
		} else {
			return null;
		}
	}
}
