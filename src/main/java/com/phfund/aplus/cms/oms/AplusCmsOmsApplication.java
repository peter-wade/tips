package com.phfund.aplus.cms.oms;

import com.phfund.aplus.framework.base.constants.LogConstants;
import com.phfund.aplus.framework.base.utils.IdGenerate;
import com.phfund.aplus.oms.boot.core.util.AplusRestTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * SpringBoot方式启动类
 *
 * @author x_dengjicai
 * @Date 2017/5/21 12:06
 */
@EnableEurekaClient
@SpringBootApplication
@ComponentScan("com.phfund.aplus")
@MapperScan({"com.phfund.aplus.**.mapper","com.phfund.aplus.**.dao"})
public class AplusCmsOmsApplication extends WebMvcConfigurerAdapter {
 
    protected final static Logger logger = LoggerFactory.getLogger(AplusCmsOmsApplication.class);
    /**
     * 由于我们在controller中注入了RestTemplate
     * 所以启动的时候需要实例化该类的一个实例  
     */
	public static void main(String[] args) {
		
		MDC.put(LogConstants.BIZ_SEQ_NO, "SysStart"+IdGenerate.getNextId());
		try {
			long start = System.currentTimeMillis();
			logger.info("aplus-cms-oms servie start");
			System.getProperties().put("spring.application.name", "aplus-cms-oms");
			SpringApplication.run(AplusCmsOmsApplication.class, args);
			logger.info("aplus-cms-oms servie start finish,cost:{}ms", System.currentTimeMillis() - start);
		}finally {
			MDC.clear();
		}

	}
    
    
    /**
     * 使用RestTemplateBuilder来实例化RestTemplate对象
     * spring默认已经注入了RestTemplateBuilder实例  
     * @return
     */
	@Bean
	@LoadBalanced
	public AplusRestTemplate restTemplate(RestTemplateBuilder builder) {
		// Do any additional configuration here
		// 设定超时时间
		builder.setReadTimeout(10000).setConnectTimeout(10000);
		AplusRestTemplate template =  builder.build(AplusRestTemplate.class);
		return template ;
	}



}
