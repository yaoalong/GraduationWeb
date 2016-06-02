package org.lab.onem2m.web.handle;

import java.util.List;

import lab.mars.ds.web.network.constant.WebOperateType;
import lab.mars.ds.web.protocol.M2mServerStatusDO;
import lab.mars.ds.web.protocol.M2mWebPacket;
import lab.mars.ds.web.protocol.M2mWebServerStatusResponse;

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

        List<M2mServerStatusDO> m2mServerStatusDOs = webGetDataResponse
                .getM2mServerStatusDOList();
        model.addAttribute("message",
                webGetDataResponse.getM2mServerStatusDOList());
        long i = 0;
        for (M2mServerStatusDO m2mServerLoadDO : m2mServerStatusDOs) {
            m2mServerLoadDO.setId(i++);
        }
        model.addAttribute("message", m2mServerStatusDOs);
        m2mWebPacket = null;
        return "hello";
    }

}
