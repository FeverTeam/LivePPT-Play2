package com.fever.liveppt.config;

import com.fever.liveppt.service.MeetingService;
import com.fever.liveppt.service.PptService;
import com.fever.liveppt.service.UserService;
import com.fever.liveppt.service.impl.MeetingServiceImpl;
import com.fever.liveppt.service.impl.PptServiceImpl;
import com.fever.liveppt.service.impl.UserServiceImpl;
import com.google.inject.AbstractModule;

public class LivePPTInjectionConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        //绑定依赖注入关系
        bind(UserService.class).to(UserServiceImpl.class);
        bind(PptService.class).to(PptServiceImpl.class);
        bind(MeetingService.class).to(MeetingServiceImpl.class);
    }

}
