package org.peng.cos.dao;

import java.util.List;

import javax.ejb.Stateless;

import org.peng.cos.model.MainPageSetting;


@Stateless
public class MainPageSettingDaoImpl extends AbstractDaoImpl<MainPageSetting> implements MainPageSettingDao<MainPageSetting>
{

	public MainPageSettingDaoImpl() {
		super(MainPageSetting.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MainPageSetting findMainPageSetting() {
		String sql = "from MainPageSetting";
		List<MainPageSetting> settings =  em.createQuery(sql).getResultList();
		if(settings.size()>0)
			return settings.get(0);
		else
		{
			return  new MainPageSetting();
		}
	}
	
}
