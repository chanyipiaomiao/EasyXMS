package org.easyxms;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;

class FunctionKit {

    /** 检测输入的内容是否为IP地址 */
    public static boolean checkStringIsIP(String ip){
        String pattern = "(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9]{1,2})(\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9]{1,2})){3}";
        Pattern ip_pattern = Pattern.compile(pattern);
        Matcher ip_matcher = ip_pattern.matcher(ip);
        return ip_matcher.matches();
    }

    /** 检查输入的字符串长度是否为0，为0则表示没有输入 */
    public static boolean checkStringLengthIsZero(String string){
        boolean is_not_zero = true;
        if (string.trim().length() == 0){
            is_not_zero = false;
        }
        return is_not_zero;
    }

    /** 检查输入的命令是否是 rm -rf /  */
    public static boolean checkCommandIsForceDeleteRootDirectory(String command){
        Pattern rm_pattern = Pattern.compile("\\s*rm\\s+-rf\\s+/\\s*");
        Matcher rm_match = rm_pattern.matcher(command);
        return rm_match.matches();
    }

    /** 重复某个字符串 */
    public static String repeatString(String string, int repeatCount) {
        StringBuilder sb = new StringBuilder(string.length() * repeatCount);
        for(int i = 0; i < repeatCount; i++) {
            sb.append(string);
        }
        return sb.toString();
    }

    /**
     * 获取日期时间
     * 需要传入想要的日期格式,像下面的格式:
     * yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd
     * */
    public static String getDate(String dateformat){
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
        return dateFormat.format(new Date());
    }
}
