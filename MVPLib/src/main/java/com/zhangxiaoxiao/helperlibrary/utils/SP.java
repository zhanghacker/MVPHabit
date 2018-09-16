package com.zhangxiaoxiao.helperlibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 基础参数的操作
 */
public class SP {

    private static final String DBName = "zhangxiaoxiao"; // 基础参数放置的数据库名称
    private static SP instance = null;
    private SharedPreferences sp;

    public SP(Context context) {
        sp = context.getSharedPreferences(DBName, Context.MODE_PRIVATE);
        instance = this;
    }

    public static SP getInstance(Context context) {
        if (instance == null) return new SP(context);
        return instance;
    }

    /**
     * 获取SP的实例
     */
    public SharedPreferences getSP() {
        return sp;
    }

    /**
     * 获取字符串
     *
     * @param key key值，要求全局唯一
     * @return 返回 Key 对应的 value，默认为 null
     */
    public String getString(String key) {
        return sp.getString("String-" + key, null);
    }

    /**
     * 获取字符串
     *
     * @param key      key值，要求全局唯一
     * @param defValue 默认值
     * @return 返回 Key 对应的 value，默认为 null
     */
    public String getString(String key, String defValue) {
        return sp.getString("String-" + key, defValue);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("String-" + key, value);
        editor.commit();
    }

    /**
     * @param key
     * @return
     */
    public Boolean getBoolean(String key) {
        return sp.getBoolean("Boolean-" + key, false);
    }

    public Boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean("Boolean-" + key, defValue);
    }

    public void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("Boolean-" + key, value);
        editor.commit();
    }

    public Float getFloat(String key) {
        return sp.getFloat("Float-" + key, 0f);
    }

    public void putFloat(String key, Float value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("Float-" + key, value);
        editor.commit();
    }

    public Integer getInt(String key, int defaultValue) {
        return sp.getInt("Int-" + key, defaultValue);
    }

    public Integer getInt(String key) {
        return getInt(key, 0);
    }

    public void putInt(String key, Integer value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Int-" + key, value);
        editor.commit();
    }

    public Long getLong(String key) {
        return sp.getLong("Long-" + key, 0);
    }

    public void putLong(String key, Long value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("Long-" + key, value);
        editor.commit();
    }

    public Set<String> getStringSet(String key) {
        return sp.getStringSet("StringSet-" + key, new HashSet<String>());
    }

    public void putStringSet(String key, Set<String> value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet("StringSet-" + key, value);
        editor.commit();
    }

    /**
     * 使用 fastJson 转成 json 数据存储
     *
     * @param key
     * @param value
     */
    public void putObject(String key, Object value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Object-" + key, value == null ? null : JSON.toJSONString(value));
        editor.commit();
    }

    /**
     * 获取存储的对象, 获取不到则返回 null
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getObject(String key, Class<T> clazz) {
        String value = sp.getString("Object-" + key, null);
        if (null == value) {
            return null;
        }
        return JSON.parseObject(value, clazz);
    }

    public void putList(String key, List list) {
        String value = (list == null || list.size() == 0) ? "" : JSON.toJSONString(list);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("List-" + key, value);
        editor.commit();
    }

    public <T> List<T> getList(String key, Class<T> clazz) {
        String value = sp.getString("List-" + key, "");
        if (TextUtils.isEmpty(value)) return new ArrayList<T>();
        try {
            return JSON.parseArray(value, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<T>();
        }
    }

}