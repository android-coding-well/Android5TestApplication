package com.gosuncn.core.dao;

import java.sql.SQLException;

import android.content.Context;

import com.gosuncn.core.db.DatabaseHelper;
import com.gosuncn.core.bean.User;

public class UserDao {
	private Context context;

	public UserDao(Context context) {
		this.context = context;
	}

	public void add(User user) {
		try {
			DatabaseHelper.getHelper(context).getUserDao().create(user);
		} catch (SQLException e) {
		}
	}
	
	public void delete(User user) {
		try {
			DatabaseHelper.getHelper(context).getUserDao().delete(user);
		} catch (SQLException e) {
		}
	}
	public void queryForAll() {
		try {
			DatabaseHelper.getHelper(context).getUserDao().queryForAll();
		} catch (SQLException e) {
		}
	}
	public void update(User user) {
		try {
			DatabaseHelper.getHelper(context).getUserDao().update(user);
		} catch (SQLException e) {
		}
	}
}
