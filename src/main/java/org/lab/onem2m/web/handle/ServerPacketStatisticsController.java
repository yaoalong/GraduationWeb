package org.lab.onem2m.web.handle;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import lab.mars.ds.web.network.constant.WebOperateType;
import lab.mars.ds.web.protocol.M2mServerLoadDO;
import lab.mars.ds.web.protocol.M2mWebPacket;
import lab.mars.ds.web.protocol.M2mWebServerLoadResponse;

import org.lab.mars.onem2m.proto.M2mRequestHeader;
import org.lab.mars.web.util.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ServerPacketStatisticsController {
    public static volatile M2mWebPacket m2mWebPacket;
    public static ReentrantLock reentrantLock = new ReentrantLock();
    public static Condition condition = reentrantLock.newCondition();

    @RequestMapping(value = "/serverPacketStatistics.html")
    public @ResponseBody List<M2mServerLoadDO> packetStatistics() {
        M2mRequestHeader m2mRequestHeader = new M2mRequestHeader();
        m2mRequestHeader.setType(WebOperateType.lookRemoteServerLoad.getCode());
        M2mWebPacket m2mPacket = new M2mWebPacket(m2mRequestHeader, null, null,
                null);
        WebUtil.send(m2mPacket);
        System.out.println("发送");
        while (m2mWebPacket == null) {
            reentrantLock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            } finally {
                reentrantLock.unlock();
            }

        }
        M2mWebServerLoadResponse m2mWebServerLoadResponse = (M2mWebServerLoadResponse) m2mWebPacket
                .getResponse();
        m2mWebPacket = null;
        Collections.sort(m2mWebServerLoadResponse.getM2mServerLoadDOs());
        return m2mWebServerLoadResponse.getM2mServerLoadDOs();
    }
}
