/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd.Common;

/**
 *
 * @author USER
 */

import javax.faces.bean.ManagedBean; 

 @ManagedBean(name="fBean") 
public class FahrenheitBean {

    private int f = 32;

    public int getF() {

        return (f);

    }

    public void setF(int f) {

        this.f = Math.max(f, -460);  // -459.67 is absolute zero 

    }

    public int getC() {

        return ((int) ((f - 32) * (5.0 / 9.0)));

    }

    public String getStatus() {

        return (String.format("%s&deg;F = %s&deg;C",
                f, getC()));
    }

}
