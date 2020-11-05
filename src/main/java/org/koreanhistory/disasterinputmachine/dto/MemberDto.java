package org.koreanhistory.disasterinputmachine.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.koreanhistory.disasterinputmachine.domain.Member;
import org.koreanhistory.disasterinputmachine.domain.MemberRole;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {

    private String uid;

    private String uname;

    // Created time for debug
    private Timestamp createTime;

    // Modified time for debug
    private Timestamp modifyTime;

    private List<MemberRole> roles;

    public MemberDto(Member entity) {
        this.uid = entity.getUid();
        this.uname = entity.getUname();
        this.createTime = entity.getCreateTime();
        this.modifyTime = entity.getModifyTime();
        this.roles = entity.getRoles();
    }
}
