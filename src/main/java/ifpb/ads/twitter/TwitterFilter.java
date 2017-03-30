/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.twitter;

import ifpb.ads.twitter.oauth.Credentials;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author Ricardo Job
 */
@WebFilter(filterName = "TwitterFilter", urlPatterns = {"/faces/*"})
public class TwitterFilter implements Filter {

    @Inject
    private Credentials credentials;

    private static final boolean debug = true;

    private FilterConfig filterConfig = null;

    public TwitterFilter() {
    }

    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("TwitterFilter:doFilter()");
        }

        if (credentials == null || credentials.getOauth_token() == null || "".equals(credentials.getOauth_token())) {
            String url = "https://api.twitter.com/oauth/authenticate?" + requestFirstToken();
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect(url);
        } else {
            chain.doFilter(request, response);
        }

    }

    public String requestFirstToken() {

        Client cliente = ClientBuilder.newClient();
        WebTarget target = cliente
                .target("http://localhost:8080/auth-twitter-exemplo/api/twitter/token");
        Response resposta = target
                .request().get();

        return resposta.readEntity(String.class);
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("TwitterFilter:Initializing filter");
            }
        }
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
