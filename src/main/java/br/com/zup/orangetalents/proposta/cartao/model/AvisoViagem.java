package br.com.zup.orangetalents.proposta.cartao.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class AvisoViagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false)
	@NotNull @Valid
	private Cartao cartao;
	
	@NotBlank
	private String destino;
	
	@NotNull @Future
	private LocalDate dataTermino;
	
	@Column(name = "ip")
	private String ipRequisicao;
	
	@Column(name = "user_agent")
	private String userAgentRequisicao;
	
	private LocalDateTime instanteAviso = LocalDateTime.now();

	@Deprecated
	public AvisoViagem() { }
	
	public AvisoViagem(@NotNull @Valid Cartao cartao, @NotBlank String destino,
			@NotNull @Future LocalDate dataTermino, String ipRequisicao, String userAgentRequisicao) {
		this.cartao = cartao;
		this.destino = destino;
		this.dataTermino = dataTermino;
		this.ipRequisicao = ipRequisicao;
		this.userAgentRequisicao = userAgentRequisicao;
	}
}
