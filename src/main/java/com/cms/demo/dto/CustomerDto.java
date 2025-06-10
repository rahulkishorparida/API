package com.cms.demo.dto;

public class CustomerDto {
	private int id;
	private String name;
	private int age;
	private long phone;
	private String imagebase64;
	
//	
//	@Override
//	public String toString() {
//		return "CustomerDto [id=" + id + ", name=" + name + ", age=" + age + ", phone=" + phone + ", imagebase64="
//				+ imagebase64 + "]";
//	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getImagebase64() {
		return imagebase64;
	}
	public void setImagebase64(String imagebase64) {
		this.imagebase64 = imagebase64;
	}
	
	

	

}
