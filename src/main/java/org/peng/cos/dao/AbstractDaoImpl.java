package org.peng.cos.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.peng.cos.model.PagerModel;


public class AbstractDaoImpl <T> implements AbstractDao<T>{

	@PersistenceContext
	protected EntityManager em;

	protected Class<? extends T> clazz;

	protected AbstractDaoImpl(Class<? extends T> clazz) {
		this.clazz = clazz;
	}

	public void flush() {
		em.flush();
	}

	public void clear() {
		try {
			em.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addModel(Object object) {
		em.persist(object);
	}

	public void deleteModel(Object object) {
		em.remove(object);
	}

	public void deleteModel(int id) {
		em.remove(em.find(clazz, id));
	}

	public void updateModel(Object object) {
		em.merge(object);
	}

	public T findModel(int id) {
		T t = em.find(clazz, id);
		if (t == null) {
			return null;
		}
		
		return t;
	}

	public <E> E findModel(Class<E> clazz, int id) {
		return em.find(clazz, id);
	}

	public T findModel(String identityAttribute, Object identityValue) {
		String sql = "from " + this.clazz.getSimpleName() + " where " + identityAttribute + " = ?";
		Query q = em.createQuery(sql);
		q.setParameter(1, identityValue);
		List<T> l = executeQuery(q);
		if (l.isEmpty()) {
			return null;
		}
		
		return l.get(0);
	}

	public Object findSingleAttribute(String attributeField, int id) {
		Object[] objects = this.findEntityAttributes("id", id, new String[] { attributeField });
		if (objects == null) {
			return null;
		} 
			
		return objects[0];
	}

	public Object[] findEntityAttributes(String indentityAttribute, Object indentityValue, String[] attributeNames) {
		return findEntityAttributes(this.clazz, indentityAttribute, indentityValue, attributeNames);
	}
	
	public Object[] findEntityAttributes(Class<?> clazz, String indentityAttribute, Object indentityValue, String[] attributeNames) {
		if (attributeNames == null) {
			return null;
		}

		StringBuffer sql = new StringBuffer("select ");

		int size = attributeNames.length;
		for (int i = 0; i < size; i++) {
			sql.append(attributeNames[i]);
			if (i != (size - 1)) {
				sql.append(", ");
			}
		}
		sql.append(" from ");
		sql.append(clazz.getSimpleName() + " where ");
		sql.append(indentityAttribute + "=?1 ");
		/* Construct the search native sql */

		String sqlString = sql.toString();

		Query q = em.createQuery(sqlString);
		q.setParameter(1, indentityValue);
		List<Object> result = executeQuery(q);

		if (result.size() > 0) {
			Object o = result.get(0);
			if (attributeNames.length == 1) {
				Object[] objs = new Object[1];
				objs[0] = o;
				return objs;
			} else {
				return (Object[]) o;
			}
		} else {
			return null;
		}
	}

	public Class<? extends T> getPersistenceClass() {
		return clazz;
	}

	public boolean isExist(String indentityAttribute, Object indentityValue) {
		String sql = " from " + this.clazz.getSimpleName() + " where " + indentityAttribute + " = " + indentityValue;
		List<T> l = executeSqlQuery(sql);
		
		if (l.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public List<T> findEntities(String[] conditions, Object[] values, String orderby) {
		if (((conditions != null) && (values != null)) && (conditions.length != values.length)) {
			throw new IllegalArgumentException("Condition and its values length is different");
		}
		StringBuffer sql = new StringBuffer("from " + this.clazz.getSimpleName() + " where ");
		int size = 0;
		if (conditions != null)
			size = conditions.length;
		for (int i = 0; i < size; i++) {
			sql.append(conditions[i] + "=?" + (i + 1));

			if (i != (size - 1)) {
				sql.append(" and ");
			}
		}
		if ((conditions == null) || (values == null) || (conditions.length == 0) || (values.length == 0)) {
			sql.append(" 1=1 ");
		}

		if ((orderby != null) && (orderby.length() != 0))
			sql.append(" order by " + orderby);
		/* construct the sql */

		String sqlString = sql.toString();
		Query q = em.createQuery(sqlString);
		for (int i = 0; i < size; i++) {
			q.setParameter((i + 1), values[i]);
		}

		List<T> x = executeQuery(q);
		em.clear();
		return x;
	}

	public void updateEntityAttributes(String indentityAttribute, Object indentityValue, String[] attributeNames,
			Object[] attributeValues) {

		if (attributeNames.length != attributeValues.length) {
			return;
		}

		StringBuffer sql = new StringBuffer("update " + this.clazz.getSimpleName() + " set ");
		int size = attributeNames.length;
		for (int i = 0; i < size; i++) {
			sql.append(attributeNames[i] + "=?" + (i + 1));

			if (i != (size - 1)) {
				sql.append(", ");
			}
		}
		sql.append(" where " + indentityAttribute + "=?" + (size + 1));
		/* construct the update sql */

		String sqlString = sql.toString();
		Query q = em.createQuery(sqlString);
		for (int i = 0; i < size; i++) {
			q.setParameter((i + 1), attributeValues[i]);
		}
		q.setParameter((size + 1), indentityValue);
		q.executeUpdate();
		q = null;
		sql = null;
	}

	protected String constructInSql(List<Integer> list) {
		if (list == null)
			return null;

		String temp = "(";
		for (int element : list) {
			temp += element + ",";
		}
		temp += " 0 )";

		return temp;
	}

	protected String constructInSql(int ids[]) {
		if (ids == null)
			return null;
		String temp = "(";
		for (int i = 0; i < ids.length; i++) {

			temp += ids[i] + ",";

		}
		temp += " 0 )";
		return temp;
	}

	protected String constructInSql(String ids[]) {
		if (ids == null)
			return null;
		String temp = "(";
		for (int i = 0; i < ids.length; i++) {
			if (ids[i].contains("'"))
				temp += ids[i] + ",";
			else
				temp += "'" + ids[i] + "',";

		}
		temp += " '' )";
		return temp;
	}

	protected <E> List<E> executeSqlQuery(String sql) {
		return executeQuery(em.createQuery(sql));
	}

	@SuppressWarnings("unchecked")
	protected <E> List<E> executeQuery(Query q) {
		return q.getResultList();
	}
	
	public PagerModel searchPaginated(String hql,int offset,int pagesize)throws Exception{
		return searchPaginated(hql,null,offset,pagesize);
	}
	 
	public PagerModel searchPaginated(String hql,Object obj,int offset,int pagesize)throws Exception{
		return searchPaginated(hql,new Object[]{obj},offset,pagesize);
	}
	
	/**
	 * 
	 * @param hql: original hql sentence.
	 * @param params: all the parameters which should be set into the hql sentences
	 * @param offset: start index of the result set
	 * @param pagesize: normally 10 records for a page 
	 * @return new Model class for carrying the data list and number of records
	 * @throws Exception
	 */
	public PagerModel searchPaginated(String hql, Object [] params, int offset, int pagesize) throws Exception
	{
		PagerModel page=new PagerModel();		/* Define a new Model class for carrying the data list and number of records */
		String countHql=this.getCountHql(hql);
	
		
		Query q=em.createQuery(countHql);
		if((params!=null)&&(params.length>0))
		{
			if(params != null && params.length > 0){
				for(int i=0; i<params.length; i++){
					if(params[i] instanceof Date)
					q.setParameter(i+1, (Date) params[i],TemporalType.DATE);
					else
					q.setParameter(i+1, params[i]);
				}
			}
		}
		/* set parameters for the countHql */
		
		int total=0;	/* it is used to carry the number of records */
		try
		{
			if(hql.indexOf("group by")==-1)
				total=((Long)q.getSingleResult()).intValue();
			else
			{
				//countHql = "select count(*) from (" + countHql + ") v";
				//total=((Long)q.getSingleResult()).intValue();
				total = q.getResultList().size();
			}
		}
		catch(Exception ex)	/* The countHql contains "group by" key words inside */
		{
			throw new Exception(ex);		/* otherwise throw the exception */
		}
		
		
		q=em.createQuery(hql);
		if((params!=null)&&(params.length>0))
		{
			if(params != null && params.length > 0){
				for(int i=0; i<params.length; i++)
				{
					if(params[i] instanceof Date)
					{
						q.setParameter(i+1, (Date) params[i],TemporalType.DATE);
					}
					else
					{
						q.setParameter(i+1, params[i]);
					}
				}
			}
		}
		q=q.setFirstResult(offset).setMaxResults(pagesize);
		List<Object> all=executeQuery(q);
		page.setTotal(total);
		page.setDatas(all);
		/* Get the requested data list from the database and save the number of records and data list into the PagerModel object */
		
		q=null;		/* release the Query object to accelerate the GC */
		return page;
	}
	
	/**
	 * 
	 * @param hql: any hql sentenses
	 * @return A hql string which means that obtaining the number of records which could get by passed "hql" input parameter
	 * @throws Exception
	 */
	private String getCountHql(String hql)
	{
		String hqlLowCase=hql.toLowerCase();
		
		if(!hqlLowCase.contains("distinct"))
		{
			int index=hqlLowCase.indexOf("from");
			if(index!=-1)
			{
				String sql = "select count(*)" + hql.substring(index);
				int i= sql.indexOf("order");
				if(i!=-1)
				{
					sql = sql.substring(0,i);
				}
				return sql;
			}
		    throw new RuntimeException("getCountHql parameter does not has a 'from' inside");
		}
		else
		{
			int index=hqlLowCase.toLowerCase().indexOf("from");
			int selectIndex = hqlLowCase.indexOf("t")+1;
			String selectContent = hqlLowCase.substring(selectIndex, index);
			
			if(index!=-1)
			{
				String sql = "select count("+ selectContent +") " + hql.substring(index);
				int i= sql.indexOf("order");
				if(i!=-1)
				{
					sql = sql.substring(0,i);
				}
				return sql;
			}
		    throw new RuntimeException("getCountHql parameter does not has a 'from' inside");
		}
	}
	
	public List listModel(String classname, String orderBy)
	{
		
		String sql = "from "+classname +" order by " + orderBy;
		return em.createQuery(sql).getResultList();
	}
	
}
