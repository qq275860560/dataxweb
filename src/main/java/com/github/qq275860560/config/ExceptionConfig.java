package com.github.qq275860560.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangyuanlin@163.com
 */
@Configuration
@Slf4j
public class ExceptionConfig {
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new Filter() {
			@Override
			public void init(FilterConfig config) throws ServletException {
				return;
			}

			@Override
			public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
					throws IOException, ServletException {
				HttpServletRequest request = (HttpServletRequest) req;
				HttpServletResponse response = (HttpServletResponse) res;
				try {
					chain.doFilter(request, response);
				} catch (Exception e) {
					log.error("", e);
					String result = "{\"code\":" + HttpStatus.BAD_REQUEST.value() + ",\"msg\":\"请求错误\",\"data\":\""
							+ ExceptionUtils.getStackTrace(e) + "\"}";
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Content-Type", "application/json;charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println(result);
					out.flush();
					out.close();
				}
			}

			@Override
			public void destroy() {
				return;
			}
		});
		registration.addUrlPatterns("/*");
		return registration;
	}
}