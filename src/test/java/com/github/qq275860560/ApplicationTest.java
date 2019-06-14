package com.github.qq275860560;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangyuanlin@163.com
 *
 */
@SuppressWarnings(value = { "serial", "rawtypes" })
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void login() throws Exception {

		// ROLE为普通客户端登录

		ResponseEntity<Map> response = testRestTemplate.exchange("/login?username=username1&password=123456",
				HttpMethod.GET, null, Map.class);
		String access_token = (String) response.getBody().get("access_token");
		log.info("" + access_token);
		Assert.assertTrue(access_token.length() > 0);

		// 错误(没有认证)
		response = testRestTemplate.exchange("/api/github/qq275860560/user/pageUser?pageNum=1&pageSize=10",
				HttpMethod.GET, null, Map.class);
		Assert.assertEquals(401, response.getStatusCode().value());

		// get正常
		response = testRestTemplate.exchange("/api/github/qq275860560/user/pageUser?pageNum=1&pageSize=10",
				HttpMethod.GET, new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));

		// save错误(没有权限)
		response = testRestTemplate.exchange("/api/github/qq275860560/user/saveUser?username=username2", HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class);
		Assert.assertEquals(403, response.getStatusCode().value());

		// ROLE为管理员客户端登录
		response = testRestTemplate.exchange("/login?username=admin&password=123456", HttpMethod.GET, null, Map.class);
		String access_token2 = (String) response.getBody().get("access_token");
		log.info("" + access_token2);
		Assert.assertTrue(access_token2.length() > 0);

		// 错误(没有认证)
		response = testRestTemplate.exchange("/api/github/qq275860560/user/pageUser?pageNum=1&pageSize=10",
				HttpMethod.GET, null, Map.class);
		Assert.assertEquals(401, response.getStatusCode().value());

		// get正常
		response = testRestTemplate.exchange("/api/github/qq275860560/user/pageUser?pageNum=1&pageSize=10",
				HttpMethod.GET, new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token2);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));

		// save正常
		response = testRestTemplate.exchange("/api/github/qq275860560/user/saveUser?username=username2", HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token2);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));

	}

}
// curl "http://localhost:8080/index"
// curl "http://localhost:8080/guide"
// curl "http://localhost:8080/markdown?plugin=mysqlreader"
// curl "http://localhost:8080/pluginTemplate?plugin=mysqlreader"
// curl "http://localhost:8080/download?jobname=mysql-mysql"
// curl "http://localhost:8080/save?"
// curl "http://localhost:8080/build?"