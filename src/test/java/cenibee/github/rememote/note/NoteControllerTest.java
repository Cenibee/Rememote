package cenibee.github.rememote.note;

import cenibee.github.rememote.note.detail.NoteDetail;
import cenibee.github.rememote.tag.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mvc;

    @Autowired
    NoteRepository noteRepository;

    @Test
    void nullTest() {
        assertThat(mvc).isNotNull();
        assertThat(noteRepository).isNotNull();
    }

    @Test
    @DisplayName("노트 하나 가져오기")
    void getOneNote() throws Exception {
        // given: 노트 하나가 저장되어 있을 때
        Note note = Note.builder()
                .keyword("this is a getOneNote")
                .build();
        note.addDetail(NoteDetail.builder()
                .note(note)
                .category("this is a category")
                .detail("this is a detail")
                .build());
        note.addTag(Tag.builder()
                .name("this tag is a getOneNote")
                .build());
        note = this.noteRepository.save(note);

        // expect: 생성된 id 로 노트를 가져온다.
        mvc.perform(get("/api/notes/{id}", note.getId())
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("tags[*]").exists())
                .andExpect(jsonPath("details[*]._links.self").exists())
                .andExpect(jsonPath("_links.self.href").exists());
    }

    @Test
    @DisplayName("노트 하나 생성하기")
    void createNote() throws Exception {
        Note note = Note.builder()
                .keyword("this is a createNote")
                .build();
        note.addDetail(NoteDetail.builder()
                .note(note)
                .category("this is a category")
                .detail("this is a detail")
                .build());
        note.addTag(Tag.builder()
                .name("this tag is a createNote")
                .build());

        mvc.perform(post("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(note))
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("tags[*]").exists())
                .andExpect(jsonPath("details[*]._links.self").exists())
                .andExpect(jsonPath("_links.self.href").exists());
    }

}