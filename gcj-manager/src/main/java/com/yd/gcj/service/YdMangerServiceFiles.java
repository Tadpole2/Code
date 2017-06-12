package com.yd.gcj.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yd.gcj.entity.YdMangerFiles;

public interface YdMangerServiceFiles {

	/***
	 * 根据sql多条件查询文件信息
	 * @param sql
	 * @return
	 */
	List<YdMangerFiles> $queryAllBySql(String sql);
	
	/***
	 * 根据任务id查询任务所属文件
	 * @param taskId
	 * @return
	 */
	List<YdMangerFiles> $queryAllByTaskId(Integer taskId);
	
	/***
	 * 查询指定文件信息
	 * @param files_id
	 * @return
	 */
	YdMangerFiles $queryById(Integer fileId);
	
	/***
	 * 根据sql多条件查询文件信息数量
	 * @param sql
	 * @return
	 */
	Integer $queryCountNum(String sql);
	
	/***
	 * 添加文件信息
	 * @param files
	 * @return
	 */
	@Transactional
	Integer $insert(YdMangerFiles files);
	
	/***
	 * 修改文件信息
	 * @param files
	 * @return
	 */
	@Transactional
	Integer $update(YdMangerFiles files);
	
	/***
	 * 删除指定文件信息
	 * @param files_id
	 * @return
	 */
	@Transactional
	Integer $delete(Integer files_id);
}