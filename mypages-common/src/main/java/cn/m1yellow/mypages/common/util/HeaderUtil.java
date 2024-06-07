package cn.m1yellow.mypages.common.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Random;

/**
 * 请求头 Header 工具类
 * 不断变换 UA（UA在变，但如果没有使用 ip 代理，只有自己一个真实 ip，切换 UA 作用也不大）
 */
public class HeaderUtil {
    private static final String[] HEADERS = {
            "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/537.75.14",
            "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Win64; x64; Trident/6.0)",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; it; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11",
            "Opera/9.25 (Windows NT 5.1; U; en)",
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
            "Mozilla/5.0 (compatible; Konqueror/3.5; Linux) KHTML/3.5.5 (like Gecko) (Kubuntu)",
            "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.0.12) Gecko/20070731 Ubuntu/dapper-security Firefox/1.5.0.12",
            "Lynx/2.8.5rel.1 libwww-FM/2.14 SSL-MM/1.4.1 GNUTLS/1.2.9",
            "Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.7 (KHTML, like Gecko) Ubuntu/11.04 Chromium/16.0.912.77 Chrome/16.0.912.77 Safari/535.7",
            "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:10.0) Gecko/20100101 Firefox/10.0 "
    };

    public static String getOneHeaderRandom() {
        return HEADERS[new Random(HEADERS.length).nextInt(HEADERS.length)]; // [0, HEADERS.length)
    }

    public static String getOneHeader(int idx) {
        if (idx >= HEADERS.length) {
            return getOneHeaderRandom();
        }
        return HEADERS[idx];
    }


    /**
     * 获取请求真实IP地址
     * 注意！请求头可以伪造！最终获取的不一定是客户端真实IP，可能是代理IP，也可能是伪造IP
     */
    public static String getRequestIp(HttpServletRequest request) {
        // x-forwarded-for Squid http 服务代理
        // X-Forwarded-For:client1,proxy1,proxy2，一般情况下，第一个 ip 为客户端真实 ip，后面的为经过的代理服务器 ip
        String ip = request.getHeader("X-Forwarded-For");
        if (!checkIP(ip)) {
            // apache 服务代理
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!checkIP(ip)) {
            // weblogic 服务代理
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!checkIP(ip)) {
            ip = request.getRemoteAddr();
            // 从本地访问时根据网卡取本机配置的IP
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ip = inetAddress.getHostAddress();
                } catch (Exception e) {
                    e.printStackTrace();
                    ip = "127.0.0.1";
                }
            }
        }
        // 通过多个代理转发的情况，第一个IP为客户端真实IP，多个IP按照','分割
        if (checkIP(ip) && ip.contains(",")) {
            String[] ips = ip.split(",");
            for (String s : ips) {
                if (checkIP(s)) {
                    ip = s;
                    break;
                }
            }
        }
        return ip;
    }


    public static boolean checkIP(String ip) {
        return !(null == ip || "".equals(ip.trim()) || "unknown".equalsIgnoreCase(ip) || ip.length() < 7);
    }

}
