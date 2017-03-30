package ifpb.ads.twitter.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 30/03/2017, 08:16:15
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableHashtag.class)

public interface Hashtag {

    String text();

    int[] indices();
}
