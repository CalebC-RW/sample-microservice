package com.robustwealth.microservices.sample.controller;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("sample.controller.SampleControllerRest")
public interface SampleControllerRestClient extends SampleControllerRest {
}
