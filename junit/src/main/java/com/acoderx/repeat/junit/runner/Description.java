package com.acoderx.repeat.junit.runner;

import java.io.Serializable;

/**
 * Description:对于测试项目的描述
 *
 * @author  xudi
 * @since  2018-10-29
 */
public class Description {
    private final String fDisplayName;
    private volatile Class fTestClass;

    private final Serializable fUniqueId;

    public Description(Class fTestClass) {
        this.fDisplayName = fTestClass.getName();
        this.fTestClass = fTestClass;
        this.fUniqueId = fTestClass.getName();
    }

    public Description(Class fTestClass, String name) {
        this.fDisplayName = formatDisplayName(name, fTestClass.getName());
        this.fTestClass = fTestClass;
        this.fUniqueId = formatDisplayName(name, fTestClass.getName());
    }


    public String getfDisplayName() {
        return fDisplayName;
    }

    public Class getfTestClass() {
        return fTestClass;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Description)) {
            return false;
        }
        if (((Description) obj).fUniqueId.equals(this.fUniqueId)) {
            return true;
        }
        return false;
    }

    private static String formatDisplayName(String name, String className) {
        return String.format("%s(%s)", name, className);
    }

}
