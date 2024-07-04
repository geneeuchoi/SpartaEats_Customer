package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Pick;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PickRepositoryCustom {
    Page<Pick> findAllByCustomerAndIsPickTrue(Long customer_id, Pageable pageable);
}
