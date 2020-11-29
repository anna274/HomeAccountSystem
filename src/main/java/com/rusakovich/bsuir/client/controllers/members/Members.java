package com.rusakovich.bsuir.client.controllers.members;

import com.rusakovich.bsuir.client.app.ApplicationContext;
import com.rusakovich.bsuir.client.app.Client;
import com.rusakovich.bsuir.client.controllers.ApplicationPane;
import com.rusakovich.bsuir.server.entity.AccountMember;
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

public class Members extends ApplicationPane {

    @FXML
    private TableColumn<Long, String> idColumn;
    @FXML
    private TableColumn<AccountMember, String> nameColumn;
    @FXML
    private TableColumn<AccountMember, String> selectionColumn;
    @FXML
    private TableView<AccountMember> table;
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
    public void addMember() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/members/AddMember.fxml"));
            Parent root = loader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            updateTableContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editMember() {
        try {
            ArrayList<AccountMember> selectedMembers = getSelectedMembers();

            if(selectedMembers.size() == 0){
                message.setText("Для редактирования выделите запись из таблицы");
                return;
            }
            if(selectedMembers.size() > 1){
                message.setText("Функция 'Редактировать' активна только при одной выделенной записи");
                return;
            }
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/members/EditMember.fxml"));
            Parent root = loader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            EditMember editMemberController = loader.getController();
            editMemberController.setEditedMember(selectedMembers.get(0));

            stage.showAndWait();

            updateTableContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteSelectedMembers() {
        ArrayList<AccountMember> selectedMembers = getSelectedMembers();
        int selectedNumber = selectedMembers.size();

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
            for(AccountMember member : selectedMembers) {
                String query = "account_member?command=delete&id=" + member.getId();
                Client.doRequest(query);
            }
            updateTableContent();
            deleteBtn.setText("Удалить");
        }
    }

    private ArrayList<AccountMember> getSelectedMembers() {
        ArrayList<AccountMember> selectedMembers = new ArrayList<>();
        ObservableList<AccountMember> members = table.getItems();
        for(AccountMember member: members) {
            if(member.getSelected().isSelected()) {
                selectedMembers.add(member);
            }
        }
        return selectedMembers;
    }

    private void updateTableContent() {
        ArrayList<AccountMember> members = getMembersFromDB();
        ApplicationContext.getInstance().setMembersList(members);
        table.setItems(FXCollections.observableArrayList(members));
        if(members.size() == 0) {
            table.setPlaceholder(new Label("Добавьте пользователей в аккаунт. Для этого нажмите кнопку 'Добавить'."));
        }
        message.setText("");
    }

    @FXML
    private void selectAll() {
        ObservableList<AccountMember> members = table.getItems();
        for(AccountMember member: members) {
            member.getSelected().setSelected(selectAllCheckbox.isSelected());
        }
        table.setItems(members);
    }

    public static ArrayList<AccountMember> getMembersFromDB(){
        Long accountId = ApplicationContext.getInstance().getCurrentAccount().getId();
        String query = "account_member?command=getAllByAccountId&accountId=" + accountId;
        Map<String, String> params = Client.doRequest(query);
        ArrayList<AccountMember> members = new ArrayList<>();
        if(params.get("status").equals("ok")) {
            if(params.containsKey("data")) {
                ArrayList<Map<String, String>> membersParams = Client.getResponseArray(params.get("data"));

                for(Map<String, String> memberParam : membersParams){
                    AccountMember member = AccountMember.fromMap(memberParam);
                    member.setSelected(new CheckBox());
                    members.add(member);
                }
            }
        }
        return members;
    }
}
