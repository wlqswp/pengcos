package org.peng.cos.action.funcutil;

import java.io.Serializable;

import javax.faces.model.DataModel;

import org.peng.cos.model.Charactor;
import org.peng.cos.model.PagerModel;

public abstract class PagerDataModel extends DataModel implements Serializable {

	private PagerModel pm;
	private int rowIndex;
	private int pageSize ;

	public PagerDataModel(int pageSize) {  
        super();  
        this.pageSize = pageSize;  
    }  
	

	@Override
	public Object getRowData() {
		return getPM().getDatas().get(rowIndex);
	}

	@Override
	public boolean isRowAvailable() {
		
		  int rowIndex = getRowIndex();  
	        if (rowIndex < 0) {  
	            return false;  
	        } else if (rowIndex >= pageSize) {  
	            return false;  
	        } else {  
	            return true;  
	        }  
	}

	@Override
	public void setRowIndex(int rowIndex) {
		if(rowIndex>=0)
		{
			int startRow =  rowIndex/pageSize;
			this.rowIndex = rowIndex - startRow*pageSize;
		}
		else
			this.rowIndex =rowIndex;
		
	//	System.out.println("Passed index:" + rowIndex +" real index:" + this.rowIndex);
	}
	
	@Override
	public int getRowIndex() {
		// TODO Auto-generated method stub
		return rowIndex;
	}

	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.getPM().getTotal();
	}

	@Override
	public Object getWrappedData() {
		// TODO Auto-generated method stub
		return getPM();
	}

	@Override
	public void setWrappedData(Object o) 
	{
	   this.pm = (PagerModel) o;  
	}
	
	private PagerModel getPM() {  
        if (pm != null) {  
            return pm;  
        }  
        pm= getPagerModel();
        return pm;  
    }  
	
	
	public abstract PagerModel getPagerModel();
	
	
  
 
}  