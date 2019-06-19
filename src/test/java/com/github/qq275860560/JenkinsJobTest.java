package com.github.qq275860560;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;
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
	 
		
	    
	 // disableJob请求
 		response = restTemplate.exchange(String.format("%s/job/%s/disable", url, jobName), HttpMethod.POST,
 				new HttpEntity<>(new HttpHeaders() {
 					{
 						set("Content-Type", "text/xml; charset=UTF-8");

 					}
 				}), String.class);
 		log.info(response.getBody());
 		
 	 
		
 	    // enableJob请求
		response = restTemplate.exchange(String.format("%s/job/%s/enable", url, jobName), HttpMethod.POST,
				new HttpEntity<>(new HttpHeaders() {
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
		
		Thread.sleep(10000);
		// getJob请求
		ResponseEntity<Map > response3 = restTemplate.exchange(String.format("%s/job/%s/api/json?pretty=true", url, jobName), HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), Map.class);
	 
		
		 Map<String, Object>  responseMap=( Map<String, Object> )response3.getBody() ;
		List list=((List<Map<String, Object>>)responseMap.get("builds")).stream().map(map->((Integer)map.get("number") )).collect(Collectors.toList());
		log.info("job的所有构建编号"+list);
		Integer number = (Integer)((Map<String, Object>)responseMap.get("lastBuild")).get("number");
		log.info("最后一次构建编号"+number);
		log.info("最后一次成功构建编号"+responseMap.get("lastSuccessfulBuild"));
		log.info("最后一次失败构建编号"+responseMap.get("lastUnsuccessfulBuild"));
		
	
		// getBuild-1请求
		 response3 = restTemplate.exchange(String.format("%s/job/%s/%s/api/json?pretty=true", url, jobName,number), HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), Map.class);		
		responseMap=( Map<String, Object> )response3.getBody() ;
		log.info("是否构建中"+(boolean)responseMap.get("building"));
		log.info("预期构建时长"+(Integer)responseMap.get("estimatedDuration"));
		log.info("实际构建时长"+(Integer)responseMap.get("duration"));
		log.info("构建结果"+(String)responseMap.get("result"));
		
		// getBuild-2请求
		 response = restTemplate.exchange(String.format("%s/job/%s/%s/consoleText", url, jobName,number), HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);		
		String consoleText = response.getBody() ;
		log.info("控制台日志="+consoleText);


		 	
		// 更新
		String parameterName;
		if(System.getProperty("os.name").toLowerCase().indexOf("windows")>=0) {
			parameterName="-n ";
		}else {
			parameterName="-c ";
		}
		
		command = "ping " + parameterName +" 1000  www.baidu.com \r\n curl https://www.baidu.com";
		response = restTemplate.exchange(String.format("%s/job/%s/config.xml", url, jobName), HttpMethod.POST,
				new HttpEntity<>(JenkinsUtil.generateJenkinsJobXml(command), new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);
		log.info(response.getBody());
		
		
		
		
		 
		
		// getJobConfig
		response = restTemplate.exchange(String.format("%s/job/%s/config.xml", url, jobName), HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);
		log.info(response.getBody());
		
		
		 
		 // getJob请求
		response3 = restTemplate.exchange(String.format("%s/job/%s/api/json?pretty=true", url, jobName), HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), Map.class);
	 
		
		
		responseMap=( Map<String, Object> )response3.getBody() ;		
		Integer nextBuildNumber = (Integer)responseMap.get("nextBuildNumber");
		log.info("下一次构建编号"+nextBuildNumber);
		
		// runJob请求
		response = restTemplate.exchange(String.format("%s/job/%s/build", url, jobName), HttpMethod.POST,
				new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);
		log.info(response.getBody());
				
		Thread.sleep(20000);
		//stopBuild,只有building=true时才可以stop
		response = restTemplate.exchange(String.format("%s/job/%s/%s/stop", url, jobName,nextBuildNumber), HttpMethod.POST,
				null, String.class);
		log.info(response.getBody());
 
	
		// deleteJob
		response = restTemplate.exchange(String.format("%s/job/%s/doDelete", url, jobName), HttpMethod.POST,
				new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);
		log.info(response.getBody());

	}

	 
}
