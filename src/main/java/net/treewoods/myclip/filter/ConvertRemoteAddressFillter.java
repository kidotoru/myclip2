package net.treewoods.myclip.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kido
 */
@WebFilter(filterName = "ConvertRemoteAddressFillter", urlPatterns = {"/*"})
public class ConvertRemoteAddressFillter implements Filter {
private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.log.info("init");
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        if( request instanceof HttpServletRequest) {
            // IPアドレスを、Webサーバーのものからリモートアドレスのものへ変更
            String remoteAddr = request.getRemoteAddr();
            String http_x_forward_for = ((HttpServletRequest)request).getHeader("x-forwarded-for");
            
            if (http_x_forward_for != null && !http_x_forward_for.isEmpty()){
            }
            
        }
        

        
        chain.doFilter(request, response);

    
    }

    @Override
    public void destroy() {
        this.log.info("destroy");
    }
}
