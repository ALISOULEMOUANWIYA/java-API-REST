package com.javatechie.crud.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT_TBL")
public class Product {

    @Id
    @GeneratedValue
    private int id;
    
    @Column(name= "name")
    private String name;
    
    @Column(name="quantity")
    private int quantity;
    
    @Column(name="price")
    private double price;
    
    
    public void setId(int id) {
		this.id = id;
	}
    
    public void setName(String name) {
		this.name = name;
	}
    
    public void setPrice(double price) {
		this.price = price;
	}
    
    public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
	public String getName() {
		return this.name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	
}
