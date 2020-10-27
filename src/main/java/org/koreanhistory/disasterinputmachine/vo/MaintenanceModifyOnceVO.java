package org.koreanhistory.disasterinputmachine.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Component
public class MaintenanceModifyOnceVO {
    private List<Long> modifyList;

    public void addModifyKey(Long key) {
        if(modifyList == null)
            modifyList = new ArrayList<>();
        modifyList.add(key);
    }

    public void removeModifyKey(Long key) {
        if(modifyList == null)
            return;
        int index = modifyList.indexOf(key);
        modifyList.remove(index);
    }

    public void cleanUp() {
        modifyList.clear();
    }
}
