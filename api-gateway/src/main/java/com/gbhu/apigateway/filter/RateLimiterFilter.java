package com.gbhu.apigateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 订单限流
 */
@Component
public class RateLimiterFilter extends ZuulFilter {

    //每秒产生100个令牌
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 限流要在最前面,源码中过滤器最小是-3
        return -4;
    }

    @Override
    public boolean shouldFilter() {
        //前中后过滤器 共享 RequestContext 对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //只对订单限流
        if ("/apigateway/order/api/v1/order/save".equals(request.getRequestURI())) {
            //禁止访问
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        //前中后过滤器 共享 RequestContext 对象
        RequestContext requestContext = RequestContext.getCurrentContext();

        if (!RATE_LIMITER.tryAcquire()) {
            //限流
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        }

        return null;
    }
}
