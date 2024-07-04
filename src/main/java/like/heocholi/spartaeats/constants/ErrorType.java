package like.heocholi.spartaeats.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    // user
    DUPLICATE_ACCOUNT_ID(HttpStatus.LOCKED, "이미 아이디가 존재합니다."),
    INVALID_ACCOUNT_ID(HttpStatus.UNAUTHORIZED, "아이디가 일치하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    DEACTIVATE_USER(HttpStatus.FORBIDDEN, "이미 탈퇴한 회원입니다."),
    DUPLICATE_PASSWORD(HttpStatus.LOCKED, "중복된 비밀번호입니다."),
    NO_AUTHENTICATION(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    REQUIRES_LOGIN(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다."),

    // JWT
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다. 다시 로그인 해주세요."),
    NOT_FOUND_AUTHENTICATION_INFO(HttpStatus.NOT_FOUND, "인증 정보를 찾을 수 없습니다."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "유효하지 않는 JWT 입니다."),
    EXPIRED_JWT(HttpStatus.FORBIDDEN, "만료된 JWT 입니다."),
    LOGGED_OUT_TOKEN(HttpStatus.FORBIDDEN, "이미 로그아웃된 토큰입니다."),
    
    //order
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "주문이 존재하지 않습니다."),
    INVALID_ORDER_CUSTOMER(HttpStatus.BAD_REQUEST, "본인의 주문 내역이 아닙니다."),
    
    //page
    INVALID_PAGE(HttpStatus.BAD_REQUEST, "페이지 번호가 올바르지 않습니다."),

    // cart
    INVALID_CART(HttpStatus.BAD_REQUEST, "장바구니에는 같은 가게의 메뉴만 담을 수 있습니다."),
    DUPLICATE_CART(HttpStatus.BAD_REQUEST, "이미 장바구니에 담긴 메뉴입니다."),
    NOT_FOUND_CART(HttpStatus.NOT_FOUND, "장바구니가 비어있습니다."),
    NOT_FOUND_CART_MENU(HttpStatus.NOT_FOUND, "장바구니에 해당 메뉴가 존재하지 않습니다."),
    
    // menu
    NOT_FOUND_STORE(HttpStatus.NOT_FOUND,"선택한 음식점이 존재하지 않습니다."),
    NOT_FOUND_MENUS(HttpStatus.NOT_FOUND,"메뉴가 없습니다."),
    NOT_FOUND_MENU(HttpStatus.NOT_FOUND,"해당 메뉴가 존재하지 않습니다."),

    //review
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND,"리뷰가 존재하지 않습니다."),

    //password
    RECENTLY_USED_PASSWORD(HttpStatus.BAD_REQUEST, "최근 3번 안에 사용한 비밀번호로는 변경할 수 없습니다."),

    //like
    INVALID_LIKE(HttpStatus.BAD_REQUEST, "본인이 작성한 리뷰에는 좋아요를 할 수 없습니다."),
    
      
    //store
    INVALID_TYPE(HttpStatus.BAD_REQUEST, "타입이 올바르지 않습니다."),
    NOT_FOUND_STORES(HttpStatus.NOT_FOUND,"음식점이 존재하지 않습니다."),

    //pick
    NOT_FOUND_PICK(HttpStatus.NOT_FOUND,"찜 목록이 존재하지 않습니다."),

    //review
    NOT_FOUND_LIKED_REVIEW(HttpStatus.NOT_FOUND,"좋아요 한 리뷰가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
