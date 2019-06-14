package com.github.qq275860560.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.qq275860560.constant.Constant;
import com.github.qq275860560.dao.InputDao;
import com.github.qq275860560.dao.JobDao;
import com.github.qq275860560.dao.OutputDao;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangyuanlin@163.com
 *
 */
@RestController
@Slf4j
public class JobController {


 
	@Autowired
	private JobDao jobDao;
	@Autowired
	private InputDao inputDao;
	@Autowired
	private OutputDao outputDao;
 
	@Autowired
	private   ObjectMapper objectMapper ;

	/*  curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/job/pageJob?pageNum=1&pageSize=10" 
	*/
	@RequestMapping(value = "/api/github/qq275860560/job/pageJob")
	public Map<String, Object> pageJob(
			@RequestParam Map<String, Object> requestMap
			)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);	
		
		String name=(String)requestMap.get("name");
		String inputName=(String)requestMap.get("inputName");
		String readerName=(String)requestMap.get("readerName");
		String outputName=(String)requestMap.get("outputName");
		String writerName=(String)requestMap.get("writerName");
		Integer status=(Integer)requestMap.get("status");
		Double progress=(Double)requestMap.get("progress");
		String createUserName=(String)requestMap.get("createUserName");
		String startCreateTime=(String)requestMap.get("startCreateTime");
		String endCreateTime=(String)requestMap.get("endCreateTime");
		Integer pageNum =requestMap.get("pageNum")==null?1:(Integer)requestMap.get("pageNum");
		Integer pageSize =requestMap.get("pageSize")==null?10:(Integer)requestMap.get("pageSize");
		 
		Map<String, Object> data = jobDao.pageJob(null, name, null, inputName, null, outputName, null, outputName, null, writerName, null, status, progress, null, createUserName, startCreateTime, endCreateTime, pageNum, pageSize);
		
		return new HashMap<String, Object>() {
			{				 
				put("code", HttpStatus.OK.value());//此字段可以省略，这里仿照蚂蚁金服的接口返回字段code，增加状态码说明
				put("msg", "分页搜索成功");//此字段可以省略，这里仿照蚂蚁金服的接口返回字段msg，增加说明
				put("data", data);								
			}
		};
	}

	 
	

	/*  curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/job/getJob?id=1" 
	*/
 	@RequestMapping(value = "/api/github/qq275860560/job/getJob")
	public Map<String, Object> getJob(@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		Map<String, Object> data=jobDao.getJob(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "获取对象成功");
				put("data", data);
			}
		};
	}
	
	
 	/* curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/job/saveJob?name=jobName1&inputId=&inputName=inputName1&readerId=&readerName=mysqlreader&outputId=1&outputName=outputName1&writerId=&writerName=mysqlwriter" 
	*/
	@RequestMapping(value = "/api/github/qq275860560/job/saveJob")
	public Map<String, Object> saveJob(@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);		
	 
		String id=UUID.randomUUID().toString().replace("-", "");
		requestMap.put("id", id);	
		
		
		String inputId=(String)requestMap.get("inputId");
		Map<String, Object> inputMap = inputDao.getInput(inputId);
		requestMap.put("inputName",inputMap.get("name"));
		requestMap.put("readerId",inputMap.get("readerId"));
		requestMap.put("readerName",inputMap.get("readerName"));
		requestMap.put("readerParameterUsername",inputMap.get("readerParameterUsername"));
		requestMap.put("readerParameterPassword",inputMap.get("readerParameterPassword"));
		requestMap.put("readerParameterColumn",inputMap.get("readerParameterColumn"));
		requestMap.put("readerParameterWhere",inputMap.get("readerParameterWhere"));
		requestMap.put("readerParameterConnectionJdbcUrl",inputMap.get("readerParameterConnectionJdbcUrl"));
		requestMap.put("readerParameterConnectionTable",inputMap.get("readerParameterConnectionTable"));
			
		String outputId=(String)requestMap.get("outputId");
		Map<String, Object> outputMap = outputDao.getOutput(outputId);
		requestMap.put("outputName",outputMap.get("name"));		
		requestMap.put("writerId",outputMap.get("writerId"));
		requestMap.put("writerName",outputMap.get("writerName"));
		requestMap.put("writerParameterUsername",outputMap.get("writerParameterUsername"));
		requestMap.put("writerParameterPassword",outputMap.get("writerParameterPassword"));
		requestMap.put("writerParameterWriteMode",outputMap.get("writerParameterWriteMode"));
		requestMap.put("writerParameterColumn",outputMap.get("writerParameterColumn"));
		requestMap.put("writerParameterPreSql",outputMap.get("writerParameterPreSql"));
		requestMap.put("writerParameterConnectionJdbcUrl",outputMap.get("writerParameterConnectionJdbcUrl"));
		requestMap.put("writerParameterConnectionTable",outputMap.get("writerParameterConnectionTable"));
		
		String dataxJson = objectMapper.writeValueAsString(generateDataxMap(requestMap));
		requestMap.put("dataxJson", dataxJson);
		
		Integer status=1;
		requestMap.put("status",status);
		Double progress=0.0;
		requestMap.put("progress", progress);
		
		String createUserName=currentLoginUsername;
		requestMap.put("createUserName", createUserName);
		String createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		requestMap.put("createTime", createTime);
		jobDao.saveJob(requestMap);
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
											String[] table = ((String) requestMap
													.get("readerParameterConnectionTable")).split(",");
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

											String[] table = ((String) requestMap
													.get("writerParameterConnectionTable")).split(",");
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

	
	/*  curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/job/updateJob?id=2&name=jobname2" 
	*/
	@RequestMapping(value = "/api/github/qq275860560/job/updateJob")
	public Map<String, Object> updateJob(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		Map<String, Object> map=jobDao.getJob(id);
		map.putAll(requestMap);
		jobDao.updateJob(map);		
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "更新成功");
				put("data", null);
			}
		};
	}
	
	/*  curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/job/deleteJob?id=2" 
	*/
 	@RequestMapping(value = "/api/github/qq275860560/job/deleteJob")
	public Map<String, Object> deleteJob(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		jobDao.deleteJob(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "删除成功");
				put("data", null);
			}
		};
	}
	
	
 	/*  curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/job/runJob?id=1" 
	*/
 	@RequestMapping(value = "/api/github/qq275860560/job/runJob")
	public Map<String, Object> runJob(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		String result=runJob(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "构建成功");
				put("data", result);
			}
		};
	}




	private String runJob(String id) throws Exception {
		Map<String, Object> map = jobDao.getJob(id);
		String dataxJson=(String)map.get("dataxJson");
		
		String name = (String) map.get("name");
		File file = new File(Constant.DATAX_HOME + File.separator + "job" + File.separator + name + ".json");
		FileUtils.writeStringToFile(file, dataxJson, "UTF-8");

		log.info(file.getAbsolutePath());

		return runComand("python " + Constant.DATAX_HOME + File.separator + "bin" + File.separator + "datax.py "
				+ file.getAbsolutePath());
	 
		
		
	}
	public static String runComand(String command) throws Exception {
		StringBuilder result = new StringBuilder();
		Process pr = Runtime.getRuntime().exec(command);
		log.info(command);
		BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));

		String line = null;
		while ((line = in.readLine()) != null) {
			result.append(line).append("\r\n");
		}
		in.close();
		pr.waitFor();
		return result.toString();

	}
     
	

	/*  curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/job/getJobProgress?id=1" 
	*/
 	@RequestMapping(value = "/api/github/qq275860560/job/getJobProgress")
	public Map<String, Object> getJobProgress(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		Double data=(Double)jobDao.getJob(id).get("progress");	
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "获取进度成功");
				put("data", data);
			}
		};
	}
 	
 	/*  curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/job/stopJob?id=1" 
	*/
 	@RequestMapping(value = "/api/github/qq275860560/job/stopJob")
	public Map<String, Object> stopJob(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
	 
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "停止成功");
				put("data", null);
			}
		};
	}
 	
 	
	/*  curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/job/enableJob?id=1" 
	*/
 	@RequestMapping(value = "/api/github/qq275860560/job/enableJob")
	public Map<String, Object> enableJob(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);	
	 

		String id=(String)requestMap.get("id");
		Map<String, Object> map=jobDao.getJob(id);
		map.put("status",1);
		jobDao.updateJob(map);			
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "启用成功");
				put("data", null);
			}
		};
	}
 	
 	
 	/*  curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/job/disableJob?id=1" 
	*/
 	@RequestMapping(value = "/api/github/qq275860560/job/disableJob")
	public Map<String, Object> disableJob(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);	
	 
		String id=(String)requestMap.get("id");
		Map<String, Object> map=jobDao.getJob(id);
		map.put("status",0);
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
