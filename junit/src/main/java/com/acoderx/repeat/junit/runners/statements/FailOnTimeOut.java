package com.acoderx.repeat.junit.runners.statements;

import java.util.concurrent.*;

/**
 * Description: statement的装饰者，超时退出
 *
 * @author  xudi
 * @since  2018-10-30
 */
public class FailOnTimeOut extends Statement {

    private TimeUnit timeUnit;
    private long timeout;
    private Statement statement;

    @Override
    public void evaluate() throws Throwable {
        CallableStatement callableStatement = new CallableStatement();
        FutureTask<Throwable> task = new FutureTask<>(callableStatement);

        Thread thread = new Thread(task);
        //设置为守护线程，从而及时退出
        thread.setDaemon(true);
        thread.start();

        try {
            Throwable throwable = task.get(timeout, timeUnit);
            if (throwable != null) {
                throw throwable;
            }
        } catch (InterruptedException | ExecutionException e) {
            throw e;
        } catch (TimeoutException e) {
            System.out.println("超时了");
            throw e;
        }

    }

    //建造者模式
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private FailOnTimeOut failOnTimeOut = new FailOnTimeOut();
        private Builder() {
        }

        public Builder withTimeout(TimeUnit unit, long timeout) {
            failOnTimeOut.timeout = timeout;
            failOnTimeOut.timeUnit = unit;
            return this;
        }

        public FailOnTimeOut build(Statement statement) {
            failOnTimeOut.statement = statement;
            return failOnTimeOut;
        }

    }

    private class CallableStatement implements Callable<Throwable> {
        private final CountDownLatch countDownLatch = new CountDownLatch(1);
        @Override
        public Throwable call() {
            countDownLatch.countDown();
            try {
                statement.evaluate();
            } catch (Throwable throwable) {
                return throwable;
            }
            return null;
        }

    }

}
