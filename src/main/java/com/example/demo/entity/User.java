package com.example.demo.entity;

import java.util.List;

import javax.persistence.*;

@Entity
public class User{

    @Id
    //@GeneratedValue
    private String id;
    @Column
    private Integer age;
    @Column
    private String name;
    @Column
    private String address;
    @ManyToMany(cascade={CascadeType.REFRESH},fetch=FetchType.EAGER)
    private List<Role> roles;
    
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
    
}