package org.lab.onem2m.web.handle;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import lab.mars.ds.web.network.constant.WebOperateType;
import lab.mars.ds.web.protocol.M2mServerStatusDO;
import lab.mars.ds.web.protocol.M2mServerStatusDOs;
import lab.mars.ds.web.protocol.M2mWebPacket;
import lab.mars.ds.web.protocol.M2mWebServerStatusResponse;

import org.lab.mars.onem2m.jute.M2mBinaryInputArchive;
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

        M2mWebPacket m2mPacket = new M2mWebPacket(m2mRequestHeader, null, null,
                null);
        WebUtil.send(m2mPacket);
        while (m2mWebPacket == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "error";
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
            return "error";
        }
        model.addAttribute("message",
                m2mServerStatusDOs.getM2mServerStatusDOs());
        long i = 0;
        for (M2mServerStatusDO m2mServerLoadDO : m2mServerStatusDOs
                .getM2mServerStatusDOs()) {
            m2mServerLoadDO.setId(i++);
        }
        m2mWebPacket = null;
        return "hello";
    }

}
