/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb.local;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ThorChiam
 */
@Local
public interface SeviceCatalogSessionBeanLocal {

    //individual user can delete their own service catalog info

    public void deleteServiceCatalog(String email, Long serviceId);

    public void updateServiceCatalog(String email, Long serviceId, String serviceType, String carrierType, String route, long scheduleFrom, long scheduleTo, long price, long maxVol, long avaVol);

    public List getServiceCatalog(String email, Long serviceId);

    public List getAllServiceCatalog(String email);

    public Long addServiceCatalog(String email, String serviceType, String carrierType, String route, String availableScheduleFrom, String availableScheduleTo, long price, long maxVol, long availableVol);

}
