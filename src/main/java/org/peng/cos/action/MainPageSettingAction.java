package org.peng.cos.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.peng.cos.manager.AdminManager;
import org.peng.cos.model.Charactor;
import org.peng.cos.model.MainPageSetting;
import org.peng.cos.model.MainpageSettingEntry;
import org.peng.cos.model.MainpageSettingEntry.SETTING_ENTRY_TYPE;

@ManagedBean(name="mainpage")
@ViewScoped
public class MainPageSettingAction extends BaseAction implements Serializable
{
	private int box;
	
	private Map<String,  Charactor> charactorMap = new HashMap<String,  Charactor>();

	private MainPageSetting inputSetting;
	
	@EJB
	private AdminManager adminManager;
	
	
	public List<MainpageSettingEntry> getCharactorEntries()
	{

		return (List<MainpageSettingEntry>)this.getVariValue("getCharactorEntries");
	}
	
	public List<MainpageSettingEntry> getSidebarEntries()
	{
		return (List<MainpageSettingEntry>)this.getVariValue("getSidebarEntries");
	}
	
	public int getBox() {
		return box;
	}

	public void setBox(int box) {
		this.box = box;
	}

	public Map<String, Charactor> getCharactorMap() {
		return charactorMap;
	}

	public void setCharactorMap(Map<String, Charactor> charactorMap) {
		this.charactorMap = charactorMap;
	}

	public synchronized MainPageSetting getInputSetting() {
		if(inputSetting ==null)
		{
			inputSetting = adminManager.findMainPageSetting();
		}
		return inputSetting;
	}
	
	public Object getVariValue(String key)
	{
		Object o = this.getVariableByKey(key);
		
		if(o == null)
		{
			if(key.equals("getCharactorEntries"))
			{
				Iterator<MainpageSettingEntry>iter = getInputSetting().getEntries().iterator();
				List<MainpageSettingEntry> result = new ArrayList();
				while(iter.hasNext())
				{
					MainpageSettingEntry mse = iter.next();
					if(mse.getType() == SETTING_ENTRY_TYPE.REALC || mse.getType() == SETTING_ENTRY_TYPE.CARTOON )
					{
						result.add(mse);
					}
				}
				o = result;
			}
			if(key.equals("getSidebarEntries"))
			{
				Iterator<MainpageSettingEntry>iter = getInputSetting().getEntries().iterator();
				List<MainpageSettingEntry> result = new ArrayList();
				while(iter.hasNext())
				{
					MainpageSettingEntry mse = iter.next();
					if(mse.getType() == SETTING_ENTRY_TYPE.SIDEBAR1 )
					{
						result.add(mse);
					}
				}
				o = result;
			}
			this.setVariableByKey(key, o);
		}

		return o;
	}
	

	
	
}
