/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import repository.entities.PublicUser;
import repository.entities.Service;
import repository.entities.ServiceUse;
import repository.entities.Worker;

/**
 *
 * @author YuanZhan
 */
@Stateless
public class ServiceRepositoryImpl implements ServiceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Search a service by its unique number.
     *
     * @param serviceNumber
     * @return
     * @throws Exception
     */
    @Override
    public Service searchServiceByNumber(int serviceNumber) throws Exception {
        Service service = entityManager.find(Service.class, serviceNumber);
        return service;
    }

    /**
     * Search service by name
     *
     * @param serviceName
     * @return
     * @throws Exception
     */
    @Override
    public List<Service> searchServiceByName(String serviceName) throws Exception {
        Query query = entityManager.createNamedQuery(Service.FIND_NAME);
        query.setParameter("name", serviceName);
        if (query.getResultList().isEmpty()) {
            return null;
        } else {
            return query.getResultList();
        }
    }

    /**
     * Search services by type
     *
     * @param serviceType
     * @return
     * @throws Exception
     */
    @Override
    public List<Service> searchServiceByType(String serviceType) throws Exception {
        Query query = entityManager.createNamedQuery(Service.FIND_TYPE);
        query.setParameter("type", serviceType);
        if (query.getResultList().isEmpty()) {
            return null;
        } else {
            return query.getResultList();
        }
    }

    /**
     * Search the services by description
     *
     * @param description
     * @return
     * @throws Exception
     */
    @Override
    public List<Service> searchServiceByDescription(String description) throws Exception {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Service> query = cb.createQuery(Service.class);
        Root<Service> pu = query.from(Service.class);
        query.select(pu).where(cb.equal(pu.get("description"), description));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Add a service to database
     *
     * @param service
     * @throws Exception
     */
    @Override
    public void addService(Service service) throws Exception {
        entityManager.persist(service);
    }

    /**
     * Modify a service's detail.
     *
     * @param service
     * @throws Exception
     */
    @Override
    public void updateService(Service service) throws Exception {
        entityManager.merge(service);
    }

    /**
     * Get all services from the database.
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<Service> getAllServices() throws Exception {
        return entityManager.createNamedQuery(Service.FIND_ALL).getResultList();
    }

    /**
     * Get all service use record from the database.
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<ServiceUse> getAllServicesUse() throws Exception {
        return entityManager.createNamedQuery(ServiceUse.FIND_ALL_SU).getResultList();
    }

    /**
     * Delete a service from database by its number.
     *
     * @param serviceNumber
     * @throws Exception
     */
    @Override
    public void deleteService(int serviceNumber) throws Exception {
        Service service = this.searchServiceByNumber(serviceNumber);

        if (service != null) {
            entityManager.remove(service);
        }
    }

    /**
     * Delete a public user from database by his/her id.
     *
     * @param userId
     * @throws Exception
     */
    @Override
    public void deletePublicUser(int userId) throws Exception {
        PublicUser pu = this.searchPublicById(userId);

        if (pu != null) {
            entityManager.remove(pu);
        }
    }

//    @Override
//    public void useService(int userId, int serviceNumber) throws Exception {
//        //get current time and date
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = new Date();
//        //date
//        String currentDate = dateFormat.format(date);
//
//        //通过service编号,得到涉及的具体service
//        Service serviceUsed = entityManager.find(Service.class, serviceNumber);
//
//        //通过userid 得到当前user
//        PublicUser currUser = entityManager.find(PublicUser.class, userId);
////
////        //得到当前user所使用的service list, 如果为空,那么往新建的list里面添加当前service; 否则,得到旧有的list,并往里添加
////        List<Service> currUserUsingServices = new ArrayList<>();
////        if (currUser.getServiceUsedByThisPublic() != null) {
////            currUserUsingServices = currUser.getServiceUsedByThisPublic();
////            currUserUsingServices.add(serviceUsed);
////        } else {
////            currUserUsingServices.add(serviceUsed);
////        }
////
////        //得到每一个当前public所用的service,并得到其相应的worker,然后添加到对应的worker list中,用于生成serviceuse
////        List<Worker> linkedWorkers = new ArrayList<>();
////        if (serviceUsed.getWorker() == null) {
//////用于最开始的实验,如果这个service没有worker,就给一个new的worker给他
////            serviceUsed.setWorker(new Worker());
////        }
////
////        for (int i = 0; i < currUserUsingServices.size(); i++) {
////            Worker relatedWorker = currUserUsingServices.get(i).getWorker();
////            linkedWorkers.add(relatedWorker);
////        }
////
////        ServiceUse application = new ServiceUse(currentDate, currUser, currUserUsingServices);
////        entityManager.persist(application);
////
//////        //get worker who need to manage this service
//////        if(serviceUsed.getWorker() != null){
//////            ServiceUse application = new ServiceUse(currentDate, currUser, currUserUsingServices, serviceUsed.getWorker());
//////            entityManager.persist(application);
//////        }else{
//////            Worker fakeWorker =new Worker();
//////            fakeWorker.setUserId(888);
//////            ServiceUse application = new ServiceUse(currentDate, currUser, currUserUsingServices, fakeWorker);
//////            entityManager.persist(application);
//////        }
//    }
    /**
     * Add a public user to database.
     *
     * @param publicUser
     * @throws Exception
     */
    @Override
    public void addPublicUser(PublicUser publicUser) throws Exception {
        entityManager.persist(publicUser);
    }

    /**
     * Add a worker to database
     *
     * @param worker
     * @throws Exception
     */
    @Override
    public void addWorker(Worker worker) throws Exception {
        entityManager.persist(worker);
    }

    /**
     * Set a worker for a certain service, this method will be executed during
     * the creation of a service
     *
     * @param worker
     * @param service
     * @throws Exception
     */
    @Override
    public void setWorkerToService(Worker worker, Service service) throws Exception {
        service.setWorker(worker);
    }

    /**
     * A public user use a service, related info. will be saved to database,
     * including the date, the userId, the servicesNo
     *
     * @param userId
     * @param serviceNumber
     * @return
     * @throws Exception
     */
    @Override
    public boolean useService(int userId, int serviceNumber) throws Exception {
        //get current time and date
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String currentDate = dateFormat.format(date);

        //通过service编号,得到涉及的具体service
        Service serviceUsed = entityManager.find(Service.class, serviceNumber);
        //通过userid 得到当前user
        PublicUser currUser = entityManager.find(PublicUser.class, userId);

//        //得到被该用户所使用的service的list,并将现在这个service添加进去
//        currUser.getServiceUse().getServiceUsed().add(serviceUsed);
//得到被该用户所使用的service的list,并将现在这个service添加进去
//        List<Worker> linkedWorkers = new ArrayList<>();
//        if (currUser.getUsingServices() == null) {
//            List<Service> usingServicesList = new ArrayList<>();
//            usingServicesList.add(serviceUsed);
//            currUser.setUsingServices(usingServicesList);
//        } else {
//            currUser.getUsingServices().add(serviceUsed);
//        }
//
//        for (int i = 0; i < currUser.getUsingServices().size(); i++) {
//            Worker tempWorker = currUser.getUsingServices().get(i).getWorker();
//            linkedWorkers.add(tempWorker);
//        }
//        //得到被该用户用的services相关联的workers的list,然后将现在这个新的service相关联的worker添加进去
//        currUser.getServiceUse().getLinkedWorker().add(serviceUsed.getWorker());
//        currUser.getServiceUse().setDate(currentDate);
//        //ServiceUse(date, PublicUser linkedUserId, List<Service> serviceUsed, List<Worker> linkedWorker)
//        //ServiceUse tempSU = new ServiceUse(currentDate, currUser, );
        //如果该用户之前还没有serviceuse的话,还是需要在每一次use的时候新建一个serviceuse 
        //ServiceUse(String date, List<Worker> linkedWorker, PublicUser linkedUserId, List<Service> serviceUsed)
        //if (currUser.getServiceUse() == null) {
//        ServiceUse tempServiceUse = new ServiceUse();
//        entityManager.persist(tempServiceUse);
//        tempServiceUse.setDate(currentDate);
//        tempServiceUse.setLinkedUserId(currUser);
//        tempServiceUse.setLinkedWorker(linkedWorkers);
//        tempServiceUse.setServiceUsed(currUser.getUsingServices());
//        entityManager.persist(tempServiceUse);
        //int currServiceUseId = 0;
//        ServiceUse serviceUse = new ServiceUse(currentDate, linkedWorkers, currUser, currUser.getUsingServices());
        if (serviceUsed.getAvailability().equals("yes")) {
            ServiceUse serviceUse = new ServiceUse(currentDate, currUser, serviceUsed);
//        serviceUse.setAllServiceNo();
//        serviceUse.setAllWorkerId();
            entityManager.persist(serviceUse);
            return true;
        }

//        } else {
//            currUser.getServiceUse().setDate(currentDate);
//            currUser.getServiceUse().setLinkedUserId(currUser);
//            currUser.getServiceUse().setServiceUsed(currUser.getUsingServices());
//            currUser.getServiceUse().setLinkedWorker(linkedWorkers);
//            entityManager.merge(currUser.getServiceUse());
//        }
        return false;
    }

    /**
     * Search a worker by his/her unique id
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Worker searchWorkerById(int id) throws Exception {
        Worker worker = entityManager.find(Worker.class, id);
        return worker;
    }

    /**
     * Get all public users from database.
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<PublicUser> getAllPublicUsers() throws Exception {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(PublicUser.class);
        Root<PublicUser> cp = query.from(PublicUser.class);
        query.select(cp);
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Modify a public user's details.
     *
     * @param tempPublic
     */
    @Override
    public void updatePublicUser(PublicUser tempPublic) {
        entityManager.merge(tempPublic);
    }

    /**
     * Search a public user by his/her unique id.
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public PublicUser searchPublicById(int id) throws Exception {
        return this.entityManager.find(PublicUser.class, id);
    }

    /**
     * Search public users by their first name.
     *
     * @param fname
     * @return
     * @throws Exception
     */
    @Override
    public List<PublicUser> searchPublicByFName(String fname) throws Exception {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PublicUser> query = cb.createQuery(PublicUser.class);
        Root<PublicUser> pu = query.from(PublicUser.class);
        query.select(pu).where(cb.equal(pu.get("firstName"), fname));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Search public users by their last name.
     *
     * @param lname
     * @return
     * @throws Exception
     */
    @Override
    public List<PublicUser> searchPublicByLName(String lname) throws Exception {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PublicUser> query = cb.createQuery(PublicUser.class);
        Root<PublicUser> pu = query.from(PublicUser.class);
        query.select(pu).where(cb.equal(pu.get("lastName"), lname));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Search public users by their email address.
     *
     * @param email
     * @return
     * @throws Exception
     */
    @Override
    public List<PublicUser> searchPublicByEMail(String email) throws Exception {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PublicUser> query = cb.createQuery(PublicUser.class);
        Root<PublicUser> pu = query.from(PublicUser.class);
        query.select(pu).where(cb.equal(pu.get("email"), email));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * search by searchCriteria and searchContent, using criteria API
     *
     * @param userId
     * @param firstName
     * @param lastName
     * @param email
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public List<PublicUser> searchPublic(int userId, String firstName, String lastName, String email) throws Exception {

        if (userId > 0) {
            List<PublicUser> resultPU = new ArrayList<>();
            resultPU.add(this.searchPublicById(userId));
            return resultPU;
        } else {
            Query query = entityManager.createNamedQuery(PublicUser.FIND_BLURRED);
            query.setParameter("firstName", "%" + firstName + "%");
            query.setParameter("lastName", "%" + lastName + "%");
            query.setParameter("email", "%" + email + "%");
            if (query.getResultList().isEmpty()) {
                return new ArrayList<>();
            } else {
                return query.getResultList();
            }
        }
    }

    /**
     * Implementation, search service by combination of serviceNo, service Name,
     * type and description
     *
     * @param serviceNo
     * @param serviceName
     * @param type
     * @param description
     * @return
     * @throws Exception
     */
    @Override
    public List<Service> searchService(int serviceNo, String serviceName, String type, String description) throws Exception {
        if (serviceNo > 0) {
            List<Service> resultService = new ArrayList<>();
            resultService.add(this.searchServiceByNumber(serviceNo));
            return resultService;
        } else {
            Query query1 = entityManager.createNamedQuery(Service.FIND_BLURRED);
            query1.setParameter("serviceName", "%" + serviceName + "%");
            query1.setParameter("serviceType", "%" + type + "%");
            query1.setParameter("description", "%" + description + "%");
            if (query1.getResultList().isEmpty()) {
                return new ArrayList<>();
            } else {
                return query1.getResultList();
            }
        }
    }
}
