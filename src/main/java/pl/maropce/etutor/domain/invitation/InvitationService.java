package pl.maropce.etutor.domain.invitation;

import org.springframework.stereotype.Service;
import pl.maropce.etutor.domain.invitation.exception.InvalidInvitationCodeException;
import pl.maropce.etutor.domain.user.AppUser;
import pl.maropce.etutor.domain.user.AppUserRepository;
import pl.maropce.etutor.domain.user.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class InvitationService {

    private final AppUserRepository appUserRepository;
    private final InvitationRepository invitationRepository;

    public InvitationService(AppUserRepository appUserRepository, InvitationRepository invitationRepository) {
        this.appUserRepository = appUserRepository;
        this.invitationRepository = invitationRepository;
    }

    public String generateJoinCode(String teacherId) {
        AppUser teacher = appUserRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        String code = UUID.randomUUID().toString();

        InvitationCode joinCode = InvitationCode.builder()
                .code(code)
                .teacher(teacher)
                .createdAt(LocalDateTime.now())
                .build();

        invitationRepository.save(joinCode);

        return code;
    }

    public void joinTeacherByCode(String code, String studentId) {
        AppUser student = appUserRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException("Student not found"));

        InvitationCode joinCode = invitationRepository.findByCode(code)
                .orElseThrow(InvalidInvitationCodeException::new);

        AppUser teacher = joinCode.getTeacher();


        teacher.getStudents().add(student);
        student.getTeachers().add(teacher);

        appUserRepository.save(teacher);
        appUserRepository.save(student);

        invitationRepository.delete(joinCode);
    }
}
