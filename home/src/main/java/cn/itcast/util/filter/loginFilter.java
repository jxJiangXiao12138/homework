package cn.itcast.util.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.SoundbankResource;
import java.io.IOException;
import java.net.URL;

/**
 * @Auther : 32725
 * @Date: 2019/2/11 17:33
 * @Description: 登陆过滤器
 */
public class loginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String URI = request.getRequestURI();
        if (URI.contains("/js")||URI.contains("/css")||URI.contains("/fonts")||URI.contains("/login")|| URI.contains("/checkCode")){
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute("user")==null){
            request.getRequestDispatcher("/WEB-INF/page/login.jsp").forward(request, response);
            return;
        }else {
            chain.doFilter(request, response);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
