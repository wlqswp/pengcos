package org.peng.cos.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.peng.cos.model.Charactor.CHARACTOR_TYPE;

@Entity
public class CosMessage  implements Serializable
{
	public static enum MSG_TYPE {PIC,VIDEO,AUDIO};
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	private Charactor realCharactor;
	
	@ManyToOne
	private Charactor catoonCharactor;
	
	@Enumerated(EnumType.STRING)
	private MSG_TYPE mtype; 
	

	private int picWidth;
	private int picHeight;
	
	public int getPicWidth() {
		return picWidth;
	}

	public void setPicWidth(int picWidth) {
		this.picWidth = picWidth;
	}

	public int getPicHeight() {
		return picHeight;
	}

	public void setPicHeight(int picHeight) {
		this.picHeight = picHeight;
	}

	private String fileName; 
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private int likeCount;
	
	private int dislikeCount;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Charactor getRealCharactor() {
		return realCharactor;
	}

	public void setRealCharactor(Charactor realCharactor) {
		this.realCharactor = realCharactor;
	}

	public Charactor getCatoonCharactor() {
		return catoonCharactor;
	}

	public void setCatoonCharactor(Charactor catoonCharactor) {
		this.catoonCharactor = catoonCharactor;
	}

	public MSG_TYPE getMtype() {
		return mtype;
	}

	public void setMtype(MSG_TYPE mtype) {
		this.mtype = mtype;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getDislikeCount() {
		return dislikeCount;
	}

	public void setDislikeCount(int dislikeCount) {
		this.dislikeCount = dislikeCount;
	}

}
