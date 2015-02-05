package model;

public enum TipoPessoa {
	FISICA("Física"), JURIDICA("Jurídica");

	TipoPessoa(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
