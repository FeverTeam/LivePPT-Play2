package com.liveppt.configs;

import com.google.inject.AbstractModule;
import com.liveppt.services.UserService;
import com.liveppt.services.impl.UserServiceImpl;

/**
 * 配置依赖绑定关系
 * author 黎伟杰
 */

public class InjectionConfigModule extends AbstractModule {

	/**
	 * 绑定接口与实现
	 */
	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		bind(UserService.class).to(UserServiceImpl.class);
	}
	
}
