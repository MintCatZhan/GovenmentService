/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package govenmentserviceapp;

import govenmentserviceapp.gui.GovenAppGUI;
import govenmentserviceapp.gui.GovenAppGUIImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.ejb.EJB;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import repository.ServiceRepository;
import repository.entities.Service;

/**
 *
 * @author YuanZhan
 */
public class GovenService implements ActionListener, ListSelectionListener {
    
    
    @EJB
    private static ServiceRepository serviceRepository;
    
    private String name;
    private GovenAppGUI gui;

    public GovenService(String name) throws Exception {
        this.name = name;
    }

    public void initView() {
        this.gui = new GovenAppGUIImpl(this, this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == gui.getSearchNumberButton()) {
            this.searchServiceByNumber();
        } else if (event.getSource() == gui.getSearchNameButton()) {
            this.searchServiceByName();
        } else if (event.getSource() == gui.getSearchTypeButton()) {
            this.searchServiceByType();
        } else {
            System.exit(0);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        if ((event.getSource() == this.gui.getServiceTable().getSelectionModel())
            && (! event.getValueIsAdjusting()))
        {
            try
            {
                if (this.gui.isServiceSelected()) {
                    int serviceNumber = this.gui.getSelectedServiceNumber();
                
                    Service service = serviceRepository.searchServiceByNumber(serviceNumber);
                    this.gui.displaySelectedServiceDetails(service);
                }               
            }
            catch (Exception e)
            {
                gui.displayMessageInDialog(e.getMessage());
            }
        }
    }
    

    public void searchServiceByNumber() {
        
        int number = this.gui.getServiceNumber();
        
        try {
            
            Service service = serviceRepository.searchServiceByNumber(number);
            
            if (service != null) {
                this.gui.displayServiceDetails(service);
            } else {
                this.gui.displayMessageInDialog("Service not found");
            }  
        } catch (Exception ex) {
            this.gui.displayMessageInDialog("Failed to search service by No: " + ex.getMessage());
        } finally {
            this.gui.clearTextFields();
        }
    }
    
    public void searchServiceByName() {
        
        String name = this.gui.getServiceName();
        
        try {
            
            List<Service> services = serviceRepository.searchServiceByName(name);
            
            if (services != null) {
                this.gui.displayServiceDetails(services);
            } else {
                this.gui.displayMessageInDialog("Service not found");
            }  
        } catch (Exception ex) {
            this.gui.displayMessageInDialog("Failed to search service by Name: " + ex.getMessage());
        } finally {
            this.gui.clearTextFields();
        }
    }
    
    public void searchServiceByType() {
        
        String type = this.gui.getServiceType();
        
        try {
            
            List<Service> services = serviceRepository.searchServiceByType(type);
            
            if (services != null) {
                this.gui.displayServiceDetails(services);
            } else {
                this.gui.displayMessageInDialog("Service not found");
            }  
        } catch (Exception ex) {
            this.gui.displayMessageInDialog("Failed to search service by Type: " + ex.getMessage());
        } finally {
            this.gui.clearTextFields();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        try {
            final GovenService govenService = new GovenService("Government Service");
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    govenService.initView();
                }
            });
        } catch (Exception ex) {
            System.out.println("Failed to run application: " + ex.getMessage());
        }
    }

}
