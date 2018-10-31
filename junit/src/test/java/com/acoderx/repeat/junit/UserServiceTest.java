package com.acoderx.repeat.junit;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-10-25
 */
public class UserServiceTest {
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
}
