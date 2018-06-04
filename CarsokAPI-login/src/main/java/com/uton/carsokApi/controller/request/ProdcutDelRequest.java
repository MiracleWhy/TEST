package com.uton.carsokApi.controller.request;
/**
 * 商品删除（在售，售出，未上架同一个删除）假删除
 * @author bing.cheng
 *
 */
public class ProdcutDelRequest {
	/** 商品id*/
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
