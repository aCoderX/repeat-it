package com.acoderx.repeat.junit.runners;

import com.acoderx.repeat.junit.Before;
import com.acoderx.repeat.junit.Rule;
import com.acoderx.repeat.junit.Test;
import com.acoderx.repeat.junit.rules.TestRule;
import com.acoderx.repeat.junit.runner.Description;
import com.acoderx.repeat.junit.runner.notification.RunNotifier;
import com.acoderx.repeat.junit.runners.statements.InvokeMethod;
import com.acoderx.repeat.junit.runners.statements.RunBefores;
import com.acoderx.repeat.junit.runners.statements.Statement;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:runner的实现，用来抽象类层次的runner，会包含多个子测试项（被@Test注解了的方法）
 * 设计模式：composite中的子节点
 *
 * @author: xudi
 * @since: 2018-10-29
 */
public class BlockJUnit4ClassRunner extends ParentRunner<Method> {

    public BlockJUnit4ClassRunner(Class aClass) {
        super(aClass);
    }

    /**
     * 返回被Test注解了的方法
     * @return
     */
    @Override
    List<Method> getChildren() {
        List<Method> methodList = new ArrayList<>();
        Method[] methods = getTestClass().getMethods();
        for (Method method : methods) {
            Test test = method.getAnnotation(Test.class);
            if (test != null) {
                methodList.add(method);
            }
        }
        return methodList;
    }

    /**
     * 运行测试方法
     * 设计模式：
     *  装饰者模式：将statement经过相应的装饰
     * @param child
     * @param notifier
     */
    @Override
    void runChild(Method child, RunNotifier notifier) {
        try {
            Object target = getTestClass().newInstance();
            Statement statement = new InvokeMethod(child, target);
            statement = withBefores(target, statement);
            statement = withRules(target, statement);
            statement.evaluate();
        } catch (Throwable throwable) {
            notifier.fireTestFailure(throwable);
        }

    }

    /**
     * 返回对方法的描绘信息
     * @param child
     * @return
     */
    @Override
    Description describeChild(Method child) {
        return new Description(getTestClass(), child.getName());
    }

    private Statement withBefores(Object target, Statement statement) {
        List<Method> beforeMethods = new ArrayList<>();
        Method[] methods = getTestClass().getMethods();
        for (Method method : methods) {
            if (method.getAnnotation(Before.class)!=null) {
                beforeMethods.add(method);
            }
        }
        if (beforeMethods.size() > 0) {
            statement = new RunBefores(beforeMethods, target, statement);
        }
        return statement;
    }

    private Statement withRules(Object target, Statement statement) {
        List<TestRule> rules = new ArrayList<>();
        Field[] fields = getTestClass().getFields();
        for (Field field : fields) {
            if (field.getAnnotation(Rule.class)!=null) {
                try {
                    Object fieldValue = field.get(target);
                    if (fieldValue instanceof TestRule) {
                        rules.add((TestRule) fieldValue);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        if (rules.size() > 0) {
            for (TestRule rule : rules) {
                statement = rule.apply(statement);
            }
        }
        return statement;
    }
}
