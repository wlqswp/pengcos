package org.peng.cos.dao;

import javax.ejb.Stateless;

import org.peng.cos.model.Member;

@Stateless
public class MemberDaoImpl extends AbstractDaoImpl<Member> implements MemberDao<Member> {

	public MemberDaoImpl() {
		super(Member.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addMember(Member newMember) {
		em.persist(newMember);
	}

}
