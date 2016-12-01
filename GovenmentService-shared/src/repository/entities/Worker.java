/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.entities;

import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author YuanZhan
 */
@Entity
@DiscriminatorValue(value="W")
@PrimaryKeyJoinColumn(name="user_id")
public class Worker extends AllUser{

//    @ManyToMany(mappedBy = "linkedWorker")
//    private List<ServiceUse> serviceUses;


    public Worker() {
    }

    public Worker(int userId, String lastName, 
            String firstName, String email, String password, 
            String address, String phoneNumber) {
        super(userId, lastName, firstName, email, password, address, phoneNumber);
    }

//    public List<ServiceUse> getServiceUses() {
//        return serviceUses;
//    }
//
//    public void setServiceUses(List<ServiceUse> serviceUses) {
//        this.serviceUses = serviceUses;
//    }
}
