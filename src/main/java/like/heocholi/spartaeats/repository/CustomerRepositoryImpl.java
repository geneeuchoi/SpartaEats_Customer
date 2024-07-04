package like.heocholi.spartaeats.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import like.heocholi.spartaeats.entity.QLike;
import like.heocholi.spartaeats.entity.QPick;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public long findLikedReviewCount(Long id) {
        QLike qLike = QLike.like;

        return jpaQueryFactory
                .selectFrom(qLike)
                // 사용자가 좋아요 한 리뷰 개수
                // 좋아요가 되어 있고(isTrue)
                // qLike의 customer의 id가 파라미터로 받은 id와 동일한 경우만 필터링
                .where(qLike.isLike.isTrue().and(qLike.customer.id.eq(id)))
                .fetch().size();
    }

    @Override
    public long findPickedStoreCount(Long id) {
        QPick qPick = QPick.pick;

        return jpaQueryFactory.selectFrom(qPick)
                .where(qPick.isPick.isTrue().and(qPick.customer.id.eq(id)))
                .fetch().size();
    }
}
