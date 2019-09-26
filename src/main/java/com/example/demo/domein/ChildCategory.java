package com.example.demo.domein;

import java.util.List;
import java.util.Map;

public class ChildCategory {
	
	Integer childCategoryId;
	
	Integer childParent;
	
	String childCategory;
	
	Map<Integer,String> childCategoryMap;
	
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

	public Map<Integer, String> getChildCategoryMap() {
		return childCategoryMap;
	}

	public void setChildCategoryMap(Map<Integer, String> childCategoryMap) {
		this.childCategoryMap = childCategoryMap;
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
