package org.koreanhistory.disasterinputmachine.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SplitReferenceIndexs {
    public String[] splitReferenceIndex(String refer) {
        if(refer == null || refer.equals("")) return null;
        List<String> splitedRefer = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(refer, "\\|");
        while(st.hasMoreTokens())
            splitedRefer.add(st.nextToken());

        String[] refers = splitedRefer.toArray(new String[0]);
        return refers;
    }
}
