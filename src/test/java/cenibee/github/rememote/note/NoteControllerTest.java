package cenibee.github.rememote.note;

import cenibee.github.rememote.note.detail.NoteDetail;
import cenibee.github.rememote.tag.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.hypermedia.HypermediaDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO Test Note 들을 고정하고, 쓰기 작업은 Order 로 뒤에 수행하도록 지정하자
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
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
    @DisplayName("노트 하나 가져오기 (DOC)")
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
        mvc.perform(get("/api/note/{id}", note.getId())
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-note",
                        responseFields(
                                fieldWithPath("keyword").description("노트의 키워드"),
                                subsectionWithPath("details").description("노트에 저장된 내용들"),
                                subsectionWithPath("tags").description("노트의 태그들"),
                                subsectionWithPath("_links").description("현재 응답에서 전이 가능한 링크들")
                        ),
                        links(
                                linkWithRel("self").description("현재 응답의 링크")
                        ))
                );
    }

    @Test
    @DisplayName("존재하지 않는 노트 가져오기 (DOC)")
    void getInvalidNote() throws Exception {

        // expect: 생성된 id 로 노트를 가져온다.
        mvc.perform(get("/api/note/{id}", -1)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("error-response",
                        responseFields(
                                fieldWithPath("errorType").description("에러 타입"),
                                fieldWithPath("message").description("에러 메시지")
                        )
                ));
    }

    @Test
    @DisplayName("노트 키워드 리스트 가져오기")
    void noteList() throws Exception {
        // given: 여러 노트가 저장되어 있을 때
        List<Note> noteList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Note note = Note.builder()
                    .keyword("this is a noteList" + i)
                    .build();
            note.addDetail(NoteDetail.builder()
                    .note(note)
                    .category("this is a category")
                    .detail("this is a detail")
                    .build());
            note.addTag(Tag.builder()
                    .name("this tag is a noteList" + i)
                    .build());
            noteList.add(note);
        }
        this.noteRepository.saveAll(noteList);

        // expect: 생성된 id 로 노트를 가져온다.
        mvc.perform(get("/api/note/list")
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("select-note",
                        responseFields(
                                subsectionWithPath("_embedded.noteModelList").description("노트 리스트"),
                                subsectionWithPath("_links").description("현재 응답에서 전이 가능한 링크들")
                        ),
                        responseFields(beneathPath("_embedded.noteModelList").withSubsectionId("NoteList"),
                                fieldWithPath("keyword").description("노트의 키워드"),
                                fieldWithPath("_links.self.href").description("해당 노트의 링크")
                        ),
                        links(
                                linkWithRel("self").description("현재 응답의 링크")
                        )
                ));
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

        mvc.perform(post("/api/note")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(note))
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("tags[*]._links.self").exists())
                .andExpect(jsonPath("details[*]._links.self").exists())
                .andExpect(jsonPath("_links.self.href").exists());
    }

    @Test
    @DisplayName("노트 생성 실패 (중복 키워드)")
    void failToCreateNoteSameKeyword() throws Exception {
        Note note = Note.builder()
                .keyword("failToCreateNoteSameKeyword")
                .build();
        note.addDetail(NoteDetail.builder()
                .note(note)
                .category("this is a category")
                .detail("this is a detail")
                .build());
        note.addTag(Tag.builder()
                .name("failToCreateNoteSameKeyword")
                .build());
        this.noteRepository.save(note);

        mvc.perform(post("/api/note")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(note))
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("노트 생성 실패 (존재하는 ID)")
    void failToCreateNoteSameID() throws Exception {
        Note note = Note.builder()
                .keyword("failToCreateNoteSameID")
                .build();
        note.addDetail(NoteDetail.builder()
                .note(note)
                .category("this is a category")
                .detail("this is a detail")
                .build());
        note.addTag(Tag.builder()
                .name("failToCreateNoteSameID")
                .build());
        note = this.noteRepository.save(note);

        mvc.perform(post("/api/note")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(note))
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().isConflict());
    }
}