package util.jsf;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

public class FacesProducer {

	@Produces
	@RequestScoped
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	@Produces
	@RequestScoped
	public ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
	}

	// Estou usando o JavaEE7, e o HttpServletRequest nas novas versões já tem
	// um produtor padrão, ou seja, não precisamos implementar mais um produtor
	// pra ele.
	/*
	 * @Produces
	 * 
	 * @RequestScoped public HttpServletRequest getHttpServletRequest() { return
	 * ((HttpServletRequest) getExternalContext().getRequest()); }
	 */

	@Produces
	@RequestScoped
	public HttpServletResponse getHttpServletResponse() {
		return ((HttpServletResponse) getExternalContext().getResponse());
	}

}
