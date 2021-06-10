package br.com.zup.orangetalents.proposta.cartao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.orangetalents.proposta.cartao.model.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, String> {

}
