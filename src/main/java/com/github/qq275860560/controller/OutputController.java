package com.github.qq275860560.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.qq275860560.dao.OutputDao;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangyuanlin@163.com
 *
 */
@RestController
@Slf4j
public class OutputController {


  
	@Autowired
	private OutputDao outputDao;
 
 

	/*  curl -i -X POST "http://admin:123456@localhost:8045/api/github/qq275860560/output/pageOutput?pageNum=1&pageSize=10" 
	*/
	@RequestMapping(value = "/api/github/qq275860560/output/pageOutput")
	public Map<String, Object> pageOutput(
			@RequestParam Map<String, Object> requestMap
			)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);	
		
		String name=(String)requestMap.get("name");
	
		String createUserName=(String)requestMap.get("createUserName");
		String startCreateTime=(String)requestMap.get("startCreateTime");
		String endCreateTime=(String)requestMap.get("endCreateTime");
		Integer pageNum =requestMap.get("pageNum")==null?1:Integer.parseInt(requestMap.get("pageNum").toString());
		Integer pageSize =requestMap.get("pageSize")==null?10:Integer.parseInt(requestMap.get("pageSize").toString());
		 
		Map<String, Object> data = outputDao.pageOutput(null,name, null, null, null, null, null, null, null, null, null, null, createUserName, startCreateTime, endCreateTime, pageNum, pageSize) ;
		return new HashMap<String, Object>() {
			{				 
				put("code", HttpStatus.OK.value());//此字段可以省略，这里仿照蚂蚁金服的接口返回字段code，增加状态码说明
				put("msg", "分页搜索成功");//此字段可以省略，这里仿照蚂蚁金服的接口返回字段msg，增加说明
				put("data", data);								
			}
		};
	}

	 
	

	/*  curl -i -X POST "http://admin:123456@localhost:8045/api/github/qq275860560/output/getOutput?id=1" 
	*/
 	@RequestMapping(value = "/api/github/qq275860560/output/getOutput")
	public Map<String, Object> getOutput(@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		Map<String, Object> data=outputDao.getOutput(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "获取对象成功");
				put("data", data);
			}
		};
	}
	
	
 
 	/*  
 	 * curl -i -X POST "http://admin:123456@localhost:8045/api/github/qq275860560/output/saveOutput?name=outputname1&writerId=&writerName=mysqlwriter&writerParameterUsername=root&writerParameterPassword=123456&writerParameterWriteMode=insert&writerParameterColumn=id,name&writerParameterPreSql=delete from test&writerParameterConnectionJdbcUrl=&writerParameterConnectionTable=test" 
	*/
	@RequestMapping(value = "/api/github/qq275860560/output/saveOutput")
	public Map<String, Object> saveOutput(@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);		
	 
		String id=UUID.randomUUID().toString().replace("-", "");
		requestMap.put("id", id);	
				
		String createUserName=currentLoginUsername;
		requestMap.put("createUserName", createUserName);
		String createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		requestMap.put("createTime", createTime);
		outputDao.saveOutput(requestMap);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "保存成功");
				put("data", null);
			}
		};
	}
	


	
	/*  curl -i -X POST "http://admin:123456@localhost:8045/api/github/qq275860560/output/updateOutput?id=2&name=outputname2" 
	*/
	@RequestMapping(value = "/api/github/qq275860560/output/updateOutput")
	public Map<String, Object> updateOutput(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		Map<String, Object> map=outputDao.getOutput(id);
		map.putAll(requestMap);
		outputDao.updateOutput(map);		
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "更新成功");
				put("data", null);
			}
		};
	}
	
	/*  curl -i -X POST "http://admin:123456@localhost:8045/api/github/qq275860560/output/deleteOutput?id=2" 
	*/
 	@RequestMapping(value = "/api/github/qq275860560/output/deleteOutput")
	public Map<String, Object> deleteOutput(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		outputDao.deleteOutput(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "删除成功");
				put("data", null);
			}
		};
	}
	 
 	
}
