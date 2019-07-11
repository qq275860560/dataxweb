package com.github.qq275860560;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.qq275860560.common.util.CommandUtil;
import com.github.qq275860560.constant.Constant;
import com.github.qq275860560.controller.JobController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangyuanlin@163.com
 *
 */
@SuppressWarnings(value = { "serial", "rawtypes" })
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DataxJobTest {
	@Autowired
	private  ObjectMapper objectMapper ;
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void execute() throws Exception {
	

	 
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("name", "mysql-mysql");
		requestMap.put("type", "mysqlreader");
		requestMap.put("readerParameterUsername", "root");
		requestMap.put("readerParameterPassword", "123456");
		requestMap.put("readerParameterColumn", "id,name");
		requestMap.put("readerParameterWhere", "");
		requestMap.put("readerParameterConnectionJdbcUrl",
				"jdbc:mysql://127.0.0.1:3306/dataxweb?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
		requestMap.put("readerParameterConnectionTable", "job");

		requestMap.put("type", "mysqlwriter");
		requestMap.put("writerParameterUsername", "root");
		requestMap.put("writerParameterPassword", "123456");
		requestMap.put("writerParameterWriteMode", "insert");
		requestMap.put("writerParameterColumn", "id,name");
		requestMap.put("writerParameterPreSql", "delete from test");
		requestMap.put("writerParameterConnectionJdbcUrl",
				"jdbc:mysql://127.0.0.1:3306/dataxweb?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
		requestMap.put("writerParameterConnectionTable", "test");

		Map<String, Object> dataxMap = JobController.generateDataxMap(requestMap);

		String readerResult = objectMapper.writeValueAsString(dataxMap);
		log.info(readerResult);

		String name = (String) requestMap.get("name");
		File file = new File(Constant.DATAX_HOME + File.separator + "job" + File.separator + name + ".json");
		FileUtils.writeStringToFile(file, readerResult, "UTF-8");

		log.info(file.getAbsolutePath());

		//工具类实现参考https://github.com/qq275860560/common/blob/master/src/main/java/com/github/qq275860560/common/util/CommandUtil.java 
		String execResult = CommandUtil.runComand("python " + Constant.DATAX_HOME + File.separator + "bin" + File.separator + "datax.py "
				+ file.getAbsolutePath());
		log.info(execResult);
	}


}
