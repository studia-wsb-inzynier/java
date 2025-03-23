package pl.maropce.etutor.config;

import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.maropce.etutor.domain.question.Question;
import pl.maropce.etutor.domain.question.QuestionRepository;
import pl.maropce.etutor.domain.question.QuestionType;
import pl.maropce.etutor.domain.quiz.Quiz;
import pl.maropce.etutor.domain.quiz.QuizRepository;
import pl.maropce.etutor.domain.user.AppUser;
import pl.maropce.etutor.domain.user.AppUserRepository;
import pl.maropce.etutor.domain.user_details.AppUserDetails;
import pl.maropce.etutor.domain.user_details.Role;

import java.util.List;

@Component
public class AppTest {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final PasswordEncoder passwordEncoder;

    private final AppUserRepository appUserRepository;

    public AppTest(QuizRepository quizRepository, QuestionRepository questionRepository,
                   PasswordEncoder passwordEncoder, AppUserRepository appUserRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository = appUserRepository;
    }


    @Bean
    @Transactional
    public ApplicationRunner initializeDatabase() {
        return args -> {
            AppUserDetails appUserDetails = AppUserDetails.builder()
                    .role(Role.TEACHER)
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .build();

            AppUser appUser = AppUser.builder()
                    .appUserDetails(appUserDetails)
                    .firstName("Marek")
                    .lastName("Krosny")
                    .phoneNumber("654753734")
                    .build();

            appUserRepository.save(appUser);

            Quiz quiz = Quiz.builder()
                    .title("Quiz 1")
                    .build();
            quizRepository.save(quiz);

            Question question1 = Question.builder()
                    .content("Jaki język jest najlepszy?")
                    .type(QuestionType.SINGLE)
                    .options(List.of("Python", "Java", "C#", "C++"))
                    .correctOptions(List.of("Java"))
                    .quiz(quiz)
                    .build();

            Question question2 = Question.builder()
                    .content("Które typy danych są poprawne w języku Java")
                    .type(QuestionType.MULTI)
                    .options(List.of("int", "float", "number", "string", "String"))
                    .correctOptions(List.of("int", "float", "String"))
                    .quiz(quiz)
                    .build();

            Question question3 = Question.builder()
                    .content("Ułóż te typy danych w kolejności ich rozmiaru od najmniejszego do największego")
                    .type(QuestionType.ORDERING)
                    .options(List.of("int", "short", "byte", "double"))
                    .correctOptions(List.of("byte", "short", "int", "double"))
                    .quiz(quiz)
                    .build();

            questionRepository.save(question1);
            questionRepository.save(question2);
            questionRepository.save(question3);
        };
    }

}
