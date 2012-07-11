package org.peng.cos.scopevo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named("appvo")
@ApplicationScoped
public class ConstantBean 
{
	private int pageSize = 15;

	public static String picDirectory="d://pengcospic//";
	
	private int preferredWidth = 400;
	
	private int preferedHeight = 535;
	
	public int getPreferredWidth() {
		return preferredWidth;
	}

	public int getPreferedHeight() {
		return preferedHeight;
	}

	public int getPageSize() {
		return pageSize;
	}
}
