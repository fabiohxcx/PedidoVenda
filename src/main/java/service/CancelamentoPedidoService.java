package service;

import java.io.Serializable;

import javax.inject.Inject;

import model.Pedido;
import model.StatusPedido;
import repository.PedidoRepository;
import util.jpa.Transactional;

public class CancelamentoPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository pedidos;

	@Inject
	private EstoqueService estoqueService;

	@Transactional
	public Pedido cancelar(Pedido pedido) {
		pedido = this.pedidos.porId(pedido.getId());

		if (pedido.isNaoCancelavel()) {
			throw new NegocioException("Pedido não pode ser cancelado com status " + pedido.getStatus().getDescricao()
					+ ".");
		}

		if (pedido.isEmitido()) {
			this.estoqueService.retornarItensEstoque(pedido);
		}

		pedido.setStatus(StatusPedido.CANCELADO);

		pedido = this.pedidos.guardar(pedido);

		return pedido;
	}
}
