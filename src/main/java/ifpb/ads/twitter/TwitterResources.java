package ifpb.ads.twitter;

import ifpb.ads.rest.TwitterClient;
import ifpb.ads.Pair;
import ifpb.ads.twitter.oauth.Credentials;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 29/03/2017, 11:28:40
 */
@Path("/twitter")
@Stateless
public class TwitterResources {

    @Inject
    private Credentials credentials;

    @GET
    public Response requestToken(
            @QueryParam("oauth_token") String oauth_token,
            @QueryParam("oauth_verifier") String oauth_verifier,
            @Context UriInfo uriInfo) {
        try {

            credentials = requestLastToken(oauth_token, oauth_verifier);

            URI uri = new URI("http://localhost:8080/auth-twitter-exemplo/faces/timeline.xhtml");
            Response response = Response.seeOther(uri).build();
            return response;
        } catch (URISyntaxException ex) {
            Logger.getLogger(TwitterResources.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    public Credentials requestLastToken(String oauth_token, String oauth_verifier) {
        Credentials c = new Credentials();
        // tokens temporarios
        credentials.setOauth_token(oauth_token);
        credentials.setOauth_verifier(oauth_verifier);

        try {
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target("https://api.twitter.com/oauth/access_token");
            Form form = new Form("oauth_verifier", oauth_verifier);

            String header = headerUpdate("POST", "https://api.twitter.com/oauth/access_token");

            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .post(Entity.form(form));

            String tokens = resposta.readEntity(String.class);
            String[] split = tokens.split("&");
            //tokens finais
            String token_a = split[0].split("=")[1];
            String token_v = split[1].split("=")[1];
            c.setOauth_token(token_a);
            c.setOauth_verifier(token_v);
        } catch (Exception ex) {
            Logger.getLogger(TwitterClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    @GET
    @Path("timeline")
    @Produces(MediaType.APPLICATION_JSON)
    public Response timeline() {
        try {
            String url = "https://api.twitter.com/1.1/statuses/user_timeline.json";
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target(url);
            String header = headerUpdate("GET", url);

            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .get();

            return resposta;
        } catch (Exception ex) {
            Logger.getLogger(TwitterClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }
    
    @GET  
    @Path("token")
    @Produces(MediaType.TEXT_PLAIN)
    public String requestFirstToken() {

        try {
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target("https://api.twitter.com/oauth/request_token");
            Form form = new Form();
            String header = headerUpdate("POST", "https://api.twitter.com/oauth/request_token");

            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .post(Entity.form(form));
            return resposta.readEntity(String.class);

        } catch (Exception ex) {
            Logger.getLogger(TwitterClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response twitar(@FormParam("status") String status) {
        try {
            String url = "https://api.twitter.com/1.1/statuses/update.json";
            String header = headerStatusToUpdate(status, "POST", url);
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target(url);
            Form form = new Form("status", status);
            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .post(Entity.form(form));

            return resposta;
        } catch (Exception ex) {
            Logger.getLogger(TwitterClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    private String headerStatusToUpdate(String status, String method, String url) throws Exception {
        List<Pair> urlParams = new ArrayList<>();
        urlParams.add(Pair.create("status", status));
        return new AuthenticationHeaderTwitter(this.credentials)
                .header(urlParams, method, url);

    }

    private String headerUpdate(String method, String url) throws Exception {
        List<Pair> urlParams = new ArrayList<>();
        return new AuthenticationHeaderTwitter(this.credentials)
                .header(urlParams, method, url);

    }
}
