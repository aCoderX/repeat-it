package com.aoderx.spring.context.entity;

import com.aoderx.spring.context.stereotype.Component;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-09
 */
@Component
public class TestBean {
    private String foo = "foo";

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }
}
