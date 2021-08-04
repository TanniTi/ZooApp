package com.java.happyZoo_app.service;

import org.springframework.stereotype.Component;

import com.java.happyZoo_app.dto.AnimalsDto;
import com.java.happyZoo_app.dto.AnimalsLayoutDto;
import com.java.happyZoo_app.entity.Animals;
import com.java.happyZoo_app.entity.AnimalsLayout;


@Component
public class AnimalsConverter {
	
	public Animals fromAnimalDtoToAnimal(AnimalsDto animalsDto) {
		Animals animals = new Animals();
		animals.setId(animalsDto.getId());
		animals.setName(animalsDto.getName());
		animals.setKind(animalsDto.isKind());
        return animals;
    }

    	
    public AnimalsDto fromAnimalToAnimalDto(Animals animals) {
    	
    	AnimalsDto animalsDto = new AnimalsDto();
    	animalsDto.setId(animals.getId());
    	animalsDto.setName(animals.getName());
    	animalsDto.setKind(animals.isKind());
        return animalsDto;
    }
    
    //Layout
    public AnimalsLayout l_fromAnimalDtoToAnimal(AnimalsLayoutDto animalsLayoutDto) {
    	AnimalsLayout animalsLayout = new AnimalsLayout();
    	animalsLayout.setId(animalsLayoutDto.getId());
    	animalsLayout.setNameA(animalsLayoutDto.getNameA());
    	animalsLayout.setNameB(animalsLayoutDto.getNameB());
    	animalsLayout.setCount(animalsLayoutDto.getCount());
        return animalsLayout;
    }
    
    
   public AnimalsLayoutDto l_fromAnimalToAnimalDto(AnimalsLayout animalsLayout) {
    	
	    AnimalsLayoutDto animalsLayoutDto = new AnimalsLayoutDto();
	    animalsLayoutDto.setId(animalsLayout.getId());
	    animalsLayoutDto.setNameA(animalsLayout.getNameA());
	    animalsLayoutDto.setNameB(animalsLayout.getNameB());
	    animalsLayoutDto.setCount(animalsLayout.getCount());
        return animalsLayoutDto;
    }

}
