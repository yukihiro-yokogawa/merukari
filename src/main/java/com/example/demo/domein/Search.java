package com.example.demo.domein;

/**
 * フォームで受け取ったリクエストパラメータを格納するファイルです.
 * 
 * @author yukihiro.yokogawa
 *
 */
public class Search {

	/**	検索フォームに入力された商品名 */
	String name;
	
	/** 親要素のプルダウンのカテゴリID */
	Integer parentCategoryId;
	
	/** 子要素のプルダウンのカテゴリID */
	Integer childCategoryId;
	
	/**	孫要素のプルダウンのカテゴリID */
	Integer grandChildCategoryId;
	
	/**	ブランド名 */
	String brand;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Integer parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public Integer getChildCategoryId() {
		return childCategoryId;
	}

	public void setChildCategoryId(Integer childCategoryId) {
		this.childCategoryId = childCategoryId;
	}

	public Integer getGrandChildCategoryId() {
		return grandChildCategoryId;
	}

	public void setGrandChildCategoryId(Integer grandChildCategoryId) {
		this.grandChildCategoryId = grandChildCategoryId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "Search [name=" + name + ", parentCategoryId=" + parentCategoryId + ", childCategoryId="
				+ childCategoryId + ", grandChildCategoryId=" + grandChildCategoryId + ", brand=" + brand + "]";
	}
	
	
	
}
