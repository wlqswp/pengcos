package org.peng.cos.dao;

import javax.ejb.Local;

import org.peng.cos.model.MainPageSetting;


@Local
public interface MainPageSettingDao<T> extends AbstractDao<T>
{
	public MainPageSetting findMainPageSetting();
}
