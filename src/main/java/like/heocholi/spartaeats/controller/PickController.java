package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.dto.PickPageResponseDto;
import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.PickService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/picks")
public class PickController {

    private final PickService pickService;

    // 내가 좋아하는 댓글(가게) 목록 조회하기
    @GetMapping
    public ResponseEntity<ResponseMessage<PickPageResponseDto>> getPickList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        PickPageResponseDto pickPageResponseDto = pickService.getPickList(userDetails.getCustomer(), page);

        ResponseMessage<PickPageResponseDto> responseMessage = ResponseMessage.<PickPageResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("찜하기 리스트를 성공적으로 불러왔습니다.")
                .data(pickPageResponseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}
