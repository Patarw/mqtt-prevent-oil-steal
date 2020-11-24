package com.fty.mqtt.dao;

import com.fty.mqtt.entity.Mqtt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MqttRepository extends JpaRepository<Mqtt, String> {
    Mqtt findMqttById(String id);
}
