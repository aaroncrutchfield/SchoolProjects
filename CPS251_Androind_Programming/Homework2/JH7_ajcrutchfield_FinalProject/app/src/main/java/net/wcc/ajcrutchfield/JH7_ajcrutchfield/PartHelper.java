package net.wcc.ajcrutchfield.JH7_ajcrutchfield;

import java.io.Serializable;

/**
 * Created by AaronC on 11/24/2015.
 */

public class PartHelper implements Serializable {
    private int demand;
    private String partNumber;
    private int standardCount;
    private int toteDemand;

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public int getStandardCount() {
        return standardCount;
    }

    public void setStandardCount(int standardCount) {
        this.standardCount = standardCount;
    }

    public int getToteDemand() {
        return toteDemand;
    }

    public void setToteDemand(int toteDemand) {
        this.toteDemand = toteDemand;
    }

}
