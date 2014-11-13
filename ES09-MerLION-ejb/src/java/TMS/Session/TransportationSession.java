/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TMS.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import CI.Entity.Account;
import TMS.Entity.Location;
import TMS.Entity.Route;

/**
 *
 * @author Zeng Xunhao
 */
@Stateless
@LocalBean
public class TransportationSession {

    static int dim;
    static int size;// dim is number of nodes in graph
    // size had been used to keep record of index to remove node from Arraylist
    static boolean[] color;      // to remember visit
    static int[][] graph;
    static ArrayList<Integer> al = new ArrayList<Integer>();
    static List<Location> locations = new ArrayList<Location>();
    static List<Location> path;
    static List<List<Location>> allpath = new ArrayList<List<Location>>();
    
    @PersistenceContext
    private EntityManager entityManager;
    HashMap hm = new HashMap();

    public Account getAccount(String userId) {
        Account account = entityManager.find(Account.class, userId);
        return account;
    }
    
    public Location getLocation(Long Id) {
        Location l = entityManager.find(Location.class, Id);
        return l;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public List<Route> getMyRoutes(String userId) {

        Query query = entityManager.createQuery("SELECT r FROM Route r where r.location1.account=:account");
        Account account = getAccount(userId);
        query.setParameter("account", account);

        return query.getResultList();
    }

    public List<Route> getAllRoutes(String userId) {

        Query query = entityManager.createQuery("SELECT r FROM Route r");

        return query.getResultList();
    }

    public List<Location> getMyLocations(String userId) {

        Query query = entityManager.createQuery("SELECT r FROM Location r where r.account=:account");
        Account account = getAccount(userId);
        query.setParameter("account", account);

        return query.getResultList();
    }
    public List<Location> getAllLocations() {

        Query query = entityManager.createQuery("SELECT r FROM Location r" );
        
        return query.getResultList();
    }

    public List<Account> getAllServiceProvider() {
        Query query = entityManager.createQuery("SELECT a FROM Account a");

        return query.getResultList();
    }
    public void deleteMyRoute(Long Id){
  
     
        Query query = entityManager.createQuery("Delete From Route r where r.id=:Id");
        query.setParameter("Id", Id);
        
        query.executeUpdate();
        
    }

    private void iniGraph(String userId) {
        List<Route> routes = getMyRoutes(userId);
        locations = getMyLocations(userId);
        dim = locations.size();
        graph = new int[dim+1][dim+1];
        color = new boolean[dim+1];

        Arrays.fill(color, false);

        for (Route i : routes) {
            //System.out.println(i.getLocation1().getName() + i.getLocation2().getName());
            //System.out.println(locations.indexOf(i.getLocation1()) + "" + locations.indexOf(i.getLocation2()));
            graph[locations.indexOf(i.getLocation1())+1][locations.indexOf(i.getLocation2())+1] = 1;
        }
    }
    public void addNewRoute( double price,double cost,double distance,Long ori,Long des ){
        Route r=new Route(); 
        r.setCost(cost);
        r.setDistance(distance);
        r.setPrice(price);
        
        r.setLocation1(getLocation(ori));
        r.setLocation2(getLocation(des));
        entityManager.persist(r);
        
       
    }

    public List<List<Location>> getMyPaths(String start, String end, String userId) {
        al = new ArrayList<Integer>();
        allpath = new ArrayList<List<Location>>();
        size=0;
        iniGraph(userId);

        for (Location i : locations) {
            hm.put(i.getName(), locations.indexOf(i));
        }
        int s = (int) hm.get(start);
        int t = (int) hm.get(end);

       // System.out.print("from location " + start + s + "  ");
        //System.out.println(end + t);
        if (s >= 0 && t >= 0) {
            dfs(s+1, t+1);
        }
                      
        return allpath;
        
    }
    

    static void dfs(int src, int dst) {

        al.add(src);
        size++;

        color[src] = true;
        if (src == dst) {       // tests for base condition to stop
            
             path = new ArrayList<Location>();
            for (Integer i : al) {
                //     Prints the path
                
                path.add(locations.get(i-1));
                
                System.out.print(i + "  "+locations.get(i-1).getName());
            }
            
            System.out.print("next path");
            allpath.add(path);
            
            return;
        }
        for (int I = 1; I <= dim; I++) {
            if (graph[src][I] == 1) {
                if (color[I] == false) {
                    dfs(I, dst);        // These lines do
                    color[I] = false;   // main job of backtracking
                    size--;
                    al.remove(size);
                }
            }
        }
    }
}
