package org.springframework.samples.petclinic.security;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.stereotype.Component;
 
// Enable it for Servlet 3.x implementations
/* @ WebFilter(asyncSupported = true, urlPatterns = { "/*" }) */
@Component

public class CORSFilter implements Filter {
	
	private final Logger log = LoggerFactory.getLogger(CORSFilter.class);
 
    public CORSFilter() {
        log.info("=============CORSFilter constructor=====================================");
    }
 
    public void destroy() {
        log.info("=============CORSFilter destroy()=====================================");
    }
 
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
    	
    	log.info("=======================================================================");
        log.info("=============CORSFilter doFilter()=====================================");
        log.info("=======================================================================");
 
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("CORSFilter HTTP Request: " + request.getMethod());
        
        Enumeration<String> headerNames = request.getHeaderNames();
    	while (headerNames.hasMoreElements()) {
    		String headerName = headerNames.nextElement();
    		log.debug("Header Name:  " + headerName);
    		String headerValue = request.getHeader(headerName);
    		log.debug(" , Header Value:  " + headerValue);
    		log.debug(" ");
    	}
        
    	log.debug("request.getHeader(Content-Type)");
        log.debug(request.getHeader("Content-Type"));
        
        log.debug("request.getHeader(content-type)");
        log.debug(request.getHeader("content-type"));
        
        log.debug("request.getHeader(authorization)");
        log.debug(request.getHeader("authorization"));
        
        
 
        // Authorize (allow) all domains to consume the content
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Credentials", "true");
        
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Max-Age", "10800");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", "*");
        // ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", "access-control-allow-methods, access-control-allow-origin, Content-Type, Accept, X-Requested-With, remember-me, Authorization");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", "content-type, accept, x-requested-with, authorization");
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
 
        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
        if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }
 
        // pass the request along the filter chain
        chain.doFilter(request, servletResponse);
    }
 
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }
    
    @Bean
    public FilterRegistrationBean CORSFilterRegistration(CORSFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

}