package br.com.zup.orangetalents.proposta.cartao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.zup.orangetalents.proposta.cartao.model.Carteira;

public interface CarteiraRepository extends JpaRepository<Carteira, Long>{

	Optional<Carteira> findByCartao_IdAndNomeCarteira(String idCartao, String nomeCarteira);
}
