package com.acoderx.repeat.junit.runner;

/**
 * Description: runner接口,决定了怎么去运行测试用例
 * 如SpringRunner、MockitoJUnitRunner等 都是通过扩展该接口，去加载上下文或者初始化等操作
 *
 * @author  xudi
 * @since  2018-10-29
 */
public interface Runner extends Describable {
    Result run();
}
