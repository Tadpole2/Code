package com.yd.gcj.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yd.gcj.entity.YdMangerTender;
import com.yd.gcj.entity.vo.YdMangerTenderVo;

public interface YdMangerServiceTender {

	/***
	 * 查询指定任务投标信息
	 * @param map
	 * @return
	 */
	List<YdMangerTenderVo> $queryByTid(Integer taskId,Integer type);
	
	/***
	 * 根据任务id查询投标者信息
	 * @param taskId
	 * @return
	 */
	YdMangerTenderVo queryTenderByTid(Integer taskId);
	
	/***
	 * 查询指定的投标信息  附加（服务商信息，案例，好评，服务商技能等）用于查看服务商投标详情
	 * @param tenderId
	 * @return
	 */
	YdMangerTenderVo $queryByIdAndUserMsg(Integer tenderId);
	
	/***
	 * 查询指定任务中按个人或公司类型查询
	 * @param map
	 * @return
	 */
	Object $queryByType(HashMap<String, Object> map);
	
	/***
	 * 查询指定服务商所有投标信息
	 * @param map
	 * @return
	 */
	Object $queryBySid(HashMap<String, Object> map);
	
	/***
	 * 查询指定服务商投标信息数量
	 * @param map
	 * @return
	 */
	Object $queryCountNumBySid(HashMap<String, Object> map);
	
	/***
	 * 查询任务投标数量
	 * @param map
	 * @return
	 */
	Object $queryCountNumByTid(HashMap<String, Object> map);
	
	/**
	 * 检查用户是否对任务进行过投标
	 * @param map
	 * @return
	 */
	Object $isExist(HashMap<String, Object> map);
	
	/***
	 * 添加新投标信息
	 * @param map
	 * @return
	 */
	@Transactional
	Object $insert(YdMangerTender tender);
	
	/***
	 * 更新指定投标信息
	 * @param map
	 * @return
	 */
	@Transactional
	Object $update(YdMangerTender tender);
	
	/***
	 * 修改投标状态
	 * @param map
	 * @return
	 */
	@Transactional
	Object $updateState(HashMap<String, Object> map);
	
	/**
	 * 选标
	 * @param map
	 * @return
	 */
	@Transactional
	Object $selection(Integer taskId,Integer tenderId,Integer type,Integer userId,Integer userSId);
	
	/***
	 * 设置投标用户为组长
	 * @param map
	 * @return
	 */
//	Object $leader(HashMap<String, Object> map);
	
	/***
	 * 删除投标信息
	 * @param map
	 * @return
	 */
	@Transactional
	Object $delete(Integer taskId,Integer userId);
	
}