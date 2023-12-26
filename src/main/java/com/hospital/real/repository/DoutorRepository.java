package com.hospital.real.repository;

import com.hospital.real.model.Doutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoutorRepository extends JpaRepository<Doutor, Long> {

}
