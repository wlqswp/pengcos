package org.peng.cos.action;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.peng.cos.manager.AdminManager;
import org.peng.cos.manager.DaoManager;
import org.peng.cos.model.Charactor;
import org.peng.cos.model.CosMessage;
import org.peng.cos.model.CosMessage.MSG_TYPE;
import org.peng.cos.scopevo.ConstantBean;
import org.peng.cos.util.FileUtil;
import org.peng.cos.util.MD5Code;
import org.peng.cos.util.image.ImageHandler;
import org.peng.cos.util.image.ImageSizer;
import org.primefaces.event.FileUploadEvent;
import org.richfaces.event.DropEvent;

@ManagedBean(name="pic")
@ViewScoped
public class PictureAction extends BaseAction
{
	@Inject
	private ConstantBean costant;
	
	@Inject
	private ImageHandler imageHandler;
	
	@Inject
	private FileUtil fileUtil;
	
	@EJB
	private AdminManager adminManager;
	
	@EJB
	private DaoManager daoManager;
	
	private List<CosMessage> currentMsgs = new LinkedList();
	
	private int index; 
	private int type; // current list type: 0 default 1 real list 2 cartoon list
	private int currentCharactorId;


	private String realFilter;
	private String cartoonFilter;

	public void setDelete(int param)
	{
		System.out.println("delete");
		try
		{
			CosMessage i = currentMsgs.get(index);
			if(i!=null)
			{
		
				adminManager.deleteCosMessage(i.getId());
				fileUtil.deletePicFile(i.getFileName());
			}
			
			if(index ==0 || index == (this.getSize() - 1) )
			{
				currentMsgs.remove(0);
			}
			else
			{
				List list1 = currentMsgs.subList(0, index);
				List list2 = currentMsgs.subList(index+1, this.getSize());
				List arrayList=  new ArrayList();
				arrayList.addAll(list2);
				arrayList.addAll(list1);
				currentMsgs = arrayList;
				index = 0;
			}
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setNoRealMsgs()
	{
		setUpCurrentList(adminManager.listNoRealPicMessages(), 0);
	}
	
	public void setNoCartoonMsgs()
	{
		setUpCurrentList(adminManager.listNoCartoonPicMessages(), 0);
	}
	


	public void setCurrentListForReal(int cid)
	{
		setUpCurrentList(adminManager.listRealCharactorRelatedList(cid, MSG_TYPE.PIC), 1);
		currentCharactorId = cid;
	}
	
	public void setCurrentListForCartoon(int cid)
	{
		setUpCurrentList(adminManager.listCartoonCharactorRelatedList(cid, MSG_TYPE.PIC), 2);
		currentCharactorId = cid;
	}
	
	public CosMessage getCurrentMsg()
	{
		try
		{
			return currentMsgs.get(index);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public List<Charactor> getAllCartoonCharactors()
	{
		return  ( List<Charactor>) getVariValue("getAllCartoonCharactors");
	}
	
	public List<Charactor> getAllRealCharactors()
	{
		return (List<Charactor>) getVariValue("getAllRealCharactors");
	}
	
	public void setIndex(int index) {
		if(index==1)
		{
			this.index+=1;
			this.index= this.index % this.getSize();
		}
		if(index == -1)
		{
			this.index -=1;
			if(this.index <0)
			{
				this.index += this.getSize();
			}
		}
	}

	public int getSize()
	{
		return currentMsgs.size();
	}
	
	public void drop(DropEvent e)
	{
		Integer idstr= (Integer)e.getDragValue();
		
		if(idstr!=null)
		{
			Charactor c = daoManager.findModel(Charactor.class, idstr);
			CosMessage cm = currentMsgs.get(this.index);
			if(c.getCtype()== Charactor.CHARACTOR_TYPE.TYPE_CATOON)
			{
				cm.setCatoonCharactor(c);
			} 
			else if(c.getCtype()== Charactor.CHARACTOR_TYPE.TYPE_REAL)
			{
				cm.setRealCharactor(c);
			}
			daoManager.updateModel(cm);
		}
	}
	public void handleFileUpload(FileUploadEvent event) 
	{  
		index = 0;
		BufferedOutputStream buffer = null;
		try
		{
			
			String fx = new MD5Code().getMD5ofStr(event.getFile().getFileName()) + new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
			String filename = fx+".jpg";
			byte[] content = imageHandler.getPreferredImage( event.getFile().getContents());
			fileUtil.savePicFile(content, filename);
			fileUtil.savePicFile(event.getFile().getContents(), "ori_" +filename);
			addFaceMessage("Successul", "文件保存成功");
			
			int[] size = ImageSizer.getImageWidthAndHeight(content);
			
			int realcid = 0;
			int cartooncid = 0;
			if(type==1)
			{
				realcid = this.currentCharactorId;
			}
			if(type==2)
			{
				cartooncid = this.currentCharactorId;
			}
			CosMessage cm = adminManager.addPicCosMessage(filename,size[0],size[1],realcid,cartooncid);
			currentMsgs.add(0, cm);
			
			
			/** if current context is not real or cartoon, then we always set the index to 0 */ 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			addFaceMessage("Error", "Fail to save the picture");
		}
		finally
		{
			if(buffer!=null)
				try {
					buffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}	
		}
		
    }  
	
	private void setUpCurrentList(List<CosMessage> msgs, int type)
	{
		this.currentMsgs = msgs;
		this.type =type;
		this.index = 0;
	}
	
	
	
	public Object getVariValue(String key)
	{
		Object o = this.getVariableByKey(key);
		
		if(o == null)
		{
			if(key.equals("getAllCartoonCharactors"))
			{
				o = adminManager.listAllCartoonCharactor(0, Integer.MAX_VALUE).getDatas();
			}
			if(key.equals("getAllRealCharactors"))
			{
				o = adminManager.listAllRealCharactor(0, Integer.MAX_VALUE).getDatas();
			}
			this.setVariableByKey(key, o);
		}

		return o;
	}
	
	
	
	
	public List<CosMessage> getCurrentMsgs() {
		return currentMsgs;
	}



	public void setCurrentMsgs(List<CosMessage> currentMsgs) {
		this.currentMsgs = currentMsgs;
	}



	public ConstantBean getCostant() {
		return costant;
	}

	public void setCostant(ConstantBean costant) {
		this.costant = costant;
	}


	public int getIndex() {
		return index;
	}
	public String getRealFilter() {
		return realFilter;
	}

	public void setRealFilter(String realFilter) {
		this.realFilter = realFilter;
	}

	public String getCartoonFilter() {
		return cartoonFilter;
	}

	public void setCartoonFilter(String cartoonFilter) {
		this.cartoonFilter = cartoonFilter;
	}
	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
	public int getCurrentCharactorId() {
		return currentCharactorId;
	}

	public void setCurrentCharactorId(int currentCharactorId) {
		this.currentCharactorId = currentCharactorId;
	}

}
