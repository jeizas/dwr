package com.jeizas.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeizas.dao.BaseDao;
import com.jeizas.entity.User;
import com.jeizas.service.LoginService;

@Service("loginService")
public class LoginServiceImpl implements LoginService{
	@Resource
	private BaseDao<User> userDao;

	public User getUser(String hql) {
		User user;
		List<User> list = userDao.find(hql);
		if(list!=null&&list.size()>0){
			user = (User)list.get(0);
		}else{
			user = null;
		}
		return user;
	}
	public List<User> getAllUser(){
		String hql = "from User u ";
		List<User> list= userDao.find(hql);
		return list;
	}
	
}
