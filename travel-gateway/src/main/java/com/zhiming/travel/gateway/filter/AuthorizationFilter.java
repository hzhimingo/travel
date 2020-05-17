package com.zhiming.travel.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
//@Component
public class AuthorizationFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("start authorization");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if (isNeedAuth(request)) {
            TokenInfo tokenInfo  = (TokenInfo) request.getAttribute("tokenInfo");
            if (tokenInfo != null && tokenInfo.isActive()) {
                if (!hasPermission(tokenInfo, request)) {
                    log.info("fail 403");
                    handleError(403, requestContext);
                }
                //在这里放置需要的信息
            } else {
                if (!StringUtils.startsWith(request.getRequestURI(), "/authorize/oauth/token")) {
                    log.info("fail 401");
                    handleError(401, requestContext);
                }
            }
        }
        return null;
    }

    private void handleError(int status, RequestContext requestContext) {
        requestContext.getResponse().setContentType("application/json");
        requestContext.setResponseStatusCode(status);
        requestContext.setResponseBody("{\"message\": \"auth fail\"}");
        requestContext.setSendZuulResponse(false);
    }

    private boolean hasPermission(TokenInfo tokenInfo, HttpServletRequest request) {
        return true;
    }

    //填写需要token就能访问的判断
    private boolean isNeedAuth(HttpServletRequest request) {
        return true;
    }
}
