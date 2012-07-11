package org.peng.cos.util;

public class ThreadUtil 
{
	private static ThreadLocal pageThreadLocal = new ThreadLocal();
	
	public static void setPageOffset(int offset)
	{
		pageThreadLocal.set(offset);
	}
	
	public static int getPageOffset()
	{
		Integer i= (Integer)pageThreadLocal.get();
		if(i==null)
			return 0;
		return i;
	}
}
