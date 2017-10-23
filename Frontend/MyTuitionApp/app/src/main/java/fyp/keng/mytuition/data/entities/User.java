package fyp.keng.mytuition.data.entities;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class User {

    @NonNull
    @SerializedName("id")
    private final int id;

    @NonNull
    @Expose
    @SerializedName("first_name")
    private String firstName;

    @Expose
    @SerializedName("avatar")
    private String avatar;

    @NonNull
    @Expose
    @SerializedName("last_name")
    private String lastName;

    @NonNull
    @Expose
    @SerializedName("email")
    private String email;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("auth_method")
    @Expose
    private AuthMethod authMethod;

    @SerializedName("user_type")
    @Expose
    private UserType userType;

    @SerializedName("subjects")
    @Expose
    private ArrayList<Subject> subjects;

    @SerializedName("price")
    @Expose
    private int price;

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public User(@NonNull int id, String description) {
        this.id = id;
        this.description = description;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        return email.equals(user.email);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean needsToFillProfile() {
        if (!this.userType.equals(UserType.TUTOR)) {
            return false;
        }

        if (TextUtils.isEmpty(this.description)) {
            return true;
        }

        if (this.subjects == null || this.subjects.size() == 0) {
            return true;
        }

        return false;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AuthMethod getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(AuthMethod authMethod) {
        this.authMethod = authMethod;
    }

    public String getAvatar() {
        String url = null;

        if (avatar != null) {

                url = "http://192.168.43.95:8216/images/"+avatar;

        } else {
            url = "android.resource://fyp.keng.mytuition/drawable/profile_picture";
        }

        return url;
    }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public boolean isTutor() { return this.userType.equals(UserType.TUTOR); }

}
