package com.shsxt.model;

public class CustomerReprieve extends BaseModel{

	private String measure;//暂缓措施
	private Integer lossId;//客户流失
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
	}
	public Integer getLossId() {
		return lossId;
	}
	public void setLossId(Integer lossId) {
		this.lossId = lossId;
	}
	
}
