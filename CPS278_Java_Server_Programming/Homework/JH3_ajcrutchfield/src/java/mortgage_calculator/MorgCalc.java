/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mortgage_calculator;

/**
 *
 * @author AaronC
 */
public class MorgCalc {
    private int  
            payment,
            years;
    private double interest,
            newPrincipal,
            interestPaid;
    
    MorgCalc(double interest, double principal, int payment){
        newPrincipal = principal;
        this.payment = payment;
        interestPaid = (newPrincipal * interest) / (12*100);
        newPrincipal = newPrincipal + interestPaid - payment;
    }
    
    public String getPrincipal(){
//        newPrincipal = newPrincipal + interestPaid - payment;
        String sPrinciple = String.format("%.2f", newPrincipal);
        return sPrinciple;
    }
    
    public String getInterestPaid(){
//        interestPaid = (newPrincipal * interest) / (12*100);
        String sInterestPaid = String.format("%.2f", interestPaid);
        return sInterestPaid;
    }
}
