package com.blog.util;

public class Criteria {
	private Integer pageNum; // ?��?���? 번호
	private Integer amount; // 1?��?���??�� 출력?�� ?��코드 개수
	private String type; // �??��조건 title, content
	private String keyword; // �??�� ?��?��?��

	public Criteria() {
		this(1);
	}

	public Criteria(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Criteria(Integer pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getOffset() {
		return (this.pageNum - 1) * this.amount;
	}
}
