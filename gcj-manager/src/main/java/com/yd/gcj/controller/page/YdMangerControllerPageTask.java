package com.yd.gcj.controller.page;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yd.gcj.entity.YdMangerFiles;
import com.yd.gcj.entity.YdMangerFilesTask;
import com.yd.gcj.entity.YdMangerTaskCondition;
import com.yd.gcj.entity.YdMangerUser;
import com.yd.gcj.entity.vo.YdMangerTaskVo;
import com.yd.gcj.entity.vo.YdMangerUserVo;
import com.yd.gcj.file.YdMangerFilesFactory;
import com.yd.gcj.service.YdMangerServiceFiles;
import com.yd.gcj.service.YdMangerServiceFilesTask;
import com.yd.gcj.service.YdMangerServiceTask;
import com.yd.gcj.service.YdMangerServiceTaskFolder;
import com.yd.gcj.tool.MapInitFactory;
import com.yd.gcj.tool.ObjectMapperFactory;

@RestController
@RequestMapping(value = "/page/task", produces = { "application/json;charset=UTF-8" })
public class YdMangerControllerPageTask {

	@Autowired
	private YdMangerServiceTask ydMangerServiceTask;

	@Autowired
	private YdMangerServiceFiles ydMangerServiceFiles;

	@Autowired
	private YdMangerServiceFilesTask ydMangerServiceFilesTask;
	
	@Autowired
	private YdMangerServiceTaskFolder serviceTaskFolder;
	

	/**
	 * 任务大厅条件搜索提交条件接口
	 * 
	 * @param taskTypeIds
	 * @param taskLabelIds
	 * @param taskAddr
	 * @param taskState
	 * @param taskTerm
	 * @return
	 */
	@RequestMapping(value = "condition", method = RequestMethod.POST)
	public Object condition(String taskTypeIds, String taskLabelIds, String taskAddr, Integer taskState,
			String taskTerm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		MapInitFactory mapInitFactory = new MapInitFactory();
		YdMangerTaskCondition conditions = new YdMangerTaskCondition();
		if (taskTypeIds.equals("-1")) {
			session.setAttribute("taskTypeState", 1);
		} else {
			session.setAttribute("taskTypeState", 0);
		}
		conditions.setTaskTypeIds(taskTypeIds);
		conditions.setTaskLabelIds(taskLabelIds);
		conditions.setTaskState(taskState);
		conditions.setTaskTerm(taskTerm);
		conditions.setTaskAddr(taskAddr);
		session.setAttribute("taskCondition", conditions);
		mapInitFactory.init();
		return mapInitFactory.getMap();
	}

	/***
	 * 
	 * @param request
	 * @param files
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/addTaskFile/{taskId}", method = RequestMethod.POST)
	public Object fileUpLoad(HttpServletRequest request, MultipartFile[] files, @PathVariable Integer taskId) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
		if (userVo != null) {
			// 这里可以支持多文件上传
			BufferedOutputStream bw = null;
			try {
				if (files != null && files.length >= 1) {
					Date date = new Date();
					long time = date.getTime();
					String times = String.valueOf(time);
					Integer timesL = times.length();
					String fileName = files[0].getOriginalFilename();
					String fileTName = times.substring(3, timesL) + getFileType(fileName);
					InputStream iptS = files[0].getInputStream();
					YdMangerFilesFactory ymff = new YdMangerFilesFactory();
					boolean success = ymff.fileUpLoadToJGY(iptS, fileTName);
					if (success) {
						mapInitFactory.setMsg("200", "文件上传成功！");
						YdMangerFiles file = new YdMangerFiles();
						file.setFiles_create_time(date);
						file.setFiles_update_time(date);
						file.setFiles_name(fileName);
						file.setFiles_path(fileTName);
						file.setFiles_size(files[0].getSize());
						ydMangerServiceFiles.$insert(file);
						YdMangerFilesTask fileTask = new YdMangerFilesTask();
						fileTask.setFiletr_id(file.getFiles_id());
						fileTask.setFiletr_tid(taskId);
						ydMangerServiceFilesTask.$insert(fileTask);
					} else {
						mapInitFactory.setMsg("502", "文件上传失败！");
					}
				} else {
					mapInitFactory.setMsg("501", "没有接收到文件");
				}
			} catch (Exception e) {
				e.printStackTrace();
				mapInitFactory.setSystemError();
			} finally {
				try {
					if (bw != null) {
						bw.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
					mapInitFactory.setSystemError();
				}
			}
		} else {
			mapInitFactory.setMsg("600", "您当前没有权限操作此功能！");
		}
		return mapInitFactory.getMap();
	}

	/***
	 * 添加新的任务信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public Object insert(String taskName, String taskAddr, String taskSize, String typeIds, String labelIds,
			String description, String term, float price, String fileIds, Date taskPtime, HttpServletRequest request) {
		try {
			YdMangerUserVo user = (YdMangerUserVo) request.getSession().getAttribute("user");
			if (user != null) {
				Integer userId = user.getUser_id();
				Date d = new Date();
				HashMap<String, Object> map = new HashMap<String, Object>();
				// 项目信息
				map.put("task_uid", userId);
				map.put("task_num", userId + "_" + String.valueOf(d.getTime()));
				map.put("task_pname", taskName);
				map.put("task_paddr", taskAddr);
				map.put("task_discrip", description);
				map.put("task_price", price);
				map.put("task_pnum", taskSize);
				map.put("task_term", term);
				map.put("task_start_time", d);
				map.put("task_end_time", taskPtime);
				map.put("task_create_time", d);
				map.put("task_update_time", d);
				map.put("fileIds", fileIds);

				// 项目关系信息
				map.put("typeIds", typeIds);
				map.put("labelIds", labelIds);

				return ydMangerServiceTask.$insert(map);
			} else {
				return new MapInitFactory("501", "请先登录！").getMap();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}
	}

	/***
	 * 雇主修改任务信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Object update(Integer taskId, String taskName, String taskAddr, String taskSize, String typeIds,
			String labelIds, String description, String term, float price, String filesPath, HttpSession session) {
		try {
			YdMangerUser user = (YdMangerUser) session.getAttribute("user");
			if (user != null) {

				Integer userId = user.getUser_id();

				Integer taskState = ydMangerServiceTask.$queryStateByEidAndTaskId(userId, taskId);
				if (taskState == 0) {
					Date d = new Date();
					HashMap<String, Object> map = new HashMap<String, Object>();
					// 项目信息
					map.put("task_id", taskId);
					map.put("task_uid", userId);
					map.put("task_num", userId + "_" + String.valueOf(d.getTime()));
					map.put("task_pname", taskName);
					map.put("task_paddr", taskAddr);
					map.put("task_discrip", description);
					map.put("task_price", price);
					map.put("task_pnum", taskSize);
					map.put("task_term", term);
					map.put("task_update_time", d);
					// 项目关系信息
					map.put("typeIds", typeIds);
					map.put("labelIds", labelIds);

					return ydMangerServiceTask.$update(map);
				} else {
					return new MapInitFactory().setMsg("502", "参数有误！").getMap();
				}
			} else {
				return new MapInitFactory().setMsg("501", "请先登录，在进行操作！").getMap();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}
	}

	/**
	 * 发布任务以及修改任务时删除文件接口
	 * 
	 * @param fileId
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/delTaskFile")
	public Object delFiles(@RequestParam("fileId") Integer fileId,@RequestParam("taskId") Integer taskId, HttpServletRequest request) {
		try {
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if (userVo != null) {
				return ydMangerServiceTask.$delTaskFile(fileId, taskId);
			} else {
				return new MapInitFactory("600", "对不起，您没有操作权限！").getMap();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}
	}

	@RequestMapping(value = "/startWorking")
	public Object startWorking(Integer taskId, HttpServletRequest request){
		try {
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if (userVo != null && userVo.getUser_type() == 0) {
				return ydMangerServiceTask.$updateTaskState(taskId, 4);
			} else {
				return new MapInitFactory("600", "对不起，您没有此功能操作权限！").getMap();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}
	}

	@RequestMapping(value = "/payBalance")
	public Object payBalance(Integer taskId, float price, String phone, String code, String payPwd,
			HttpServletRequest request) {
		try {
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if (userVo != null && userVo.getUser_type() == 0) {
				return ydMangerServiceTask.$updateTaskState(taskId, 5);
			} else {
				return new MapInitFactory("600", "对不起，您没有权限操作此功能！").getMap();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}
	}

	/***
	 * 修改任务中合同状态
	 * 
	 * @param taskId
	 * @param state
	 * @return
	 */
	@RequestMapping("/setContractState")
	public Object setContractState(Integer userId, Integer taskId, Integer state, HttpServletRequest request) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if (userVo != null && userVo.getUser_id() == userId) {
				int type = userVo.getUser_type();
				int isTask = ydMangerServiceTask.$isTask(taskId, userId, userVo.getUser_type());
				if (isTask == 1) {
					Integer taskContractState = ydMangerServiceTask.$queryContractStateBytaskId(taskId);
					if ((type == 0 && state != 3 && taskContractState != 3)
							|| ((type == 1 || type == 2) && taskContractState != 3 && state != 2)) {
						YdMangerTaskVo taskVo = ydMangerServiceTask.$queryById(taskId, userId);
						if(state == 2 && taskVo.getTask_host_price() < 100){
							mapInitFactory.setMsg("506", "最少需要托管100元佣金！");
						}else{
							MapInitFactory obj = ydMangerServiceTask.$updateTaskContractState(taskId, state);
							if (userVo.getUser_type() == 0) {
								obj.setDatas("url", "/list-guzhu/hetong/");
							} else {
								obj.setDatas("url", "/list-fuwushang/hetong/");
							}
							return obj.getMap();
						}
					} else {
						mapInitFactory.setMsg("505", "请求失败!");
					}
				} else {
					mapInitFactory.setMsg("506", "请求失败!");
				}
			} else {
				mapInitFactory.setMsg("600", "您没有登录!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}
	
	/***
	 * 添加任务文件夹
	 * @param taskId
	 * @param folderName
	 * @param request
	 * @return
	 */
	@RequestMapping("/addFolder")
	public Object addFolder(Integer taskId, String folderName, HttpServletRequest request){
		YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
		MapInitFactory mf = new MapInitFactory();
		try {
			if(userVo != null){
				return serviceTaskFolder.addFolder(taskId, folderName);
			}else{
				mf.setMsg("600", "请登录！");
			}
		} catch (Exception e) {
			mf.setSystemError();
			e.printStackTrace();
		}
		return mf.getMap();
	}
	
	public Object uploadToFolder(Integer taskId,Integer folderId,String path,String name,HttpServletRequest request){
		YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
		MapInitFactory mf = new MapInitFactory();
		try {
			if(userVo != null){
				YdMangerFiles file = new YdMangerFiles();
				file.setFiles_name(name);
				file.setFiles_path(path);
				int success = ydMangerServiceFiles.$insert(file);
				if(success > 0){
					YdMangerFilesTask filesTask = new YdMangerFilesTask();
					filesTask.setFiletr_fid(folderId);
					filesTask.setFiletr_tid(taskId);
					filesTask.setFiletr_id(file.getFiles_id());
					int isok = ydMangerServiceFilesTask.$insert(filesTask);
					if(isok > 0){
						mf.setMsg("200", "上传成功！");
					}else{
						mf.setMsg("502", "上传失败！");
					}
				}else{
					mf.setMsg("501", "上传失败！");
				}
			}else{
				mf.setMsg("600", "请登录！");
			}
		} catch (Exception e) {
			mf.setSystemError();
			e.printStackTrace();
		}
		return mf.getMap();
	}
	
	
	/***
	 * 雇主操作，完成任务（修改任务状态）
	 * @param taskId
	 * @param request
	 * @return
	 */
	@RequestMapping("carryOut")
	public Object carryOut(Integer taskId,HttpServletRequest request){
		MapInitFactory mf = new MapInitFactory();
		try {
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if(userVo != null && userVo.getUser_type() == 0){
				return ydMangerServiceTask.$updateTaskState(taskId, 5);
			}else{
				mf.setMsg("600", "对不起，您没有权限操作此功能！");
			}
		} catch (Exception e) {
			mf.setSystemError();
			e.printStackTrace();
		}
		return mf.getMap();
	}
	
	/**
	 * 获取文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	private String getFileType(String fileName) {
		if (fileName != null && fileName.indexOf(".") >= 0) {
			return fileName.substring(fileName.lastIndexOf("."), fileName.length());
		}
		return "";
	}

}