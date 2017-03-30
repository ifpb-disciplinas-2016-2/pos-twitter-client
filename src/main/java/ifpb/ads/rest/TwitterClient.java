package ifpb.ads.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ifpb.ads.twitter.model.TwitterStatus;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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

public class TwitterClient implements Serializable {

    public TwitterClient() {
    }

    public String timeline() {

        Client cliente = ClientBuilder.newClient();
        WebTarget target = cliente
                .target("http://localhost:8080/auth-twitter-exemplo/api/twitter/timeline");
        //Basic am9iOjEyMw== Authorization 
        Response resposta = target
                .request()
//                .header("Authorization", "Basic am9iOjEyMw==")
                .get();
        return resposta.readEntity(String.class);
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
            Logger.getLogger(TwitterClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    public String twitar(String status) {

        Client cliente = ClientBuilder.newClient();
        Form form = new Form("status", status);
        WebTarget target = cliente
                .target("http://localhost:8080/auth-twitter-exemplo/api/twitter/");
        Response resposta = target
                .request().post(Entity.form(form));
        return resposta.readEntity(String.class);

    }

}
