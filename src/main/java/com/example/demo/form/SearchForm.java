package com.example.demo.form;

/**
 * 検索フォームのリクエストパラメータを受け取るFormクラスです.
 * 
 * @author yukihiro.yokogawa
 *
 */
public class SearchForm {
	
	
	/**	検索フォームに入力された商品名 */
	String name;
	
	/** 親要素のプルダウンのカテゴリID */
	String parentCategoryId;
	
	/** 子要素のプルダウンのカテゴリID */
	String childCategoryId;
	
	/**	孫要素のプルダウンのカテゴリID */
	String grandChildCategoryId;
	
	/**	ブランド名 */
	String brand;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public String getChildCategoryId() {
		return childCategoryId;
	}

	public void setChildCategoryId(String childCategoryId) {
		this.childCategoryId = childCategoryId;
	}

	public String getGrandChildCategoryId() {
		return grandChildCategoryId;
	}

	public void setGrandChildCategoryId(String grandChildCategoryId) {
		this.grandChildCategoryId = grandChildCategoryId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public boolean getIsEmpty(SearchForm searchForm) {
		if(searchForm == null) {
			return true;
		}

		if(searchForm.getName() == null && searchForm.getParentCategoryId() == null && searchForm.getChildCategoryId() == null && searchForm.getGrandChildCategoryId() == null && searchForm.getBrand() == null) {
			return true;
		}
		
		if(searchForm.getName() != null && searchForm.getParentCategoryId() != null && searchForm.getChildCategoryId() != null && searchForm.getGrandChildCategoryId() != null && searchForm.getBrand() != null) {
			if(searchForm.getName().isEmpty() && searchForm.getParentCategoryId().isEmpty() && searchForm.getChildCategoryId().isEmpty() && searchForm.getGrandChildCategoryId().isEmpty() && searchForm.getBrand().isEmpty()){
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String toString() {
		return "SearchForm [name=" + name + ", parentCategoryId=" + parentCategoryId + ", childCategoryId="
				+ childCategoryId + ", grandChildCategoryId=" + grandChildCategoryId + ", brand=" + brand + "]";
	}
	
}
