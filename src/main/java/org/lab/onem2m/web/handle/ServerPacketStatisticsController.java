package org.lab.onem2m.web.handle;

import java.util.ArrayList;
import java.util.List;

import org.lab.mars.onem2m.web.nework.protol.M2mNetworkMessage;
import org.lab.onem2m.web.DO.StatisticsDO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ServerPacketStatisticsController {

    @RequestMapping(value = "/serverPacketStatistics.html", produces = "application/json")
    public @ResponseBody M2mNetworkMessage packetStatistics() {
        System.out.println("long");

        List<StatisticsDO> arrayList = new ArrayList<StatisticsDO>();
        for (int i = 0; i < 10; i++) {
            arrayList.add(new StatisticsDO("192.168.10.1" + i, Long
                    .valueOf((i * 2 + 10 - i / 3) + "")));
        }
        M2mNetworkMessage m2mNetworkMessage = new M2mNetworkMessage();
        m2mNetworkMessage.setResult(arrayList);
        return m2mNetworkMessage;
    }
}
