package com.mao.imageloader.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.text.TextUtils;

public class FileUtils {

	public static boolean exist(String path) {
		if(TextUtils.isEmpty(path)) {
			return false;
		}
		File f = new File(path);
		return f.exists();
	}
}
