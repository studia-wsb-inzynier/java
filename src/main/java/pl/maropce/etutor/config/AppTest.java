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
import pl.maropce.etutor.domain.user_details.AppUserDetails;
import pl.maropce.etutor.domain.user_details.AppUserDetailsRepository;
import pl.maropce.etutor.domain.user_details.Role;

import java.util.List;

@Component
public class AppTest {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final PasswordEncoder passwordEncoder;

    private final AppUserDetailsRepository appUserDetailsRepository;

    public AppTest(QuizRepository quizRepository, QuestionRepository questionRepository,
                   PasswordEncoder passwordEncoder, AppUserDetailsRepository appUserDetailsRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserDetailsRepository = appUserDetailsRepository;
    }

    @Bean
    @Transactional
    public ApplicationRunner initializeDatabase() {
        return args -> {

            AppUser appUser = AppUser.builder()
                    .firstName("Marek")
                    .lastName("Krosny")
                    .phoneNumber("654753734")
                    .build();

            AppUserDetails appUserDetails = AppUserDetails.builder()
                    .appUser(appUser)
                    .role(Role.TEACHER)
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .build();

            appUserDetailsRepository.save(appUserDetails);


            Quiz quiz = Quiz.builder()
                    .title("Quiz 1")
                    .build();
            quizRepository.save(quiz);

            Question question1 = Question.builder()
                    .question("Jaki język jest najlepszy?")
                    .type(QuestionType.SINGLE)
                    .options(List.of("Python", "Java", "C#", "C++"))
                    .correctOptions(List.of("Java"))
                    .quiz(quiz)
                    .build();

            Question question2 = Question.builder()
                    .question("Które typy danych są poprawne w języku Java")
                    .type(QuestionType.MULTI)
                    .options(List.of("int", "float", "number", "string", "String"))
                    .correctOptions(List.of("int", "float", "String"))
                    .quiz(quiz)
                    .build();

            Question question3 = Question.builder()
                    .question("Ułóż te typy danych w kolejności ich rozmiaru od najmniejszego do największego")
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
