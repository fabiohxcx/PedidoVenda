package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Produto;
import repository.ProdutoRepository;
import util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {

	// @Inject
	private ProdutoRepository produtos;

	public ProdutoConverter() {
		this.produtos = CDIServiceLocator.getBean(ProdutoRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String str) {
		Produto retorno = null;
		if (str != null) {
			Long id = new Long(str);
			retorno = this.produtos.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		if (obj != null) {
			Produto produto = (Produto) obj;
			return produto.getId() == null ? null : produto.getId().toString();
		}

		return "";
	}

}
