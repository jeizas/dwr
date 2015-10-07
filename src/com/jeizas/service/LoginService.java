package com.jeizas.service;

import java.util.List;

import com.jeizas.entity.User;

public interface LoginService {
	User getUser(String hql);
	List<User> getAllUser();
}
