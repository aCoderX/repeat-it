package com.aoderx.spring.context.support;

/**
 * Description:可以设置config locations（加载的配置文件）
 *
 * @author xudi
 * @since 2018-12-11
 */
public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext {
    private String[] configLocations;

    protected String[] getConfigLocations() {
        return configLocations;
    }

    public void setConfigLocations(String[] configLocations) {
        this.configLocations = configLocations;
    }

}
