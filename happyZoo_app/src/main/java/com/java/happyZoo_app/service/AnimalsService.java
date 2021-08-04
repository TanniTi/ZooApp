package com.java.happyZoo_app.service;

import java.util.List;
import com.java.happyZoo_app.dto.AnimalsDto;
import com.java.happyZoo_app.dto.AnimalsLayoutDto;
import com.java.happyZoo_app.exception.ValidationException;

public interface  AnimalsService {
	
	AnimalsDto saveAnimal(AnimalsDto animalsDto) throws ValidationException;

    void deleteAnimal(Integer animalId);

    List<AnimalsDto> findAll();
    
    List<AnimalsDto> findByOrderByName();//
    
    //Layout
    AnimalsLayoutDto saveLayoutAnimal(AnimalsLayoutDto animalsLayoutDto) throws ValidationException;

    List<AnimalsLayoutDto> findAllLayout();
    
    List<AnimalsLayoutDto> generateLayout();
    
    
    

}
