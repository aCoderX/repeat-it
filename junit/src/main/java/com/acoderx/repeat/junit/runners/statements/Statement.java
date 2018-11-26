package com.acoderx.repeat.junit.runners.statements;

/**
 * Description:用于抽象测试项的运行
 *
 * @author  xudi
 * @since  2018-10-30
 */
public abstract class Statement {
    public abstract void evaluate() throws Throwable;
}
