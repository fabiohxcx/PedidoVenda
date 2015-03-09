package model.vo;

import java.math.BigDecimal;

public class UsuarioValor {
	private String usuario;
	private BigDecimal valor;

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
