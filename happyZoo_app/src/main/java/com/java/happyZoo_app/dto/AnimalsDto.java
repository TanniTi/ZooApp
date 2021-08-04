package com.java.happyZoo_app.dto;

public class AnimalsDto {
	
	private Integer id;
	private String name;
	private boolean kind;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isKind() {
		return kind;
	}
	public void setKind(boolean kind) {
		this.kind = kind;
	}
	@Override
	public String toString() {
		return "AnimalsDto [name=" + name + ", kind=" + kind + ", id=" + id + "]";
	}
	
	

}
