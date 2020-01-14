package com.leyou.fileter;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.gateway.config.FileterProperties;
import com.leyou.gateway.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import sun.misc.Request;
import utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Component
@EnableConfigurationProperties({JwtProperties.class, FileterProperties.class})
public class LoginFilter extends ZuulFilter {

    @Autowired
    private  JwtProperties jwtProperties;

    @Autowired
    private  FileterProperties fileterProperties;

    @Override
    public String filterType() {
        //请求在路由之前被执行
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //请求头之前查看请求参数
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        //获取请求的上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //获取request请求
        HttpServletRequest request = currentContext.getRequest();
        String requestURI=request.getRequestURI();
        System.out.println(requestURI);

        //读取配置获取白名单
        List<String> allowPaths = fileterProperties.getAllowPaths();
        for (String url:allowPaths){
            //请求地址包含白名单
            if(requestURI.startsWith(url)){
                return false;
            }
        }

        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //第一步：获取cookie
            //获取请求的上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
            //获取request请求
        HttpServletRequest request = currentContext.getRequest();
            //从request中拿出cookie

        //下一步:解密
        try {
            String s = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
            UserInfo infoFromToken = JwtUtils.getInfoFromToken(s, jwtProperties.getPublicKey());
        } catch (Exception e) {
            //用户没有登陆
            //用户登录，token假的
            //不存在，未登录，则拦截
            currentContext.setSendZuulResponse(false);
            //返回403
            currentContext.setResponseStatusCode(403);
            e.printStackTrace();
        }
        return null;
    }
}
