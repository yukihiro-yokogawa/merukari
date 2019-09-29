package com.example.demo.form;

import javax.validation.constraints.Pattern;

public class PaginationForm {
	
	@Pattern(regexp="[0-9０-９]+", message="不正な値です。")
	String page;
	
	String paging;

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPaging() {
		return paging;
	}

	public void setPaging(String paging) {
		this.paging = paging;
	}

	@Override
	public String toString() {
		return "PaginationForm [page=" + page + ", paging=" + paging + "]";
	}
	
	
	
}
