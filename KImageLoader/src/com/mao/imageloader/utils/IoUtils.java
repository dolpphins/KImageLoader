package com.mao.imageloader.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IoUtils {

	public static boolean closeStream(Closeable stream) {
		if(stream != null) {
			try {
				stream.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 复制流数据
	 * 
	 * @param is 输入流
	 * @param os 输出流
	 * @return 成功返回true，失败返回false
	 */
	public static boolean copy(InputStream is, OutputStream os) {
		if(is == null || os == null) {
			return false;
		}
		byte[] buffer = new byte[1024];
		int length;
		try {
			while((length = is.read(buffer)) != -1) {
				os.write(buffer, 0, length);
			}
			os.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
