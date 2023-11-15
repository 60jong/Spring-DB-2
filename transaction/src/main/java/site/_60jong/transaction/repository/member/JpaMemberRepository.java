package site._60jong.transaction.repository.member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site._60jong.transaction.domain.Member;
import site._60jong.transaction.repository.member.querydsl.MemberQueryRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;
    private final MemberQueryRepository memberQueryRepository;

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    @Override
    public List<Member> findAll(MemberSearchCond cond) {
        return memberQueryRepository.findAll(cond);
    }
}
