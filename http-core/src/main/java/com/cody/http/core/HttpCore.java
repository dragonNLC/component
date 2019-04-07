/*
 * ************************************************************
 * 文件：HttpCore.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月07日 12:28:20
 * 上次修改时间：2019年04月06日 02:01:01
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core;

import android.content.Context;

import com.cody.http.cat.HttpCat;
import com.cody.http.lib.exception.HttpCoreNotInitializedException;

import java.lang.ref.WeakReference;

/**
 * Created by xu.yi. on 2019/4/6.
 */
public class HttpCore {

    private static class InstanceHolder {
        private static final HttpCore INSTANCE = new HttpCore();
    }

    public static HttpCore getInstance() {
        if (InstanceHolder.INSTANCE.getContext() == null) {
            throw new HttpCoreNotInitializedException();
        }
        return InstanceHolder.INSTANCE;
    }

    private WeakReference<Context> mContextWeakReference;

    public Context getContext() {
        if (mContextWeakReference == null) return null;
        return mContextWeakReference.get();
    }

    /**
     * 必须在application中初始化
     */
    public static HttpCore init(Context context) {
        HttpCore.getInstance().mContextWeakReference = new WeakReference<>(context);
        return getInstance();
    }

    /**
     * 默认关闭log
     */
    public HttpCore withLog(boolean log) {
        RetrofitManagement.getInstance().setLog(log);
        return this;
    }

    /**
     * 获取HttpCat
     */
    public HttpCat getHttpCat() {
        return HttpCat.getInstance();
    }

    /**
     * 杀死HttpCat
     */
    public HttpCore killHttpCat() {
        RetrofitManagement.getInstance().setHttpCatInterceptor(null);
        return this;
    }

    /**
     * 默认关闭log
     */
    public HttpCore withHttpCat(boolean cat) {
        if (cat) {
            RetrofitManagement.getInstance().setHttpCatInterceptor(HttpCat.create(getContext()));
        } else {
            RetrofitManagement.getInstance().setHttpCatInterceptor(null);
        }
        return this;
    }

    public void done() {
        // do nothing
    }
}