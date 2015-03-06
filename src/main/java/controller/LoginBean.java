package controller;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.jsf.FacesUtil;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletRequest request;

	@Inject
	private HttpServletResponse response;

	public void login() throws ServletException, IOException {
		RequestDispatcher dispatcher = this.request.getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward(this.request, this.response);

		this.facesContext.responseComplete();
	}

	public void preRender() {
		if ("true".equals(this.request.getParameter("invalid"))) {
			FacesUtil.addErrorMessage("Usuário ou senha inválido.");
		}
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
