package org.peng.cos.util.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;

import org.peng.cos.scopevo.ConstantBean;

@Named("imageHandler")
@ApplicationScoped
public class ImageHandler 
{
	@Inject
	private ConstantBean cb;
	
	
	public byte[] getPreferredImage (byte[] originImage)
	{
		ByteArrayOutputStream out = null;
		try
		{
			int[] originImageDimension=  ImageSizer.getImageWidthAndHeight(originImage);
			if(originImageDimension==null)
				return null;
			
			int owidth = originImageDimension[0];
			int oheight = originImageDimension[1];
			
			int preferedWidth = owidth;
			int preferedHeight = oheight;
			
			double xtimes = 1;
			double ytimes = 1;
			if(owidth> cb.getPreferredWidth())
			{
				xtimes =  (owidth+0.0) / cb.getPreferredWidth();
			}
			if(oheight> cb.getPreferedHeight())
			{
				ytimes =  (oheight+0.0) / cb.getPreferedHeight();
			}
			
			if(xtimes>ytimes && xtimes>1)
			{
				preferedWidth =  cb.getPreferredWidth();
				preferedHeight = (int)(oheight / xtimes );
			}
			
			if(ytimes>xtimes && ytimes>1)
			{
				preferedHeight =  cb.getPreferedHeight();
				preferedWidth = (int)(owidth / ytimes );
			}

			int newx = (cb.getPreferredWidth()- preferedWidth)/2;
			int newy = (cb.getPreferedHeight()- preferedHeight)/2;
			
			BufferedImage src = ImageIO.read(new ByteArrayInputStream(originImage));
			
			BufferedImage result =  makeTransImage(src,newx,newy, preferedWidth, preferedHeight);
		   
			 out = new ByteArrayOutputStream();
			ImageIO.write(result, "png", out);
			return out.toByteArray();
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			if(out!=null)
				try {
					out.close();
				} catch (IOException e) {
				}
		}
		
		
	}
	
    private BufferedImage makeTransImage(BufferedImage src, int x, int y, int width, int height)
    {
	    if (src == null) {
	      System.out.println("makeTransImage: input image is null");
	      return null;
	    }
	
	    BufferedImage dest = new BufferedImage( cb.getPreferredWidth(), cb.getPreferedHeight(),
	                                  BufferedImage.TYPE_INT_ARGB);  // alpha channel
	    Graphics2D g2d = dest.createGraphics();
	
	    // copy image
	    g2d.drawImage(src, x, y,width,  height, null);
	    g2d.dispose();
	
	    return dest; 
    } // end of makeTransImage()

}
