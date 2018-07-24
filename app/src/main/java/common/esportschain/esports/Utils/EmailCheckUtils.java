package common.esportschain.esports.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liangzhaoyou
 * @date 2018/6/13
 */

public class EmailCheckUtils {
    //正则表达式
  /*
    String regex = "^[A-Za-z]{1,40}@[A-Za-z0-9]{1,40}\\.[A-Za-z]{2,3}$";
    return email.matches(regex);
   */

    public boolean EmailCheckUtils(String email) {

        //不适用正则
        if (email == null || "".equals(email)) {
            return false;
        }
        if (!containsOneWord('@', email) || !containsOneWord('.', email)) {
            return false;
        }
        String prefix = email.substring(0, email.indexOf("@"));
        String middle = email.substring(email.indexOf("@") + 1, email.indexOf("."));
        String subfix = email.substring(email.indexOf(".") + 1);
        System.out.println("prefix=" + prefix + "  middle=" + middle + "  subfix=" + subfix);

        if (prefix == null || prefix.length() > 40 || prefix.length() == 0) {
            return false;
        }
        if (!isAllWords(prefix)) {
            return false;
        }
        if (middle == null || middle.length() > 40 || middle.length() == 0) {
            return false;
        }
        if (!isAllWordsAndNo(middle)) {
            return false;
        }
        if (subfix == null || subfix.length() > 3 || subfix.length() < 2) {
            return false;
        }
        if (!isAllWords(subfix)) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串只包含指定的一个字符c
     * @param c
     * @param word
     * @return
     */
    private boolean containsOneWord(char c, String word) {
        char[] array = word.toCharArray();
        int count = 0;
        for (Character ch : array) {
            if (c == ch) {
                count++;
            }
        }
        return count == 1;
    }

    /**
     * 检查一个字符串是否全部是字母
     * @param prefix
     * @return
     */
    private boolean isAllWords(String prefix) {
        char[] array = prefix.toCharArray();
        for (Character ch : array) {
            if (ch < 'A' || ch > 'z' || (ch < 'a' && ch > 'Z')) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查一个字符串是否包含字母和数字
     * @param middle
     * @return
     */
    private boolean isAllWordsAndNo(String middle) {
        char[] array = middle.toCharArray();
        for (Character ch : array) {
            if (ch < '0' || ch > 'z') {
                return false;
            } else if (ch > '9' && ch < 'A') {
                return false;
            } else if (ch > 'Z' && ch < 'a') {
                return false;
            }
        }
        return true;
    }

    public boolean isEmail(String email) {
        if (null == email || "".equals(email)) {
            return false;
        }
//        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
        // 简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            System.out.println("验证邮箱地址错误" + e);
            flag = false;
        }

        return flag;
    }
}
