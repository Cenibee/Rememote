package cenibee.github.rememote.note.detail;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class NoteDetailModelAssembler implements RepresentationModelAssembler<NoteDetail, NoteDetailModel> {

    @NonNull
    @Override
    public NoteDetailModel toModel(@NonNull NoteDetail detail) {
        return this.assemble(detail)
                .add(links(detail));
    }

    private NoteDetailModel assemble(NoteDetail detail) {
        return NoteDetailModel.builder()
                .category(detail.getCategory())
                .detail(detail.getDetail())
                .build();
    }

    private Collection<Link> links(NoteDetail detail) {
        Collection<Link> links = new ArrayList<>();
        links.add(linkTo(NoteDetailController.class).slash(detail.getId()).withSelfRel());

        return links;
    }

}
