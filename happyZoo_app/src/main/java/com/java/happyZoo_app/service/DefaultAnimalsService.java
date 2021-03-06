package com.java.happyZoo_app.service;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.happyZoo_app.controller.AnimalsController;
import com.java.happyZoo_app.dto.AnimalsDto;
import com.java.happyZoo_app.dto.AnimalsLayoutDto;
import com.java.happyZoo_app.entity.Animals;
import com.java.happyZoo_app.entity.AnimalsLayout;
import com.java.happyZoo_app.exception.ValidationException;
import com.java.happyZoo_app.repository.AnimalsLayoutRepository;
import com.java.happyZoo_app.repository.AnimalsRepository;

@Service
public class DefaultAnimalsService implements AnimalsService {
	
	private static Logger log_ = Logger.getLogger(AnimalsController.class.getName());
	
	@Autowired
	private AnimalsRepository animalsRepository;
	
	@Autowired
	private AnimalsLayoutRepository animalsLayoutRepository;
	
	@Autowired
	private AnimalsConverter animalsConverter;

	@Override
	public AnimalsDto saveAnimal(AnimalsDto animalsDto) throws ValidationException {
		validateAnimalsDto(animalsDto);
		Animals savedAnimal = animalsRepository.save(animalsConverter.fromAnimalDtoToAnimal(animalsDto));
        return animalsConverter.fromAnimalToAnimalDto(savedAnimal);
	}
	
	private void validateAnimalsDto(AnimalsDto animalsDto) throws ValidationException {
        if (isNull(animalsDto)) {
            throw new ValidationException("Object animal is null");
        }
        if (isNull(animalsDto.getName()) || animalsDto.getName().isEmpty()) {
            throw new ValidationException("is empty");
        }
        
        if (!(animalsRepository.findByName(animalsDto.getName()).isEmpty())) {
        	throw new ValidationException("Animal is exist");
        }
        
    }

	@Override
	public void deleteAnimal(Integer animalId) {
		animalsRepository.deleteById(animalId);
		
	}

	@Override
	public List<AnimalsDto> findAll() {
		return animalsRepository.findAll()
                .stream()
                .map(animalsConverter::fromAnimalToAnimalDto)
                .collect(Collectors.toList());
	}

	
	//Layout
	@Override
	public AnimalsLayoutDto saveLayoutAnimal(AnimalsLayoutDto animalsLayoutDto) throws ValidationException {
		
		AnimalsLayout saveLayoutAnimal = animalsConverter.l_fromAnimalDtoToAnimal(animalsLayoutDto);
		
		//???????? ???? Name A ?? ???????????? A?
		if (!((animalsLayoutRepository.findByNameA(saveLayoutAnimal.getNameA())).isEmpty())) {
			
			//?? ???????????? A ???????? Name A
			List<AnimalsLayout> tempListAAnimalsLayout = animalsLayoutRepository.findByNameA(saveLayoutAnimal.getNameA());
			
			//???????? ???? ????????????????????????, ?????????? ?????????????????? ???????? ???? ?????????????????? (Name B = ?????????????? ????-????)
			for (int i = 0; i < tempListAAnimalsLayout.size(); i++) {
				AnimalsLayout element = tempListAAnimalsLayout.get(i);
				
				if(saveLayoutAnimal.getNameB().equals(element.getNameB())) {
					saveLayoutAnimal.setCount((element.getCount()+1));
					
					animalsLayoutRepository.deleteById(element.getId());
					saveLayoutAnimal = animalsLayoutRepository.save(saveLayoutAnimal);
			        return animalsConverter.l_fromAnimalToAnimalDto(saveLayoutAnimal);
				}
			}
		}
		//?? ???????????? A ?????? Name A, ???? ???????? ???? ?? B ????????????
		if (!((animalsLayoutRepository.findByNameB(saveLayoutAnimal.getNameA())).isEmpty())) {
			
			//?? ???????????? B ???????? Name A
			List<AnimalsLayout> tempListBAnimalsLayout = animalsLayoutRepository.findByNameB(saveLayoutAnimal.getNameA());
	
			//???????? ???? ????????????????????????, ?????????? ?????????????????? ???????? ???? ?????????????????? (Name B = ?????????????? ????-????)
			for (int i = 0; i < tempListBAnimalsLayout.size(); i++) {
				AnimalsLayout element = tempListBAnimalsLayout.get(i);
				
				if(saveLayoutAnimal.getNameB().equals(element.getNameA())) {
		
					saveLayoutAnimal.setCount((element.getCount()+1));
					animalsLayoutRepository.deleteById(element.getId());
					saveLayoutAnimal = animalsLayoutRepository.save(saveLayoutAnimal);
			        return animalsConverter.l_fromAnimalToAnimalDto(saveLayoutAnimal);
				}
			}
		}
		saveLayoutAnimal = animalsLayoutRepository.save(saveLayoutAnimal);
        return animalsConverter.l_fromAnimalToAnimalDto(saveLayoutAnimal);
		
	}

	@Override
	public List<AnimalsLayoutDto> findAllLayout() {
		return animalsLayoutRepository.findAll()
                .stream()
                .map(animalsConverter::l_fromAnimalToAnimalDto)
                .collect(Collectors.toList());
	}

	@Override
	public List<AnimalsDto> findByOrderByName() {
		return animalsRepository.findByOrderByName()
				.stream()
                .map(animalsConverter::fromAnimalToAnimalDto)
                .collect(Collectors.toList());
	}

	@Override
	public List<AnimalsLayoutDto> generateLayout() {
		
		
		List<AnimalsLayout> MAIN_RESULT_LIST = new ArrayList<AnimalsLayout>();
		
		
		//???????????????????????? ???????????? ?????????????????????????? ???? ????
		List<AnimalsLayout> listAnimalsLayoutDb = animalsLayoutRepository.findByOrderByCountDesc();
		log_.info("listAnimalsLayoutDb " + listAnimalsLayoutDb);
		
		//???????????? ?????????? ???????????????? ?? ????
		List<String> arrNameAnimals = new ArrayList<String>();
		
		List<Animals> listAnimalsDb = animalsRepository.findAll();
		log_.info("listAnimalsDb (?????? ???????????????? ????????) " + listAnimalsLayoutDb);
		
		
		for (int i = 0; i < listAnimalsDb.size(); i++) {
			Animals element = listAnimalsDb.get(i);//????????????????[i]
			
			 if ((!((animalsLayoutRepository.findByNameA(element.getName())).isEmpty()))
						||
						(!((animalsLayoutRepository.findByNameB(element.getName())).isEmpty())))
			 {
				 arrNameAnimals.add(element.getName());
			 }
			 else {
				 	AnimalsLayout tempElement = new AnimalsLayout();
					tempElement.setNameA(element.getName());
					tempElement.setCount(0);
					MAIN_RESULT_LIST.add(tempElement);
					log_.info("???????? ?????????????? ?? ????????????????" + tempElement);
			 }
			
		}
		log_.info("arrNameAnimals " + arrNameAnimals);
		log_.info("listAnimalsDb " + listAnimalsDb);
		
		
		List<AnimalsLayout> listAnimalsLayoutResult;
		List<AnimalsLayout> listAnimalsLayoutResult_2;
		//???????????????????? ???? ???????????? ?? ???????????????????? ???????? ???????????????? ???? ????
		while (arrNameAnimals.size() > 1) {
			
			String element = arrNameAnimals.get(0);//????????????????[i]
			
			listAnimalsLayoutResult = animalsLayoutRepository.findByNameA(element);//???????????????????? ???? ?????????????? A
			listAnimalsLayoutResult_2 = animalsLayoutRepository.findByNameB(element);//???????????????????? ???? ?????????????? B
			log_.info("element = " + element);
			log_.info("listAnimalsLayoutResult " + listAnimalsLayoutResult);
			log_.info("listAnimalsLayoutResult_2 " + listAnimalsLayoutResult_2);
			//???????? ???? ?????????????? A
			for (int j = 0; j < listAnimalsLayoutResult.size(); j++) {
			AnimalsLayout elementLayout = listAnimalsLayoutResult.get(j);
			log_.info("elementLayout.getNameB() " + elementLayout.getNameB());
			
			//???????? ?????????????? ???????????????? ?????? ?? ???????????? ????????????????
			//???? ?????????????? ???? ???????????????? Result ????????????
			
			boolean f = isContains(elementLayout.getNameB(),arrNameAnimals);
			if (!f){
				log_.info("?????????????? ???? listAnimalsLayoutResult ????????????");
				listAnimalsLayoutResult.remove(elementLayout);
				
				}
			}
			log_.info("?????????????????? listAnimalsLayoutResult" + listAnimalsLayoutResult);
			
			for (int j = 0; j < listAnimalsLayoutResult_2.size(); j++) {
				AnimalsLayout elementLayout = listAnimalsLayoutResult_2.get(j);
				
				
				boolean f = isContains(elementLayout.getNameA(),arrNameAnimals);
				if (!f){
					log_.info("?????????????? ???? listAnimalsLayoutResult ????????????");
					listAnimalsLayoutResult_2.remove(elementLayout);
					
					}
				}
			log_.info("?????????????????? listAnimalsLayoutResult_2" + listAnimalsLayoutResult_2);
			
			List<AnimalsLayout> newList = new ArrayList<AnimalsLayout>(listAnimalsLayoutResult);
			newList.addAll(listAnimalsLayoutResult_2);
			
			
			if (newList.size()==1) {
				arrNameAnimals.remove(newList.get(0).getNameA());
				arrNameAnimals.remove(newList.get(0).getNameB());
				MAIN_RESULT_LIST.addAll(newList);
				log_.info("FINAL newList = " + newList);
				log_.info("???????????????? ?? ???????????? ?? ??????????????????:" + arrNameAnimals);
				
			}

			//???????? ???? ?????????????? ???????? 
			if (newList.size()==0) {
				AnimalsLayout tempElement = new AnimalsLayout();
				tempElement.setNameA(element);
				tempElement.setCount(0);
				MAIN_RESULT_LIST.add(tempElement);
				arrNameAnimals.remove(element);
				log_.info("???????? ?????????????? ?? ????????????????" + tempElement);
			}
			
			
			
			log_.info("???????? ???????? (newList): " + newList);
			
			//???????? ?????????????? ?????????? 1 ????????
			int count = 0;
			if (newList.size()>1) {
				log_.info("?? ???????????? ???????????? 1 ????????????????");
				
				//?????????????????????? ???????????????????????? count
				log_.info("count(0) " + count);
				for (int j = 1; j < newList.size(); j++) {
					AnimalsLayout elementl = newList.get(j);
					if (elementl.getCount()>count) {
						count = elementl.getCount();
					}
				}
				log_.info(" max count = " + count);
			
			for (int j = 0; j < newList.size(); j++) {
				log_.info("?????????????? ??????, ?????? ???????????? ?????????????????????????? count");
				AnimalsLayout elementl = newList.get(j);
				if (elementl.getCount() < count) {
					log_.info("?????????????? " + elementl);
					newList.remove(elementl);
				}
			} 
			
			//???????? ?? ???????????? ???? ???????????????? ?????????? ???????????? ????-???? - ?????????????? ????????????????
			if (newList.size()>1) {

			/* Map<Integer, Integer> hashmap = new HashMap<Integer, Integer>(); */
			int smallest = 0;
			int _id = 0;
			AnimalsLayout tempElement = null;
					log_.info("???????????? ???????????????????? count");
					for (int k = 0; k < newList.size(); k++) {
						AnimalsLayout elementk = newList.get(k);
						log_.info("element = " + element);
						log_.info("elementk = " + elementk.getNameA());
						if (elementk.getNameA().equals(element)) {
							log_.info("listAnimalsLayoutDb" + listAnimalsLayoutDb);
							log_.info("?????????????????? NameB ?? ???????????? ????????????????" + elementk.getNameB());
							
							for (int t = 0; t < listAnimalsLayoutDb.size(); t++) {
								 
								if (
										(listAnimalsLayoutDb.get(t).getNameA().equals(elementk.getNameB()))
										|| (listAnimalsLayoutDb.get(t).getNameB().equals(elementk.getNameB()))
										&&
										(listAnimalsLayoutDb.get(t).getId() != elementk.getId())
									)
										{
									
									if ( (listAnimalsLayoutDb.get(t).getCount()<smallest) || (t==1)) {
										smallest = listAnimalsLayoutDb.get(t).getCount();
										_id = elementk.getId();
										tempElement = elementk;
									}	
										}
										
							}
						}
						else {
							log_.info("listAnimalsLayoutDb" + listAnimalsLayoutDb);
							log_.info("?????????????????? NameA ?? ???????????? ????????????????" + elementk.getNameA());
							
							for (int t = 0; t < listAnimalsLayoutDb.size(); t++) {
								if (
										(listAnimalsLayoutDb.get(t).getNameA().equals(elementk.getNameA()))
										|| (listAnimalsLayoutDb.get(t).getNameB().equals(elementk.getNameA()))
										&&
										(listAnimalsLayoutDb.get(t).getId() != elementk.getId())
									)
										{
									if ( (listAnimalsLayoutDb.get(t).getCount()<smallest) || (t==1)) {
										smallest = listAnimalsLayoutDb.get(t).getCount();
										_id = elementk.getId();
										tempElement = elementk;
										
									}
										}
										
							}
						}
						
						
					} 
					
					if (tempElement == null) {
						tempElement = newList.get(0);
					}
						  log_.info("_id = " + _id);
						  MAIN_RESULT_LIST.add(tempElement);
						  arrNameAnimals.remove(tempElement.getNameA());
						  arrNameAnimals.remove(tempElement.getNameB());
						  log_.info("FINAL tempElement = " + tempElement);
						  log_.info("???????????????? ?? ???????????? ?? ??????????????????:" + arrNameAnimals);
						  
			}
			
			 else { MAIN_RESULT_LIST.add(newList.get(0));
			 arrNameAnimals.remove(newList.get(0).getNameA());
			 arrNameAnimals.remove(newList.get(0).getNameB());
			 log_.info("???????????????? ?? ???????????? ?? ??????????????????:" + arrNameAnimals); }
			 
			}
						  
		}
		if (arrNameAnimals.size() == 1)
		{
			AnimalsLayout tempElement = new AnimalsLayout();
			tempElement.setNameA(arrNameAnimals.get(0));
			tempElement.setCount(0);
			MAIN_RESULT_LIST.add(tempElement);
			log_.info("FINAL tempElement = " + tempElement);
		}
		
		Collections.sort(MAIN_RESULT_LIST, Collections.reverseOrder(AnimalsLayout.COMPARE_BY_COUNT));
		log_.info("FINALLLLL MAIN_RESULT_LIST = " + MAIN_RESULT_LIST);	
		
		return MAIN_RESULT_LIST
                .stream()
                .map(animalsConverter::l_fromAnimalToAnimalDto)
                .collect(Collectors.toList());
		
	}
	
	
	//?????????????? ???????????????? ???? ????????????????????
	public boolean isContains (String animalName, List<String> currentAnimalNamesArr) {
		boolean f = false;
		for (int i = 0; (i<currentAnimalNamesArr.size()) && !f; i++) {
				if (animalName.equals(currentAnimalNamesArr.get(i))) {
					f = true;
				}
		}
		return f;
	}


}


