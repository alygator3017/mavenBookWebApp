package edu.wctc.ajs.bookwebapp.util;


import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 * * * @author jlombardo
 */
public class MyAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        final String USER_URL =  "/index.html";
        final String ADMIN_URL =  "/admin/adminHome.jsp";
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_MGR")) {
            getRedirectStrategy().sendRedirect(request, response, ADMIN_URL);
        } else if (roles.contains("ROLE_USER")) {
            getRedirectStrategy().sendRedirect(request, response, USER_URL);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
    }
}
