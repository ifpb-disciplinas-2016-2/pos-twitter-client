package ifpb.ads.twitter.oauth;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 30/03/2017, 09:44:08
 */
@Named
@SessionScoped
public class Credentials implements Serializable {

    private String acess_key = "2URxXsnyMBfn71XTtRs8A";
    private String acess_secret = "CM8WbuGDFPIQGhkLhFEVQMyCK6sZFq10uXM4IzHQQ";

    private String oauth_token = "";
    private String oauth_verifier = "";

    public Credentials() {
    }

    public String getAcess_key() {
        return acess_key;
    }

    public void setAcess_key(String acess_key) {
        this.acess_key = acess_key;
    }

    public String getAcess_secret() {
        return acess_secret;
    }

    public void setAcess_secret(String acess_secret) {
        this.acess_secret = acess_secret;
    }

    public String getOauth_token() {
        return oauth_token;
    }

    public void setOauth_token(String oauth_token) {
        this.oauth_token = oauth_token;
    }

    public String getOauth_verifier() {
        return oauth_verifier;
    }

    public void setOauth_verifier(String oauth_verifier) {
        this.oauth_verifier = oauth_verifier;
    }

}
