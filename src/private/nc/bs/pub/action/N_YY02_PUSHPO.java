package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.framework.common.NCLocator;
import nc.bs.pubapp.pf.action.AbstractPfAction;
import nc.impl.pubapp.pattern.rule.processer.CompareAroundProcesser;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pubapp.AppContext;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.yy_errorlog.YyErrorLogVO;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;
import nc.vo.yuyuan.yy_order.PushCTDto;
import nc.vo.yuyuan.yy_order.YyOrderBVO;

/**
 * ��ʽ���ɲɹ�����:���ñ�׼�˵����
 * 
 * @author shidl
 * 
 */
public class N_YY02_PUSHPO extends AbstractPfAction<AggYyOrderVO> {

	@Override
	protected CompareAroundProcesser<AggYyOrderVO> getCompareAroundProcesserWithRules(
			Object userObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AggYyOrderVO[] processBP(Object userObj,
			AggYyOrderVO[] clientFullVOs, AggYyOrderVO[] originBills) {
		// [����+��֯]�����ɹ���ͬ��ȡ��Ӧ����Ϣ,�洢��vdef1,�Ƶ���������[��Ӧ��+������+��������]���зֵ�
		List<String> list = new ArrayList<String>();
		List<String> orderPKList = new ArrayList<String>();
		Map<String, PushCTDto> pk2dto = new HashMap<String, PushCTDto>();
		for (AggYyOrderVO agg : clientFullVOs) {
			orderPKList.add(agg.getParentVO().getPk_order());
			for (CircularlyAccessibleValueObject obj : agg.getChildrenVO()) {
				YyOrderBVO bvo = (YyOrderBVO) obj;
				String key = agg.getParentVO().getPk_org() + "_"
						+ bvo.getCmaterialvid();
				list.add(key);
			}
		}
		try {
			List<PushCTDto> list2 = this.process(list, orderPKList);
			StringBuffer message = new StringBuffer();
			if (list2.size() == 0) {
				for (AggYyOrderVO agg : clientFullVOs) {
					message.append("���ݺ�:" + agg.getParentVO().getVbillcode()
							+ ",û�й����Ĳɹ���ͬ����\n");
				}
				if (message.length() > 0) {
					ExceptionUtils.wrappBusinessException(message.toString());
				}
			}
			for (PushCTDto dto : list2) {
				pk2dto.put(dto.getPk_order() + "_" + dto.getCmaterialvid(), dto);
			}
			ArrayList<YyErrorLogVO> list3 = new ArrayList<YyErrorLogVO>();
			for (AggYyOrderVO agg : clientFullVOs) {
				for (CircularlyAccessibleValueObject co : agg.getChildrenVO()) {
					YyOrderBVO bvo = (YyOrderBVO) co;
					PushCTDto dto = pk2dto.get(agg.getParentVO().getPk_order()
							+ "_" + bvo.getCmaterialvid());
					if (dto == null) {
						message.append("���ݺ�:"
								+ agg.getParentVO().getVbillcode()
								+ "�к�:"
								+ bvo.getCrowno()
								+ "���ϱ���:"
								+ nc.pub.yuyuan.utils.MaterialUtil.getCode(bvo
										.getCmaterialvid())
								+ ",��������:"
								+ nc.pub.yuyuan.utils.MaterialUtil.getName(bvo
										.getCmaterialvid()) + ",û�й����Ĳɹ���ͬ����\n");
						YyErrorLogVO logvo = new YyErrorLogVO();
						logvo.setErrortyle("��������ʽ���ɲɹ����������ɹ���ͬУ��");
						logvo.setErrordate(AppContext.getInstance()
								.getBusiDate());
						logvo.setErrorinfo("���ݺ�:"
								+ agg.getParentVO().getVbillcode()
								+ "�к�:"
								+ bvo.getCrowno()
								+ "���ϱ���:"
								+ nc.pub.yuyuan.utils.MaterialUtil.getCode(bvo
										.getCmaterialvid())
								+ ",��������:"
								+ nc.pub.yuyuan.utils.MaterialUtil.getName(bvo
										.getCmaterialvid()) + ",û�й����Ĳɹ���ͬ����\n");
						logvo.setPk_org(agg.getParentVO().getPk_org());
						logvo.setBillno(agg.getParentVO().getVbillcode());
						logvo.setBill(agg.getParentVO().getPk_billtypecode());
						logvo.setBillmaker(agg.getParentVO().getBillmaker());
						logvo.setBilldate(agg.getParentVO().getDmakedate());
						logvo.setBillbno(bvo.getCrowno());
						logvo.setBillbmaterial(bvo.getCmaterialvid());
						list3.add(logvo);
						continue;
					}
					// �пգ���¼���ݵ�������־��
					if (dto.getCvendorid() == null
							|| "".equals(dto.getCvendorid())) {
						String msg = "���ݺ�:" + agg.getParentVO().getVbillcode()
								+ ",��֯����:" + dto.getOrg_code() + ",�к�:"
								+ dto.getCrowno() + ",���ϱ���:"
								+ dto.getMaterial_code();
						message.append(msg + ",�����Ĳɹ���ͬ��Ӧ����ϢΪ��\n");
						YyErrorLogVO logvo = new YyErrorLogVO();
						logvo.setErrortyle("��������ʽ���ɲɹ����������ɹ���ͬУ��");
						logvo.setErrordate(AppContext.getInstance()
								.getBusiDate());
						logvo.setErrorinfo(msg);
						logvo.setPk_org(agg.getParentVO().getPk_org());
						logvo.setBillno(agg.getParentVO().getVbillcode());
						logvo.setBill(agg.getParentVO().getPk_billtypecode());
						logvo.setBillmaker(agg.getParentVO().getBillmaker());
						logvo.setBilldate(agg.getParentVO().getDmakedate());
						logvo.setBillbno(dto.getCrowno());
						logvo.setBillbmaterial(dto.getCmaterialvid());
						list3.add(logvo);
					}
					// ����ƥ�乩Ӧ����Ϣ
					bvo.setVdef1(dto.getCvendorid());
				}
			}
			// ���ú�̨�ӿڴ洢������Ϣ,��������ӿ�
			if (list3.size() > 0) {
				NCLocator.getInstance()
						.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
						.insertLogVO_RequiresNew(list3);
			}
			if (message.length() > 0) {
				ExceptionUtils.wrappBusinessException(message.toString());
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			ExceptionUtils.wrappBusinessException(e.getMessage());
		}
		return clientFullVOs;
	}

	/**
	 * [��֯+����]�����ɹ���ͬ
	 * 
	 * @param list
	 * @return
	 * @throws BusinessException
	 */
	private List<PushCTDto> process(List<String> list, List<String> orderPKList)
			throws BusinessException {
		StringBuffer sb = new StringBuffer();
		sb.append("	select t1.pk_order,	")
				.append("	       mm1.cvendorid,	")
				.append("	       t2.cmaterialvid,	")
				.append("	       t2.crowno,	")
				.append("	       t5.code         material_code,	")
				.append("	       t6.code         org_code	")
				.append("	  from yy_order t1	")
				.append("	 inner join yy_order_b t2	")
				.append("	    on t1.pk_order = t2.pk_order	")
				.append("	 inner join (select t7.pk_scopeorg || '_' || t4.pk_material pk_mm1,	")
				.append("	                    t3.cvendorid cvendorid	")
				.append("	               from ct_pu t3	")
				.append("	              inner join ct_pu_b t4	")
				.append("	                 on t3.pk_ct_pu = t4.pk_ct_pu	")
				.append("	              inner join ct_scope t7	")
				.append("	                 on t3.pk_ct_pu = t7.pk_ct_pu	")
				.append("	              where nvl(t3.dr, 0) = 0	")
				.append("	                and nvl(t4.dr, 0) = 0	")
				.append("	                and nvl(t7.dr, 0) = 0	")
				.append("	                and t3.fstatusflag = 1	")
				.append("	                and t3.blatest = 'Y') mm1	")
				.append("	    on t1.pk_org || '_' || t2.cmaterialvid = mm1.pk_mm1	")
				.append("	 inner join bd_material t5	")
				.append("	    on t2.cmaterialvid = t5.pk_material	")
				.append("	 inner join org_orgs t6	")
				.append("	    on t1.pk_org = t6.pk_org	")
				.append("	 where nvl(t1.dr, 0) = 0	")
				.append("	   and nvl(t2.dr, 0) = 0	")
				.append("	   and nvl(t5.dr, 0) = 0	")
				.append("	   and nvl(t6.dr, 0) = 0	")
				.append("	   and  ")
				.append(nc.pubitf.bd.reportitem.pub.SqlUtils.getInStr(
						"t1.pk_org || '_' || t2.cmaterialvid", list, true))
				.append("	   and  ")
				.append(nc.pubitf.bd.reportitem.pub.SqlUtils.getInStr(
						"t1.pk_order", orderPKList, true));
		BaseDAO dao = new BaseDAO();
		List<PushCTDto> executeQuery = (List<PushCTDto>) dao.executeQuery(
				sb.toString(), new BeanListProcessor(PushCTDto.class));
		return executeQuery;
	}
}
