import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardPanel extends JPanel {

    private JTabbedPane tabbedPane;
    private LogoutListener logoutListener;

    public interface LogoutListener{
        void onLogout();
    }

    public DashboardPanel(LogoutListener logoutListener){
        this.logoutListener = logoutListener;
        setLayout(new BorderLayout());

        //INITIALIZE TABBED PANE
        tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI(){
            @Override
            protected int calculateTabAreaHeight(int tabPlacement,int rowCount,int maxTabHeight){
                return 0;
            }
        });

        //PANELS
        tabbedPane.addTab("Home", createHomeContent());
        tabbedPane.addTab("Residency", createPlaceHolderContent("Residency Tab"));
        tabbedPane.addTab("Indigency", createPlaceHolderContent("Indigency Tab"));
        tabbedPane.addTab("Clearance", createClearanceContent());
        tabbedPane.addTab("Rentals", createRentalContent());

        //SIDE BAR NAVIGATION
        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar,BoxLayout.Y_AXIS));
        sideBar.setBackground(new Color(41,50,65));
        sideBar.setPreferredSize(new Dimension(220,getHeight()));

        //SIDE BAR NAVIGATION BUTTONS
        sideBar.add(createNavButton("Home",0));
        sideBar.add(Box.createRigidArea(new Dimension(0,5)));
        sideBar.add(createNavButton("Residency",1));
        sideBar.add(Box.createRigidArea(new Dimension(0,5)));
        sideBar.add(createNavButton("Indigency",2));
        sideBar.add(Box.createRigidArea(new Dimension(0,5)));
        sideBar.add(createNavButton("Clearance",3));
        sideBar.add(Box.createRigidArea(new Dimension(0,5)));
        sideBar.add(createNavButton("Rentals",4));
        sideBar.add(Box.createRigidArea(new Dimension(0,5)));

        //PUSH LOGOUT BUTTON TO THE BOTTOM OF THE SIDEBARR
        sideBar.add(Box.createVerticalGlue());

        //LOGOUT BUTTON
        JButton logoutButton = new JButton("Logout");
        logoutButton.setMaximumSize(new Dimension(190,40));
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setHorizontalAlignment(SwingConstants.LEFT);
        logoutButton.setBackground(new Color(244,49,49));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e ->{
            if(logoutListener != null){
                logoutListener.onLogout();
            }
        });

        sideBar.add(logoutButton);
        sideBar.add(Box.createRigidArea(new Dimension(0,20)));

        //ADD COMPONENTS TO DASHBOARD
        add(sideBar, BorderLayout.WEST);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JButton createNavButton(String text, int tabIndex){
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(200,40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBackground(new Color(41,50,65));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));
        button.addActionListener(e-> tabbedPane.setSelectedIndex(tabIndex));
        return button;
    }

    private JPanel createHomeContent(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel("Welcome back, NASH");
        label.setFont(new Font("Arial", Font.BOLD,20));
        panel.add(label);
        return panel;
    }

    private JPanel createPlaceHolderContent(String title){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(title, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD,20));
        panel.add(label);
        return panel;
    }

    // RENTALS TAB
    private JPanel createRentalContent() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(245, 247, 250));

        // HARDCODED DATA
        String[] columnNames = {"ID", "Name", "Type", "Date", "Fee"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // POPULATE HARDCODED DATA WHEN OPENED
        tableModel.addRow(new Object[]
                {"RNT-0001", "Bogart Dimagiba", "Covered Court", LocalDate.now().plusDays(2).toString(), "1500.00"});
        tableModel.addRow(new Object[]
                {"RNT-0002", "Maria Pola", " Tent", LocalDate.now().plusDays(5).toString(), "300.00"});

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // INPUT FORM PANEL
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtRenterName = new JTextField(12);
        JComboBox<String> cmbRentalType = new JComboBox<>(new String[] {"Covered Court", "Tent"});
        JTextField txtRentalDate = new JTextField(LocalDate.now().plusDays(1).toString(), 1);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Renter Name: "), gbc);
        gbc.gridx = 1;
        formPanel.add(txtRenterName, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Renter Type: "), gbc);
        gbc.gridx = 3;
        formPanel.add(cmbRentalType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Date (YYYY-MM-DD): "), gbc);
        gbc.gridx = 1;
        formPanel.add(txtRentalDate, gbc);

        // ACTION BUTTONS
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);
        JButton btnAdd = new JButton("Add new");
        JButton btnUpdate = new JButton("Update Selected");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear Form");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;

        formPanel.add(buttonPanel, gbc);
        panel.add(formPanel, BorderLayout.SOUTH);

        // LISTENERS
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                txtRenterName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                cmbRentalType.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
                txtRentalDate.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        });

        //CREATE ACTION
        btnAdd.addActionListener(e -> {
            try {
                String name = txtRenterName.getText().trim();
                String type = (String) cmbRentalType.getSelectedItem();
                LocalDate date = LocalDate.parse(txtRentalDate.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE);

                BrgyRental rental = new BrgyRental(name, type, date);

                tableModel.addRow(new Object[]{
                        rental.getRentalId(),
                        rental.getRenterName(),
                        rental.getRentalType(),
                        rental.getRentalDate().toString(),
                        String.format("%.2f", rental.getFee())

                });
                clearForm(txtRenterName, txtRentalDate);
                JOptionPane.showMessageDialog(panel, "Rental Added Successfully");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnUpdate.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(panel, "Please select a rental record to update", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String id = tableModel.getValueAt(selectedRow, 0).toString();
                String name = txtRenterName.getText().trim();
                String type =(String) cmbRentalType.getSelectedItem();
                LocalDate date = LocalDate.parse(txtRentalDate.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE);

                BrgyRental rental = new BrgyRental(id, name, type, date);

                tableModel.setValueAt(rental.getRenterName(), selectedRow, 1);
                tableModel.setValueAt(rental.getRentalType(), selectedRow, 2);
                tableModel.setValueAt(rental.getRentalDate(), selectedRow, 3);
                tableModel.setValueAt(String.format("%2f", rental.getFee()), selectedRow, 4);

                JOptionPane.showMessageDialog(panel, "Rental Updated Successfully!");


            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(ex);
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(panel, "Please select a rental record to delete", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm= JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete this record?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if(confirm== JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                clearForm(txtRenterName, txtRentalDate);
                JOptionPane.showMessageDialog(panel, "Record Deleted");

            }
        });

        btnClear.addActionListener(e -> {
            table.clearSelection();
            clearForm(txtRenterName, txtRentalDate);
        });


        return panel;


    }

    // CLEARANCE TAB
    private JPanel createClearanceContent() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(245, 247, 250));

        // HARDCODED DATA
        String[] columnNames = {"ID", "Full Name", "Address", "Contact Number" ,"Date", "Fee"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // POPULATE HARDCODED DATA WHEN OPENED
        tableModel.addRow(new Object[]
                {"RNT-0001", "Bogart Dimagiba", "Malolos, Bulacan", "09123456789", LocalDate.now().plusDays(2).toString(), "50.00", "Education"});


        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // INPUT FORM PANEL
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtFullName = new JTextField(12);
        JTextField txtAddress = new JTextField(12);
        JTextField txtRequestDate = new JTextField(LocalDate.now().plusDays(1).toString(), 1);
        JTextField txtContactNumber = new JTextField(12);
        JComboBox<String> cmbPurpose = new JComboBox<>(new String[] {"Education", "Business", "Employment"});

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Full Name: "), gbc);
        gbc.gridx = 1;
        formPanel.add(txtFullName, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Address: "), gbc);
        gbc.gridx = 3;
        formPanel.add(txtAddress, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Contact Number: "), gbc);
        gbc.gridx = 3;
        formPanel.add(txtContactNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Date (YYYY-MM-DD): "), gbc);
        gbc.gridx = 1;
        formPanel.add(txtRequestDate, gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Purpose: "), gbc);
        gbc.gridx = 5;
        formPanel.add(cmbPurpose, gbc);



        // ACTION BUTTONS
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);
        JButton btnAdd = new JButton("Add new");
        JButton btnUpdate = new JButton("Update Selected");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear Form");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;

        formPanel.add(buttonPanel, gbc);
        panel.add(formPanel, BorderLayout.SOUTH);

        // LISTENERS
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                txtFullName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtAddress.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtContactNumber.setText(tableModel.getValueAt(selectedRow, 3).toString());
                txtRequestDate.setText(tableModel.getValueAt(selectedRow, 4).toString());
                cmbPurpose.setSelectedItem(tableModel.getValueAt(selectedRow, 5).toString());

            }
        });

        //CREATE ACTION
        btnAdd.addActionListener(e -> {
            try {
                String name = txtFullName.getText().trim();
                String address = txtAddress.getText().trim();
                String contactNumber = txtContactNumber.getText().trim();
                LocalDate date = LocalDate.parse(txtRequestDate.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE);
                String purpose = (String) cmbPurpose.getSelectedItem();

                Resident resident = new Resident(name, address, contactNumber);
                Clearance clearance = new Clearance(resident, purpose);

                tableModel.addRow(new Object[]{
                        clearance.getRequestId(),
                        clearance.getResidentName(),
                        clearance.getResident().getAddress(),
                        clearance.getResident().getContactNumber(),
                        clearance.getDateRequested().toString(),
                        String.format("%.2f", clearance.getFee())

                });
                clearForm(txtFullName, txtRequestDate );
                JOptionPane.showMessageDialog(panel, "Request Added Successfully");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnUpdate.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(panel, "Please select a Clearance record to update", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String id = tableModel.getValueAt(selectedRow, 0).toString();
                String name = txtFullName.getText().trim();
                String address = txtAddress.getText().trim();
                String contactNumber = txtContactNumber.getText().trim();
                LocalDate date = LocalDate.parse(txtRequestDate.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE);
                String purpose =(String) cmbPurpose.getSelectedItem();

                Resident resident = new Resident(name, address, contactNumber);
                Clearance clearance = new Clearance(resident, purpose);

                tableModel.setValueAt(clearance.getResidentName(), selectedRow, 1);
                tableModel.setValueAt(clearance.getResident().getAddress(), selectedRow, 2);
                tableModel.setValueAt(clearance.getResident().getContactNumber(), selectedRow, 3);
                tableModel.setValueAt(String.format("%2f", clearance.getFee()), selectedRow, 4);

                JOptionPane.showMessageDialog(panel, "Clearance Document Updated Successfully!");


            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(ex);
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(panel, "Please select a Clearance record to delete", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm= JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete this record?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if(confirm== JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                clearForm(txtFullName, txtRequestDate);
                JOptionPane.showMessageDialog(panel, "Record Deleted");

            }
        });

        btnClear.addActionListener(e -> {
            table.clearSelection();
            clearForm(txtFullName, txtRequestDate);
        });


        return panel;


    }
    private void clearForm(JTextField nameField, JTextField dateField) {
        nameField.setText("");
        dateField.setText((LocalDate.now().plusDays(1).toString()));
    }

}
