/**
 * 
 */
package org.lab.onem2m.web.handle;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.lab.mars.onem2m.proto.M2mCreateRequest;
import org.lab.mars.onem2m.proto.M2mCreateResponse;
import org.lab.mars.onem2m.proto.M2mReplyHeader;
import org.lab.mars.onem2m.proto.M2mRequestHeader;
import org.lab.mars.onem2m.web.network.WebTcpClient;
import org.lab.mars.onem2m.web.network.constant.OperateCode;
import org.lab.mars.onem2m.web.nework.protol.M2mNetworkMessage;
import org.lab.mars.onem2m.web.nework.protol.M2mWebPacket;
import org.lab.mars.onem2m.web.nework.protol.M2mWebRetriveKeyResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Administrator
 *
 */
@Controller
public class RetrieveKeyController {
    static WebTcpClient webTcpClient = new WebTcpClient();
    public static volatile M2mWebPacket m2mWebPacket;
    public static ReentrantLock reentrantLock = new ReentrantLock();
    public static Condition condition = reentrantLock.newCondition();

    static {
        try {
            webTcpClient.connectionOne("192.168.10.131", 2222);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @RequestMapping(value = "/test.html", produces = "application/json")
    public @ResponseBody M2mNetworkMessage helloWorld(
            @RequestParam(value = "key") String key) {
        System.out.println("long");
        M2mNetworkMessage m2mNetworkMessage = new M2mNetworkMessage();
        M2mRequestHeader m2mRequestHeader = new M2mRequestHeader();
        m2mRequestHeader.setType(OperateCode.retriveRemoteKey.getCode());
        m2mRequestHeader.setKey(key);
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }

        }
        M2mWebRetriveKeyResponse webGetDataResponse = (M2mWebRetriveKeyResponse) m2mWebPacket
                .getResponse();
        HashMap<String, Set<String>> map = new HashMap<String, Set<String>>();
        map.put("servers", webGetDataResponse.getServers());
        m2mNetworkMessage.setResult(map);
        System.out.println("发送成功" + key);
        return m2mNetworkMessage;
    }
}
