package kg.attractor.jobsearch.util;

import jakarta.servlet.http.HttpServletRequest;

public class Utilities {

    private Utilities() {

    }

    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
