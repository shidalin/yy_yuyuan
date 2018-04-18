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
 * 单据表体字段编辑后事件
 * 
 * @since 6.0
 * @version 2011-7-12 下午08:17:33
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
			 * 校验界面数据是否有相同物料
			 */
			if ("cmaterialvid".equals(e.getKey())) {
				String value = e.getValue().toString();
				int count = e.getBillCardPanel().getBillTable(tabcode)
						.getRowCount();
				// 处理物料单位，取默认生产单位
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
					ExceptionUtils.wrappBusinessException("物料查询默认生产单位错误"
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
							// 排查到重复物料，清空行数据值-保留pk_org,pk_group,crowno
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
									.wrappBusinessException("表体物料不能重复,重复数据行号:"
											+ repeat_crowno);
							return;
						}
					}
					
				}
				MaterialHandler handler = new MaterialHandler();
				handler.afterEdit(e);

			}
			// 数量编辑后事件-勾选采购默认单位情况下，换算率带出主数量,否则主数量等于辅数量
			if ("nastnum".equals(e.getKey())) {
				Object value = e.getValue();
				// 物料主键
				String cmaterialvid = e.getBillCardPanel().getBodyValueAt(
						e.getRow(), "cmaterialvid") == null ? "" : e
						.getBillCardPanel()
						.getBodyValueAt(e.getRow(), "cmaterialvid").toString();
				if ("".equals(cmaterialvid)) {
					ExceptionUtils.wrappBusinessException("请先选择物料");
					return;
				}
				// 辅计量单位
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
						// 勾选默认采购单位
						if (ispumeasdoc != null
								&& UFBoolean.TRUE.equals(ispumeasdoc)) {
							String measrate = convertVO.getMeasrate();// 主单位/辅单位
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

			// 数量编辑后事件-勾选采购默认单位情况下，换算率带出辅数量,否则主数量带出辅数量
			if ("nnum".equals(e.getKey())) {
				Object value = e.getValue();
				// 物料主键
				String cmaterialvid = e.getBillCardPanel().getBodyValueAt(
						e.getRow(), "cmaterialvid") == null ? "" : e
						.getBillCardPanel()
						.getBodyValueAt(e.getRow(), "cmaterialvid").toString();
				if ("".equals(cmaterialvid)) {
					ExceptionUtils.wrappBusinessException("请先选择物料");
					return;
				}
				// 辅计量单位
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
						// 勾选默认采购单位
						if (ispumeasdoc != null
								&& UFBoolean.TRUE.equals(ispumeasdoc)) {
							String measrate = convertVO.getMeasrate();// 主单位/辅单位
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
