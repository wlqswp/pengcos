package org.peng.cos.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.peng.cos.scopevo.ConstantBean;

@Named
@ApplicationScoped
public class FileUtil 
{
	@Inject
	private ConstantBean cb;
	
	public void deletePicFile(String filename)
	{
		File f = new File (cb.picDirectory+File.separator+filename);
		f.delete();
		
		f = new File (cb.picDirectory+File.separator+"ori_" + filename);
		f.delete();
	}
	
	public  void savePicFile(byte[] content, String filename)
	{
		BufferedOutputStream buffer = null;
		try
		{
			buffer = new BufferedOutputStream(new FileOutputStream(cb.picDirectory+File.separator+filename));
			buffer.write(content);
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			if(buffer!=null)
				try {
					buffer.close();
				} catch (IOException e) {
				}
		}
		
	}
}
