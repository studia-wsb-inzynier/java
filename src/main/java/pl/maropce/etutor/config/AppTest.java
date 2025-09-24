package pl.maropce.etutor.config;

import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
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
@Profile("dev")
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

            createUsers();
            createQuizzes();
        };
    }

    private void createUsers() {
        AppUser appUser = AppUser.builder()
                .firstName("Mirek")
                .lastName("Adminowy")
                .phoneNumber("654753734")
                .build();

        AppUserDetails appUserDetails = AppUserDetails.builder()
                .appUser(appUser)
                .role(Role.ADMIN)
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .isEnabled(true)
                .build();

        appUser.setAppUserDetails(appUserDetails);

        appUserDetailsRepository.save(appUserDetails);

        /////////////////////////////////////////////////////////////

        AppUser appUser2 = AppUser.builder()
                .firstName("Juliusz")
                .lastName("Ticzerowy")
                .phoneNumber("834028457")
                .build();

        AppUserDetails appUserDetails2 = AppUserDetails.builder()
                .appUser(appUser2)
                .role(Role.TEACHER)
                .username("teacher")
                .password(passwordEncoder.encode("teacher"))
                .isEnabled(true)
                .build();

        appUser2.setAppUserDetails(appUserDetails2);

        appUserDetailsRepository.save(appUserDetails2);

        /////////////////////////////////////////////////////////////

        AppUser appUser3 = AppUser.builder()
                .firstName("Mateusz")
                .lastName("Uczniowy")
                .phoneNumber("381203285")
                .build();

        AppUserDetails appUserDetails3 = AppUserDetails.builder()
                .appUser(appUser3)
                .role(Role.STUDENT)
                .username("student")
                .password(passwordEncoder.encode("student"))
                .isEnabled(true)
                .build();

        appUser3.setAppUserDetails(appUserDetails3);

        appUserDetailsRepository.save(appUserDetails3);
    }

    private void createQuizzes() {
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
                .type(QuestionType.ORDER)
                .options(List.of("int", "short", "byte", "double"))
                .correctOptions(List.of("byte", "short", "int", "double"))
                .quiz(quiz)
                .build();

        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);
    }
}
