package cenibee.github.rememote.tag;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class TagModelAssembler implements RepresentationModelAssembler<Tag, TagModel> {

    @Override
    @NonNull
    public TagModel toModel(@NonNull Tag tag) {
        return assemble(tag)
                .add(links(tag));
    }

    private TagModel assemble(Tag tag) {
        return TagModel.builder()
                .name(tag.name)
                .build();
    }

    private Collection<Link> links(Tag tag) {
        Collection<Link> links = new ArrayList<>();
        links.add(linkTo(TagController.class).slash(tag.getId()).withSelfRel());

        return links;
    }

}
