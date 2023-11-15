package site._60jong.transaction.repository.member.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import site._60jong.transaction.domain.Member;
import site._60jong.transaction.domain.QMember;
import site._60jong.transaction.repository.member.MemberSearchCond;

import java.util.List;

import static site._60jong.transaction.domain.QMember.*;
import static site._60jong.transaction.repository.member.querydsl.MemberQueryRepositoryImpl.MemberExpression.*;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> findAll(MemberSearchCond cond) {
        return queryFactory
                .select(member)
                .from(member)
                .where(
                        nameLike(cond.getName()),
                        moneyBetween(cond.getMinMoney(), cond.getMaxMoney()))
                .fetch();
    }

    static class MemberExpression {

        static BooleanExpression nameLike(String name) {
            return StringUtils.hasText(name) ? member.name.like(name) : null ;
        }

        static BooleanExpression moneyBetween(Integer min, Integer max) {
            return (min == null && max == null) ? null : member.money.between(min, max);
        }
    }
}
