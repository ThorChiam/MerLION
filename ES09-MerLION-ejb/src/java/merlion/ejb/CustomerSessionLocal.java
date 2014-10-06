/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package merlion.ejb;

import javax.ejb.Local;
import java.util.List;
import merlion.entity.Customer;


/**
 *
 * @author Tomato
 */
@Local
public interface CustomerSessionLocal {
    
    public List<Customer> getAllcustomer();
    public Customer getCustomer(Long id);
    public String addCustomer(String name, String email, String phone, String address, String cityRegion);
    
}

