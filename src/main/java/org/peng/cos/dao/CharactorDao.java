package org.peng.cos.dao;

import javax.ejb.Local;

import org.peng.cos.model.Charactor;
import org.peng.cos.model.PagerModel;

@Local
public interface CharactorDao<T> extends AbstractDao<T>
{
	public void addNewCoser( Charactor c);
	
	public PagerModel listAllCartoonCharactor(int offset, int pagesize) throws Exception;
	
	public PagerModel listAllRealCharactor(int offset, int pagesize) throws Exception;
}
