package br.com.dfdevforge.common.utils;

public class Utils {
	private  Utils() {}

	public static final LogUtils log = new LogUtils();
	public static final DateUtils date = new DateUtils();
	public static final ValueUtils value = new ValueUtils();
	public static final DecryptUtils decrypt = new DecryptUtils();
	public static final EncryptUtils encrypt = new EncryptUtils();
}