package com.lloyds.brownbag.amqp.trafficlights.publisher.web.springconfig;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.lloyds.brownbag.amqp.trafficlights.publisher.web")
public class WebConfig {

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/view/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("bunny.cloudamqp.com");
        connectionFactory.setUsername("xvjpxzlg");
        connectionFactory.setPassword("IXe4AHnR9vflfNZUS0bMTIbB2_dBEMcq");
        connectionFactory.setVirtualHost("xvjpxzlg");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setExchange("trafficExchange");
        return template;
    }
	
}
