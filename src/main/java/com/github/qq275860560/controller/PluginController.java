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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.github.qq275860560.common.util.CommandUtil;
import com.github.qq275860560.common.util.CompressUtil;
import com.github.qq275860560.common.util.ResponseUtil;
import com.github.qq275860560.dao.PluginDao;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author jiangyuanlin@163.com
 * 
 * @apiDefine PluginController 插件接口
 * @apiError {Object} data 返回数据
 * @apiError {String} msg 说明
 * @apiError {Integer} code 返回状态码,{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
 * @apiSuccess {Object} data 返回数据
 * @apiSuccess {String} msg 说明
 * @apiSuccess {Integer} code 返回状态码,{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
 */
@RestController
@Slf4j
public class PluginController {



	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private PluginDao pluginDao;
 
 
 
	/**
	 * @api {POST} /api/plugin/checkPlugin  校验唯一性
	 * @apiGroup PluginController
	 * @apiName /api/plugin/checkPlugin
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>校验唯一性，成功code返回200 </p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-插件管理-新建-校验合法性</font></li></p>
	 * <p><li><font color="red">数据交换组件-插件管理-编辑-校验合法性</font></li></p>
	  
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
	 * @apiParam {String} id ID,新建时为空，编辑时必填，
	 * @apiParam {String} name 要校验唯一性的名称，必填
	 * 
	 * @apiParamExample {String} 请求参数示例:
	 * id=2&name=name2
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/plugin/checkPlugin' -H "Authorization:Bearer admin_token" 
	
	 * @apiSuccess (返回结果:) {Integer} code 状态码:{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
	 * @apiSuccess (返回结果:) {String} msg 提示信息
	 * @apiSuccess (返回结果:) {Boolean} data 校验结果{true:合法,false:不合法}		
	 * 
	 * @apiSuccessExample {json} 成功返回(校验成功时): 
	 * {"code":200,"msg":"名称有效","data":true}
	 * @apiSuccessExample {json} 成功返回(校验失败时):
	 * {"code":200,"msg":"名称已存在","data":false}	 
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":400,"msg":"名称必填","data":null}
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":401,"msg":"token已过期","data":null}
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":403,"msg":"用户无权限访问该接口","data":null}
	 *  
	 * @apiSampleRequest /api/plugin/checkPlugin
	 *	 
	 */
	@RequestMapping(value = "/api/plugin/checkPlugin")
	public Map<String, Object> checkPlugin(@RequestParam Map<String, Object> requestMap) throws Exception {
		String id = (String) requestMap.get("id");
		String name = (String) requestMap.get("name");
		if (StringUtils.isEmpty(name)) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "名称必填");
					put("data", null);
				}
			};
		}
		boolean data = pluginDao.checkPlugin(id, name);
		String msg = data == true ? "名称有效" : "名称已存在";
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", msg);
				put("data", data);
			}
		};

	}

	/**
	 * @api {POST} /api/plugin/pagePlugin  分页搜索插件
	 * @apiGroup PluginController
	 * @apiName /api/plugin/pagePlugin
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>分页搜索插件，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-插件管理</font></li></p>
	  
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
	 * @apiParam {String} name 名称
	 * @apiParam {Integer} pageNum 查询页码，从1开始计算
     * @apiParam {Integer} pageSize 每页展示的条数
	 * 
	 * 
	 * @apiParamExample {String} 请求参数示例:
	 * pageNum=1&pageSize=10
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/plugin/pagePlugin?pageNum=1&pageSize=10' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/plugin/pagePlugin
	 *	 
	 */
	@RequestMapping(value = "/api/plugin/pagePlugin")
	public Map<String, Object> pagePlugin(
			@RequestParam Map<String, Object> requestMap
			)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);	
		
		String name=(String)requestMap.get("name");
		Integer type=requestMap.get("type")==null?null:Integer.parseInt(requestMap.get("type").toString());
	
		String createUserName=(String)requestMap.get("createUserName");
		String startCreateTime=(String)requestMap.get("startCreateTime");
		String endCreateTime=(String)requestMap.get("endCreateTime");
		Integer pageNum =requestMap.get("pageNum")==null?1:Integer.parseInt(requestMap.get("pageNum").toString());
		Integer pageSize =requestMap.get("pageSize")==null?10:Integer.parseInt(requestMap.get("pageSize").toString());
		 
		Map<String, Object> data = pluginDao.pagePlugin(null, name, type ,null, createUserName, startCreateTime, endCreateTime, pageNum, pageSize) ;
		return new HashMap<String, Object>() {
			{				 
				put("code", HttpStatus.OK.value());//此字段可以省略，这里仿照蚂蚁金服的接口返回字段code，增加状态码说明
				put("msg", "分页搜索成功");//此字段可以省略，这里仿照蚂蚁金服的接口返回字段msg，增加说明
				put("data", data);								
			}
		};
	}

	 
	

	/**
	 * @api {POST} /api/plugin/getPlugin  获取插件详情
	 * @apiGroup PluginController
	 * @apiName /api/plugin/getPlugin
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>获取插件详情，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-插件管理-编辑</font></li></p>
	 * <p><li><font color="red">数据交换组件-插件管理-详情</font></li></p>
	  
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
	 * curl -i -X POST 'http://localhost:8045/api/plugin/getPlugin?id=1' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/plugin/getPlugin
	 *	 
	 */
 	@RequestMapping(value = "/api/plugin/getPlugin")
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
	
	
 
 	/**
	 * @api {POST} /api/plugin/savePlugin  保存插件
	 * @apiGroup PluginController
	 * @apiName /api/plugin/savePlugin
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>保存插件，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-插件管理-新建-确定</font></li></p>

	  
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
	 * @apiParam {String} name 名称
	 * 
	 * 
	 * @apiParamExample {String} 请求参数示例:
	 * name=pluginName1
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/plugin/savePlugin?name=pluginName1' -H "Authorization:Bearer admin_token" 
	
	 * @apiSuccess (返回结果:) {Integer} code 状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
	 * @apiSuccess (返回结果:) {String} msg 提示信息
	 * @apiSuccess (返回结果:) {Object} data null
	 * 
	 * @apiSuccessExample {json} 成功返回: 
	 * {"code":200,"msg":"请求成功","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":400,"msg":"XXX参数不规范","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":401,"msg":"token已过期","data":null}
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":403,"msg":"用户无权限访问该接口","data":null}
	 *  
	 * @apiSampleRequest /api/plugin/savePlugin
	 *	 
	 */
	@RequestMapping(value = "/api/plugin/savePlugin")
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
		String result=CommandUtil.runComand("mvn install", destDir);
		log.info(result);
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
	
	 
	


	
	/**
	 * @api {POST} /api/plugin/updatePlugin  更新插件
	 * @apiGroup PluginController
	 * @apiName /api/plugin/updatePlugin
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>更新插件，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-插件 管理-编辑-确定</font></li></p>

	  
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
	 * @apiParam {String} name 名称
	 * 
	 * 
	 * @apiParamExample {String} 请求参数示例:
	 * id=2&name=pluginName2
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/plugin/updatePlugin?id=2&name=pluginName2' -H "Authorization:Bearer admin_token" 
	
	 * @apiSuccess (返回结果:) {Integer} code 状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
	 * @apiSuccess (返回结果:) {String} msg 提示信息
	 * @apiSuccess (返回结果:) {Object} data null
	 * 
	 * @apiSuccessExample {json} 成功返回: 
	 * {"code":200,"msg":"请求成功","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":400,"msg":"XXX参数不规范","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":401,"msg":"token已过期","data":null}
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":403,"msg":"用户无权限访问该接口","data":null}
	 *  
	 * @apiSampleRequest /api/plugin/updatePlugin
	 *	 
	 */
	@RequestMapping(value = "/api/plugin/updatePlugin")
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
	
	/**
	 * @api {POST} /api/plugin/deletePlugin 删除插件
	 * @apiGroup PluginController
	 * @apiName /api/plugin/deletePlugin
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>删除插件，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-插件管理-删除</font></li></p>
		  
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
	 * curl -i -X POST 'http://localhost:8045/api/plugin/deletePlugin?id=1' -H "Authorization:Bearer admin_token" 
	
	 * @apiSuccess (返回结果:) {Integer} code 状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
	 * @apiSuccess (返回结果:) {String} msg 提示信息
	 * @apiSuccess (返回结果:) {Object} data null

	 * 
	 * @apiSuccessExample {json} 成功返回: 
	 * {"code":200,"msg":"请求成功","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":400,"msg":"XXX参数不规范","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":401,"msg":"token已过期","data":null}
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":403,"msg":"用户无权限访问该接口","data":null}
	 *  
	 * @apiSampleRequest /api/plugin/deletePlugin
	 *	 
	 */
 	@RequestMapping(value = "/api/plugin/deletePlugin")
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
	 
 	/*  curl -i -X POST "http://admin:123456@localhost:8045/api/plugin/getPluginReadme?id=1" 
	*/
	@RequestMapping(value = "/api/plugin/getPluginReadme")
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
 	 	/*  curl -i -X POST   "http://admin:123456@localhost:8045/api/plugin/getPluginSource?id=1" -o "/tmp/mysqlreader-source.zip" 
 		*/
	@RequestMapping(value = "/api/plugin/getPluginSource")
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
 	 	/*  curl -i -X POST   "http://admin:123456@localhost:8045/api/plugin/getPluginDistribute?id=1" -o "/tmp/mysqlreader-distribute.zip" 
 		*/
 	 	@RequestMapping(value = "/api/plugin/getPluginDistribute")
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
