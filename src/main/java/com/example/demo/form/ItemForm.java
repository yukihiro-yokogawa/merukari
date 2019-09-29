package com.example.demo.form;

public class ItemForm {

	/** 商品ID */
	String id;
	
	/** 商品名 */
	String name;
	
	/**	商品状況 */
	String condition;
	
	/** 親カテゴリ名 */
	String parentName;
	
	/** 子カテゴリ名 */
	String childName;
	
	/** 孫カテゴリ名 */
	String grandChildName;
	
	/** ブランド名 */
	String brand;
	
	/** 値段 */
	String price;
	
	/** 配送方法 */
	String shipping;
	
	/** 商品概要 */
	String description;

	String getId() {
		return id;
	}

	void setId(String id) {
		this.id = id;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	String getCondition() {
		return condition;
	}

	void setCondition(String condition) {
		this.condition = condition;
	}

	String getParentName() {
		return parentName;
	}

	void setParentName(String parentName) {
		this.parentName = parentName;
	}

	String getChildName() {
		return childName;
	}

	void setChildName(String childName) {
		this.childName = childName;
	}

	String getGrandChildName() {
		return grandChildName;
	}

	void setGrandChildName(String grandChildName) {
		this.grandChildName = grandChildName;
	}

	String getBrand() {
		return brand;
	}

	void setBrand(String brand) {
		this.brand = brand;
	}

	String getPrice() {
		return price;
	}

	void setPrice(String price) {
		this.price = price;
	}

	String getShipping() {
		return shipping;
	}

	void setShipping(String shipping) {
		this.shipping = shipping;
	}

	String getDescription() {
		return description;
	}

	void setDescription(String description) {
		this.description = description;
	}

	
}
