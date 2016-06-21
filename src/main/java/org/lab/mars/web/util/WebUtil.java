package org.lab.mars.web.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lab.mars.ds.web.protocol.M2mWebPacket;

import org.lab.mars.onem2m.IpAndPortDO;
import org.lab.mars.onem2m.web.WebTcpClient;

public class WebUtil {
    public static WebTcpClient webTcpClient = new WebTcpClient();

    private static List<IpAndPortDO> ipAndPortDOList = new ArrayList<>();
    private static int currentIndex;

    static {
        String server = "192.168.10.131:33333,192.168.10.131:11111,192.168.10.131:33333";
        String[] servers = server.split(",");
        for (String index : servers) {
            String[] serverAndPort = index.split(":");
            ipAndPortDOList.add(new IpAndPortDO(serverAndPort[0], Integer
                    .parseInt(serverAndPort[1])));
        }
        create();
    }

    private static void create() {
        webTcpClient = new WebTcpClient();
        try {
            webTcpClient.connectionOne(ipAndPortDOList.get(currentIndex)
                    .getIp(), ipAndPortDOList.get(currentIndex).getPort());
        } catch (IOException e) {
            create();
        }
    }

    private static void write(M2mWebPacket m2mPacket) {
        while (true) {
            try {
                webTcpClient.write(m2mPacket);
                break;
            } catch (Exception e) {
                e.printStackTrace();
                if (currentIndex == ipAndPortDOList.size() - 1) {
                    currentIndex = -1;
                }
                currentIndex++;
                create();
            }
        }
    }

    public static void send(M2mWebPacket m2mPacket) {
        write(m2mPacket);
    }

}
