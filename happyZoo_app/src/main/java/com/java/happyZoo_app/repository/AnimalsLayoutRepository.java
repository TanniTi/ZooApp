package com.java.happyZoo_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.happyZoo_app.entity.Animals;
import com.java.happyZoo_app.entity.AnimalsLayout;

public interface AnimalsLayoutRepository extends JpaRepository<AnimalsLayout, Integer>{
	
	List<AnimalsLayout> findByNameA(String animalNameA);
	
	List<AnimalsLayout> findByNameB(String animalNameB);

	List<AnimalsLayout> findByOrderByCountDesc();

	List<AnimalsLayout> findByNameAOrNameB(String nameA, String nameA2);
}
