package org.peng.cos.scopevo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

@Named("uploadBean")
@SessionScoped
public class FileUploadBean implements Serializable {
	private byte[] fileUpload;
	
	public FileUploadBean()
	{
	//	System.out.println("create FileUploadBean");
	}
	private String test;
	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public void paint(OutputStream stream, Object object) throws IOException {

		if(fileUpload!=null)
		{
		   stream.write(fileUpload);
		}
	    stream.close();
    }
 
    public void uploadLogo(FileUploadEvent event) throws Exception {
    	
    	System.out.println("update");
        UploadedFile item = event.getUploadedFile();
        fileUpload = item.getData();
        FileOutputStream f = new FileOutputStream("c://1.jpg");
        f.write(fileUpload);
        f.close();
    }

    public void clearUpload()
    {
    	fileUpload = null;
    }
    
	public byte[] getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(byte[] fileUpload) {
		this.fileUpload = fileUpload;
	}
    
 
  
}