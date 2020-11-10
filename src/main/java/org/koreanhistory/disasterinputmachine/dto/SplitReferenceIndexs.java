package org.koreanhistory.disasterinputmachine.dto;

public class SplitReferenceIndexs {
    public String[] splitReferenceIndex(String refer) {
        return refer.split(", ");
    }
}
