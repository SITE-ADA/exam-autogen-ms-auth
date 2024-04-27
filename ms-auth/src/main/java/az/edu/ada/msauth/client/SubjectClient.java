package az.edu.ada.msauth.client;

import az.edu.ada.msauth.model.dto.SubjectDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "MS-QUESTIONS", path = "/api/v1/questions/subject")
public interface SubjectClient {
    @GetMapping("/by-user")
    List<SubjectDTO> findSubjectsByUserId(@RequestParam("userId") Long userId);
}

