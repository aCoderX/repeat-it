package com.acoderx.repeat.junit.rules;

import com.acoderx.repeat.junit.runners.statements.Statement;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-08
 */
public interface TestRule {
    Statement apply(Statement statement);
}
