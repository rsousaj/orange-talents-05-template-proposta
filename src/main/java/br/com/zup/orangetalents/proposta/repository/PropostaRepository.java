package br.com.zup.orangetalents.proposta.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.zup.orangetalents.proposta.model.Proposta;

public interface PropostaRepository extends JpaRepository<Proposta, UUID> {

	Optional<Proposta> findByDocumento(String documento);
	
	@Query("SELECT p FROM Proposta p WHERE p.status = 'ELEGIVEL' AND p.cartao IS NULL")
	List<Proposta> findByStatusIsElegivelAndCartaoIsNull();
}
