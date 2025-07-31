package com.lidaqian.user_center.server.service;

import com.lidaqian.user_center.server.entity.App;
import com.lidaqian.user_center.server.entity.App_;
import com.lidaqian.user_center.server.exception.BadRequestException;
import com.lidaqian.user_center.server.repository.AppRepository;
import com.lidaqian.user_center.server.util.UniqueUtil;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppRepository appRepository;

    public boolean isAppNameUnique(UUID id, String name) {
        return !appRepository.exists((root, query, builder) -> {
            return builder.and(
                builder.equal(root.get(App_.name), name),
                builder.notEqual(root.get(App_.id), id)
            );
        });
    }

    @Transactional
    public Mono<App> create(App app) {
        if (!isAppNameUnique(app.getId(), app.getName())) {
            throw new BadRequestException("App with the same name already exists.");
        }

        app.setAppId(UniqueUtil.shortId());
        app.setSecret(UniqueUtil.simpleUuid());

        appRepository.save(app);

        return Mono.just(app);
    }

}
