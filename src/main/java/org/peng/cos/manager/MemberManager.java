package org.peng.cos.manager;

import javax.ejb.Local;

import org.peng.cos.model.Member;

@Local
public interface MemberManager {

		public void addMember(Member newMember);
		
		public boolean isUserIdUsed(String userId);
}
