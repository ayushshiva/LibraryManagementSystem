import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Member {
    private int id;
    private String name;
    private String email;
    private List<Integer> issuedBookIds;

    public Member(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.issuedBookIds = new ArrayList<>();
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Integer> getIssuedBookIds() { return new ArrayList<>(issuedBookIds); }

    public boolean issueBook(int bookId) {
        return issuedBookIds.add(bookId);
    }

    public boolean returnBook(int bookId) {
        return issuedBookIds.remove(Integer.valueOf(bookId));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id == member.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Member{id=%d, name='%s', email='%s', issuedBooks=%s}", id, name, email, issuedBookIds);
    }
}
