package com.github.qq275860560;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.github.qq275860560.common.util.CommandUtil;
import com.github.qq275860560.common.util.CompressUtil;
import com.github.qq275860560.constant.Constant;
import com.github.qq275860560.dao.PluginDao;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangyuanlin@163.com
 *
 */
@SuppressWarnings(value = { "serial", "rawtypes" })
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PluginTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void plugin() throws Exception {

		// 登录
		ResponseEntity<Map> response = testRestTemplate.exchange("/login?username=username1&password=123456",
				HttpMethod.GET, null, Map.class);
		String access_token = (String) response.getBody().get("access_token");
		log.info("" + access_token);
		Assert.assertTrue(access_token.length() > 0);

		String name = "pluginname" + System.currentTimeMillis();
		// savePlugin请求
		File file = new File(FileUtils.getTempDirectoryPath(), File.separator + "mysqlreader1.zip");
		FileUtils.copyURLToFile(new URL("https://github.com/qq275860560/dataxweb/blob/master/src/main/resources/static/mysqlreader.zip?raw=true"), file);;
		    // 文件必须封装成FileSystemResource这个类型后端才能收到附件
        FileSystemResource resource = new FileSystemResource(file);
        // 然后所有参数要封装到MultiValueMap里面
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);
        param.add("name", name);
        param.add("type", 0);
		response = testRestTemplate.exchange("/api/github/qq275860560/plugin/savePlugin",
				HttpMethod.POST, new HttpEntity<>(param,new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));
		// pagePlugin请求
		response = testRestTemplate.exchange("/api/github/qq275860560/plugin/pagePlugin?name=" + name, HttpMethod.GET,
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

		
		// getPluginMarkdown请求
		response = testRestTemplate.exchange("/api/github/qq275860560/plugin/getPluginMarkdown?id=" + 1, HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));
		
		
		
		// getPluginSource请求
		ResponseEntity<byte[]> response2 = testRestTemplate.exchange("/api/github/qq275860560/plugin/getPluginSource?id="+ id, HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), byte[].class);
		Assert.assertEquals(200, response2.getStatusCode().value());
		File file2 = new File(FileUtils.getTempDirectoryPath(), File.separator + "mysqlreader2.zip");
		FileUtils.writeByteArrayToFile(file2, response2.getBody());
		Assert.assertEquals(DigestUtils.md5Hex(FileUtils.readFileToByteArray(file)), DigestUtils.md5Hex(FileUtils.readFileToByteArray(file2)));
	
				
				
		// updatePlugin请求
		String name2 = "pluginName" + System.currentTimeMillis();
		response = testRestTemplate.exchange("/api/github/qq275860560/plugin/updatePlugin?id=" + id + "&name=" + name2,
				HttpMethod.GET, new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));

		// getPlugin请求
		response = testRestTemplate.exchange("/api/github/qq275860560/plugin/getPlugin?id=" + id, HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));

		String name3 = (String) ((Map<String, Object>) response.getBody().get("data")).get("name");
		Assert.assertEquals(name2, name3);
		
		

 

		// deletePlugin请求
		response = testRestTemplate.exchange("/api/github/qq275860560/plugin/deletePlugin?id=" + id, HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));

	}

	@Autowired
	PluginDao pluginDao;

	@Test
	public void saveBinaryArray() throws Exception {
		String id = "" + System.currentTimeMillis();
		byte[] binary = IOUtils.toByteArray(new URL("https://github.com/qq275860560/dataxweb/blob/master/src/main/resources/static/mysqlreader.zip?raw=true"));

		log.info("" + binary.length);

		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("source", binary);
		pluginDao.savePlugin(map);

		Map<String, Object> map2 = pluginDao.getPlugin(id);
		byte[] source = (byte[]) map2.get("source");
 
		Assert.assertEquals(binary.length, source.length);
		File zipFile = new File(FileUtils.getTempDirectoryPath(), File.separator + "test-sources.zip");
		FileUtils.writeByteArrayToFile(zipFile, source);
		log.info(zipFile.getAbsolutePath());
		CompressUtil.unZip(zipFile);
		
		//工具类实现参考https://github.com/qq275860560/common/blob/master/src/main/java/com/github/qq275860560/common/util/CommandUtil.java 
		//CommandUtil.runComand("cd mysqlreader-master && mvn install");
		//
		pluginDao.deletePlugin(id);

	}

	 

	

	
}
