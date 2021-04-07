package cenibee.github.rememote.note;

import cenibee.github.rememote.note.detail.NoteDetail;
import cenibee.github.rememote.note.dto.NoteKeywordsOnly;
import cenibee.github.rememote.tag.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    @Test
    void findAllKeywords() {

        List<Note> noteList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Note note = Note.builder()
                    .keyword("this is a findAllKeywords" + i)
                    .build();
            note.addDetail(NoteDetail.builder()
                    .note(note)
                    .category("this is a category")
                    .detail("this is a detail")
                    .build());
            note.addTag(Tag.builder()
                    .name("this tag is a findAllKeywords" + i)
                    .build());
            noteList.add(note);
        }
        this.noteRepository.saveAll(noteList);

        Collection<NoteKeywordsOnly> keywords = this.noteRepository.findAllKeywords();
        assert keywords != null;
        assertThat(keywords.size() >= 10).isTrue();
        keywords.forEach(noteKeywordsOnly -> assertThat(noteKeywordsOnly.getKeyword()).isNotNull());
    }
}