package ifpb.ads.twitter.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 30/03/2017, 08:13:54
 */
@Value.Immutable
@JsonDeserialize(as=ImmutableTwitterEntity.class)

public interface TwitterEntity {

    Hashtag[] hashtags();
}
