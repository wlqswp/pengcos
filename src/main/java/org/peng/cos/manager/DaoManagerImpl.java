package org.peng.cos.manager;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DaoManagerImpl implements DaoManager
{
	@PersistenceContext
	private EntityManager em;


	public Object findNativeEntityAttributes(String classname,
			String indentityAttribute, Object indentityValue,
			String[] attributeNames) 
	{
		
			if  (attributeNames == null)
			{
				return null;	
			}
			
			StringBuffer sql = new StringBuffer("select ");
			
			int size = attributeNames.length;
			for(int i=0; i<size ; i++)
			{
				sql.append( attributeNames[i]);
				if(i != (size-1))
				{
					sql.append(", ");
				}
			}
			sql.append(" from ");
			sql.append(classname+" where ");
			sql.append( indentityAttribute +"=?1 ");
			/* Construct the search native sql */
			
			String sqlString = sql.toString();
			
			
			Query q = em.createNativeQuery(sqlString);
			q.setParameter(1, indentityValue);
			List result = q.getResultList();
	
			q = null;
			sqlString = null;
			sql = null;
			if(result.size()>0)
			{
				return result.get(0);
			}
			else
			{
				return null;
			}
		
	}
	
	public <T> T findModelWithClear(Class<T> c, Object identityField) {
		T o = em.find(c, identityField);
		em.clear();
		return o;
	}
	
	public <T> T findModel(Class <T> c, Object identityField)
	{
		return em.find(c, identityField);
	}
	
	public <T> T  findModel(Class <T> c, String indentityValueField, Object identityField)
	{	
		
		String sql = "from " + c.getSimpleName()+" where " + indentityValueField +" = ?";
		Query q = em.createQuery(sql);
		q.setParameter(1, identityField);
		List l = q.getResultList();
		int size = l.size();
		if(size>0) 
		{
			return (T)l.get(0);
		}
		
		return null;
	
	}
	
	public List findQuanlifiedEntities(Class clazz, String indentityValueField[], Object identityField[])
	{
		String condition = "";
		for(int i=0;i<indentityValueField.length;i++)
		{
			condition+=indentityValueField +" =? and ";
		}
		condition = " 1=1";
		
		String sql = "from " + clazz.getSimpleName()+" where " + condition;
		Query q = em.createQuery(sql);
		for(int i=0;i<indentityValueField.length;i++)
		{
			q.setParameter(i+1, identityField[i]);
		}
		return q.getResultList();
		
	}
	
	public void addModel(Object o)
	{
		em.persist(o);
	}
	
	public void addModels(List list)
	{
		for(Object o: list)
		{
			em.persist(o);
			em.clear();
		}
	}
	
	public void updateModel(Object o)
	{
		em.merge(o);
	}
	
	public void deleteModel(Object o)
	{
		em.remove(o);
	}
	
	public void deleteModel(Class clazz , int id)
	{
		em.remove(em.find(clazz, id));
	}
	
	
	public void addModelAndClear(Object o)
	{
		em.persist(o);
		em.flush();
		em.clear();
	}
	
	public void updateModelAndClear(Object o)
	{
		em.merge(o);
		em.flush();
		em.clear();
	}
	
	public void deleteModelAndClear(Object o)
	{
		em.remove(o);
		em.flush();
		em.clear();
	}

	
	public byte[] getMemberPic(int id) 
	{
		if(id<1)
			return null;
	
		String sql="select image From Member ms where ms.id=?1";
		Query q=em.createQuery(sql);
		q.setParameter(1, id);
		
		byte[] o=(byte[])q.getSingleResult();
		
		q = null;
		sql = null;
		return o;
	}
	
	public byte[] getCompanyLOGO(int id) 
	{
		String sql = null;
		Query q =null;
		List x =null;
	
		sql ="select logo from Company where id="+id;
		q = em.createQuery(sql);
		x = q.getResultList();
		if(x.size()>0)
		{
			return (byte[])x.get(0);
		}
		else
		{
			return null;
		}
	}
	
	


	
	public List listModel(String classname, String orderBy)
	{
		
		String sql = "from "+classname +" order by " + orderBy;
		return em.createQuery(sql).getResultList();
		
		
	}
	
	
	public List listModel(String classname, String orderBy, int [] ids)
	{
		
		String str= "(";
		for (int i = 0; i < ids.length; i++) {
			str+=ids[i]+",";
		}
		 str+= "0)";
		String sql = "from "+classname +" where id in "+str+" order by " + orderBy ;
		Query q  =  em.createQuery(sql);
		return q.getResultList();
		
	}

	public Object findEntityAttributes(Class clazz, String indentityAttribute, Object indentityValue, String[] attributeNames) {
		if  (attributeNames == null)
		{
			return null;	
		}
		
		StringBuffer sql = new StringBuffer("select ");
		
		int size = attributeNames.length;
		for(int i=0; i<size ; i++)
		{
			sql.append( attributeNames[i]);
			if(i != (size-1))
			{
				sql.append(", ");
			}
		}
		sql.append(" from ");
		sql.append(clazz.getSimpleName() + " where ");
		sql.append( indentityAttribute +"=?1 ");
		/* Construct the search native sql */
		
		String sqlString = sql.toString();
		Query q = em.createQuery(sqlString);
		q.setParameter(1, indentityValue);
		List result = q.getResultList();

		q = null;
		sqlString = null;
		sql = null;
		if(result.size()>0)
		{
			return result.get(0);
		}
		else
		{
			return null;
		}
	}

	public void updateEntityAttributes(Class clazz, String indentityAttribute, Object indentityValue, String[] attributeNames, Object[] attributeValues) {
		if(attributeNames.length!= attributeValues.length)
		{
			return;
		}
		
		
		StringBuffer sql = new StringBuffer("update "+clazz.getSimpleName()+" set ");
		int size = attributeNames.length;
		for(int i=0; i<size ; i++)
		{
			if(attributeNames[i].equals("password"))
				sql.append( attributeNames[i] + "=md5(?" + (i+1) + ")");
			else
				sql.append( attributeNames[i] + "=?" + (i+1));
			
			if(i != (size-1))
			{
				sql.append(", ");
			}
		}
		sql.append(" where "+indentityAttribute+ "=?"+(size+1));
		/* construct the update sql */
		
		String sqlString = sql.toString();
		Query q=em.createQuery(sqlString);
		for(int i=0; i<size ; i++)
		{
			q.setParameter((i+1), attributeValues[i]);
		}
		q.setParameter((size+1), indentityValue);
		q.executeUpdate();
		q = null;
		sql = null;
	}

	@Override
	public void clear() {
		em.clear();
		
	}

	
}
