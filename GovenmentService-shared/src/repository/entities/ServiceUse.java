/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author YuanZhan
 */
@Entity
@NamedQuery(name = ServiceUse.FIND_ALL_SU, query = "SELECT su FROM ServiceUse su")
@SequenceGenerator(name = "seq", allocationSize = 1, initialValue = 100)
//@TableGenerator(name = "su_worker_gntor", table="su_worker")
public class ServiceUse implements Serializable {

    public static final String FIND_ALL_SU = "ServiceUse.findAll";

    //, sequenceName = "oracle_seq"
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "service_use_id")
    private int serviceUseId;

    private String date;
    
//    private String allServiceNo;
//    private String allWorkerId;

    //@JoinColumn(name = "managedby", nullable = false)
    //@ManyToMany(cascade = CascadeType.ALL)
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "su_worker",
//            joinColumns = @JoinColumn(name = "su_fk"),
//            inverseJoinColumns = @JoinColumn(name = "juc_worker_fk"))
//    private List<Worker> linkedWorker;

    @OneToOne
    @JoinColumn(name = "created_by", nullable = false)
    private PublicUser linkedUserId;

//    @JoinColumn(name = "services", nullable = false)
//    @ManyToMany
//    @JoinTable(name = "su_service",
//            joinColumns = @JoinColumn(name = "su_fk"),
//            inverseJoinColumns = @JoinColumn(name = "juc_servicer_fk"))
//    private List<Service> serviceUsed;
    
    @OneToOne
    private Service serviceUsed;

    public ServiceUse() {
    }

    public ServiceUse(String date, PublicUser linkedUserId, Service serviceUsed) {
//, List<Service> serviceUsed
        this.date = date;
        //this.linkedWorker = linkedWorker;
//        this.allServiceNo = "";
//        this.allWorkerId = "";
        this.linkedUserId = linkedUserId;
        this.serviceUsed = serviceUsed;
//        this.serviceUsed = serviceUsed;
    }

    public Service getServiceUsed() {
        return serviceUsed;
    }

    public void setServiceUsed(Service serviceUsed) {
        this.serviceUsed = serviceUsed;
    }

    public int getServiceUseId() {
        return serviceUseId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PublicUser getLinkedUserId() {
        return linkedUserId;
    }

    public void setLinkedUserId(PublicUser linkedUserId) {
        this.linkedUserId = linkedUserId;
    }

//    public List<Service> getServiceUsed() {
//        return serviceUsed;
//    }
//
//    public void setServiceUsed(List<Service> serviceUsed) {
//        this.serviceUsed = serviceUsed;
//    }

//    public List<Worker> getLinkedWorker() {
//        return linkedWorker;
//    }

//    public void setLinkedWorker(List<Worker> linkedWorker) {
//        this.linkedWorker = linkedWorker;
//    }

//    public String getAllServiceNo(){
////        for (Service s:this.getServiceUsed()){
////            allServiceNo = allServiceNo + ", " + s.getServiceName();
////        }
//        return allServiceNo;
//    }
    
//    public void setAllServiceNo(){
//        for (Service s:this.getServiceUsed()){
//            this.allServiceNo = this.allServiceNo + " " + s.getServiceNumber();
//        }
//    }

//    public String getAllWorkerId() {
////        for(Service s:this.getServiceUsed()){
////            allWorkerId = allWorkerId + ", " + s.getWorker().getUserId();
////        }
//        return allWorkerId;
//    }

//    public void setAllWorkerId() {
//        for(Service s:this.getServiceUsed()){
//            this.allWorkerId = this.allWorkerId + " " + s.getWorker().getUserId();
//        }
//    }
    
}
