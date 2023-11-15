package site._60jong.transaction.repository.member;

import site._60jong.transaction.domain.Member;
import site._60jong.transaction.repository.member.querydsl.MemberQueryRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends MemberQueryRepository {

    Optional<Member> findById(Long id);

    List<Member> findAll();
}
