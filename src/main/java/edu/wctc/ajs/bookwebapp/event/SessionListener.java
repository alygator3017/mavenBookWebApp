package edu.wctc.ajs.bookwebapp.event;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Alyson
 */
public class SessionListener implements HttpSessionListener {
    private static int sessionOpenCount = 0;
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        sessionOpenCount++;
        se.getSession().getServletContext().setAttribute("sessionOpenCount", SessionListener.sessionOpenCount);
        
        String sessID = se.getSession().getId();
        System.out.println("Session created: " + sessID);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        sessionOpenCount--;
        se.getSession().getServletContext().setAttribute("sessionOpenCount", SessionListener.sessionOpenCount);
        String sessID = se.getSession().getId();
        System.out.println("Session Ended: " + sessID);
    }

    
}
