package pl.maropce.etutor.domain.user;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.maropce.etutor.domain.quiz.Quiz;
import pl.maropce.etutor.domain.quiz.QuizRepository;
import pl.maropce.etutor.domain.quiz.dto.QuizDTO;
import pl.maropce.etutor.domain.quiz.dto.QuizMapper;
import pl.maropce.etutor.domain.quiz.exception.QuizNotFoundException;
import pl.maropce.etutor.domain.user.dto.AppUserDTO;
import pl.maropce.etutor.domain.user.dto.AppUserMapper;
import pl.maropce.etutor.domain.user.dto.UpdateAppUserDto;
import pl.maropce.etutor.domain.user.exception.UserNotFoundException;
import pl.maropce.etutor.domain.user_details.AppUserDetails;
import pl.maropce.etutor.domain.user_details.AppUserDetailsRepository;
import pl.maropce.etutor.domain.user_details.auth.ChangePasswordRequest;
import pl.maropce.etutor.domain.user_details.auth.RegisterRequest;
import pl.maropce.etutor.domain.user_details.exception.InvalidCurrentPasswordException;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserDetailsRepository appUserDetailsRepository;
    private final PasswordEncoder passwordEncoder;
    private final QuizMapper quizMapper;
    private final QuizRepository quizRepository;
    private final AppUserMapper appUserMapper;

    public AppUserService(AppUserRepository appUserRepository, AppUserDetailsRepository appUserDetailsRepository, PasswordEncoder passwordEncoder, QuizMapper quizMapper, QuizRepository quizRepository, AppUserMapper appUserMapper) {
        this.appUserRepository = appUserRepository;
        this.appUserDetailsRepository = appUserDetailsRepository;
        this.passwordEncoder = passwordEncoder;
        this.quizMapper = quizMapper;
        this.quizRepository = quizRepository;
        this.appUserMapper = appUserMapper;
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

        appUser.setAppUserDetails(appUserDetails);

        return appUserDetailsRepository.save(appUserDetails);
    }

    public List<QuizDTO> getUserQuizzes(String id) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return appUser.getQuizList().stream()
                .map(quizMapper::toDTO)
                .toList();
    }

    public void addQuizToUser(String id, String quizId) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        appUser.getQuizList().add(quiz);
        appUserRepository.save(appUser);
    }

    public void changePassword(String username, ChangePasswordRequest request) {
        AppUserDetails user = appUserDetailsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidCurrentPasswordException();
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        appUserDetailsRepository.save(user);
    }

    public Page<AppUserDTO> getContacts(String id, Pageable pageable) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        List<AppUserDTO> students = user.getStudents().stream().map(appUserMapper::toDTO).toList();
        List<AppUserDTO> teachers = user.getTeachers().stream().map(appUserMapper::toDTO).toList();

        List<AppUserDTO> contacts = new ArrayList<>();
        contacts.addAll(students);
        contacts.addAll(teachers);

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), contacts.size());

        List<AppUserDTO> pagedList = contacts.subList(start, end);

        return new PageImpl<>(pagedList, pageable, contacts.size());

    }

    @Transactional
    public void deleteContact(String contactId, String appUserId) {

        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(() -> new UserNotFoundException(appUserId));

        AppUser contactToDelete = appUserRepository.findById(contactId).orElseThrow(() -> new UserNotFoundException(contactId));

        appUser.getStudents().remove(contactToDelete);
        appUser.getTeachers().remove(contactToDelete);

        contactToDelete.getStudents().remove(appUser);
        contactToDelete.getTeachers().remove(appUser);

        appUserRepository.save(appUser);
        appUserRepository.save(contactToDelete);
    }

    @Transactional
    public AppUserDTO updateCurrentUser(String userId, UpdateAppUserDto dto) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (dto.getFirstName() != null) appUser.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) appUser.setLastName(dto.getLastName());
        if (dto.getPhoneNumber() != null) appUser.setPhoneNumber(dto.getPhoneNumber());

        AppUser savedUser = appUserRepository.save(appUser);

        return appUserMapper.toDTO(savedUser);
    }

    @Transactional
    public AppUserDTO updateAnyUser(String userId, UpdateAppUserDto dto) {

        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (dto.getFirstName() != null) appUser.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) appUser.setLastName(dto.getLastName());
        if (dto.getPhoneNumber() != null) appUser.setPhoneNumber(dto.getPhoneNumber());

        AppUser savedUser = appUserRepository.save(appUser);

        return appUserMapper.toDTO(savedUser);
    }
}
