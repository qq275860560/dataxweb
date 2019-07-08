package com.github.qq275860560.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.qq275860560.dao.TransformerDao;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangyuanlin@163.com
 *
 */
@RestController
@Slf4j
public class TransformerController {


  
	@Autowired
	private TransformerDao transformerDao;
 
 
 

	/*  curl -i -X POST "http://admin:123456@localhost:8045/api/github/qq275860560/transformer/pageTransformer?pageNum=1&pageSize=10" 
	*/
	@RequestMapping(value = "/api/github/qq275860560/transformer/pageTransformer")
	public Map<String, Object> pageTransformer(
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
		 
		Map<String, Object> data = transformerDao.pageTransformer(null, name, null, null, null,null, createUserName, startCreateTime, endCreateTime, pageNum, pageSize) ;
		return new HashMap<String, Object>() {
			{				 
				put("code", HttpStatus.OK.value());//此字段可以省略，这里仿照蚂蚁金服的接口返回字段code，增加状态码说明
				put("msg", "分页搜索成功");//此字段可以省略，这里仿照蚂蚁金服的接口返回字段msg，增加说明
				put("data", data);								
			}
		};
	}

	 
	

	/*  curl -i -X POST "http://admin:123456@localhost:8045/api/github/qq275860560/transformer/getTransformer?id=1" 
	*/
 	@RequestMapping(value = "/api/github/qq275860560/transformer/getTransformer")
	public Map<String, Object> getTransformer(@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		Map<String, Object> data=transformerDao.getTransformer(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "获取对象成功");
				put("data", data);
			}
		};
	}
	
	
 
 	/*  curl -i -X POST "http://admin:123456@localhost:8045/api/github/qq275860560/transformer/saveTransformer?name=dx_groovy&transformerParameterCode=Column column = record.getColumn(1);\nString oriValue = column.asString();\nString newValue = oriValue.substring(0, 3);\nrecord.setColumn(1, new StringColumn(newValue));\nreturn record;&transformerParameterExtraPackage=mysqlreader" 
	*/
	@RequestMapping(value = "/api/github/qq275860560/transformer/saveTransformer")
	public Map<String, Object> saveTransformer(@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);		
	 
		String id=UUID.randomUUID().toString().replace("-", "");
		requestMap.put("id", id);	
		
		String name = (String) requestMap.get("name");
		if (StringUtils.isEmpty(name)) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "名称不能为空");
					put("data", null);
				}
			};
		}
		
		String createUserName=currentLoginUsername;
		requestMap.put("createUserName", createUserName);
		String createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		requestMap.put("createTime", createTime);
		transformerDao.saveTransformer(requestMap);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "保存成功");
				put("data", null);
			}
		};
	}
	


	
	/*  curl -i -X POST "http://admin:123456@localhost:8045/api/github/qq275860560/transformer/updateTransformer?id=2&name=transformername2" 
	*/
	@RequestMapping(value = "/api/github/qq275860560/transformer/updateTransformer")
	public Map<String, Object> updateTransformer(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		Map<String, Object> map=transformerDao.getTransformer(id);
		map.putAll(requestMap);
		transformerDao.updateTransformer(map);		
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "更新成功");
				put("data", null);
			}
		};
	}
	
	/*  curl -i -X POST "http://admin:123456@localhost:8045/api/github/qq275860560/transformer/deleteTransformer?id=2" 
	*/
 	@RequestMapping(value = "/api/github/qq275860560/transformer/deleteTransformer")
	public Map<String, Object> deleteTransformer(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		transformerDao.deleteTransformer(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "删除成功");
				put("data", null);
			}
		};
	}
	 
 	
}
