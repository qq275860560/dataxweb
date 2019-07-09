package com.github.qq275860560.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.qq275860560.common.util.CommandUtil;
import com.github.qq275860560.common.util.JenkinsUtil;
import com.github.qq275860560.constant.Constant;
import com.github.qq275860560.dao.BuildDao;
import com.github.qq275860560.dao.InputDao;
import com.github.qq275860560.dao.JobDao;
import com.github.qq275860560.dao.OutputDao;
import com.github.qq275860560.dao.TransformerDao;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author jiangyuanlin@163.com
 * 
 * @apiDefine JobController 计划任务接口
 * @apiError {Object} data 返回数据
 * @apiError {String} msg 说明
 * @apiError {Integer} code 返回状态码,{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
 * @apiSuccess {Object} data 返回数据
 * @apiSuccess {String} msg 说明
 * @apiSuccess {Integer} code 返回状态码,{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
 */
@RestController
@Slf4j
public class JobController {

	@Value("${jenkinsUrl}")
	private String jenkinsUrl;
	@Autowired
	private RestTemplate restTemplate  ;
	@Autowired
	private JobDao jobDao;
	@Autowired
	private BuildDao buildDao;
	@Autowired
	private InputDao inputDao;
	@Autowired
	private OutputDao outputDao;
	@Autowired
	private TransformerDao transformerDao;
	
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * @api {POST} /api/github/qq275860560/job/checkJob  校验唯一性
	 * @apiGroup JobController
	 * @apiName checkJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>校验唯一性，成功code返回200 </p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-新建-校验合法性</font></li></p>
	 * <p><li><font color="red">数据交换组件-计划任务管理-编辑-校验合法性</font></li></p>
	  
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
	 * curl -i -X POST 'http://localhost:8045/api/github/qq275860560/job/checkJob' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/github/qq275860560/job/checkJob
	 *	 
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/checkJob")
	public Map<String, Object> checkJob(@RequestParam Map<String, Object> requestMap) throws Exception {
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
		boolean data = jobDao.checkJob(id, name);
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
	 * @api {POST} /api/github/qq275860560/job/pageJob  分页搜索计划任务
	 * @apiGroup JobController
	 * @apiName pageJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>分页搜索计划任务，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理</font></li></p>
	  
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
	 * curl -i -X POST 'http://localhost:8045/api/github/qq275860560/job/pageJob?pageNum=1&pageSize=10' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/github/qq275860560/job/pageJob
	 *	 
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/pageJob")
	public Map<String, Object> pageJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String name = (String) requestMap.get("name");
		String inputName = (String) requestMap.get("inputName");
		String readerName = (String) requestMap.get("readerName");
		String outputName = (String) requestMap.get("outputName");
		String writerName = (String) requestMap.get("writerName");
		Integer status = requestMap.get("status") == null ? null
				: Integer.parseInt(requestMap.get("status").toString());
		Double progress = requestMap.get("progress") == null ? null
				: Double.parseDouble(requestMap.get("progress").toString());
		String createUserName = (String) requestMap.get("createUserName");
		String startCreateTime = (String) requestMap.get("startCreateTime");
		String endCreateTime = (String) requestMap.get("endCreateTime");
		log.info("" + requestMap.get("pageNum"));
		Integer pageNum = requestMap.get("pageNum") == null ? 1
				: Integer.parseInt(requestMap.get("pageNum").toString());
		Integer pageSize = requestMap.get("pageSize") == null ? 10
				: Integer.parseInt(requestMap.get("pageSize").toString());

		Map<String, Object> data = jobDao.pageJob(null, name, null, inputName, null, outputName, null, readerName, null,
				writerName, null, status,null,null,null,null, progress, null, createUserName, startCreateTime, endCreateTime, pageNum,
				pageSize);

		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());// 此字段可以省略，这里仿照蚂蚁金服的接口返回字段code，增加状态码说明
				put("msg", "分页搜索成功");// 此字段可以省略，这里仿照蚂蚁金服的接口返回字段msg，增加说明
				put("data", data);
			}
		};
	}

	/**
	 * @api {POST} /api/github/qq275860560/job/getJob  获取计划任务详情
	 * @apiGroup JobController
	 * @apiName getJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>获取计划任务详情，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-编辑</font></li></p>
	 * <p><li><font color="red">数据交换组件-计划任务管理-详情</font></li></p>
	  
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
	 * curl -i -X POST 'http://localhost:8045/api/github/qq275860560/job/getJob?id=1' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/github/qq275860560/job/getJob
	 *	 
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/getJob")
	public Map<String, Object> getJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		Map<String, Object> data = jobDao.getJob(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "获取对象成功");
				put("data", data);
			}
		};
	}

	
	
	/**
	 * @api {POST} /api/github/qq275860560/job/saveJob  保存计划任务
	 * @apiGroup JobController
	 * @apiName saveJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>保存计划任务，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-新建-确定</font></li></p>

	  
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
	 * name=jobName1&inputId=&inputName=inputName1&readerId=&readerName=mysqlreader&outputId=1&outputName=outputName1&writerId=&writerName=mysqlwriter
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/github/qq275860560/job/saveJob?name=jobName1&inputId=&inputName=inputName1&readerId=&readerName=mysqlreader&outputId=1&outputName=outputName1&writerId=&writerName=mysqlwriter' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/github/qq275860560/job/saveJob
	 *	 
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/saveJob")
	public Map<String, Object> saveJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = UUID.randomUUID().toString().replace("-", "");
		requestMap.put("id", id);

		String inputId = (String) requestMap.get("inputId");
		Map<String, Object> inputMap = inputDao.getInput(inputId);
		requestMap.put("inputName", inputMap.get("name"));
		requestMap.put("readerId", inputMap.get("readerId"));
		requestMap.put("readerName", inputMap.get("readerName"));
		requestMap.put("readerParameterUsername", inputMap.get("readerParameterUsername"));
		requestMap.put("readerParameterPassword", inputMap.get("readerParameterPassword"));
		requestMap.put("readerParameterColumn", inputMap.get("readerParameterColumn"));
		requestMap.put("readerParameterWhere", inputMap.get("readerParameterWhere"));
		requestMap.put("readerParameterConnectionJdbcUrl", inputMap.get("readerParameterConnectionJdbcUrl"));
		requestMap.put("readerParameterConnectionTable", inputMap.get("readerParameterConnectionTable"));

		String outputId = (String) requestMap.get("outputId");
		Map<String, Object> outputMap = outputDao.getOutput(outputId);
		requestMap.put("outputName", outputMap.get("name"));
		requestMap.put("writerId", outputMap.get("writerId"));
		requestMap.put("writerName", outputMap.get("writerName"));
		requestMap.put("writerParameterUsername", outputMap.get("writerParameterUsername"));
		requestMap.put("writerParameterPassword", outputMap.get("writerParameterPassword"));
		requestMap.put("writerParameterWriteMode", outputMap.get("writerParameterWriteMode"));
		requestMap.put("writerParameterColumn", outputMap.get("writerParameterColumn"));
		requestMap.put("writerParameterPreSql", outputMap.get("writerParameterPreSql"));
		requestMap.put("writerParameterConnectionJdbcUrl", outputMap.get("writerParameterConnectionJdbcUrl"));
		requestMap.put("writerParameterConnectionTable", outputMap.get("writerParameterConnectionTable"));

		String transformerId = (String) requestMap.get("transformerId");
		Map<String, Object> transformerMap = transformerDao.getTransformer(transformerId);
		requestMap.put("transformerType", transformerMap.get("transformerType"));
		requestMap.put("transformerParameterCode", transformerMap.get("transformerParameterCode"));
		requestMap.put("transformerParameterExtraPackage", transformerMap.get("transformerParameterExtraPackage"));
		
		String dataxJson = objectMapper.writeValueAsString(generateDataxMap(requestMap));
		requestMap.put("dataxJson", dataxJson);

		Integer status = 1;
		requestMap.put("status", status);
		requestMap.put("nextBuildNumber", "1");
		Double progress = 0.0;
		requestMap.put("progress", progress);

		String createUserName = currentLoginUsername;
		requestMap.put("createUserName", createUserName);
		String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		requestMap.put("createTime", createTime);
		jobDao.saveJob(requestMap);
		
	    //TODO 这部分使用异步处理
		String name=(String)requestMap.get("name");
		File file = new File(Constant.DATAX_HOME + File.separator + "job" + File.separator + name + ".json");
		FileUtils.writeStringToFile(file, dataxJson, "UTF-8");

		String command = "python " + Constant.DATAX_HOME + File.separator + "bin" + File.separator + "datax.py "
				+ file.getAbsolutePath() ;		
		ResponseEntity<String> response = restTemplate.exchange(String.format("%s/createItem?name=%s", jenkinsUrl, name),
				HttpMethod.POST, new HttpEntity<>(JenkinsUtil.generateJenkinsJobXml(command), new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);
		log.info(response.getBody());
		//		
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "保存成功");
				put("data", null);
			}
		};
	}

	public static Map<String, Object> generateDataxMap(Map<String, Object> requestMap)
			throws IOException, JsonParseException, JsonMappingException {
		Map<String, Object> readerMap = generateReaderMap(requestMap);
		Map<String, Object> writerMap = generateWriterMap(requestMap);
		List<HashMap<String, Object>> transformerList = generateTransformerList(requestMap);
		Map<String, Object> dataxMap = new HashMap<String, Object>() {
			{
				put("job", new HashMap<String, Object>() {
					{
						put("setting", new HashMap<String, Object>() {
							{
								put("speed", new HashMap<String, Object>() {
									{
										put("channel", 3);
									}
								});
								put("errorLimit", new HashMap<String, Object>() {
									{
										put("record", 0);
										put("percentage", 0.02);
									}
								});

							}
						});
						put("content", new ArrayList<Map<String, Object>>() {
							{
								add(new HashMap<String, Object>() {
									{
										put("reader", readerMap);
										put("writer", writerMap);
										if(!transformerList.isEmpty()) {
											put("transformer", transformerList);
										}

									}
								});
							}
						});

					}
				});
			}
		};
		return dataxMap;
	}

	public static Map<String, Object> generateReaderMap(Map<String, Object> requestMap) {
		String name = (String) requestMap.get("readerName");
		if (name.equals("mysqlreader")) {
			return new HashMap<String, Object>() {
				{
					put("name", name);
					put("parameter", new HashMap<String, Object>() {
						{
							String username = (String) requestMap.get("readerParameterUsername");
							put("username", username);
							String password = (String) requestMap.get("readerParameterPassword");
							put("password", password);

							String[] column = ((String) requestMap.get("readerParameterColumn")).split(",");
							put("column", column);

							String where = (String) requestMap.get("readerParameterWhere");
							put("where", where);

							put("connection", new ArrayList<HashMap<String, Object>>() {
								{
									add(new HashMap<String, Object>() {
										{
											String[] jdbcUrl = ((String) requestMap
													.get("readerParameterConnectionJdbcUrl")).split(",");
											put("jdbcUrl", jdbcUrl);
											String[] table = ((String) requestMap.get("readerParameterConnectionTable"))
													.split(",");
											put("table", table);
										}
									});

								}
							});

						}
					});

				}
			};
		}
		return null;
	}

	public static Map<String, Object> generateWriterMap(Map<String, Object> requestMap)
			throws IOException, JsonParseException, JsonMappingException {
		String name = (String) requestMap.get("writerName");

		if (name.equals("mysqlwriter")) {
			return new HashMap<String, Object>() {
				{
					put("name", name);
					put("parameter", new HashMap<String, Object>() {
						{
							String username = (String) requestMap.get("writerParameterUsername");
							put("username", username);
							String password = (String) requestMap.get("writerParameterPassword");
							put("password", password);

							String writeMode = (String) requestMap.get("writerParameterWriteMode");
							put("writeMode", writeMode);

							String[] column = ((String) requestMap.get("writerParameterColumn")).split(",");
							put("column", column);

							String[] preSql = ((String) requestMap.get("writerParameterPreSql")).split(",");
							put("preSql", preSql);

							put("connection", new ArrayList<HashMap<String, Object>>() {
								{
									add(new HashMap<String, Object>() {
										{
											String jdbcUrl = (String) requestMap
													.get("writerParameterConnectionJdbcUrl");
											put("jdbcUrl", jdbcUrl);

											String[] table = ((String) requestMap.get("writerParameterConnectionTable"))
													.split(",");
											put("table", table);
										}
									});

								}
							});

						}
					});

				}
			};
		}
		return null;

	}

	
	
	public static List<HashMap<String, Object>> generateTransformerList(Map<String, Object> requestMap)
			throws IOException, JsonParseException, JsonMappingException {
		String transformerType = (String) requestMap.get("transformerType");
		
		if (  "dx_groovy".equals(transformerType) ) {			
			return new ArrayList<HashMap<String, Object>>() {
				{
					add(new HashMap<String, Object>() {
						{
							put("name", transformerType);
							put("parameter", new HashMap<String, Object>() {
								{
									String transformerParameterCode = (String) requestMap.get("transformerParameterCode");
									put("code", transformerParameterCode);
								 

									String[] column = ((String) requestMap.get("transformerParameterExtraPackage")).split("\n");
									put("extraPackage", column);

									 

								}
							});
						}
					});

				}
			};
		}
		return Collections.EMPTY_LIST;
					
				 
	}

	
	/**
	 * @api {POST} /api/github/qq275860560/job/updateJob  更新计划任务
	 * @apiGroup JobController
	 * @apiName updateJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>更新计划任务，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-编辑-确定</font></li></p>

	  
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
	 * id=2&name=jobName2
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/github/qq275860560/job/updateJob?id=2&name=jobName2' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/github/qq275860560/job/updateJob
	 *	 
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/updateJob")
	public Map<String, Object> updateJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		
		String id = (String) requestMap.get("id");
		Map<String, Object> map = jobDao.getJob(id);
		map.putAll(requestMap);
		
		String inputId = (String) map.get("inputId");
		Map<String, Object> inputMap = inputDao.getInput(inputId);
		map.put("inputName", inputMap.get("name"));
		map.put("readerId", inputMap.get("readerId"));
		map.put("readerName", inputMap.get("readerName"));
		map.put("readerParameterUsername", inputMap.get("readerParameterUsername"));
		map.put("readerParameterPassword", inputMap.get("readerParameterPassword"));
		map.put("readerParameterColumn", inputMap.get("readerParameterColumn"));
		map.put("readerParameterWhere", inputMap.get("readerParameterWhere"));
		map.put("readerParameterConnectionJdbcUrl", inputMap.get("readerParameterConnectionJdbcUrl"));
		map.put("readerParameterConnectionTable", inputMap.get("readerParameterConnectionTable"));

		String outputId = (String) map.get("outputId");
		Map<String, Object> outputMap = outputDao.getOutput(outputId);
		map.put("outputName", outputMap.get("name"));
		map.put("writerId", outputMap.get("writerId"));
		map.put("writerName", outputMap.get("writerName"));
		map.put("writerParameterUsername", outputMap.get("writerParameterUsername"));
		map.put("writerParameterPassword", outputMap.get("writerParameterPassword"));
		map.put("writerParameterWriteMode", outputMap.get("writerParameterWriteMode"));
		map.put("writerParameterColumn", outputMap.get("writerParameterColumn"));
		map.put("writerParameterPreSql", outputMap.get("writerParameterPreSql"));
		map.put("writerParameterConnectionJdbcUrl", outputMap.get("writerParameterConnectionJdbcUrl"));
		map.put("writerParameterConnectionTable", outputMap.get("writerParameterConnectionTable"));

		String dataxJson = objectMapper.writeValueAsString(generateDataxMap(map));
		map.put("dataxJson", dataxJson);
 
		jobDao.updateJob(map);
		
		//TODO 这部分使用异步处理
		 //不能对任务名称继续更新		
		String name=(String)map.get("name");
		File file = new File(Constant.DATAX_HOME + File.separator + "job" + File.separator + name + ".json");
		FileUtils.writeStringToFile(file, dataxJson, "UTF-8");
		//
		
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "更新成功");
				put("data", null);
			}
		};
	}

	/**
	 * @api {POST} /api/github/qq275860560/job/deleteJob  删除计划任务
	 * @apiGroup JobController
	 * @apiName deleteJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>删除计划任务，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-删除</font></li></p>
		  
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
	 * curl -i -X POST 'http://localhost:8045/api/github/qq275860560/job/deleteJob?id=1' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/github/qq275860560/job/deleteJob
	 *	 
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/deleteJob")
	public Map<String, Object> deleteJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		//
		try {//TODO 这部分使用异步处理
		Map<String, Object> map = jobDao.getJob(id);
		String name=(String)map.get("name");
		ResponseEntity<String> response = restTemplate.exchange(String.format("%s/job/%s/doDelete", jenkinsUrl, name), HttpMethod.POST,
				new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);
		log.info(response.getBody());
		}catch (Exception e) {
			log.error("",e);
		}
		//
		jobDao.deleteJob(id);		
		buildDao.deleteBuildByJobId(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "删除成功");
				put("data", null);
			}
		};
	}

	/**
	 * @api {POST} /api/github/qq275860560/job/runJob  运行计划任务
	 * @apiGroup JobController
	 * @apiName runJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>运行计划任务，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-运行</font></li></p>
		  
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
	 * curl -i -X POST 'http://localhost:8045/api/github/qq275860560/job/runJob?id=1' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/github/qq275860560/job/runJob
	 *	 
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/runJob")
	public Map<String, Object> runJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		Map<String, Object> map = jobDao.getJob(id);
		String name = (String) map.get("name");

		Integer status = (Integer) map.get("status");
		if (status == 0) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "构建失败,任务禁用中");
					put("data", null);
				}
			};
		}
		
		//todo 正在构建中不允许发送第二次请求
		// String result = runJob(id);
		ResponseEntity<String> response = restTemplate.exchange(String.format("%s/job/%s/build", jenkinsUrl, name),
				HttpMethod.POST, new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);
		String currentBuildNumber = (String) map.get("nextBuildNumber");

		// 不断的更新mysql		
				while (true) {
				try {
					
						// 获取job的最后一次构建状态
						ResponseEntity<Map> response3 = restTemplate.exchange(
								String.format("%s/job/%s/api/json?pretty=true", jenkinsUrl, name), HttpMethod.GET,
								new HttpEntity<>(new HttpHeaders() {
									{
										set("Content-Type", "text/xml; charset=UTF-8");

									}
								}), Map.class);

						Map<String, Object> responseMap = (Map<String, Object>) response3.getBody();
						if (responseMap.get("lastBuild")== null) {
							continue;
						}
							Integer number = (Integer) ((Map<String, Object>) responseMap.get("lastBuild")).get("number");

							log.info("最后一次构建编号" + number);
							log.info("最后一次成功构建编号", responseMap.get("lastSuccessfulBuild") == null ? null
									: ((Map<String, Object>) responseMap.get("lastSuccessfulBuild")).get("number"));
							log.info("最后一次失败构建编号", responseMap.get("lastUnsuccessfulBuild") == null ? null
									: ((Map<String, Object>) responseMap.get("lastUnsuccessfulBuild")).get("number"));
							Map<String, Object> map2 = jobDao.getJob(id);
							map2.put("number", number);
							map2.put("lastSuccessfulBuild", responseMap.get("lastSuccessfulBuild") == null ? null
									: ((Map<String, Object>) responseMap.get("lastSuccessfulBuild")).get("number"));
							map2.put("lastUnsuccessfulBuild", responseMap.get("lastUnsuccessfulBuild") == null ? null
									: ((Map<String, Object>) responseMap.get("lastUnsuccessfulBuild")).get("number"));
							map2.put("nextBuildNumber", responseMap.get("nextBuildNumber"));
							jobDao.updateJob(map2);

							// getBuild-1请求
							response3 = restTemplate.exchange(
									String.format("%s/job/%s/%s/api/json?pretty=true", jenkinsUrl, name, currentBuildNumber),
									HttpMethod.GET, new HttpEntity<>(new HttpHeaders() {
										{
											set("Content-Type", "text/xml; charset=UTF-8");

										}
									}), Map.class);
							responseMap = (Map<String, Object>) response3.getBody();
							log.info("是否构建中" + (boolean) responseMap.get("building"));
							log.info("预期构建时长" + (Integer) responseMap.get("estimatedDuration"));
							log.info("实际构建时长" + (Integer) responseMap.get("duration"));
							log.info("构建结果" + (String) responseMap.get("result"));

							// getBuild-2请求
							ResponseEntity<String> response4 = restTemplate.exchange(
									String.format("%s/job/%s/%s/consoleText", jenkinsUrl, name, currentBuildNumber), HttpMethod.GET,
									new HttpEntity<>(new HttpHeaders() {
										{
											set("Content-Type", "text/xml; charset=UTF-8");

										}
									}), String.class);
							String consoleText = response4.getBody();
							log.info("控制台日志=" + consoleText);

							Map<String, Object> buildMap = buildDao.getBuildByJobNameAndNumber(name, currentBuildNumber);
							if (buildMap.isEmpty()) {
								buildMap.put("id", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
								buildDao.saveBuild(buildMap);
							}
							buildMap.put("jobId", id);
							buildMap.put("jobName", name);
							buildMap.put("number", currentBuildNumber);
							boolean building = (boolean) responseMap.get("building");
							buildMap.put("building", building == false ? 0 : 1);
							buildMap.put("estimatedDuration", (Integer) responseMap.get("estimatedDuration"));
							buildMap.put("duration", (Integer) responseMap.get("duration"));
							String result = (String) responseMap.get("result");
							if(result==null) {
								buildMap.put("result", null);
							}else if(result.equals("SUCCESS")) {
									buildMap.put("result", 1);
							}else if(result.equals("FAILURE")) {
								buildMap.put("result", 2);
							}else if(result.equals("ABORTED")) {
								buildMap.put("result", 3);
						    }
							buildMap.put("consoleText", consoleText);
							buildDao.updateBuild(buildMap);

							if (building == false && responseMap.get("result") != null) {
								break;
							}

						
						
				} catch (Exception e) {
					log.error("", e);
					continue;
				}
				try{Thread.sleep(5000);}catch (Exception e) {
					// TODO: handle exception
				}
					}
				
				
		 

		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "构建成功");
				put("data", null);
			}
		};
	}

	

	/*
	 * 
	 * 把接口改在build模块
	 * curl -i -X POST
	 * "http://admin:123456@localhost:8045/api/github/qq275860560/job/getJobProgress?id=1"
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/getJobProgress")
	public Map<String, Object> getJobProgress(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		Double data = (Double) jobDao.getJob(id).get("progress");
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "获取进度成功");
				put("data", data);
			}
		};
	}

	/*把接口改在build模块
	 * curl -i -X POST
	 * "http://admin:123456@localhost:8045/api/github/qq275860560/job/stopJob?id=1&number=1"
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/stopJob")
	public Map<String, Object> stopJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id = (String) requestMap.get("id");
		String number = (String) requestMap.get("number");
		Map<String, Object> map = jobDao.getJob(id);
		String name = (String) map.get("name");

		Integer status = (Integer) map.get("status");
		if (status == 0) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "停止失败,任务禁用中");
					put("data", null);
				}
			};
		}
		
		
		ResponseEntity<Map> response3  = restTemplate.exchange(
				String.format("%s/job/%s/%s/api/json?pretty=true", jenkinsUrl, name, number),
				HttpMethod.GET, new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), Map.class);
		Map<String, Object> responseMap = (Map<String, Object>) response3.getBody();
		log.info("是否构建中" + (boolean) responseMap.get("building"));
		if((boolean) responseMap.get("building")==false) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "任务已经构建完毕，无法停止");
					put("data", null);
				}
			};
		}
		//log.info("预期构建时长" + (Integer) responseMap.get("estimatedDuration"));
		//log.info("实际构建时长" + (Integer) responseMap.get("duration"));
		//log.info("构建结果" + (String) responseMap.get("result"));
		
		
		//停止
		ResponseEntity<String> response4  =  restTemplate.exchange(String.format("%s/job/%s/%s/stop", jenkinsUrl, name,number),
				HttpMethod.POST, new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);	
		log.info(""+response4.getStatusCode());;

		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "停止成功");
				put("data", null);
			}
		};
	}

	/**
	 * @api {POST} /api/github/qq275860560/job/enableJob  启用计划任务
	 * @apiGroup JobController
	 * @apiName enableJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>启用计划任务，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-启用</font></li></p>
		  
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
	 * curl -i -X POST 'http://localhost:8045/api/github/qq275860560/job/enableJob?id=1' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/github/qq275860560/job/enableJob
	 *	 
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/enableJob")
	public Map<String, Object> enableJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		Map<String, Object> map = jobDao.getJob(id);
		
		Integer status = (Integer) map.get("status");
		if (status == 1) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "启用失败,任务启用中");
					put("data", null);
				}
			};
		}
		
		map.put("status", 1);
		jobDao.updateJob(map);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "启用成功");
				put("data", null);
			}
		};
	}

	/**
	 * @api {POST} /api/github/qq275860560/job/disableJob  停用计划任务
	 * @apiGroup JobController
	 * @apiName disableJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>停用计划任务，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-停用</font></li></p>
		  
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
	 * curl -i -X POST 'http://localhost:8045/api/github/qq275860560/job/disableJob?id=1' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/github/qq275860560/job/disableJob
	 *	 
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/disableJob")
	public Map<String, Object> disableJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		Map<String, Object> map = jobDao.getJob(id);
		
		Integer status = (Integer) map.get("status");
		if (status == 0) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "禁用失败,任务禁用中");
					put("data", null);
				}
			};
		}
		
		map.put("status", 0);
		jobDao.updateJob(map);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "停用成功");
				put("data", null);
			}
		};
	}

}
