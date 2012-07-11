package org.peng.cos.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Access(AccessType.FIELD)
public class Charactor implements Serializable
{
	public static enum CHARACTOR_TYPE {TYPE_REAL, TYPE_CATOON};
	
	@Id
	@GeneratedValue
	private int id;
	
	@Enumerated(EnumType.STRING)
	private CHARACTOR_TYPE ctype; 
	
	@Size(max=255)
	private String name;
    
    @Basic(fetch=FetchType.LAZY)
    @Lob
    @Column(length=3000000)
    private byte[] logo;
    

	@Basic(fetch=FetchType.LAZY)
    @Lob
    private String discription;
	
	private String link;
	
	
    public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CHARACTOR_TYPE getCtype() {
		return ctype;
	}

	public void setCtype(CHARACTOR_TYPE ctype) {
		this.ctype = ctype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Collection<CosMessage> getrMessages() {
		return rMessages;
	}

	public void setrMessages(Collection<CosMessage> rMessages) {
		this.rMessages = rMessages;
	}

	public Collection<CosMessage> getcMessages() {
		return cMessages;
	}

	public void setcMessages(Collection<CosMessage> cMessages) {
		this.cMessages = cMessages;
	}

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastUpdateDate;
	
	
	
	@OneToMany(mappedBy="realCharactor", fetch=FetchType.LAZY)
	@OrderBy("id desc")
	private Collection<CosMessage> rMessages;
	
	@OneToMany(mappedBy="catoonCharactor", fetch=FetchType.LAZY)
	@OrderBy("id desc")
	private Collection<CosMessage> cMessages;
	
	
}
