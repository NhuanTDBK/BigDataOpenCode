package model;

import java.util.Date;

public class Customer {
	public static int ID=1;
	public static int NAME = 2;
	public static int GENDER = 3;
	public static int ADDRESS = 4;
	public static int CCODE = 7;
	public static int CITY = 8;
	public static int PHONE = 10;
	public static int STORE = 13;
	private int _ID;
	private String Name;
	private String Gender;
	private String Address;
	private int CCode;//Loyality Card ID
	private String City;
	private Date Birthday;
	private String Type;//accounts type
	private String Category;
	private int Phone;
	private String Store;
	private long Revenue;
	private int getID() {
		return _ID;
	}
	public void setID(int _ID) {
		this._ID = _ID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public int getCCode() {
		return CCode;
	}
	public void setCCode(int cCode) {
		CCode = cCode;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public Date getBirthday() {
		return Birthday;
	}
	public void setBirthday(Date birthday) {
		Birthday = birthday;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public int getPhone() {
		return Phone;
	}
	public void setPhone(int phone) {
		Phone = phone;
	}
	public String getStore() {
		return Store;
	}
	public void setStore(String store) {
		Store = store;
	}
	public long getRevenue() {
		return Revenue;
	}
	public void setRevenue(long revenue) {
		Revenue = revenue;
	}
	
}
