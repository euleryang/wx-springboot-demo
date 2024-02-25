package com.tencent.wxcloudrun.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.stream.Stream;

/**
 * counter控制器
 */
@RestController

public class CounterController {

  @Autowired
  CounterService counterService;

  //@Autowired
  //Logger logger;



  /**
   * 获取当前计数
   * @return API response json
   */
  @GetMapping(value = "/api/count")
  ApiResponse get() {
    //logger.info("/api/count get request");

    Optional<Counter> counter = counterService.getCounter(1);
    Integer count = 0;
    if (counter.isPresent()) {
      count = counter.get().getCount();
    }

    Thread.getAllStackTraces().forEach(
            (k, v)-> {
              //Stream.of(v).map(StackTraceElement::getClassName).forEach(System.out::println);
            }
    );
    Thread.getAllStackTraces().forEach(
            (k, v)-> {
              Stream.of(v)
                      .map(StackTraceElement::getMethodName).forEach(System.out::println);
            }
    );

    return ApiResponse.ok(count);
  }

  /**
   * 获取当前计数
   * @return API response json
   */
  @GetMapping(value = "/api/test")
  ApiResponse getTest() {

    CounterRequest req = new CounterRequest();
    req.getActionOk();
    req.getAction();

    Thread.getAllStackTraces().forEach(
            (k, v)-> {
              //Stream.of(v).map(StackTraceElement::getClassName).forEach(System.out::println);
            }
    );

    Thread.getAllStackTraces().forEach(
            (k, v)-> {
              //stream.of(v).map(StackTraceElement::getMethodName).forEach(System.out::println);
            }
    );

    return ApiResponse.ok(2);
  }


  /**
   * 更新计数，自增或者清零
   * @param request {@link CounterRequest}
   * @return API response json
   */
  @PostMapping(value = "/api/count")
  ApiResponse create(@RequestBody CounterRequest request) {
    //logger.info("/api/count post request, action: {}", request.getAction());

    Optional<Counter> curCounter = counterService.getCounter(1);
    if (request.getAction().equals("inc")) {
      Integer count = 1;
      if (curCounter.isPresent()) {
        count += curCounter.get().getCount();
      }
      Counter counter = new Counter();
      counter.setId(1);
      counter.setCount(count);
      counterService.upsertCount(counter);
      return ApiResponse.ok(count);
    } else if (request.getAction().equals("clear")) {
      if (!curCounter.isPresent()) {
        return ApiResponse.ok(0);
      }
      counterService.clearCount(1);
      return ApiResponse.ok(0);
    } else {
      return ApiResponse.error("参数action错误");
    }
  }
  
}