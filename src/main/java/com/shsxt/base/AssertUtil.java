package com.shsxt.base;

import org.apache.commons.lang3.StringUtils;

import com.shsxt.exception.ParamException;

public class AssertUtil {
	
	/**
	 * 对象为空
	 * @param obj
	 * @param message
	 */
	public static void objectIsEmpty(Object obj,String...message) {
		if (obj == null) {
			throw new ParamException(loadMessage(message));
		}
	}

	/**
	 * 对象为空
	 * @param obj
	 * @param code
	 * @param message
	 */
	public static void objectIsEmpty(Object obj,Integer code,String...message){
		if (obj == null) {
			throw new ParamException(code, loadMessage(message));
		}
	}
		
	/**
	 * 字符参数为空
	 * @param str
	 * @param message
	 */
	public static void stringIsEmpty(String str,String...message) {
		if (StringUtils.isBlank(str)) {
			throw new ParamException(loadMessage(message));
		}
	}
	
	/**
	 * 字符参数不能为空
	 * @param str
	 * @param code
	 * @param message
	 */
	public  static void stringIsEmpty(String str,Integer code,String...message) {
		if (StringUtils.isBlank(str)) {
			throw new ParamException(code,loadMessage(message));
		}
	}
	
	/**
	 * 整形参数不能为空
	 * @param value
	 * @param message
	 */
	public static void intIsEmpty(Integer value,String...message) {
		isTrue(value == null || value <1,loadMessage(message));
	}
	
	public static void isTrue(boolean isTrue,String...message) {
		if (isTrue) {
			throw new ParamException(loadMessage(message));
		}
	}
	
	/**
	 * 获取打印信息
	 * @param message
	 * @return
	 */
	public static String loadMessage(String...message) {
		
		String msg = "";
		if (message == null || message.length == 0 || StringUtils.isBlank(message[0])) {
			msg = Constant.ERROR_MSG;
		} else {
			msg = message[0];
		}
		return msg;
	}
}
