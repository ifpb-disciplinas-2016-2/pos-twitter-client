package ifpb.ads.twitter.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 29/08/2016, 00:59:10
 */
@Value.Immutable
@JsonDeserialize(as=ImmutableTwitterUser.class)

public interface TwitterUser {

//    @Value.Parameter
    public String name();
}
