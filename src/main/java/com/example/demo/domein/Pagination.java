package com.example.demo.domein;

public class Pagination {
	
	/**	ページ番号 */
	Integer page;
	
	/**	ページ操作 */
	String paging;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
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
		return "Pagination [page=" + page + ", paging=" + paging + "]";
	}
	
}
