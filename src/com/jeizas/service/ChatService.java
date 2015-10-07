package com.jeizas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public interface ChatService {
	void initData(HttpServletRequest request);
	void sendMsg(String userSelect,String chatInfo,HttpServletRequest request);
//	void getLoginUser(HttpServletRequest request);
	void clearWindow(HttpServletRequest request);
	void logout(HttpServletRequest request);
	boolean privateMsg(final String toName,final String msg,HttpServletRequest request);
	void onPageLoad(final String tag);
}
