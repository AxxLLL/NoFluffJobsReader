package com.rzoltanski.nofluffjobsreader.service.impl;

import com.rzoltanski.nofluffjobsreader.configuration.NoFluffJobsClientProperties;
import com.rzoltanski.nofluffjobsreader.service.NoFluffJobsService;
import com.rzoltanski.nofluffjobsreader.service.NoFluffJobsServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoFluffJobsServiceFactoryImpl implements NoFluffJobsServiceFactory {

    private final NoFluffJobsClientProperties properties;

    private final ApplicationContext context;

    @Override
    public NoFluffJobsService getService() {

        String serviceName;
        if(properties.isUseCache()) {
            serviceName = CachedNoFluffJobsServiceImpl.class.getAnnotation(Service.class).value();
        } else {
            serviceName = DefaultNoFluffJobsServiceImpl.class.getAnnotation(Service.class).value();
        }

        return context.getBean(serviceName, NoFluffJobsService.class);
    }
}
