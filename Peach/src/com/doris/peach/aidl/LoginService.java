package com.doris.peach.aidl;

import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * 
 * @author Doris
 *
 * 2016年4月20日
 */
public class LoginService extends Service{
	
	class LoginBinder extends Login.Stub{

		@Override
		public AIDLUser login(String userName, String password)
				throws RemoteException {
			// TODO Auto-generated method stub
			if("admin".equals(userName)&&"123456".equals(password)){
				return new AIDLUser(userName,password);
			}
			return null;
		}

		@Override
		public AIDLUser loginMap(Map user)
				throws RemoteException {
			// TODO Auto-generated method stub
			String userName=user.get("userName").toString();
			String password=user.get("password").toString();
			if("admin".equals(userName)&&"123456".equals(password)){
				return new AIDLUser(userName,password);
			}
			return null;
		}
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new LoginBinder();
	}

}
