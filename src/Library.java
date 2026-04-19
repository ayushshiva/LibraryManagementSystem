import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Library {
    private List<Book> books;
    private List<Member> members;
    private List<IssueEntry> issues;
    private int nextBookId = 1;
    private int nextMemberId = 1;
    private int nextIssueId = 1;

    private List<User> users = new ArrayList<>();

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.issues = new ArrayList<>();
        this.users = new ArrayList<>();
        
        // Sample books
        books.add(new Book(nextBookId++, "1984", "George Orwell", "978-0451524935"));
        books.add(new Book(nextBookId++, "To Kill a Mockingbird", "Harper Lee", "978-0061120084"));
        books.add(new Book(nextBookId++, "The Great Gatsby", "F. Scott Fitzgerald", "978-0743273565"));
        books.add(new Book(nextBookId++, "Pride and Prejudice", "Jane Austen", "978-0141439518"));
    }

    public boolean authenticate(String username, String password, String role) {
        return users.stream().anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password) && u.getRole().equals(role));
    }

    public void registerAdmin(String mobile, String username, String password) {
        users.add(new User("admin", mobile, username, password, "", ""));
    }

    public void registerStudent(String mobile, String username, String password, String enrolmentNo, String grNo) {
        users.add(new User("student", mobile, username, password, enrolmentNo, grNo));
    }

    // Save/Load data to JSON file
    public void saveData() {
        // Simple text file for demo
        try (java.io.PrintWriter writer = new java.io.PrintWriter("library_data.txt")) {
            writer.println("USERS");
            for (User u : users) writer.println(u.getRole() + "|" + u.getMobile() + "|" + u.getUsername() + "|" + u.getPassword() + "|" + u.getEnrolmentNo() + "|" + u.getGrNo());
            writer.println("BOOKS");
            for (Book b : books) writer.println(b.getId() + "|" + b.getTitle() + "|" + b.getAuthor() + "|" + b.getIsbn() + "|" + b.isAvailable());
            writer.println("MEMBERS");
            for (Member m : members) writer.println(m.getId() + "|" + m.getName() + "|" + m.getEmail() + "|" + m.getIssuedBookIds());
            writer.println("ISSUES");
            for (IssueEntry i : issues) writer.println(i.getId() + "|" + i.getBookId() + "|" + i.getMemberId() + "|" + i.getIssueDate() + "|" + i.getReturnDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        try (java.util.Scanner scanner = new java.util.Scanner(new java.io.File("library_data.txt"))) {
            String section = "";
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                if (line.equals("USERS") || line.equals("BOOKS") || line.equals("MEMBERS") || line.equals("ISSUES")) {
                    section = line;
                    continue;
                }
                String[] parts = line.split("\\|");
                if (section.equals("USERS") && parts.length == 6) {
                    users.add(new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
                } // Add other sections parsing later
            }
        } catch (Exception e) {
            // No file, start empty
        }
    }

    public void addBook(Scanner scanner) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        Book book = new Book(nextBookId++, title, author, isbn);
        books.add(book);
        System.out.println("Book added: " + book);
    }

    public void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        System.out.println("\n--- Books ---");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void viewAvailableBooks() {
        List<Book> available = books.stream().filter(Book::isAvailable).collect(Collectors.toList());
        if (available.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        System.out.println("\n--- Available Books ---");
        for (Book book : available) {
            System.out.println(book);
        }
    }

    public void searchBook(Scanner scanner) {
        System.out.print("Enter title or author to search: ");
        String query = scanner.nextLine().toLowerCase();
        List<Book> results = books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(query) || b.getAuthor().toLowerCase().contains(query))
                .collect(Collectors.toList());
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("\n--- Search Results ---");
            for (Book book : results) {
                System.out.println(book);
            }
        }
    }

    public void removeBook(Scanner scanner) {
        System.out.print("Enter book ID to remove: ");
        int id = Integer.parseInt(scanner.nextLine());
        books.removeIf(b -> b.getId() == id);
        System.out.println("Book removed if existed.");
    }

    public void addMember(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        Member member = new Member(nextMemberId++, name, email);
        members.add(member);
        System.out.println("Member added: " + member);
    }

    public void viewMembers() {
        if (members.isEmpty()) {
            System.out.println("No members.");
            return;
        }
        System.out.println("\n--- Members ---");
        for (Member member : members) {
            System.out.println(member);
        }
    }

    public void issueBook(Scanner scanner) {
        System.out.print("Enter book ID: ");
        int bookId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter member ID: ");
        int memberId = Integer.parseInt(scanner.nextLine());

        Book book = findBook(bookId);
        Member member = findMember(memberId);
        if (book != null && member != null && book.isAvailable()) {
            IssueEntry issue = new IssueEntry(nextIssueId++, bookId, memberId);
            issues.add(issue);
            book.setAvailable(false);
            member.issueBook(bookId);
            System.out.println("Book issued: " + issue);
        } else {
            System.out.println("Cannot issue: Book or member not found, or book not available.");
        }
    }

    public void returnBook(Scanner scanner) {
        System.out.print("Enter issue ID: ");
        int issueId = Integer.parseInt(scanner.nextLine());
        IssueEntry issue = findIssue(issueId);
        if (issue != null && issue.getReturnDate() == null) {
            issue.setReturnDate(LocalDate.now());
            Book book = findBook(issue.getBookId());
            Member member = findMember(issue.getMemberId());
            if (book != null) book.setAvailable(true);
            if (member != null) member.returnBook(issue.getBookId());
            System.out.println("Book returned: " + issue);
        } else {
            System.out.println("Issue not found or already returned.");
        }
    }

    public void viewIssues() {
        if (issues.isEmpty()) {
            System.out.println("No issues.");
            return;
        }
        System.out.println("\n--- Issues ---");
        for (IssueEntry issue : issues) {
            System.out.println(issue);
        }
    }

    public void viewMemberIssues(int memberId) {
        List<IssueEntry> memberIssues = issues.stream()
            .filter(i -> i.getMemberId() == memberId)
            .collect(Collectors.toList());
        if (memberIssues.isEmpty()) {
            System.out.println("No issues for this member.");
            return;
        }
        System.out.println("\n--- Your Issues ---");
        for (IssueEntry issue : memberIssues) {
            System.out.println(issue);
        }
    }

    private Book findBook(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    private Member findMember(int id) {
        return members.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }

    private IssueEntry findIssue(int id) {
        return issues.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }

    // GUI helpers
    public String getBooksText() {
        if (books.isEmpty()) return "No books.";
        StringBuilder sb = new StringBuilder("Books:\\n");
        for (Book b : books) sb.append(b).append("\\n");
        return sb.toString();
    }

    public String getAvailableBooksText() {
        List<Book> avail = books.stream().filter(Book::isAvailable).collect(Collectors.toList());
        if (avail.isEmpty()) return "No available books.";
        StringBuilder sb = new StringBuilder("Available Books:\\n");
        for (Book b : avail) sb.append(b).append("\\n");
        return sb.toString();
    }

    public String searchBooksText(String query) {
        String q = query.toLowerCase();
        List<Book> results = books.stream()
            .filter(b -> b.getTitle().toLowerCase().contains(q) || b.getAuthor().toLowerCase().contains(q))
            .collect(Collectors.toList());
        if (results.isEmpty()) return "No results.";
        StringBuilder sb = new StringBuilder("Search Results:\\n");
        for (Book b : results) sb.append(b).append("\\n");
        return sb.toString();
    }

    public String getMembersText() {
        if (members.isEmpty()) return "No members.";
        StringBuilder sb = new StringBuilder("Members:\\n");
        for (Member m : members) sb.append(m).append("\\n");
        return sb.toString();
    }

    public String getIssuesText() {
        if (issues.isEmpty()) return "No issues.";
        StringBuilder sb = new StringBuilder("Issues:\\n");
        for (IssueEntry i : issues) sb.append(i).append("\\n");
        return sb.toString();
    }

    public String getMemberIssuesText(int memberId) {
        List<IssueEntry> mis = issues.stream().filter(i -> i.getMemberId() == memberId).collect(Collectors.toList());
        if (mis.isEmpty()) return "No issues.";
        StringBuilder sb = new StringBuilder("Your Issues:\\n");
        for (IssueEntry i : mis) sb.append(i).append("\\n");
        return sb.toString();
    }

    public void removeBook(int id) {
        books.removeIf(b -> b.getId() == id);
    }

    public void addBookNoScan(String title, String author, String isbn) {
        Book book = new Book(nextBookId++, title, author, isbn);
        books.add(book);
    }

    public void addMemberNoScan(String name, String email) {
        Member member = new Member(nextMemberId++, name, email);
        members.add(member);
    }

    public void issueBookNoScan(int bookId, int memberId) {
        Book book = findBook(bookId);
        Member member = findMember(memberId);
        if (book != null && member != null && book.isAvailable()) {
            IssueEntry issue = new IssueEntry(nextIssueId++, bookId, memberId);
            issues.add(issue);
            book.setAvailable(false);
            member.issueBook(bookId);
        }
    }

    public void returnBookNoScan(int issueId) {
        IssueEntry issue = findIssue(issueId);
        if (issue != null && issue.getReturnDate() == null) {
            issue.setReturnDate(LocalDate.now());
            Book book = findBook(issue.getBookId());
            Member member = findMember(issue.getMemberId());
            if (book != null) book.setAvailable(true);
            if (member != null) member.returnBook(issue.getBookId());
        }
    }
}
