package opencart.Repository.DataJPA;

import opencart.Model.Customer;
import opencart.Repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.Assert.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JPACustomerRepositoryImpl implements CustomerRepository {
    @PersistenceContext
    private EntityManager em;
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public Customer findByAccountNameAndPassword(String accountName, String password) {
//        TypedQuery<Customer> query = this.em.createQuery("SELECT c FROM Customer c " +
//                "WHERE c.accountName=:account", Customer.class);
//        query.setParameter("account", accountName);
//        passwordEncoder.matches(password, );
        Customer customer = findCustomerAccount(accountName);
        if(passwordEncoder.matches(password, customer.getPassword())){
            System.out.println("Find Customer Successfully");
            return customer;
        }
        return null;
//        return query.getSingleResult();
    }

    @Override
    public Customer findCustomerByName(String name) {
        TypedQuery<Customer> query = this.em.createQuery("SELECT c FROM Customer c " +
                "WHERE c.lastName=:name OR c.firstName=:name", Customer.class);
        query.setParameter("name", name);
        return query.getResultList().get(0);
    }

    @Override
    public Customer findCustomerByID(Integer ID) {
        TypedQuery<Customer> query = this.em.createQuery("SELECT c FROM Customer c " +
                "WHERE c.customerId=:id", Customer.class);
        query.setParameter("id", ID);
        return query.getSingleResult();
    }

    @Override
    public Customer findCustomerAccount(String accountName) {
        try {
            TypedQuery<Customer> q = em.createQuery("SELECT c FROM Customer c WHERE c.accountName = :accountName", Customer.class);
            q.setParameter("accountName", accountName);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Collection<Customer> findAllCustomer() {
        TypedQuery<Customer> query = this.em.createQuery("SELECT c FROM Customer c", Customer.class);
        return query.getResultList();
    }

    @Override
    public void addCustomer(Customer customer) {
        Query query = this.em.createNativeQuery("INSERT INTO customer " +
                "(firstName, lastName, email, phone, accountName, password, addressLine1, addressLine2, city, country) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        passwordEncoder = new BCryptPasswordEncoder(10);
        String password = passwordEncoder.encode(customer.getPassword());
        assertTrue(passwordEncoder.matches(customer.getPassword(), password));
        query.setParameter(1, customer.getFirstName());
        query.setParameter(2, customer.getLastName());
        query.setParameter(3, customer.getEmail());
        query.setParameter(4, customer.getPhoneNumber());
        query.setParameter(5, customer.getAccountName());
        query.setParameter(6, password);
        query.setParameter(7, customer.getAddressLine1());
        query.setParameter(8, customer.getAddressLine2());
        query.setParameter(9, customer.getCity());
        query.setParameter(10, customer.getCountry());
        query.executeUpdate();
    }

    @Override
    public void saveInfo(Customer customer) {
        Query query = this.em.createNativeQuery("UPDATE customer c " +
                "SET " +
                "c.accountName=:account, " +
                "c.addressLine1=:al1, " +
                "c.addressLine2=:al2, " +
                "c.city=:city, " +
                "c.country=:coun, " +
                "c.email=:email, " +
                "c.firstName=:fn, " +
                "c.lastName=:ln, " +
                "c.password=:pw, " +
                "c.phone=:phone " +
                "WHERE c.customerId = " + customer.getCustomerId());
        //query.setParameter("id", customer.getCustomerId());
        query.setParameter("account", customer.getAccountName());
        query.setParameter("al1", customer.getAddressLine1());
        query.setParameter("al2", customer.getAddressLine2());
        query.setParameter("city", customer.getCity());
        query.setParameter("coun", customer.getCountry());
        query.setParameter("email", customer.getEmail());
        query.setParameter("fn", customer.getFirstName());
        query.setParameter("ln", customer.getLastName());
        query.setParameter("pw", customer.getPassword());
        query.setParameter("phone", customer.getPhoneNumber());
        query.executeUpdate();
    }
}
