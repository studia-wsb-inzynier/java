package pl.maropce.etutor.domain.question;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.maropce.etutor.domain.question.dto.QuestionDTO;
import pl.maropce.etutor.domain.quiz.dto.GenerateQuizRequest;
import pl.maropce.etutor.domain.user_details.AppUserDetails;

import java.util.List;

@Deprecated
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/generate")
    public ResponseEntity<List<QuestionDTO>> generateQuiz(@RequestBody GenerateQuizRequest generateQuizRequest,
                                                          @AuthenticationPrincipal AppUserDetails appUserDetails) {
        List<QuestionDTO> quizDTO = questionService.generateQuestionsWithAI(generateQuizRequest, appUserDetails);
        return ResponseEntity.ok(quizDTO);
    }
}
