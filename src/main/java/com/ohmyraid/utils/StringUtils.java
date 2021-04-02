package com.ohmyraid.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

public class StringUtils {
    static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

    public final static String EMPTY_STRING = "";
    private final static String WHITE_SPACE = " \t\n\r\f";

    public static String evl(String value, String defaultValue) {
        return isEmpty(value) ? defaultValue : value;
    }

    public static String nvl(String value, String defaultValue) {
        return isNull(value) ? defaultValue : value;
    }

    public static String trim(String value) {
        return (value == null) ? "" : value.trim();
    }

    public static String ltrim(String value) {
        if (value == null) {
            return "";
        }

        for (int i = 0; i < value.length(); i++) {
            if (WHITE_SPACE.indexOf(value.charAt(i)) == -1) {
                return value.substring(i);
            }
        }

        return "";
    }

    public static String rtrim(String value) {
        if (value == null || value.equals("")) {
            return "";
        }

        for (int i = value.length() - 1; i >= 0; i--) {
            if (WHITE_SPACE.indexOf(value.charAt(i)) == -1) {
                return value.substring(0, i);
            }
        }

        return "";
    }

    public static String atrim(String value) {
        if (value == null || value.equals("")) {
            return "";
        }

        StringBuffer result = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
            if (WHITE_SPACE.indexOf(value.charAt(i)) == -1) {
                result.append(value.charAt(i));
            }
        }

        return result.toString();
    }

    public static String lpad(String value, int padLen, char padChar) {
        String val = nvl(value, "");
        StringBuffer sb = new StringBuffer();
        int needPads = padLen - val.length();
        for (int i = 0; i < needPads; i++) {
            sb.append(padChar);
        }
        sb.append(val);
        return sb.toString();
    }

    public static String rpad(String value, int padLen, char padChar) {
        String val = nvl(value, "");
        StringBuffer sb = new StringBuffer();
        sb.append(val);
        int needPads = padLen - val.length();
        for (int i = 0; i < needPads; i++) {
            sb.append(padChar);
        }
        return sb.toString();
    }

    public static String[] split(String str, char delim, boolean isSkipNull) {
        if (str == null) {
            return null;
        }

        String[] arr = null;
        String strDelim = String.valueOf(delim);

        if (isSkipNull) {
            StringTokenizer st = new StringTokenizer(str, strDelim);
            arr = new String[st.countTokens()];
            for (int i = 0; i < arr.length && st.hasMoreTokens(); i++) {
                arr[i] = st.nextToken();
            }
        } else {
            Vector<String> vt = new Vector<String>();
            boolean setNull = str.startsWith(strDelim);
            StringTokenizer st = new StringTokenizer(str, strDelim, true);
            while (st.hasMoreTokens()) {
                String value = st.nextToken();
                if (strDelim.equals(value)) {
                    if (setNull) {
                        vt.add((String) null);
                    } else {
                        setNull = true;
                    }
                } else {
                    vt.add(value);
                    setNull = false;
                }
            }

            if (setNull) { // if (str.endsWith(strDelim))
                vt.add((String) null);
            }

            if (!vt.isEmpty()) {
                arr = new String[vt.size()];
                vt.copyInto(arr);
            }
        }

        return arr;
    }

    public static String[] split(String str, boolean isSkipNull) {
        return split(str, ',', isSkipNull);
    }

    public static String[] split(String str, char delim) {
        return split(str, delim, true);
    }

    public static String[] split(String str) {
        return split(str, ',', true);
    }

    public static String join(String[] arr, char delim, boolean isSkipNull) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        int intCnt = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (isSkipNull) {
                if (arr[i] != null) {
                    if (intCnt > 0) {
                        sb.append(delim);
                    }
                    sb.append(arr[i]);
                    intCnt++;
                }
            } else {
                if (i > 0) {
                    sb.append(delim);
                }
                sb.append(arr[i] == null ? "" : arr[i]);
            }
        }

        return sb.toString();
    }

    public static String join(String[] arr, boolean isSkipNull) {
        return join(arr, ',', isSkipNull);
    }

    public static String join(String[] arr, char delim) {
        return join(arr, delim, true);
    }

    public static String join(String[] arr) {
        return join(arr, ',', true);
    }

    public static String removeChar(String str, char rmChar) {
        if (str == null || str.indexOf(rmChar) == -1) {
            return str;
        }
        StringBuffer sb = new StringBuffer();
        char[] arr = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != rmChar) {
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    public static String removeChar(String str, char[] rmChar) {
        if (str == null || rmChar == null) {
            return str;
        }
        char[] arr = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            boolean isAppend = true;
            for (int k = 0; k < rmChar.length; k++) {
                if (arr[i] == rmChar[k]) {
                    isAppend = false;
                    break;
                }
            }
            if (isAppend) {
                sb.append(arr[i]);
            }
        }

        return sb.toString();
    }

    public static String removeReturn(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.replaceAll("\r", "").replaceAll("\n", "");
    }

    public static String encodeText(String str, String fromEnc, String toEnc) throws UnsupportedEncodingException {
        return (str == null) ? str : new String(str.getBytes(fromEnc), toEnc);
    }

    public static String encodeText(String src, String enc) throws UnsupportedEncodingException {
        return (src == null) ? src : new String(src.getBytes(), enc);
    }

    public static String encodeText(byte[] src, String enc) throws UnsupportedEncodingException {
        if (src == null) {
            return null;
        } else {
            return new String(src, enc);
        }
    }

    public static String changeCharToString(String buff, char cmp, String che) {

        StringBuffer target = new StringBuffer();

        if (buff == null || buff.length() < 1) {
            return "";
        }

        for (int i = 0; i < buff.length(); i++) {

            if (buff.charAt(i) == cmp) {
                target.append(che);
            } else {
                target.append(buff.charAt(i));
            }
        }

        return target.toString();
    }

    public static boolean isNull(String str) {
        if (str == null) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof List) {
            if (((List<?>) obj).size() > 0) {
                return false;
            }
        } else if (obj instanceof Map) {
            if (((Map<? , ?>) obj).size() > 0) {
                return false;
            }
        } else {

            String str = obj.toString();

            if (str != null) {
                int len = str.length();
                for (int i = 0; i < len; ++i) {
                    char sp = ' ';
                    if (str.charAt(i) > sp) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static String number(double d) {

        String temp = null;

        if (d == 0) {
            temp = "0";
        } else {
            DecimalFormat decimal = new DecimalFormat("###,###,###.###");
            temp = decimal.format(d);
        }

        return temp;
    }

    public static String number(String str) {

        String temp = null;

        if (str == null) {
            temp = "0";
        } else {
            double change = Double.parseDouble(str);
            DecimalFormat decimal = new DecimalFormat("###,###,###.###");
            temp = decimal.format(change);
        }

        return temp;
    }

    public static String getMessage(String msg, Object[] msgArgs) {

        if (msgArgs == null || msgArgs.length <= 0) {
            return msg;
        }
        String[] strArgs = new String[msgArgs.length];
        int i = 0;
        for (Object msgArg : msgArgs) {
            if (msgArg == null) {
                strArgs[i++] = "";
            } else {
                strArgs[i++] = msgArg.toString();
            }
        }
        return getMessage(msg, strArgs);
    }

    public static String getMessage(String msg, String[] msgArgs) {
        if (org.springframework.util.StringUtils.isEmpty(msg)) {
            return msg;
        }
        if (msgArgs == null || msgArgs.length <= 0) {
            return msg;
        }

        int i = 0;
        for (String msgArg : msgArgs) {
            String arg = "{" + i++ + "}";

            int argIdx = msg.indexOf(arg);
            if (argIdx < 0) {
                continue;
            }

            int idx = msg.indexOf(arg) + arg.length();

            String srcJosa; // 조사 확인을 위한 변수
            if (msg.length() < idx + 1) {
                srcJosa = "";
            } else {
                srcJosa = msg.substring(idx, idx + 1);
            }

            String srcSpc; // 조사 뒤에 공백 확인을 위한 변수
            if (msg.length() < idx + 2) {
                srcSpc = "";
            } else {
                srcSpc = msg.substring(idx + 1, idx + 2);
            }

            String tgtJosa = getJosa(msgArg, srcJosa.trim()); // 단어에 해당하는 조사

            String space = " ";
            if (!org.springframework.util.StringUtils.isEmpty(tgtJosa) && !srcJosa.equals(tgtJosa) && space.equals(srcSpc)) {
                msg = msg.substring(0, idx) + tgtJosa + msg.substring(idx + 1);
            }
            msg = msg.replace(arg, msgArg);
        }

        return msg;
    }

    /**
     * 입력된 단어에 해당하는 조사를 선택적으로 리턴함
     *
     * @param txt
     * @param josa
     * @return
     */
    public static String getJosa(String txt, String josa) {
        if (org.springframework.util.StringUtils.isEmpty(txt)) {
            return null;
        }

        if (!("을".equals(josa) || "를".equals(josa) || "이".equals(josa) || "가".equals(josa) || "은".equals(josa) || "는".equals(josa) || "와".equals(josa)
                || "과".equals(josa))) {
            return null;
        }

        boolean jong = hasFinalConsonant(txt);

        // jong : true면 받침있음, false면 받침없음
        String eul = "을";
        String reul = "를";
        String yi = "이";
        String ga = "가";
        String eun = "은";
        String neun = "는";
        String gwa = "과";
        String wa = "와";
        if (eul.equals(josa) || reul.equals(josa)) {
            return (jong ? eul : reul);
        } else if (yi.equals(josa) || josa.equals(ga)) {
            return (jong ? yi : ga);
        } else if (eun.equals(josa) || josa.equals(neun)) {
            return (jong ? eun : neun);
        } else { // if (josa.equals("와") || josa.equals("과")) {
            return (jong ? gwa : wa);
        }
    }

    /**
     * 한글 조사를 결정하기 위해 마지막 글자의 받침 존재 여부를 리턴함
     *
     * @param str
     * @return true면 받침있음, false면 받침없음
     */
    public static boolean hasFinalConsonant(String str) {
        if (org.springframework.util.StringUtils.isEmpty(str)) {
            return false;
        }

        String last = str.substring(str.length() - 1);
        int code = last.codePointAt(0);

        // 숫자일 때
        if (code >= 48 && code <= 57) {
            if (code == 50 || code == 52 || code == 53 || code == 57) {
                // 받침없는 2, 4, 5, 9 일 경우
                return false;
            } else {
                // 받침있는 0, 1, 3, 6, 7, 8 일 경우
                return true;
            }
        }

        // 영문일 때
        if (code >= 97 && code <= 122 || code >= 65 && code <= 90) {
            if (code == 108 || code == 109 || code == 110 || code == 114 || code == 76 || code == 77 || code == 78 || code == 82) {
                // 받침있는 l, m, n, r 일 경우
                return true;
            } else {
                // 받침없는 알파펫의 경우
                return false;
            }
        }

        return (Character.codePointAt(last, 0) - 16) % 28 != 0;
    }


    /**
     * Swagger Api Key 생성
     *
     * @param str
     * @return true면 받침있음, false면 받침없음
     */
    public static String makeApiKey(String msaName, String methodName, String apiUri) {

        return msaName.toUpperCase() + "-" + methodName.toUpperCase() + "-" + apiUri;

        // StringBuffer key = new StringBuffer();
        //
        // key.append(msaName.toUpperCase());
        // key.append("_");
        // key.append(methodName.toUpperCase());
        // key.append("_");
        // key.append(apiUri);
        //
        // String[] uris = apiUri.split("/");
        // for (String uri : uris) {
        // if (!StringUtils.isEmpty(uri)) {
        // key.append("_");
        // if (uri.contains("{")) {
        // key.append(uri.replace("{", "").replace("}", ""));
        // } else {
        // key.append(uri.toUpperCase());
        // }
        // }
        // }
        // return key.toString();

    }

    public static Object nullToString(Object taget, String defalut) {
        if (taget == null) {
            return defalut;
        } else {
            return taget;
        }
    }

    public static String nullToString(String taget, String defalut) {
        if (isEmpty(taget)) {
            return defalut;
        } else {
            return taget;
        }
    }


    public static String getSaftyFileNm(String fileNm) {
        if (isNotEmpty(fileNm)) {
            fileNm = fileNm.replaceAll("/", "");
            fileNm = fileNm.replaceAll("\\\\", "");
            // fileNm = fileNm.replaceAll("\\.", "");
            // fileNm = fileNm.replaceAll("&", "");
        }
        return fileNm;
    }
}
