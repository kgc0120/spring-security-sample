package com.sample.security.form;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author bumblebee
 */
@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "bumblebee", roles = "USER")
public @interface WithUser {
}
