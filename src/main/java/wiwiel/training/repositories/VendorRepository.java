package wiwiel.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wiwiel.training.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
