package org.lab.onem2m.web.handle;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import lab.mars.ds.web.network.constant.WebOperateType;
import lab.mars.ds.web.protocol.M2mWebPacket;
import lab.mars.ds.web.protocol.M2mWebReplicationServersResponse;

import org.lab.mars.onem2m.proto.M2mCreateRequest;
import org.lab.mars.onem2m.proto.M2mCreateResponse;
import org.lab.mars.onem2m.proto.M2mReplyHeader;
import org.lab.mars.onem2m.proto.M2mRequestHeader;
import org.lab.mars.onem2m.web.WebTcpClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReplicationServersController {
    static WebTcpClient webTcpClient = new WebTcpClient();
    public static volatile M2mWebPacket m2mWebPacket;
    public static ReentrantLock reentrantLock = new ReentrantLock();
    public static Condition condition = reentrantLock.newCondition();

    static {
        try {
            webTcpClient.connectionOne("192.168.10.131", 22222);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @RequestMapping(value = "/{server}-replicationServer.html", produces = "application/json")
    public String helloWorld(Model model, @PathVariable("server") String server) {
        System.out.println("wo " + server);
        M2mRequestHeader m2mRequestHeader = new M2mRequestHeader();
        m2mRequestHeader.setType(WebOperateType.lookReplicationServers
                .getCode());
        m2mRequestHeader.setKey(server.replace("~", ":"));
        M2mCreateRequest m2mCreateRequest = new M2mCreateRequest();
        M2mCreateResponse m2mCreateResponse = new M2mCreateResponse();
        M2mReplyHeader m2mReplyHeader = new M2mReplyHeader();
        M2mWebPacket m2mPacket = new M2mWebPacket(m2mRequestHeader,
                m2mReplyHeader, m2mCreateRequest, m2mCreateResponse);
        webTcpClient.write(m2mPacket);
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
        M2mWebReplicationServersResponse m2mWebReplicationServersResponse = (M2mWebReplicationServersResponse) m2mWebPacket
                .getResponse();
        model.addAttribute("server", server.replace("~", ":"));

        model.addAttribute("message",
                m2mWebReplicationServersResponse.getReplicationServers());
        m2mWebPacket = null;
        return "replicationServer";
    }

}
