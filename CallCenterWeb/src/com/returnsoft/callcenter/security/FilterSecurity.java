package com.returnsoft.callcenter.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponse;

public class FilterSecurity implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Expires", "Tue, 03 Jul 2001 06:00:00 GMT");
        resp.setDateHeader("Last-Modified", new Date().getTime());
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
        resp.setHeader("Pragma", "no-cache");

        chain.doFilter(request, response);
		
		//String uri = request.getRequestURI();
		
		//System.out.println("LA URI:"+uri);
		
		
		//HttpSession session = request.getSession(false);
		
		//System.out.println("LA SESSION:"+session);
		
		//InetAddress address = InetAddress.getLocalHost();
		//String ipServer = address.getHostAddress();
		
		/*if (uri.equals("/softphone/faces/login.xhtml")) {
			chain.doFilter(req, res);
		}else{
			if (session!=null) {
				SessionBean sessionBean = (SessionBean)session.getAttribute("sessionBean");	
				if (null == sessionBean) {
					// response.sendRedirect("http://"+ipServer+":8081/softphone/faces/login.xhtml");
					response.sendRedirect("http://www.geainternacional.com:8081/softphone/faces/login.xhtml");
				}else{
					chain.doFilter(req, res);
				}
			}else{
				// response.sendRedirect("http://"+ipServer+":8081/softphone/faces/login.xhtml");
				response.sendRedirect("http://www.geainternacional.com:8081/softphone/faces/login.xhtml");
			}
		}*/

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
