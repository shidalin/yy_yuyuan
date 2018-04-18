package nc.vo.yuyuan.yy_ordertemp;

import nc.vo.pubapp.pattern.model.entity.view.AbstractDataView;
import nc.vo.pubapp.pattern.model.meta.entity.view.DataViewMetaFactory;
import nc.vo.pubapp.pattern.model.meta.entity.view.IDataViewMeta;
/**
 * 拉单视图VO
 * @author shidl
 *
 */
public class OrderTempViewVO extends AbstractDataView {

	public AggYyOrderTempVO changeToBill() {
		AggYyOrderTempVO aggvo = new AggYyOrderTempVO();
		aggvo.setParent(this.getHead());
		YyOrderTempBVO[] items = new YyOrderTempBVO[] { this.getItem() };
		aggvo.setChildrenVO(items);
		return aggvo;
	}

	public YyOrderTempVO getHead() {
		return (YyOrderTempVO) this.getVO(YyOrderTempVO.class);
	}

	public YyOrderTempBVO getItem() {
		return (YyOrderTempBVO) this.getVO(YyOrderTempBVO.class);
	}

	@Override
	public IDataViewMeta getMetaData() {
		// TODO 自动生成的方法存根
		return DataViewMetaFactory.getInstance().getBillViewMeta(
				AggYyOrderTempVO.class);
	}

	public void setHead(YyOrderTempVO head) {
		this.setVO(head);
	}

	public void setItem(YyOrderTempBVO item) {
		this.setVO(item);
	}
}
