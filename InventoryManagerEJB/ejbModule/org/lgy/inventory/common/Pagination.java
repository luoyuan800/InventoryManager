package org.lgy.inventory.common;

public class Pagination {
	private int start = 0;
	private int pageSize = 10;
	private int currentPage = 1;
	private long totalPages = 0;
	private long totalRecords = 0;

	public int getStart() {
		this.start = (this.currentPage - 1) * pageSize;
		return start;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
		this.totalPages = (long) ((this.totalRecords % this.pageSize == 0) ? this.totalRecords
				/ this.pageSize
				: this.totalRecords / this.pageSize + 1);
	}

}
