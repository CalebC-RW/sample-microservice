package com.robustwealth.microservices.sample.sampleconsumer;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.robustwealth.microservices")
@EnableCircuitBreaker
@ComponentScan(basePackages = "com.robustwealth.microservices")
public class OpenFeignServiceConfiguration {

}
