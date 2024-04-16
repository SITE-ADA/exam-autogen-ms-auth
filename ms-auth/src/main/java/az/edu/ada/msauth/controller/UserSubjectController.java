package az.edu.ada.msauth.controller;

import az.edu.ada.msauth.client.SubjectClient;
import az.edu.ada.msauth.model.dto.SubjectDTO;
import az.edu.ada.msauth.model.entities.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-subjects")
public class UserSubjectController {
    private final SubjectClient subjectClient;

    public UserSubjectController(SubjectClient subjectClient) {
        this.subjectClient = subjectClient;
    }

    @GetMapping
    public List<SubjectDTO> getSubjects(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        return subjectClient.findSubjectsByUserId(currentUser.getId());
    }
}
