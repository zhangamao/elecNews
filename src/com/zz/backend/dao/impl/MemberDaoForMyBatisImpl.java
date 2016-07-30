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
		
		//���Ȳ�ѯ���ݿ⣬�ǳ��Ƿ��Ѿ����ڣ�
		Member m = findMemberByNickname(member.getNickname());
		if(m != null){
			throw new RuntimeException("��ע����û��ǳ��Ѿ����ڣ��뻻һ������");
		}
		
		//����ǳƲ����ڣ�������ע�ᣡ
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
			if(m.getPassword().equals(oldPass)){ //ֻ������ľ����������ݿ��е�����һ�£��������������
				m.setPassword(newPass); //��Ϊ�µ�����
				session.update(Member.class.getName()+".updatePassword", m); //���µ����ݿ���
			}else{
				throw new RuntimeException("���������벻��ȷ��������");
			}
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			throw new RuntimeException(e.getMessage(),e); //�����׳��쳣���Ա�ͻ��˿��Դ�����쳣�����û���ʾ��Ϣ
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

