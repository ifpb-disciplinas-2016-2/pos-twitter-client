package ifpb.ads.twitter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ifpb.ads.Pair;
import ifpb.ads.twitter.model.TwitterStatus;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 29/03/2017, 10:41:23
 */
@Named
@SessionScoped
public class ClientTwitter implements Serializable {

    public ClientTwitter() {
    }

    public ClientTwitter(String token, String verifier) {
        this.oauth_token = token;
        this.oauth_verifier = verifier;
    }

    private String acess_key = "2URxXsnyMBfn71XTtRs8A";
    private String acess_secret = "CM8WbuGDFPIQGhkLhFEVQMyCK6sZFq10uXM4IzHQQ";

    private String oauth_token = "";
    private String oauth_verifier = "";

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

    public String requestFirstToken() {

        try {
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target("https://api.twitter.com/oauth/request_token");
            Form form = new Form("", acess_key);
            form.param("", acess_secret);
            form.param("", "");
            String header = headerUpdate("POST", "https://api.twitter.com/oauth/request_token");

            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .post(Entity.form(form));

            return resposta.readEntity(String.class);

        } catch (Exception ex) {
            Logger.getLogger(ClientTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String headerUpdate(String method, String url) throws Exception {
        List<Pair> urlParams = new ArrayList<>();
        return new AuthenticationHeaderTwitter(acess_key, acess_secret, oauth_token, oauth_verifier).header(urlParams, method, url);

    }

    public String timeline() {

        try {
            String url = "https://api.twitter.com/1.1/statuses/user_timeline.json";
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target(url);
            String header = headerUpdate("GET", url);

            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .get();

            return resposta.readEntity(String.class);
        } catch (Exception ex) {
            Logger.getLogger(ClientTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "não veio nada!";
    }

    public List<TwitterStatus> requestTimeline(String json) {
        try {
            ObjectMapper objectMapper
                    = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                    false);
            List<TwitterStatus> twitters
                    = objectMapper.readValue(json,
                            new TypeReference<List<TwitterStatus>>() {
                    });

            return twitters;
        } catch (IOException ex) {
            Logger.getLogger(ClientTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    public void requestLastToken(String oauth_token, String oauth_verifier) {
        this.oauth_token = oauth_token;
        this.oauth_verifier = oauth_verifier;

        try {
            Client cliente = ClientBuilder.newClient();
            WebTarget target = cliente.target("https://api.twitter.com/oauth/access_token");
            Form form = new Form("oauth_verifier", this.oauth_verifier);

            String header = headerUpdate("POST", "https://api.twitter.com/oauth/access_token");

            Response resposta = target
                    .request()
                    .header("Authorization", header)
                    .post(Entity.form(form));

            String tokens = resposta.readEntity(String.class);
            String[] split = tokens.split("&");
            this.oauth_token = split[0].split("=")[1];
            this.oauth_verifier = split[1].split("=")[1];

            //oauth_token=90332417-YWgBgJkfrMNizwRi5vXMih4n3ikarxj9ZO3uRVocn
            //&oauth_token_secret=wjdFh2OTy6EXaAuoGI9B3V8V3G571Bb1jWwb39sXevgQu
            //&user_id=90332417&screen_name=ricardojob&x_auth_expires=0
//            return resposta.readEntity(String.class);
        } catch (Exception ex) {
            Logger.getLogger(ClientTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
//        return null;
    }

    public String twitar(String status) {
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

            return resposta.readEntity(String.class);
        } catch (Exception ex) {
            Logger.getLogger(ClientTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "erro";
    }

    private String headerStatusToUpdate(String status, String method, String url) throws Exception {
        List<Pair> urlParams = new ArrayList<>();
        urlParams.add(Pair.create("status", status));
        return new AuthenticationHeaderTwitter(acess_key, acess_secret, oauth_token, oauth_verifier).header(urlParams, method, url);

    }

}

//https://accounts.google.com/AccountChooser?
//continue=https://accounts.google.com/o/oauth2/auth?
//client_id%3D877826870670.apps.googleusercontent.com%26
//redirect_uri%3Dpostmessage%26response_type%3Dcode%2Btoken%2Bid_token%2Bgsession%26scope%3D
//https://www.googleapis.com/auth/userinfo.profile%2Bhttps://www.googleapis.com/auth/userinfo.email%26cookie_policy%3Dsingle_host_origin%26
//include_granted_scopes%3Dtrue%26proxy%3Doauth2
//relay1362405845%26origin%3Dhttps://www.jogatina.com%26gsiwebsdk%3D1%26state%3D920024916%257C0.2356739033%26%26jsh%3Dm;/_/scs/apps-static/_/js/k%253Doz.gapi.pt_BR.NQs_arU45YQ.O/m%253D__features__/am%253DAQ/rt%253Dj/d%253D1/rs%253DAGLTcCOgt19vAhoA6UUuHB7EGp5nUfOSdg%26from_login%3D1%26as%3D6a1a55add6b33b0d&btmpl=authsub&scc=1&oauth=1
