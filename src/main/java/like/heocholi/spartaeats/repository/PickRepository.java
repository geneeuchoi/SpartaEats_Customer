package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Pick;
import like.heocholi.spartaeats.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickRepository extends JpaRepository<Pick, Long>, PickRepositoryCustom {
    Pick findByStoreAndCustomer(Store store, Customer customer);

    // 내가 좋아하는 댓글(가게) 목록 조회하기
    // JPA -> QueryDSL로 변경
    //Page<Pick> findAllByCustomerAndIsPickTrue(Customer customer, Pageable pageable);
}
