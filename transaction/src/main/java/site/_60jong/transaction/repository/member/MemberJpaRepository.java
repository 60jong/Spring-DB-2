package site._60jong.transaction.repository.member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import site._60jong.transaction.domain.Member;
import site._60jong.transaction.repository.member.querydsl.MemberQueryRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MemberJpaRepository implements MemberRepository {

    private final EntityManager em;
    private final MemberQueryRepository memberQueryRepository;

    @Transactional
    @Override
    public Member save(Member member) {
        em.persist(member);
        log.info("Member saved : {} / {}", member.getId(), member.getName());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    @Override
    public Optional<Member> findByName(String username) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", username)
                .getResultStream()
                .findAny();
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
