package com.acoderx.repeat.junit;

import com.acoderx.repeat.junit.rules.TimeOut;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-10-25
 */
public class UserServiceTest {
    @Rule
    public TimeOut timeOut = new TimeOut(1, TimeUnit.SECONDS);

    UserService userService = new UserService();
    @Test
    public void sumUserNum() {
        System.out.println(userService.sumUserNum());
    }

    @Test
    public void doNothing() {
        System.out.println("do nothing...");
    }

    @Before
    public void sumUserNumBefore() {
        System.out.println("before method run...");
    }

    @Test
    public void sumUserNumTimeOut() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println(userService.sumUserNum());
    }
}
