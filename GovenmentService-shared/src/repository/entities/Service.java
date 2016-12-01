/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author YuanZhan
 */


@Entity
@NamedQueries({
    @NamedQuery(name = Service.FIND_ALL, query = "SELECT s FROM Service s"),
    @NamedQuery(name = Service.FIND_NAME, query = "SELECT s FROM Service s WHERE s.serviceName = :name"),
    @NamedQuery(name = Service.FIND_TYPE, query = "SELECT s FROM Service s WHERE s.serviceType = :type"),
    @NamedQuery(name = Service.FIND_BLURRED, query = "SELECT s FROM Service s "
            + "WHERE s.serviceName LIKE :serviceName"
            + " AND s.serviceType LIKE :serviceType"
            + " AND s.description LIKE :description")})
public class Service implements Serializable {

    //->declare and initialise a final attribute by tbe named query.
    public static final String FIND_ALL = "Service.findAll";
    public static final String FIND_NAME = "Service.findName";
    public static final String FIND_TYPE = "Service.findType";
    public static final String FIND_BLURRED = "Service.findBulrred";

    @Id
    @Column(name = "service_number")
    private int serviceNumber;
    @Column(name = "service_name")
    private String serviceName;
    @Column(name = "service_type")
    private String serviceType;
    @Column(name = "thumbnail")
    private String thumbnail;
    @Column(name = "description")
    private String description;
    @Column(name = "availability")
    private String availability;
    @ManyToOne
    @JoinTable(name = "service_worker",
            joinColumns = @JoinColumn(name = "managedby"),
            inverseJoinColumns = @JoinColumn(name = "worker"))
    private Worker worker;
//    @ManyToMany(mappedBy = "serviceUsed")
//    private List<ServiceUse> serviceUses;
    
    

    public Service() {
    }

    public Service(Service service) {
        this.serviceNumber = service.serviceNumber;
        this.serviceName = service.serviceName;
        this.serviceType = service.serviceType;
        this.thumbnail = service.thumbnail;
        this.description = service.description;
        this.availability = "yes";
    }

    public Service(int serviceNumber, String serviceName,
            String serviceType, String thumbnail, String description, Worker worker) {
        this.serviceNumber = serviceNumber;
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.thumbnail = thumbnail;
        this.description = description;
        this.worker = worker;
        this.availability = "yes";
    }
    
    public Service(int serviceNumber, String serviceName,
            String serviceType, String thumbnail, String description, 
            Worker worker, String availability) {
        this.serviceNumber = serviceNumber;
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.thumbnail = thumbnail;
        this.description = description;
        this.worker = worker;
        this.availability = availability;
    }

    public Service(int serviceNumber, String serviceName, String serviceType, String thumbnail, String description) {
        this.serviceNumber = serviceNumber;
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.thumbnail = thumbnail;
        this.description = description;
        this.availability = "yes";
    }


//    public List<ServiceUse> getServiceUses() {
//        return serviceUses;
//    }
//
//    public void setServiceUses(List<ServiceUse> serviceUses) {
//        this.serviceUses = serviceUses;
//    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
    
    public int getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(int serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
