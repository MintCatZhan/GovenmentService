/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbeans;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.Part;
import repository.ServiceRepository;
import repository.entities.PublicUser;
import repository.entities.Service;
import repository.entities.ServiceUse;
import repository.entities.Worker;

/**
 *
 * @author YuanZhan
 */
@Named(value = "adminManagedBean")
@SessionScoped
public class AdminManagedBean implements Serializable {

    @EJB
    private ServiceRepository serviceRepository;

    //
    private PublicUser publicUser;
    ///
    private Worker worker;
    private int workerId;

    private Service service;
    private List<Service> services;
    ///
    private List<ServiceUse> serviceUses;
    ///hd part
    private List<PublicUser> publicUsers;
    /**
     * Service thumbnail
     */
    private Part thumbnail;

    public AdminManagedBean() {
        publicUser = new PublicUser();
        service = new Service();
        services = new ArrayList<>();
        worker = new Worker();
        serviceUses = new ArrayList<>();
        publicUsers = new ArrayList<>();
    }

    public Part getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Part thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<PublicUser> getPublicUsers() {
        return publicUsers;
    }

    public void setPublicUsers(List<PublicUser> publicUsers) {
        this.publicUsers = publicUsers;
    }

    public List<ServiceUse> getServiceUses() {
        return serviceUses;
    }

    public void setServiceUses(List<ServiceUse> serviceUses) {
        this.serviceUses = serviceUses;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public PublicUser getPublicUser() {
        return publicUser;
    }

    public void setPublicUser(PublicUser publicUser) {
        this.publicUser = publicUser;
    }

    public String updatePublicUser(PublicUser publicUser) {
        this.publicUser = publicUser;
        return "updatePublicUser";
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

    public String initial() {
        this.service = new Service();
        return "addService";
    }

    public String setService(Service service) {
        this.service = service;
        return "updateService";
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String showDetails(int number) throws Exception {
        this.service = serviceRepository.searchServiceByNumber(number);
        return "serviceDetailsforAdmin";
    }

    public String getAllServices() throws Exception {
        this.services = serviceRepository.getAllServices();
        return "allServices";
    }

    public String getAllServiceUses() throws Exception {
        this.serviceUses = this.serviceRepository.getAllServicesUse();
        return "allServicesUse";
    }

    public void removeService(int serviceNumber) throws Exception {
        this.serviceRepository.deleteService(serviceNumber);
    }

    public void addService(int serviceNumber, String serviceName, String serviceType, String description, int workerId) throws Exception {
        Service tempService = new Service(serviceNumber, serviceName, serviceType, "", description, this.serviceRepository.searchWorkerById(workerId));
        tempService.setThumbnail("" + serviceNumber);
        uploadThumbnail(serviceNumber + "");
        this.serviceRepository.addService(tempService);
    }

    public void addPublicUser(int userId, String lname, String fname, String email, String password, String address, String phoneNumber) throws Exception {
        //List<Service> serviceUsedByThisPublic, int userId,
        //String lastName, String firstName, String email, String password, 
        //String address, String phoneNumber
        PublicUser tempPublic = new PublicUser(userId, lname, fname, email, password, address, phoneNumber);
        this.serviceRepository.addPublicUser(tempPublic);
    }

    public void updatePublicUser(int userId, String lname, String fname, String email, String password, String address, String phoneNumber) throws Exception {
        PublicUser tempPublic = new PublicUser(userId, lname, fname, email, password, address, phoneNumber);
        this.serviceRepository.updatePublicUser(tempPublic);
    }

    public void updateService(int serviceNumber, String serviceName, String serviceType, String thumbnail, String description, int workerId, String availibility) throws Exception {
        if (availibility.equals("yes") || availibility.equals("no")) {
            Service tempService = new Service(serviceNumber, serviceName, serviceType, thumbnail, description, this.serviceRepository.searchWorkerById(workerId), availibility);
            this.serviceRepository.updateService(tempService);
        }
    }

    public void addWorker(int userId, String lname, String fname, String email, String password, String address, String phoneNumber) throws Exception {
        Worker tempWorker = new Worker(userId, lname, fname, email, password, address, phoneNumber);
        this.serviceRepository.addWorker(tempWorker);
    }

//    public void setWorkerToService(int serviceNo, int userId) throws Exception{
//        Service tempService = this.serviceRepository.searchServiceByNumber(serviceNo);
//        Worker tempWorker = this.serviceRepository
//        this.serviceRepository.setWorkerToService(worker, service);
//    }
    // get all service numbers in one certain serviceUse case
//    public String getServiceNumbers() throws Exception{
//        String allServiceNo = "";
//        for(ServiceUse su:this.serviceUses){
//            for(Service s:su.getServiceUsed()){
//                allServiceNo = allServiceNo + ", " + s.getServiceNumber();
//            }
//        }
//        return allServiceNo;
//    }
//    public String searchPublicByFName() throws Exception {
//        this.publicUsers = this.serviceRepository.searchPublicByFName(this.publicUser.getFirstName());
//        return "publicUserList";
//    }
    public String searchPublic() throws Exception {
        //need to be done next
        return "publicUserList";
    }

    public String showUserDetails(int id) throws Exception {
        this.publicUser = serviceRepository.searchPublicById(id);
        return "publicUserDetails";
    }

    public void removePublicUser(int userId) throws Exception {
        this.serviceRepository.deletePublicUser(userId);
    }

    public String getAllPublicUsers() throws Exception {
        this.publicUsers = this.serviceRepository.getAllPublicUsers();
        return "allPublicUsers";
    }

    public String getUsingServices(int userId) throws Exception {
        String output = "";
        if (this.serviceUses != null && !this.serviceUses.isEmpty()) {
            for (ServiceUse su : this.serviceUses) {
                if (su.getLinkedUserId().getUserId() == userId) {
                    output = output + " " + su.getServiceUsed().getServiceName();
                }
            }
            return output;
        } else {
            return "No record";
        }
    }

    /**
     * Upload the thumbnail if service to server
     *
     * @param serviceNo service id
     */
    public void uploadThumbnail(String serviceNo) {
        try {
            InputStream inputStream = thumbnail.getInputStream();
            FileOutputStream outputStream = new FileOutputStream("/Users/YuanZhan/Desktop/GovenmentService/GovenmentService/GovenmentService-war/web/resources/img/" + serviceNo);
            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            while (true) {
                bytesRead = inputStream.read(buffer);
                if (bytesRead > 0) {
                    outputStream.write(buffer, 0, bytesRead);
                } else {
                    break;
                }
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException ex) {
            System.out.println("Failed to upload thumbail" + serviceNo);
        }
    }

    /**
     * Search public User by combination of id, fname, lname and email
     *
     * @return
     * @throws java.lang.Exception
     */
    public String SearchByBlurred() throws Exception {
//        this.services = serviceRepository.searchServiceByType(service.getServiceType());
        this.publicUsers = this.serviceRepository.searchPublic(this.publicUser.getUserId(),
                this.publicUser.getFirstName(),
                this.publicUser.getLastName(),
                this.publicUser.getEmail());
        return "publicUserList";
    }
}
