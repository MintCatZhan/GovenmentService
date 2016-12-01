/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;
import javax.ejb.Remote;
import repository.entities.PublicUser;
import repository.entities.Service;
import repository.entities.ServiceUse;
import repository.entities.Worker;

/**
 *
 * @author YuanZhan
 */
@Remote
public interface ServiceRepository {
    public Worker searchWorkerById(int id) throws Exception;
    
    //hd part specification
    //stupid ways to search -->
    public PublicUser searchPublicById(int id) throws Exception;
    public List<PublicUser> searchPublicByFName(String fname) throws Exception;
    public List<PublicUser> searchPublicByLName(String lname) throws Exception;
    public List<PublicUser> searchPublicByEMail(String email) throws Exception;
    
    public void deletePublicUser(int userId) throws Exception;
    
    public List<PublicUser> getAllPublicUsers() throws Exception;
    public void updatePublicUser(PublicUser tempPublic);
    
    
    
    
    public Service searchServiceByNumber(int number) throws Exception;
    public List<Service> searchServiceByName(String name) throws Exception;
    public List<Service> searchServiceByType(String type) throws Exception;
    public List<Service> searchServiceByDescription(String description) throws Exception;
    
    public void addService(Service service) throws Exception;
    
    public void updateService(Service service) throws Exception;
    
    public List<Service> getAllServices() throws Exception;
    public List<ServiceUse> getAllServicesUse() throws Exception;
    
    public void deleteService(int serviceNumber) throws Exception;
    
    
    //provided withe user id and service number, the record of using a 
    //certain service will be saved
    public boolean useService(int userId, int serviceNumber) throws Exception;
    
    public void addPublicUser(PublicUser publicUser) throws Exception;
    public void addWorker(Worker worker) throws Exception;
    
    public void setWorkerToService(Worker worker, Service service) throws Exception;

//    /*
//    Search for a member of Public in the system by the following combination of criteria:
//    */
//    public List<PublicUser> searchPublic(int userId, String lname, String fname, String email) throws Exception;
//    
//    /*
//    Search for a service in the system by the following combination of criteria:
//    */
//    public List<Service> searchService(int serviceNo, String serviceName, String serviceType, String description) throws Exception;
//    

    public List<PublicUser> searchPublic(int userId, String firstName, String lastName, String email) throws Exception;

    /**
     * Search a service by the combination of service no, service name, type and description
     * 
     * 
     * @param serviceNo
     * @param serviceName
     * @param type
     * @param description
     * @return
     * @throws Exception 
     */
    public List<Service> searchService(int serviceNo, String serviceName, String type, String description) throws Exception;
}
