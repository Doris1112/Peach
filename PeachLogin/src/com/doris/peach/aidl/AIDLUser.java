package com.doris.peach.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * @author Doris
 *
 * 2016年4月20日
 */
public class AIDLUser implements Parcelable{

	private String userName;
	private String password;
	
	public AIDLUser(Parcel source){
		createFromParcel(source);
	}
	
	public AIDLUser(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "AIDLUser [userName=" + userName + ", password=" + password + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void createFromParcel(Parcel source) {
		// TODO Auto-generated method stub
		userName = source.readString();
		password = source.readString();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(userName);
		dest.writeString(password);
	}
	
	public static final Parcelable.Creator<AIDLUser> CREATOR = new Parcelable.Creator<AIDLUser>() {

		@Override
		public AIDLUser createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new AIDLUser(source);
		}

		@Override
		public AIDLUser[] newArray(int size) {
			// TODO Auto-generated method stub
			return new AIDLUser[size];
		}
	};
	
}
