package cenibee.github.rememote.tag;

import cenibee.github.rememote.common.BaseModelAssembler;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class TagModelAssembler implements BaseModelAssembler<Tag, TagModel> {

    @Override
    public TagModel assemble(Tag tag) {
        return TagModel.builder()
                .name(tag.getName())
                .build();
    }

    @Override
    public Collection<Link> links(Tag tag) {
        Collection<Link> links = new ArrayList<>();
        links.add(linkTo(TagController.class).slash(tag.getId()).withSelfRel());

        return links;
    }

}
