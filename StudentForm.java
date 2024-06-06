import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentForm extends JFrame implements ActionListener {
    // GUI components
    JTextField nameField, fatherNameField, rollNumberField, departmentField;
    JButton insertButton, updateButton, deleteButton, viewButton;

    // Constructor to set up the GUI
    public StudentForm() {
        // Set up the frame
        setTitle("Student Form");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        // Initialize GUI components
        nameField = new JTextField();
        fatherNameField = new JTextField();
        rollNumberField = new JTextField();
        departmentField = new JTextField();
        insertButton = new JButton("Insert");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        viewButton = new JButton("View All");

        // Add components to the frame
        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Father's Name:"));
        add(fatherNameField);
        add(new JLabel("Roll Number:"));
        add(rollNumberField);
        add(new JLabel("Department:"));
        add(departmentField);
        add(insertButton);
        add(updateButton);
        add(deleteButton);
        add(viewButton);

        // Add action listeners to the buttons
        insertButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        viewButton.addActionListener(this);

        // Make the frame visible
        setVisible(true);
    }

    // Action performed method to handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String fatherName = fatherNameField.getText();
        String rollNumber = rollNumberField.getText();
        String department = departmentField.getText();

        if (e.getSource() == insertButton) {
            // Insert data into the database
            insertData(name, fatherName, rollNumber, department);
        } else if (e.getSource() == updateButton) {
            // Update data in the database
            updateData(name, fatherName, rollNumber, department);
        } else if (e.getSource() == deleteButton) {
            // Delete data from the database
            deleteData(rollNumber);
        } else if (e.getSource() == viewButton) {
            // View all data from the database
            viewData();
        }
    }

    // Method to insert data into the database
    public void insertData(String name, String fatherName, String rollNumber, String department) {
        String url = "jdbc:mysql://localhost:3306/StudentDB";
        String username = "root";
        String password = "12345";

        try {
            // Load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create a connection
            Connection con = DriverManager.getConnection(url, username, password);

            // Create a SQL insert statement
            String query = "INSERT INTO students (name, father_name, roll_number, department) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, fatherName);
            pstmt.setString(3, rollNumber);
            pstmt.setString(4, department);

            // Execute the statement
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Record inserted successfully!");
            }

            // Close the connection
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // Method to update data in the database
    public void updateData(String name, String fatherName, String rollNumber, String department) {
        String url = "jdbc:mysql://localhost:3306/StudentDB";
        String username = "root";
        String password = "12345";

        try {
            // Load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create a connection
            Connection con = DriverManager.getConnection(url, username, password);

            // Create a SQL update statement
            String query = "UPDATE students SET name = ?, father_name = ?, department = ? WHERE roll_number = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, fatherName);
            pstmt.setString(3, department);
            pstmt.setString(4, rollNumber);

            // Execute the statement
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Record updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No record found with the given roll number!");
            }

            // Close the connection
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // Method to delete data from the database
    public void deleteData(String rollNumber) {
        String url = "jdbc:mysql://localhost:3306/StudentDB";
        String username = "root";
        String password = "12345";

        try {
            // Load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create a connection
            Connection con = DriverManager.getConnection(url, username, password);

            // Create a SQL delete statement
            String query = "DELETE FROM students WHERE roll_number = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, rollNumber);

            // Execute the statement
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Record deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No record found with the given roll number!");
            }

            // Close the connection
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // Method to view all data from the database
    public void viewData() {
        String url = "jdbc:mysql://localhost:3306/StudentDB";
        String username = "root";
        String password = "12345";

        try {
            // Load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create a connection
            Connection con = DriverManager.getConnection(url, username, password);

            // Create a SQL select statement
            String query = "SELECT * FROM students";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Create a text area to display the result
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);

            // Append the fetched records to the text area
            while (rs.next()) {
                textArea.append("Name: " + rs.getString("name") + "\n");
                textArea.append("Father's Name: " + rs.getString("father_name") + "\n");
                textArea.append("Roll Number: " + rs.getString("roll_number") + "\n");
                textArea.append("Department: " + rs.getString("department") + "\n");
                textArea.append("=====================================\n");
            }

            // Create a scroll pane to display the text area
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            // Display the scroll pane in a dialog
            JOptionPane.showMessageDialog(this, scrollPane, "Student Records", JOptionPane.INFORMATION_MESSAGE);

            // Close the connection
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        // Run the application on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new StudentForm());
    }
}
