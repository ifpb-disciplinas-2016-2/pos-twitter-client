package ifpb.ads.rest;

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
@Named("controllerTwitter")
@RequestScoped
public class TwitterController implements Serializable {

    private String status;

//    @Inject
    private TwitterClient clientTwitter = new TwitterClient();

    private List<TwitterStatus> twitters = new ArrayList<>();

    public TwitterClient getClientTwitter() {
        return clientTwitter;
    }

    public void setClientTwitter(TwitterClient clientTwitter) {
        this.clientTwitter = clientTwitter;
    }

    public String timeline() {
        this.twitters = clientTwitter
                .requestTimeline(clientTwitter.timeline());
        return "Profile";
    }

    public String twitar() {
        System.out.println(clientTwitter.twitar(status));
        return null;
    }

    public List<TwitterStatus> getTwitters() {
        return twitters;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
