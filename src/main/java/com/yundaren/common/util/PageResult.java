package com.yundaren.common.util;

import java.util.List;

public class PageResult<T> {
	/**
	 * 展示条数
	 */
	private int pageSize = 10;
	/**
	 * 当前页
	 */
	private int currentPage = 1;

	/**
	 * 总页数
	 */
	private int totalPage = 0;
	/**
	 * 总记录数
	 * */
	private int totalRow = 0;

	private List<T> data;

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
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

	public int getTotalPage() {
		totalPage = totalRow % pageSize != 0 ? (totalRow / pageSize) + 1 : totalRow / pageSize;
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * 分页开始
	 * 
	 * @return
	 */
	public int startRow() {
		return (currentPage - 1) > 0 ? pageSize * (currentPage - 1) : 0;
	}

	/**
	 * 分页结束
	 * 
	 * @return
	 */
	public int endRow() {
		return pageSize;
	}
}
