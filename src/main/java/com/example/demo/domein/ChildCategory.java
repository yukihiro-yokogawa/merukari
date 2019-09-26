package com.example.demo.domein;

import java.util.List;

public class ChildCategory {
	
	Integer childCategoryId;
	
	Integer childParent;
	
	String childCategory;
	
	List<GrandChild> grandCategory;
	
	public Integer getChildCategoryId() {
		return childCategoryId;
	}

	public void setChildCategoryId(Integer childCategoryId) {
		this.childCategoryId = childCategoryId;
	}

	public Integer getChildParent() {
		return childParent;
	}

	public void setChildParent(Integer childParent) {
		this.childParent = childParent;
	}

	public String getChildCategory() {
		return childCategory;
	}

	public void setChildCategory(String childCategory) {
		this.childCategory = childCategory;
	}

	public List<GrandChild> getGrandCategory() {
		return grandCategory;
	}

	public void setGrandCategory(List<GrandChild> grandCategory) {
		this.grandCategory = grandCategory;
	}

	@Override
	public String toString() {
		return "ChildCategory [childCategoryId=" + childCategoryId + ", childParent=" + childParent + ", childCategory="
				+ childCategory + ", grandCategory=" + grandCategory + "]";
	}
	
	
	
}
