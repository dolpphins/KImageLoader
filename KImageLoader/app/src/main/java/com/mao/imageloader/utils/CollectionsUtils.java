package com.mao.imageloader.utils;

import java.util.List;

public class CollectionsUtils {

	/**
	 * 比较两个集合是否相等，有两种情况：
	 * 1.如果都为null，则认为相等
	 * 2.如果集合A包含集合B的所有元素，同时集合B包含集合A的所有元素，则认为相等
	 * 
	 * @param one
	 * @param another
	 * @return
	 */
	public static boolean compareList(List<?> one, List<?> another) {
		if(one == null && another == null) {
			return true;
		} else if(one != null && another != null) {
			if(one.containsAll(another) && another.containsAll(one)) {
				return true;
			}
		}
		return false;
	}
}
