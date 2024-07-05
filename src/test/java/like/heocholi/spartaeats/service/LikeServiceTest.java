package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.dto.SignupRequestDto;
import like.heocholi.spartaeats.entity.*;
import like.heocholi.spartaeats.exception.LikeException;
import like.heocholi.spartaeats.repository.LikeRepository;
import like.heocholi.spartaeats.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @InjectMocks
    private LikeService likeService;

    @Test
    public void 좋아요_등록() {
        //given

        //customer1 생성
        SignupRequestDto signupRequestDto = new SignupRequestDto("test1234", "Test1234!!", "testName", "testAddress");
        String encodedPassword = "testPassword";
        Customer customer1 = new Customer(signupRequestDto, encodedPassword);

        //customer2 생성
        SignupRequestDto signupRequestDto2 = new SignupRequestDto("test5678", "Test1234!!", "testName", "testAddress");
        String encodedPassword2 = "testPassword";
        Customer customer2 = new Customer(signupRequestDto2, encodedPassword2);

        //id 설정
        ReflectionTestUtils.setField(customer1, "id", 1L);
        ReflectionTestUtils.setField(customer2, "id", 2L);

        //리뷰 생성
        Order order = new Order();
        Store store = new Store();
        String contents = "test Contents";
        Review review1 = new Review(order, store, customer2, contents);

        //id 설정
        ReflectionTestUtils.setField(review1, "id", 1L);
        Long review1Id = review1.getId();

        // Mock 설정
        given(reviewRepository.findById(review1Id)).willReturn(Optional.of(review1));
        given(likeRepository.findByCustomerIdAndReviewId(customer1.getId(), review1Id)).willReturn(Optional.empty());

        //when
        boolean result = likeService.likeReview(review1Id, customer1);

        //then
        assertTrue(result);
    }

    @Test
    public void 좋아요_취소() {
        //given

        //customer1 생성
        SignupRequestDto signupRequestDto = new SignupRequestDto("test1234", "Test1234!!", "testName", "testAddress");
        String encodedPassword = "testPassword";
        Customer customer1 = new Customer(signupRequestDto, encodedPassword);

        //customer2 생성
        SignupRequestDto signupRequestDto2 = new SignupRequestDto("test5678", "Test1234!!", "testName", "testAddress");
        String encodedPassword2 = "testPassword";
        Customer customer2 = new Customer(signupRequestDto2, encodedPassword2);

        //id 설정
        ReflectionTestUtils.setField(customer1, "id", 1L);
        ReflectionTestUtils.setField(customer2, "id", 2L);

        //리뷰 생성
        Order order = new Order();
        Store store = new Store();
        String contents = "test Contents";
        Review review1 = new Review(order, store, customer2, contents);

        //리뷰 id 설정
        ReflectionTestUtils.setField(review1, "id", 1L);
        Long review1Id = review1.getId();

        //Like 설정
        Like like = Like.builder()
                .customer(customer1)
                .review(review1)
                .isLike(true)
                .build();

        // Mock 설정
        given(reviewRepository.findById(review1Id)).willReturn(Optional.of(review1));
        given(likeRepository.findByCustomerIdAndReviewId(customer1.getId(), review1Id)).willReturn(Optional.of(like));

        //when
        boolean result = likeService.likeReview(review1Id, customer1);

        //then
        assertFalse(result);
    }

    @Test
    public void 자신이_쓴_리뷰에_좋아요() {
        //given

        //customer1 생성
        SignupRequestDto signupRequestDto = new SignupRequestDto("test1234", "Test1234!!", "testName", "testAddress");
        String encodedPassword = "testPassword";
        Customer customer1 = new Customer(signupRequestDto, encodedPassword);

        ReflectionTestUtils.setField(customer1, "id", 1L);

        //customer1이 customer1의 리뷰에 좋아요 등록
        Order order = new Order();
        Store store = new Store();
        String contents = "test Contents";
        Review review1 = new Review(order, store, customer1, contents);

        ReflectionTestUtils.setField(review1, "id", 1L);
        Long review1Id = review1.getId();

        // Mock 설정
        given(reviewRepository.findById(review1Id)).willReturn(Optional.of(review1));

        //when & then
        LikeException exception = assertThrows(LikeException.class, () -> {
            likeService.likeReview(review1Id, customer1);
        });

        assertEquals(ErrorType.INVALID_LIKE, exception.getErrorType());
    }



}