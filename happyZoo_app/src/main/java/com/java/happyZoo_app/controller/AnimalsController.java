package com.java.happyZoo_app.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.happyZoo_app.dto.AnimalsDto;
import com.java.happyZoo_app.dto.AnimalsLayoutDto;
import com.java.happyZoo_app.exception.ValidationException;
import com.java.happyZoo_app.service.AnimalsService;

@RestController
@RequestMapping("/animals")
@CrossOrigin
public class AnimalsController {
	
	@Autowired
    private AnimalsService animalsService;
    
    private static Logger log = Logger.getLogger(AnimalsController.class.getName());
    
    @PostMapping("/save")
    public AnimalsDto saveAnimal(@RequestBody AnimalsDto animalsDto) throws ValidationException {
        log.info("Handling save animal: " + animalsDto);
        return animalsService.saveAnimal(animalsDto);
    }
    
    @PostMapping("/saveLayout")
    public AnimalsLayoutDto saveLayoutAnimal(@RequestBody AnimalsLayoutDto animalsLayoutDto) throws ValidationException {
        log.info("Handling save animal: " + animalsLayoutDto);
        return animalsService.saveLayoutAnimal(animalsLayoutDto);
    }
     
    @GetMapping("/findAll")
    public List<AnimalsDto> findAllAnimals() {
        log.info("Handling find all animal request");
        return animalsService.findAll();
    }
    
    @GetMapping("/generate")
    public List<AnimalsLayoutDto> generateLayout() {
        log.info("Handling generateLayout request");
        return animalsService.generateLayout();
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAnimals(@PathVariable Integer id) {
        log.info("Handling delete animal request: " + id);
        animalsService.deleteAnimal(id);
        return ResponseEntity.ok().build();
    }

}
