package com.acoderx.repeat.junit.simplest;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:最简单的返回结果处理
 *
 * @author: xudi
 * @since: 2018-10-26
 */
public class Result {
    List<Throwable> failure = new ArrayList<>();


    public List<Throwable> getFailure() {
        return failure;
    }

    public void setFailure(List<Throwable> failure) {
        this.failure = failure;
    }

    public void mergeResult(Result result) {
        this.getFailure().addAll(result.getFailure());
    }
}
