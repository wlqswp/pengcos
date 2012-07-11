package org.peng.cos.manager;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.peng.cos.dao.MemberDao;
import org.peng.cos.model.Member;
import org.peng.cos.util.MD5Code;



@Stateless
public class MemberManagerImpl implements MemberManager{
	
	@EJB
	private MemberDao memberDao;

	@Override
	public void addMember(Member newMember) {
		MD5Code md5 =new MD5Code();
		String password = md5.getMD5ofStr(newMember.getPassword());
		newMember.setPassword(password);
		memberDao.addMember(newMember);
	}

	@Override
	public boolean isUserIdUsed(String userId)
	{
		Object o = memberDao.findModel("userId", userId);
		if(o!=null) 
			return false;
		return true;
	}
	
	

}
