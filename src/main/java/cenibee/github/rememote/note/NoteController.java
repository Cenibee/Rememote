package cenibee.github.rememote.note;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteRepository noteRepository;

    private final NoteModelAssembler assembler;

    public NoteController(NoteRepository noteRepository, NoteModelAssembler assembler) {
        this.noteRepository = noteRepository;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNote(@PathVariable Long id) {
        Optional<Note> noteOptional = this.noteRepository.findById(id);

        if (noteOptional.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(assembler.toModel(noteOptional.get()));
    }

    @GetMapping
    public ResponseEntity<?> selectNotes() {
        return ResponseEntity.ok(assembler.toCollectionModel(this.noteRepository.findAll()));
    }

    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody Note note) {
        note = this.noteRepository.save(note);
        return ResponseEntity
                .created(linkTo(NoteController.class).slash(note.getId()).toUri())
                .body(assembler.toModel(note));
    }

}
