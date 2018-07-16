package com.phfund.aplus.cms.oms;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * aplus Web程序启动类
 *
 * @author fengshuonan
 * @date 2017-05-21 9:43
 */
public class AplusServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AplusCmsOmsApplication.class);
    }

}
