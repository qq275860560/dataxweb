package com.github.qq275860560;

import java.util.ArrayList;
import java.util.HashMap;
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
public class OutputTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void execute() throws Exception {

		// 登录
		ResponseEntity<Map> response = testRestTemplate.exchange("/login?username=username1&password=123456",
				HttpMethod.GET, null, Map.class);
		String access_token = (String) response.getBody().get("access_token");
		log.info("" + access_token);
		Assert.assertTrue(access_token.length() > 0);

		String name = "outputname" + System.currentTimeMillis();
		// saveOutput请求
		response = testRestTemplate.exchange(
				"//output/saveOutput?name={name}&writerId=1&type=mysqlwriter&parameterUsername=root&parameterPassword=123456&parameterWriteMode=insert&parameterColumn=id,name&parameterPreSql=delete from test&parameterConnectionJdbcUrl={parameterConnectionJdbcUrl}&parameterConnectionTable=test",
				HttpMethod.GET, new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class, new HashMap<String, Object>() {
					{
						put("parameterConnectionJdbcUrl",
								"jdbc:mysql://127.0.0.1:3306/dataxweb?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
						put("name", name);
					}
				});
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));
		// pageOutput请求
		response = testRestTemplate.exchange("//output/pageOutput?name=" + name, HttpMethod.GET,
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

		// updateOutput请求
		String name2 = "outputName" + System.currentTimeMillis();
		response = testRestTemplate.exchange("//output/updateOutput?id=" + id + "&name=" + name2,
				HttpMethod.GET, new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));

		// getOutput请求
		response = testRestTemplate.exchange("//output/getOutput?id=" + id, HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));

		String name3 = (String) ((Map<String, Object>) response.getBody().get("data")).get("name");
		Assert.assertEquals(name2, name3);

		// deleteOutput请求
		response = testRestTemplate.exchange("//output/deleteOutput?id=" + id, HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));

	}

}
