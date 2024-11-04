package migueldelgg.com.github.infra.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
@Table(name = "restaurant")
public class RestaurantEntity implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "photo")
    private String photo;    

    public RestaurantEntity() {
    }

    public RestaurantEntity(UUID id, AddressEntity address, @NotBlank String name, String photo) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Restaurant{id=" + id + ", name='" + name + "', photo='" + photo + "'}";
    }
}   
