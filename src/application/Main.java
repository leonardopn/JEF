package application;

import java.io.IOException;

import gui.controllers.ViewController;
import gui.controllers.ViewOperacoesController;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import model.collections.entities.Caixa;
import model.daos.DaoCliente;
import model.daos.DaoFuncionario;
import model.daos.DaoLogin;
import model.daos.DaoPacote;
import model.daos.DaoServico;
import model.daos.DaoTransacao;

public class Main extends Application {

	private static Stage stage;
	private static Scene login;
	private static Scene loginConfirmacao;
	private static Scene main;
	private static Stage stageLoginConfirmacao = new Stage();

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader splashLoader = new FXMLLoader(getClass().getResource("/gui/views/ViewSplashScreen.fxml"));
			Parent splashPane = splashLoader.load();

			// Cria a Janela do Splash
			// Define como transparente para que não apareça decoração de janela (maximizar,
			// minimizar)
			Stage splashStage = new Stage(StageStyle.TRANSPARENT);
			final Scene scene = new Scene(splashPane);
			scene.setFill(Color.TRANSPARENT); // Define que a cor do painel root seja transparente para que dê o efeito
												// de sombra
			splashStage.setScene(scene);

			// Cria o serviço para rodar alguma tarefa em background enquanto o splash é
			// mostrado (no caso somente um delay de 10s)
			Service<Boolean> splashService = new Service<Boolean>() {

				// Mostra o splash quando o serviço for iniciado
				@Override
				public void start() {
					super.start();
					splashStage.show(); // mostra a janela
				}

				// Simulação de uma tarefa pesada
				@Override
				protected Task<Boolean> createTask() {
					return new Task<Boolean>() {
						@Override
						protected Boolean call() throws Exception {
							DaoServico.carregaServicos();
							DaoServico.carregaCategoria();
							DaoCliente.carregaCliente();
							DaoFuncionario.carregaFuncionario();
							Caixa.setStatus(DaoTransacao.carregaCaixa());
							DaoPacote.carregaPacote();
							DaoPacote.carregaPacoteAssociado();
							DaoLogin.carregaLogin();
							return true;
						}
					};
				}

				// Quando a tarefa for finalizada fecha o splash e mostra a tela principal
				@Override
				protected void succeeded() {
					splashStage.close(); // Fecha o splash
					try {
						Parent fxmlMain = FXMLLoader.load(getClass().getResource("/gui/views/View.fxml"));
						main = new Scene(fxmlMain);
						stage = primaryStage;
						stage.getIcons().add(new Image(getClass().getResourceAsStream("/model/images/icon.png")));
						stage.setTitle("JEF - Início");
						Parent fxmlLogin = FXMLLoader.load(getClass().getResource("/gui/views/ViewLogin.fxml"));
						login = new Scene(fxmlLogin);
						stage.setScene(login);
						stage.centerOnScreen();
						stage.show();
						stage.setResizable(true);
						closeRequestProgram(); // Chama a tela principal
					} catch (Exception ex) {
					}
				}
			};

			splashService.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Scene getMain() {
		return main;
	}

	public static Stage getStage() {
		return stage;
	}

	public static Scene getScene() {
		return login;
	}

	public static Stage getStageLoginConfirmacao() {
		return stageLoginConfirmacao;
	}

	public static Scene getLoginConfirmacao() {
		return loginConfirmacao;
	}

	public static void closeRequestProgram() {
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				ViewController.getStageCliente().close();
				ViewController.getStageFuncionario().close();
				ViewController.getStageAgenda().close();
				ViewController.getStageSobre().close();
				ViewController.getStageCaixa().close();
				ViewController.getStagePacote().close();
				ViewController.getStagePagamento().close();
				ViewController.getStageOperacoes().close();
				ViewOperacoesController.getStageDespesa().close();
				ViewOperacoesController.getStageReceita().close();
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
