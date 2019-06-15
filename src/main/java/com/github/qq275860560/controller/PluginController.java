package com.github.qq275860560.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.qq275860560.common.util.CommandUtil;
import com.github.qq275860560.common.util.CompressUtil;
import com.github.qq275860560.common.util.ResponseUtil;
import com.github.qq275860560.dao.PluginDao;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangyuanlin@163.com
 *
 */
@RestController
@Slf4j
public class PluginController {



	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private PluginDao pluginDao;
 
 
	@Autowired
	private   ObjectMapper objectMapper ;

	/*  curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/plugin/pagePlugin?pageNum=1&pageSize=10" 
	*/
	@RequestMapping(value = "/api/github/qq275860560/plugin/pagePlugin")
	public Map<String, Object> pagePlugin(
			@RequestParam Map<String, Object> requestMap
			)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);	
		
		String name=(String)requestMap.get("name");
		Integer type=Integer.parseInt(requestMap.get("type").toString());
	
		String createUserName=(String)requestMap.get("createUserName");
		String startCreateTime=(String)requestMap.get("startCreateTime");
		String endCreateTime=(String)requestMap.get("endCreateTime");
		Integer pageNum =requestMap.get("pageNum")==null?1:Integer.parseInt(requestMap.get("pageNum").toString());
		Integer pageSize =requestMap.get("pageSize")==null?10:Integer.parseInt(requestMap.get("pageSize").toString());
		 
		Map<String, Object> data = pluginDao.pagePlugin(null, name, type, null,null, null,null, createUserName, startCreateTime, endCreateTime, pageNum, pageSize) ;
		return new HashMap<String, Object>() {
			{				 
				put("code", HttpStatus.OK.value());//此字段可以省略，这里仿照蚂蚁金服的接口返回字段code，增加状态码说明
				put("msg", "分页搜索成功");//此字段可以省略，这里仿照蚂蚁金服的接口返回字段msg，增加说明
				put("data", data);								
			}
		};
	}

	 
	

	/*  curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/plugin/getPlugin?id=1" 
	*/
 	@RequestMapping(value = "/api/github/qq275860560/plugin/getPlugin")
	public Map<String, Object> getPlugin(@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		Map<String, Object> data=pluginDao.getPlugin(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "获取对象成功");
				put("data", data);
			}
		};
	}
	
	
 
 	/* 
 	 * curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/plugin/savePlugin"  -F 'name=pluginName1' -F 'type=0' -F 'readme=@D:/workspace_git/github-qq275860560-dataxweb/src/main/resources/static/mysqlreader-README.md;type=application/octet-stream' -F 'source=@D:/workspace_git/github-qq275860560-dataxweb/src/main/resources/static/mysqlreader-source.zip;type=application/octet-stream' -F 'distribute=@D:/workspace_git/github-qq275860560-dataxweb/src/main/resources/static/mysqlreader-distribute.zip;type=application/octet-stream'
	*/
	@RequestMapping(value = "/api/github/qq275860560/plugin/savePlugin")
	public Map<String, Object> savePlugin(@RequestParam Map<String, Object> requestMap,@RequestParam("readme") MultipartFile readme,@RequestParam("source") MultipartFile source,@RequestParam("distribute") MultipartFile distribute)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);		
	 
		String id=UUID.randomUUID().toString().replace("-", "");
		requestMap.put("id", id);	
		requestMap.put("readme",readme.getBytes());
		// 解压验证源码是否能够编译
		File zipFile = new File(FileUtils.getTempDirectoryPath(), File.separator + requestMap.get("name")+"-source.zip");
		FileUtils.writeByteArrayToFile(zipFile, source.getBytes());
		//工具类实现参考https://github.com/qq275860560/common/blob/master/src/main/java/com/github/qq275860560/common/util/CompressUtil.java 
		File destDir=CompressUtil.unZip(zipFile);
		//工具类实现参考https://github.com/qq275860560/common/blob/master/src/main/java/com/github/qq275860560/common/util/CommandUtil.java 
		//String result=CommandUtil.runComand("mvn install", destDir);
		//log.info(result);
		requestMap.put("source",source.getBytes());
		
		requestMap.put("distribute",distribute.getBytes());
		String createUserName=currentLoginUsername;
		requestMap.put("createUserName", createUserName);
		String createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		requestMap.put("createTime", createTime);
		pluginDao.savePlugin(requestMap);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "保存成功");
				put("data", null);
			}
		};
	}
	
	 
	


	
	/*  curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/plugin/updatePlugin?id=2&name=pluginname2" 
	*/
	@RequestMapping(value = "/api/github/qq275860560/plugin/updatePlugin")
	public Map<String, Object> updatePlugin(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		Map<String, Object> map=pluginDao.getPlugin(id);
		map.putAll(requestMap);
		pluginDao.updatePlugin(map);		
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "更新成功");
				put("data", null);
			}
		};
	}
	
	/*  curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/plugin/deletePlugin?id=2" 
	*/
 	@RequestMapping(value = "/api/github/qq275860560/plugin/deletePlugin")
	public Map<String, Object> deletePlugin(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		pluginDao.deletePlugin(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "删除成功");
				put("data", null);
			}
		};
	}
	 
 	/*  curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/plugin/getPluginReadme?id=1" 
	*/
	@RequestMapping(value = "/api/github/qq275860560/plugin/getPluginReadme")
	public Map<String, Object> getPluginReadme(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		Map<String, Object> map = pluginDao.getPlugin(id);
		byte[] readme = (byte[]) map.get("readme");
		String data = new String(readme, "UTF-8");

		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "获取使用说明成功");
				put("data", data);
			}
		};
	}
 	
 	
 	    //下载插件源码接口 	 
 	 	/*  curl -i -X POST   "http://admin:123456@localhost:8080/api/github/qq275860560/plugin/getPluginSource?id=1" -o "/d/tmp/mysqlreader-source.zip" 
 		*/
	@RequestMapping(value = "/api/github/qq275860560/plugin/getPluginSource")
	public void getPluginSource(@RequestParam Map<String, Object> requestMap, HttpServletResponse response)
			throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		Map<String, Object> map = pluginDao.getPlugin(id);
		byte[] source = (byte[]) map.get("source");
		String fileName = (String) map.get("name") + "-source.zip";
		// 工具类实现参考https://github.com/qq275860560/common/blob/master/src/main/java/com/github/qq275860560/common/util/ResponseUtil.java
		ResponseUtil.sendFileByteArray(response, source, fileName, "application/octet-stream;charset=UTF-8");
	}
 	 
 	 	//下载插件发布包接口
 	 	/*  curl -i -X POST   "http://admin:123456@localhost:8080/api/github/qq275860560/plugin/getPluginDistribute?id=1" -o "/d/tmp/mysqlreader-distribute.zip" 
 		*/
 	 	@RequestMapping(value = "/api/github/qq275860560/plugin/getPluginDistribute")
 		public void getPluginDistribute(
 				@RequestParam Map<String, Object> requestMap,HttpServletResponse response)  throws Exception{
 			String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
 			log.info("当前登录用户=" + currentLoginUsername);
 			
 			String id=(String)requestMap.get("id");
 			Map<String, Object> map=pluginDao.getPlugin(id);
 			byte[] source = (byte[])map.get("distribute");
 			String fileName=(String)map.get("name")+"-distribute.zip";	
 			//工具类实现参考https://github.com/qq275860560/common/blob/master/src/main/java/com/github/qq275860560/common/util/ResponseUtil.java 
 			ResponseUtil.sendFileByteArray(response, source, fileName,"application/octet-stream;charset=UTF-8");
 	 	}


 
}
