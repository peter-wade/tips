package com.phfund.aplus.cms.oms.util;

public class HttpUrlConvert {

    public static String htmlEncode(String source) {
        if (source == null) {
            return "";
        }
        String html = "";
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            switch (c) {
                case '<':
                    buffer.append("&lt;");
                    break;
                case '>':
                    buffer.append("&gt;");
                    break;
                case '&':
                    buffer.append("&amp;");
                    break;
                case '"':
                    buffer.append("&quot;");
                    break;
                default:
                    buffer.append(c);
            }
        }
        html = buffer.toString();
        return html;
    }

    public static void main(String[] args) {
        System.out.println(htmlEncode("https://m.test.phfund.com.cn/corpweb/ad/registerActivity/index.html?campId=433986881399554048&protocolLabel=活动H5&title=活动H5"));
    }
}
