package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.dto.*;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.PasswordHistory;
import like.heocholi.spartaeats.repository.CustomerRepository;
import like.heocholi.spartaeats.repository.PasswordHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입 메서드
    // requestDto 회원가입 요청 DTO (SignupRequestDto)
    // String 회원가입 성공 메시지
    @Transactional
    public String signup(SignupRequestDto requestDto) {
        String userId = requestDto.getUserId();
        String password = requestDto.getPassword();

        Optional<Customer> checkUsername = customerRepository.findByUserId(userId);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        Customer customer = new Customer(requestDto, encodedPassword);
        customerRepository.save(customer);
        insertPasswordHistory(customer);

        return "회원가입 성공";
    }

    //유저 정보 조회
    public CustomerResponseDTO getCustomerInfo(Customer customer) {
        return new CustomerResponseDTO(customer);
    }


    //비밀번호 업데이트 메서드
    // customerId 사용자 ID
    // request 비밀번호 업데이트 요청 DTO (PasswordRequest)
    // String 비밀번호 업데이트 결과 메시지

    @Transactional
    public String updatePassword(PasswordRequestDTO request, Customer customer) {
        //1. 현재 저장된 비밀번호랑 request에서 현재 비밀번호라고 입력한 애랑 일치하는지!
        if (! passwordEncoder.matches(request.getCurrentPassword(), customer.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호와 일치하지 않습니다.");
        }

        //2. 얘가 새로 바꾸려고 하는 비밀번호랑, 얘가 최근에 사용했던 비밀번호 3개 중에 일치하는 게 있는지!
        List<PasswordHistory> passwordHistories = passwordHistoryRepository.findTop3ByCustomerOrderByCreatedAtDesc(customer);

        for (PasswordHistory passwordHistory : passwordHistories) {
            if (passwordEncoder.matches(passwordHistory.getPassword(), request.getNewPassword())) {
                throw new IllegalArgumentException("최근 3번 안에 사용한 비밀번호로는 변경할 수 없습니다.");
            }
        }

        //3. PasswordHistory에 현재 비밀번호 저장!
        PasswordHistory passwordHistory = PasswordHistory.builder()
                .customer(customer)
                .password(customer.getPassword())
                .build();

        passwordHistoryRepository.save(passwordHistory);

        //4. 비밀번호 업데이트!
        customer.updatePassword(passwordEncoder.encode(request.getNewPassword()));
        customerRepository.save(customer);

        return customer.getUserId();
    }

    //프로필 업데이트 메서드
    // customerId 사용자 ID
    // request 프로필 업데이트 요청 DTO (ProfileRequest)
    // String 프로필 업데이트 결과 메시지

    @Transactional
    public ProfileResponseDTO updateProfile(ProfileRequestDTO request, Customer customer) {
        // 프로필 업데이트
        customer.updateProfile(request);
        customerRepository.save(customer);

        return new ProfileResponseDTO(customer);
    }

    private void insertPasswordHistory(Customer customer) {
        PasswordHistory passwordHistory = PasswordHistory.builder()
                .customer(customer)
                .password(customer.getPassword())
                .build();

        passwordHistoryRepository.save(passwordHistory);
    }
}
