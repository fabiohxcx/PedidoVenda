package controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import util.jsf.FacesUtil;
import util.report.ExecutorRelatorio;

@Named
@RequestScoped
public class RelatorioProdutosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal valorUnitarioDe;
	private BigDecimal valorUnitarioAte;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("valor_de", this.valorUnitarioDe);
		parametros.put("valor_ate", this.valorUnitarioAte);

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/relatorio_produtos.jasper",
				this.response, parametros, "Produtos.pdf");

		Session session = this.manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			this.facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}

	public BigDecimal getValorUnitarioDe() {
		return this.valorUnitarioDe;
	}

	public void setValorUnitarioDe(BigDecimal valorUnitarioDe) {
		this.valorUnitarioDe = valorUnitarioDe;
	}

	public BigDecimal getValorUnitarioAte() {
		return this.valorUnitarioAte;
	}

	public void setValorUnitarioAte(BigDecimal valorUnitarioAte) {
		this.valorUnitarioAte = valorUnitarioAte;
	}

}