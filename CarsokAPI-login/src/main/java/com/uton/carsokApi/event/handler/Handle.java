package com.uton.carsokApi.event.handler;

import org.springframework.transaction.annotation.Transactional;

import com.uton.carsokApi.event.constants.OperateResult;
public interface Handle<T> {
	@Transactional
	public OperateResult handle(T t);
}
