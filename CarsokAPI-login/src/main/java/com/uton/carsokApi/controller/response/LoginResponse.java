package com.uton.carsokApi.controller.response;

/**
 * 登录res
 * @author bing.cheng
 *
 */
public class LoginResponse {
	private String token;
	private String role;
	private int id;
	private Double quotientScore;
	private int maxQuotientScore;
	private int countSum;

	public int getCountSum() {
		return countSum;
	}

	public void setCountSum(int countSum) {
		this.countSum = countSum;
	}

	public int getMaxQuotientScore() {
		return maxQuotientScore;
	}

	public void setMaxQuotientScore(int maxQuotientScore) {
		this.maxQuotientScore = maxQuotientScore;
	}

	public Double getQuotientScore() {
		return quotientScore;
	}

	public void setQuotientScore(Double quotientScore) {
		this.quotientScore = quotientScore;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
