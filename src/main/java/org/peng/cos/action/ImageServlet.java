package org.peng.cos.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.peng.cos.manager.DaoManager;
import org.peng.cos.model.Charactor;
import org.peng.cos.scopevo.ConstantBean;

@WebServlet("/imgservlet")
public class ImageServlet extends HttpServlet {
 
     private static final long serialVersionUID = 1L;
   
     @EJB
     private DaoManager daoManager;
     
     public void doGet(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
          byte[] imageData = null;
     
      
          response.setHeader("Cache-Control","no-cache"); //HTTP 1.1    
          response.setHeader("Pragma","no-cache"); //HTTP 1.0    
          response.setDateHeader ("Expires", 0); //prevents cachin
          
          String model = request.getParameter("model");
          String id = request.getParameter("id");
          String filename = request.getParameter("filename");
          String ori = request.getParameter("ori");
          int mid = 0;
          try
          {
        	  mid = Integer.parseInt(id);
          }
          catch(Exception e)
          {}
      
          
          if(model.equals("rc"))
          {
        	 Charactor c =  daoManager.findModel(Charactor.class, mid);
        	 if(c.getLogo()!=null)
        	 {
	        	 response.reset();
	             response.setContentType("image/jpg");
	             response.getOutputStream().write(c.getLogo());
	             response.getOutputStream().flush();
        	 }
          }
          
          if(model.equals("cosmsg"))
          {
        	 BufferedInputStream buffer= null;
        	 if(ori==null || !ori.equals("true"))
        	 {
        		 buffer = new BufferedInputStream(new FileInputStream(ConstantBean.picDirectory+File.separator+filename));
        	 }
        	 else
        	 {
        		 buffer = new BufferedInputStream(new FileInputStream(ConstantBean.picDirectory+File.separator+"ori_" + filename));
        	 }
        	 byte[] content = new byte[buffer.available()];
        	 buffer.read(content);
        	 buffer.close();
        	 if(content!=null)
        	 {
	        	 response.reset();
	             response.setContentType("image/jpg");
	             response.getOutputStream().write(content);
	             response.getOutputStream().flush();
        	 }
          }
          
      }
}