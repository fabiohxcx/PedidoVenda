package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Usuario;
import repository.UsuarioRepository;
import util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

	// @Inject
	private UsuarioRepository usuarios;

	public UsuarioConverter() {
		this.usuarios = CDIServiceLocator.getBean(UsuarioRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String str) {
		Usuario retorno = null;
		if (str != null) {
			Long id = new Long(str);
			retorno = this.usuarios.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		if (obj != null) {
			return ((Usuario) obj).getId() == null ? "" : ((Usuario) obj).getId().toString();
		}
		return "";
	}

}
