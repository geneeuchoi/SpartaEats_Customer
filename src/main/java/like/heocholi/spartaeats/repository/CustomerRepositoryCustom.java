package like.heocholi.spartaeats.repository;

public interface CustomerRepositoryCustom {
    long findLikedReviewCount(Long id);

    long findPickedStoreCount(Long id);
}
