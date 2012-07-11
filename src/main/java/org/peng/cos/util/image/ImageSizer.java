package org.peng.cos.util.image;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class ImageSizer {    
	private static Logger logger = Logger.getLogger(ImageSizer.class.getName());
	
    public static final MediaTracker tracker = new MediaTracker(new Component() {    
        private static final long serialVersionUID = 1234162663955668507L;}     
    );    
    /**   
     * @param originalFile 原图像   
     * @param resizedFile 压缩后的图像   
     * @param width 图像宽   
     * @param format 图片格式 jpg, png, gif(非动画)   
     * @throws IOException   
     */   
    public static int[] getImageWidthAndHeight(byte[] image)
    {
    	ByteArrayInputStream bi = null;
    	try
    	{
    		bi = new ByteArrayInputStream(image);
			BufferedImage inputimg = ImageIO.read(bi);
	       // Image inputImage = Toolkit.getDefaultToolkit().createImage( image );    
			waitForImage( inputimg );    
	        if (inputimg == null) {
	        	return null;
	        }
	        
	        int imageWidth = inputimg.getWidth( null );    
	        if ( imageWidth < 1 )     
	           throw new IllegalArgumentException( "image width " + imageWidth + " is out of range" );    
	        int imageHeight = inputimg.getHeight( null );    
	        if ( imageHeight < 1 )     
	           throw new IllegalArgumentException( "image height " + imageHeight + " is out of range" );   
	        
	        return new int[]{imageWidth,imageHeight};
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return null;
    	}
    	finally {
    		try {
    			if (bi != null) {
    				bi.close();
    			}
			} catch (IOException e) {}
    	}
    }
    
     /**
     * Convenience method that returns a scaled instance of the
     * provided {@code BufferedImage}.
     *
     * @param img the original image to be scaled
     * @param targetWidth the desired width of the scaled instance,
     *    in pixels
     * @param targetHeight the desired height of the scaled instance,
     *    in pixels
     * @param hint one of the rendering hints that corresponds to
     *    {@code RenderingHints.KEY_INTERPOLATION} (e.g.
     *    {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
     * @param higherQuality if true, this method will use a multi-step
     *    scaling technique that provides higher quality than the usual
     *    one-step technique (only useful in downscaling cases, where
     *    {@code targetWidth} or {@code targetHeight} is
     *    smaller than the original dimensions, and generally only when
     *    the {@code BILINEAR} hint is specified)
     * @return a scaled version of the original {@code BufferedImage}
     */
    /*private  BufferedImage getScaledInstance(BufferedImage img,
                                           int targetWidth,
                                           int targetHeight,
                                           Object hint,
                                           boolean higherQuality)
    {
        int type = (img.getTransparency() == Transparency.OPAQUE) ?
            BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage)img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }
        
        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }


            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }


            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();


            ret = tmp;
        } while (w != targetWidth || h != targetHeight);


        return ret;
    }*/
    
    /**
     * Might be a bit slower than {@link #resize(byte[], int, String)} if you do several resizes in a row.
     */
    public static byte[] resize(byte[] in, int maxDimension) {
    	return resize(in, maxDimension, ImageTools.getImageFormat(in));
    }
    
    public static byte[] resize(byte[] in, int maxDimension, String format) {    
        
    	try
    	{
    		ByteArrayInputStream bi = new ByteArrayInputStream(in);
    		Image inputImage = ImageIO.read(bi);
	       
	        waitForImage( inputImage );    
	        int originalWidth = inputImage.getWidth( null );    
	        if ( originalWidth < 1 )     
	           throw new IllegalArgumentException( "image width " + originalWidth + " is out of range" );    
	        int originalHeight = inputImage.getHeight( null );    
	        if ( originalHeight < 1 )     
	           throw new IllegalArgumentException( "image height " + originalHeight + " is out of range" );   
	        
	        int width = -1;
	        int height = -1;
	        if (originalWidth > originalHeight) {
	        	width = maxDimension;
	        } else {
	        	height = maxDimension;
	        }
	            
	        Image outputImage = inputImage.getScaledInstance( width, height, java.awt.Image.SCALE_SMOOTH);    
	        checkImage( outputImage );         
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        encode(out, outputImage, format);            
	        return out.toByteArray();
    	}
    	catch(Exception e)
    	{
    		logger.error("Unable to resize image", e);
    		return null;
    	}
    }
    
    public static byte[] resizeByWidth(byte[] in, int width) {
    	return resizeByWidth(in, width, ImageTools.getImageFormat(in));
    }
    
    public static byte[] resizeByWidth(byte[] in, int width, String format) {    
      
    	try
    	{
    		ByteArrayInputStream bi = new ByteArrayInputStream(in);
    		Image inputImage = ImageIO.read(bi);
	       
	        waitForImage( inputImage );    
	        int imageWidth = inputImage.getWidth( null );    
	        if ( imageWidth < 1 )     
	           throw new IllegalArgumentException( "image width " + imageWidth + " is out of range" );    
	        int imageHeight = inputImage.getHeight( null );    
	        if ( imageHeight < 1 )     
	           throw new IllegalArgumentException( "image height " + imageHeight + " is out of range" );    
	            
	        // Create output image.    
	        Image outputImage = inputImage.getScaledInstance( width, -1, java.awt.Image.SCALE_SMOOTH);    
	        checkImage( outputImage );         
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        encode(out, outputImage, format);            
	        return out.toByteArray();
    	}
    	catch(Exception e)
    	{
    		logger.error("Unable to resize image", e);
    		return null;
    	}
    }      
    
    public static byte[] resizeByHeight(byte[] in, int height, String format) {    
        
    	try
    	{
    		ByteArrayInputStream bi = new ByteArrayInputStream(in);
    		Image inputImage = ImageIO.read(bi);
	        waitForImage( inputImage );    
	        int imageWidth = inputImage.getWidth( null );    
	        if ( imageWidth < 1 )     
	           throw new IllegalArgumentException( "image width " + imageWidth + " is out of range" );    
	        int imageHeight = inputImage.getHeight( null );    
	        if ( imageHeight < 1 )     
	           throw new IllegalArgumentException( "image height " + imageHeight + " is out of range" );    
	            
	    
	        Image outputImage = inputImage.getScaledInstance( -1, height, java.awt.Image.SCALE_SMOOTH);    
	        checkImage( outputImage );         
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        encode(out, outputImage, format);            
	        return out.toByteArray();
    	}
    	catch(Exception e)
    	{
    		logger.error("Unable to resize image", e);
    		return null;
    	}
    }        
   
    /** Checks the given image for valid width and height. */   
    private static void checkImage( Image image ) {    
       waitForImage( image );    
       int imageWidth = image.getWidth( null );    
       if ( imageWidth < 1 )     
          throw new IllegalArgumentException( "image width " + imageWidth + " is out of range" );    
       int imageHeight = image.getHeight( null );    
       if ( imageHeight < 1 )     
          throw new IllegalArgumentException( "image height " + imageHeight + " is out of range" );    
    }    
   
    /** Waits for given image to load. Use before querying image height/width/colors. */   
    private static void waitForImage( Image image ) {    
       try {    
    	  int imageId = image.hashCode();
          tracker.addImage( image, imageId );    
          tracker.waitForID( imageId );    
          tracker.removeImage(image, imageId);    
       } catch( InterruptedException e ) { 
    	   logger.error("Waiting for image loading interrupted", e); 
       }    
    }     
   
    /** Encodes the given image at the given quality to the output stream. */   
    private static void encode( OutputStream outputStream, Image outputImage, String format )     
       throws java.io.IOException {    
       int outputWidth  = outputImage.getWidth( null );    
       if ( outputWidth < 1 )     
          throw new IllegalArgumentException( "output image width " + outputWidth + " is out of range" );    
       int outputHeight = outputImage.getHeight( null );    
       if ( outputHeight < 1 )     
          throw new IllegalArgumentException( "output image height " + outputHeight + " is out of range" );    
   
       // Get a buffered image from the image.    
       BufferedImage bi = new BufferedImage( outputWidth, outputHeight,    
          BufferedImage.TYPE_INT_RGB );                                                       
       Graphics2D biContext = bi.createGraphics();    
       biContext.drawImage( outputImage, 0, 0, null );    
       ImageIO.write(bi, format, outputStream);    
       outputStream.flush();      
       outputStream.close();    
    }     
        
    /**   
     * 缩放gif图片   
     * @param originalFile 原图片   
     * @param resizedFile 缩放后的图片   
     * @param newWidth 宽度   
     * @param quality 缩放比例 (等比例)   
     * @throws IOException   
     */   
/*    private static void resize(File originalFile, File resizedFile, int newWidth, float quality) throws IOException {    
        if (quality < 0 || quality > 1) {    
            throw new IllegalArgumentException("Quality has to be between 0 and 1");    
        }     
        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());    
        Image i = ii.getImage();    
        Image resizedImage = null;     
        int iWidth = i.getWidth(null);    
        int iHeight = i.getHeight(null);     
        if (iWidth > iHeight) {    
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);    
        } else {    
            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight, newWidth, Image.SCALE_SMOOTH);    
        }     
        // This code ensures that all the pixels in the image are loaded.    
        Image temp = new ImageIcon(resizedImage).getImage();     
        // Create the buffered image.    
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null),    
                                                        BufferedImage.TYPE_INT_RGB);     
        // Copy image to buffered image.    
        Graphics g = bufferedImage.createGraphics();     
        // Clear background and paint the image.    
        g.setColor(Color.white);    
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));    
        g.drawImage(temp, 0, 0, null);    
        g.dispose();     
        // Soften.    
        float softenFactor = 0.05f;    
        float[] softenArray = {0, softenFactor, 0, softenFactor, 1-(softenFactor*4), softenFactor, 0, softenFactor, 0};    
        Kernel kernel = new Kernel(3, 3, softenArray);    
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);    
        bufferedImage = cOp.filter(bufferedImage, null);     
        // Write the jpeg to a file.    
        FileOutputStream out = new FileOutputStream(resizedFile);            
        // Encodes image as a JPEG data stream    
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);     
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);     
        param.setQuality(quality, true);     
        encoder.setJPEGEncodeParam(param);    
        encoder.encode(bufferedImage);    
    }   */
    
}  