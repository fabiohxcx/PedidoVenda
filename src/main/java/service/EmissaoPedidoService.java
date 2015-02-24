package service;

import java.io.Serializable;

import javax.inject.Inject;

import model.Pedido;
import model.StatusPedido;
import repository.PedidoRepository;
import util.jpa.Transactional;

public class EmissaoPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroPedidoService cadastroPedidoService;

	@Inject
	private PedidoRepository pedidos;

	@Inject
	private EstoqueService estoqueService;

	@Transactional
	public Pedido emitir(Pedido pedido) {

		this.cadastroPedidoService.salvar(pedido);

		if (pedido.isNaoEmissivel()) {
			throw new NegocioException("Pedido não pode ser emitido com status " + pedido.getStatus().getDescricao()
					+ ".");
		}

		this.estoqueService.baixarItensEstoque(pedido);

		pedido.setStatus(StatusPedido.EMITIDO);

		pedido = this.pedidos.guardar(pedido);

		return pedido;
	}
}
