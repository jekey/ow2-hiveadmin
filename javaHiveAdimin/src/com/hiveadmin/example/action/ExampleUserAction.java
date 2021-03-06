package com.hiveadmin.example.action;
import org.apache.hadoop.hive.jdbc.HiveDriver;
import org.apache.hadoop.hive.jdbc.HiveDataSource;

import java.sql.ResultSet;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hiveadmin.hive.HiveConst;
import org.hiveadmin.hive.beans.HistoryRecord;
import org.hiveadmin.hive.dao.impl.UserHistoryLog;
import org.hiveadmin.hive.service.HiveDatabaseService;
import org.springframework.stereotype.Component;

import com.hiveadmin.example.beans.UserExample;
import com.hiveadmin.example.service.ExampleUserService;
import com.hiveadmin.example.service.impl.ExampleUserServiceImpl;
import com.opensymphony.xwork2.ActionSupport;
@Component
public class ExampleUserAction extends ActionSupport{

	private String msg;
	private ExampleUserService exampleUserService;
	private HiveDatabaseService hiveDatabaseService;
	private UserHistoryLog userHistoryLog;
	
	public UserHistoryLog getUserHistoryLog() {
		return userHistoryLog;
	}
	@Resource
	public void setUserHistoryLog(UserHistoryLog userHistoryLog) {
		this.userHistoryLog = userHistoryLog;
	}
	Logger log = Logger.getLogger(this.getClass());
	
	
	public ExampleUserService getExampleUserService() {
		return exampleUserService;
	}
	@Resource(name="exampleUserServiceImpl")
	public void setExampleUserService(ExampleUserService exampleUserService) {
		this.exampleUserService = exampleUserService;
	}
	@Resource(name="hiveDatabaseServiceImpl")
	public void setHiveDatabaseService(HiveDatabaseService hiveDatabaseService){
		this.hiveDatabaseService = hiveDatabaseService;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String login() throws Exception {
		
		
		msg="hello wangjie";
		try {
			//exampleUserService.register(new UserExample(1,"liguyi"));
			//hiveDatabaseService.createDatebase("xxxxx", null, null, null);
			//hiveDatabaseService.listDatabase();
			//hiveDatabaseService.deleteDatebase("xxxxx",HiveConst.RESTRICT);
			//hiveDatabaseService.listDatabase();
			HistoryRecord historyRecord = new HistoryRecord();
			historyRecord.setOp_user_name((String) ServletActionContext.getContext().getSession().get("user"));
			historyRecord.setOp_desc("create database.");
			hiveDatabaseService.setHistoryRecord(historyRecord);
			hiveDatabaseService.createDatebase("xxxxx", null, null, null);

		}catch(Exception e){
			log.error("catch exception when regist user. msg:"+e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}
}
