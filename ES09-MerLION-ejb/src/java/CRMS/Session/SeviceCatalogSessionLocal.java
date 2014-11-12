/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CRMS.Entity.Company;
import WMS.Entity.WMSServiceCatalog;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ThorChiam
 */
@Local
public interface SeviceCatalogSessionLocal {

    public List<WMSServiceCatalog> getAllServices();

    public List<Company> getCompanies();

    public List<String> getAllServiceTypes();

    public List<String> getAllServiceLocations(Long serviceId);

    public void selectProvider(String content, Company company);
}
