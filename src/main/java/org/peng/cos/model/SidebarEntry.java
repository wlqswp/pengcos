package org.peng.cos.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class SidebarEntry implements Serializable{

	private String title;
	
	private String tooltip;
	
	private String link;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
