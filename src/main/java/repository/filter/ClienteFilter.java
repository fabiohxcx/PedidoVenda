package repository.filter;

import java.io.Serializable;

public class ClienteFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cpfCnpj;
	private String nome;

	public String getCpfCnpj() {
		return this.cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj.replace(".", "").replace("/", "").replace("-", "").trim();
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
