/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbeans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import repository.ServiceRepository;
import repository.entities.PublicUser;
import repository.entities.Service;

/**
 *
 * @author YuanZhan
 */
@Named(value = "serviceManagedBean")
@SessionScoped
public class ServiceManagedBean implements Serializable {

    @EJB
    private ServiceRepository serviceRepository;

    private Service service;
    //current public user
    private PublicUser currUser;
    private List<Service> services;
    private boolean used;

    public ServiceManagedBean() {
        currUser = new PublicUser();
        service = new Service();
        services = new ArrayList<>();
        used = false;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
    
//    public String setCurrUserId(int id) {
//        this.currUser.setUserId(id);
//        return "publicPage";
//    }

//    public void getCurrUserId() {
//        this.currUser.getUserId();
//    }

    public PublicUser getCurrUser() {
        return currUser;
    }

//    public void setCurrUser(PublicUser currUser) {
//        this.currUser = currUser;
//    }
    
    public String setCurrUser(int id) throws Exception {
        this.currUser = this.serviceRepository.searchPublicById(id);
        return "publicPage";
    }

    public ServiceRepository getServiceRepository() {
        return serviceRepository;
    }

    public void setServiceRepository(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String searchByNumber() throws Exception {
        this.services = new ArrayList<>();
        if (serviceRepository.searchServiceByNumber(service.getServiceNumber()) != null) {
            this.services.add(serviceRepository.searchServiceByNumber(service.getServiceNumber()));
        }
        return "serviceList";
    }

    public String searchByName() throws Exception {
        this.services = serviceRepository.searchServiceByName(service.getServiceName());
        return "serviceList";
    }

    public String searchByType() throws Exception {
        this.services = serviceRepository.searchServiceByType(service.getServiceType());
        return "serviceList";
    }

    public String showDetails(int number) throws Exception {
        this.service = serviceRepository.searchServiceByNumber(number);
        return "serviceDetails";
    }

    public String getAllServices() throws Exception {
        this.services = serviceRepository.getAllServices();
        return "allServices";
    }
//    
//    public void removeService(int serviceNumber) throws Exception{
//        this.serviceRepository.deleteService(serviceNumber);
//    }
//    
////    private int serviceNumber;
////    private String serviceName;
////    private String serviceType;
////    private String thumbnail;
////    private String description;
//    public void addService(int serviceNumber, String serviceName, String serviceType, String thumbnail, String description) throws Exception{
//        Service tempService = new Service(serviceNumber, serviceName, serviceType, thumbnail, description);
//        this.serviceRepository.addService(tempService);
//    }
//    
//    public void updateService(int serviceNumber, String serviceName, String serviceType, String thumbnail, String description) throws Exception{
//        Service tempService = new Service(serviceNumber, serviceName, serviceType, thumbnail, description);
//        this.serviceRepository.updateService(tempService);
//    }

    public void useService(int userId, int serviceNo) throws Exception {
        if( this.serviceRepository.useService(userId, serviceNo)){
            this.used = true;
        }
    }
    
    
//    /**
//     * Search public User by combination of id, fname, lname and email
//     * @return 
//     * @throws java.lang.Exception
//     */
//    public String SearchByBlurred() throws Exception{
////        this.services = serviceRepository.searchServiceByType(service.getServiceType());
//        this.publicUsers = this.serviceRepository.searchPublic(""+ this.publicUser.getUserId(), 
//                this.publicUser.getFirstName(), 
//                this.publicUser.getLastName(), 
//                this.publicUser.getEmail());
//        return "publicUserList";
//    }
    
    
    /**
     * Search services from database by the combination of serviceNo, serviceName, serviceType and description
     * @return
     * @throws Exception 
     */
    public String searchServiceByBlurred() throws Exception{
        this.services = this.serviceRepository.searchService(this.service.getServiceNumber(), 
                this.service.getServiceName(), 
                this.service.getServiceType(), 
                this.service.getDescription());
        return "serviceList";
    }
}
