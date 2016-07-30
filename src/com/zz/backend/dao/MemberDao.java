package com.zz.backend.dao;

import com.zz.backend.model.Member;
import com.zz.backend.vo.PagerVO;

public interface MemberDao {
	public void addMember(Member member);
	public void delMembers(String[] ids);
	public void updateMember(Member member);
	public void updatePassword(int memberId,String oldPass,String newPass);
	public Member findMemberById(int id);
	public Member findMemberByNickname(String nickname);
	public PagerVO findAllMembers();
}