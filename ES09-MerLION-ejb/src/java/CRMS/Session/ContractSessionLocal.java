/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CRMS.Entity.Contract;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sunny
 */
@Local
public interface ContractSessionLocal {

    public void createCrontract(int requiredCapacity, Long serviceId, String email, String requestorId);

    public void terminate(String email, Long contractId);

    public void signed(String email, Long contractId);

    public Contract getContract(Long contract_id);

    public List<Contract> getAllContracts(String email);

}
