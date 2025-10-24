package pl.maropce.etutor.domain.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final QuizMapper quizMapper;
    private final QuizRepository quizRepository;
    private final AppUserMapper appUserMapper;

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

    public AppUserDTO getContactDetails(String currentUserId, String contactId) {
        AppUser currentUser = appUserRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException(currentUserId));

        List<AppUser> contacts = new ArrayList<>();
        contacts.addAll(currentUser.getStudents());
        contacts.addAll(currentUser.getTeachers());

        return contacts.stream()
                .filter(contact -> contact.getId().equals(contactId))
                .findFirst()
                .map(appUserMapper::toDTO)
                .orElse(null);
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
