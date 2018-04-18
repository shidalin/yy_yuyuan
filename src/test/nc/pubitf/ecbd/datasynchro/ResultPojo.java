package nc.pubitf.ecbd.datasynchro;

import java.io.Serializable;

public class ResultPojo implements Serializable{
	
	private Object result;
	private String errorMsg;

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
