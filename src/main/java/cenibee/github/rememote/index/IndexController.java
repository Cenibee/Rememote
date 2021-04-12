package cenibee.github.rememote.index;

import cenibee.github.rememote.note.NoteController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.hal.HalModelBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("api")
public class IndexController {

    @GetMapping
    public ResponseEntity<RepresentationModel<?>> index() {
        return ResponseEntity.ok(HalModelBuilder.emptyHalModel().build()
                .add(linkTo(NoteController.class).slash("list").withRel("note-keywords"))
        );
    }

}
