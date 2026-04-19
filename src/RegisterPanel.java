import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    private Library library;
    private JDialog dialog;
    private ButtonGroup roleGroup;
    private JRadioButton studentRadio, adminRadio;
    private JTextField mobileField, usernameField, enrolmentField, grField;
    private JPasswordField passwordField;

    public RegisterPanel(JDialog dialog, Library library) {
        this.dialog = dialog;
        this.library = library;
        setLayout(new BorderLayout());
        setBackground(new Color(52, 73, 94));
        initModernDesign();
    }

    private void initModernDesign() {
        // ✨ Gradient Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setPreferredSize(new Dimension(0, 120));
        
        JLabel title = new JLabel("✨ CREATE ACCOUNT", SwingConstants.CENTER);
        title.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        headerPanel.add(title, BorderLayout.NORTH);

        JLabel subtitle = new JLabel("Join the Library Community", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        subtitle.setForeground(new Color(173, 216, 230));
        headerPanel.add(subtitle, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        // Perfect Form Container
        JPanel formContainer = new JPanel(new BorderLayout());
        formContainer.setBackground(new Color(248, 249, 250));
        formContainer.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Role Selection - Modern Cards
        JPanel roleCard = createModernRoleCard();
        formContainer.add(roleCard, BorderLayout.NORTH);

        // Input Fields Container
        JPanel inputContainer = new JPanel(new GridBagLayout());
        inputContainer.setBackground(new Color(248, 249, 250));
        inputContainer.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 0, 12, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Modern Input Fields
        gbc.gridy = 0;
        addModernInputField(inputContainer, gbc, "📱 Mobile No:", mobileField = new JTextField());

        gbc.gridy = 1;
        addModernInputField(inputContainer, gbc, "👤 Username:", usernameField = new JTextField());

        gbc.gridy = 2;
        addModernInputField(inputContainer, gbc, "🎓 Enrolment No:", enrolmentField = new JTextField());

        gbc.gridy = 3;
        addModernInputField(inputContainer, gbc, "📚 GR No:", grField = new JTextField());

        gbc.gridy = 4;
        addModernInputField(inputContainer, gbc, "🔒 Password:", passwordField = new JPasswordField());

        formContainer.add(inputContainer, BorderLayout.CENTER);

        // Perfect Action Buttons
        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonContainer.setBackground(new Color(248, 249, 250));
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton cancelBtn = createPerfectButton("❌ Cancel", new Color(239, 83, 80), Color.WHITE);
        JButton registerBtn = createPerfectButton("🚀 Register Now", new Color(30, 144, 255), Color.WHITE);
        registerBtn.addActionListener(e -> performRegistration());

        buttonContainer.add(cancelBtn);
        buttonContainer.add(registerBtn);
        formContainer.add(buttonContainer, BorderLayout.SOUTH);

        add(formContainer, BorderLayout.CENTER);
    }

    private JPanel createModernRoleCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(380, 80));

        JLabel roleTitle = new JLabel("Choose Role", SwingConstants.CENTER);
        roleTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        roleTitle.setForeground(new Color(52, 73, 94));
        card.add(roleTitle, BorderLayout.NORTH);

        roleGroup = new ButtonGroup();
        studentRadio = new JRadioButton("👨‍🎓 Student");
        adminRadio = new JRadioButton("👨‍💼 Admin");
        studentRadio.setFont(new Font("Segoe UI", Font.BOLD, 16));
        adminRadio.setFont(new Font("Segoe UI", Font.BOLD, 16));
        studentRadio.setBackground(Color.WHITE);
        adminRadio.setBackground(Color.WHITE);
        studentRadio.setSelected(true);
        roleGroup.add(studentRadio);
        roleGroup.add(adminRadio);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        radioPanel.setBackground(Color.WHITE);
        radioPanel.add(studentRadio);
        radioPanel.add(adminRadio);
        card.add(radioPanel, BorderLayout.CENTER);

        return card;
    }

    private void addModernInputField(JPanel panel, GridBagConstraints gbc, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(new Color(52, 73, 94));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(280, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        field.setBackground(new Color(255, 255, 255));
        panel.add(field, gbc);
    }

    private JButton createPerfectButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 50));
        return btn;
    }

    private void performRegistration() {
        String mobile = mobileField.getText().trim();
        String username = usernameField.getText().trim();
        String enrolment = enrolmentField.getText().trim();
        String gr = grField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || pass.isEmpty() || mobile.isEmpty() || (studentRadio.isSelected() && (enrolment.isEmpty() || gr.isEmpty()))) {
            JOptionPane.showMessageDialog(this, "⚠️ Please fill all required fields!", "Incomplete", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (studentRadio.isSelected()) {
            library.registerStudent(mobile, username, pass, enrolment, gr);
        } else {
            library.registerAdmin(mobile, username, pass);
        }

        library.saveData();
        JOptionPane.showMessageDialog(this, "✅ Registration successful! Welcome to Library!", "Success", JOptionPane.INFORMATION_MESSAGE);
        dialog.dispose();
    }
}

