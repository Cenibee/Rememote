package cenibee.github.rememote.note;

import cenibee.github.rememote.note.detail.NoteDetail;
import cenibee.github.rememote.tag.Tag;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotePreloader implements CommandLineRunner {

    private final NoteRepository noteRepository;

    public NotePreloader(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public void run(String... args) {
        List<Note> noteList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Note note = Note.builder()
                    .keyword("this is a selectNotes" + i)
                    .build();
            note.addDetail(NoteDetail.builder()
                    .note(note)
                    .category("this is a category")
                    .detail("this is a detail")
                    .build());
            note.addTag(Tag.builder()
                    .name("this tag is a selectNotes" + i)
                    .build());
            noteList.add(note);
        }
        this.noteRepository.saveAll(noteList);
    }
}
