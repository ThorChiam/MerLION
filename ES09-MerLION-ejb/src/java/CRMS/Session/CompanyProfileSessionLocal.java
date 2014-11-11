/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CRMS.Entity.Company;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ThorChiam
 */
@Local
public interface CompanyProfileSessionLocal {

    public void addCompanyProfile(String companyName, String companyAddress, String tel, String email, String website, String companyHistory, String service, String vision);

    public List getAllCompanyProfile(String email);
    
    public Company getCompamyProfile(String email);

    //admin can delete company profile
    public String deleteCompanyProfile(Long companyId);
    //update company profile

    public void updateCompanyProfile(String cemail, String companyName, String companyAddress, String tel, String email, String website, String companyHistory, String service, String vision);

}
