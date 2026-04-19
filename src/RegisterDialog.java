import javax.swing.*;
import java.awt.*;

public class RegisterDialog extends JDialog {

    public RegisterDialog(JFrame parent, Library library) {
        super(parent, "Register", ModalityType.APPLICATION_MODAL);
        setSize(450, 650);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(52, 73, 94)); // Match login dark slate

        // Header
        JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        add(title, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBackground(new Color(52, 73, 94));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Role toggle - white labels matching login
        JLabel roleLabel = new JLabel("Register as:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        roleLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        formPanel.add(roleLabel, gbc);

        JToggleButton studentBtn = new JToggleButton("Student");
        studentBtn.setFont(new Font("Arial", Font.BOLD, 16));
        studentBtn.setBackground(Color.WHITE);
        studentBtn.setForeground(new Color(52, 73, 94));
        studentBtn.setFocusPainted(false);
        studentBtn.setBorderPainted(false);
        studentBtn.setPreferredSize(new Dimension(100, 45));
        studentBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 1;
        gbc.gridx = 0;
        formPanel.add(studentBtn, gbc);

        JToggleButton adminBtn = new JToggleButton("Admin");
        adminBtn.setFont(new Font("Arial", Font.BOLD, 16));
        adminBtn.setBackground(Color.LIGHT_GRAY);
        adminBtn.setForeground(Color.GRAY);
        adminBtn.setFocusPainted(false);
        adminBtn.setBorderPainted(false);
        adminBtn.setPreferredSize(new Dimension(100, 45));
        adminBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 1;
        formPanel.add(adminBtn, gbc);

        // Form fields - white matching login
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel mobileLabel = new JLabel("Mobile No:");
        mobileLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        mobileLabel.setForeground(Color.WHITE);
        formPanel.add(mobileLabel, gbc);

        gbc.gridx = 1;
        JTextField mobileField = new JTextField(20);
        mobileField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(mobileField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(usernameField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel enrolmentLabel = new JLabel("Enrolment No:");
        enrolmentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        enrolmentLabel.setForeground(Color.WHITE);
        formPanel.add(enrolmentLabel, gbc);

        gbc.gridx = 1;
        JTextField enrolmentField = new JTextField(20);
        enrolmentField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(enrolmentField, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        JLabel grLabel = new JLabel("GR No:");
        grLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        grLabel.setForeground(Color.WHITE);
        formPanel.add(grLabel, gbc);

        gbc.gridx = 1;
        JTextField grField = new JTextField(20);
        grField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(grField, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passLabel.setForeground(Color.WHITE);
        formPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passField = new JPasswordField(20);
        passField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(passField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Bottom buttons matching login
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelBtn.addActionListener(e -> dispose());
        JButton registerBtn = new JButton("REGISTER");
        registerBtn.setFont(new Font("Arial", Font.BOLD, 18));
        registerBtn.setBackground(new Color(30, 144, 255));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.addActionListener(e -> {
            String mobile = mobileField.getText().trim();
            String username = usernameField.getText().trim();
            String enrolment = enrolmentField.getText().trim();
            String gr = grField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            
            if (username.isEmpty() || pass.isEmpty() || mobile.isEmpty() || enrolment.isEmpty() || gr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Please fill all fields!", "Incomplete", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (studentBtn.isSelected()) {
                library.registerStudent(mobile, username, pass, enrolment, gr);
            } else if (adminBtn.isSelected()) {
                library.registerAdmin(mobile, username, pass);
            }
            
            library.saveData();
            JOptionPane.showMessageDialog(this, "✅ Registered successfully! Data saved.");
            dispose();
        });
        buttonPanel.add(cancelBtn);
        buttonPanel.add(registerBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

}

