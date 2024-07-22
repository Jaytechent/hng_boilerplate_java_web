package hng_java_boilerplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SigninService {

    @Autowired
    private JavaMailSender mailSender;

    private Map<String, String> tokenStorage = new HashMap<>();

    public ResponseEntity<?> requestSigninToken(SigninRequest signinRequest) {
        String token = generateToken();
        tokenStorage.put(signinRequest.getEmail(), token);
        sendEmail(signinRequest.getEmail(), token);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Sign-in token sent to email");
        response.put("status_code", 200);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<?> verifySigninToken(VerifyRequest verifyRequest) {
        String token = tokenStorage.get(verifyRequest.getEmail());
        if (token != null && token.equals(verifyRequest.getToken())) {
            // Generate JWT (dummy token for now)
            String jwtToken = "dummy-jwt-token";

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Sign-in successful");
            response.put("token", jwtToken);
            response.put("status_code", 200);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid token or email");
            response.put("status_code", 401);

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    private String generateToken() {
        Random random = new Random();
        int token = 100000 + random.nextInt(900000);
        return String.valueOf(token);
    }

    private void sendEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Sign-in Token");
        message.setText("Your sign-in token is: " + token);
        mailSender.send(message);
    }
}
