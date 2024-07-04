package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Review;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class LikedReviewResponseDto {

    private Integer currentPage;
    private Long totalElements;
    private Integer totalPages;
    private Integer size;
    private List<ReviewResponseDto> reviewList;

    public LikedReviewResponseDto(Integer page, Page<Review> reviewPage) {
        this.currentPage = page;
        this.totalElements = reviewPage.getTotalElements();
        this.totalPages = reviewPage.getTotalPages();
        this.size = reviewPage.getSize();
        this.reviewList = reviewPage.getContent().stream().map(ReviewResponseDto::new).toList();
    }
}
