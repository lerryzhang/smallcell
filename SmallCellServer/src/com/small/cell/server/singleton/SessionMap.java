package com.small.cell.server.singleton;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

/**
 * @Description: 单例工具类，保存所有mina客户端连接
 * @author whl
 * @date 2014-9-29 上午10:09:15
 * 
 */
public class SessionMap {

	private static SessionMap sessionMap = null;

	private Map<String, IoSession> map = new HashMap<String, IoSession>();


	private SessionMap() {
	}

	public static SessionMap newInstance() {

		if (sessionMap == null) {
			sessionMap = new SessionMap();
		}
		return sessionMap;
	}

	public void addSession(String key, IoSession session) {

		this.map.put(key, session);
	}

	public IoSession getSession(String key) {

		return this.map.get(key);
	}
	
	public int getSize(){
		
		
		return this.map.size();
	}

	public void sendMessage(String key, Object message) {
		
			IoSession session = getSession(key);
			if (session == null) {
				System.out.println("===sessionweikong");
				return;
			}
			System.out.println("==session");
			session.write(message);

		
	}

}