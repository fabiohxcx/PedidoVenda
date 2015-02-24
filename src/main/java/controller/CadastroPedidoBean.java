package controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Cliente;
import model.EnderecoEntrega;
import model.FormaPagamento;
import model.ItemPedido;
import model.Pedido;
import model.Produto;
import model.Usuario;

import org.apache.commons.lang3.StringUtils;

import repository.ClienteRepository;
import repository.ProdutoRepository;
import repository.UsuarioRepository;
import service.CadastroPedidoService;
import util.jsf.FacesUtil;
import validation.SKU;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Produces
	@PedidoEdicao
	private Pedido pedido;

	private List<Usuario> vendedores;
	@Inject
	private UsuarioRepository usuarios;
	@Inject
	private ClienteRepository clientes;
	@Inject
	private CadastroPedidoService cadastroPedidoService;
	@Inject
	private ProdutoRepository produtos;

	private Produto produtoLinhaEditavel;

	private String sku;

	public CadastroPedidoBean() {
		limpar();
	}

	public void pedidoAlterado(@Observes PedidoAlteradoEvent event) {
		this.pedido = event.getPedido();
	}

	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			this.vendedores = this.usuarios.vendedores();
			recalcularPedido();
			this.pedido.adicionarItemVazio();
		}

	}

	public List<Cliente> completarCliente(String nome) {
		return this.clientes.porNome(nome);
	}

	public List<Produto> completarProduto(String nome) {
		return this.produtos.porNome(nome);
	}

	public void carregarProdutoLinhaEditavel() {
		ItemPedido item = this.pedido.getItens().get(0);

		if (this.produtoLinhaEditavel != null) {
			if (exiteItemComProduto(this.produtoLinhaEditavel)) {
				FacesUtil.addErrorMessage("Já existe um item com o produto informado.");
			} else {
				item.setProduto(this.produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());

				this.pedido.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.sku = null;

				this.pedido.recalcularValorTotal();
			}
		}
	}

	public void atualizarQuantidadeItemPedido(ItemPedido item, int linha) {
		if (item.getQuantidade() < 1) {
			if (linha == 0) {
				item.setQuantidade(1);
			} else {
				getPedido().getItens().remove(linha);
			}
		}

		this.pedido.recalcularValorTotal();
	}

	private boolean exiteItemComProduto(Produto produto) {
		boolean existeItem = false;

		for (ItemPedido item : getPedido().getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}

		return existeItem;
	}

	public void carregarProdutoPorSku() {
		if (StringUtils.isNotEmpty(this.sku)) {
			this.produtoLinhaEditavel = this.produtos.porSku(this.sku);
			carregarProdutoLinhaEditavel();
		}
	}

	@SKU
	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}

	public Produto getProdutoLinhaEditavel() {
		return this.produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

	private void limpar() {
		this.pedido = new Pedido();
		this.pedido.setEnderecoEntrega(new EnderecoEntrega());
	}

	public void salvar() {
		this.pedido.removerItemVazio();

		try {
			this.pedido = this.cadastroPedidoService.salvar(this.pedido);

			FacesUtil.addInfoMessage("Pedido Salvo com sucesso.");
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}

	public void recalcularPedido() {
		if (this.pedido != null) {
			this.pedido.recalcularValorTotal();
		}
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Pedido getPedido() {
		return this.pedido;
	}

	public List<Usuario> getVendedores() {
		return this.vendedores;
	}

	public boolean isEditando() {
		return this.pedido.getId() != null;
	}

}