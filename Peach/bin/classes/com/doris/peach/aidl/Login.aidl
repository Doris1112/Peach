package com.doris.peach.aidl;
import com.doris.peach.aidl.AIDLUser;

interface Login {
	AIDLUser login(String userName,String password);
	AIDLUser loginMap(in Map user);
}