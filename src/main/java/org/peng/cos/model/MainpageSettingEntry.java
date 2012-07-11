package org.peng.cos.model;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MainpageSettingEntry  implements Serializable{

	public static enum SETTING_ENTRY_TYPE {REALC,CARTOON, SIDEBAR1};
	@Id
	@GeneratedValue
	private int id;
	
	@Enumerated(EnumType.STRING)
	private SETTING_ENTRY_TYPE type;
	
	@ManyToOne
	private MainPageSetting mainPageSetting;
	
	@ManyToOne 
	private Charactor charactor;
	
	@Embedded
	private SidebarEntry sidebarEntry; 
	
	public MainPageSetting getMainPageSetting() {
		return mainPageSetting;
	}

	public void setMainPageSetting(MainPageSetting mainPageSetting) {
		this.mainPageSetting = mainPageSetting;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SETTING_ENTRY_TYPE getType() {
		return type;
	}

	public void setType(SETTING_ENTRY_TYPE type) {
		this.type = type;
	}

	

	
	public Charactor getCharactor() {
		return charactor;
	}

	public void setCharactor(Charactor charactor) {
		this.charactor = charactor;
	}

	public SidebarEntry getSidebarEntry() {
		return sidebarEntry;
	}

	public void setSidebarEntry(SidebarEntry sidebarEntry) {
		this.sidebarEntry = sidebarEntry;
	}


	
}
