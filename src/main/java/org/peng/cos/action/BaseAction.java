package org.peng.cos.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.peng.cos.scopevo.ConstantBean;
import org.peng.cos.util.ContextUtil;
import org.peng.cos.util.ThreadUtil;


public abstract class BaseAction implements Serializable
{
	protected String msgInfo; 

	protected Map variableMap = new HashMap();
	
	protected int page;

	@Inject
	protected ConstantBean constant;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		ThreadUtil.setPageOffset((page-1) * constant.getPageSize() );
		this.page = page;
	}

	public void addFaceMessage(String summary, String detail)
	{
	     FacesMessage msg = new FacesMessage(summary,detail);  
	 	FacesContext.getCurrentInstance().addMessage(null, msg);
	     
	}
	public void addI18FaceMessage(String key, Object[] param)
	{
		String message= null;
		if(param==null)
			message = ContextUtil.getText(key);
		else
			message =  ContextUtil.getText(key, param);
		
		FacesMessage fm = new FacesMessage(message);
		FacesContext.getCurrentInstance().addMessage(null,fm);
	}
	
	public void addI18FaceMessage(String messageFor, String key, Object[] param)
	{
		String message= null;
		if(param==null)
			message = ContextUtil.getText(key);
		else
			message =  ContextUtil.getText(key, param);
		
		FacesMessage fm = new FacesMessage(message);
		FacesContext.getCurrentInstance().addMessage(messageFor,fm);
	}
	
	public String getText(String key)
	{
		return ContextUtil.getText(key);
	}
	
	public String getText(String key, Object[] params)
	{
		return ContextUtil.getText(key, params);
	}
	
	
	public String getMsgInfo() {
		return msgInfo;
	}

	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}
	
	protected Object getVariableByKey(String key)
	{
		return variableMap.get(key);
	}
	
	protected void setVariableByKey(String key, Object o)
	{
		variableMap.put(key, o);
	}
	



}
