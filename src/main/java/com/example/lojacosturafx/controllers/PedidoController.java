package com.example.lojacosturafx.controllers;

import com.example.lojacosturafx.JavaFxApplication;
import com.example.lojacosturafx.entidades.Pedido;
import com.example.lojacosturafx.enums.Situacao;
import com.example.lojacosturafx.enums.TipoPagamento;
import com.example.lojacosturafx.servicos.OrcamentoService;
import com.example.lojacosturafx.servicos.PedidoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class PedidoController implements Initializable {
    private final JavaFxApplication javaFxApplication;
    private final PedidoService pedidoService;
    private URL url;
    private final OrcamentoService orcamentoService;

    @FXML
    public TableView<Pedido> tvPedidos;
    @FXML
    public TableColumn<Pedido, Long> tcId;
    @FXML
    public TableColumn<Pedido, Long> tcUsuario;
    @FXML
    public TableColumn<Pedido, Long> tcCliente;
    @FXML
    public TableColumn<Pedido, Date> tcDataCriacao;
    @FXML
    public TableColumn<Pedido, Double> tcValorTotal;
    @FXML
    public TableColumn<Pedido, Date> tcDataEntrega;
    @FXML
    public TableColumn<Pedido, Boolean> tcPago;
    @FXML
    public TableColumn<Pedido, Date> tcDataPagamento;
    @FXML
    public TableColumn<Pedido, String> tcTipoPagamento;
    @FXML
    public TableColumn<Pedido, String> tcSituacao;
    @FXML
    public TableColumn<Pedido, Long> tcOrcamento;
    @FXML
    public Button btnHome;
    @FXML
    public DatePicker dpDataEntrega;
    @FXML
    public CheckBox checkBoxPago;
    @FXML
    public DatePicker dpDataPago;
    @FXML
    public Button deletePedidoButton;
    @FXML
    public Button updatePedidoButton;
    @FXML
    public ComboBox<String> cbTipoPagamento;
    @FXML
    public ComboBox<String> cbSituacao;

    @FXML
    public void onCliqueHome(MouseEvent mouseEvent) {
        javaFxApplication.publicarContextoPagina("homeScreen");
    }

    @FXML
    public void onCliqueOrcamento(MouseEvent mouseEvent) {
        javaFxApplication.publicarContextoPagina("orcamento");
    }

    @FXML
    public void onCliquePago(ActionEvent mouseEvent) {
    }

    @FXML
    public void deletePedidoButton(ActionEvent event) throws Exception {
        Pedido pedido = pegarOrcamentoDaLinha();
        orcamentoService.desconfirmar(pedido.getOrcamento());
        pedidoService.delete(pedido);
        showPedido();
        initialize(url, null);
    }

    @FXML
    public void updatePedidoButton(ActionEvent event) throws Exception {
        Pedido pedido = pegarOrcamentoDaLinha();
        pedidoService.update(
                pedido.getId(),
                dpDataEntrega.getValue(),
                checkBoxPago.isSelected(),
                dpDataPago.getValue(),
                cbTipoPagamento.getValue(),
                cbSituacao.getValue()
        );
        showPedido();
        clearFTPedido();
        initialize(url, null);
    }

    @FXML
    public void onTipoPagamento(ActionEvent actionEvent) {
    }

    @FXML
    public void onSituacao(ActionEvent actionEvent) {
    }

    private void clearFTPedido() {
        dpDataEntrega.getEditor().clear();
        dpDataPago.getEditor().clear();
        checkBoxPago.setSelected(false);
        cbTipoPagamento.getSelectionModel().clearSelection();
        cbSituacao.getSelectionModel().clearSelection();
    }

    public void showPedido() {
        ObservableList<Pedido> lista = getPedidoList();

        tcId.setCellValueFactory(new PropertyValueFactory<Pedido, Long>("id"));
        tcDataEntrega.setCellValueFactory(new PropertyValueFactory<Pedido, Date>("dataEntrega"));
        tcPago.setCellValueFactory(new PropertyValueFactory<Pedido, Boolean>("pago"));
        tcDataPagamento.setCellValueFactory(new PropertyValueFactory<Pedido, Date>("dataPagamento"));
        tcTipoPagamento.setCellValueFactory(new PropertyValueFactory<Pedido, String>("tipoPagamento"));
        tcSituacao.setCellValueFactory(new PropertyValueFactory<Pedido, String>("situacao"));
        tcOrcamento.setCellValueFactory(new PropertyValueFactory<Pedido, Long>("orcamento"));

        tvPedidos.setItems(lista);
    }

    public ObservableList<Pedido> getPedidoList(){
        ObservableList<Pedido> listaPedido = FXCollections.observableArrayList();
        List<Pedido> pedidos = pedidoService.findAll();
        if (!pedidos.isEmpty()) {
            listaPedido.addAll(pedidos);
        }
        return listaPedido;
    }

    public Pedido pegarOrcamentoDaLinha() {
        return tvPedidos.getSelectionModel().getSelectedItem();
    }

    private void preencherCbSituacao() {
        Situacao[] situacoes = Situacao.values();
        ObservableList<String> situacoesList = FXCollections.observableArrayList();
        if (situacoes.length > 0) {
            for (Situacao situacao : situacoes) {
                situacoesList.add(situacao.name());
            }
        }
        cbSituacao.setItems(situacoesList);
    }

    private void preencherCbTipoDePagamento() {
        TipoPagamento[] tipoPagamentos = TipoPagamento.values();
        ObservableList<String> tipoPagamentoList = FXCollections.observableArrayList();
        tipoPagamentoList.add("");
        if (tipoPagamentos.length > 0) {
            for (TipoPagamento tipoPagamento : tipoPagamentos) {
                tipoPagamentoList.add(tipoPagamento.name());
            }
        }
        cbTipoPagamento.setItems(tipoPagamentoList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preencherCbTipoDePagamento();
        preencherCbSituacao();
        showPedido();
        this.url = url;
    }
}
