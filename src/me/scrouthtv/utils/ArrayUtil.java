package me.scrouthtv.utils;

import java.util.List;

public class ArrayUtil {
	public static <T> void listToArray(List<T> list, T[] target) {
		for (int i = 0; i < target.length; i++)
			target[i] = list.get(i);
	}
}
