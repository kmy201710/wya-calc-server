package com.wya.pub;

import com.github.pagehelper.PageInfo;
import com.wya.pub.enums.ResponseEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseVo<T> implements Serializable {

	private String code;
	private String message;
	private T data;
	private PageInfo<T> pageInfo;

	public ResponseVo(ResponseEnum responseEnum, T data) {
		this.code = responseEnum.getCode();
		this.message = responseEnum.getMessage();
		this.data = data;
	}

	public ResponseVo(ResponseEnum responseEnum, PageInfo<T> pageInfo) {
		this.code = responseEnum.getCode();
		this.message = responseEnum.getMessage();
		this.pageInfo = pageInfo;
	}

	public ResponseVo(ResponseEnum responseEnum) {
		this.code = responseEnum.getCode();
		this.message = responseEnum.getMessage();
	}

	public ResponseVo() {
	}

	@Override
	public String toString() {
		return "BaseVO [code=" + code + ", message=" + message + "]";
	}

}
