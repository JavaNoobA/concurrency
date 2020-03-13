package com.eru.concurrency.inaction.ch3.case1;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * Created by eru on 2020/3/14.
 */
@WebFilter("echo")
public class CountingFilter implements Filter {

    final Indicator indicator = Indicator.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        indicator.newRequestReceive();
        StatusExposingResponse response = new StatusExposingResponse((HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest, response);

        int statusCode = response.getStatus();
        if (0 == statusCode || 2 == statusCode / 100){
            indicator.newRequestProcessed();
        }else {
            indicator.requestProcessedFailed();
        }
    }

    public class StatusExposingResponse extends HttpServletResponseWrapper{
        private int httpStatus;

        public StatusExposingResponse(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            httpStatus = sc;
            super.sendError(sc, msg);
        }

        @Override
        public void sendError(int sc) throws IOException {
            httpStatus = sc;
            super.sendError(sc);
        }

        @Override
        public void setStatus(int sc, String sm) {
            httpStatus = sc;
            super.setStatus(sc);
        }

        @Override
        public int getStatus() {
            return httpStatus;
        }
    }
}
