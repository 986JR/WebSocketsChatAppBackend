package com.message.chat.controller;

import com.message.chat.model.User;
import com.message.chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // POST /api/users/register
    // Body: { "username": "alice", "password": "1234" }
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> body) {
        User user = userService.register(body.get("username"), body.get("password"));
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "message", "Registration successful"
        ));
    }

    // POST /api/users/login
    // Body: { "username": "alice", "password": "1234" }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        User user = userService.login(body.get("username"), body.get("password"));
        return ResponseEntity.ok(Map.of(
                "username", user.getUsername(),
                "message", "Login successful"
        ));
    }
}
