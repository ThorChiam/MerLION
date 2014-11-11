/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionTimeoutHandler;

import java.io.IOException;
import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ThorChiam
 */
public class SessionTimeoutFilter implements Filter {
    // This should be your default Home or Login page
// "login.seam" if you use Jboss Seam otherwise "login.jsf" / "login.xhtml" or whatever

    private String timeoutPage = "signIn.xhtml";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {
        if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//// is session expire control required for this request?
            if (isSessionControlRequiredForThisResource(httpServletRequest)) {
// is session invalid?

                if (isSessionInvalid(httpServletRequest, httpServletResponse)) {
                    String currentUrl = httpServletRequest.getContextPath();
                    System.out.println("Current Url:" + currentUrl);
                    String[] cUrl = currentUrl.split("/");
                    String timeoutUrl = cUrl[0] + "/" + cUrl[1] + "/" + getTimeoutPage();

                    System.out.println("Session is invalid! redirecting to timeoutpage : " + timeoutUrl);

                    httpServletResponse.sendRedirect(timeoutUrl);
                    return;
                }
            }
        }
        System.out.println("Request:" + request.getLocalAddr());
        filterChain.doFilter(request, response);
    }
    /*
     * session shouldn’t be checked for some pages. For example: for timeout page..
     * Since we’re redirecting to timeout page from this filter,
     * if we don’t disable session control for it, filter will again redirect to it
     * and this will be result with an infinite loop…
     */

    private boolean isSessionControlRequiredForThisResource(HttpServletRequest httpServletRequest) {
        String requestPath = httpServletRequest.getRequestURI();
        if (requestPath.equals("/ES09-MerLION-war/") || requestPath.equals("/ES09-MerLION-war/main.xhtml") || requestPath.equals("/ES09-MerLION-war/signIn.xhtml")) {
            return false;
        }
        return true;
    }

    private boolean isSessionInvalid(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        System.out.println("==null:" + (httpServletRequest.getRequestedSessionId() == null));
//        System.out.println("Valid:" + (httpServletRequest.isRequestedSessionIdValid()));
        if (httpServletRequest.getRequestedSessionId() == null) {
            return true;
        } else {
            if (!httpServletRequest.isRequestedSessionIdValid()) {
                return true;
            } else {
                FacesContext fc = this.getFacesContext(httpServletRequest, httpServletResponse);
                System.out.println("Contains:" + fc.getExternalContext().getSessionMap().containsKey("userId"));
                if (fc.getExternalContext().getSessionMap().containsKey("userId")) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    protected FacesContext getFacesContext(HttpServletRequest request,
            HttpServletResponse response) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null) {

            FacesContextFactory contextFactory
                    = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
            LifecycleFactory lifecycleFactory
                    = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
            Lifecycle lifecycle
                    = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

            facesContext
                    = contextFactory.getFacesContext(request.getSession().getServletContext(),
                            request, response, lifecycle);

            // Set using our inner class
            InnerFacesContext.setFacesContextAsCurrentInstance(facesContext);

            // set a new viewRoot, otherwise context.getViewRoot returns null
            UIViewRoot view
                    = facesContext.getApplication().getViewHandler().createView(facesContext, "");
            facesContext.setViewRoot(view);
        }
        return facesContext;
    }

    private abstract static class InnerFacesContext extends FacesContext {

        protected static void setFacesContextAsCurrentInstance(FacesContext facesContext) {
            FacesContext.setCurrentInstance(facesContext);
        }
    }

    @Override
    public void destroy() {
    }

    public String getTimeoutPage() {
        return timeoutPage;
    }

    public void setTimeoutPage(String timeoutPage) {
        this.timeoutPage = timeoutPage;
    }
}
