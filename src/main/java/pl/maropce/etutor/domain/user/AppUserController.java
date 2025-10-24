package pl.maropce.etutor.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.maropce.etutor.domain.quiz.dto.QuizDTO;
import pl.maropce.etutor.domain.user.dto.AppUserDTO;
import pl.maropce.etutor.domain.user.dto.UpdateAppUserDto;
import pl.maropce.etutor.domain.user_details.AppUserDetails;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PatchMapping("/{id}/quizzes/{quizId}")
    public ResponseEntity<Void> addQuizToUser(@PathVariable String id, @PathVariable String quizId) {
        appUserService.addQuizToUser(id, quizId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/quizzes")
    public ResponseEntity<List<QuizDTO>> getUserQuizzes(@PathVariable String id) {
        List<QuizDTO> userQuizzes = appUserService.getUserQuizzes(id);

        return ResponseEntity.ok(userQuizzes);
    }

    @GetMapping("/contacts")
    public ResponseEntity<Page<AppUserDTO>> getContacts(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AppUserDTO> contacts = appUserService.getContacts(userDetails.getId(), pageable);
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<AppUserDTO> getContactDetails(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @PathVariable String id
    ) {
        AppUserDTO contact = appUserService.getContactDetails(userDetails.getId(), id);
        if (contact == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(contact);
    }

    @DeleteMapping("contacts/delete/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable String contactId, @AuthenticationPrincipal AppUserDetails userDetails) {
        appUserService.deleteContact(contactId, userDetails.getId());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me")
    public ResponseEntity<AppUserDTO> updateCurrentUser(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestBody UpdateAppUserDto dto) {

        AppUserDTO updated = appUserService.updateCurrentUser(userDetails.getId(), dto);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUserDTO> updateAnyUser(
            @PathVariable String id,
            @RequestBody UpdateAppUserDto dto) {

        AppUserDTO updated = appUserService.updateAnyUser(id, dto);
        return ResponseEntity.ok(updated);
    }
}
