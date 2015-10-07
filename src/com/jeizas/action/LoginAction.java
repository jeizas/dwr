package com.jeizas.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.directwebremoting.WebContextFactory;

import com.jeizas.entity.User;
import com.jeizas.service.LoginService;
import com.opensymphony.xwork2.ActionSupport;


public class LoginAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	@Resource
	private LoginService loginService;
	private User user;
	
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public String login(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String imgRand = request.getParameter("imgRand");
		String rand = (String) request.getSession().getAttribute("rand");
		String msg = "";
			//User u = new User(1,"jeizas","123456");
			User u = loginService.getUser("from User u where u.username='"+user.getUsername()+"'");
			if(u==null){
				msg = "用户名不存在！";
			}else{
				if(!u.getPassword().equals(user.getPassword())){
					msg = "密码输入错误！";
				}else{
					//session.setAttribute("userId", u.getUsername());
					session.setAttribute("user", u);
				}
			}
			if(!"".equals(msg)){
				request.setAttribute("msg", msg);
				return "fail";
			}else{
				return SUCCESS;
			}
	}
	
	public String logout(){
		HttpServletRequest request = ServletActionContext.getRequest();
		return SUCCESS;
	}
	
	public String toChat(){
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User)request.getSession().getAttribute("user");
		ServletContext application = request.getSession().getServletContext();
		List<User> userList = (List<User>) application.getAttribute("userList");
		if(userList==null){
			userList = new ArrayList();
		}
		if(userList.contains(user)){
		}else{
			userList.add(user);
		}
		application.setAttribute("userList", userList);
		return SUCCESS;
	}
}
