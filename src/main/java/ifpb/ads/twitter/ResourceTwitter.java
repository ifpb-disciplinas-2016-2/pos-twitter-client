package ifpb.ads.twitter;

import ifpb.ads.Pair;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 29/03/2017, 11:28:40
 */
@Path("/twitter")
@Stateless
public class ResourceTwitter {

    @Inject
    private ClientTwitter clientTwitter;
    
    @Context
    private HttpServletRequest request;

    @GET
    public Response requestToken(
            @QueryParam("oauth_token") String oauth_token,
            @QueryParam("oauth_verifier") String oauth_verifier,
            @Context UriInfo uriInfo) {
        try {
            System.out.println("setou!");

//        clientTwitter.setOauth_token(oauth_token);
//        clientTwitter.setOauth_verifier(oauth_verifier);
//        System.out.println(clientTwitter.requestLastToken(oauth_token,oauth_verifier));
            clientTwitter.requestLastToken(oauth_token, oauth_verifier);
            
            request.getSession().setAttribute("token", "akhjfkjahfkjhak");
            
            URI uri = new URI("http://localhost:8080/auth-twitter-exemplo/faces/timeline.xhtml");
            Response response = Response.seeOther(uri).build();
            return response;
        } catch (URISyntaxException ex) {
            Logger.getLogger(ResourceTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

//    @GET
//    @Path("{timeline}")
//    public Response timeline() {
//        try {
//            String url = "https://api.twitter.com/1.1/statuses/user_timeline.json";
//            Client cliente = ClientBuilder.newClient();
//            WebTarget target = cliente.target(url);
//            String header = headerUpdate("GET", url);
//
//            Response resposta = target
//                    .request()
//                    .header("Authorization", header)
//                    .get();
//
//            return resposta;
//        } catch (Exception ex) {
//            Logger.getLogger(ClientTwitter.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return Response.noContent().build();
//    }

//    private String headerUpdate(String method, String url) throws Exception {
//        List<Pair> urlParams = new ArrayList<>();
//        return new AuthenticationHeaderTwitter(acess_key, acess_secret, oauth_token, oauth_verifier).header(urlParams, method, url);
//
//    }
}
