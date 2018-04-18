package nc.vo.yuyuan;

import java.util.List;

public class PoOrderDTOForWMS {
	private PoOrderHDTOForWMS slipHeader;
	private List<PoOrderBDTOForWMS> slipDetail;

	public PoOrderHDTOForWMS getSlipHeader() {
		return slipHeader;
	}

	public void setSlipHeader(PoOrderHDTOForWMS slipHeader) {
		this.slipHeader = slipHeader;
	}

	public List<PoOrderBDTOForWMS> getSlipDetail() {
		return slipDetail;
	}

	public void setSlipDetail(List<PoOrderBDTOForWMS> slipDetail) {
		this.slipDetail = slipDetail;
	}

}
