package com.yd.gcj.mapper;

import org.apache.ibatis.annotations.Param;

import com.yd.gcj.entity.YdMangerVerified;

public interface YdMangerMapperVerified {
	
	/***
	 * 查询指定用户的实名认证信息
	 * @param userId
	 * @return
	 */
	public YdMangerVerified $queryByUserId(@Param("userId") Integer userId);
	
	/***
	 * 查询指定id的实名认证信息
	 * @param id
	 * @return
	 */
	public YdMangerVerified $queryById(@Param("id") Integer id);
	
	/****
	 * 根据用户id查询该用户实名信息的id
	 * @param userId
	 * @return
	 */
	public Integer $queryIdByUserId(@Param("userId") Integer userId);
	
	/***
	 * 根据用户id检查是否有该用户的实名认证信息
	 * @param userId
	 * @return
	 */
	public Integer $exsitByUserId(@Param("userId") Integer userId);
	
	/***
	 * 根据用户手机号检查是否有该用户的实名认证信息
	 * @param userPhone
	 * @return
	 */
	public Integer $exsitByUserPhone(@Param("userPhone") String userPhone);
	
	/***
	 * 根据实名认证信息id检查该信息是否存在
	 * @param id
	 * @return
	 */
	public Integer $exsitById(@Param("id") Integer id);
	
	/***
	 * 根据用户id添加用户默认实名认证信息
	 * @param userId
	 * @return
	 */
	public Integer $insert(YdMangerVerified verified);
	
	/***
	 * 从默认实名认证信息中更新真实姓名信息
	 * @param name
	 * @param id
	 * @return
	 */
	public Integer $updateVName(@Param("name") String name,@Param("id") Integer id,@Param("idNum") String idNum,@Param("yosId") Integer yosId);
	
	/****
	 * 从默认实名认证信息中更新用户身份证正面图片信息
	 * @param idcaraimg
	 * @param id
	 * @return
	 */
	public Integer $updateVIdCarImgA(@Param("idcaraimg") String idcaraimg,@Param("id") Integer id);
	
	/****
	 * 从默认实名认证信息中更新用户身份证反面图片信息
	 * @param idcarbimg
	 * @param id
	 * @return
	 */
	public Integer $updateVIdCarImgB(@Param("idcarbimg") String idcarbimg,@Param("id") Integer id);

}