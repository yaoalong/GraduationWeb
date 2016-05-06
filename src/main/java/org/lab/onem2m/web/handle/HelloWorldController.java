package org.lab.onem2m.web.handle;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import lab.mars.ds.web.network.constant.WebOperateType;
import lab.mars.ds.web.protocol.M2mServerStatusDOs;
import lab.mars.ds.web.protocol.M2mWebPacket;
import lab.mars.ds.web.protocol.M2mWebServerStatusResponse;

import org.lab.mars.onem2m.jute.M2mBinaryInputArchive;
import org.lab.mars.onem2m.proto.M2mCreateRequest;
import org.lab.mars.onem2m.proto.M2mCreateResponse;
import org.lab.mars.onem2m.proto.M2mReplyHeader;
import org.lab.mars.onem2m.proto.M2mRequestHeader;
import org.lab.mars.web.util.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author:yaoalong. Date:2016/1/20. Email:yaoalong@foxmail.com
 */
@Controller
public class HelloWorldController {
    public static volatile M2mWebPacket m2mWebPacket;

    @RequestMapping("index.html")
    public String helloWorld(Model model) {
        System.out.println("wo ");
        M2mRequestHeader m2mRequestHeader = new M2mRequestHeader();
        m2mRequestHeader.setType(WebOperateType.getStatus.getCode());
        M2mCreateRequest m2mCreateRequest = new M2mCreateRequest();
        M2mCreateResponse m2mCreateResponse = new M2mCreateResponse();
        M2mReplyHeader m2mReplyHeader = new M2mReplyHeader();
        M2mWebPacket m2mPacket = new M2mWebPacket(m2mRequestHeader,
                m2mReplyHeader, m2mCreateRequest, m2mCreateResponse);
        WebUtil.webTcpClient.write(m2mPacket);
        while (m2mWebPacket == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        M2mWebServerStatusResponse webGetDataResponse = (M2mWebServerStatusResponse) m2mWebPacket
                .getResponse();
        M2mServerStatusDOs m2mServerStatusDOs = new M2mServerStatusDOs();
        ByteArrayInputStream inbaos = new ByteArrayInputStream(
                webGetDataResponse.getData());
        DataInputStream dis = new DataInputStream(inbaos);
        M2mBinaryInputArchive inboa = M2mBinaryInputArchive.getArchive(dis);
        try {
            m2mServerStatusDOs.deserialize(inboa, "data");
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("message",
                m2mServerStatusDOs.getM2mServerStatusDOs());
        m2mWebPacket = null;
        return "hello";
    }

}
