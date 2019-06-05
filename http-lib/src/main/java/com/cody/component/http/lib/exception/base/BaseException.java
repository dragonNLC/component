/*
 * ************************************************************
 * 文件：BaseException.java  模块：http-lib  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-lib
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.lib.exception.base;


import com.cody.component.http.lib.config.HttpCode;

/**
 * Created by xu.yi. on 2019/4/6.
 *
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -4760213625687945508L;
    private int errorCode = HttpCode.CODE_UNKNOWN;

    public BaseException() {
    }

    public BaseException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}