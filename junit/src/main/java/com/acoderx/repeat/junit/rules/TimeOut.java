package com.acoderx.repeat.junit.rules;

import com.acoderx.repeat.junit.runners.statements.FailOnTimeOut;
import com.acoderx.repeat.junit.runners.statements.Statement;

import java.util.concurrent.TimeUnit;

/**
 * Description: rule：超时结束
 *
 * @author  xudi
 * @since  2018-11-08
 */
public class TimeOut implements TestRule {
    private final long timeout;
    private final TimeUnit timeUnit;

    public TimeOut(long timeout, TimeUnit timeUnit) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    @Override
    public Statement apply(Statement statement) {
        return FailOnTimeOut.builder().withTimeout(TimeUnit.SECONDS, 1).build(statement);
    }
}
