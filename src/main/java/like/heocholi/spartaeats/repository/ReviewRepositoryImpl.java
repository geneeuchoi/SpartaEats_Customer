package like.heocholi.spartaeats.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import like.heocholi.spartaeats.entity.QLike;
import like.heocholi.spartaeats.entity.QReview;
import like.heocholi.spartaeats.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{

    // JPAQueryFactory: 객체 또는 함수로 쿼리를 작성할 수 있게 하는 QueryDSL의 도구
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Review> findLikedReview(Long userId, Pageable pageable) {

        // Q객체 생성
        QReview qReview = QReview.review;
        QLike qLike = QLike.like;

        // 동적 sorting
        OrderSpecifier<?> orderSpecifier = qReview.createdAt.desc();

        //좋아요를 누른 리뷰 목록 조회
        //생성일자 기준으로 최신순 정렬
        List<Review> reviews = jpaQueryFactory.selectFrom(qReview)
                // review_id를 기준으로 leftJoin
                .leftJoin(qLike).on(qReview.id.eq(qLike.review.id))
                // customer의 userId와 qLike의 customer_id가 일치하고
                // isLike가 True인 값만
                .where(qLike.customer.id.eq(userId).and(qLike.isLike.isTrue()))
                // 생성일자 기준으로 최신순 정렬(내림차순) - 동적 정렬 이용
                .orderBy(orderSpecifier)
                // offset~limit 페이지 조회
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                // 리스트로 반환
                .fetch();

        // 사용자가 좋아요를 누른 전체 리뷰 수 count
        long count = jpaQueryFactory
                .select(qReview)
                .from(qReview)
                .leftJoin(qLike).on(qReview.id.eq(qLike.review.id))
                .where(qLike.customer.id.eq(userId).and(qLike.isLike.isTrue()))
                .fetch().size();

        return new PageImpl<>(reviews, pageable, count);
    }

}
