package com.fty.mqtt.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "mqtt")
public class Mqtt implements Serializable {
    /**
     * ID
     */
    @Id
    private String id;

    private String message;
}
