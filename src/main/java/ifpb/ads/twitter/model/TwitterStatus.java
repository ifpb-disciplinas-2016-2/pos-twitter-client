package ifpb.ads.twitter.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 29/08/2016, 00:59:00
 */
@Value.Immutable
@JsonSerialize(as = ImmutableTwitterStatus.class)
@JsonDeserialize(as = ImmutableTwitterStatus.class)
public interface TwitterStatus {

    String created_at();

    String text();

    TwitterUser user();

    TwitterEntity entities();
}
