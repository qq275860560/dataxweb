package com.github.qq275860560;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.github.qq275860560.common.util.JenkinsUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangyuanlin@163.com
 *
 */
@SuppressWarnings(value = { "serial", "rawtypes" })
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JenkinsJobTest {

	@Test
	public void execute() throws Exception {

		// saveJob请求
		String url = "http://127.0.0.1:8081";
		String jobName = "jobName"+System.currentTimeMillis();
		String command = "curl https://github.com";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(String.format("%s/createItem?name=%s", url, jobName),
				HttpMethod.POST, new HttpEntity<>(JenkinsUtil.generateJenkinsJobXml(command), new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);
		log.info(response.getBody());
		// runJob请求
		response = restTemplate.exchange(String.format("%s/job/%s/build", url, jobName), HttpMethod.POST,
				new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);
		log.info(response.getBody());

		// 启用"%s/job/%s/enable"
		// 校验
		// 停用 "%s/job/%s/disable";
		// 校验

		// 最后一次日志
		// 最后一次成功日志编号"%s/job/%s/lastStableBuild/buildNumber"
		// 最后一次失败日志编号"%s/job/%s/lastUnsuccessfulBuild/buildNumber"
		// 指定job的所有构建编号"%s/job/%s/%s/api/json";
		// 所有构建编号"%s/queue/api/json"
		// 某个job某次构建的日志"%s/job/%s/%s/consoleText";
		// 更新
		command = "curl https://www.baidu.com";
		response = restTemplate.exchange(String.format("%s/job/%s/config.xml", url, jobName), HttpMethod.POST,
				new HttpEntity<>(JenkinsUtil.generateJenkinsJobXml(command), new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);
		log.info(response.getBody());

		// 获取
		response = restTemplate.exchange(String.format("%s/job/%s/config.xml", url, jobName), HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);
		log.info(response.getBody());
	
		// 删除
		response = restTemplate.exchange(String.format("%s/job/%s/doDelete", url, jobName), HttpMethod.POST,
				new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);
		log.info(response.getBody());

	}

	 
}
