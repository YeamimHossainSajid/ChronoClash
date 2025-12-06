package com.example.UserService.controller;

import com.example.UserService.model.*;
import com.example.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createUser(request.getUsername(), request.getEmail(), request.getPasswordHash());
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody UpdateUserRequest request) {
        try {
            User user = userService.updateUser(id, request.getUsername(), request.getEmail());
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{followerId}/follow/{followingId}")
    public ResponseEntity<UserRelationship> followUser(
            @PathVariable UUID followerId,
            @PathVariable UUID followingId) {
        try {
            UserRelationship relationship = userService.followUser(followerId, followingId);
            return ResponseEntity.ok(relationship);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{followerId}/follow/{followingId}")
    public ResponseEntity<Void> unfollowUser(
            @PathVariable UUID followerId,
            @PathVariable UUID followingId) {
        try {
            userService.unfollowUser(followerId, followingId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<List<UserRelationship>> getFollowing(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getFollowing(userId));
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<UserRelationship>> getFollowers(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getFollowers(userId));
    }

    @GetMapping("/{userId}/timeline-events")
    public ResponseEntity<List<UserTimelineEvent>> getTimelineEvents(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUserTimelineEvents(userId));
    }

    @PostMapping("/{userId}/era-preferences")
    public ResponseEntity<EraPreference> addEraPreference(
            @PathVariable UUID userId,
            @RequestBody EraPreferenceRequest request) {
        try {
            EraPreference preference = userService.addEraPreference(
                userId, request.getEraName(), request.getAestheticStyle());
            return ResponseEntity.ok(preference);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{userId}/era-preferences")
    public ResponseEntity<List<EraPreference>> getEraPreferences(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUserEraPreferences(userId));
    }

    @PutMapping("/{userId}/current-era")
    public ResponseEntity<User> updateCurrentEra(
            @PathVariable UUID userId,
            @RequestBody UpdateEraRequest request) {
        try {
            userService.updateCurrentEra(userId, request.getEraName());
            return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // DTOs
    public static class CreateUserRequest {
        private String username;
        private String email;
        private String passwordHash;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPasswordHash() { return passwordHash; }
        public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    }

    public static class UpdateUserRequest {
        private String username;
        private String email;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    public static class EraPreferenceRequest {
        private String eraName;
        private String aestheticStyle;

        public String getEraName() { return eraName; }
        public void setEraName(String eraName) { this.eraName = eraName; }
        public String getAestheticStyle() { return aestheticStyle; }
        public void setAestheticStyle(String aestheticStyle) { this.aestheticStyle = aestheticStyle; }
    }

    public static class UpdateEraRequest {
        private String eraName;

        public String getEraName() { return eraName; }
        public void setEraName(String eraName) { this.eraName = eraName; }
    }
}

