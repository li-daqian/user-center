package com.lidaqian.user_center.controller;

import com.lidaqian.user_center.entity.App;
import com.lidaqian.user_center.repository.AppRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final AppRepository appRepository;

    @GetMapping("/apps")
    public List<App> getAllApps() {
        return appRepository.findAll();
    }
}
