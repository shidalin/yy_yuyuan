package nc.ui.yuyuan.order.ace.handler;

import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterEditEvent;
import nc.vo.bd.material.MaterialConvertVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.yy_order.YyOrderBVO;

/**
 * ���ݱ����ֶα༭���¼�
 * 
 * @since 6.0
 * @version 2011-7-12 ����08:17:33
 * @author duy
 */
public class AceBodyAfterEditHandler implements
		IAppEventHandler<CardBodyAfterEditEvent> {

	@Override
	public void handleAppEvent(CardBodyAfterEditEvent e) {
		String tabcode = e.getTableCode();
		if (null != tabcode
				&& !tabcode.equals(e.getBillCardPanel()
						.getCurrentBodyTableCode())) {
			e.getBillCardPanel().setBodyValueAt(e.getOldValue(), e.getRow(),
					e.getKey(), tabcode);
		} else {
			/**
			 * У����������Ƿ�����ͬ����
			 */
			if ("cmaterialvid".equals(e.getKey())) {
				String value = e.getValue().toString();
				int count = e.getBillCardPanel().getBillTable(tabcode)
						.getRowCount();
				// �������ϵ�λ��ȡĬ��������λ
				String querySql = "select pk_measdoc from bd_materialconvert where nvl(dr,0)=0 and pk_material = ? and ispumeasdoc = 'Y'";
				SQLParameter sqlParameter = new SQLParameter();
				sqlParameter.addParam(value);
				try {
					Object pk_measdoc = NCLocator
							.getInstance()
							.lookup(IUAPQueryBS.class)
							.executeQuery(querySql, sqlParameter,
									new ColumnProcessor());
					e.getBillCardPanel().setBodyValueAt(pk_measdoc, e.getRow(), "castunitid");
				} catch (BusinessException e1) {
					// TODO Auto-generated catch block
					ExceptionUtils.wrappBusinessException("���ϲ�ѯĬ��������λ����"
							+ e.getRow());
				}
				for (int i = 0; i < count; i++) {
					if (i != e.getRow()) {
						Object obj = e.getBillCardPanel().getBodyValueAt(i,
								"cmaterialvid");
						String param = obj == null ? "" : obj.toString();
						if (value.equals(param)) {
							Object obj1 = e.getBillCardPanel().getBodyValueAt(
									i, "crowno");
							String repeat_crowno = obj1 == null ? "" : obj1
									.toString();
							// �Ų鵽�ظ����ϣ����������ֵ-����pk_org,pk_group,crowno
							String pk_group = e.getBillCardPanel()
									.getBodyValueAt(e.getRow(), "pk_group") == null ? ""
									: e.getBillCardPanel()
											.getBodyValueAt(e.getRow(),
													"pk_group").toString();
							String pk_org = e.getBillCardPanel()
									.getBodyValueAt(e.getRow(), "pk_org") == null ? ""
									: e.getBillCardPanel()
											.getBodyValueAt(e.getRow(),
													"pk_org").toString();
							String crowno = e.getBillCardPanel()
									.getBodyValueAt(e.getRow(), "crowno") == null ? ""
									: e.getBillCardPanel()
											.getBodyValueAt(e.getRow(),
													"crowno").toString();
							YyOrderBVO bvo = new YyOrderBVO();
							bvo.setPk_group(pk_group);
							bvo.setPk_org(pk_org);
							bvo.setCrowno(crowno);
							e.getBillCardPanel().getBillModel()
									.setBodyRowVO(bvo, e.getRow());
							ExceptionUtils
									.wrappBusinessException("�������ϲ����ظ�,�ظ������к�:"
											+ repeat_crowno);
							return;
						}
					}
					
				}
				MaterialHandler handler = new MaterialHandler();
				handler.afterEdit(e);

			}
			// �����༭���¼�-��ѡ�ɹ�Ĭ�ϵ�λ����£������ʴ���������,�������������ڸ�����
			if ("nastnum".equals(e.getKey())) {
				Object value = e.getValue();
				// ��������
				String cmaterialvid = e.getBillCardPanel().getBodyValueAt(
						e.getRow(), "cmaterialvid") == null ? "" : e
						.getBillCardPanel()
						.getBodyValueAt(e.getRow(), "cmaterialvid").toString();
				if ("".equals(cmaterialvid)) {
					ExceptionUtils.wrappBusinessException("����ѡ������");
					return;
				}
				// ��������λ
				String castunitid = e.getBillCardPanel().getBodyValueAt(
						e.getRow(), "castunitid") == null ? "" : e
						.getBillCardPanel()
						.getBodyValueAt(e.getRow(), "castunitid").toString();
				Map<String, MaterialConvertVO> materialAndMeasdoc = nc.itf.scmpub.reference.uap.bd.material.MaterialPubService
						.queryMaterialConvertByMaterialAndMeasdoc(
								new String[] { cmaterialvid },
								new String[] { castunitid }, new String[] {
										"ispumeasdoc", "measrate" });
				if (materialAndMeasdoc.values() != null
						&& materialAndMeasdoc.values().size() > 0) {
					int count = 0;
					for (MaterialConvertVO convertVO : materialAndMeasdoc
							.values()) {
						UFBoolean ispumeasdoc = convertVO.getIspumeasdoc();
						// ��ѡĬ�ϲɹ���λ
						if (ispumeasdoc != null
								&& UFBoolean.TRUE.equals(ispumeasdoc)) {
							String measrate = convertVO.getMeasrate();// ����λ/����λ
							UFDouble changerate = null;
							UFDouble nastnum = null;
							if (value == null) {
								nastnum = UFDouble.ZERO_DBL;
							} else {
								nastnum = new UFDouble(value.toString());
							}
							if (measrate == null) {
								changerate = new UFDouble(1);
							} else {
								String[] split = measrate.split("/");
								changerate = new UFDouble(split[0])
										.div(new UFDouble(split[1]));
							}
							UFDouble nnum = changerate.multiply(nastnum);
							e.getBillCardPanel().setBodyValueAt(nnum,
									e.getRow(), "nnum");
						} else {
							count++;
						}
					}
					if (count == materialAndMeasdoc.values().size()) {
						e.getBillCardPanel().setBodyValueAt(value, e.getRow(),
								"nnum");
					}
				} else {
					e.getBillCardPanel().setBodyValueAt(value, e.getRow(),
							"nnum");
				}
			}

			// �����༭���¼�-��ѡ�ɹ�Ĭ�ϵ�λ����£������ʴ���������,��������������������
			if ("nnum".equals(e.getKey())) {
				Object value = e.getValue();
				// ��������
				String cmaterialvid = e.getBillCardPanel().getBodyValueAt(
						e.getRow(), "cmaterialvid") == null ? "" : e
						.getBillCardPanel()
						.getBodyValueAt(e.getRow(), "cmaterialvid").toString();
				if ("".equals(cmaterialvid)) {
					ExceptionUtils.wrappBusinessException("����ѡ������");
					return;
				}
				// ��������λ
				String castunitid = e.getBillCardPanel().getBodyValueAt(
						e.getRow(), "castunitid") == null ? "" : e
						.getBillCardPanel()
						.getBodyValueAt(e.getRow(), "castunitid").toString();
				Map<String, MaterialConvertVO> materialAndMeasdoc = nc.itf.scmpub.reference.uap.bd.material.MaterialPubService
						.queryMaterialConvertByMaterialAndMeasdoc(
								new String[] { cmaterialvid },
								new String[] { castunitid }, new String[] {
										"ispumeasdoc", "measrate" });
				if (materialAndMeasdoc.values() != null
						&& materialAndMeasdoc.values().size() > 0) {
					int count = 0;
					for (MaterialConvertVO convertVO : materialAndMeasdoc
							.values()) {
						// MaterialConvertVO convertVO =
						// materialAndMeasdoc.values()
						// .iterator().next();
						UFBoolean ispumeasdoc = convertVO.getIspumeasdoc();
						// ��ѡĬ�ϲɹ���λ
						if (ispumeasdoc != null
								&& UFBoolean.TRUE.equals(ispumeasdoc)) {
							String measrate = convertVO.getMeasrate();// ����λ/����λ
							UFDouble changerate = null;
							UFDouble nnum = null;
							if (value == null) {
								nnum = UFDouble.ZERO_DBL;
							} else {
								nnum = new UFDouble(value.toString());
							}
							if (measrate == null) {
								changerate = new UFDouble(1);
							} else {
								String[] split = measrate.split("/");
								changerate = new UFDouble(split[0])
										.div(new UFDouble(split[1]));
							}
							// UFDouble nastnum = changerate.multiply(nnum);
							UFDouble nastnum = nnum.div(changerate);
							e.getBillCardPanel().setBodyValueAt(nastnum,
									e.getRow(), "nastnum");
						} else {
							count++;
						}
					}
					if (count == materialAndMeasdoc.values().size()) {
						e.getBillCardPanel().setBodyValueAt(value, e.getRow(),
								"nastnum");
					}
				} else {
					e.getBillCardPanel().setBodyValueAt(value, e.getRow(),
							"nastnum");
				}
			}

		}
	}

}
