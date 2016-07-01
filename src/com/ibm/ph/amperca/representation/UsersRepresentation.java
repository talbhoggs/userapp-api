package com.ibm.ph.amperca.representation;

import java.util.ArrayList;
import java.util.List;

import com.ibm.ph.amperca.model.User;

public class UsersRepresentation {
	private String totalRecords;

	private boolean next;
	private boolean prev;

	private String nextLink;
	private String prevLink;

	private Integer currentPage;

	public UsersRepresentation() {
		this.toString();
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	private List<User> entries = new ArrayList<>();

	public List<User> getEntries() {
		return entries;
	}

	public void setEntries(List<User> entries) {
		this.entries = entries;
	}

	public String getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public String getNextLink() {
		return nextLink;
	}

	public void setNextLink(String nextLink) {
		this.nextLink = nextLink;
	}

	public String getPrevLink() {
		return prevLink;
	}

	public void setPrevLink(String prevLink) {
		this.prevLink = prevLink;
	}

	@Override
	public String toString() {
		return "UsersRepresentation [totalRecords=" + totalRecords + ", next="
				+ next + ", prev=" + prev + ", nextLink=" + nextLink
				+ ", prevLink=" + prevLink + ", currentPage=" + currentPage
				+ ", entries=" + entries + "]";
	}
}
