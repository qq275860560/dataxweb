package com.github.qq275860560.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangyuanlin@163.com
 *
 */
@RestController
@Slf4j
public class MysqlReaderDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int countMysqlReader(String name) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(" SELECT count(1) count from mysqlReader where 1=1 ");
		sb.append(" and name = ? ");
		condition.add(name);
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));// 如果存在blog等字节数组类型的，请注释此行打印
		return jdbcTemplate.queryForObject(sb.toString(), condition.toArray(), Integer.class);
	}

	public boolean checkMysqlReader(String id, String name) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(" SELECT count(1) count  from mysqlReader where 1=1 ");
		if (!StringUtils.isEmpty(id)) {
			sb.append(" and id != ? ");
			condition.add(id);
		}
		sb.append(" and name= ? ");
		condition.add(name);
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));// 如果存在blog等字节数组类型的，请注释此行打印
		int count = jdbcTemplate.queryForObject(sb.toString(), condition.toArray(), Integer.class);
		if (count > 0)
			return false;
		else
			return true;
	}

	public int deleteMysqlReader(String id) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(" delete  from mysqlReader where 1=1 ");
		sb.append(" and id = ? ");
		condition.add(id);
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));// 如果存在blog等字节数组类型的，请注释此行打印
		return jdbcTemplate.update(sb.toString(), condition.toArray());
	}

	public Map<String, Object> getMysqlReader(Object id) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(
				" SELECT id,readerParameterUsername,readerParameterPassword,readerParameterColumn,readerParameterWhere,readerParameterConnectionJdbcUrl,readerParameterConnectionTable,createUserId,createUserName,date_format(createTime,	'%Y-%m-%d %H:%i:%s') createTime from mysqlReader where 1=1 ");
		if (!StringUtils.isEmpty(id)) {
			sb.append(" and id = ? ");
			condition.add(id);
		}
		sb.append(" limit ? ,?  ");
		condition.add(0);
		condition.add(1);
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));// 如果存在blog等字节数组类型的，请注释此行打印
		Map<String, Object> map = new HashMap<>();
		try {
			map = jdbcTemplate.queryForMap(sb.toString(), condition.toArray());
		} catch (Exception e) {
		}
		return map;
	}

	public Map<String, Object> getMysqlReaderByKeyValue(String key, Object value) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(
				" SELECT id,readerParameterUsername,readerParameterPassword,readerParameterColumn,readerParameterWhere,readerParameterConnectionJdbcUrl,readerParameterConnectionTable,createUserId,createUserName,date_format(createTime,	'%Y-%m-%d %H:%i:%s') createTime from mysqlReader where 1=1 ");
		sb.append(" and " + key + " = ? ");
		condition.add(value);
		sb.append(" limit ? ,?  ");
		condition.add(0);
		condition.add(1);
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));// 如果存在blog等字节数组类型的，请注释此行打印
		Map<String, Object> map = new HashMap<>();
		try {
			map = jdbcTemplate.queryForMap(sb.toString(), condition.toArray());
		} catch (Exception e) {
		}
		return map;
	}

	public int saveMysqlReader(Map<String, Object> map) throws Exception {
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb1.append("id").append(",");
		sb2.append("?,");
		condition.add(map.get("id"));

		sb1.append("readerParameterUsername").append(",");
		sb2.append("?,");
		condition.add(map.get("readerParameterUsername"));

		sb1.append("readerParameterPassword").append(",");
		sb2.append("?,");
		condition.add(map.get("readerParameterPassword"));

		sb1.append("readerParameterColumn").append(",");
		sb2.append("?,");
		condition.add(map.get("readerParameterColumn"));

		sb1.append("readerParameterWhere").append(",");
		sb2.append("?,");
		condition.add(map.get("readerParameterWhere"));

		sb1.append("readerParameterConnectionJdbcUrl").append(",");
		sb2.append("?,");
		condition.add(map.get("readerParameterConnectionJdbcUrl"));

		sb1.append("readerParameterConnectionTable").append(",");
		sb2.append("?,");
		condition.add(map.get("readerParameterConnectionTable"));

		sb1.append("createUserId").append(",");
		sb2.append("?,");
		condition.add(map.get("createUserId"));

		sb1.append("createUserName").append(",");
		sb2.append("?,");
		condition.add(map.get("createUserName"));

		sb1.append("createTime").append(",");
		sb2.append("?,");
		condition.add(map.get("createTime"));

		if (sb1.length() > 0)
			sb1.deleteCharAt(sb1.length() - 1);
		if (sb2.length() > 0)
			sb2.deleteCharAt(sb2.length() - 1);
		String sql = "insert into mysqlReader(" + sb1.toString() + ") values(" + sb2.toString() + ")";
		log.info("sql=" + sql);
		log.info("condition=" + Arrays.deepToString(condition.toArray()));// 如果存在blog等字节数组类型的，请注释此行打印
		return jdbcTemplate.update(sql, condition.toArray());

	}

	public int updateMysqlReader(Map<String, Object> map) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(" readerParameterUsername = ? ,");
		condition.add(map.get("readerParameterUsername"));

		sb.append(" readerParameterPassword = ? ,");
		condition.add(map.get("readerParameterPassword"));

		sb.append(" readerParameterColumn = ? ,");
		condition.add(map.get("readerParameterColumn"));

		sb.append(" readerParameterWhere = ? ,");
		condition.add(map.get("readerParameterWhere"));

		sb.append(" readerParameterConnectionJdbcUrl = ? ,");
		condition.add(map.get("readerParameterConnectionJdbcUrl"));

		sb.append(" readerParameterConnectionTable = ? ,");
		condition.add(map.get("readerParameterConnectionTable"));

		sb.append(" createUserId = ? ,");
		condition.add(map.get("createUserId"));

		sb.append(" createUserName = ? ,");
		condition.add(map.get("createUserName"));

		sb.append(" createTime = ? ,");
		condition.add(map.get("createTime"));

		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		String sql = "update mysqlReader set " + sb.toString() + " where    id=?";
		condition.add(map.get("id"));
		log.info("sql=" + sql);
		log.info("condition=" + Arrays.deepToString(condition.toArray()));// 如果存在blog等字节数组类型的，请注释此行打印
		return jdbcTemplate.update(sql, condition.toArray());
	}

	public List<Map<String, Object>> listMysqlReader(String id, String readerParameterUsername,
			String readerParameterPassword, String readerParameterColumn, String readerParameterWhere,
			String readerParameterConnectionJdbcUrl, String readerParameterConnectionTable, String createUserId,
			String createUserName, String startCreateTime, String endCreateTime) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(
				" SELECT id,readerParameterUsername,readerParameterPassword,readerParameterColumn,readerParameterWhere,readerParameterConnectionJdbcUrl,readerParameterConnectionTable,createUserId,createUserName,date_format(createTime,	'%Y-%m-%d %H:%i:%s') createTime from mysqlReader where 1=1 ");
		if (!StringUtils.isEmpty(id)) {
			sb.append(" and id like ? ");
			condition.add("%" + id + "%");
		}
		if (!StringUtils.isEmpty(readerParameterUsername)) {
			sb.append(" and readerParameterUsername like ? ");
			condition.add("%" + readerParameterUsername + "%");
		}
		if (!StringUtils.isEmpty(readerParameterPassword)) {
			sb.append(" and readerParameterPassword like ? ");
			condition.add("%" + readerParameterPassword + "%");
		}
		if (!StringUtils.isEmpty(readerParameterColumn)) {
			sb.append(" and readerParameterColumn like ? ");
			condition.add("%" + readerParameterColumn + "%");
		}
		if (!StringUtils.isEmpty(readerParameterWhere)) {
			sb.append(" and readerParameterWhere like ? ");
			condition.add("%" + readerParameterWhere + "%");
		}
		if (!StringUtils.isEmpty(readerParameterConnectionJdbcUrl)) {
			sb.append(" and readerParameterConnectionJdbcUrl like ? ");
			condition.add("%" + readerParameterConnectionJdbcUrl + "%");
		}
		if (!StringUtils.isEmpty(readerParameterConnectionTable)) {
			sb.append(" and readerParameterConnectionTable like ? ");
			condition.add("%" + readerParameterConnectionTable + "%");
		}
		if (!StringUtils.isEmpty(createUserId)) {
			sb.append(" and createUserId like ? ");
			condition.add("%" + createUserId + "%");
		}
		if (!StringUtils.isEmpty(createUserName)) {
			sb.append(" and createUserName like ? ");
			condition.add("%" + createUserName + "%");
		}
		if (!StringUtils.isEmpty(startCreateTime)) {
			sb.append(" and createTime >=  ?  ");
			condition.add(startCreateTime);
		}
		if (!StringUtils.isEmpty(endCreateTime)) {
			sb.append(" and createTime <=  ?  ");
			condition.add(endCreateTime);
		}
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));// 如果存在blog等字节数组类型的，请注释此行打印
		return jdbcTemplate.queryForList(sb.toString(), condition.toArray());

	}

	public Map<String, Object> pageMysqlReader(String id, String readerParameterUsername,
			String readerParameterPassword, String readerParameterColumn, String readerParameterWhere,
			String readerParameterConnectionJdbcUrl, String readerParameterConnectionTable, String createUserId,
			String createUserName, String startCreateTime, String endCreateTime, Integer pageNum, Integer pageSize)
			throws Exception {
		if (pageNum == null)
			pageNum = 1;// 取名pageNum为了兼容mybatis-pageHelper中的page对象的pageNum,注意spring的PageRequest使用page表示页号,综合比较，感觉pageNum更加直观,不需要看上下文能猜出字段是页号
		if (pageSize == null)
			pageSize = 10;// 取名pageSize为了兼容mybatis-pageHelper中的page对象的pageSize,注意spring的PageRequest使用size表示页数量，综合比较，感觉pageSize会更加直观,不需要看上下文能猜出字段是分页时当前页的数量
		int from = (pageNum - 1) * pageSize;
		int size = pageSize;
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(
				" SELECT id,readerParameterUsername,readerParameterPassword,readerParameterColumn,readerParameterWhere,readerParameterConnectionJdbcUrl,readerParameterConnectionTable,createUserId,createUserName,date_format(createTime,	'%Y-%m-%d %H:%i:%s') createTime from mysqlReader where 1=1 ");
		if (!StringUtils.isEmpty(id)) {
			sb.append(" and id like ? ");
			condition.add("%" + id + "%");
		}
		if (!StringUtils.isEmpty(readerParameterUsername)) {
			sb.append(" and readerParameterUsername like ? ");
			condition.add("%" + readerParameterUsername + "%");
		}
		if (!StringUtils.isEmpty(readerParameterPassword)) {
			sb.append(" and readerParameterPassword like ? ");
			condition.add("%" + readerParameterPassword + "%");
		}
		if (!StringUtils.isEmpty(readerParameterColumn)) {
			sb.append(" and readerParameterColumn like ? ");
			condition.add("%" + readerParameterColumn + "%");
		}
		if (!StringUtils.isEmpty(readerParameterWhere)) {
			sb.append(" and readerParameterWhere like ? ");
			condition.add("%" + readerParameterWhere + "%");
		}
		if (!StringUtils.isEmpty(readerParameterConnectionJdbcUrl)) {
			sb.append(" and readerParameterConnectionJdbcUrl like ? ");
			condition.add("%" + readerParameterConnectionJdbcUrl + "%");
		}
		if (!StringUtils.isEmpty(readerParameterConnectionTable)) {
			sb.append(" and readerParameterConnectionTable like ? ");
			condition.add("%" + readerParameterConnectionTable + "%");
		}
		if (!StringUtils.isEmpty(createUserId)) {
			sb.append(" and createUserId like ? ");
			condition.add("%" + createUserId + "%");
		}
		if (!StringUtils.isEmpty(createUserName)) {
			sb.append(" and createUserName like ? ");
			condition.add("%" + createUserName + "%");
		}
		if (!StringUtils.isEmpty(startCreateTime)) {
			sb.append(" and createTime >=  ?  ");
			condition.add(startCreateTime);
		}
		if (!StringUtils.isEmpty(endCreateTime)) {
			sb.append(" and createTime <=  ?  ");
			condition.add(endCreateTime);
		}
		String countSql = "select count(1) count from ( " + sb.toString() + ") t";
		int count = jdbcTemplate.queryForObject(countSql, condition.toArray(), Integer.class);
		sb.append(" order by createTime desc ");
		sb.append(" limit ? ,?  ");
		condition.add(from);
		condition.add(size);
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));// 如果存在blog等字节数组类型的，请注释此行打印
		List<Map<String, Object>> pageList = jdbcTemplate.queryForList(sb.toString(), condition.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);// 取名total为了兼容mybatis-pageHelper中的page对象的total,spring框架的PageImpl也使用total
		map.put("pageList", pageList);// 不同的框架取名不一样，可以把pageList改成list,array,rows,data,content,result等,spring框架使用的是content,mybatis因为page对象是继承ArrayList，字段命名乱七八糟，有时pageList，有时result，综上感觉pageList会更加直观和简洁,不需要看上下文能猜出字段是列表
		return map;

	}
}
