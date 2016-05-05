package org.lab.onem2m.web.handle;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import lab.mars.ds.web.network.constant.WebOperateType;
import lab.mars.ds.web.protocol.M2mServerLoadDO;
import lab.mars.ds.web.protocol.M2mWebPacket;
import lab.mars.ds.web.protocol.M2mWebServerLoadResponse;

import org.lab.mars.onem2m.proto.M2mCreateRequest;
import org.lab.mars.onem2m.proto.M2mCreateResponse;
import org.lab.mars.onem2m.proto.M2mReplyHeader;
import org.lab.mars.onem2m.proto.M2mRequestHeader;
import org.lab.mars.onem2m.web.WebTcpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ServerPacketStatisticsController {
    static WebTcpClient webTcpClient = new WebTcpClient();
    public static volatile M2mWebPacket m2mWebPacket;
    public static ReentrantLock reentrantLock = new ReentrantLock();
    public static Condition condition = reentrantLock.newCondition();

    static {
        try {
            webTcpClient.connectionOne("192.168.10.131", 11111);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @RequestMapping(value = "/serverPacketStatistics.html")
    public @ResponseBody List<M2mServerLoadDO> packetStatistics() {

        M2mRequestHeader m2mRequestHeader = new M2mRequestHeader();
        m2mRequestHeader.setType(WebOperateType.lookRemoteServerLoad.getCode());
        M2mCreateRequest m2mCreateRequest = new M2mCreateRequest();
        M2mCreateResponse m2mCreateResponse = new M2mCreateResponse();
        M2mReplyHeader m2mReplyHeader = new M2mReplyHeader();
        M2mWebPacket m2mPacket = new M2mWebPacket(m2mRequestHeader,
                m2mReplyHeader, m2mCreateRequest, m2mCreateResponse);
        webTcpClient.write(m2mPacket);
        System.out.println("发送");
        while (m2mWebPacket == null) {
            reentrantLock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }

        }
        System.out.println("接收到了");
        M2mWebServerLoadResponse m2mWebServerLoadResponse = (M2mWebServerLoadResponse) m2mWebPacket
                .getResponse();
        System.out.println(m2mWebServerLoadResponse.getM2mServerLoadDOs()
                .size());
        m2mWebServerLoadResponse.getM2mServerLoadDOs().forEach(t -> {
            System.out.println("ip:" + t.getLabel());
            System.out.println("count:" + t.getY());
        });
        m2mWebPacket = null;
        return m2mWebServerLoadResponse.getM2mServerLoadDOs();
    }
}
