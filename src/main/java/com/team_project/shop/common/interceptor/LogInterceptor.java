package com.team_project.shop.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.logging.Logger;

/*
    사용자가 요청 할 경우 접근 정보 로그 등을 확인
 */
@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

    //컨트롤러 보내기 전 이벤트 작동(false -> 컨트롤러로 요청을 안함)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(">>>>>>>>>>>>>>>>>>>>> Before Controller <<<<<<<<<<<<<<<<<<<<<<<<");
        log.info(" Class        : {}", handler.getClass());
        log.info(" Http Method  : {}", request.getMethod());
        log.info(" Request URI  : {}", request.getRequestURI());
        log.info(" Query        : {}", request.getQueryString());
        Enumeration<String> paramNames = request.getParameterNames();
        
        while(paramNames.hasMoreElements()){
            String key = (String) paramNames.nextElement();
            String value = request.getParameter(key);
            log.info("## RequestParameter: " + key + "=" + value);
        }
        return true;
    }

    //컨트롤러 처리 이후 이벤트 작동(dispatcherServlet이 view처리 이전)
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("================= After Controller ====================");
        log.info(" Response Statu: {}", response.getStatus());
    }

    //dispatcherServlet이 view까지 완료된 상태에서 처리
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("================= complete =================");
    }
}
