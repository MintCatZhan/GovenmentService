/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package govenmentserviceapp.gui;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JTable;
import repository.entities.Service;

/**
 *
 * @author YuanZhan
 */
public interface GovenAppGUI {

    /**
     * Clear all the TextField in the GUI
     */
    void clearTextFields();

    void displayMessageInDialog(String message);

    void displayServiceDetails(Service service);
    
    void displaySelectedServiceDetails(Service service);
    
    void displayServiceDetails(List<Service> services);
    
    int getSelectedServiceNumber() throws Exception;
    
    //JButton getAddButton();

    JButton getCloseButton();
    
    JButton getSearchNumberButton();
    
    JButton getSearchNameButton();
    
    JButton getSearchTypeButton();

    JButton getViewButton(); 
    
    public JTable getServiceTable();

    Service getServiceDetails();

    int getServiceNumber();
    
    String getServiceName();
    
    String getServiceType();
    
    boolean isServiceSelected();
}