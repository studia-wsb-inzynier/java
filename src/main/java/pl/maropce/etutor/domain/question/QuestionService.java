package pl.maropce.etutor.domain.question;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.maropce.etutor.domain.question.dto.QuestionDTO;
import pl.maropce.etutor.domain.quiz.dto.GenerateQuizRequest;
import pl.maropce.etutor.domain.quiz.dto.GenerateQuizResponse;
import pl.maropce.etutor.domain.user_details.AppUserDetails;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class QuestionService {

    @Value("${quiz.generator.url}")
    private String quizURL;

    public List<QuestionDTO> generateQuestionsWithAI(GenerateQuizRequest request, AppUserDetails appUserDetails) {
        WebClient webClient = WebClient.builder()
                .baseUrl(quizURL)
                .build();

        request.setUserId(appUserDetails.getAppUser().getId());
        System.out.println(request);

        GenerateQuizResponse response = webClient.post()
                .uri("/api/quiz/generate")
                //.bodyValue(request)
                .body(Mono.just(request), GenerateQuizRequest.class)
                .retrieve()
                .bodyToMono(GenerateQuizResponse.class)
                .block();

        assert response != null;
        return response.getQuestions();
    }
}
