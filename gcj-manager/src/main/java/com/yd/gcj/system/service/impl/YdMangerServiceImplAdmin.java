package com.yd.gcj.system.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.gcj.entity.YdMangerSystemAdmin;
import com.yd.gcj.system.mapper.YdMangerMapperSystemAdmin;
import com.yd.gcj.system.service.YdMangerServiceSystemAdmin;

@Service("ydMangerServiceSystemAdmin")
public class YdMangerServiceImplAdmin implements YdMangerServiceSystemAdmin {

	@Autowired
	private YdMangerMapperSystemAdmin ydMangerMapperSystemAdmin;

	// 查询所有管理员
	@Override
	public List<YdMangerSystemAdmin> queryAllAdmin(String admin_name, Integer admin_is_super) {
		List<YdMangerSystemAdmin> adminList = ydMangerMapperSystemAdmin.queryAllAdmin(admin_name, admin_is_super);
		return adminList;
	}

	// 添加管理员
	@Override
	public Integer addAdmin(YdMangerSystemAdmin ydMangerSystemAdmin) {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String stringDae = sdf.format(date);
		Date parseDate;
		try {
			parseDate = sdf.parse(stringDae);
			ydMangerSystemAdmin.setAdmin_create_time(parseDate);
			ydMangerSystemAdmin.setAdmin_update_time(parseDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer addNum = ydMangerMapperSystemAdmin.addAdmin(ydMangerSystemAdmin);
		return addNum;
	}

	// 删除管理员
	@Override
	public Integer deleteAdmin(Integer admin_id) {
		Integer delNum = ydMangerMapperSystemAdmin.deleteAdmin(admin_id);
		return delNum;
	}

	// 修改管理员
	@Override
	public Integer updateAdmin(YdMangerSystemAdmin ydMangerSystemAdmin) {

		Integer updateNum = ydMangerMapperSystemAdmin.updateAdmin(ydMangerSystemAdmin);
		return updateNum;
	}

	@Override
	public Integer deleteBatchAdminByAdminIds(List<Integer> admin_ids) {
		return null;
	}

	// 阿姨只能横手机号码的唯一性
	@Override
	public YdMangerSystemAdmin queryAdminByAdminPhon(String admin_phone) {
		YdMangerSystemAdmin ydMangerSystemAdmin = ydMangerMapperSystemAdmin.queryAdminByAdminPhon(admin_phone);
		return ydMangerSystemAdmin;
	}

	// ajax验证用户名称的唯一性
	@Override
	public YdMangerSystemAdmin queryAdminByAdminName(String admin_name) {
		YdMangerSystemAdmin ydMangerSystemAdmin = ydMangerMapperSystemAdmin.queryAdminByAdmiName(admin_name);
		return ydMangerSystemAdmin;
	}

	// ajax验证管理员账号的唯一性
	@Override
	public YdMangerSystemAdmin queryAdminByAdminAccount(String admin_account) {
		YdMangerSystemAdmin ydMangerSystemAdmin = ydMangerMapperSystemAdmin.queryAdminByAdminAccount(admin_account);
		return ydMangerSystemAdmin;
	}

	// 修改之前先查到当前管理员信息
	@Override
	public YdMangerSystemAdmin queryAdminByAdminId(Integer admin_id) {
		YdMangerSystemAdmin ydMangerSystemAdmin = ydMangerMapperSystemAdmin.queryAdminByAdminId(admin_id);
		return ydMangerSystemAdmin;
	}

	// 管理员登陆
	@Override
	public YdMangerSystemAdmin queryAdminByUserAccountAndUserPassword(String admin_account, String admin_password) {
		YdMangerSystemAdmin systemAdmin = ydMangerMapperSystemAdmin
				.queryAdminByUserAccountAndUserPassword(admin_account, admin_password);
		if (systemAdmin != null) {
			return systemAdmin;
		} else {
			return null;
		}

	}

	// 修改管理员密码
	@Override
	public Integer updateAdminPassword(Integer admin_id, String newpassword) {
		Integer updateNum = ydMangerMapperSystemAdmin.updateAdminPassword(admin_id, newpassword);
		return updateNum;
	}

	// 登录成功之后修改登录次数
	@Override
	public Integer updateAdminByAdminId(YdMangerSystemAdmin systemAdmin) {
		Integer updateNum = ydMangerMapperSystemAdmin.updateAdminByAdminId(systemAdmin);
		return updateNum;
	}

}