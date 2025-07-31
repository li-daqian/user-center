package com.lidaqian.user_center.server.controller;

import com.lidaqian.user_center.server.entity.App;
import com.lidaqian.user_center.server.repository.AppRepository;
import com.lidaqian.user_center.server.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final AppRepository appRepository;
    private final AppService appService;

    @GetMapping("/apps")
    public Flux<App> getAllApps() {
        return Flux.fromIterable(appRepository.findAll());
    }
}