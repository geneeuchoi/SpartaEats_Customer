package like.heocholi.spartaeats.controller;

import jakarta.validation.Valid;
import like.heocholi.spartaeats.dto.*;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 조회
    @GetMapping("/stores/{storeId}/reviews")
    public ResponseEntity<ResponseMessage<List<ReviewResponseDto>>> getReview(@PathVariable Long storeId) {

        List<ReviewResponseDto> reviewList = reviewService.getReviews(storeId);

        ResponseMessage<List<ReviewResponseDto>> responseMessage = ResponseMessage.<List<ReviewResponseDto>>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("모든 리뷰가 조회되었습니다.")
                .data(reviewList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping("/stores/{storeId}/reviews/{reviewId}")
    public ResponseEntity<ResponseMessage<ReviewResponseDto>> getReview(@PathVariable Long storeId,
                                                                        @PathVariable Long reviewId) {

        ReviewResponseDto responseDto = reviewService.getReview(storeId, reviewId);

        ResponseMessage<ReviewResponseDto> responseMessage = ResponseMessage.<ReviewResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("해당 리뷰가 조회되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    //리뷰 등록
    @PostMapping("/orders/{orderId}/reviews")
    public ResponseEntity<ResponseMessage<ReviewResponseDto>> addReview(@PathVariable Long orderId,
                                                                        @RequestBody @Valid ReviewAddRequestDto requestDto,
                                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ReviewResponseDto responseDto = reviewService.addReview(orderId, requestDto, userDetails.getCustomer());

        ResponseMessage<ReviewResponseDto> responseMessage = ResponseMessage.<ReviewResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("리뷰가 등록되었습니다.")
                .data(responseDto)
                .build();


        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    //리뷰 수정
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ResponseMessage<ReviewResponseDto>> updateReview(@PathVariable Long reviewId,
                                                                           @RequestBody @Valid ReviewUpdateRequestDto requestDto,
                                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ReviewResponseDto responseDto = reviewService.updateReview(reviewId, requestDto, userDetails.getCustomer());

        ResponseMessage<ReviewResponseDto> responseMessage = ResponseMessage.<ReviewResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("리뷰가 수정 되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }


    //리뷰 삭제
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ResponseMessage<Long>> deleteReview(@PathVariable Long reviewId,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long deleteId = reviewService.deleteReview(reviewId, userDetails.getCustomer());

        ResponseMessage<Long> responseMessage = ResponseMessage.<Long>builder()
                .statusCode(HttpStatus.OK.value())
                .message("리뷰가 삭제 되었습니다.")
                .data(deleteId)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // 좋아요 누른 리뷰 조회
    @GetMapping("/reviews/liked")
    public ResponseEntity<ResponseMessage<LikedReviewResponseDto>> getLikedReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {

        LikedReviewResponseDto reviewList = reviewService.getLikedReviews(userDetails.getCustomer(), page);

        ResponseMessage<LikedReviewResponseDto> responseMessage = ResponseMessage.<LikedReviewResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("좋아요를 누른 모든 리뷰가 조회되었습니다.")
                .data(reviewList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }




}
