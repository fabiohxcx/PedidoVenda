package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Column(name = "data_criacao", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;

	@Column(columnDefinition = "text")
	private String observacao;

	@NotNull
	@Column(name = "data_entrega", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataEntrega;

	@NotNull
	@Column(name = "valor_frete", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorFrete;

	@NotNull
	@Column(name = "valor_desconto", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorDesconto;

	@NotNull
	@Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorTotal;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private StatusPedido status;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "forma_pagamento", nullable = false, length = 20)
	private FormaPagamento formaPagamento;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "vendedor_id", nullable = false)
	private Usuario vendedor;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	@Embedded
	private EnderecoEntrega enderecoEntrega;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemPedido> itens = new ArrayList<ItemPedido>();

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataEntrega() {
		return this.dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public BigDecimal getValorFrete() {
		return this.valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public BigDecimal getValorDesconto() {
		return this.valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public StatusPedido getStatus() {
		return this.status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public FormaPagamento getFormaPagamento() {
		return this.formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Usuario getVendedor() {
		return this.vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public EnderecoEntrega getEnderecoEntrega() {
		return this.enderecoEntrega;
	}

	public void setEnderecoEntrega(EnderecoEntrega enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public List<ItemPedido> getItens() {
		return this.itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		return true;
	}

}
