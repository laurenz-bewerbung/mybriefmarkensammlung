package de.lm.mybriefmarkensammlung.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.support.MultipartFilter;

@Configuration
public class ServletFilterConfiguration {

    // Multipart has to be resolved before csrf check
    @Bean
    public FilterRegistrationBean<MultipartFilter> multipartFilterRegistration() {
        FilterRegistrationBean<MultipartFilter> registration = new FilterRegistrationBean<>();

        MultipartFilter filter = new MultipartFilter();
        filter.setMultipartResolverBeanName("multipartResolver");
        registration.setFilter(filter);

        registration.setOrder(Integer.MIN_VALUE + 1); // before security
        return registration;
    }

    // UTF-8 encoding has to be done before resolving multipart
    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilterRegistration() {
        FilterRegistrationBean<CharacterEncodingFilter> registration = new FilterRegistrationBean<>();
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        registration.setFilter(filter);
        registration.setOrder(Integer.MIN_VALUE); // before multipart
        return registration;
    }
}
