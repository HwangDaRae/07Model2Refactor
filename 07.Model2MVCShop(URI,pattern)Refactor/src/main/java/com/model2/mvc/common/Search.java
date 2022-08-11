package com.model2.mvc.common;

public class Search {

	private int currentPage;
	private String searchCondition;
	private String searchKeyword;
	private int pageSize;
	private String priceSort;

	public Search() {
	}

	public Search(int currentPage, String searchCondition, String searchKeyword, int pageSize) {
		super();
		this.currentPage = currentPage;
		this.searchCondition = searchCondition;
		this.searchKeyword = searchKeyword;
		this.pageSize = pageSize;
	}

	public Search(int currentPage, String searchCondition, String searchKeyword, int pageSize, String priceSort) {
		super();
		this.currentPage = currentPage;
		this.searchCondition = searchCondition;
		this.searchKeyword = searchKeyword;
		this.pageSize = pageSize;
		this.priceSort = priceSort;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getPriceSort() {
		return priceSort;
	}

	public void setPriceSort(String priceSort) {
		this.priceSort = priceSort;
	}

	@Override
	public String toString() {
		return "Search [currentPage=" + currentPage + ", searchCondition=" + searchCondition + ", searchKeyword="
				+ searchKeyword + ", pageSize=" + pageSize + ", priceSort=" + priceSort + "]";
	}
}