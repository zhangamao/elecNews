package com.zz.backend.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.apache.ibatis.session.SqlSession;

import com.zz.backend.dao.MemberDao;
import com.zz.backend.model.Member;
import com.zz.backend.vo.PagerVO;
import com.zz.utils.MyBatisUtil;

public class MemberDaoForMyBatisImpl extends BaseDao implements MemberDao {

	public void addMember(Member member) {
		
		//首先查询数据库，昵称是否已经存在！
		Member m = findMemberByNickname(member.getNickname());
		if(m != null){
			throw new RuntimeException("您注册的用户昵称已经存在，请换一个再试");
		}
		
		//如果昵称不存在，则允许注册！
		add(member);
	}

	public void delMembers(String[] ids) {
		del(Member.class,ids);
	}

	public PagerVO findAllMembers() {
		Map params = new HashMap();
		return findPaginated(Member.class.getName()+".findPaginated", params);
	}

	public Member findMemberById(int id) {
		return (Member)findById(Member.class, id);
	}

	public void updateMember(Member member) {
		update(member);
	}

	public void updatePassword(int memberId, String oldPass, String newPass) {
		SqlSession session = MyBatisUtil.getSession();
		try {
			Member m = (Member)session.selectOne(Member.class.getName()+".findById", memberId);
			if(m.getPassword().equals(oldPass)){ //只有输入的旧密码与数据库中的密码一致，才允许更改密码
				m.setPassword(newPass); //改为新的密码
				session.update(Member.class.getName()+".updatePassword", m); //更新到数据库中
			}else{
				throw new RuntimeException("旧密码输入不正确，请重试");
			}
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			throw new RuntimeException(e.getMessage(),e); //重新抛出异常，以便客户端可以处理此异常，给用户提示信息
		} finally{
			session.close();
		}
	}

	public Member findMemberByNickname(String nickname) {
		SqlSession session = MyBatisUtil.getSession();
		try {
			Member m = (Member)session.selectOne(Member.class.getName()+".findByNickname", nickname);
			return m;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			session.close();
		}
		return null;
	}

}

