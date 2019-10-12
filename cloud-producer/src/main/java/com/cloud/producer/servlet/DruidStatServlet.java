package com.cloud.producer.servlet;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(
        name = "DruidStatView",
        urlPatterns = "/druid/*",
        initParams={
                @WebInitParam(name = "servletName", value = "DruidStatView")
        }
)
public class DruidStatServlet extends StatViewServlet {

    private String servletName;

    public void setServletName(String servletName) {
        System.out.println("Servlet Name> " + servletName);
        this.servletName = servletName;
    }
}
