package cn.liuwei.travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1. 强转格式
        HttpServletRequest request = (HttpServletRequest) req;
        //2. 获去资源的请求路径
        String uri = request.getRequestURI();
        //3. 判断是否是登录相关的资源
        if(uri.contains("/login.html")||uri.contains("/register.html")||uri.contains("/header.html")||uri.contains("/footer.html")||uri.contains("/checkCode")||uri.contains("/userServlet/login")||uri.contains("/userServlet/register")||
                uri.contains("/js/")||uri.contains("/css/")||uri.contains("/fonts/")||uri.contains("/error/")||uri.contains("/images/")||uri.contains("/img/")
                ||uri.contains("/userServlet/findUser")||uri.contains("/categoryServlet/findAll")){
            // 包含说明用户就是想要去登录，放行
            chain.doFilter(req, resp);
        }else{
            //不包含说明用户不是想要去登录,判断用户是否登录
            //从session中获取user
            Object obj = request.getSession().getAttribute("user");
            if(obj != null){
                //说明用户已经登录了，放行
                chain.doFilter(req, resp);
            }else{
                request.getSession().setAttribute("error","你尚未登录，请登录");
                request.getRequestDispatcher("/login.html").forward(request,resp);
            }

        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
