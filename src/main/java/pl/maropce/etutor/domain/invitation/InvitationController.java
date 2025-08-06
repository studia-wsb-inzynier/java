package pl.maropce.etutor.domain.invitation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.maropce.etutor.domain.user_details.AppUserDetails;

@RestController
@RequestMapping("/api/invitation")
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping("/generate-code")
    public ResponseEntity<String> generateCode(@AuthenticationPrincipal AppUserDetails appUserDetails) {
        String code = invitationService.generateJoinCode(appUserDetails.getId());
        return ResponseEntity.ok(code);
    }

    @PostMapping("/join/{code}")
    public ResponseEntity<String> joinTeacher(@PathVariable String code,
                                              @AuthenticationPrincipal AppUserDetails appUserDetails) {
        invitationService.joinTeacherByCode(code, appUserDetails.getId());

        return ResponseEntity.ok("Contact added successfully!");
    }
}
