package FxApp.CrazyZoo.panel;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

public class SshPanel extends GridPane {

	//Pongo el textarea compartido en la clase para que las 
	//Funciones puedan usuarlo y escribir en él el resultado
	//de los comandos
	private TextArea outputArea;

	//Creamos un objeto de tipo sshclient para conectarnos con un servidor ssh y ejecutar comandos
	private SSHClient sshClient;

	//Los comandos ssh deben de ejecutarse en un hilo (proceso) distinto al de la app principal
	//Usaremos la clase ExecutorService para crear hilos de ejecucion de los comandos
	//Esto es necesario porque nos tenemos que quedar esperando a la respuesta del comando
	//y recogerla para mostrarla, siempre que hay un tiempo de espera hay que crear un nuevo hilo
	//Para no bloquear la aplicacion principal
	private final ExecutorService executor = Executors.newCachedThreadPool();

	public SshPanel() {

		//Ponemos el espaciado
		this.setVgap(10);
		this.setHgap(10);
		this.setPadding(new Insets(10));

		Label hostLabel = new Label("Host:");
		TextField hostField = new TextField();
		hostField.setPromptText("Hostname o ip");

		Label portLabel = new Label("Port:");
		TextField portField = new TextField("22");

		Label userLabel = new Label("UserName:");
		TextField userField = new TextField();
		userField.setPromptText("User Name");

		Label passLabel = new Label("Password:");
		PasswordField passField = new PasswordField();
		passField.setPromptText("Password");

		Button connectButton = new Button("Conectar");
		Button disconnectButton = new Button("Desconectar");
		disconnectButton.setDisable(true);

		outputArea = new TextArea();
		outputArea.setEditable(false);

		TextField commandField = new TextField();
		commandField.setPromptText("Introduce el comando..");

		//Por defecto el boton estara deshabilitado hasta que nos conectemos
		Button executeButton = new Button("Execute");
		executeButton.setDisable(true);

		this.add(hostLabel, 0, 0);
		this.add(hostField, 1, 0);
		this.add(portLabel, 0, 1);
		this.add(portField, 1, 1);
		this.add(userLabel, 0, 2);
		this.add(userField, 1, 2);
		this.add(passLabel, 0, 3);
		this.add(passField, 1, 3);
		this.add(connectButton, 0, 4);
		this.add(disconnectButton, 1, 4);

		//La parte de terminal de comandos la metemos en un vBox
		VBox commandBox = new VBox(10);
		commandBox.getChildren().addAll(outputArea, new Label("Comando:"), commandField, executeButton);

		//Añadimos el vBox al final del grid
		this.add(commandBox, 0, 5, 2, 1);

		/****************************+
		 * EVENTOS
		 ************************/
		connectButton.setOnAction(e -> {

			executor.execute(() -> connectSSH(hostField.getText(), Integer.parseInt(portField.getText()),
					userField.getText(), passField.getText()));

			//Deshabilitamos el boton de conectar, habilitamos el de desconectar y el de ejecutar comando
			connectButton.setDisable(true);
			disconnectButton.setDisable(false);
			executeButton.setDisable(false);

		});

		disconnectButton.setOnAction(e -> {

			executor.execute(() -> disconnectSSH());
			outputArea.clear();

			connectButton.setDisable(false);
			disconnectButton.setDisable(true);
			executeButton.setDisable(true);

		});

		executeButton.setOnAction(e -> {

			String command = commandField.getText();

			if (!command.isEmpty()) {
				executor.execute(() -> executeCommand(command));
				commandField.clear();
			}

		});

	}

	private void connectSSH(String host, int port, String username, String password) {

		try {
			appendOutput("Conectando a " + host + "Puerto " + port);

			sshClient = new SSHClient();

			sshClient.addHostKeyVerifier(new PromiscuousVerifier());

			//Nos conectamos al servidor
			sshClient.connect(host, port);
			sshClient.authPassword(username, password);

			appendOutput("Conectando Correctamente! ");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			appendOutput("Error Al conectar! ");
			e.printStackTrace();
		}

	}

	private void disconnectSSH() {

		try {
			if (sshClient != null && sshClient.isConnected()) {
				sshClient.disconnect();
				appendOutput("Desconectando Correctamente! ");

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			appendOutput("Error Al desconectar! ");
			e.printStackTrace();
		}
	}

	private void executeCommand(String Command) {

		//Si no estamos conectados no podemos ejecutar el comando, nos salimos
		if (sshClient == null || !sshClient.isConnected()) {
			appendOutput("No connected to server");
			return;
		}

		try {
			//Recogemos la sesion ssh
			Session session = sshClient.startSession();
			//Ejecutamos el comando y recogemos el objeto para recibir la respuesta cuando esta llegue
			Command cmd = session.exec(Command);

			//Para recoger la salida del comando leemos del inputstream con la funcion readFully
			String output = net.schmizz.sshj.common.IOUtils.readFully(cmd.getInputStream()).toString();
			String error = net.schmizz.sshj.common.IOUtils.readFully(cmd.getErrorStream()).toString();

			cmd.join();

			//Si hay salida de comando la mostramos por el textarea
			if (!output.isEmpty()) {
				appendOutput(output);
			}

			//Si hay salida de error la mostramos por el textarea
			if (!error.isEmpty()) {
				appendOutput("Error: " + error);
			}

		} catch (Exception e) {
			appendOutput("Error executing the command:" + e.getMessage());
			e.printStackTrace();
		}

	}

	private void appendOutput(String text) {
		javafx.application.Platform.runLater(() -> {
			outputArea.appendText(text + "\n");
		});
	}

}
