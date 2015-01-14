package controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class PesquisaUsuariosBean {

	private List<Integer> usuariosFiltrados;

	public PesquisaUsuariosBean() {
		usuariosFiltrados = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			usuariosFiltrados.add(i);
		}
	}

	public List<Integer> getUsuariosFiltrados() {
		return usuariosFiltrados;
	}

}