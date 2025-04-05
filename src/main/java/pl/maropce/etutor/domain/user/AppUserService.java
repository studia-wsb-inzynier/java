package pl.maropce.etutor.domain.user;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.maropce.etutor.domain.quiz.Quiz;
import pl.maropce.etutor.domain.quiz.QuizRepository;
import pl.maropce.etutor.domain.quiz.dto.QuizDTO;
import pl.maropce.etutor.domain.quiz.dto.QuizMapper;
import pl.maropce.etutor.domain.quiz.exception.QuizNotFoundException;
import pl.maropce.etutor.domain.user_details.AppUserDetails;
import pl.maropce.etutor.domain.user_details.AppUserDetailsRepository;
import pl.maropce.etutor.domain.user_details.auth.RegisterRequest;

import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserDetailsRepository appUserDetailsRepository;
    private final PasswordEncoder passwordEncoder;
    private final QuizMapper quizMapper;
    private final QuizRepository quizRepository;

    public AppUserService(AppUserRepository appUserRepository, AppUserDetailsRepository appUserDetailsRepository, PasswordEncoder passwordEncoder, QuizMapper quizMapper, QuizRepository quizRepository) {
        this.appUserRepository = appUserRepository;
        this.appUserDetailsRepository = appUserDetailsRepository;
        this.passwordEncoder = passwordEncoder;
        this.quizMapper = quizMapper;
        this.quizRepository = quizRepository;
    }

    @Transactional
    public AppUserDetails register(RegisterRequest registerRequest) {

        AppUser appUser = AppUser.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .quizList(List.of())
                .build();

        AppUserDetails appUserDetails = AppUserDetails.builder()
                .appUser(appUser)
                .username(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();

        return appUserDetailsRepository.save(appUserDetails);
    }

    public List<QuizDTO> getUserQuizzes(String id) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found! Create an Exception for that!"));

        return appUser.getQuizList().stream()
                .map(quizMapper::toDTO)
                .toList();
    }

    public void addQuizToUser(String id, String quizId) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found! Create an Exception for that!"));
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        appUser.getQuizList().add(quiz);
        appUserRepository.save(appUser);
    }
}
