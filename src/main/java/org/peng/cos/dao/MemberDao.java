package org.peng.cos.dao;

import javax.ejb.Local;

import org.peng.cos.model.Member;

@Local
public interface MemberDao<T> extends AbstractDao<T>
{
	public void addMember(Member newMember);
}
