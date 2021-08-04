package com.java.happyZoo_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.happyZoo_app.entity.Animals;

public interface AnimalsRepository extends JpaRepository<Animals, Integer> {
	
	List<Animals> findByOrderByName();

	List<Animals> findByName(String nameB);
	
	
}