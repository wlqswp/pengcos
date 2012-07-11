package org.peng.cos.dao;

import java.util.List;

import org.peng.cos.model.PagerModel;

public interface AbstractDao<T> {
	public <T> T findModel(Class<T> clazz, int id);

	public T findModel(int id);

	public T findModel(String identitfyAttribute, Object identityValue);

	public Object[] findEntityAttributes(String indentityAttribute, Object indentityValue, String[] attributeNames);
	
	public Object[] findEntityAttributes(Class<?> clazz, String indentityAttribute, Object indentityValue, String[] attributeNames);

	public void updateEntityAttributes(String indentityAttribute, Object indentityValue, String[] attributeNames,
			Object[] values);

	public List<T> findEntities(String[] conditions, Object[] values, String orderby);

	public void addModel(Object object);

	public void updateModel(Object object);

	public void deleteModel(Object object);

	public void deleteModel(int id);

	public boolean isExist(String indentityAttribute, Object indentityValue);

	public Object findSingleAttribute(String indentityAttribute, int id);

	public Class<?> getPersistenceClass();

	public void flush();

	public void clear();
	
	public PagerModel searchPaginated(String hql,int offset,int pagesize)throws Exception;
	
	 
	public PagerModel searchPaginated(String hql,Object obj,int offset,int pagesize)throws Exception;
	
	/**
	 * 
	 * @param hql: original hql sentence.
	 * @param params: all the parameters which should be set into the hql sentences
	 * @param offset: start index of the result set
	 * @param pagesize: normally 10 records for a page 
	 * @return new Model class for carrying the data list and number of records
	 * @throws Exception
	 */
	public PagerModel searchPaginated(String hql, Object [] params, int offset, int pagesize) throws Exception;
	
	public List listModel(String classname, String orderBy);
	
}
