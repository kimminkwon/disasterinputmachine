package org.koreanhistory.disasterinputmachine.mapping;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class DynastyKRMappingTest {

    @Test
    public void 클래스정상생성확인() {
        DynastyKRMapping instance = DynastyKRMapping.getInstance();
        DynastyKRMapping instance2 = DynastyKRMapping.getInstance();

        assertThat(instance).isEqualTo(instance2);

        // 신라 테스트
        assertThat(instance.getYearADAndAge("新羅", "赫居世居西干1")).contains("B.C57");
        assertThat(instance.getYearADAndAge("新羅", "敬順王9")).contains("935");

        // 고구려 테스트
        assertThat(instance.getYearADAndAge("高句麗", "東明聖王1")).contains("B.C37");
        assertThat(instance.getYearADAndAge("高句麗", "寶藏王27")).contains("668");
        assertThat(instance.getYearADAndAge("高句麗", "廣開土王3")).contains("393").contains("永樂3");

        // 백제 테스트
        assertThat(instance.getYearADAndAge("百濟", "溫祚王1")).contains("B.C18");
        assertThat(instance.getYearADAndAge("百濟", "豊王3")).contains("663");

        // 발해 테스트
        assertThat(instance.getYearADAndAge("渤海", "高王1")).contains("698");
        assertThat(instance.getYearADAndAge("渤海", "文王57")).contains("793").contains("大興57");
        assertThat(instance.getYearADAndAge("渤海", "大諲譔21")).contains("926");

        // 후백제 테스트
        assertThat(instance.getYearADAndAge("後百濟", "甄萱1")).contains("892");
        assertThat(instance.getYearADAndAge("後百濟", "神劍1")).contains("936");

        // 후고구려 테스트
        assertThat(instance.getYearADAndAge("後高句麗", "弓裔1")).contains("901");
        assertThat(instance.getYearADAndAge("後高句麗", "弓裔11")).contains("911").contains("水德萬歲1");
        assertThat(instance.getYearADAndAge("後高句麗", "弓裔18")).contains("918").contains("政開5");

        // 고려 테스트
        assertThat(instance.getYearADAndAge("高麗", "太祖1")).contains("918").contains("天授1");
        assertThat(instance.getYearADAndAge("高麗", "仁宗14")).contains("1136").contains("天開2");
        assertThat(instance.getYearADAndAge("高麗", "恭讓王4")).contains("1392");

        // 조선 테스트
        assertThat(instance.getYearADAndAge("朝鮮", "太祖1")).contains("1392");
        assertThat(instance.getYearADAndAge("朝鮮", "哲宗14")).contains("1863");
    }
}
