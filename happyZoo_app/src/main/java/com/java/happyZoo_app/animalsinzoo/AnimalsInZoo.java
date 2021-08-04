package com.java.happyZoo_app.animalsinzoo;

import java.util.ArrayList;
import java.util.List;

public class AnimalsInZoo {
	
	private String name;
	private boolean kind;
	
	
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
	
	boolean contains(String str) {
        if(name.contains(str)) return true;
        return false;
    }
	

}
