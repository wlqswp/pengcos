package org.peng.cos.manager;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.peng.cos.dao.CharactorDao;
import org.peng.cos.dao.CosMessageDao;
import org.peng.cos.dao.MainPageSettingDao;
import org.peng.cos.model.Charactor;
import org.peng.cos.model.CosMessage;
import org.peng.cos.model.CosMessage.MSG_TYPE;
import org.peng.cos.model.MainPageSetting;
import org.peng.cos.model.PagerModel;

@Stateless
public class AdminManagerImpl implements AdminManager
{
	@EJB
	private CharactorDao charactorDao;
	
	@EJB
	private CosMessageDao cosMessageDao;
	
	@EJB
	private MainPageSettingDao mainPageSettingDao;

	public void addNewCoser( Charactor c)
	{
		charactorDao.addNewCoser(c);
		
	}
	
	public void deleteCosMessage(int cmid)
	{
		CosMessage cm = (CosMessage)cosMessageDao.findModel(cmid);
		if(cm!=null)
		{
			cosMessageDao.deleteModel(cm);
			
			
		}
	
	}
	
	@Override
	public List<CosMessage> listRealCharactorRelatedList(int cid, MSG_TYPE type) {
		return cosMessageDao.listRealCharactorRelatedList(cid,type);
	}

	@Override
	public List<CosMessage> listCartoonCharactorRelatedList(int cid,MSG_TYPE type) {
		return cosMessageDao.listCartoonCharactorRelatedList(cid,type);
	}
	
	public List<CosMessage> listNoRealPicMessages()
	{
		return cosMessageDao.listNoRealPicMessages();
	}
	public List<CosMessage> listNoCartoonPicMessages()
	{
		return cosMessageDao.listNoCartoonPicMessages();
	}
	
	public PagerModel listAllCartoonCharactor(int offset, int pagesize)
	{
		try {
			return this.charactorDao.listAllCartoonCharactor(offset, pagesize);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public PagerModel listAllRealCharactor(int offset, int pagesize) {
		try {
			return this.charactorDao.listAllRealCharactor(offset, pagesize);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void updateCoser( Charactor c)
	{
		charactorDao.updateModel(c);
	}

	@Override
	public boolean deleteCoser(int cid) 
	{
		try
		{
			charactorDao.deleteModel(charactorDao.findModel(cid));
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
		
	}

	@Override
	public CosMessage addPicCosMessage(String filename, int width, int height, int realcid, int cartooncid)
	{
		CosMessage cm = new CosMessage();
		cm.setFileName(filename);
		cm.setMtype(CosMessage.MSG_TYPE.PIC);
		cm.setPicWidth(width);
		cm.setPicHeight(height);
		if(realcid>0)
			cm.setRealCharactor((Charactor)charactorDao.findModel(realcid));
		if(cartooncid>0)
			cm.setRealCharactor((Charactor)charactorDao.findModel(cartooncid));
		
		cosMessageDao.addModel(cm);
		return cm;
	}


	@Override
	public MainPageSetting findMainPageSetting() 
	{
		return mainPageSettingDao.findMainPageSetting();
	}

	
	
	
	
}
