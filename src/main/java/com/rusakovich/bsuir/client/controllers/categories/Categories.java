package com.rusakovich.bsuir.client.controllers.categories;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.client.controllers.ApplicationPane;
import com.rusakovich.bsuir.client.controllers.currencies.EditCurrency;
import com.rusakovich.bsuir.client.controllers.members.EditMember;
import com.rusakovich.bsuir.server.entity.AccountMember;
import com.rusakovich.bsuir.server.entity.Category;
import com.rusakovich.bsuir.server.entity.Currency;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Categories extends ApplicationPane {

    private String serverControllerName = "income_category";

    @FXML
    private TableColumn<Long, String> idColumn;
    @FXML
    private TableColumn<Category, String> nameColumn;
    @FXML
    private TableColumn<Category, String> selectionColumn;
    @FXML
    private TableView<Category> table;
    @FXML
    private Label message;
    @FXML
    private Button deleteBtn;
    @FXML
    private CheckBox selectAllCheckbox;

    @FXML
    public void initialize() {

        table.setPlaceholder(new Label("Загрузка ..."));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectionColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));

        updateTableContent();
    }

    @FXML
    public void openIncomeCategories() {
        table.setPlaceholder(new Label("Загрузка ..."));
        setServerControllerName("income_category");
        updateTableContent();
    }

    @FXML
    public void openExpenseCategories() {
        table.setPlaceholder(new Label("Загрузка ..."));
        setServerControllerName("expense_category");
        updateTableContent();
    }

    @FXML
    public void addCategory() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/categories/AddCategory.fxml"));
            Parent root = loader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            CategoryModal categoryModalController = loader.getController();
            categoryModalController.setServerControllerName(serverControllerName);

            stage.showAndWait();
            updateTableContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editCategory() {
        try {
            ArrayList<Category> selectedRows = getSelectedRows();

            if(selectedRows.size() == 0){
                message.setText("Для редактирования выделите запись из таблицы");
                return;
            }
            if(selectedRows.size() > 1){
                message.setText("Функция 'Редактировать' активна только при одной выделенной записи");
                return;
            }
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/categories/EditCategory.fxml"));
            Parent root = loader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            EditCategory editCategoryController = loader.getController();
            editCategoryController.setEditedCategory(selectedRows.get(0));
            editCategoryController.setServerControllerName(serverControllerName);
            stage.showAndWait();
            updateTableContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteSelectedCategories() {
        ArrayList<Category> selectedRows = getSelectedRows();
        int selectedNumber = selectedRows.size();

        if(selectedNumber == 0) {
            message.setText("Выберите записи для удаления");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Удаление записей");
        alert.setContentText("Вы уверены, что хотите удалить " +  selectedNumber + " записей?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            deleteBtn.setText("Удаление...");
            for(Category category : selectedRows) {
                String query = serverControllerName + "?command=delete&id=" + category.getId();
                Client.doRequest(query);
            }
            updateTableContent();
            deleteBtn.setText("Удалить");
        }
    }

    private ArrayList<Category> getSelectedRows() {
        ArrayList<Category> selectedRows = new ArrayList<>();
        ObservableList<Category> categories = table.getItems();
        for(Category category: categories) {
            if(category.getSelected().isSelected()) {
                selectedRows.add(category);
            }
        }
        return selectedRows;
    }

    private void updateTableContent() {
        message.setText("Загрузка...");
        ArrayList<Category> categories = Categories.getCategoriesFromDB(serverControllerName);
        System.out.println(categories);
        table.setItems(FXCollections.observableArrayList(categories));
        if(categories.size() == 0) {
            table.setPlaceholder(new Label("Добавьте информацию о категориях. Для этого нажмите кнопку 'Добавить'."));
        }
        if(serverControllerName.equals("income_category")) {
            ApplicationContext.getInstance().setIncomeCategories(categories);
        } else {
            ApplicationContext.getInstance().setExpenseCategories(categories);
        }
        message.setText("");
    }

    @FXML
    private void selectAll() {
        ObservableList<Category> categories = table.getItems();
        for(Category category: categories) {
            category.getSelected().setSelected(selectAllCheckbox.isSelected());
        }
        table.setItems(categories);
    }

    public void setServerControllerName(String serverControllerName) {
        this.serverControllerName = serverControllerName;
    }

    public static ArrayList<Category> getCategoriesFromDB(String serverControllerName){
        String query = serverControllerName + "?command=getAll";
        Map<String, String> params = Client.doRequest(query);
        ArrayList<Category> categories = new ArrayList<>();

        if(params.get("status").equals("ok")) {
            if(params.containsKey("data")) {
                ArrayList<Map<String, String>> categoriesParams = Client.getResponseArray(params.get("data"));

                for(Map<String, String> categoryParams : categoriesParams){
                    Category category = Category.fromMap(categoryParams);
                    category.setSelected(new CheckBox());
                    categories.add(category);
                }
            }
        }
        return categories;
    }
}
