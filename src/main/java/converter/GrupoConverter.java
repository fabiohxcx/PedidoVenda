package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Grupo;
import repository.filter.GrupoRepository;
import util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Grupo.class)
public class GrupoConverter implements Converter {

	private GrupoRepository grupos;

	public GrupoConverter() {
		this.grupos = CDIServiceLocator.getBean(GrupoRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String str) {
		Grupo retorno = null;
		if (str != null) {
			Long id = new Long(str);
			retorno = this.grupos.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		if (obj != null) {
			return ((Grupo) obj).getId() == null ? "" : ((Grupo) obj).getId().toString();
		}
		return "";
	}

}
