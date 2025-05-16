package com.example.loginsystem.service;

import com.example.loginsystem.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public UserRecord createUser(User user) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
            .setEmail(user.getEmail())
            .setPassword(user.getPassword())
            .setDisplayName(user.getDisplayName())
            .setEmailVerified(false);

        return FirebaseAuth.getInstance().createUser(request);
    }

    public UserRecord getUserByEmail(String email) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().getUserByEmail(email);
    }

    public String authenticate(String email, String password) {
        try {
            UserRecord user = getUserByEmail(email);
            // Note: Firebase Admin SDK doesn't provide direct password verification
            // In a production environment, you should use Firebase Authentication on the client side
            // This is a simplified version for demonstration
            return FirebaseAuth.getInstance().createCustomToken(user.getUid());
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Authentication failed", e);
        }
    }

    public void deleteUser(String uid) throws FirebaseAuthException {
        FirebaseAuth.getInstance().deleteUser(uid);
    }

    public UserRecord updateUser(String uid, User user) throws FirebaseAuthException {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
            .setEmail(user.getEmail())
            .setDisplayName(user.getDisplayName());

        return FirebaseAuth.getInstance().updateUser(request);
    }
}
