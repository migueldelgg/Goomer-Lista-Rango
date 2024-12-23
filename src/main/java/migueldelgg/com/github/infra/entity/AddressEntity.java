package migueldelgg.com.github.infra.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne(mappedBy = "address")
    private RestaurantEntity restaurant;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "address_complement")
    private String addressComplement;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "zip_code")
    private String zipcode;

    public AddressEntity() {}

    public AddressEntity(UUID id, RestaurantEntity restaurant, String address, String addressComplement,
                        String city, String state, String country, String zipcode) {
        this.id = id;
        this.restaurant = restaurant;
        this.address = address;
        this.addressComplement = addressComplement;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
    }

}
