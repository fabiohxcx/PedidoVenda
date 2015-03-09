package controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Usuario;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

import repository.PedidoRepository;
import security.UsuarioLogado;
import security.UsuarioSistema;

@Named
@RequestScoped
public class GraficosPedidosCriadosBean {

	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");

	private LineChartModel model;
	private PieChartModel modelPie;

	@Inject
	private PedidoRepository pedidos;

	@Inject
	@UsuarioLogado
	private UsuarioSistema usuarioLogado;

	public void preRender() {
		this.model = new LineChartModel();
		this.model.setTitle("Pedidos criados");
		this.model.setAnimate(true);

		this.model.setLegendPosition("e");

		this.model.getAxes().put(AxisType.X, new CategoryAxis("Datas"));

		Axis yAxis = this.model.getAxis(AxisType.Y);
		yAxis.setLabel("Valores");
		yAxis.setMin(0);
		// yAxis.setMax(1100);

		adicionarSeries("Todos os pedidos", null);
		adicionarSeries("Meus pedidos", this.usuarioLogado.getUsuario());

		// ModelPie
		this.modelPie = new PieChartModel();

		Map<String, BigDecimal> valoresPorUsuario = this.pedidos.valoresTotaisPorVendedor();

		for (String usuario : valoresPorUsuario.keySet()) {
			this.modelPie.set(usuario, valoresPorUsuario.get(usuario));
		}

		this.modelPie.setTitle("Totais por Vendedor");
		this.modelPie.setLegendPosition("w");
		this.modelPie.setShowDataLabels(true);
		this.modelPie.setShadow(true);
	}

	private void adicionarSeries(String rotulo, Usuario criadoPor) {
		Map<Date, BigDecimal> valoresPorData = this.pedidos.valoresTotaisPorData(15, criadoPor);

		ChartSeries series = new ChartSeries();

		for (Date data : valoresPorData.keySet()) {
			series.set(DATE_FORMAT.format(data), valoresPorData.get(data));
		}

		series.setLabel(rotulo);

		this.model.addSeries(series);
	}

	public LineChartModel getModel() {
		return this.model;
	}

	public PieChartModel getModelPie() {
		return this.modelPie;
	}

}
