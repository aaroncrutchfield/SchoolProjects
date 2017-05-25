/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mortgage_calculator;

import java.util.ArrayList;

/**
 *
 * @author AaronC
 */
public class AllCalculations {
    public static ArrayList<MorgCalc> getMorgCalcArray(double interest, int years, int principal, int payment){
        int months = 12 * years;
        ArrayList<MorgCalc> morgArray = new ArrayList<MorgCalc>();
        double newPrincipal = 0;
        for (int y = 0; y <= months; y++){
            if (newPrincipal == 0){
                newPrincipal = principal;
            }
            MorgCalc temp = new MorgCalc(interest, newPrincipal, payment);
            morgArray.add(temp);
            newPrincipal = Double.parseDouble(temp.getPrincipal());
        }
        
        return morgArray;
    }
}
