package org.peng.cos.model;

import java.io.Serializable;
import java.util.List;

public class PagerModel implements Serializable{
	

	private int total;

	private List datas;

	public List getDatas() {
		return datas;
	}

	public void setDatas(List datas) {
		this.datas = datas;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
