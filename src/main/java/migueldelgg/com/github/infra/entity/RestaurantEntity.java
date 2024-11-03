package migueldelgg.com.github.infra.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "photo")
    private String photo;    

    public RestaurantEntity() {
    }

    public RestaurantEntity(UUID id, @NotBlank String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Restaurant{id=" + id + ", name='" + name + "', photo='" + photo + "'}";
    }
}   
