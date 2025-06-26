package edu.esoft.com.repository;

import edu.esoft.com.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
    boolean existsByNameIgnoreCase(String name);
}
