/**
 * 
 */
package org.lab.onem2m.web.handle;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import lab.mars.ds.web.network.constant.WebOperateType;
import lab.mars.ds.web.protocol.M2mWebPacket;
import lab.mars.ds.web.protocol.M2mWebRetriveKeyResponse;

import org.lab.mars.onem2m.proto.M2mCreateRequest;
import org.lab.mars.onem2m.proto.M2mCreateResponse;
import org.lab.mars.onem2m.proto.M2mReplyHeader;
import org.lab.mars.onem2m.proto.M2mRequestHeader;
import org.lab.mars.web.util.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Administrator
 *
 */
@Controller
public class RetrieveKeyController {
    public static volatile M2mWebPacket m2mWebPacket;
    public static ReentrantLock reentrantLock = new ReentrantLock();
    public static Condition condition = reentrantLock.newCondition();

    @RequestMapping(value = "/test.html")
    public @ResponseBody M2mWebRetriveKeyResponse helloWorld(String key) {
        System.out.println("long" + key);

        M2mRequestHeader m2mRequestHeader = new M2mRequestHeader();
        m2mRequestHeader.setType(WebOperateType.retriveLocalKey.getCode());
        m2mRequestHeader.setKey(key);
        M2mCreateRequest m2mCreateRequest = new M2mCreateRequest();
        M2mCreateResponse m2mCreateResponse = new M2mCreateResponse();
        M2mReplyHeader m2mReplyHeader = new M2mReplyHeader();
        M2mWebPacket m2mPacket = new M2mWebPacket(m2mRequestHeader,
                m2mReplyHeader, m2mCreateRequest, m2mCreateResponse);
        WebUtil.send(m2mPacket);
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
        m2mWebPacket = null;
        return webGetDataResponse;
    }
}
