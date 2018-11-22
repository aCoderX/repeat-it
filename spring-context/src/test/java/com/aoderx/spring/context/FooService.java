package com.aoderx.spring.context;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-22
 */
public class FooService {
    private TestBean testBean;

    public void bar() {
        System.out.println("in fooService:"+testBean.getFoo());
    }
}
