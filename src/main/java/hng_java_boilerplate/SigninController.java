package hng_java_boilerplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class SigninController {

    @Autowired
    private SigninService signinService;

    @GetMapping("/signin-token")
    public ResponseEntity<?> requestSigninToken(@RequestBody SigninRequest signinRequest) {
        return signinService.requestSigninToken(signinRequest);
    }

    @PostMapping("/signin-token")
    public ResponseEntity<?> verifySigninToken(@RequestBody VerifyRequest verifyRequest) {
        return signinService.verifySigninToken(verifyRequest);
    }
}
