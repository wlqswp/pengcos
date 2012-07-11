package org.peng.cos.dao;

import javax.ejb.Stateless;

import org.peng.cos.model.Charactor;
import org.peng.cos.model.Charactor.CHARACTOR_TYPE;
import org.peng.cos.model.PagerModel;

@Stateless
public class CharactorDaoImpl extends AbstractDaoImpl<Charactor> implements CharactorDao<Charactor> {

	public CharactorDaoImpl() {
		super(Charactor.class);

	}

	@Override
	public void addNewCoser(Charactor c)
	{
		em.persist(c);
	}

	public PagerModel listAllCartoonCharactor(int offset, int pagesize) throws Exception
	{
		String sql = "from Charactor c where c.ctype = ?";
		return this.searchPaginated(sql, new Object[]{ CHARACTOR_TYPE.TYPE_CATOON} , offset, pagesize);
	}

	@Override
	public PagerModel listAllRealCharactor(int offset, int pagesize)
			throws Exception {
		String sql = "from Charactor c where c.ctype = ?";
		return this.searchPaginated(sql, new Object[]{ CHARACTOR_TYPE.TYPE_REAL} , offset, pagesize);
	}
	
}
