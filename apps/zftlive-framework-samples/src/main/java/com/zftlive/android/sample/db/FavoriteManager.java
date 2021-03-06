/*
 *     Android基础开发个人积累、沉淀、封装、整理共通
 *     Copyright (c) 2016. 曾繁添 <zftlive@163.com>
 *     Github：https://github.com/zengfantian || http://git.oschina.net/zftlive
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.zftlive.android.sample.db;

import java.sql.Savepoint;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.DatabaseConnection;
import com.zftlive.android.library.db.DatabaseHelper;
import com.zftlive.android.sample.IContant;

/**
 * 信息搜藏业务控制
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class FavoriteManager {

	private DatabaseHelper dbHelper;
	private String TAG = "FavoriteManager";
	
	/**
	 * 初始化收藏业务控制
	 * @param mContext
	 */
	public FavoriteManager(Context mContext) {
		dbHelper = DatabaseHelper.gainInstance(mContext, IContant.DB_NAME, IContant.DB_VERSION);
	}
	
	public int insert(Favorite model) {
		
		//返回受影响行数
		int rows = 0;
		
		String savePointName = "FavoriteInsert";
		Savepoint savePoint = null;
		DatabaseConnection connection = null;
		try {
			//获取数据库连接
		    connection = dbHelper.getConnection(Favorite.class.getSimpleName());
			connection.setAutoCommit(false);
			savePoint = connection.setSavePoint(savePointName);
			
			//插入数据
			Dao<Favorite, String> dao = dbHelper.getDao(Favorite.class);
			rows = dao.create(model);
			
			//提交事务
			connection.commit(savePoint);
			
		} catch (Exception e) {
			//回滚事务
			dbHelper.rollback(connection,savePoint);
			
			Log.e(TAG, "插入数据失败，原因："+e.getMessage());
		}finally{
			//释放数据库连接
			dbHelper.closeConnection(connection);
		}
		
		return rows;
	}

	public int insertBatch(final List<Favorite> data) {
		// 返回受影响行数
		int rows = 0;
		
		long start = System.currentTimeMillis();
		
		String savePointName = "FavoriteinsertBatch";
		Savepoint savePoint = null;
		DatabaseConnection connection = null;
		try {
			// 获取数据库连接
			connection = dbHelper.getConnection(Favorite.class.getSimpleName());
			connection.setAutoCommit(false);
			savePoint = connection.setSavePoint(savePointName);

			// 插入数据
			final Dao<Favorite, String> dao = dbHelper.getDao(Favorite.class);

			for (Favorite favorite : data) {
				rows += dao.create(favorite);
			}

//			rows = dao.callBatchTasks(new Callable<Integer>() {
//
//				@Override
//				public Integer call() throws Exception {
//					int rows = 0;
//					for (Favorite favorite : data) {
//						rows += dao.create(favorite);
//					}
//					return rows;
//				}
//			});

			// 提交事务
			connection.commit(savePoint);

		} catch (Exception e) {
			// 回滚事务
			dbHelper.rollback(connection, savePoint);

			Log.e(TAG, "插入数据失败，原因：" + e.getMessage());
		} finally {
			// 释放数据库连接
			dbHelper.closeConnection(connection);
		}
		
		long end = System.currentTimeMillis();
		Log.e(TAG, "操作耗时：" + (start-end)/1000 );

		return rows;
	}

	public void delete(Favorite model) {

	}

	public void update(Favorite model) {

	}

	public Favorite findById(String strPK) {
		return null;
	}

	public List<Favorite> findByCondtion() {
		return null;
	}
	
	public List<Favorite> findAllPagenation(final long offset, final long limit){
		return null;
	}
}
