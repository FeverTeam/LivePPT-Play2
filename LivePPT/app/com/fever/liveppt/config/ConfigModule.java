package com.fever.liveppt.config;

import com.fever.liveppt.service.PptService;
import com.fever.liveppt.service.impl.PptServiceImpl;
import com.google.inject.AbstractModule;

public class ConfigModule extends AbstractModule {

	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		bind(PptService.class).to(PptServiceImpl.class);

	}

}
