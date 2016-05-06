package org.lab.mars.web.util;

import org.lab.mars.onem2m.web.WebTcpClient;

public class WebUtil {
    public static WebTcpClient webTcpClient = new WebTcpClient();

    static {
        try {
            webTcpClient.connectionOne("192.168.10.131", 33333);
        } catch (Exception ex) {

        }

    }
}
