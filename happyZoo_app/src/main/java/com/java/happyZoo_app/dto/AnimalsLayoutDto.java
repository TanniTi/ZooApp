package com.java.happyZoo_app.dto;

public class AnimalsLayoutDto {
	
	private Integer id;
	private String nameA;
	private String nameB;
	private Integer count;
	
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNameA() {
		return nameA;
	}
	public void setNameA(String nameA) {
		this.nameA = nameA;
	}
	public String getNameB() {
		return nameB;
	}
	public void setNameB(String nameB) {
		this.nameB = nameB;
	}
	@Override
	public String toString() {
		return "AnimalsLayoutDto [id=" + id + ", nameA=" + nameA + ", nameB=" + nameB + ", count=" + count + "]";
	}

	

}
