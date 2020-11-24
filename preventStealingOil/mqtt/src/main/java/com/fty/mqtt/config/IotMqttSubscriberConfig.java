package com.fty.mqtt.config;



import com.fty.mqtt.dao.MqttRepository;
import com.fty.mqtt.entity.Mqtt;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;

import java.util.Map;


@Configuration
public class IotMqttSubscriberConfig {

    @Autowired
    private MqttConfig mqttConfig;
    @Autowired
    private MqttRepository mqttRepository;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setServerURIs(mqttConfig.getServers());
        return factory;
    }

    @Bean
    public MessageChannel iotMqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(mqttConfig.getClientId(), mqttClientFactory(), mqttConfig.getDefaultTopic());
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(2);
        adapter.setOutputChannel(iotMqttInputChannel());
        return adapter;
    }


    @Bean
    @ServiceActivator(inputChannel = "iotMqttInputChannel")
    public MessageHandler handlerTest() {

        return message -> {
            try {
                String msg = message.getPayload().toString();
                System.out.println("aa：" + msg);
                MessageHeaders id = message.getHeaders();
                String idd = id.get("id").toString();
                Mqtt mqtt = new Mqtt();
                mqtt.setId(idd);
                mqtt.setMessage(msg);
                mqttRepository.save(mqtt);
            } catch (MessagingException ex) {
                System.out.println(ex.getFailedMessage());
                System.out.println("失败");
            }
        };

    }
}


