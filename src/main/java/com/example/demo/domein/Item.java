package com.example.demo.domein;

public class Item {

	/** 商品ID */
	Integer id;
	
	/** 商品名 */
	String name;
	
	/**	商品状況 */
	Integer condition;
	
	Integer parentId;
	
	String parentName;
	
	Integer childId;
	
	Integer childParent;
	
	String childName;
	
	Integer grandChildParent;
	
	String grandChildName;
	
	/** ブランド名 */
	String brand;
	
	/** 値段 */
	Integer price;
	
	/** 配送方法 */
	Integer shipping;
	
	/** 商品概要 */
	String description;
	
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

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	public Integer getChildParent() {
		return childParent;
	}

	public void setChildParent(Integer childParent) {
		this.childParent = childParent;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public Integer getGrandChildParent() {
		return grandChildParent;
	}

	public void setGrandChildParent(Integer grandChildParent) {
		this.grandChildParent = grandChildParent;
	}

	public String getGrandChildName() {
		return grandChildName;
	}

	public void setGrandChildName(String grandChildName) {
		this.grandChildName = grandChildName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getShipping() {
		return shipping;
	}

	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", condition=" + condition + ", parentId=" + parentId
				+ ", parentName=" + parentName + ", childId=" + childId + ", childParent=" + childParent
				+ ", childName=" + childName + ", grandChildParent=" + grandChildParent + ", grandChildName="
				+ grandChildName + ", brand=" + brand + ", price=" + price + ", shipping=" + shipping + ", description="
				+ description + "]";
	}
	
	
	
}
