package com.github.qq275860560;

import java.util.ArrayList;
import java.util.HashMap;
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
public class JobTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	
	@Test
	public void job() throws Exception {

		// 登录
		ResponseEntity<Map> response = testRestTemplate.exchange("/login?username=username1&password=123456",
				HttpMethod.GET, null, Map.class);
		String access_token = (String) response.getBody().get("access_token");
		log.info("" + access_token);
		Assert.assertTrue(access_token.length() > 0);

		String name = "jobName" + System.currentTimeMillis();
		// saveJob请求
		response = testRestTemplate.exchange("/api/github/qq275860560/job/saveJob?name={name}&inputId=1&inputName=inputName1&readerId=&readerName=mysqlreader&outputId=1&outputName=outputName1&writerId=&writerName=mysqlwriter" , HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class,new HashMap<String, Object>() {
					{
							put("name", name);
					}
				});
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));
		// pageJob请求
		response = testRestTemplate.exchange("/api/github/qq275860560/job/pageJob?name=" + name, HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));
		String id = (String) ((Map<String, Object>) ((ArrayList) ((Map<String, Object>) response.getBody().get("data"))
				.get("pageList")).get(0)).get("id");
		Assert.assertTrue(id.length() > 0);

		////////////////////////////////
		// runJob请求
		response = testRestTemplate.exchange("/api/github/qq275860560/job/runJob?id=" + id ,
				HttpMethod.GET, new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));
		////////////////////////////////
		
		// updateJob请求
		String name2 = "jobName" + System.currentTimeMillis();
		response = testRestTemplate.exchange("/api/github/qq275860560/job/updateJob?id=" + id + "&name=" + name2,
				HttpMethod.GET, new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));

		// getJob请求
		response = testRestTemplate.exchange("/api/github/qq275860560/job/getJob?id=" + id, HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));

		String name3 = (String) ((Map<String, Object>) response.getBody().get("data")).get("name");
		Assert.assertEquals(name2, name3);

		// deleteJob请求
		response = testRestTemplate.exchange("/api/github/qq275860560/job/deleteJob?id=" + id, HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
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