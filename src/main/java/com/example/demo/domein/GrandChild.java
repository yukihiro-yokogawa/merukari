package com.example.demo.domein;

public class GrandChild {

	Integer grandChildParent;
	
	String grandChild;

	public String getGrandChild() {
		return grandChild;
	}
	
	public Integer getGrandChildParent() {
		return grandChildParent;
	}

	public void setGrandChildParent(Integer grandChildParent) {
		this.grandChildParent = grandChildParent;
	}

	public void setGrandChild(String grandChild) {
		this.grandChild = grandChild;
	}

	@Override
	public String toString() {
		return "GrandChild [grandChildParent=" + grandChildParent + ", grandChild=" + grandChild + "]";
	}
	
}
