package service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import model.Pedido;
import model.StatusPedido;
import repository.PedidoRepository;
import util.jpa.Transactional;

public class CadastroPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	PedidoRepository pedidos;

	@Transactional
	public Pedido salvar(Pedido pedido) {
		if (pedido.isNovo()) {
			pedido.setDataCriacao(new Date());
			pedido.setStatus(StatusPedido.ORCAMENTO);
		}

		pedido.recalcularValorTotal();

		if (pedido.isNaoAlteravel()) {
			throw new NegocioException("O pedido não pode ser alterado no status " + pedido.getStatus().getDescricao()
					+ ".");
		}

		if (pedido.getItens().isEmpty()) {
			throw new NegocioException("O pedido deve possuir pelo menos um item.");
		}

		if (pedido.isValorTotalNegativo()) {
			throw new NegocioException("Valor total do pedido não pode ser negativo.");
		}

		pedido = this.pedidos.guardar(pedido);

		return pedido;

	}
}
