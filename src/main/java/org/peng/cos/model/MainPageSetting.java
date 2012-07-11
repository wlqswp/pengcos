package org.peng.cos.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Max;

@Entity
public class MainPageSetting  implements Serializable
{

	@Id
	@GeneratedValue
	private int id;
	@Max(8)
	private int boxsize = 8;
	
	@OneToMany(mappedBy="mainPageSetting",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy(value = "id ASC")
	private Set<MainpageSettingEntry> entries = new HashSet<MainpageSettingEntry>();

	public Set<MainpageSettingEntry> getEntries() {
		return entries;
	}

	public void setEntries(Set<MainpageSettingEntry> entries) {
		this.entries = entries;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBoxsize() {
		return boxsize;
	}

	public void setBoxsize(int boxsize) {
		this.boxsize = boxsize;
	}

	public int[] getCharactorIds() {
		return charactorIds;
	}

	public void setCharactorIds(int[] charactorIds) {
		this.charactorIds = charactorIds;
	}

	private int[] charactorIds;
	
}
