/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CRMS.Entity.Contract;
import CRMS.Entity.ServiceOrder;
import java.util.List;

/**
 *
 * @author sunny
 */
public interface ContractSessionLocal {
    public void createCrontract(String sign_date, String total_price, String contract_status, ServiceOrder serviceorder);
    public Contract getContract(long contract_id);
    public List<Contract> getAllContract(String email);
    public void deleteContract(long contract_id);
    public void terminateContract(long contract_id);   
    public ServiceOrder getServiceOrder(long service_id);
}
