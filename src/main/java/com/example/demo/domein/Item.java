package com.example.demo.domein;

public class Item {

	/** 商品ID */
	Integer id;
	
	/** 商品名 */
	String name;
	
	/**	商品状況 */
	Integer condition;
	
	/** 親カテゴリのID */
	Integer parentId;
	
	/** 親カテゴリ名 */
	String parentName;
	
	/** 子カテゴリのID */
	Integer childId;
	
	/** 子カテゴリの親ID */
	Integer childParent;
	
	/** 子カテゴリ名 */
	String childName;
	
	/** 孫カテゴリの親ID */
	Integer grandChildParent;
	
	/** 孫カテゴリ名 */
	String grandChildName;
	
	/** ブランド名 */
	String brand;
	
	/** 値段 */
	Integer price;
	
	/** 配送方法 */
	Integer shipping;
	
	/** 商品概要 */
	String description;
	
	/**	ページ内IDの最大値  */
	Integer maxId;
	
	/**	ページ内のIDの最小値 */
	Integer minId;
	
	Integer lastPage;

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

	public Integer getMaxId() {
		return maxId;
	}

	public void setMaxId(Integer maxId) {
		this.maxId = maxId;
	}
	
	public Integer getMinId() {
		return minId;
	}

	public void setMinId(Integer minId) {
		this.minId = minId;
	}
	
	public Integer getLastPage() {
		return lastPage;
	}

	public void setLastPage(Integer lastPage) {
		this.lastPage = lastPage;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", condition=" + condition + ", parentId=" + parentId
				+ ", parentName=" + parentName + ", childId=" + childId + ", childParent=" + childParent
				+ ", childName=" + childName + ", grandChildParent=" + grandChildParent + ", grandChildName="
				+ grandChildName + ", brand=" + brand + ", price=" + price + ", shipping=" + shipping + ", description="
				+ description + ", maxId=" + maxId + ", minId=" + minId + "]";
	}
	
	
	
}
