package ifpb.ads.twitter;

import ifpb.ads.twitter.model.TwitterStatus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 29/03/2017, 10:40:53
 */
@Named
@RequestScoped
public class ControladorTwitter implements Serializable {

    @Inject
    private ClientTwitter clientTwitter;

    public ClientTwitter getClientTwitter() {
        return clientTwitter;
    }

    public void setClientTwitter(ClientTwitter clientTwitter) {
        this.clientTwitter = clientTwitter;
    }

    public String autorize() {
        return clientTwitter.requestFirstToken();
    }
    private List<TwitterStatus> twitters = new ArrayList<>();

    public String timeline() {
        this.twitters = clientTwitter
                .requestTimeline(clientTwitter.timeline());
        return "Profile";
    }

    public List<TwitterStatus> getTwitters() {
        return twitters;
    }

}
