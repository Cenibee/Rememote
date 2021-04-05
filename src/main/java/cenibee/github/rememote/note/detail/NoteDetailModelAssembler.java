package cenibee.github.rememote.note.detail;

import cenibee.github.rememote.common.BaseModelAssembler;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class NoteDetailModelAssembler implements BaseModelAssembler<NoteDetail, NoteDetailModel> {

    @Override
    public NoteDetailModel assemble(NoteDetail detail) {
        return NoteDetailModel.builder()
                .category(detail.getCategory())
                .detail(detail.getDetail())
                .build();
    }

    @Override
    public Collection<Link> links(NoteDetail detail) {
        Collection<Link> links = new ArrayList<>();
        links.add(linkTo(NoteDetailController.class).slash(detail.getId()).withSelfRel());

        return links;
    }

}
