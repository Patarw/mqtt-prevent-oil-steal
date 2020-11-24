package com.fty.mqtt.producer;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/fun")
public class IotMqttController {

    @Autowired
    private IotMqttGateway mqttGateway;

    @RequestMapping("/testMqtt")
    @ResponseBody
    public String sendMqtt(@RequestParam(value = "topic") String topic, @RequestParam(value = "message") String message) {
        mqttGateway.sendMessage2Mqtt(message);
        return "SUCCESS";
    }

}
