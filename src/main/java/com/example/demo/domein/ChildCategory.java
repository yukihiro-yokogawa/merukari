package com.example.demo.domein;

import java.util.List;

public class ChildCategory {

	Integer childParent;
	
	String childCategory;
	
	List<GrandChild> grandCategory;
	
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
		return "ChildCategory [childParent=" + childParent + ", childCategory=" + childCategory + ", grandCategory="
				+ grandCategory + "]";
	}
	
	
	
}
