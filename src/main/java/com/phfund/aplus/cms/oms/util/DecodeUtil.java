package com.phfund.aplus.cms.oms.util;

import java.io.IOException;
import sun.misc.BASE64Decoder;

public class DecodeUtil {

    public static String decode(String str) {
        return DecodeUtil.unescape(DecodeUtil.decodeBase64(str));
    }

    public static String decodeBase64(String str) {
        try {
            byte[] buffer = new BASE64Decoder().decodeBuffer(str);
            return new String(buffer, "utf-8");
        } catch (IOException e) {
            return null;
        }
    }

    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                }
                else {
                    ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            }
            else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                }
                else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }
}
