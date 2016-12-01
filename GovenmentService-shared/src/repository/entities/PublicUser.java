/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.entities;

import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author YuanZhan
 */
@Entity
@DiscriminatorValue(value="P")
@PrimaryKeyJoinColumn(name="user_id")
@NamedQuery(name = PublicUser.FIND_BLURRED, query = "SELECT pu FROM PublicUser pu"
        + " WHERE pu.firstName LIKE :firstName "
        + "AND pu.lastName LIKE :lastName "
        + "AND pu.email LIKE :email")
public class PublicUser extends AllUser {
    public static final String FIND_BLURRED = "PublicUser.Find_Blurred";
    
    @OneToMany
    private List<Service> usingServices;
    
    @OneToOne(mappedBy = "linkedUserId")
    private ServiceUse serviceUse;
    
//    private String allUsingServices;

    public PublicUser() {
    }

    public PublicUser(int userId, String lastName, String firstName, String email, String password, String address, String phoneNumber) {
        super(userId, lastName, firstName, email, password, address, phoneNumber);
    }

    public List<Service> getUsingServices() {
        return usingServices;
    }

    public void setUsingServices(List<Service> usingServices) {
        this.usingServices = usingServices;
    }

    public ServiceUse getServiceUse() {
        return serviceUse;
    }

    public void setServiceUse(ServiceUse serviceUse) {
        this.serviceUse = serviceUse;
    }
//    
//    public String getAllUsingServices(){
//        if(this.usingServices != null && !this.usingServices.isEmpty()){
//            String output = "";
//            for(Service s:this.usingServices){
//                output = output + " " + s.getServiceNumber();
//            }
//            return output;
//        }else{
//            return "No record yet";
//        }
//    }
//
//    public void setAllUsingServices(String allUsingServices) {
//        this.allUsingServices = allUsingServices;
//    }
//    
}
