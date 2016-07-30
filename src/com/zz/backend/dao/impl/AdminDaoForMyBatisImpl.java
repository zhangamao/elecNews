package com.zz.backend.dao.impl;

import org.apache.ibatis.session.SqlSession;

import com.zz.backend.dao.AdminDao;
import com.zz.backend.model.Admin;
import com.zz.utils.MyBatisUtil;

public class AdminDaoForMyBatisImpl extends BaseDao implements AdminDao {

	public void addAdmin(Admin admin) {
		add(admin);
	}

	public Admin findAdminByUsername(String username) {
		//打开一个session
		SqlSession session = MyBatisUtil.getSession();
		Admin admin = null;
		try {
			
			admin = (Admin)session.selectOne(Admin.class.getName()+".findAdminByUsername", username);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//关闭session
			session.close();
		}
		return admin;
	}

}
