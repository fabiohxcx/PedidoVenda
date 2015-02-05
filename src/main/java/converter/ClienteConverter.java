package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Cliente;
import repository.ClienteRepository;
import util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter {

	// @Inject
	private ClienteRepository clientes;

	public ClienteConverter() {
		this.clientes = CDIServiceLocator.getBean(ClienteRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String str) {
		Cliente retorno = null;
		if (str != null) {
			Long id = new Long(str);
			retorno = this.clientes.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		if (obj != null) {
			return ((Cliente) obj).getId() == null ? "" : ((Cliente) obj).getId().toString();
		}
		return "";
	}

}
