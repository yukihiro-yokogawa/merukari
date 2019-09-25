package com.example.demo.domein;

import java.util.List;

public class ParentCategory {
	
	Integer id;
	
	String parentCategory;
	
	List<ChildCategory> childCategory;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}

	public List<ChildCategory> getChildCategory() {
		return childCategory;
	}

	public void setChildCategory(List<ChildCategory> childCategory) {
		this.childCategory = childCategory;
	}

	@Override
	public String toString() {
		return "ParentCategory [id=" + id + ", parentCategory=" + parentCategory + ", childCategory=" + childCategory
				+ "]";
	}
	
	
}
