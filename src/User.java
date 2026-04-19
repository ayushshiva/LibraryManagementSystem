import java.util.Objects;

public class User {
    private String role; // "admin" or "student"
    private String mobile;
    private String username;
    private String password;
    private String enrolmentNo;
    private String grNo;

    public User(String role, String mobile, String username, String password, String enrolmentNo, String grNo) {
        this.role = role;
        this.mobile = mobile;
        this.username = username;
        this.password = password;
        this.enrolmentNo = enrolmentNo;
        this.grNo = grNo;
    }

    // Getters/Setters
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEnrolmentNo() { return enrolmentNo; }
    public void setEnrolmentNo(String enrolmentNo) { this.enrolmentNo = enrolmentNo; }
    public String getGrNo() { return grNo; }
    public void setGrNo(String grNo) { this.grNo = grNo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, username);
    }

    @Override
    public String toString() {
        return String.format("User{role='%s', username='%s', mobile='%s', enrolment='%s', gr='%s'}", 
            role, username, mobile, enrolmentNo, grNo);
    }
}

