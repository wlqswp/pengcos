package org.peng.cos.dao;

import java.util.List;

import javax.ejb.Local;

import org.peng.cos.model.CosMessage;
import org.peng.cos.model.CosMessage.MSG_TYPE;

@Local
public interface CosMessageDao<T> extends AbstractDao<T>
{
	public List<CosMessage> listRealCharactorRelatedList(int cid, MSG_TYPE type );
	public List<CosMessage> listCartoonCharactorRelatedList(int cid, MSG_TYPE type);
	
	public List<CosMessage> listNoRealPicMessages();
	public List<CosMessage> listNoCartoonPicMessages();
}
