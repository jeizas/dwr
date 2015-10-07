package com.jeizas.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.ScriptSessions;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.extend.TaskDispatcherFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.stereotype.Service;

import com.jeizas.entity.ChatInfo;
import com.jeizas.entity.User;
import com.jeizas.service.ChatService;
import com.jeizas.utils.DWRScriptSessionListener;

@Service("chatService")
public class ChatServiceImpl implements ChatService{

	@SuppressWarnings("unchecked")
	public void initData(HttpServletRequest request) {
		// 获取所有在线用户
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		ServletContext application = session.getServletContext();
		List<User> list = (List<User>) application.getAttribute("userList");
		
		// 自己管理ScriptSession,放在application作用域， 防止用户刷新页面或退出时ScriptSession对象还存在！
		WebContext wctx = WebContextFactory.get();
		ScriptSession ss = wctx.getScriptSession();
		Map<Integer, ScriptSession> map = (Map<Integer, ScriptSession>) application.getAttribute("ScriptSession");
		if(map==null){
			map = new HashMap<Integer, ScriptSession>();
		}
		if(map.get(user.getId())==null){
			map.put(user.getId(),ss);
		}else{
			if(map.get(user.getId()).hashCode()!=ss.hashCode()){
				map.remove(user.getId());
				map.put(user.getId(),ss);
			}
		}
		application.setAttribute("ScriptSession", map);
		
        Collection<ScriptSession> sessions = map.values();
        Util util = new Util(sessions);
        util.removeAllOptions("userList");
        util.addOptions("userList", list, "username");
 //       util.addFunctionCall("addLoginUser",user);
        
        util.removeAllOptions("userSelect");
        List<User> testList = new ArrayList();
        User u = new User();
        u.setUsername("大家");
        testList.add(u);
        util.addOptions("userSelect",testList,"username","username");
        util.addOptions("userSelect",list,"username","username");
        
//        List<User> testList1 = new ArrayList();
//        User u1 = new User();
//        u1.setUsername(user.getUsername()+"进入了聊天室！");
//        testList1.add(u1);
//        util.addOptions("chatInfos", testList1, "username");
//        util.cloneNode(elementId, idPrefix, idSuffix);
        
//        util.addFunctionCall("userLogin",user.getUsername());
        
//        for (ScriptSession session : sessions) {
//            session.addScript(script);
//        }
	}
	@SuppressWarnings("unchecked")
	public void logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		ServletContext application = request.getSession().getServletContext();
		List<User> userList = (List<User>) application.getAttribute("userList");
		userList.remove(user);
		application.setAttribute("userList", userList);
        Map<Integer, ScriptSession> map = (Map<Integer, ScriptSession>) application.getAttribute("ScriptSession");
        Collection<ScriptSession> sessions = map.values();
        Util util = new Util(sessions);
        util.removeAllOptions("userList");
        util.addOptions("userList", userList, "username");
        util.removeAllOptions("userSelect");
        util.addOptions("userSelect",userList,"username","username");
        session.invalidate();
	}

	@SuppressWarnings("deprecation")
	public void sendMsg(String us,String ci,HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");//当前用户
		ServletContext application = session.getServletContext();
		List<String> chatInfoList = (List<String>) application.getAttribute("chatInfoList");//获取当前的聊天信息
		if(chatInfoList==null){
			chatInfoList = new ArrayList<String>();
		}
		String chatInfo = timeToString(new Date())+"     "+user.getUsername()+"对"+us+"说："+ci;
		chatInfoList.add(chatInfo);
		application.setAttribute("chatInfoList", chatInfoList);//新的消息加入消息列表
		List<ChatInfo> newInfo = new ArrayList<ChatInfo>();
		ChatInfo c = new ChatInfo();
		c.setInfo(chatInfo);
		newInfo.add(c);
		
		
		Map<Integer, ScriptSession> map = (Map<Integer, ScriptSession>) application.getAttribute("ScriptSession");
	    Collection<ScriptSession> sessions = map.values();
        @SuppressWarnings("deprecation")
		Util util = new Util(sessions);
        util.addOptions("chatInfos", newInfo, "info");
	}
	
//	public void getLoginUser(HttpServletRequest request){
//		HttpSession session = request.getSession();
//		User user = (User)session.getAttribute("user");
//		WebContext wctx = WebContextFactory.get();
//	    ServerContext sctx = ServerContextFactory.get(wctx.getServletContext());
//	    String cp = wctx.getCurrentPage();
//	    Collection<ScriptSession> sessions = sctx.getScriptSessionsByPage(cp);
//	    Util util = new Util(sessions);
//	    util.addFunctionCall("loginUser",user.getUsername());
//	}
	
	public void clearWindow(HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		ServletContext application = session.getServletContext();
		Map<Integer, ScriptSession> map = (Map<Integer, ScriptSession>) application.getAttribute("ScriptSession");
		ScriptSession s = map.get(user.getId());
        Util util = new Util(s);
        util.removeAllOptions("chatInfos");
	}
	
	public static String timeToString(Date date){
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public boolean privateMsg(final String toName,final String msg,HttpServletRequest request){
		HttpSession session = request.getSession();
        try {  
            //System.out.println("private " + toId);  
            final User from = (User) session.getAttribute("user"); 
            ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
            //System.out.println(from.getUsername()+"---->"+toName);
            //Browser.withSession( toName, new Runnable(){
            //Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
                /*Collection<ScriptSession> sessions = DWRScriptSessionListener.getScriptSessions();*/
            /*ScriptSessionFilter filter = new ScriptSessionFilter() {
				public boolean match(ScriptSession scriptSession) {
	                String tag = (String)scriptSession.getAttribute("tag"); 
	                System.out.println(tag+"--->"+toName);
	                return tag == null ? false : tag.equals(toName);
					return true;
                }
            }; 
            Runnable run = new Runnable(){
            	public void run(){
            		ScriptBuffer script = new ScriptBuffer();
            		script.appendCall("chatInfos", from.getUsername(), toName, msg, new Date()); 
            		Collection<ScriptSession> coll = TaskDispatcherFactory.get().getTargetSessions();
            		for(ScriptSession session:coll){
            			if (session.getAttribute("tag").equals(toName)) {
            				session.addScript(script);
            			}
            		}
            	}
            };*/
            //Browser. withAllSessionsFiltered(filter, run);
            
            Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
                // 实现match方法，条件为真为筛选出来的session
                public boolean match(ScriptSession session) {
                	String name = (String)session.getAttribute("name"); 
	                System.out.println(name+"--->"+toName);
	                return name == null ? false : name.equals(toName);
                }
              }, new Runnable() {
                private ScriptBuffer script = new ScriptBuffer();
                public void run() {
                	// 设定前台接受消息的方法和参数
                	script.appendCall("chatInfos", from.getUsername(), toName, msg, new Date()); 
                	Collection<ScriptSession> sessions = Browser.getTargetSessions();
                	// 向所有符合条件的页面推送消息
                	System.out.println("start");
                	for (ScriptSession scriptSession : sessions) {
                			//if (((String)scriptSession.getAttribute("name")).equals(toName)) {
                			System.out.println("into");
                			scriptSession.addScript(script);
                		}
                	}
                	//}
              	});
        } catch (Exception e) {  
            e.printStackTrace();  
            Thread.currentThread().interrupt();  
        } 
        return true; 
    }
	public void onPageLoad(String tag){
        //获取当前的ScriptSession
        ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
        scriptSession.setAttribute("name",tag);
        System.out.println("setAttribute"+tag);
	}
}
