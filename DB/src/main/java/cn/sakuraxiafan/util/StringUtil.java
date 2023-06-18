package cn.sakuraxiafan.util;

public class StringUtil {
    public static String upperFirst(String s){
        char[] array = s.toCharArray();
        array[0]=Character.toUpperCase(array[0]);
        return String.valueOf(array);
    }
}
