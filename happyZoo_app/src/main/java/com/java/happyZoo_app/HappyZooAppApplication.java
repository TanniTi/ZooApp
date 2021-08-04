package com.java.happyZoo_app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.java.happyZoo_app.entity.Animals;
import com.java.happyZoo_app.entity.AnimalsLayout;
import com.java.happyZoo_app.repository.AnimalsLayoutRepository;
import com.java.happyZoo_app.repository.AnimalsRepository;

@SpringBootApplication
public class HappyZooAppApplication implements CommandLineRunner {
	
	@Autowired
	private AnimalsRepository animalsRepo;
	@Autowired 
	private AnimalsLayoutRepository animalsLayoutRepo;
	 

	public static void main(String[] args) {
		SpringApplication.run(HappyZooAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
			List<Animals> animals = animalsRepo.findAll();
			animals.forEach(System.out::println);
			
			List<AnimalsLayout> animalL = animalsLayoutRepo.findAll();
			animalL.forEach(System.out::println);
	}
	
	

}
