package com.example.demo.domein;

import java.util.LinkedHashMap;
import java.util.Map;

public class GrandChild {
	
	Integer grandChildParent;
	
	String grandChildName;
	
	Map<Integer,String> grandChildCategoryMap = new LinkedHashMap<>();	
	
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

	public Map<Integer, String> getGrandChildCategoryMap() {
		return grandChildCategoryMap;
	}

	public void setGrandChildCategoryMap(Map<Integer, String> grandChildCategoryMap) {
		this.grandChildCategoryMap = grandChildCategoryMap;
	}

	@Override
	public String toString() {
		return "GrandChild [grandChildId=" + grandChildParent + ", grandChildName=" + grandChildName
				+ ", grandChildCategoryMap=" + grandChildCategoryMap + "]";
	}
	
}
