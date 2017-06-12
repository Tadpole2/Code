package com.yd.gcj.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yd.gcj.entity.YdMangerSpcs;

public interface YdMangerServiceSpcs {

	/***
	 * 查询指定用户的评价信息
	 * @param spcs_sid
	 * @return
	 */
	Object $queryAll(HashMap<String, Object> map);
	
	/***
	 * 查询指定用户的评价信息
	 * @param userId
	 * @return
	 */
	List<YdMangerSpcs> $queryByUserId(Integer userId); 
	
	/***
	 * 查询指定用户根据 分页 的评价信息
	 * @param spcs_sid
	 * @param startPageNum
	 * @param queryPageNum
	 * @return
	 */
	Object $queryAllByPageNum(HashMap<String, Object> map);
	
	/***
	 * 查询指定评价信息
	 * @param spcs_id
	 * @return
	 */
	Object $queryById(HashMap<String, Object> map);
	
	/***
	 * 新增用户评价信息
	 * @param spcs
	 * @return
	 */
	@Transactional
	Object $insert(YdMangerSpcs spcs);
	
	/***
	 * 更新用户评价信息
	 * @param spcs
	 * @return
	 */
	@Transactional
	Object $update(HashMap<String, Object> map);
	
	/***
	 * 删除指定评价信息
	 * @param spcs_id
	 * @return
	 */
	@Transactional
	Object $delete(HashMap<String, Object> map);
	
}