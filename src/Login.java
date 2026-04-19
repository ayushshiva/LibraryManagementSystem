import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private Library library;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private int currentMemberId = 0;
    private Color primaryColor = new Color(30, 144, 255); // Dodger Blue
    private Color bgColor = new Color(248, 249, 250); // Light gray

    public Login() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        library = new Library();
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(bgColor);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(bgColor);

        JPanel loginPanel = createStyledLoginPanel();
        mainPanel.add(loginPanel, "login");

        JPanel adminPanel = createStyledAdminPanel();
        mainPanel.add(adminPanel, "admin");

        JPanel studentPanel = createStyledStudentPanel();
        mainPanel.add(studentPanel, "student");

        add(mainPanel);
        cardLayout.show(mainPanel, "login");
    }

    private JPanel createStyledLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(52, 73, 94)); // Dark slate

        // Header
        JLabel title = new JLabel("Library Management System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        panel.add(title, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(userLabel, gbc);

        JTextField userField = new JTextField(20);
        userField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(userField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(20);
        passField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passField, gbc);

        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 18));
        loginBtn.setBackground(primaryColor);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginBtn.addActionListener(createLoginAction(userField, passField));
        formPanel.add(loginBtn, gbc);

        JButton registerBtn = new JButton("REGISTER");
        registerBtn.setFont(new Font("Arial", Font.BOLD, 16));
        registerBtn.setBackground(new Color(60, 179, 113));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.addActionListener(e -> {
            JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Student", "Admin"});
            if (JOptionPane.showConfirmDialog(this, new Object[]{"Select Role:", roleCombo}, "Choose Role", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                String role = (String) roleCombo.getSelectedItem();
                if ("Student".equals(role)) {
                    JTextField mobile = new JTextField(20);
                    JTextField username = new JTextField(20);
                    JPasswordField pass = new JPasswordField(20);
                    JTextField enrolment = new JTextField(20);
                    JTextField gr = new JTextField(20);
                    Object[] fields = {"Mobile No:", mobile, "Username:", username, "🔒 Password:", pass, "Enrolment No:", enrolment, "GR No:", gr};
                    if (JOptionPane.showConfirmDialog(this, fields, "Student Registration", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        library.registerStudent(mobile.getText(), username.getText(), new String(pass.getPassword()), enrolment.getText(), gr.getText());
                        library.saveData();
                        JOptionPane.showMessageDialog(this, "✅ Student registered & saved!");
                    }
                } else {
                    JTextField mobile = new JTextField(20);
                    JTextField username = new JTextField(20);
                    JPasswordField pass = new JPasswordField(20);
                    Object[] fields = {"Mobile No:", mobile, "Username:", username, "Password:", pass};
                    if (JOptionPane.showConfirmDialog(this, fields, "Admin Registration", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        library.registerAdmin(mobile.getText(), username.getText(), new String(pass.getPassword()));
                        library.saveData();
                        JOptionPane.showMessageDialog(this, "✅ Admin registered & saved!");
                    }
                }
            }
        });
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(registerBtn, gbc);

        gbc.gridy = 4;
        panel.add(formPanel, BorderLayout.CENTER);

        JButton exitBtn = new JButton("EXIT");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFocusPainted(false);
        exitBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        exitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitBtn.addActionListener(e -> System.exit(0));
        panel.add(exitBtn, BorderLayout.SOUTH);

        return panel;
    }

    private ActionListener createLoginAction(JTextField userField, JPasswordField passField) {
        return e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();
            if (library.authenticate(username, password, "admin")) {
                cardLayout.show(mainPanel, "admin");
            } else if (library.authenticate(username, password, "student")) {
                JOptionPane.showInputDialog("Enter Member ID for student view:");
                // Assume currentMemberId set
                cardLayout.show(mainPanel, "student");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Invalid credentials. Register first or use default admin/student.");
            }
            userField.setText("");
            passField.setText("");
        };
    }

    private JPanel createStyledAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(52, 73, 94)); // Match login dark slate

        // Header matching login
        JLabel title = new JLabel("👨‍💼 ADMIN DASHBOARD", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        panel.add(title, BorderLayout.NORTH);

        // Buttons in modern scrollable panel
        JPanel buttonsPanel = new JPanel(new GridLayout(0,2,15,15));
        buttonsPanel.setBackground(new Color(248, 249, 250));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        String[] adminButtons = {
            "➕ Add Book", "📚 View Books", "✅ Available Books", "🔍 Search Book", 
            "🗑️ Remove Book", "👤 Add Member", "👥 View Members", "📖 Issue Book", 
            "🔙 Return Book", "📋 View Issues", "🚪 Logout"
        };
        ActionListener[] adminListeners = {
            e -> showAddBookDialog(),
            e -> showMessage(library.getBooksText()),
            e -> showMessage(library.getAvailableBooksText()),
            e -> showStyledSearchDialog(),
            e -> showRemoveDialog(),
            e -> showAddMemberDialog(),
            e -> showMessage(library.getMembersText()),
            e -> showIssueDialog(),
            e -> showReturnDialog(),
            e -> showMessage(library.getIssuesText()),
            e -> cardLayout.show(mainPanel, "login")
        };

        for (int i = 0; i < adminButtons.length; i++) {
            addStyledButton(buttonsPanel, adminButtons[i], adminListeners[i]);
        }

        JScrollPane scrollPane = new JScrollPane(buttonsPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(52, 73, 94));
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStyledStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(52, 73, 94)); // Match login dark slate

        // Header matching login
        JLabel title = new JLabel("👨‍🎓 STUDENT DASHBOARD", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        panel.add(title, BorderLayout.NORTH);

        // Buttons in modern panel
        JPanel buttonsPanel = new JPanel(new GridLayout(0,1,20,20));
        buttonsPanel.setBackground(new Color(248, 249, 250));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        addStyledButton(buttonsPanel, "📚 View Available Books", e -> showMessage(library.getAvailableBooksText()));
        addStyledButton(buttonsPanel, "🔍 Search Books", e -> showStyledSearchDialog());
        addStyledButton(buttonsPanel, "📋 My Issues", e -> showMessage(library.getMemberIssuesText(currentMemberId)));
        addStyledButton(buttonsPanel, "🚪 Logout", e -> cardLayout.show(mainPanel, "login"));

        panel.add(buttonsPanel, BorderLayout.CENTER);

        return panel;
    }

    private void addStyledButton(JPanel panel, String text, ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(primaryColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setRolloverEnabled(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(listener);
        panel.add(btn);
    }

    private void showStyledSearchDialog() {
        String query = JOptionPane.showInputDialog("Enter title or author to search:");
        if (query != null) showStyledMessage(library.searchBooksText(query), "Search Results");
    }

    private void showRemoveDialog() {
        String idStr = JOptionPane.showInputDialog("Enter book ID to remove:");
        if (idStr != null) {
            try {
                library.removeBook(Integer.parseInt(idStr));
                JOptionPane.showMessageDialog(this, "Book removed if existed.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID.");
            }
        }
    }

    private void showReturnDialog() {
        String idStr = JOptionPane.showInputDialog("Enter issue ID to return:");
        if (idStr != null) {
            try {
                library.returnBookNoScan(Integer.parseInt(idStr));
                JOptionPane.showMessageDialog(this, "Book returned if valid.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID.");
            }
        }
    }

    private void showStyledMessage(String text, String title) {
        JTextArea textArea = new JTextArea(text.replace("\\n", "\n"));
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(this, scroll, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMessage(String text) {
        showStyledMessage(text, "Information");
    }

    // Other dialog methods remain the same
    private void showAddBookDialog() {
        JTextField titleField = new JTextField(15);
        JTextField authorField = new JTextField(15);
        JTextField isbnField = new JTextField(15);
        Object[] fields = {
            "Title:", titleField,
            "Author:", authorField,
            "ISBN:", isbnField
        };
        int option = JOptionPane.showConfirmDialog(this, fields, "Add Book", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            library.addBookNoScan(titleField.getText(), authorField.getText(), isbnField.getText());
        }
    }

    private void showAddMemberDialog() {
        JTextField nameField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        Object[] fields = {
            "Name:", nameField,
            "Email:", emailField
        };
        int option = JOptionPane.showConfirmDialog(this, fields, "Add Member", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            library.addMemberNoScan(nameField.getText(), emailField.getText());
        }
    }

    private void showIssueDialog() {
        JTextField bookField = new JTextField(5);
        JTextField memberField = new JTextField(5);
        Object[] fields = {
            "Book ID:", bookField,
            "Member ID:", memberField
        };
        int option = JOptionPane.showConfirmDialog(this, fields, "Issue Book", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                library.issueBookNoScan(Integer.parseInt(bookField.getText()), Integer.parseInt(memberField.getText()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Cannot issue book.");
            }
        }
    }

}
