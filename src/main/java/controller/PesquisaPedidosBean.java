package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Pedido;
import model.StatusPedido;
import repository.PedidoRepository;
import repository.filter.PedidoFilter;

@Named
@ViewScoped
public class PesquisaPedidosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private PedidoFilter filtro;

	private List<Pedido> pedidosFiltrados;

	@Inject
	private PedidoRepository pedidos;

	public PesquisaPedidosBean() {
		this.filtro = new PedidoFilter();
		this.pedidosFiltrados = new ArrayList<Pedido>();
	}

	public void pesquisar() {
		this.pedidosFiltrados = this.pedidos.filtrados(this.filtro);
	}

	public List<Pedido> getPedidosFiltrados() {
		return this.pedidosFiltrados;
	}

	public PedidoFilter getFiltro() {
		return this.filtro;
	}

	public void setFiltro(PedidoFilter filtro) {
		this.filtro = filtro;
	}

	public PedidoRepository getPedidos() {
		return this.pedidos;
	}

	public void setPedidos(PedidoRepository pedidos) {
		this.pedidos = pedidos;
	}

	public void setPedidosFiltrados(List<Pedido> pedidosFiltrados) {
		this.pedidosFiltrados = pedidosFiltrados;
	}

	public StatusPedido[] getStatuses() {
		return StatusPedido.values();
	}

}