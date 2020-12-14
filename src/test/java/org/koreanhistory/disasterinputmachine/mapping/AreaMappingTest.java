package org.koreanhistory.disasterinputmachine.mapping;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class AreaMappingTest {

    @Test
    public void 클래스정상생성확인() {
        AreaMapping instance = AreaMapping.getInstance();
        AreaMapping instance2 = AreaMapping.getInstance();

        assertThat(instance).isEqualTo(instance2);

        assertThat(instance.getAreaOfChina("경기")).isEqualTo("京畿");
        assertThat(instance.getAreaOfChina("영원")).isEqualTo("寧遠");
        assertThat(instance.getAreaOfChina("부령도호부")).isEqualTo("富寧都護府");
    }
}
