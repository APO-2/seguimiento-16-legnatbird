package com.legnatbird.seguimiento16legnatbird;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.application.Application;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class TableController extends Application {

  private ObservableList<Person> data;
  private TableView<Person> table;
  private TextField txtId;
  private TextField txtName;
  private TextField txtLasName;
  private ComboBox<String> cbGender;

  @Override
  public void start(Stage stage) {
    BorderPane panelPrincipal = getBorderPane();
    Scene scene = new Scene(panelPrincipal, 1024, 768);
    Stage primaryStage = new Stage();
    primaryStage.setScene(scene);
    primaryStage.setTitle("Table");
    primaryStage.show();
  }

  private BorderPane getBorderPane() {
    TableView<Person> table = generateTable();
    VBox fieldsPanel = generateFields();

    BorderPane panelPrincipal = new BorderPane();
    panelPrincipal.setCenter(table);
    panelPrincipal.setRight(fieldsPanel);
    return panelPrincipal;
  }

  private VBox generateFields() {
    txtId = new TextField();
    txtId.setPromptText("ID");
    txtName = new TextField();
    txtName.setPromptText("Name");
    txtLasName = new TextField();
    txtLasName.setPromptText("Last Name");

    cbGender = new ComboBox<>();
    cbGender.getItems().addAll("Men", "Women");
    cbGender.setPromptText("Gender");

    TextField txtFilter = new TextField();
    txtFilter.setPromptText("Filter");

    Button addBtn = new Button("Add");
    Button removeBtn = new Button("Remove");

    addBtn.setOnAction(e -> addPerson());
    removeBtn.setOnAction(e -> removePerson());

    txtFilter.textProperty().addListener((observable, oldValue, newValue) -> personFilter(newValue));

    HBox fieldsBox = new HBox(10);
    fieldsBox.getChildren().addAll(txtId, txtName, txtLasName, cbGender);

    HBox btnBox = new HBox(10);
    btnBox.getChildren().addAll(addBtn, removeBtn);

    VBox fieldsView = new VBox(10);
    fieldsView.getChildren().addAll(fieldsBox, btnBox, txtFilter);
    return fieldsView;
  }

  private TableView<Person> generateTable() {
    data = PersonManager.readData();

    table = new TableView<>();
    TableColumn<Person, Integer> colId = new TableColumn<>("ID");
    TableColumn<Person, String> colName = new TableColumn<>("Name");
    TableColumn<Person, String> colLastName = new TableColumn<>("Last Name");
    TableColumn<Person, String> colGender = new TableColumn<>("Gender");

    colId.setCellValueFactory(new PropertyValueFactory<>("id"));
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

    table.getColumns().add(colId);
    table.getColumns().add(colName);
    table.getColumns().add(colLastName);
    table.getColumns().add(colGender);

    table.setItems(data);
    return table;
  }

  private void addPerson() {
    String id = txtId.getText();
    String name = txtName.getText();
    String lastName = txtLasName.getText();
    String gender = cbGender.getValue();

    if (id.isEmpty() || name.isEmpty() || lastName.isEmpty() || gender == null) {
      Alert alerta = new Alert(Alert.AlertType.ERROR);
      alerta.setTitle("Error");
      alerta.setHeaderText("Invalid data");
      alerta.setContentText("All fields are required");
      alerta.showAndWait();
    } else {
      Person person = new Person(id, name, lastName, gender);
      data.add(person);
      txtId.clear();
      txtName.clear();
      txtLasName.clear();
      cbGender.setValue(null);
      PersonManager.writePersons(data);
    }
  }

  private void removePerson() {
    Person person = table.getSelectionModel().getSelectedItem();
    if (person == null) {
      Alert alerta = new Alert(Alert.AlertType.ERROR);
      alerta.setTitle("Error");
      alerta.setHeaderText("Anybody selected");
      alerta.setContentText("Select a person to remove");
      alerta.showAndWait();
    } else {
      data.remove(person);
      PersonManager.writePersons(data);
    }
  }

  private void personFilter(String filter) {
    ObservableList<Person> filteredData = FXCollections.observableArrayList();
    for (Person person : data) {
      String id = String.valueOf(person.getId());
      String name = person.getName();
      String lastName = person.getLastName();
      if (id.contains(filter) || name.contains(filter) || lastName.contains(filter)) {
        filteredData.add(person);
      }
    }
    table.setItems(filteredData);
  }

  public static void main(String[] args) {
    launch();
  }
}