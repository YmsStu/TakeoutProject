package com.itheima.takeout.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.itheima.takeout.MyApplication;
import com.itheima.takeout.model.dao.bean.AddressBean;
import com.itheima.takeout.model.dao.bean.UserBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Teacher on 2016/9/2.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASENAME = "itheima.db";
    private static final int DATABASEVERSION = 1;


    //创建一个hashmap存储多个Dao对象
    private HashMap<String,Dao> hashMap = new HashMap<>();

    private static DBHelper dbHelper = null;


    private DBHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    /**
     * 单例处理
     * 双重校验：提高效率
     * 如果在方法上加锁，每次调用都需要排队
     */

    private static DBHelper instance;


    public static DBHelper getInstance() {
        if (instance == null) {// 第一次校验：提高效率
            // 考虑加锁
            synchronized (DBHelper.class) {
                if (instance == null) {// 第二次校验：防止对象的多次创建
                    instance = new DBHelper(MyApplication.getContext());
                    //instance = new DBHelper(ctx);
                    instance.getWritableDatabase();
                }
            }
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //原生创建表做法,编写sql语句
        //使用ormlite以后只需要调用以下方法
        //传递进来的javabean,以及由注解替代掉了原有的sql语句
        try {
            TableUtils.createTable(connectionSource, UserBean.class);
            TableUtils.createTable(connectionSource, AddressBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
/*
    //获取操作UserInfo表的dao对象,多个,项目中有多张表,每一个dao对象对应一张表进行操作
    public Dao getDao(Class clazz){
        Dao dao = null;
        String clazzName = clazz.getSimpleName();
        dao = hashMap.get(clazzName);
        if (dao==null){
            //获取clazz对应表的dao操作对象
            try {
                dao = super.getDao(clazz);
                hashMap.put(clazzName,dao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dao;
    }
    //销毁所有dao
    @Override
    public void close() {
        //遍历hashMap清空dao对象
        for (String key: hashMap.keySet()) {
            Dao dao = hashMap.get(key);
            dao = null;
        }
        super.close();
    }*/

}
