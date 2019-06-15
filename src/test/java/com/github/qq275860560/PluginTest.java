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
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		File readme = new File(FileUtils.getTempDirectoryPath(), File.separator + "mysqlreader-README.md");
		FileUtils.copyURLToFile(new URL("https://github.com/qq275860560/dataxweb/blob/master/src/main/resources/static/mysqlreader-README.md?raw=true"), readme);;
	    param.add("readme", new FileSystemResource(readme));
	    
	    
		File source = new File(FileUtils.getTempDirectoryPath(), File.separator + "mysqlreader-source.zip");
		FileUtils.copyURLToFile(new URL("https://github.com/qq275860560/dataxweb/blob/master/src/main/resources/static/mysqlreader-source.zip?raw=true"), source);;
	    param.add("source", new FileSystemResource(source));
	    
		File distribute = new File(FileUtils.getTempDirectoryPath(), File.separator + "mysqlreader-distribute.zip");
		FileUtils.copyURLToFile(new URL("https://github.com/qq275860560/dataxweb/blob/master/src/main/resources/static/mysqlreader-distribute.zip?raw=true"), distribute);;
	    param.add("distribute", new FileSystemResource(distribute));
	    
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

		
		// getPluginReadme请求
		response = testRestTemplate.exchange("/api/github/qq275860560/plugin/getPluginReadme?id=" + id, HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertEquals(200, response.getBody().get("code"));		
		Assert.assertEquals(FileUtils.readFileToString(readme,"UTF-8"), (String)response.getBody().get("data"));
	
		
		// getPluginSource请求
		ResponseEntity<byte[]> response2 = testRestTemplate.exchange("/api/github/qq275860560/plugin/getPluginSource?id="+ id, HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), byte[].class);
		Assert.assertEquals(200, response2.getStatusCode().value());
		File source2 = new File(FileUtils.getTempDirectoryPath(), File.separator + "mysqlreader-source2.zip");
		FileUtils.writeByteArrayToFile(source2, response2.getBody());
		Assert.assertEquals(DigestUtils.md5Hex(FileUtils.readFileToByteArray(source)), DigestUtils.md5Hex(FileUtils.readFileToByteArray(source2)));
	
			
		// getPluginDistribute请求
		response2 = testRestTemplate.exchange("/api/github/qq275860560/plugin/getPluginDistribute?id="+ id, HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), byte[].class);
		Assert.assertEquals(200, response2.getStatusCode().value());
		File distribute2 = new File(FileUtils.getTempDirectoryPath(), File.separator + "mysqlreader-distribute2.zip");
		FileUtils.writeByteArrayToFile(distribute2, response2.getBody());
		Assert.assertEquals(DigestUtils.md5Hex(FileUtils.readFileToByteArray(distribute)), DigestUtils.md5Hex(FileUtils.readFileToByteArray(distribute2)));
	
				
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
		File zipFile = new File(FileUtils.getTempDirectoryPath(), File.separator + "mysqlreader.zip");
		FileUtils.writeByteArrayToFile(zipFile, source);
		log.info(zipFile.getAbsolutePath());
		File destDir=CompressUtil.unZip(zipFile);
		
		
		//工具类实现参考https://github.com/qq275860560/common/blob/master/src/main/java/com/github/qq275860560/common/util/CommandUtil.java 
		String result = CommandUtil.runComand( "mvn install",destDir);
		log.info(result);
		//
		pluginDao.deletePlugin(id);

	}
	
	

	 

	

	
}
