package br.com.zup.orangetalents.proposta.proposta.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.zup.orangetalents.proposta.proposta.model.Proposta;
import br.com.zup.orangetalents.proposta.proposta.model.StatusProposta;

public interface PropostaRepository extends JpaRepository<Proposta, UUID> {

	Optional<Proposta> findByDocumento(String documento);
	
	@Query("SELECT p FROM Proposta p WHERE p.status = 'ELEGIVEL' AND p.cartao IS NULL")
	List<Proposta> findByStatusIsElegivelAndCartaoIsNull();
	
	//Outra forma de fazer utilizando Derived Querys
	List<Proposta> findByStatusEqualsAndCartaoIsNull(StatusProposta status);
}
