package com.acoderx.spring.context.entity;

import com.acoderx.beans.factory.annotation.Autowired;
import com.acoderx.spring.context.stereotype.Component;

/**
 * Description:
 *
 * @author  xudi
 * @since  2018-11-22
 */
@Component
public class FooService {
    @Autowired
    private TestBean testBean;

    public FooService() {
    }

    public FooService(TestBean testBean) {
        this.testBean = testBean;
    }

    public void bar() {
        System.out.println("in fooService:"+testBean.getFoo());
    }

    public TestBean getTestBean() {
        return testBean;
    }
}
