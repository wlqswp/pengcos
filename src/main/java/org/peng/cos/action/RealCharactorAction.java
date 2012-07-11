package org.peng.cos.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.peng.cos.action.funcutil.PagerDataModel;
import org.peng.cos.manager.AdminManager;
import org.peng.cos.manager.DaoManager;
import org.peng.cos.model.Charactor;
import org.peng.cos.model.Charactor.CHARACTOR_TYPE;
import org.peng.cos.model.PagerModel;
import org.peng.cos.scopevo.FileUploadBean;
import org.peng.cos.util.ThreadUtil;
import org.peng.cos.util.image.ImageTools;
import org.richfaces.component.UICommandButton;

@Named("real")
@RequestScoped
public class RealCharactorAction extends BaseAction implements Serializable
{
	@Inject
	private Charactor inputCharactor;
	
	@Inject
	private FileUploadBean uploadBean;
	
	@EJB
	private DaoManager daoManager;
	
	@EJB
	private AdminManager adminManager;
	
	private UIComponent deleteButton = new UICommandButton();
	
	public RealCharactorAction()
	{
		deleteButton.setRendered(false);
	}
	
	public void setUpdate(int id)
	{
		inputCharactor = daoManager.findModel(Charactor.class, id);
		uploadBean.setFileUpload(inputCharactor.getLogo());
		this.msgInfo = null;
		deleteButton.setRendered(true);
	}
	
	public void deleteCoser()
	{
		boolean b = adminManager.deleteCoser(inputCharactor.getId());
		this.msgInfo = null;
		deleteButton.setRendered(false);
		inputCharactor = new Charactor();
	}

	
	public DataModel getPageRealCharactors()
	{
		PagerDataModel pdm=
		new PagerDataModel(constant.getPageSize())
		{
			@Override
			public PagerModel getPagerModel() {
				PagerModel list=  adminManager.listAllRealCharactor(ThreadUtil.getPageOffset(), constant.getPageSize());
				return list;
			}
		};
		return pdm;
	}
	
	public void showLogo(OutputStream stream, Object object) throws IOException {

		if(object instanceof byte[])
		{
			 stream.write((byte[])object);
		}
	    stream.close();
    }
	
	public void addOrUpdateCoser()
	{
		try
		{
			if(inputCharactor.getId()==0)
			{
				this.msgInfo =  getText("add_new_coser");
				inputCharactor.setCtype(CHARACTOR_TYPE.TYPE_REAL);
				
				byte[] imgContent= uploadBean.getFileUpload();
				if(imgContent!=null && ImageTools.isImage(imgContent))
				{
					imgContent = ImageTools.compressAndChange(imgContent, 300, 300);
					inputCharactor.setLogo(imgContent);
				}
				inputCharactor.setLastUpdateDate(Calendar.getInstance());
				adminManager.addNewCoser(inputCharactor);
			
			}
			else
			{
				this.msgInfo =  getText("update_coser");
				inputCharactor.setCtype(CHARACTOR_TYPE.TYPE_REAL);
				byte[] imgContent= uploadBean.getFileUpload();
				if(imgContent!=null && ImageTools.isImage(imgContent))
				{
					imgContent = ImageTools.compressAndChange(imgContent, 300, 300);
					inputCharactor.setLogo(imgContent);
				}
				inputCharactor.setLastUpdateDate(Calendar.getInstance());
				adminManager.updateCoser(inputCharactor);
			}
			uploadBean.clearUpload();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.msgInfo =  getText("error_add_new_coser");
		}
	}


	public FileUploadBean getUploadBean() {
		return uploadBean;
	}

	public void setUploadBean(FileUploadBean uploadBean) {
		this.uploadBean = uploadBean;
	}

	public Charactor getInputCharactor() {
		return inputCharactor;
	}

	public void setInputCharactor(Charactor inputCharactor) {
		this.inputCharactor = inputCharactor;
	}

	public UIComponent getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(UIComponent deleteButton) {
		this.deleteButton = deleteButton;
	}

	
	
	
}
