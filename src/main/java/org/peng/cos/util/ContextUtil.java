package org.peng.cos.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class ContextUtil {
	
	private static final String RESOURCE_BASENAME = "msgs";
	public static String getText(String key)
	{
		try
		{
			FacesContext context = FacesContext.getCurrentInstance();
			UIViewRoot viewRoot = context.getViewRoot();
			Locale locale = viewRoot.getLocale();
	
			Application app = context.getApplication();
			ResourceBundle bundle = app.getResourceBundle(context, "msgs");
			return bundle.getString(key);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return key;
		}
		
		
	}
	
	public static String getText(String key, Object[] params)
	{
		try
		{
			FacesContext context = FacesContext.getCurrentInstance();
			UIViewRoot viewRoot = context.getViewRoot();
			Locale locale = viewRoot.getLocale();
	
			Application app = context.getApplication();
		//	ResourceBundle bundle = app.getResourceBundle(context, "msgs");
	
			MessageFormat formatter = new MessageFormat("msgs", locale);
			return formatter.format(params);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return key;
		}
	}
	

	
	public static String getI18NMessage(FacesContext context,String key){
		Application application = context.getApplication();
		ExpressionFactory f = application.getExpressionFactory();
		ValueExpression ve = f.createValueExpression(context.getELContext(), key, String.class);
		return (String)ve.getValue(context.getELContext());
		

	}
}
