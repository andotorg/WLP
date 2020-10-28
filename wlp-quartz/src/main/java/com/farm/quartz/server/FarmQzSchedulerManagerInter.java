package com.farm.quartz.server;

import java.text.ParseException;

import org.quartz.SchedulerException;

import com.farm.quartz.domain.FarmQzScheduler;
import com.farm.quartz.domain.FarmQzTask;
import com.farm.quartz.domain.FarmQzTrigger;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.sql.query.DataQuery;

 
public interface FarmQzSchedulerManagerInter {
	 
	public FarmQzScheduler insertSchedulerEntity(FarmQzScheduler entity,
			LoginUser user);

	 
	public void startTask(String SchedulerId) throws ClassNotFoundException,
			ParseException, SchedulerException;

	 
	public void stopTask(String SchedulerId) throws SchedulerException;

	 
	public void start() throws SchedulerException, ParseException,
			ClassNotFoundException;

	 
	public FarmQzScheduler editSchedulerEntity(FarmQzScheduler entity,
			LoginUser user);

	 
	public void deleteSchedulerEntity(String entity, LoginUser user);

	 
	public FarmQzScheduler getSchedulerEntity(String id);

	 
	public DataQuery createSchedulerSimpleQuery(DataQuery query);

	 
	public boolean isRunningFindScheduler(String SchedulerId)
			throws SchedulerException;

	 
	public FarmQzTrigger insertTriggerEntity(FarmQzTrigger entity,
			LoginUser user);

	 
	public FarmQzTrigger editTriggerEntity(FarmQzTrigger entity, LoginUser user);

	 
	public void deleteTriggerEntity(String entity, LoginUser user);

	 
	public FarmQzTrigger getTriggerEntity(String id);

	 
	public DataQuery createTriggerSimpleQuery(DataQuery query);

	 
	public FarmQzTask insertTaskEntity(FarmQzTask entity, LoginUser user);

	 
	public FarmQzTask editTaskEntity(FarmQzTask entity, LoginUser user);

	 
	public void deleteTaskEntity(String entity, LoginUser user);

	 
	public FarmQzTask getTaskEntity(String id);

	 
	public DataQuery createTaskSimpleQuery(DataQuery query);

	 
	public FarmQzTask getTaskBySchedulerId(String schedulerId);
}