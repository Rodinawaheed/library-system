
package com.flairs.libraryapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class LoanFlowIT {

    @Autowired MockMvc mvc;

    @Test
    void aMemberBorrowsAndReturnsABook() throws Exception {
        String token = login("sara@example.com");

        String body = mvc.perform(post("/api/loans")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookId\": 1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.memberId").value(1))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Number loanId = JsonPath.read(body, "$.id");

        mvc.perform(patch("/api/loans/" + loanId.longValue() + "/return")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        mvc.perform(patch("/api/loans/" + loanId.longValue() + "/return")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isConflict());
    }

    @Test
    void theLastCopyIsAlreadyOut() throws Exception {
        String token = login("omar@example.com");

        mvc.perform(post("/api/loans")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookId\": 2}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail")
                        .value("No copies of 'Animal Farm' are available"));
    }

    @Test
    void aMemberCannotBorrowForSomeoneElse() throws Exception {
        mvc.perform(post("/api/loans").header("Authorization", "Bearer " + login("sara@example.com"))
                        .contentType(MediaType.APPLICATION_JSON).content("{\"bookId\": 3, \"memberId\": 2}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void aMemberCannotListMembersButALibrarianCan() throws Exception {
        mvc.perform(get("/api/members").header("Authorization", "Bearer " + login("sara@example.com")))
                .andExpect(status().isForbidden());

        mvc.perform(get("/api/members").header("Authorization", "Bearer " + login("librarian@example.com")))
                .andExpect(status().isOk());
    }

    private String login(String email) throws Exception {
        String json = mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\",\"password\":\"secret123\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return JsonPath.read(json, "$.accessToken");
    }
}
