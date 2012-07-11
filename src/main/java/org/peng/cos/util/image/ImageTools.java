package org.peng.cos.util.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * this class suppose to be the image tool class which supply util
 * fucntionalities used for image,
 * 
 * @author wlqswp
 * 
 */
public class ImageTools {
	private static final Logger logger = Logger.getLogger(ImageTools.class);
	/**
	 * cnvert the input image to png format and compress them to 33,40 size
	 * 
	 * @param input:
	 *            the byte[] of input image
	 * @return: the byte[] of output png image byte array
	 * @throws Exception
	 */
	public static byte[] compressAndChangeToPNG(byte[] input) throws Exception {
		ByteArrayOutputStream out = null;
		try {
			BufferedImage target = new BufferedImage(33, 40,
					BufferedImage.TYPE_4BYTE_ABGR_PRE);
			Graphics g = target.getGraphics();
			g.drawImage(ImageIO.read(new ByteArrayInputStream(input)), 0, 0,
					33, 40, null);
			out = new ByteArrayOutputStream();
			ImageIO.write(target, "png", out);
		} catch (IOException ex) {
			throw new Exception(
					"can not convert the image file to png format");
		}
		return out.toByteArray();
	}
	
	public static byte[] compressAndChange(byte[] input, int width, int height) throws Exception {
		ByteArrayOutputStream out = null;
		try {
			
			BufferedImage target = new BufferedImage(width, height,
					 BufferedImage.TYPE_INT_ARGB);
			Graphics g = target.getGraphics();
			g.drawImage(ImageIO.read(new ByteArrayInputStream(input)), 0, 0,
					width, height, null);
			out = new ByteArrayOutputStream();
			ImageIO.write(target, "jpg", out);
		} catch (IOException ex) {
			throw new Exception(
					"can not convert the image file to png format");
		}
		return out.toByteArray();
	}


	

	/**
	 * read the specifed file from disk and convert them to memory BufferedImage
	 * 
	 * @param fileName
	 * @return
	 */
	public static BufferedImage readImageFromClasspath(String fileName) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File(fileName));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return bi;
	}

	public static byte[] rotateJ2D(byte[] in, int radian, RenderingHints hints, String format) {
		ByteArrayOutputStream out = null;
		try {
			BufferedImage bufferedImage = ImageIO
					.read(new ByteArrayInputStream(in));
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();

			BufferedImage dstImage = null;
			AffineTransform affineTransform = new AffineTransform();

			if (radian == 180) {
				affineTransform.translate(width, height);
				dstImage = new BufferedImage(width, height, bufferedImage
						.getType());
			} else if (radian == 90) {
				affineTransform.translate(height, 0);
				dstImage = new BufferedImage(height, width, bufferedImage
						.getType());
			} else if (radian == 270) {
				affineTransform.translate(0, width);
				dstImage = new BufferedImage(height, width, bufferedImage
						.getType());
			}

			affineTransform.rotate(java.lang.Math.toRadians(radian));
			AffineTransformOp affineTransformOp = new AffineTransformOp(
					affineTransform, hints);

			BufferedImage bi = affineTransformOp
					.filter(bufferedImage, dstImage);
			out = new ByteArrayOutputStream();

			ImageIO.write(bi, format, out);

			byte[] result = out.toByteArray();
			return result;
		} catch (Exception e) {
			return null;
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException ignore) {
				}
		}

	}

	/**
	 * 
	 * @param pressImg
	 * @param targetImg
	 * @param x
	 * @param y
	 * @param targetImgWidth
	 * @return
	 */
	public final static byte[] pressImage(final byte[] orignalpressImg,
			final byte[] targetImg, final byte[][] newpressImg, int[] x, int[] y) {
		ByteArrayOutputStream bout = null;
		try {
			bout = new ByteArrayOutputStream();
			BufferedImage src_biao = ImageIO.read(new ByteArrayInputStream(
					orignalpressImg));
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);

			BufferedImage des_biao = ImageIO.read(new ByteArrayInputStream(
					newpressImg[0]));
			int wideth_biao_des = des_biao.getWidth(null);
			int height_biao_des = des_biao.getHeight(null);

			BufferedImage target = ImageIO.read(new ByteArrayInputStream(
					targetImg));
			int wideth = target.getWidth(null);

			int imgWithStickerWidth = 400;

			Graphics g = target.createGraphics();
			for (int i = 0; i < x.length; i++) {
				x[i] = x[i] + wideth_biao / 2; // the point that the client
												// want to point
				y[i] = y[i] + height_biao;

				int real_x = (int) (wideth * x[i] / (imgWithStickerWidth + 0.0)); // the
																					// point
																					// x,y
																					// int
																					// the
																					// big(real)
																					// image
				int real_y = (int) (wideth * y[i] / (imgWithStickerWidth + 0.0));

				real_x = real_x - wideth_biao_des / 2;// the mark x,y int the
														// big(real) image
				real_y = real_y - height_biao_des;

				BufferedImage temp = ImageIO.read(new ByteArrayInputStream(
						newpressImg[i]));
				g.drawImage(temp, real_x, real_y, wideth_biao_des,
						height_biao_des, null);
			}

			g.dispose();

			ImageIO.write(target, getImageFormat(targetImg), bout);
			byte[] b = bout.toByteArray().clone();
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (bout != null)
				try {
					bout.close();
				} catch (IOException e) {
				}
		}
	}

	public final static void pressImage(String pressImg, String targetImg,
			int x, int y) {
		try {
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);

			// 水印文件
			File _filebiao = new File(pressImg);
			Image src_biao = ImageIO.read(_filebiao);
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.drawImage(src_biao, x, y, wideth_biao, height_biao, null);
			// /
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean getImageBySvg(String svg, String path, String filename) {
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] decodedBytes = decoder.decodeBuffer(svg);

			FileOutputStream out = new FileOutputStream(path + "temp.svg");
			out.write(decodedBytes);
			out.close();

			List<String> commend = new java.util.ArrayList<String>();
			commend.add("java");
			commend.add("-jar");
			commend.add("C:\\batik\\batik-rasterizer.jar");
			commend.add(path + "temp.svg");
			commend.add("-d");
			commend.add(path + filename);

			try {
				ProcessBuilder builder = new ProcessBuilder();
				builder.command(commend);
				Process proc = builder.start();
				InputStream stderr = proc.getErrorStream();
				InputStreamReader isr = new InputStreamReader(stderr);
				BufferedReader br = new BufferedReader(isr);
				String line = null;

				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}

				int exitVal = proc.waitFor(); // it must print the error
												// stream to make proc.waitFor()
												// work properly

				if (exitVal != 0) {
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}

	public static List<String> listImage(String directoryName) {
		File folder = new File(directoryName);

		File[] listOfFiles = folder.listFiles();
		List<String> images = new ArrayList<String>();
		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()
						&& !listOfFiles[i].getName().endsWith(".db")) {
					images.add(listOfFiles[i].getName());
				}
			}
		}
		return images;
	}

	public static byte[] changeFormat(byte[] input, String format)
			throws Exception {
		ByteArrayOutputStream out = null;
		try {

			BufferedImage target = ImageIO
					.read(new ByteArrayInputStream(input));
			out = new ByteArrayOutputStream();
			ImageIO.write(target, format, out);
		} catch (IOException ex) {
			throw new Exception(
					"can not convert the image file to png format");
		}
		return out.toByteArray();
	}
	
	public static boolean isImage(byte[] bytes) {
		ByteArrayInputStream b =new ByteArrayInputStream (bytes);
		try {
			BufferedImage src_biao = ImageIO.read(b);
			src_biao.getWidth(null);
			src_biao.getHeight(null);
			return true;
		} catch (Exception e) {
			return false;
		}
		finally
		{
			if(b!=null)
				try {
					b.close();
				} catch (IOException ignore) {
				}
		}

	}
	
	public static boolean isImage(File file) {
		try {
			BufferedImage src_biao = ImageIO.read(file);
			src_biao.getWidth(null);
			src_biao.getHeight(null);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String getImageFormat(byte[] data) {
		try {
			ImageInputStream input = ImageIO.createImageInputStream(new ByteArrayInputStream(data));
			Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(input);
			input.close();
			
			if (!imageReaders.hasNext()) {
				return null;
			}
			
			ImageReader reader = imageReaders.next();
			return reader.getFormatName();
		} catch (IOException e) {
			logger.error("Unable to determine image format", e);
			return null;
		}
	}

	public static void main(String[] args) throws Exception {

		FileInputStream file = new FileInputStream("c:\\1.jpg");
		byte[] bytes = new byte[file.available()];
		file.read(bytes);

		byte[] b = ImageTools.rotateJ2D(bytes, 270, null, "jpg");
		FileOutputStream out = new FileOutputStream("c:\\2.jpg");
		out.write(b);
		out.close();

	}
}
