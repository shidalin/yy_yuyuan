package nc.exception.yuyuan.yy_order;

/**
 * 推单交互信息异常
 * 
 * @author shidl
 * 
 */
public class PushSucessException extends Exception {

	private nc.vo.pu.m21.entity.OrderVO[] inObject;

	public nc.vo.pu.m21.entity.OrderVO[] getInObject() {
		return inObject;
	}

	public void setInObject(nc.vo.pu.m21.entity.OrderVO[] inObject) {
		this.inObject = inObject;
	}

}
