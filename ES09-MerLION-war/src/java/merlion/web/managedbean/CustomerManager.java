/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package merlion.web.managedbean;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import merlion_ejb.CustomerSessionLocal;
import merlion_ejb.entity.Customer;


/**
 *
 * @author Tomato
 */
public class CustomerManager {
    public CustomerSessionLocal cLocal;
    
    public CustomerManager(){
        
    }
    public CustomerManager(CustomerSessionLocal cLocal){
        this.cLocal=cLocal;
    }
    public List<Customer> getAllcustomer(){
        return cLocal.getAllcustomer();
    }
    public Customer getCustomer(Long id){
        return cLocal.getCustomer(id);
    }
    public String addCustomer(HttpServletRequest request){
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String address=request.getParameter("address");
        String cityRegion=request.getParameter("cityRegion");
        return cLocal.addCustomer(name, email, phone, address, cityRegion);
    }

}

