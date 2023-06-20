package com.example.lojacosturafx.controllers;

import com.example.lojacosturafx.JavaFxApplication;
import com.example.lojacosturafx.entidades.*;
import com.example.lojacosturafx.enums.Tamanho;
import com.example.lojacosturafx.servicos.*;
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
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrcamentoController implements Initializable {

    Double valorItem = 0d;
    Double valorAdicionais = 0d;
    Double valorTotal = 0d;
    DecimalFormat formatter = new DecimalFormat("#0.00");
    private final JavaFxApplication javaFxApplication;
    private final ClienteService clienteService;
    private final ClienteController clienteController;
    private final OrcamentoService orcamentoService;
    private URL url;
    private final ItemPedidoService itemPedidoService;
    private final AdicionalService adicionalService;
    private final PedidoService pedidoService;
    private final CorService corService;
    private final ModeloController modeloController;
    private final PecaController pecaController;
    private final CorController corController;
    private final TecidoController tecidoController;
    private final PecaService pecaService;
    private final TecidoService tecidoService;
    private final ModeloService modeloService;

    @FXML
    public Label lblValorItem;

    @FXML
    public Label lblUsuario;

    @FXML
    public Button btnPedidos;

    @FXML
    public TableView<ItemPedido> tvItens;

    @FXML
    public TableView<Adicional> tvAdicionais;

    @FXML
    public ComboBox<String> cbClientes;

    @FXML
    public Button btnNovoCliente;

    @FXML
    public Button btnInserirOrcamento;

    @FXML
    public Button btnAtualizarOrcamento;

    @FXML
    public ComboBox<String> cbModelos;

    @FXML
    public ComboBox<String> cbTecido;

    @FXML
    public TableView<Orcamento> tvOrcamentos;

    @FXML
    public TableColumn<Orcamento, Long> tcIdOrcamento;

    @FXML
    public TableColumn<Orcamento, Long> tcUsuarioOrcamento;

    @FXML
    public TableColumn<Orcamento, Long> tcClienteOrcamento;

    @FXML
    public TableColumn<Orcamento, Date> tcDataCriacaoOrcamento;

    @FXML
    public TableColumn<Orcamento, Boolean> tcConfirmadoOrcamento;

    @FXML
    public Button btnInserirItem;

    @FXML
    public Button btnAtualizarItem;

    @FXML
    public TableColumn<ItemPedido, Long> tcIdItem;

    @FXML
    public TableColumn<ItemPedido, Long> tcPecaItem;

    @FXML
    public TableColumn<ItemPedido, Tamanho> tcTamanhoItem;

    @FXML
    public TableColumn<ItemPedido, Long> tcModeloItem;

    @FXML
    public TableColumn<ItemPedido, Long> tcTecidoItem;

    @FXML
    public TableColumn<ItemPedido, Long> tcCorItem;

    @FXML
    public TableColumn<ItemPedido, Double> tcValorItem;

    @FXML
    public ComboBox<String> cbTamanhos;

    @FXML
    public Button btnDeletarOrcamento;

    @FXML
    public Button btnDeletarItem;

    @FXML
    public TableColumn<Adicional, Long> tcIdAdicional;

    @FXML
    public TableColumn<Adicional, String> tcNomeAdicional;

    @FXML
    public TableColumn<Adicional, Double> tcMultiplicadorAdicional;

    @FXML
    public Button btnInserirAdicional;

    @FXML
    public Button btnAtualizarAdicional;

    @FXML
    public Button btnDeletarAdicional;

    @FXML
    public Label lblValorTotal;

    @FXML
    public Button btnConfirmar;

    @FXML
    public TextArea taObservacoes;

    @FXML
    private Button btnHome;

    @FXML
    private ComboBox<String> cbCores;

    @FXML
    private ComboBox<String> cbPecas;

    @FXML
    private Label lblAdicional;

    @FXML
    private Label lblCliente;

    @FXML
    private Label lblCor;

    @FXML
    private Label lblModel;

    @FXML
    private Label lblMultiplicador;

    @FXML
    private Label lblPeca;

    @FXML
    private Label lblTamanho;

    @FXML
    private Label lblTituloItem;

    @FXML
    private Label lblTituloOrcamento;

    @FXML
    private TextField tfNomeAdicional;

    @FXML
    private TextField tfMultiplicadorAdicional;

    @FXML
    void onClickCliente(ActionEvent event) {
        javaFxApplication.publicarContextoPagina("clientes");
    }

    @FXML
    public void onClickHome(ActionEvent event) {
        javaFxApplication.publicarContextoPagina("homeScreen");
    }

    @FXML
    public void onCliqueInserirOrcamento(ActionEvent event) {
        orcamentoService.create(new Orcamento(
                LogIOService.getLoggedID(),
                clienteService.findByNome(cbClientes.getValue()).getId(),
                taObservacoes.getText())
        );
        showOrcamento();
        clearFTOrcamento();
    }

    @FXML
    public void onCliqueAtualizarOrcamento(ActionEvent event) throws Exception {
        Orcamento orcamento = pegarOrcamentoDaLinha();
        orcamentoService.update(
                orcamento.getId(),
                new Random().nextLong(),
                clienteService.findByNome(cbClientes.getValue()).getId(),
                taObservacoes.getText()
        );
        showOrcamento();
        clearFTOrcamento();
    }

    @FXML
    public void onCliqueDeletarOrcamento(ActionEvent event) {
        Orcamento orcamento = pegarOrcamentoDaLinha();
        orcamentoService.delete(orcamento);
        showOrcamento();
        clearFTOrcamento();
        lblValorTotal.setText("0.0");
    }

    @FXML
    public void onCliqueInserirItem(ActionEvent event) throws Exception {
        Orcamento orcamento = pegarOrcamentoDaLinha();
        ItemPedido itemPedido = new ItemPedido(
                pecaService.findByNome(cbPecas.getValue()).getId(), // peca
                cbTamanhos.getValue(),
                modeloService.findByNome(cbModelos.getValue()).getId(), // modelo
                tecidoService.findByNome(cbTecido.getValue()).getId(), // tecido
                corService.findByHexCode(cbCores.getValue()).getId(), // cor
                Double.parseDouble(lblValorItem.getText())
        );
        itemPedidoService.create(itemPedido);
        orcamentoService.addItem(orcamento.getId(), itemPedido);
        showOrcamento();
        showItens();
        clearFtItens();
        clearFtAdicional();
        initialize(url, null);
    }

    private void selecionaOrcamento(Long id) {
        tvOrcamentos.requestFocus();
        tvOrcamentos.getSelectionModel().select(id.intValue() - 1);
        tvOrcamentos.getFocusModel().focus(id.intValue() - 1);
        onCliqueTvOrcamentos(null);
    }

    private void clearFtItens() {
        lblValorItem.setText("");
        valorItem = 0.0d;
        cbPecas.setItems(null);
        cbCores.getSelectionModel().clearSelection();
        cbTecido.getSelectionModel().clearSelection();
        cbModelos.getSelectionModel().clearSelection();
        cbPecas.getSelectionModel().clearSelection();
        cbTamanhos.getSelectionModel().clearSelection();
        cbClientes.getSelectionModel().clearSelection();
    }

    @FXML
    public void onCliqueAtualizarItem(ActionEvent event) throws Exception {
        ItemPedido itemPedido = pegarItemDaLinha();
        itemPedidoService.update(
                itemPedido.getId(),
                pecaService.findByNome(cbPecas.getValue()).getId(), // peca
                cbTamanhos.getValue(),
                modeloService.findByNome(cbModelos.getValue()).getId(), // modelo
                tecidoService.findByNome(cbTecido.getValue()).getId(), // tecido
                corService.findByHexCode(cbCores.getValue()).getId(), // cor
                Double.parseDouble(lblValorItem.getText())
        );
        tvItens.setItems(null);
        initialize(url, null);
        clearFtItens();
        clearFtAdicional();
    }

    @FXML
    public void onCliqueDeletarItem(ActionEvent event) {
        ItemPedido itempedido = pegarItemDaLinha();
        itemPedidoService.delete(itempedido);
        tvItens.setItems(null);
        initialize(url, null);
        clearFtItens();
        clearFtAdicional();
    }

    @FXML
    public void onCliqueInserirAdicional(ActionEvent event) throws Exception {
        ItemPedido itemPedido = pegarItemDaLinha();
        Adicional adicional = new Adicional(tfNomeAdicional.getText(), Double.parseDouble(tfMultiplicadorAdicional.getText()));
        adicionalService.create(adicional);
        itemPedidoService.addAdicional(itemPedido.getId(), adicional);
        showItens();
        showAdicionais();
        clearFtAdicional();
        initialize(url, null);
    }

    private void clearFtAdicional() {
        tvAdicionais.setItems(null);
        tfNomeAdicional.setText("");
        tfMultiplicadorAdicional.setText("");
    }

    @FXML
    public void onCliqueAtualizarAdicional(ActionEvent event) throws Exception {
        Adicional adicional = pegarAdicionalDaLinha();
        adicionalService.update(adicional.getId(), tfNomeAdicional.getId(), Double.parseDouble(tfMultiplicadorAdicional.getText()));
        tvAdicionais.setItems(null);
        initialize(url, null);
        clearFtAdicional();
    }

    @FXML
    public void onCliqueDeletarAdicional(ActionEvent event) {
        Adicional adicional = pegarAdicionalDaLinha();
        adicionalService.delete(adicional.getId());
        tvAdicionais.setItems(null);
        initialize(url, null);
        clearFtAdicional();
    }

    @FXML
    public void onCliqueConfirmar(ActionEvent event) throws Exception {
        Orcamento orcamento = pegarOrcamentoDaLinha();
        if (orcamento != null && orcamento.getValorTotal() > 0.0d) {
            orcamentoService.confirma(orcamento.getId());
            Pedido pedido = new Pedido(orcamento.getId());
            pedidoService.create(pedido);
            javaFxApplication.publicarContextoPagina("pedidos");
        }
    }

    @FXML
    public void onCliqueTvOrcamentos(MouseEvent mouseEvent) {
        Orcamento orcamento = pegarOrcamentoDaLinha();
        if (orcamento != null) {
            orcamento.setValorTotal(valorTotal());
            taObservacoes.setText(orcamento.getObservacoes());
            lblValorTotal.setText(formatter.format(valorTotal()));
            showItens();
            clearFtAdicional();
        }
    }

    @FXML
    public void onCliqueTvItens(MouseEvent mouseEvent) {
        showAdicionais();
        valorAdicionais = 0d;
    }

    @FXML
    public void onCliquePedidos(ActionEvent event) {
        javaFxApplication.publicarContextoPagina("pedidos");
    }

    @FXML
    public void onActionPeca(ActionEvent event) throws Exception {
        if (calculaValorItem() != null) {
            lblValorItem.setText(formatter.format(calculaValorItem()));
        }
    }

    @FXML
    public void onActionModelo(ActionEvent event) throws Exception {
        if (calculaValorItem() != null) {
            lblValorItem.setText(formatter.format(calculaValorItem()));
        }
    }

    @FXML
    public void onActionTamanho(ActionEvent event) throws Exception {
        if (calculaValorItem() != null) {
            lblValorItem.setText(formatter.format(calculaValorItem()));
        }
    }

    @FXML
    public void onActionTecido(ActionEvent event) throws Exception {
        if (calculaValorItem() != null) {
            lblValorItem.setText(formatter.format(calculaValorItem()));
        }
    }

    public Double calculaValorItem() {
        Double valor = (Double) 0.0d;
        if (lblValorItem.getText() != null && !lblValorItem.getText().isEmpty()) {
            valor = (Double) Double.parseDouble(lblValorItem.getText());
        }
        boolean entrou = false;
        if (cbPecas.getValue() != null ) {
            entrou = true;
            valorItem = calculoTecido() + calculoPeca() * (1  + calculoModelo() + calculoTamanho()) + calculoDosAdicionais(calculoPeca());
        }
        if (!entrou) valorItem = valor;
        return valorItem;
    }

    private Double calculoTecido() {
        if (cbTecido.getValue() != null) {
            Tecido tecido = tecidoService.findByNome(cbTecido.getValue());
            return tecido != null ? tecido.getPrecoMetro() : 0.0d;
        }
        return 0.0d;
    }

    private Double calculoPeca() {
        if (cbPecas.getValue() != null) {
            Peca peca = pecaService.findByNome(cbPecas.getValue());
            return peca != null ? peca.getPrecoBase() : 0.0d;
        }
        return 0.0d;
    }

    private Double calculoTamanho() {
        if (cbTamanhos.getValue() != null) {
            Tamanho tamanho = Tamanho.valueOf(cbTamanhos.getValue());
            return tamanho.getMultiplicador();
        }
        return 0.0d;
    }

    private Double calculoModelo() {
        if (cbModelos.getValue() != null) {
            Modelo modelo = modeloService.findByNome(cbModelos.getValue());
            return modelo != null ? modelo.getMultiplicador() : 0.0d;
        }
        return 0.0d;
    }

    private Double calculoDosAdicionais(Double precoBase) {
        ItemPedido itemPedido = pegarItemDaLinha();
        valorAdicionais = 0.0d;
        if (itemPedido != null && itemPedido.getAdicionais() != null && itemPedido.getAdicionais().size() > 0) {
            for (var adicional : itemPedido.getAdicionais()) {
                valorAdicionais += adicional.getMultiplicador() * precoBase;
            }
        }
        return valorAdicionais;
    }

    private void showItens() {
        ObservableList<ItemPedido> listaItens = getItemOrcamentoList();

        tcIdItem.setCellValueFactory(new PropertyValueFactory<ItemPedido, Long>("id"));
        tcPecaItem.setCellValueFactory(new PropertyValueFactory<ItemPedido, Long>("pecaId"));
        tcTamanhoItem.setCellValueFactory(new PropertyValueFactory<ItemPedido, Tamanho>("tamanho"));
        tcModeloItem.setCellValueFactory(new PropertyValueFactory<ItemPedido, Long>("modeloId"));
        tcTecidoItem.setCellValueFactory(new PropertyValueFactory<ItemPedido, Long>("tecidoId"));
        tcCorItem.setCellValueFactory(new PropertyValueFactory<ItemPedido, Long>("corId"));
        tcValorItem.setCellValueFactory(new PropertyValueFactory<ItemPedido, Double>("valorItem"));

        tvItens.setItems(listaItens);
    }

    public ObservableList<ItemPedido> getItemOrcamentoList(){
        Orcamento orcamento = pegarOrcamentoDaLinha();
        ObservableList<ItemPedido> itemOrcamentoList = FXCollections.observableArrayList();
        if (orcamento != null) {
            List<ItemPedido> itens = new ArrayList<>(orcamento.getItensPedido());
            if (!itens.isEmpty()) {
                itemOrcamentoList.addAll(itens);
            }
        }
        return itemOrcamentoList;
    }

    private void showAdicionais() {
        ObservableList<Adicional> listaAdicionais = getAdicionalListById();

        tcIdAdicional.setCellValueFactory(new PropertyValueFactory<Adicional, Long>("id"));
        tcNomeAdicional.setCellValueFactory(new PropertyValueFactory<Adicional, String>("nome"));
        tcMultiplicadorAdicional.setCellValueFactory(new PropertyValueFactory<Adicional, Double>("multiplicador"));

        tvAdicionais.setItems(listaAdicionais);
    }

    private ObservableList<Adicional> getAdicionalListById() {
        ItemPedido itemPedido = pegarItemDaLinha();
        ObservableList<Adicional> adicionalItemList = FXCollections.observableArrayList();
        if (itemPedido != null) {
            List<Adicional> adicionais = new ArrayList<>(itemPedido.getAdicionais());
            if (!adicionais.isEmpty()) {
                adicionalItemList.addAll(adicionais);
            }
        }
        return adicionalItemList;
    }

    public ObservableList<Orcamento> getOrcamentoList(){
        ObservableList<Orcamento> listaOrcamento = FXCollections.observableArrayList();
        List<Orcamento> orcamentos = orcamentoService.findAll().stream().filter((orcamento) -> Objects.nonNull(orcamento.getConfirmado())).collect(Collectors.toList());
        if (!orcamentos.isEmpty()) {
            listaOrcamento.addAll(orcamentos);
        }
        return listaOrcamento;
    }

    public void showOrcamento() {
        ObservableList<Orcamento> lista = getOrcamentoList();

        tcIdOrcamento.setCellValueFactory(new PropertyValueFactory<Orcamento, Long>("id"));
        tcUsuarioOrcamento.setCellValueFactory(new PropertyValueFactory<Orcamento, Long>("usuarioId"));
        tcClienteOrcamento.setCellValueFactory(new PropertyValueFactory<Orcamento, Long>("clienteId"));
        tcDataCriacaoOrcamento.setCellValueFactory(new PropertyValueFactory<Orcamento, Date>("dataCriacao"));
        tcConfirmadoOrcamento.setCellValueFactory(new PropertyValueFactory<Orcamento, Boolean>("confirmado"));

        tvOrcamentos.setItems(lista);
    }

    private Double valorTotal() {
        Orcamento orcamento = pegarOrcamentoDaLinha();
        valorTotal = 0d;
        for (var item : orcamento.getItensPedido()) {
            valorTotal += item.getValorItem();
        }
        return valorTotal;
    }

    public void preencheCbClientes() {
        ObservableList<Cliente> clientes = clienteController.getClienteList();
        List<String> nomes = clientes.stream().map(Cliente::getNome).collect(Collectors.toList());
        ObservableList<String> nomeList = FXCollections.observableArrayList();
        if (!clientes.isEmpty()) {
            nomeList.addAll(nomes);
        }
        cbClientes.setItems(nomeList);
    }

    private void clearFTOrcamento() {
        taObservacoes.setText("");
        tvItens.setItems(null);
    }

    public Orcamento pegarOrcamentoDaLinha() {
        return tvOrcamentos.getSelectionModel().getSelectedItem();
    }

    public ItemPedido pegarItemDaLinha() {
        return tvItens.getSelectionModel().getSelectedItem();
    }

    public Adicional pegarAdicionalDaLinha() {
        return tvAdicionais.getSelectionModel().getSelectedItem();
    }

    private void preencheCbCor() {
        ObservableList<Cor> cores = corController.getCorList();
        List<String> hexCode = cores.stream().map(Cor::getHexCode).collect(Collectors.toList());
        ObservableList<String> coresList = FXCollections.observableArrayList();
        if (!cores.isEmpty()) {
            coresList.addAll(hexCode);
        }
        cbCores.setItems(coresList);
    }

    private void preencheCbModelo() {
        ObservableList<Modelo> modelos = modeloController.getModeloList();
        List<String> nome = modelos.stream().map(Modelo::getNome).collect(Collectors.toList());
        ObservableList<String> modelosList = FXCollections.observableArrayList();
        if (!modelos.isEmpty()) {
            modelosList.addAll(nome);
        }
        cbModelos.setItems(modelosList);
    }

    private void preencheCbTamanho() {
        if (cbTamanhos.getItems().size() == 0) {
            cbTamanhos.getItems().add(Tamanho.pp.getSigla());
            cbTamanhos.getItems().add(Tamanho.p.getSigla());
            cbTamanhos.getItems().add(Tamanho.m.getSigla());
            cbTamanhos.getItems().add(Tamanho.g.getSigla());
            cbTamanhos.getItems().add(Tamanho.gg.getSigla());
        }
    }

    public ObservableList<Tamanho> getTamanhoList(){
        ObservableList<Tamanho> tamanhoList = FXCollections.observableArrayList();
        List<Tamanho> tamanhos = List.of(Tamanho.values());
        if (!tamanhos.isEmpty()) {
            tamanhoList.addAll(tamanhos);
        }
        return tamanhoList;
    }

    private void preencheCbPecas() {
        ObservableList<Peca> pecas = pecaController.getPecaList();
        List<String> nome = pecas.stream().map(Peca::getNome).collect(Collectors.toList());
        ObservableList<String> pecasList = FXCollections.observableArrayList();
        if (!pecas.isEmpty()) {
            pecasList.addAll(nome);
        }
        cbPecas.setItems(pecasList);
    }

    private void preencheCbTecido() {
        ObservableList<Tecido> tecidos = tecidoController.getTecidoList();
        List<String> nome = tecidos.stream().map(Tecido::getNome).collect(Collectors.toList());
        ObservableList<String> tecidoList = FXCollections.observableArrayList();
        if (!tecidos.isEmpty()) {
            tecidoList.addAll(nome);
        }
        cbTecido.setItems(tecidoList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preencheCbClientes();
        preencheCbPecas();
        preencheCbTamanho();
        preencheCbModelo();
        preencheCbTecido();
        preencheCbCor();
        showOrcamento();
        this.url = url;
        lblUsuario.setText(LogIOService.getLoggedUser().getNomeUsuario());
    }
}