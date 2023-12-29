package com.hospital.real.repository;

import com.hospital.real.model.Doutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoutorRepository extends JpaRepository<Doutor, Long> {

    Doutor findByEmailAndCrm(String email, String crm);
    List<Doutor> findByEspecializacao(String especializacao);

    Doutor findByCrm( String crm);


    Doutor findByEmailAndCrmAndEspecializacao(String email, String crm, String especializacao);

    boolean existsByCrm(String crm);
}
