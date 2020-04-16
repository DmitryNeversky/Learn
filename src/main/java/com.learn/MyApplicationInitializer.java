package com.learn;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;

@Configuration
public class MyApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private final CustomSessionEventListener customSessionEventListener;

    public MyApplicationInitializer(CustomSessionEventListener customSessionEventListener) {
        this.customSessionEventListener = customSessionEventListener;
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[0];
    }

    @Override
    protected void registerDispatcherServlet(ServletContext servletContext) {
        super.registerDispatcherServlet(servletContext);
        servletContext.addListener(customSessionEventListener);
    }
}
