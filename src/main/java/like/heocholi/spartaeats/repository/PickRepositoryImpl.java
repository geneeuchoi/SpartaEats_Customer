package like.heocholi.spartaeats.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import like.heocholi.spartaeats.entity.Pick;
import like.heocholi.spartaeats.entity.QPick;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PickRepositoryImpl implements PickRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    //사용자가 찜한 가게 리스트
    //picks : id, customer_id, store_id, is_pick
    @Override
    public Page<Pick> findAllByCustomerAndIsPickTrue(Long customer_id, Pageable pageable) {

        QPick qPick = QPick.pick;

        List<Pick> picks = jpaQueryFactory.selectFrom(qPick)
                //qPick의 customer의 id와 custmomer_id가 동일하고
                //qPick의 isPick이 true인 값만 필터링
                .where(qPick.customer.id.eq(customer_id).and(qPick.isPick.isTrue()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = jpaQueryFactory.selectFrom(qPick)
                .where(qPick.customer.id.eq(customer_id).and(qPick.isPick.isTrue()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().size();

        return new PageImpl<>(picks, pageable, count);

    }
}

