package com.zz.backend.dao;

import com.zz.backend.model.Admin;

public interface AdminDao {

	public void addAdmin(Admin admin);
	public Admin findAdminByUsername(String username);
}
