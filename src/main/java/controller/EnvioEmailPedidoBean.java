package controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Pedido;
import util.jsf.FacesUtil;
import util.mail.Mailer;

import com.outjected.email.api.MailMessage;

@Named
@RequestScoped
public class EnvioEmailPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Mailer mailer;

	@Inject
	@PedidoEdicao
	private Pedido pedido;

	public void enviarPedido() {
		MailMessage message = this.mailer.novaMensagem();

		message.to(new String[] { this.pedido.getCliente().getEmail() }).subject("Pedido " + this.pedido.getId())
				.bodyHtml("<strong>Valor total:</strong> " + this.pedido.getValorTotal()).send();

		FacesUtil.addInfoMessage("Pedido enviadopor e-mail com sucesso.");
	}

}
