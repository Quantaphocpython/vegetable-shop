package org.ecommerce.spring.boot.vegetable.project.utility;

import jakarta.servlet.http.HttpServletRequest;

public class ApplicationUrl {
    public static String getUrl(HttpServletRequest request) {
        String appUrl = request.getRequestURL().toString();
        return appUrl.replace(request.getServletPath(), "");
    }
}
