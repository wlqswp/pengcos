package org.peng.cos.dao;

import java.util.List;

import javax.ejb.Stateless;

import org.peng.cos.model.CosMessage;
import org.peng.cos.model.CosMessage.MSG_TYPE;

@Stateless
public class CosMessageDaoImpl extends AbstractDaoImpl<CosMessage> implements CosMessageDao<CosMessage>
{

	public CosMessageDaoImpl() {
		super(CosMessage.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<CosMessage> listRealCharactorRelatedList(int cid, MSG_TYPE type) {
		String sql ="from CosMessage cos where cos.realCharactor.id=" + cid +" and cos.mtype= ? " ;
		return em.createQuery(sql).setParameter(1, type).getResultList();
	}

	@Override
	public List<CosMessage> listCartoonCharactorRelatedList(int cid, MSG_TYPE type) {
		String sql ="from CosMessage cos where cos.catoonCharactor.id=" + cid+" and cos.mtype= ? " ;
		return em.createQuery(sql).setParameter(1, type).getResultList();
	}

	@Override
	public List<CosMessage> listNoRealPicMessages() 
	{
		String sql = "from CosMessage cos where cos.realCharactor is null and cos.mtype= ? " ;
		return em.createQuery(sql).setParameter(1, MSG_TYPE.PIC).getResultList();
	}

	@Override
	public List<CosMessage> listNoCartoonPicMessages() {
		String sql = "from CosMessage cos where cos.catoonCharactor is null and cos.mtype= ? " ;
		return em.createQuery(sql).setParameter(1, MSG_TYPE.PIC).getResultList();
	}

	
	

}
