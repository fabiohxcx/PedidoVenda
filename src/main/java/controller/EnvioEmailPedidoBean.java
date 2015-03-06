package controller;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Cliente;
import model.Pedido;

import org.apache.velocity.tools.generic.NumberTool;

import util.jsf.FacesUtil;
import util.mail.Mailer;

import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@RequestScoped
public class EnvioEmailPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Mailer mailer;

	@Inject
	@PedidoEdicao
	private Pedido pedido;

	@Inject
	@ClienteEdicao
	private Cliente cliente;

	public void enviarPedido() {
		MailMessage message = this.mailer.novaMensagem();

		message.from("hideki.fabio@gmail.com").to(this.pedido.getCliente().getEmail())
				.subject("Pedido " + this.pedido.getId())
				.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/pedido.template")))
				.put("pedido", this.pedido).put("numberTool", new NumberTool()).put("locale", new Locale("pt", "BR"))
				.send();

		FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso.");
	}

	public void enviarDadosCliente() {
		MailMessage message = this.mailer.novaMensagem();
		message.from("hideki.fabio@gmail.com").to(this.cliente.getEmail())
				.subject("Dados cliente:  " + this.cliente.getNome())
				.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/cliente.template")))
				.put("cliente", this.cliente).send();

		FacesUtil.addInfoMessage("Dados enviado por e-mail com sucesso.");
	}

}
