package com.example.demo.domein;

public class ItemCount {
	
	/**	検索対象の中で最も小さいid（検索時の1ページ目を表示させるため） */
	Integer minId;

	/**	検索対象の数（最大ページ数用） */
	Integer countId;

	/**	検索時のページング用 */
	Integer searchRowNumber;

	public Integer getMinId() {
		return minId;
	}

	public void setMinId(Integer minId) {
		this.minId = minId;
	}
	
	public Integer getCountId() {
		return countId;
	}

	public void setCountId(Integer countId) {
		this.countId = countId;
	}

	public Integer getSearchRowNumber() {
		return searchRowNumber;
	}

	public void setSearchRowNumber(Integer searchRowNumber) {
		this.searchRowNumber = searchRowNumber;
	}

	@Override
	public String toString() {
		return "ItemCount [minId=" + minId + ", countId=" + countId + ", searchRowNumber=" + searchRowNumber + "]";
	}
	
}
