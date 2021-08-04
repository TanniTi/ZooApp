package com.java.happyZoo_app.animalsinzoo;

import java.util.ArrayList;
import java.util.List;

public class mainAnimalList {
	public static List<AnimalsInZoo> animalList = new ArrayList<AnimalsInZoo>();
	
	public boolean addAnimal(String nameA, boolean kindA){
		AnimalsInZoo animalInZoo = new AnimalsInZoo();
		for (AnimalsInZoo temp : animalList) {
		    if(temp.contains(nameA)) {
		    	return false;
		    }
		    else {
		    	animalInZoo.setName(nameA);
				animalInZoo.setKind(kindA);
				animalList.add(animalInZoo);
		    }
	}
		return true;
	}
}
	
