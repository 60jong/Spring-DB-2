package site._60jong.transaction.repository.member.querydsl;

import site._60jong.transaction.domain.Member;
import site._60jong.transaction.repository.member.MemberSearchCond;

import java.util.List;

public interface MemberQueryRepository {

    List<Member> findAll(MemberSearchCond cond);
}
