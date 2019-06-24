package com.github.qq275860560.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangyuanlin@163.com
 */
@Configuration
@Slf4j
public class CorsConfig {
	private CorsConfiguration corsConfiguration() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
		corsConfiguration.addAllowedHeader("*"); // 2允许任何头
		corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）
		corsConfiguration.addExposedHeader("Content-Type");
		corsConfiguration.addExposedHeader("X-Requested-With");
		corsConfiguration.addExposedHeader("accept");
		corsConfiguration.addExposedHeader("Origin");
		corsConfiguration.addExposedHeader("Access-Control-Request-Method");
		corsConfiguration.addExposedHeader("Access-Control-Request-Headers");
		corsConfiguration.setAllowCredentials(true);
		return corsConfiguration;
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration());
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	/*@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new com.eshore.common.filter.CorsFilter());
		registration.addUrlPatterns("/*");
		registration.setOrder(0);
		return registration;
	}*/

}