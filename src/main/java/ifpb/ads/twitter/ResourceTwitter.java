package ifpb.ads.twitter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

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
    
    @GET
    public void requestToken(
            @QueryParam("oauth_token") String oauth_token,
            @QueryParam("oauth_verifier") String oauth_verifier) {
        System.out.println("setou!");
        
//        clientTwitter.setOauth_token(oauth_token);
//        clientTwitter.setOauth_verifier(oauth_verifier);
        
//        System.out.println(clientTwitter.requestLastToken(oauth_token,oauth_verifier));
        clientTwitter.requestLastToken(oauth_token,oauth_verifier);
       
    }
}
