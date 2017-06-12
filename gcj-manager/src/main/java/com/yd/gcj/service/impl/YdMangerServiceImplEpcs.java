package com.yd.gcj.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.gcj.entity.YdMangerEpcs;
import com.yd.gcj.mapper.YdMangerMapperEpcs;
import com.yd.gcj.mapper.YdMangerMapperTask;
import com.yd.gcj.service.YdMangerServiceEpcs;
import com.yd.gcj.tool.MapInitFactory;
import com.yd.gcj.tool.Values;
@Service("YdMangerServiceEpcs")
public class YdMangerServiceImplEpcs implements YdMangerServiceEpcs{
	
	@Autowired
	private YdMangerMapperEpcs ydMangerMapperEpcs;
	
	@Autowired
	private YdMangerMapperTask ydMangerMapperTask;
	
	@Override
	public Object $queryAllByTid(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
//		ydMangerMapperEpcs.$queryAllByTid(epcs_tid);
		return mapInitFactory.getMap();
	}
	
	@Override
	public List<YdMangerEpcs> $queryAllByUserId(Integer userId) {
		return ydMangerMapperEpcs.$queryAllByUserId(userId);
	}
	
	@Override
	public Object $queryBySql(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
//		ydMangerMapperEpcs.$queryBySql(sql);
		return mapInitFactory.getMap(); 
	}

	@Override
	public Object $queryCountNum(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
//		ydMangerMapperEpcs.$queryCountNum(epcs_tid);
		return mapInitFactory.getMap();
	}

	@Override
	public Object $queryCountNumBySql(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
//		ydMangerMapperEpcs.$queryCountNumBySql(sql);
		return mapInitFactory.getMap();
	}

	@Override
	public Object $insert(YdMangerEpcs epcs) {
		
		MapInitFactory mapInitFactory = new MapInitFactory();
		mapInitFactory.setSystemError();
		Integer isExist = ydMangerMapperEpcs.$queryCountNum(epcs.getEpcs_tid());
		if(isExist == 0){
			Date date = new Date();
			epcs.setEpcs_create_time(date);
			epcs.setEpcs_update_time(date);
			Integer success = ydMangerMapperEpcs.$insert(epcs);
			if(success > 0){
				Integer taskStateSuccess = ydMangerMapperTask.$updateTaskState(epcs.getEpcs_tid(), 6);
				if(taskStateSuccess > 0){
					mapInitFactory.setMsg(Values.INITSUCCESSCODE, "评价提交成功！");
				}else{
					mapInitFactory.setMsg("503", "评价提交失败！");
				}
			}else{
				mapInitFactory.setMsg("501", "评价失败!");
			}
		}else{
			mapInitFactory.setMsg("502", "您已经评论过了，无需重复评论！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $update(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
//		ydMangerMapperEpcs.$update(epcs);
		return mapInitFactory.getMap();
	}

	@Override
	public Object $delete(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
//		ydMangerMapperEpcs.$delete(epcs_id);
		return mapInitFactory.getMap();
	}

}