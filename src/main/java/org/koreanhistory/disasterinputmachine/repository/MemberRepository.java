package org.koreanhistory.disasterinputmachine.repository;

import org.koreanhistory.disasterinputmachine.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {

}
