package cenibee.github.rememote.tag;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagModel extends RepresentationModel<TagModel> {

    private String name;

}
