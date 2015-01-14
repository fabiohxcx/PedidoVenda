package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Categoria;
import repository.CategoriaRepository;
import util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {

	// @Inject
	private CategoriaRepository categorias;

	public CategoriaConverter() {
		this.categorias = CDIServiceLocator.getBean(CategoriaRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String str) {
		Categoria retorno = null;
		if (str != null) {
			Long id = new Long(str);
			retorno = this.categorias.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		if (obj != null) {
			return ((Categoria) obj).getId() == null ? "" : ((Categoria) obj).getId().toString();
		}
		return "";
	}

}
