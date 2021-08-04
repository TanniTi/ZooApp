package com.java.happyZoo_app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="animals")
public class Animals {
	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	 	
	    private String name;
	    private boolean kind;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
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
		@Override
		public String toString() {
			return "Animals [id=" + id + ", name=" + name + ", kind=" + kind + "]";
		}
	    
	
		

}
