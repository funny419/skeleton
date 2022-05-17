package com.funny.utils;

import com.funny.utils.helper.RegHelper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class NetworkUtil {
    public static final String LOCALHOST = "127.0.0.1";




    public static String getLocalHostname() {
        InetAddress address;
        String hostname;
        try {
            address = InetAddress.getLocalHost();
            hostname = address.getHostName();
            if (StringUtils.isEmpty(hostname)) {
                hostname = address.toString();
            }
        }
        catch (UnknownHostException noIpAddrException) {
            hostname = LOCALHOST;
        }

        return hostname;
    }


    public static String getLocalHostIp() {
        InetAddress address;
        String hostAddress;
        try {
            address = InetAddress.getLocalHost();
            hostAddress = address.getHostAddress();
            if (StringUtils.isEmpty(hostAddress)) {
                hostAddress = address.toString();
            }
        }
        catch (UnknownHostException noIpAddrException) {
            hostAddress = LOCALHOST;
        }

        return hostAddress;
    }


    public static String getRemoteIp(HttpServletRequest httpRequest) {
        String ipAddress = httpRequest.getHeader("HTTP_X_FORWARDED_FOR");

        if (ipAddress == null || ipAddress.length() == 0 || ipAddress.toLowerCase().equals("unknown")) {
            ipAddress = httpRequest.getHeader("REMOTE_ADDR");
        }

        if (ipAddress == null || ipAddress.length() == 0 || ipAddress.toLowerCase().equals("unknown")) {
            ipAddress = httpRequest.getRemoteAddr();
        }

        return RegHelper.isIP(ipAddress) ? ipAddress : LOCALHOST;
    }
}
