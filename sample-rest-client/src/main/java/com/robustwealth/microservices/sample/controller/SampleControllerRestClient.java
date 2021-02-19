package com.robustwealth.microservices.sample.controller;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "sample.SampleController", fallback = SampleControllerRestClientFallback.class)
public interface SampleControllerRestClient extends SampleControllerRest {}
