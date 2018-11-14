package com.aoderx.spring.context.annotation;

import com.aoderx.spring.context.TestBean;
import org.junit.Test;

public class AnnotationConfigApplicationContextTest {
    @Test
    public void test(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutowiredMethodConfig.class);
        TestBean testBean = applicationContext.getBean(TestBean.class);
        System.out.println(testBean.getFoo());
    }


    @Configuration
    static class AutowiredMethodConfig {
        @Bean
        public TestBean testBean() {
            return new TestBean();
        }
    }
}