package org.com.kishor.SwingEmailSender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class EmailSenderUI extends JFrame {

    private JTextField senderField, receiverField, subjectField;
    private JTextArea messageArea;
    private JButton sendButton, clearButton;
    private JLabel statusLabel;
    private EmailService emailService;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    public EmailSenderUI() {
        emailService = new EmailService();
        initializeUI();
    }

    private void initializeUI() {
        // Basic frame setup
        setTitle("Email Sender Application");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Email Sender", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(52, 152, 219), 1),
                        "Email Details",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 14),
                        new Color(44, 62, 80)),
                new EmptyBorder(10, 10, 10, 10)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Sender field
        addFormField(formPanel, "Sender Email:", gbc, 0);
        senderField = new JTextField(20);
        senderField.setToolTipText("Enter your email address");
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(senderField, gbc);

        // Receiver field
        addFormField(formPanel, "Receiver Email:", gbc, 1);
        receiverField = new JTextField(20);
        receiverField.setToolTipText("Enter recipient's email address");
        gbc.gridx = 1;
        formPanel.add(receiverField, gbc);

        // Subject field
        addFormField(formPanel, "Subject:", gbc, 2);
        subjectField = new JTextField(20);
        subjectField.setToolTipText("Enter email subject");
        gbc.gridx = 1;
        formPanel.add(subjectField, gbc);

        // Message area
        addFormField(formPanel, "Message:", gbc, 3);
        messageArea = new JTextArea(10, 20);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setToolTipText("Enter your message here");
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        formPanel.add(scrollPane, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(231, 76, 60));
        clearButton.setForeground(Color.BLACK);
        clearButton.setFocusPainted(false);

        sendButton = new JButton("Send Email");
        sendButton.setBackground(new Color(46, 204, 113));
        sendButton.setForeground(Color.BLACK);
        sendButton.setFocusPainted(false);

        buttonPanel.add(clearButton);
        buttonPanel.add(sendButton);

        // Status panel
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        statusLabel = new JLabel("Ready to send email", JLabel.LEFT);
        statusLabel.setForeground(new Color(44, 62, 80));
        statusPanel.add(statusLabel, BorderLayout.WEST);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateInputs()) {
                    sendEmail();
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
    }

    private void addFormField(JPanel panel, String labelText, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label, gbc);
    }

    private boolean validateInputs() {
        // Validate sender email
        if (!validateEmail(senderField.getText())) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid sender email address",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            senderField.requestFocus();
            return false;
        }

        // Validate receiver email
        if (!validateEmail(receiverField.getText())) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid receiver email address",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            receiverField.requestFocus();
            return false;
        }

        // Validate subject
        if (subjectField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a subject",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            subjectField.requestFocus();
            return false;
        }

        // Validate message
        if (messageArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a message",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            messageArea.requestFocus();
            return false;
        }

        return true;
    }

    private boolean validateEmail(String email) {
        return email != null && !email.trim().isEmpty() && emailPattern.matcher(email).matches();
    }

    private void sendEmail() {
        // Disable send button and update status
        sendButton.setEnabled(false);
        statusLabel.setText("Sending email...");
        statusLabel.setForeground(new Color(52, 152, 219));

        // Create a worker thread to send email
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                String sender = senderField.getText();
                String receiver = receiverField.getText();
                String subject = subjectField.getText();
                String message = messageArea.getText();

                Email email = new Email(sender, receiver, subject, message);
                return emailService.sendEmail(email);
            }

            @Override
            protected void done() {
                try {
                    boolean success = get();
                    if (success) {
                        statusLabel.setText("Email sent successfully!");
                        statusLabel.setForeground(new Color(46, 204, 113));
                        JOptionPane.showMessageDialog(EmailSenderUI.this,
                                "Email Sent Successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        clearForm();
                    } else {
                        statusLabel.setText("Failed to send email");
                        statusLabel.setForeground(new Color(231, 76, 60));
                        JOptionPane.showMessageDialog(EmailSenderUI.this,
                                "Failed to Send Email!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    statusLabel.setText("Error: " + e.getMessage());
                    statusLabel.setForeground(new Color(231, 76, 60));
                } finally {
                    sendButton.setEnabled(true);
                }
            }
        };

        worker.execute();
    }

    private void clearForm() {
        senderField.setText("");
        receiverField.setText("");
        subjectField.setText("");
        messageArea.setText("");
        statusLabel.setText("Ready to send email");
        statusLabel.setForeground(new Color(44, 62, 80));
        senderField.requestFocus();
    }
}
