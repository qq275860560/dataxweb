package com.github.qq275860560;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
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
		response = testRestTemplate.exchange("/api/github/qq275860560/plugin/savePlugin?name={name}&type=0",
				HttpMethod.GET, new HttpEntity<>(new HttpHeaders() {
					{
						setBearerAuth(access_token);
					}
				}), Map.class, new HashMap<String, Object>() {
					{
						put("readerParameterConnectionJdbcUrl",
								"jdbc:mysql://127.0.0.1:3306/dataxweb?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
						put("name", name);
					}
				});
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
	public void save() throws Exception {
		String id = "" + System.currentTimeMillis();
		byte[] binary = IOUtils.toByteArray(new URL("https://codeload.github.com/apache/maven/zip/master"));

		log.info("" + binary.length);

		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("source", binary);
		pluginDao.savePlugin(map);

		Map<String, Object> map2 = pluginDao.getPlugin(id);
		byte[] source = (byte[]) map2.get("source");

		Assert.assertEquals(binary.length, source.length);
		File zipFile = new File(FileUtils.getTempDirectoryPath(), File.separator + "maven.zip");
		FileUtils.writeByteArrayToFile(zipFile, source);
		log.info(zipFile.getAbsolutePath());
		unZip(zipFile);
		pluginDao.deletePlugin(id);

	}

	 

	public static void unZip(File file) throws Exception {
		try (ZipFile zipFile = new ZipFile(file);) {
			Enumeration<?> entries = zipFile.getEntries();
			while (entries.hasMoreElements()) {
				ZipArchiveEntry entry = (ZipArchiveEntry) entries.nextElement();
				if (entry.isDirectory()) {
					new File(file.getParent(), entry.getName()).mkdirs();
				} else {
					File tmpFile = new File(file.getParent(), entry.getName());
					InputStream is = zipFile.getInputStream(entry);
					FileUtils.copyInputStreamToFile(is, tmpFile);
				}
			}
		}

	}
}
