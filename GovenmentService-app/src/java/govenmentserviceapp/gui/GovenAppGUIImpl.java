/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package govenmentserviceapp.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import repository.entities.Service;

/**
 *
 * @author YuanZhan
 */

public class GovenAppGUIImpl extends JFrame implements GovenAppGUI {
    
    private static final String[] TABLE_COLUMNS = {"Number", "Name"};

    private final JButton closeButton;
    private final JButton viewButton;
    private final JButton searchNumberButton;
    private final JButton searchNameButton;
    private final JButton searchTypeButton;
    
    private final JPanel inputPanel;
    private final JPanel buttonPanel;

    private final JLabel serviceNumberLabel;
    private final JLabel serviceNameLabel;
    private final JLabel serviceTypeLabel;
    private final JLabel thumbnailLabel;
    private final JLabel descriptionLabel;

    private final JTextField serviceNumberTextField;
    private final JTextField serviceNameTextField;
    private final JTextField serviceTypeTextField;
    private final JTextField thumbnailTextField;
    private final JTextField descriptionTextField;
    
    private final JTable serviceTable;

    Service service;

    public GovenAppGUIImpl(ActionListener actionListener, ListSelectionListener listSelectionListener) {
        super("Australia Government Department Service");

        // create buttons
        this.closeButton = new JButton("Close");
        this.viewButton = new JButton("View");
        this.searchNumberButton = new JButton("Search By Number");
        this.searchNameButton = new JButton("Search By Name");
        this.searchTypeButton = new JButton("Search By Type");
        
        Container container = this.getContentPane();

        // create labels
        this.serviceNumberLabel = new JLabel("Service Number:");
        this.serviceNameLabel = new JLabel("Service Name:");
        this.serviceTypeLabel = new JLabel("Service Type:");
        this.thumbnailLabel = new JLabel("Thumbnail:");
        this.descriptionLabel = new JLabel("Description:");

        // create text fields
        this.serviceNumberTextField= new JTextField();
        this.serviceNameTextField = new JTextField();
        this.serviceTypeTextField = new JTextField();
        this.thumbnailTextField = new JTextField();
        this.descriptionTextField = new JTextField();
        
        // create table
        this.serviceTable = new JTable(new DefaultTableModel(TABLE_COLUMNS, 0));
        this.serviceTable.getSelectionModel().addListSelectionListener(listSelectionListener);       
        this.serviceTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        TableColumnModel serviceTableColumnModel = this.serviceTable.getColumnModel();
        serviceTableColumnModel.getColumn(0).setPreferredWidth(100);
        serviceTableColumnModel.getColumn(1).setPreferredWidth(500);

        
        // create panels
        this.inputPanel = new JPanel();
        this.buttonPanel = new JPanel();

        // set layout manager
        container.setLayout(new BorderLayout());
        this.inputPanel.setLayout(new GridLayout(5,2));
        this.buttonPanel.setLayout(new GridLayout(1,4));

        // add action listeners
        this.closeButton.addActionListener(actionListener);
        this.viewButton.addActionListener(actionListener);
        this.searchNumberButton.addActionListener(actionListener);
        this.searchNameButton.addActionListener(actionListener);
        this.searchTypeButton.addActionListener(actionListener);
        
        this.inputPanel.add(serviceNumberLabel);
        this.inputPanel.add(serviceNumberTextField);
        this.inputPanel.add(serviceNameLabel);
        this.inputPanel.add(serviceNameTextField);
        this.inputPanel.add(serviceTypeLabel);
        this.inputPanel.add(serviceTypeTextField);
        this.inputPanel.add(thumbnailLabel);
        this.inputPanel.add(thumbnailTextField);
        this.inputPanel.add(descriptionLabel);
        this.inputPanel.add(descriptionTextField);

        
        this.buttonPanel.add(this.searchNumberButton);
        this.buttonPanel.add(this.searchNameButton);
        this.buttonPanel.add(this.searchTypeButton);
        this.buttonPanel.add(this.viewButton);
        this.buttonPanel.add(this.closeButton);
        
        container.add(inputPanel,BorderLayout.NORTH);
        container.add(new JScrollPane(this.serviceTable), BorderLayout.CENTER);
        container.add(buttonPanel,BorderLayout.SOUTH);
       
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setSize(650, 570);       
        this.setVisible(true);
    }
    
    @Override
    public int getServiceNumber() {
        String number = this.serviceNumberTextField.getText();
        return number == null || number.isEmpty() ? 0 : Integer.parseInt(number);
    }
    
    @Override
    public String getServiceName() {
        String name = this.serviceNameTextField.getText();
        return name;
    }
    
    @Override
    public String getServiceType() {
        String type = this.serviceTypeTextField.getText();
        return type;
    }
    
    @Override
    public boolean isServiceSelected() {
        return (this.serviceTable.getSelectedRow() >= 0);
    }
    
    @Override
    public int getSelectedServiceNumber() {
        int selectedRowIndex = this.serviceTable.getSelectedRow();
        
        String serviceNumber = this.serviceTable.getValueAt(selectedRowIndex, 0).toString();
        return Integer.parseInt(serviceNumber); 
    }

    @Override
    public Service getServiceDetails()
    {
        return new Service(Integer.parseInt(serviceNumberTextField.getText()), serviceNameTextField.getText(), serviceTypeTextField.getText(), thumbnailTextField.getText(), descriptionTextField.getText());		
    }
    
    @Override
    public void displayMessageInDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void displayServiceDetails(Service service) {  
        this.clearServiceTable();
        ((DefaultTableModel)this.serviceTable.getModel()).addRow(new Object[]{service.getServiceNumber(), service.getServiceName(), service.getServiceType(), service.getThumbnail(), service.getDescription()});
    }
    
    @Override
    public void displayServiceDetails(List<Service> services) {    
        this.clearServiceTable();
        
        for (Service service : services) {
            ((DefaultTableModel)this.serviceTable.getModel()).addRow(new Object[]{service.getServiceNumber(), service.getServiceName(), service.getServiceType(), service.getThumbnail(), service.getDescription()});
        }
    }

    @Override
    public void displaySelectedServiceDetails(Service service) {
        this.serviceNumberTextField.setText(String.valueOf(service.getServiceNumber()));
        this.serviceNameTextField.setText(service.getServiceName());
        this.serviceTypeTextField.setText(service.getServiceType());
        this.thumbnailTextField.setText(service.getThumbnail()); 
        this.descriptionTextField.setText(service.getDescription());
    }
    
    private void clearServiceTable() {     
        int numberOfRow = this.serviceTable.getModel().getRowCount();
        
        if (numberOfRow > 0) {
            DefaultTableModel tableModel = (DefaultTableModel) this.serviceTable.getModel();
            for (int index = (numberOfRow - 1); index >= 0; index --) {
                tableModel.removeRow(index);
            }            
        }
    }

    @Override
    public void clearTextFields()
    {
        serviceNumberTextField.setText("");
        serviceNameTextField.setText("");
        serviceTypeTextField.setText("");
        thumbnailTextField.setText("");
        descriptionTextField.setText("");
    }

    @Override
    public JTable getServiceTable() {
        return serviceTable;
    }

    @Override
    public JButton getViewButton() {
        return viewButton;
    }

    @Override
    public JButton getSearchNumberButton() {
        return searchNumberButton;
    }
    
    @Override
    public JButton getSearchNameButton() {
        return searchNameButton;
    }
    
    @Override
    public JButton getSearchTypeButton() {
        return searchTypeButton;
    }

    @Override
    public JButton getCloseButton() {
        return closeButton;
    }
}

