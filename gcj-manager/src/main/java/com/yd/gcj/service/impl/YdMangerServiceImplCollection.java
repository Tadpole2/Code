package com.yd.gcj.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.gcj.entity.YdMangerCollection;
import com.yd.gcj.mapper.YdMangerMapperCollection;
import com.yd.gcj.service.YdMangerServiceCollection;
import com.yd.gcj.tool.MapInitFactory;
import com.yd.gcj.tool.Values;

@Service("YdMangerServiceCollection")
public class YdMangerServiceImplCollection implements YdMangerServiceCollection {

	@Autowired
	private YdMangerMapperCollection ydMangerMapperCollection;

	@Override
	public Object $queryAll(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {
			Integer colle_uid = (Integer) map.get("colle_uid");
			mapInitFactory.init().setData(ydMangerMapperCollection.$queryAll(colle_uid));
		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}

	@Override
	public List<YdMangerCollection> $queryAllByPageNum(Integer userId, Integer startPageNum, Integer queryPageNum) {
		List<YdMangerCollection> collection = ydMangerMapperCollection.$queryAllByPageNum(userId, startPageNum,
				queryPageNum);
		return collection;
	}

	@Override
	public Object $queryCountNum(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {
			Integer colle_uid = (Integer) map.get("colle_uid");
			mapInitFactory.init().setData(ydMangerMapperCollection.$queryCountNum(colle_uid));
		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $insert(Integer userId, Integer taskId) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		mapInitFactory.setSystemError();
		Integer isExist = ydMangerMapperCollection.$isExist(userId, taskId);
		if (isExist == 0) {
			Integer success = ydMangerMapperCollection.$insert(userId, taskId);
			if (success != null && success > 0) {
				mapInitFactory.setMsg(Values.INITSUCCESSCODE, "收藏成功");
			} else {
				mapInitFactory.setMsg("501", "收藏失败");
			}
		} else {
			mapInitFactory.setMsg("502", "您已经收藏过了！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $delete(Integer userId, Integer taskId) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		mapInitFactory.setSystemError();
		Integer isExist = ydMangerMapperCollection.$isExist(userId, taskId);
		if (isExist > 0) {
			Integer success = ydMangerMapperCollection.$delete(userId, taskId);
			if (success > 0) {
				mapInitFactory.setMsg(Values.INITSUCCESSCODE, "取消成功！");
			} else {
				mapInitFactory.setMsg("501", "取消失败，请稍后再试！");
			}
		} else {
			mapInitFactory.setMsg("503", "您没有收藏该任务！");
		}
		return mapInitFactory.getMap();
	}

}