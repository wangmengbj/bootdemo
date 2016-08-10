package com.example.demo.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class User {

    @Id
    //@GeneratedValue
    private String id;
    
    private Integer age;
    
    private String name;

    // 省略构造函数
   /* public User(){
    	super();
    }
    public User(Long id,String name,Integer age){
    	super();
    	this.id=id;
    	this.name=name;
    	this.age=age;
    }*/
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
    
    // 省略getter和setter
    
}