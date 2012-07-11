package org.peng.cos.manager;

import java.util.List;

import javax.ejb.Local;
@Local
public interface DaoManager 
{
	public Object  findEntityAttributes(Class c, String indentityAttribute, Object indentityValue, String [] attributeNames);
	
	public void updateEntityAttributes(Class c, String indentityAttribute, Object indentityValue, String [] attributeNames,Object [] attributeValues);
	
	public <T> T findModel(Class <T> c, String indentityValueField, Object identityField);

	public List findQuanlifiedEntities(Class c, String indentityValueField[], Object identityField[]);
	
	public <T> T findModel(Class <T> c, Object identityField);
	
	public <T> T findModelWithClear(Class <T> c, Object identityField);
	

	public void addModel(Object o);
	public void addModels(List list);
	public void updateModel(Object o);
	public void deleteModel(Object o);
	public void deleteModel(Class clazz , int id);
	public void addModelAndClear(Object o);
	public void updateModelAndClear(Object o);
	public void deleteModelAndClear(Object o);
	public List listModel(String classname, String orderBy);
	public List listModel(String classname, String orderBy, int [] ids);
	public void clear();
	
}
