package cenibee.github.rememote.note;

import cenibee.github.rememote.note.detail.NoteDetailModel;
import cenibee.github.rememote.tag.TagModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoteModel extends RepresentationModel<NoteModel> {

    private String keyword;
    private List<NoteDetailModel> details;
    private Set<TagModel> tags;

}
