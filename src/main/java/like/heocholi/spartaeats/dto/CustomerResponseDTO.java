package like.heocholi.spartaeats.dto;

import like.heocholi.spartaeats.entity.Customer;
import lombok.Getter;

@Getter
public class CustomerResponseDTO {
    private String customerId;
    private String name;
    private String address;
    private String bio;
    private Long likedReviewCounts;
    private Long pickedStoreCounts;

    public CustomerResponseDTO(Customer customer, long likedReviewCounts, long pickedStoreCounts) {
        this.customerId = customer.getUserId();
        this.name = customer.getName();
        this.address = customer.getAddress();
        this.bio = customer.getBio();
        this.likedReviewCounts = likedReviewCounts;
        this.pickedStoreCounts = pickedStoreCounts;
    }
}
