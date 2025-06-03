package controller;

import com.example.loginsystem.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
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

    public UserRecord getUserByUid(String uid) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().getUser(uid);
    }

    public String authenticate(String email, String password) {
        try {
            // Get user by email to verify they exist
            UserRecord user = getUserByEmail(email);
            
            // Note: Firebase Admin SDK doesn't provide password verification
            // In a real application, you should:
            // 1. Use Firebase Client SDK on frontend for authentication
            // 2. Send the ID token to backend for verification
            // 3. This is a simplified version for demonstration
            
            // Create a custom token for the user
            return FirebaseAuth.getInstance().createCustomToken(user.getUid());
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage(), e);
        }
    }

    public boolean verifyToken(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            return decodedToken != null;
        } catch (FirebaseAuthException e) {
            return false;
        }
    }

    public String verifyTokenAndGetUid(String idToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        return decodedToken.getUid();
    }

    public void deleteUser(String uid) throws FirebaseAuthException {
        FirebaseAuth.getInstance().deleteUser(uid);
    }

    public UserRecord updateUser(String uid, User user) throws FirebaseAuthException {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid);
        
        if (user.getEmail() != null) {
            request.setEmail(user.getEmail());
        }
        
        if (user.getDisplayName() != null) {
            request.setDisplayName(user.getDisplayName());
        }
        
        if (user.getPassword() != null) {
            request.setPassword(user.getPassword());
        }

        return FirebaseAuth.getInstance().updateUser(request);
    }

    public void setEmailVerified(String uid, boolean verified) throws FirebaseAuthException {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
            .setEmailVerified(verified);
        
        FirebaseAuth.getInstance().updateUser(request);
    }
}