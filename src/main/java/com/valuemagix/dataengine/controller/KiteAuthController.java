package com.valuemagix.dataengine.controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;

@RestController
@RequestMapping("/kite")
public class KiteAuthController {

    private static final Logger log = LoggerFactory.getLogger(KiteAuthController.class);

    private final KiteConnect kiteConnect;

    @Value("${kite.api_secret}")
    private String apiSecret;

    public KiteAuthController(KiteConnect kiteConnect) {
        this.kiteConnect = kiteConnect;
    }

    /** Step 1: Get login URL */
    @GetMapping("/login-url")
    public ResponseEntity<String> getLoginUrl(@RequestParam(required = false) String id) {
        String loginUrl = kiteConnect.getLoginURL();
        if (id != null) {
            log.info("Login URL requested for ID: {}", id);
        }
        return ResponseEntity.ok(loginUrl);
    }

    /** Step 2 & 3: Accept full redirect URL, extract request_token, generate access token 
     * @throws KiteException */
    @PostMapping("/generate-access-token")
    public ResponseEntity<String> generateAccessToken(
            @RequestParam String redirectUrl,
            @RequestParam(required = false) String id) throws Exception, KiteException {

        // Extract request_token using regex
        Pattern pattern = Pattern.compile("request_token=([^&]+)");
        Matcher matcher = pattern.matcher(redirectUrl);
        if (!matcher.find()) {
            return ResponseEntity.badRequest().body("Could not extract request_token from URL");
        }
        String requestToken = matcher.group(1);

        // Generate access token
        User user = kiteConnect.generateSession(requestToken, apiSecret);
        kiteConnect.setAccessToken(user.accessToken);

        if (id != null) {
            log.info("Access token generated for ID: {}", id);
            // Optionally persist to DB
        }

        return ResponseEntity.ok("Access token generated and set successfully: " + user.accessToken);
    }

    @GetMapping("/test-connection")
    public ResponseEntity<String> testConnection() {
        try {
            // This will fail if token is missing or invalid
            var profile = kiteConnect.getProfile();
            return ResponseEntity.ok("KiteConnect working. User: " + profile.userName);
        } catch (Exception | KiteException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("KiteConnect connection failed: " + e.getMessage());
        }
    }

}
