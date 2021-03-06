package com.cloud.consumer.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 */
@Data
public class B<T> extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	private PPage<T> page;

	public B() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static B error() {
		return error(500, "未知异常，请联系管理员");
	}
	
	public static B error(String msg) {
		return error(500, msg);
	}
	
	public static B error(int code, String msg) {
		B b = new B();
		b.put("code", code);
		b.put("msg", msg);
		return b;
	}

	public static B ok(String msg) {
		B b = new B();
		b.put("msg", msg);
		return b;
	}
	
	public static B ok(Map<String, Object> map) {
		B b = new B();
		b.putAll(map);
		return b;
	}
	
	public static B ok() {
		return new B();
	}

	@Override
	public B put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public B setPage(PPage<T> page) {
		this.page = page;
		return this;
	}
}
