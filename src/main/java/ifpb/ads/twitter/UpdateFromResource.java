package ifpb.ads.twitter;

import ifpb.ads.Pair;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 23/08/2016, 15:24:40
 */
public class UpdateFromResource {

    public static void main(String[] args) throws Exception {
        String url = "https://api.twitter.com/1.1/statuses/update.json";
        String status = "Exemplo na aula!! #massa";
        String header = headerUpdate(status, "POST", url);
        
        Client cliente = ClientBuilder.newClient();
        WebTarget target = cliente.target(url);
        Form form = new Form("status", status);
        Response resposta = target
                .request()
                .header("Authorization", header)
                .post(Entity.form(form));

        System.out.println(resposta.readEntity(String.class));
    }

    private static String headerUpdate(String status, String method, String url) throws Exception {
        List<Pair> urlParams = new ArrayList<>();
        urlParams.add(Pair.create("status", status));
        return new AuthenticationHeaderTwitter().header(urlParams, method, url);

    }
}
