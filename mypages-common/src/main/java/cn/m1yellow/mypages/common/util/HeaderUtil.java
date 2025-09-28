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
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36"
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
