package org.peng.cos.manager;

import java.util.List;

import javax.ejb.Local;

import org.peng.cos.model.Charactor;
import org.peng.cos.model.CosMessage;
import org.peng.cos.model.CosMessage.MSG_TYPE;
import org.peng.cos.model.MainPageSetting;
import org.peng.cos.model.PagerModel;

@Local
public interface AdminManager
{
	public void addNewCoser( Charactor c);
	
	public PagerModel listAllCartoonCharactor(int offset, int pagesize) ;
	public PagerModel listAllRealCharactor(int offset, int pagesize) ;
	public CosMessage addPicCosMessage(String filename, int width, int height, int realcid, int cartooncid);
	public void updateCoser( Charactor c);
	public boolean deleteCoser( int cid);
	
	public List<CosMessage> listRealCharactorRelatedList(int cid,MSG_TYPE type);
	public List<CosMessage> listCartoonCharactorRelatedList(int cid,MSG_TYPE type);
	public List<CosMessage> listNoRealPicMessages();
	public List<CosMessage> listNoCartoonPicMessages();
	public void deleteCosMessage(int cmid);
	public MainPageSetting findMainPageSetting();
}
