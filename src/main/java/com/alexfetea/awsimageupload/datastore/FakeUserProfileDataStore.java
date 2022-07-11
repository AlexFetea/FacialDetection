package com.alexfetea.awsimageupload.datastore;

import com.alexfetea.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("2aeb885d-8630-4880-b4a3-e83aaa8f6a80"), "janetjones", null));
    }

    public List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }

}
