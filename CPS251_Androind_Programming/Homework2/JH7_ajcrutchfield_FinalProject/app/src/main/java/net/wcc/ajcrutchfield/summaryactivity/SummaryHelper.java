package net.wcc.ajcrutchfield.summaryactivity;

import java.io.Serializable;

/**
 * Created by AaronC on 11/29/2015.
 */
public class SummaryHelper implements Serializable{

    private String partNumber;
    private String fullTotes;
    private String partialCount;
    private String totalCount;
    private int partialTotal;
    private int totalDemand;
    private int standardCount;

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getFullTotes() {
        return fullTotes;
    }

    public void setFullTotes(String fullTotes) {
        this.fullTotes = fullTotes;
    }

    public String getPartialCount() {
        return partialCount;
    }

    public void setPartialCount(String partialCount) {
        this.partialCount = partialCount;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalDemand() {
        return totalDemand;
    }

    public void setTotalDemand(int totalDemand) {
        this.totalDemand = totalDemand;
    }

    public int getStandardCount() {
        return standardCount;
    }

    public void setStandardCount(int standardCount) {
        this.standardCount = standardCount;
    }

    public int getPartialTotal() {
        return partialTotal;
    }

    public void setPartialTotal(int partialTotal) {
        this.partialTotal = partialTotal;
    }
}
