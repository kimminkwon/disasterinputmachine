package org.koreanhistory.disasterinputmachine.mapping;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClasMappingTest {

    @Test
    public void 클래스정상생성확인() {
        ClasMapping instance = ClasMapping.getInstance();
        ClasMapping instance2 = ClasMapping.getInstance();

        assertThat(instance).isEqualTo(instance2);

        assertThat(instance.getAreaDatas("10101")[0]).isEqualTo("1 천변");
        assertThat(instance.getAreaDatas("10101")[1]).isEqualTo("1 天變");
        assertThat(instance.getAreaDatas("10101")[2]).isEqualTo("01 천변");
        assertThat(instance.getAreaDatas("10101")[3]).isEqualTo("01 天變");
        assertThat(instance.getAreaDatas("10101")[4]).isEqualTo("01천변");
        assertThat(instance.getAreaDatas("10101")[5]).isEqualTo("01天變");

        assertThat(instance.getAreaDatas("30401")[0]).isEqualTo("3 변괴");
        assertThat(instance.getAreaDatas("30401")[1]).isEqualTo("3 變怪");
        assertThat(instance.getAreaDatas("30401")[2]).isEqualTo("04 이현");
        assertThat(instance.getAreaDatas("30401")[3]).isEqualTo("04 異現");
        assertThat(instance.getAreaDatas("30401")[4]).isEqualTo("01이상현상");
        assertThat(instance.getAreaDatas("30401")[5]).isEqualTo("01이상현상");

        assertThat(instance.getAreaDatas("60101")[0]).isEqualTo("6 기타");
        assertThat(instance.getAreaDatas("60101")[1]).isEqualTo("6 기타");
        assertThat(instance.getAreaDatas("60101")[2]).isEqualTo("01 기타");
        assertThat(instance.getAreaDatas("60101")[3]).isEqualTo("01 기타");
        assertThat(instance.getAreaDatas("60101")[4]).isEqualTo("01기타");
        assertThat(instance.getAreaDatas("60101")[5]).isEqualTo("01기타");
    }
}
