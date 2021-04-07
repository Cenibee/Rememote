package cenibee.github.rememote.note;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    private final NoteRepository noteRepository;

    private final NoteModelAssembler assembler;

    public NoteController(NoteRepository noteRepository, NoteModelAssembler assembler) {
        this.noteRepository = noteRepository;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNote(@PathVariable Long id) {

        return this.noteRepository.findById(id)
                .map(note -> ResponseEntity.ok(assembler.toModel(note)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/list")
    public ResponseEntity<?> noteList() {
        return ResponseEntity.ok(assembler.toCollectionModel(this.noteRepository.findAllKeywords()));
    }

    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody Note note) {
        note = this.noteRepository.save(note);
        return ResponseEntity
                .created(linkTo(NoteController.class).slash(note.getId()).toUri())
                .body(assembler.toModel(note));
    }

}
