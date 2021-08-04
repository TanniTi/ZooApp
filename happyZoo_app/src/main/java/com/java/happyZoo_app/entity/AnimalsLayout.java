package com.java.happyZoo_app.entity;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="animal_layout")
public class AnimalsLayout {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
 	
	private String nameA;
    private String nameB;
   
    private Integer count;
    
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNameA() {
		return nameA;
	}
	public void setNameA(String nameA) {
		this.nameA = nameA;
	}
	public String getNameB() {
		return nameB;
	}
	public void setNameB(String nameB) {
		this.nameB = nameB;
	}
	@Override
	public String toString() {
		return "AnimalsLayout [id=" + id + ", nameA=" + nameA + ", nameB=" + nameB + ", count=" + count + "]";
	}
	
	public static final Comparator<AnimalsLayout> COMPARE_BY_COUNT = new Comparator<AnimalsLayout>() {
        @Override
        public int compare(AnimalsLayout lhs, AnimalsLayout rhs) {
            return lhs.getCount() - rhs.getCount();
        }
    };
	
	

}
