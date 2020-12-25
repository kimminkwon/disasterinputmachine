package org.koreanhistory.disasterinputmachine.mapping;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DynastyCNMappingTest {

    @Test
    public void 클래스정상생성확인() {
        DynastyCNMapping instance = DynastyCNMapping.getInstance();
        DynastyCNMapping instance2 = DynastyCNMapping.getInstance();

        assertThat(instance).isEqualTo(instance2);

        // 당 테스트
        assertThat(instance.getYearADAndNameOfTomb("唐", "天復1")).contains("901").contains("昭宗");
        assertThat(instance.getYearADAndNameOfTomb("唐", "天佑4")).contains("907").contains("哀帝");

        // 후량 테스트
        assertThat(instance.getYearADAndNameOfTomb("後梁", "開平1")).contains("907").contains("太祖");
        assertThat(instance.getYearADAndNameOfTomb("後梁", "龍德3")).contains("923").contains("末帝");

        // 후당 테스트
        assertThat(instance.getYearADAndNameOfTomb("後唐", "同光1")).contains("923").contains("莊宗");
        assertThat(instance.getYearADAndNameOfTomb("後唐", "淸泰3")).contains("936").contains("廢帝");

        // 후진 테스트
        assertThat(instance.getYearADAndNameOfTomb("後晉", "天福1")).contains("936").contains("高祖");
        assertThat(instance.getYearADAndNameOfTomb("後晉", "開運4")).contains("947").contains("出帝");

        // 후한 테스트
        assertThat(instance.getYearADAndNameOfTomb("後漢", "天福1")).contains("947").contains("高祖");
        assertThat(instance.getYearADAndNameOfTomb("後漢", "乾祐4")).contains("951").contains("隱帝");

        // 후주 테스트
        assertThat(instance.getYearADAndNameOfTomb("後周", "廣順1")).contains("951").contains("太祖");
        assertThat(instance.getYearADAndNameOfTomb("後周", "顯德7")).contains("960").contains("恭帝");

        // 송 테스트
        assertThat(instance.getYearADAndNameOfTomb("宋", "建隆1")).contains("960").contains("太祖");
        assertThat(instance.getYearADAndNameOfTomb("宋", "祥興2")).contains("1279").contains("衛王");

        // 거란/요 테스트
        assertThat(instance.getYearADAndNameOfTomb("契丹(遼)", "太祖1")).contains("907").contains("太祖");
        assertThat(instance.getYearADAndNameOfTomb("遼", "保大5")).contains("1125").contains("天祚帝");

        // 서하 테스트
        assertThat(instance.getYearADAndNameOfTomb("西夏", "顯道1")).contains("1032").contains("景宗");
        assertThat(instance.getYearADAndNameOfTomb("西夏", "寶義2")).contains("1227").contains("李睍");

        // 금 테스트
        assertThat(instance.getYearADAndNameOfTomb("金", "太祖1")).contains("1113").contains("太祖");
        assertThat(instance.getYearADAndNameOfTomb("金", "盛昌1")).contains("1234").contains("末帝");

        // 원 테스트
        assertThat(instance.getYearADAndNameOfTomb("元", "太祖1")).contains("1206").contains("太祖");
        assertThat(instance.getYearADAndNameOfTomb("元", "皇后 海迷失 後1")).contains("1248").contains("皇后 海迷失");
        assertThat(instance.getYearADAndNameOfTomb("元", "至正30")).contains("1370").contains("順帝");

        // 명 테스트
        assertThat(instance.getYearADAndNameOfTomb("明", "洪武1")).contains("1368").contains("太祖");
        assertThat(instance.getYearADAndNameOfTomb("明", "永曆16")).contains("1662").contains("昭宗");

        // 청 테스트
        assertThat(instance.getYearADAndNameOfTomb("淸", "天命1")).contains("1616").contains("太祖");
        assertThat(instance.getYearADAndNameOfTomb("淸", "同治2")).contains("1863").contains("穆宗");

    }
}
