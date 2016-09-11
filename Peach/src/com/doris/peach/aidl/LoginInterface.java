package com.doris.peach.aidl;

import java.util.Map;

/**
 * 
 * @author Doris
 *
 * 2016年4月20日
 */
public interface LoginInterface {

	public AIDLUser login(String userName, String password);
	public AIDLUser loginMap(Map<String, String> user);
}
