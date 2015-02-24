package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Pedido;
import repository.PedidoRepository;
import util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Pedido.class)
public class PedidoConverter implements Converter {

	// @Inject
	private PedidoRepository pedidos;

	public PedidoConverter() {
		this.pedidos = CDIServiceLocator.getBean(PedidoRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String str) {
		Pedido retorno = null;
		if (str != null) {
			Long id = new Long(str);
			retorno = this.pedidos.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		if (obj != null) {
			Pedido pedido = (Pedido) obj;
			return pedido.getId() == null ? null : pedido.getId().toString();
		}

		return "";
	}

}
