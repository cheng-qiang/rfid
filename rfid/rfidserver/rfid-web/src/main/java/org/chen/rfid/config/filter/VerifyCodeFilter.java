package org.chen.rfid.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.chen.rfid.model.CodeMsg;
import org.chen.rfid.model.RespBean;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 程强
 * @date 2020年01月17日 15:28
 * @Description:
 */
@Component
public class VerifyCodeFilter extends GenericFilterBean {

    private String defaultFilterProcessUrl = "/doLogin";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if ("POST".equalsIgnoreCase(request.getMethod()) && defaultFilterProcessUrl.equals(request.getServletPath())) {
            // 验证码验证
            String requestCaptcha = request.getParameter("code");
            String genCaptcha = (String) request.getSession().getAttribute("index_code");
            if (StringUtils.isEmpty(genCaptcha)||StringUtils.isEmpty(requestCaptcha) || !requestCaptcha.toLowerCase().equals(genCaptcha.toLowerCase())){
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write(new ObjectMapper().writeValueAsString(RespBean.error(CodeMsg.VERIFICATION_CODE_ERROR.getMsg())));
                out.flush();
                out.close();
                return;
            }else {
                chain.doFilter(request, response);
            }
        }else {
            chain.doFilter(request, response);
        }
    }
}
