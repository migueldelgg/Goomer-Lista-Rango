package migueldelgg.com.github.infra.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import migueldelgg.com.github.infra.entity.AddressEntity;

@Repository
public interface AddresEntityRepository extends JpaRepository<AddressEntity, UUID>{
}
