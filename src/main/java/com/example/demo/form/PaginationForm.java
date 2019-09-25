package com.example.demo.form;

import javax.validation.constraints.Pattern;

public class PaginationForm {
	
	@Pattern(regexp="[0-9０-９]+", message="不正な値です。")
	String page;

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
}
