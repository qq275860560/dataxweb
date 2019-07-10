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

import com.github.qq275860560.dao.BuildDao;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author jiangyuanlin@163.com
 * 
 * @apiDefine BuildController 构建日志接口
 * @apiError {Object} data 返回数据
 * @apiError {String} msg 说明
 * @apiError {Integer} code 返回状态码,{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
 * @apiSuccess {Object} data 返回数据
 * @apiSuccess {String} msg 说明
 * @apiSuccess {Integer} code 返回状态码,{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
 */
@RestController
@Slf4j
public class BuildController {


  
	@Autowired
	private BuildDao buildDao;
 
 
 

	/**
	 * @api {POST} /api/build/pageBuild  分页搜索构建日志
	 * @apiGroup BuildController
	 * @apiName /api/build/pageBuild
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>分页搜索构建日志，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-构建日志管理</font></li></p>
	  
	 * @apiHeader {String} ContentType=application/x-www-form-urlencoded  请求类型
	 * @apiHeader {String} Accept=application/json;charset=UTF-8 响应类型
	 * @apiHeader {String} Authorization "Bearer "串接调用/login接口获取的令牌
	
	 * @apiHeaderExample {json} 请求头部示例: 
	 * { 
	 * 		"Content-Type":"application/x-www-form-urlencoded", 
	 *      "Accept":"application/json;charset=UTF-8",
	 *      "Authorization":"Bearer XXX" 
	 * }
	 * 
	 * @apiParam {String} name 任务名称
	 * @apiParam {Integer} pageNum 查询页码，从1开始计算
     * @apiParam {Integer} pageSize 每页展示的条数
	 * 
	 * 
	 * @apiParamExample {String} 请求参数示例:
	 * pageNum=1&pageSize=10
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/build/pageBuild?pageNum=1&pageSize=10' -H "Authorization:Bearer admin_token" 
	
	 * @apiSuccess (返回结果:) {Integer} code 状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
	 * @apiSuccess (返回结果:) {String} msg 提示信息
	 * @apiSuccess (返回结果:) {Object} data 返回对象
	 * @apiSuccess (data对象字段数据:) {Integer} total 记录总数，前端可以根据此值和pageSize，pageNum计算其他分页参数
	 * @apiSuccess (data对象字段数据:) {Object[]} pageList 数组
     * @apiSuccess (pageList数组每个对象字段数据:) {String} id Id
	 * @apiSuccess (pageList数组每个对象字段数据:) {String} name 名称
	 * 
	 * @apiSuccessExample {json} 成功返回: 
	 * {"code":200,"msg":"请求成功","data":{"total":100,"pageList":[{"id":"XXX","name":"XXX"}]}}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":400,"msg":"XXX参数不规范","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":401,"msg":"token已过期","data":null}
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":403,"msg":"用户无权限访问该接口","data":null}
	 *  
	 * @apiSampleRequest /api/build/pageBuild
	 *	 
	 */
	@RequestMapping(value = "/api/build/pageBuild")
	public Map<String, Object> pageBuild(
			@RequestParam Map<String, Object> requestMap
			)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);	
		
		String name=(String)requestMap.get("name");
		String jobName=(String)requestMap.get("jobName");
		String number=(String)requestMap.get("number");
	
		Integer status = requestMap.get("status") == null ? null
				: Integer.parseInt(requestMap.get("status").toString());
		Double progress = requestMap.get("progress") == null ? null
				: Double.parseDouble(requestMap.get("progress").toString());		
		Integer result = requestMap.get("result") == null ? null
				: Integer.parseInt(requestMap.get("result").toString());
		
		String createUserName=(String)requestMap.get("createUserName");
		String startCreateTime=(String)requestMap.get("startCreateTime");
		String endCreateTime=(String)requestMap.get("endCreateTime");
		Integer pageNum =requestMap.get("pageNum")==null?1:Integer.parseInt(requestMap.get("pageNum").toString());
		Integer pageSize =requestMap.get("pageSize")==null?10:Integer.parseInt(requestMap.get("pageSize").toString());
		 
		Map<String, Object> data = buildDao.pageBuild(
				null, name, null,jobName, number, 
				
				status, null, progress, null, result, 
				
				null, null,createUserName, startCreateTime, endCreateTime, pageNum, pageSize) ;
		return new HashMap<String, Object>() {
			{				 
				put("code", HttpStatus.OK.value());//此字段可以省略，这里仿照蚂蚁金服的接口返回字段code，增加状态码说明
				put("msg", "分页搜索成功");//此字段可以省略，这里仿照蚂蚁金服的接口返回字段msg，增加说明
				put("data", data);								
			}
		};
	}

	 
	

	/**
	 * @api {POST} /api/build/getBuild  获取构建日志详情
	 * @apiGroup BuildController
	 * @apiName /api/build/getBuild
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>获取构建日志详情，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-构建日志管理-编辑</font></li></p>
	 * <p><li><font color="red">数据交换组件-构建日志管理-详情</font></li></p>
	  
	 * @apiHeader {String} ContentType=application/x-www-form-urlencoded  请求类型
	 * @apiHeader {String} Accept=application/json;charset=UTF-8 响应类型
	 * @apiHeader {String} Authorization "Bearer "串接调用/login接口获取的令牌
	
	 * @apiHeaderExample {json} 请求头部示例: 
	 * { 
	 * 		"Content-Type":"application/x-www-form-urlencoded", 
	 *      "Accept":"application/json;charset=UTF-8",
	 *      "Authorization":"Bearer XXX" 
	 * }
	 * 
	 * @apiParam {String} id ID
	 * 
	 * 
	 * @apiParamExample {String} 请求参数示例:
	 * id=1
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/build/getBuild?id=1' -H "Authorization:Bearer admin_token" 
	
	 * @apiSuccess (返回结果:) {Integer} code 状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
	 * @apiSuccess (返回结果:) {String} msg 提示信息
	 * @apiSuccess (返回结果:) {Object} data 返回对象
	 * @apiSuccess (data对象字段数据:) {String} id Id
	 * @apiSuccess (data对象字段数据:) {String} name 名称
	 * 
	 * @apiSuccessExample {json} 成功返回: 
	 * {"code":200,"msg":"请求成功","data":{"id":"XXX","name":"XXX"}}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":400,"msg":"XXX参数不规范","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":401,"msg":"token已过期","data":null}
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":403,"msg":"用户无权限访问该接口","data":null}
	 *  
	 * @apiSampleRequest /api/build/getBuild
	 *	 
	 */
 	@RequestMapping(value = "/api/build/getBuild")
	public Map<String, Object> getBuild(@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		Map<String, Object> data=buildDao.getBuild(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "获取对象成功");
				put("data", data);
			}
		};
	}
	
	
  
	@RequestMapping(value = "/api/build/saveBuild")
	public Map<String, Object> saveBuild(@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);		
	 
		String id=UUID.randomUUID().toString().replace("-", "");
		requestMap.put("id", id);	
				
		String createUserName=currentLoginUsername;
		requestMap.put("createUserName", createUserName);
		String createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		requestMap.put("createTime", createTime);
		buildDao.saveBuild(requestMap);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "保存成功");
				put("data", null);
			}
		};
	}
	


	
 
	@RequestMapping(value = "/api/build/updateBuild")
	public Map<String, Object> updateBuild(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		Map<String, Object> map=buildDao.getBuild(id);
		map.putAll(requestMap);
		buildDao.updateBuild(map);		
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "更新成功");
				put("data", null);
			}
		};
	}
	
	 
 	@RequestMapping(value = "/api/build/deleteBuild")
	public Map<String, Object> deleteBuild(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		buildDao.deleteBuild(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "删除成功");
				put("data", null);
			}
		};
	}
	 
 	
}
