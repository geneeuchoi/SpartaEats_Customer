package like.heocholi.spartaeats.controller;

import jakarta.validation.Valid;
import like.heocholi.spartaeats.dto.*;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    /**
     * 회원가입 API
     * @param requestDto 회원가입 내용
     * @return 회원가입 정보, 응답 상태, 메시지
     */
    @PostMapping
    public ResponseEntity<ResponseMessage<SignupResponseDto>> signup(@RequestBody @Valid SignupRequestDto requestDto){
        SignupResponseDto responseDto = customerService.signup(requestDto);

        ResponseMessage<SignupResponseDto> responseMessage = ResponseMessage.<SignupResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("회원가입 성공")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    /**
     * 로그아웃 API
     * @param userDetails 유저 정보
     * @return 회원Id, 응답 상태, 메시지
     */
    @PutMapping("/logout")
    public ResponseEntity<ResponseMessage<String>> logout(@AuthenticationPrincipal UserDetailsImpl userDetails){
        String userId = customerService.logout(userDetails.getUsername());

        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("로그아웃 성공")
                .data(userId)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * 회원 탈퇴 API
     * @param requestDto 회원탈퇴 내용(비밀번호)
     * @param userDetails 회원 정보
     * @return 회원Id, 응답상태, 메시지
     */
    @PutMapping("/withdraw")
    public ResponseEntity<ResponseMessage<String>> withdrawCustomer(@RequestBody WithdrawRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userId = userDetails.getUsername();
        String withdrawnUserId = customerService.withdrawCustomer(requestDto, userId);

        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("회원 탈퇴가 완료되었습니다.")
                .data(withdrawnUserId)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    
    // 프로필 조회 API
    // 프로필에 내가 좋아요한 리뷰 수 && 찜하기 한 가게 수 필드 추가
    @GetMapping
    public ResponseEntity<ResponseMessage<CustomerResponseDTO>> getCustomerInfo (@AuthenticationPrincipal UserDetailsImpl userDetails) {
        CustomerResponseDTO responseDTO =  customerService.getCustomerInfo(userDetails.getCustomer());

        ResponseMessage<CustomerResponseDTO> responseMessage = ResponseMessage.<CustomerResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("프로필 조회가 완료되었습니다.")
                .data(responseDTO)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // 비밀번호 업데이트 API
    // customerId 사용자 ID
    // request 요청 DTO (PasswordRequest)
    // ResponseEntity<String> 업데이트 결과 메시지
    @PutMapping("/password")
    public ResponseEntity<ResponseMessage<String>> updatePassword(@RequestBody @Valid PasswordRequestDTO request,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String customerId = customerService.updatePassword(request, userDetails.getCustomer());

        ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("비밀번호 변경이 완료되었습니다.")
                .data(customerId)
                .build();


        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    //프로필 업데이트 API
    // customerId 사용자 ID
    // request 요청 DTO (ProfileRequest)
    // ResponseEntity<String> 업데이트 결과 메시지
    @PutMapping
    public ResponseEntity<ResponseMessage<ProfileResponseDTO>> updateProfile(@RequestBody @Valid ProfileRequestDTO request,
                                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDTO responseDTO = customerService.updateProfile(request, userDetails.getCustomer());

        ResponseMessage<ProfileResponseDTO> responseMessage = ResponseMessage.<ProfileResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("프로필 업데이트가 완료되었습니다.")
                .data(responseDTO)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
