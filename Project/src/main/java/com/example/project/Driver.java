package com.example.project;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.time.LocalDate;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Driver extends Application {
    DLinkedList<District> dDistrict = new DLinkedList<>();
    private int currentDistrictIndex = -1; // Initial index of the current district
    Stage stage;
    private Label resLabel;
    private Label labelDistrictInfo = new Label();
    private Label labelDisToLocal = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    private void loadRecordsFromFile(File file) {

        dDistrict.clear(); // Clear the district doubly linked list

        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine(); // Skip the header line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    String name = parts[0].isEmpty() ? "Unknown" : parts[0];
                    String dateOfDeath = parts[1].isEmpty() ? "Unknown" : parts[1];
                    int age = 0;
                    try {
                        age = parts[2].isEmpty() ? -1 : Integer.parseInt(parts[2]);
                    } catch (NumberFormatException ex) {
                        resLabel.setText("Invalid age format for record: " + name + ". Setting age to -1.\n");
                        continue; // Skip invalid record
                    }
                    String location = parts[3].isEmpty() ? "Unknown" : parts[3];
                    String district = parts[4].isEmpty() ? "Unknown" : parts[4];
                    String gender = parts[5].equals("NA") ? "NA" : parts[5];

                    // Create a new martyr record
                    Martyr martyrRecord = new Martyr(name, dateOfDeath, age, location, district, gender);

                    insertProject(martyrRecord);
                }
            }
            resLabel.setText("Records loaded successfully.\n");
        } catch (IOException e) {
            resLabel.setText("Failed to load records.\n");
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        Button loadButton = new Button("Load Records");

        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv")
            );
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                loadRecordsFromFile(selectedFile);
                menuScreen();
            }
        });

        resLabel = new Label();

        VBox vStart = new VBox();
        vStart.getChildren().addAll(loadButton, resLabel);
        vStart.setSpacing(10);
        vStart.setAlignment(Pos.CENTER);



        Scene scene = new Scene(vStart, 300, 100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Read File Screen");
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public void menuScreen(){
        Button btnDistrict = new Button("District Screen ");
        Button btnLocation = new Button("Location Screen");
        VBox vbButtons = new VBox(20);
        vbButtons.getChildren().addAll(btnDistrict, btnLocation);
        vbButtons.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbButtons , 300 , 200);
        stage.setScene(scene);
        stage.setTitle("Project Menu");
        stage.centerOnScreen();

        btnDistrict.setOnAction(event ->{
            districtScreen();
        });
        btnLocation.setOnAction(event ->{
            locationScreen();
        });
    }
    public void locationScreen(){
        Button btnInsertL = new Button("Insert New Location");
        Button btnUpdateL = new Button("Update Location");
        Button btnDeleteL = new Button("Delete Location");
        Button btnSearchLocation = new Button("Search for Location");
        Button btnNextL = new Button("Next Location");
        Button btnMartyrScreen = new Button("Martyr Screen");
        Button btnBackL = new Button("Back");

        TextField insField = new TextField();
        insField.setPromptText("Inset a new Location");
        insField.setPrefWidth(200);
        TextField insDField = new TextField();
        insDField.setPromptText("Select the District u want to add in");
        insDField.setPrefWidth(200);

        TextField upDField = new TextField();
        upDField.setPromptText("Select the District u want to update on it");
        upDField.setPrefWidth(200);
        TextField upField = new TextField();
        upField.setPromptText("Location u want to update ");
        upField.setPrefWidth(200);
        TextField upNewField = new TextField();
        upNewField.setPromptText("New Location Name");
        upNewField.setPrefWidth(200);

        TextField delDField = new TextField();
        delDField.setPromptText("Select the District u want to delete from");
        delDField.setPrefWidth(200);
        TextField delField = new TextField();
        delField.setPromptText("Delete a Location");
        delField.setPrefWidth(200);

        TextField searchDField = new TextField();
        searchDField.setPromptText("Select the District u want to");
        searchDField.setPrefWidth(200);
        TextField searchField = new TextField();
        searchField.setPromptText("Search Location to get Statistics");
        searchField.setPrefWidth(200);

        TextField nextField = new TextField();
        nextField.setPromptText("Select the District");
        nextField.setPrefWidth(200);

        Label labelLocationInfo = new Label();
        labelLocationInfo.setStyle("-fx-border-color: black;");

        BorderPane lBorder = new BorderPane();

        GridPane locPane = new GridPane();
        locPane.setAlignment(Pos.CENTER);
        locPane.setHgap(10);
        locPane.setVgap(10);

        locPane.add(btnInsertL,0,0);
        locPane.add(insDField,1,0);
        locPane.add(insField,2,0);

        locPane.add(btnUpdateL,0,1);
        locPane.add(upDField,1,1);
        locPane.add(upField,2,1);
        locPane.add(upNewField,3,1);

        locPane.add(btnDeleteL,0,2);
        locPane.add(delDField,1,2);
        locPane.add(delField,2,2);

        locPane.add(btnSearchLocation,0,3);
        locPane.add(searchDField,1,3);
        locPane.add(searchField,2,3);

        locPane.add(btnNextL,0,4);
        locPane.add(nextField,1,4);
        locPane.add(labelLocationInfo,1,5);
        locPane.add(labelDisToLocal,2,5);
        GridPane.setColumnSpan(labelLocationInfo, 3);

        lBorder.setCenter(locPane);

        HBox hBackBtn = new HBox(15);
        hBackBtn.getChildren().addAll(btnBackL);
        hBackBtn.setAlignment(Pos.BOTTOM_LEFT);
        lBorder.setBottom(hBackBtn);

        HBox hMartyr = new HBox(15);
        hBackBtn.getChildren().addAll(btnMartyrScreen);
        hBackBtn.setAlignment(Pos.BOTTOM_CENTER);
        lBorder.setRight(hMartyr);

        btnInsertL.setOnAction(event -> {
            String newLocationName = insField.getText().trim();
            String districtName = insDField.getText().trim();

            if (newLocationName.isEmpty() || districtName.isEmpty()) {
                labelLocationInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelLocationInfo.setText("Please enter both a location and district name.");
                return;
            }

            // Find the district by name (replace this with your logic to find districts)
            District district = findDistrictByName(districtName);
            if (district == null) {
                labelLocationInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelLocationInfo.setText("District not found: " + districtName);
                return;
            }

            // Check if a similar location name (case-insensitive) already exists in the district
            Location existingLocation = findNameS(district, newLocationName);
            if (existingLocation != null) {
                labelLocationInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelLocationInfo.setText("Location '" + newLocationName + "' already exists in district '" + districtName + "'.");
                return;
            }

            // Create a new Location and add it to the district's location list
            Location newLocation = new Location(newLocationName);
            district.getsLocation().insert(newLocation);

            // Clear input fields and update label
            insField.clear();
            insDField.clear();
            labelLocationInfo.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            labelLocationInfo.setText("Location '" + newLocationName + "' inserted into district '" + districtName + "'.");

        });

        btnUpdateL.setOnAction(event -> {
            String districtName = upDField.getText().trim();
            String locationName = upField.getText().trim();
            String newLocationName = upNewField.getText().trim();

            if (districtName.isEmpty() || locationName.isEmpty() || newLocationName.isEmpty()) {
                labelLocationInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelLocationInfo.setText("Please enter the district name, old location name, and new location name.");
                return;
            }

            // Find the district by name (replace this with your logic to find districts)
            District district = findDistrictByName(districtName);
            if (district == null) {
                labelLocationInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelLocationInfo.setText("District not found: " + districtName);
                return;
            }

            // Find the location within the district
            Location locationToUpdate = findNameS(district, locationName);
            if (locationToUpdate == null) {
                labelLocationInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelLocationInfo.setText("Location not found in district: " + locationName);
                return;
            }

            // Update the location name
            locationToUpdate.setLocationName(newLocationName);

            // Sort the district's locations by name
            district.getsLocation().sortByNameL();

            // Clear input fields and update label to indicate success
            upDField.clear();
            upField.clear();
            upNewField.clear();
            labelLocationInfo.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            labelLocationInfo.setText("Location updated: " + locationName + " -> " + newLocationName);
        });

        btnDeleteL.setOnAction(event -> {
            String districtName = delDField.getText().trim();
            String locationName = delField.getText().trim();

            if (districtName.isEmpty() || locationName.isEmpty()) {
                labelLocationInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelLocationInfo.setText("Please enter both district name and location name.");
                return;
            }

            // Find the district by name (replace this with your logic to find districts)
            District district = findDistrictByName(districtName);
            if (district == null) {
                labelLocationInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelLocationInfo.setText("District not found: " + districtName);
                return;
            }

            // Find the location within the district
            Location locationToDelete = findNameS(district, locationName);
            if (locationToDelete == null) {
                labelLocationInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelLocationInfo.setText("Location not found in district: " + locationName);
                return;
            }

            // Remove the location from the district's location list
            district.getsLocation().delete(locationToDelete);

            // Clear input fields and update label to indicate success
            delDField.clear();
            delField.clear();
            labelLocationInfo.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            labelLocationInfo.setText("Location deleted: " + locationName);
        });

        btnSearchLocation.setOnAction(event -> {
            String districtName = searchDField.getText().trim();
            String locationName = searchField.getText().trim();

            if (districtName.isEmpty() || locationName.isEmpty()) {
                labelLocationInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelLocationInfo.setText("Please enter both district name and location name.");
                return;
            }

            // Find the district by name
            District district = findDistrictByName(districtName);
            if (district == null) {
                labelLocationInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelLocationInfo.setText("District not found: " + districtName);
                return;
            }

            // Find the location within the district
            Location locationToSearch = findNameS(district, locationName);
            if (locationToSearch == null) {
                labelLocationInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelLocationInfo.setText("Location not found in district: " + locationName);
                return;
            }

            // Calculate specific location statistics
            int totalMartyrs = locationToSearch.getTotalMartyrsL();
            int totalMaleMartyrs = locationToSearch.getTotalMaleMartyrsL();
            int totalFemaleMartyrs = locationToSearch.getTotalFemaleMartyrsL();
            double averageMartyrAge = locationToSearch.getAverageMartyrAgeL();
            Martyr youngestMartyr = locationToSearch.getYoungestMartyr();
            Martyr oldestMartyr = locationToSearch.getOldestMartyr();

            // Display the location statistics
            labelLocationInfo.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            labelLocationInfo.setText("Location Statistics for " + locationName + " in " + districtName + ":\n" +
                    "Total Martyrs: " + totalMartyrs + "\n" +
                    "Total Male Martyrs: " + totalMaleMartyrs + "\n" +
                    "Total Female Martyrs: " + totalFemaleMartyrs + "\n" +
                    "Average Martyr Age: " + String.format("%.2f", averageMartyrAge) + "\n" +
                    "Youngest Martyr: " + (youngestMartyr != null ? youngestMartyr.getName() : "N/A") + "\n" +
                    "Oldest Martyr: " + (oldestMartyr != null ? oldestMartyr.getName() : "N/A"));
            searchDField.clear();
            searchField.clear();
        });

        btnNextL.setOnAction(event -> {
            String districtName = nextField.getText().trim();

            // Find the district by name (replace this with your logic to find districts)
            District district = findDistrictByName(districtName);
            if (district == null) {
                labelLocationInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelLocationInfo.setText("District not found: " + districtName);
                return;
            }

            // Call nextLocation to navigate to the next location
            labelLocationInfo.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            labelLocationInfo.setText(district.nextLocation());


            // Get the current location and perform actions
            Location currentLocation = district.getCurrentLocation();
            if (currentLocation != null) {
                System.out.println("Next Location: " + currentLocation.getLocationName());
            }
        });

        btnMartyrScreen.setOnAction(event -> {
            martyrScreen();
        });

        btnBackL.setOnAction(e1 -> menuScreen());

        Scene scene = new Scene(lBorder, 1050, 450);
        stage.setScene(scene);
        stage.setTitle("Location Screen");
        stage.centerOnScreen();

    }
    public void martyrScreen(){
        Button btnInsertM = new Button("Insert New Martyr");
        Button btnUpdateM = new Button("Update Martyr");
        Button btnDeleteM = new Button("Delete Martyr");
        Button btnSearchMartyr = new Button("Search by part of name");
        Button btnBackM = new Button("Back");


        Label labelDisName = new Label("District:");
        Label labelName = new Label("Name:");
        Label labelAge = new Label("Age:");
        Label labelLocation = new Label("Location:");
        Label labelDate = new Label("Date:");
        Label labelGender = new Label("Gender:");

        Label labelDistName = new Label("District:");
        Label labelLocatName = new Label("Location:");
        Label labelFirstName = new Label("First:");
        Label labelSecondName = new Label("Second:");
        Label labelThirdName = new Label("Third:");
        Label labelFourthName = new Label("Fourth:");

        Label labelDistNameD = new Label("District:");
        Label labelLocatNameD = new Label("Location:");
        Label labelNameD = new Label("Name:");

        Label labelDistNameS = new Label("District:");
        Label labelLocatNameS = new Label("Location:");
        Label labelNameS = new Label("Name:");

        Label resultLabelM = new Label();

        TextField textDisName = new TextField();
        textDisName.setPromptText("District Name");
        TextField textName = new TextField();
        textName.setPromptText("Name");
        TextField textDate = new TextField();
        textDate.setPromptText("Date");
        TextField textAge = new TextField();
        textAge.setPromptText("Age");
        TextField textLocation = new TextField();
        textLocation.setPromptText("Location");
        TextField textDistrict = new TextField();
        textDistrict.setPromptText("District");
        TextField textGender = new TextField();
        textGender.setPromptText("Gender");

        TextField disNameField = new TextField();
        disNameField.setPromptText("District Name");
        TextField locNameField = new TextField();
        locNameField.setPromptText("Location Name");
        TextField upFirstField = new TextField();
        upFirstField.setPromptText("First Name");
        TextField upSecondField = new TextField();
        upSecondField.setPromptText("Second Name");
        TextField upThirdField = new TextField();
        upThirdField.setPromptText("Third Name");
        TextField upFourthField = new TextField();
        upFourthField.setPromptText("Fourth Name");

        TextField disNameFieldM = new TextField();
        disNameFieldM.setPromptText("District Name");
        TextField locNameFieldM = new TextField();
        locNameFieldM.setPromptText("Location Name");
        TextField deleteFieldM = new TextField();
        deleteFieldM.setPromptText("Delete Martyr");

        TextField disNameFieldM2 = new TextField();
        disNameFieldM2.setPromptText("District Name");
        TextField locNameFieldM2 = new TextField();
        locNameFieldM2.setPromptText("Location Name");
        TextField searchFieldM = new TextField();
        searchFieldM.setPromptText("Search by part of name");

        HBox buttonBox = new HBox(labelDisName,textDisName,labelName,textName,labelAge,textAge,labelDate,textDate,labelLocation,textLocation,labelGender,textGender);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10, 10, 10, 10));

        HBox buttonBox2 = new HBox(labelDistName,disNameField,labelLocatName,locNameField,labelFirstName,upFirstField,labelSecondName,upSecondField,labelThirdName,upThirdField,labelFourthName,upFourthField);
        //buttonBox2.setAlignment(Pos.CENTER);
        buttonBox2.setSpacing(10);
        buttonBox2.setPadding(new Insets(10, 10, 10, 10));

        HBox buttonBox3 = new HBox(labelDistNameD,disNameFieldM,labelLocatNameD,locNameFieldM,labelNameD,deleteFieldM);
       // buttonBox3.setAlignment(Pos.CENTER);
        buttonBox3.setSpacing(10);
        buttonBox3.setPadding(new Insets(10, 10, 10, 10));

        HBox buttonBox4 = new HBox(labelDistNameS,disNameFieldM2,labelLocatNameS,locNameFieldM2,labelNameS,searchFieldM);
       // buttonBox4.setAlignment(Pos.CENTER);
        buttonBox4.setSpacing(10);
        buttonBox4.setPadding(new Insets(10, 10, 10, 10));



        BorderPane martyrBorder = new BorderPane();

        GridPane martyrGrid = new GridPane();
        martyrGrid.setAlignment(Pos.CENTER);
        martyrGrid.setHgap(10);
        martyrGrid.setVgap(10);
        martyrGrid.add(btnInsertM,0,0);
        martyrGrid.add(buttonBox,1,0);

        martyrGrid.add(btnUpdateM,0,1);
        martyrGrid.add(buttonBox2,1,1);

        martyrGrid.add(btnDeleteM,0,2);
        martyrGrid.add(buttonBox3,1,2);

        martyrGrid.add(btnSearchMartyr,0,3);
        martyrGrid.add(buttonBox4,1,3);

        martyrGrid.add(resultLabelM,0,4);
        GridPane.setColumnSpan(resultLabelM,3);
        GridPane.setHalignment(resultLabelM, HPos.CENTER);
        GridPane.setValignment(resultLabelM, VPos.CENTER);

        HBox hBackBtn = new HBox(15);
        hBackBtn.getChildren().addAll(btnBackM);
        hBackBtn.setAlignment(Pos.BOTTOM_CENTER);
        martyrBorder.setBottom(hBackBtn);

        martyrBorder.setCenter(martyrGrid);

        btnInsertM.setOnAction(event -> {
            // Get input values from text fields
            String districtName = textDisName.getText().trim();
            String locationName = textLocation.getText().trim();
            String martyrName = textName.getText().trim();
            String dateOfDeath = textDate.getText().trim();
            String ageStr = textAge.getText().trim();
            String gender = textGender.getText().trim();

            // Validate input (check for empty fields)
            if (districtName.isEmpty() || locationName.isEmpty() || martyrName.isEmpty() || dateOfDeath.isEmpty() || ageStr.isEmpty() || gender.isEmpty()) {
                resultLabelM.setText("Please fill out all fields.");
                return; // Exit the method if any field is empty
            }

            // Validate age (must be a valid integer)
            int martyrAge;
            try {
                martyrAge = Integer.parseInt(ageStr);
                if (martyrAge < 0 || martyrAge > 150) {
                    resultLabelM.setText("Invalid age. Age must be between 0 and 160.");
                    return; // Exit if age is out of range
                }
            } catch (NumberFormatException e) {
                resultLabelM.setText("Invalid age format. Please enter a valid number.");
                return; // Exit if age is not a valid number
            }

            // Find the specified district
            District selectedDistrict = findDistrictByName(districtName);
            if (selectedDistrict == null) {
                resultLabelM.setText("District not found.");
                return;
            }

            // Find the specified location within the district
            Location selectedLocation = findNameS(selectedDistrict, locationName);
            if (selectedLocation == null) {
                resultLabelM.setText("Location not found in the district.");
                return;
            }

            // Create a new martyr instance
            Martyr newMartyr = new Martyr(martyrName, dateOfDeath, martyrAge, locationName, districtName, gender);

            // Insert the new martyr into the selected location
            selectedLocation.addMartyr(newMartyr);

            // Display success message
            resultLabelM.setText("New martyr record inserted successfully.");
        });


        btnUpdateM.setOnAction(event -> {
            // Get input values from text fields
            String districtName = disNameField.getText();
            String locationName = locNameField.getText();
            String firstName = upFirstField.getText();
            String secondName = upSecondField.getText();
            String thirdName = upThirdField.getText();
            String fourthName = upFourthField.getText();

            // Validate input (assuming all fields are required)
            if (districtName.isEmpty() || locationName.isEmpty() || firstName.isEmpty() || secondName.isEmpty()) {
                resultLabelM.setText("Please fill out required fields.");
                return;
            }

            // Find the specified district
            District selectedDistrict = findDistrictByName(districtName);
            if (selectedDistrict == null) {
                resultLabelM.setText("District not found.");
                return;
            }

            // Find the specified location within the district
            Location selectedLocation = findNameS(selectedDistrict, locationName);
            if (selectedLocation == null) {
                resultLabelM.setText("Location not found in the district.");
                return;
            }

            // Find the martyr within the location based on the provided names
            Martyr martyrToUpdate = findMartyrByName(selectedLocation, firstName, secondName, thirdName, fourthName);
            if (martyrToUpdate == null) {
                resultLabelM.setText("Martyr not found in the location.");
            } else {
                updateMartyrScreen(martyrToUpdate);
            }
        });

        btnDeleteM.setOnAction(event -> {
            String districtName = disNameFieldM.getText().trim();
            String locationName = locNameFieldM.getText().trim();
            String martyrFullName = deleteFieldM.getText().trim(); // Full name of the martyr to delete

            if (districtName.isEmpty() || locationName.isEmpty() || martyrFullName.isEmpty()) {
                resultLabelM.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                resultLabelM.setText("Please fill out all required fields.");
                return;
            }

            // Find the district by name
            District district = findDistrictByName(districtName);
            if (district == null) {
                resultLabelM.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                resultLabelM.setText("District not found: " + districtName);
                return;
            }

            // Find the location within the district
            Location locationToDelete = findNameS(district, locationName);
            if (locationToDelete == null) {
                resultLabelM.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                resultLabelM.setText("Location not found in district: " + locationName);
                return;
            }

            // Find the martyr by full name within the location
            Martyr martyrToDelete = findMartyrByFullName(locationToDelete, martyrFullName);
            if (martyrToDelete == null) {
                resultLabelM.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                resultLabelM.setText("Martyr not found in location: " + martyrFullName);
                return;
            }

            // Delete the martyr from the location's list of martyrs
            if (locationToDelete.getSMartyr().delete(martyrToDelete)) {
                // Clear input fields and update label to indicate success
                disNameFieldM.clear();
                locNameFieldM.clear();
                deleteFieldM.clear();
                resultLabelM.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                resultLabelM.setText("Martyr '" + martyrFullName + "' deleted successfully.");
            } else {
                resultLabelM.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                resultLabelM.setText("Failed to delete martyr.");
            }
        });

        btnSearchMartyr.setOnAction(event -> {
            String districtName = disNameFieldM2.getText().trim();
            String locationName = locNameFieldM2.getText().trim();
            String partialName = searchFieldM.getText().trim(); // Partial name of the martyr to search for

            if (districtName.isEmpty() || locationName.isEmpty() || partialName.isEmpty()) {
                resultLabelM.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                resultLabelM.setText("Please fill out all required fields.");
                return;
            }

            // Find the district by name
            District district = findDistrictByName(districtName);
            if (district == null) {
                resultLabelM.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                resultLabelM.setText("District not found: " + districtName);
                return;
            }

            // Find the location within the district
            Location location = findNameS(district, locationName);
            if (location == null) {
                resultLabelM.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                resultLabelM.setText("Location not found in district: " + locationName);
                return;
            }

            // Find the martyr by part of name within the location
            String martyr = findMartyrByPartOfName(district, location, partialName);
            if (martyr == null) {
                resultLabelM.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                resultLabelM.setText("Martyr not found in location: " + partialName);
            } else {
                resultLabelM.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                resultLabelM.setText("Martyr found in location: " + martyr.toString());
            }
        });

        btnBackM.setOnAction(e1 -> locationScreen());

        Scene scene = new Scene(martyrBorder, 1500, 675);
        stage.setScene(scene);
        stage.setTitle("Martyr Screen");
        stage.centerOnScreen();

    }

    private void updateMartyrScreen(Martyr martyrToUpdate){

        TextField textName = new TextField(martyrToUpdate.getName());
        TextField textAge = new TextField(String.valueOf(martyrToUpdate.getAge()));
        TextField textDate = new TextField(martyrToUpdate.getDateOfDeath());
        TextField textLocation = new TextField(martyrToUpdate.getLocation());
        TextField textDistrict = new TextField(martyrToUpdate.getDistrict());
        TextField textGender = new TextField(martyrToUpdate.getGender());

        Button btnSave = new Button("Save");
        Button btnBackUp = new Button("Back");

        Label resultUpdateM = new Label();

        Label labelName = new Label("Name:");
        Label labelAge = new Label("Age:");
        Label labelLocation = new Label("Location:");
        Label labelDistrict = new Label("District:");
        Label labelDate = new Label("Date:");
        Label labelGender = new Label("Gender:");

        // Set prompt texts
        textName.setPromptText("Name");
        textAge.setPromptText("Age");
        textDate.setPromptText("Date");
        textLocation.setPromptText("Location");
        textDistrict.setPromptText("District");
        textGender.setPromptText("Gender");

        // Create layout for update martyr screen
        HBox buttonBox = new HBox(labelName, textName, labelAge, textAge, labelDate, textDate, labelLocation, textLocation,labelDistrict,textDistrict, labelGender, textGender);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10, 10, 10, 10));

        VBox VButtons = new VBox(buttonBox,resultUpdateM);
        VButtons.setAlignment(Pos.CENTER);
        VButtons.setSpacing(10);
        VButtons.setPadding(new Insets(10, 10, 10, 10));

        BorderPane upMartyrBorder = new BorderPane();
        upMartyrBorder.setCenter(VButtons);


        HBox hBackBtn = new HBox(15);
        hBackBtn.getChildren().addAll(btnBackUp);
        hBackBtn.setAlignment(Pos.BOTTOM_LEFT);
        upMartyrBorder.setBottom(hBackBtn);

        HBox hSaveBtn = new HBox(15);
        hBackBtn.getChildren().addAll(btnSave);
        hBackBtn.setAlignment(Pos.BOTTOM_CENTER);
        upMartyrBorder.setRight(hSaveBtn);

        btnBackUp.setOnAction(e -> {
            martyrScreen(); // Navigate back to martyr screen
        });

        btnSave.setOnAction(e -> {
            String newName = textName.getText().trim();
            String newAgeStr = textAge.getText().trim();
            String newDateOfDeath = textDate.getText().trim();
            String newLocation = textLocation.getText().trim();
            String newDistrict = textDistrict.getText().trim();
            String newGender = textGender.getText().trim();

            // Validate input (check for empty fields, valid age, etc.)
            if (newName.isEmpty() || newAgeStr.isEmpty() || newDateOfDeath.isEmpty() || newLocation.isEmpty() || newDistrict.isEmpty() || newGender.isEmpty()) {
                resultUpdateM.setText("Please fill out all fields.");
                return;
            }

            // Validate age
            try {
                int newAge = Integer.parseInt(newAgeStr);
                if (newAge < 0 || newAge > 150) {
                    resultUpdateM.setText("Invalid age. Please enter a valid age (0-150).");
                    return;
                }
            } catch (NumberFormatException ex) {
                resultUpdateM.setText("Invalid age format. Please enter a valid age (0-150).");
                return;
            }

            // Validate date format
            if (!isValidDateFormat(newDateOfDeath)) {
                resultUpdateM.setText("Invalid date format. Please enter a date in the format MM/DD/YYYY.");
                return;
            }
            // Delete the old martyr record based on name and date of death
            boolean deleteSuccess = deleteMartyrRecord(martyrToUpdate.getName(), martyrToUpdate.getDateOfDeath());            if (!deleteSuccess) {
                resultUpdateM.setText("Martyr not found for update.");
                return; // Exit if martyr to update is not found
            }

            // Create a new martyr instance with updated values
            Martyr updatedMartyr = new Martyr(newName, newDateOfDeath, Integer.parseInt(newAgeStr), newLocation, newDistrict, newGender);

            // Insert the updated martyr into the appropriate district and location
            insertProject(updatedMartyr);

            // Display success message
            resultUpdateM.setText("Martyr record updated successfully.");

            // Navigate back to martyr screen
            martyrScreen();
        });



        // Create and show scene for update martyr screen
        Scene scene = new Scene(upMartyrBorder, 1400, 150);
        stage.setScene(scene);
        stage.setTitle("Update Martyr Screen");
        stage.centerOnScreen();

    }

    public void districtScreen() {

        Button btnInsertD = new Button("Insert New District");
        Button btnUpdateD = new Button("Update District");
        Button btnDeleteD = new Button("Delete District");
        Button btnLoadDistrict = new Button("Load the first Location");
        Button btnSearchDistrict = new Button("Search by the Date");
        Button btnNextD = new Button("Next District");
        Button btnPreviousD = new Button("Previous District");
        Button btnBack = new Button("Back");

        TextField insField = new TextField();
        insField.setPromptText("Insert a new District");
        insField.setPrefWidth(200);
        TextField upField = new TextField();
        upField.setPromptText("District u want to update ");
        upField.setPrefWidth(200);
        TextField upNewField = new TextField();
        upNewField.setPromptText("New District Name");
        upNewField.setPrefWidth(200);
        TextField delField = new TextField();
        delField.setPromptText("Delete a District");
        delField.setPrefWidth(200);
        TextField dateField = new TextField();
        dateField.setPromptText("Search by the Date");
        dateField.setPrefWidth(200);
        TextField loadField = new TextField();
        loadField.setPromptText("give the District");
        loadField.setPrefWidth(200);

       /* DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select Date");
        datePicker.setValue(LocalDate.now()); // Optional: Set default value to today's date

*/


        btnDeleteD.setPrefWidth(200);
        btnInsertD.setPrefWidth(200);
        btnUpdateD.setPrefWidth(200);
        btnLoadDistrict.setPrefWidth(150);
        btnSearchDistrict.setPrefWidth(200);
        btnNextD.setPrefWidth(100);
        btnPreviousD.setPrefWidth(130);
        btnBack.setPrefWidth(70);

        BorderPane border = new BorderPane();

        GridPane disPane = new GridPane();
        disPane.setAlignment(Pos.CENTER);
        disPane.setHgap(10);
        disPane.setVgap(10);
        disPane.add(btnInsertD, 0, 0);
        disPane.add(insField, 1, 0);
        disPane.add(btnUpdateD,0,1);
        disPane.add(upField, 1, 1);
        disPane.add(upNewField, 2, 1);
        disPane.add(btnDeleteD, 0, 2);
        disPane.add(delField, 1, 2);
        disPane.add(btnSearchDistrict, 0, 3);
        disPane.add(dateField, 1, 3);

        disPane.add(labelDistrictInfo,0,8);
        GridPane.setColumnSpan(labelDistrictInfo, 3);

        disPane.add(btnNextD, 1, 4);
        disPane.setHalignment(btnNextD, HPos.CENTER);
        disPane.setValignment(btnNextD, VPos.CENTER);

        disPane.add(btnPreviousD, 0, 4);
        disPane.setHalignment(btnPreviousD, HPos.CENTER);
        disPane.setValignment(btnPreviousD, VPos.CENTER);


        HBox hBackBtn = new HBox(15);
        hBackBtn.getChildren().addAll(btnBack);
        hBackBtn.setAlignment(Pos.BOTTOM_LEFT);
        border.setBottom(hBackBtn);

        HBox hLoadLoc = new HBox(15);
        hBackBtn.getChildren().addAll(btnLoadDistrict,loadField);
        hBackBtn.setAlignment(Pos.BOTTOM_CENTER);
        border.setRight(hLoadLoc);

        disPane.add(hLoadLoc, 2, 10);
        disPane.setHalignment(hLoadLoc, HPos.CENTER);
        disPane.setValignment(hLoadLoc, VPos.CENTER);

        border.setCenter(disPane);

        btnInsertD.setOnAction(e -> {

            String newDistrictName = insField.getText().trim();

            if (newDistrictName.isEmpty()) {
                labelDistrictInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelDistrictInfo.setText("Please enter a name.");
                return;
            }

            // Check if the district already exists
            District existingDistrict = findDistrictByName(newDistrictName);
            if (existingDistrict != null) {
                labelDistrictInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelDistrictInfo.setText("District already exists: " + newDistrictName);
                insField.clear();
                return;
            }else{
                // Insert the new district into the linked list
                dDistrict.insert(new District(newDistrictName));
                insField.clear();
                labelDistrictInfo.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                labelDistrictInfo.setText("District inserted: " + newDistrictName);
            }
        });


        btnInsertD.setOnAction(e -> {

            String newDistrictName = insField.getText().trim();

            if (newDistrictName.isEmpty()) {
                labelDistrictInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelDistrictInfo.setText("Please enter a name.");
                return;
            }

            // Check if the district already exists
            District existingDistrict = findDistrictByName(newDistrictName);
            if (existingDistrict != null) {
                labelDistrictInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelDistrictInfo.setText("District already exists: " + newDistrictName);
                insField.clear();
                return;
            }else{
                // Insert the new district into the linked list
                dDistrict.insert(new District(newDistrictName));
                insField.clear();
                labelDistrictInfo.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                labelDistrictInfo.setText("District inserted: " + newDistrictName);
            }
        });


        btnUpdateD.setOnAction(e -> {
            String oldDistrictName = upField.getText().trim();
            String newDistrictName = upNewField.getText().trim();

            if (oldDistrictName.isEmpty() || newDistrictName.isEmpty()) {
                labelDistrictInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelDistrictInfo.setText("Please enter a name on to text fields.");
                return;
            }

            // Find the district to update by its current name
            District district = findDistrictByName(oldDistrictName);

            if (district != null) {
                // Update the district name
                //district.setDistrictName(newDistrictName);
                dDistrict.delete(district);
                District updateDistrict = new District(newDistrictName);
                dDistrict.insert(updateDistrict);

                labelDistrictInfo.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                labelDistrictInfo.setText("District updated: " + oldDistrictName + " -> " + newDistrictName);

                // Example: Compare district names (case-insensitive) using compareToIgnoreCase
                if (district.getDistrictName().compareToIgnoreCase(newDistrictName) == 0) {
                    labelDistrictInfo.setText("District names match.");
                }

            } else {
                labelDistrictInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelDistrictInfo.setText("District not found: " + oldDistrictName);
            }

            // Clear input fields after processing
            upField.clear();
            upNewField.clear();
        });



        btnDeleteD.setOnAction(e -> {
            String districtNameToDelete = delField.getText().trim();

            if (districtNameToDelete.isEmpty()) {
                labelDistrictInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelDistrictInfo.setText("Please enter a valid district name to delete.");
                return;
            }

            // Find the district to delete by its name
            District district = findDistrictByName(districtNameToDelete);
            if (district != null) {
                // Delete the district from the doubly linked list
                dDistrict.delete(district);
                labelDistrictInfo.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                labelDistrictInfo.setText("District deleted: " + districtNameToDelete);
            } else {
                labelDistrictInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelDistrictInfo.setText("District not found: " + districtNameToDelete);
            }

            // Clear the input field after processing
            delField.clear();
        });


        btnLoadDistrict.setOnAction(e -> {
            String districtName = loadField.getText().trim();

            // Check if the district name entered is empty
            if (districtName.isEmpty()) {
                labelDistrictInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelDistrictInfo.setText("Please enter a district name to load locations.");
                return;
            }

            // Find the district by name in the doubly linked list of districts
            District selectedDistrict = findDistrictByName(districtName);

            // Check if the district is found
            if (selectedDistrict != null) {
                // Get the locations of the selected district
                SLinkedList<Location> locations = selectedDistrict.getsLocation();

                // Check if the district has any locations
                if (locations.length() > 0) {
                    // Get the first location from the selected district
                    Location firstLocation = locations.get(0);

                    // Display the information of the first location
                    String locationInfo = "Loaded first location from " + districtName + ": " + firstLocation.getLocationName();
                    labelDisToLocal.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    labelDisToLocal.setText(locationInfo);

                    // Navigate to location screen or perform other actions if needed
                    locationScreen(); // You may need to pass 'firstLocation' to the location screen
                } else {
                    // Handle case where the selected district has no locations
                    String message = "No locations available in district " + districtName + ".";
                    labelDisToLocal.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    labelDisToLocal.setText(message);
                }
            } else {
                // Handle case where the district with the entered name is not found
                String message = "District not found: '" + districtName + ".";
                labelDisToLocal.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelDisToLocal.setText(message);
            }
        });


        btnNextD.setOnAction(e -> {
            if (currentDistrictIndex < dDistrict.length() - 1) {
                currentDistrictIndex++;
                displayDistrictStatistics();
            }
        });

        btnPreviousD.setOnAction(e -> {
            if (currentDistrictIndex > 0) {
                currentDistrictIndex--;
                displayDistrictStatistics();
            }
        });

        btnSearchDistrict.setOnAction(e -> {
            String dateMartyr = dateField.getText().trim();
            int searchOutput = 0;

            if (dateMartyr.isEmpty()) {
                labelDistrictInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelDistrictInfo.setText("Please enter a valid date to search.");
                dateField.clear();
                return;
            }

            // Validate the format of the entered date
            if (!isValidDateFormat(dateMartyr)) {
                labelDistrictInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                labelDistrictInfo.setText("Invalid date format. Please enter a date in the format MONTH/DAY/YEAR.");
                dateField.clear();
                return;
            }

            // Date format is valid, proceed with search logic
            for (int i = 0; i < dDistrict.length(); i++) {
                District district = dDistrict.get(i);
                for (int j = 0; j < district.getsLocation().length(); j++) {
                    Location location = district.getsLocation().get(j);
                    searchOutput += location.countMartyrsByDateL(dateMartyr);
                }
            }

            // Display search result
            labelDistrictInfo.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            labelDistrictInfo.setText("Total martyrs: " + searchOutput);
            dateField.clear();
        });


//        HBox hBackBtn = new HBox(15);
//        hBackBtn.getChildren().addAll(btnBack);
//        hBackBtn.setAlignment(Pos.BOTTOM_LEFT);
//        border.setBottom(hBackBtn);



       /* VBox vbButtons = new VBox(15);
        HBox hButtons = new HBox(15);
        vbButtons.getChildren().addAll(btnInsertD, btnUpdateD, btnDeleteD, btnLoadDistrict, btnSearchDistrict, labelDistrictInfo,btnBack);
        vbButtons.setAlignment(Pos.CENTER);
        BorderPane pane = new BorderPane();
        pane.setCenter(vbButtons);
        pane.setBottom(hButtons);*/

        btnBack.setOnAction(e1 -> menuScreen());

        Scene scene = new Scene(border, 650, 500);
        stage.setScene(scene);
        stage.setTitle("District Screen");
        stage.centerOnScreen();

    }
    private District findDistrictByName(String districtName) {
        for (int i = 0; i < dDistrict.length(); i++) {
            District district = dDistrict.get(i);
            if (district.getDistrictName().equalsIgnoreCase(districtName)) {
                return district;
            }
        }
        return null;
    }
    private void insertProject(Martyr martyrRecord) {
        String districtName = martyrRecord.getDistrict();
        String locationName = martyrRecord.getLocation();

        // Check if the district already exists
        District existingDistrict = findNameD(districtName);
        if (existingDistrict != null) {
            // District already exists
            Location existingLocation = findNameS(existingDistrict, locationName);
            if (existingLocation != null) {
                // Location exists within the district, add martyr to existing location
                existingLocation.addMartyr(martyrRecord);
            } else {
                // Location does not exist within the district, create new location and add martyr
                Location newLocation = new Location(locationName);
                newLocation.addMartyr(martyrRecord);
                existingDistrict.getsLocation().insert(newLocation);
            }
        } else {
            // District does not exist, create new district, location, and add martyr
            District newDistrict = new District(districtName);
            Location newLocation = new Location(locationName);
            newLocation.addMartyr(martyrRecord);
            newDistrict.getsLocation().insert(newLocation);
            dDistrict.insert(newDistrict);
        }
    }

    private boolean deleteMartyrRecord(String name, String dateOfDeath) {
        // Iterate over districts and locations to find the martyr to delete
        for (int i = 0; i < dDistrict.length(); i++) {
            District district = dDistrict.get(i);
            for (int j = 0; j < district.getsLocation().length(); j++) {
                Location location = district.getsLocation().get(j);
                SLinkedList<Martyr> martyrs = location.getSMartyr();
                for (int k = 0; k < martyrs.length(); k++) {
                    Martyr martyr = martyrs.get(k);
                    if (martyr.getName().equalsIgnoreCase(name) && martyr.getDateOfDeath().equalsIgnoreCase(dateOfDeath)) {
                        // Found the martyr, delete the record
                        martyrs.delete(martyr);
                        return true; // Successfully deleted the martyr
                    }
                }
            }
        }
        return false; // Martyr not found
    }


    private District findNameD(String districtName) {
        for (int i = 0; i < dDistrict.length(); i++) {
            District district = dDistrict.get(i);
            if (district.getDistrictName().equalsIgnoreCase(districtName)) {
                return district;
            }
        }
        return null;
    }

    private Location findNameS(District district, String locationName) {
        SLinkedList<Location> locations = district.getsLocation();
        for (int j = 0; j < locations.length(); j++) {
            Location location = locations.get(j);
            if (location.getLocationName().equalsIgnoreCase(locationName)) {
                return location;
            }
        }
        return null;
    }

    private Martyr findMartyrByName(Location location, String firstName, String secondName, String thirdName, String fourthName) {
        SLinkedList<Martyr> martyrs = location.getSMartyr();

        for (int i = 0; i < martyrs.length(); i++) {
            Martyr martyr = martyrs.get(i);

            // Check if the martyr's name matches any of the provided names
            if (martyrMatchesCriteria(martyr, firstName, secondName, thirdName, fourthName)) {
                return martyr; // Return the martyr if all names match
            }
        }

        return null; // Return null if martyr with specified names is not found in the location
    }

    private boolean martyrMatchesCriteria(Martyr martyr, String firstName, String secondName, String thirdName, String fourthName) {
        // Check if the martyr's name matches any of the provided names (case-insensitive)
        String martyrName = martyr.getName().toLowerCase();
        firstName = firstName.toLowerCase();
        secondName = secondName.toLowerCase();
        thirdName = thirdName.toLowerCase();
        fourthName = fourthName.toLowerCase();

        return martyrName.contains(firstName) &&
                (secondName.isEmpty() || martyrName.contains(secondName)) &&
                (thirdName.isEmpty() || martyrName.contains(thirdName)) &&
                (fourthName.isEmpty() || martyrName.contains(fourthName));
    }

    private Martyr findMartyrByFullName(Location location, String fullName) {
        SLinkedList<Martyr> martyrs = location.getSMartyr();
        for (int i = 0; i < martyrs.length(); i++) {
            Martyr martyr = martyrs.get(i);
            if (martyr.getName().equalsIgnoreCase(fullName)) {
                return martyr;
            }
        }
        return null; // Return null if martyr with specified full name is not found in the location
    }

    // Define a method to find a martyr by part of their name within a location
    private String findMartyrByPartOfName(District district, Location location, String partialName) {
        // Ensure the district and location are valid
        if (district == null || location == null || partialName.isEmpty()) {
            return ""; // Return an empty string if inputs are invalid
        }

        // Get the list of martyrs in the specified location
        SLinkedList<Martyr> martyrs = location.getSMartyr();

        // Initialize a string to store detailed information about matching martyrs
        StringBuilder result = new StringBuilder();

        // Iterate through the list of martyrs
        for (int i = 0; i < martyrs.length(); i++) {
            Martyr martyr = martyrs.get(i);
            String martyrName = martyr.getName();

            // Check if the martyr's name contains the partialName (case-insensitive)
            if (containsIgnoreCase(martyrName, partialName)) {
                // Append martyr information to the result string
                if (result.length() > 0) {
                    result.append("\n"); // Add new line between martyrs
                }
                result.append("Name: ").append(martyr.getName()).append(", ");
                result.append("Date: ").append(martyr.getDateOfDeath()).append(", ");
                result.append("Age: ").append(martyr.getAge()).append(", ");
                result.append("Location: ").append(location.getLocationName()).append(", ");
                result.append("District: ").append(district.getDistrictName()).append(", ");
                result.append("Gender: ").append(martyr.getGender()).append(", ");
            }
        }

        return result.toString(); // Return the concatenated string of martyr information
    }


    // Helper method to check if a string contains another string (case-insensitive)
    private boolean containsIgnoreCase(String source, String target) {
        if (source == null || target == null) {
            return false;
        }

        // Convert both strings to lowercase for case-insensitive comparison
        source = source.toLowerCase();
        target = target.toLowerCase();

        // Check if the source string contains the target string
        return source.contains(target);
    }

    private void displayDistrictStatistics() {
        if (currentDistrictIndex >= 0 && currentDistrictIndex < dDistrict.length()) {
            District currentDistrict = dDistrict.get(currentDistrictIndex);

            // Calculate district statistics
            int totalMartyrs = currentDistrict.getTotalMartyrs();
            int totalMaleMartyrs = currentDistrict.getTotalMaleMartyrs();
            int totalFemaleMartyrs = currentDistrict.getTotalFemaleMartyrs();
            double averageAge = currentDistrict.getAverageMartyrAge();
            String dateWithMaxMartyrs = currentDistrict.getDateWithMaxMartyrs();

            // Construct statistics text
            String statisticsText = "Statistics for District: " + currentDistrict.getDistrictName() + "\n";
            statisticsText += "Total Martyrs: " + totalMartyrs + "\n";
            statisticsText += "Total Male Martyrs: " + totalMaleMartyrs + "\n";
            statisticsText += "Total Female Martyrs: " + totalFemaleMartyrs + "\n";
            statisticsText += "Average Martyr Age: " + String.format("%.2f", averageAge) + "\n";
            statisticsText += "Date with Maximum Martyrs: " + dateWithMaxMartyrs + "\n";

            // Update the label to display statistics
            labelDistrictInfo.setStyle("-fx-text-fill: black; -fx-font-weight: normal;"); // Reset style
            labelDistrictInfo.setText(statisticsText);
        } else {
            // Invalid district index
            labelDistrictInfo.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            labelDistrictInfo.setText("Invalid district index.");
        }
    }


    public boolean isValidDateFormat(String date) {
        // Split the date string by '/' to extract components
        String[] parts = date.split("/");

        // Check if the split resulted in exactly three parts
        if (parts.length != 3) {
            return false;
        }

        try {
            // Extract month, day, and year components
            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            // Validate month (1-12), day (1-31), and year (1900-2099)
            if (month < 1 || month > 12 || day < 1 || day > 31 || year < 1900 || year > 2099) {
                return false;
            }

            // Additional validation: Ensure day is valid for the given month
            int daysInMonth = daysInMonth(month, year);
            if (day > daysInMonth || day < 1) {
                return false;
            }

            // Construct LocalDate object from components
            LocalDate inputDate = LocalDate.of(year, month, day);

            // Get the current local date
            LocalDate currentDate = LocalDate.now();

            // Compare input date with current date
            return inputDate.isBefore(currentDate);
        } catch (NumberFormatException e) {
            // If any component cannot be parsed as an integer, return false
            return false;
        }
    }

    // Helper method to determine the number of days in a given month and year (taking leap years into account)
    private int daysInMonth(int month, int year) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return (year % 4 == 0 ) ? 29 : 28;
            default:
                return 0;
        }
    }

    private Martyr findMartyrToUpdate(Location location, String name, String dateOfDeath) {
        SLinkedList<Martyr> martyrs = location.getSMartyr();

        // Iterate through the list of martyrs in the location
        for (int i = 0; i < martyrs.length(); i++) {
            Martyr martyr = martyrs.get(i);

            // Check if martyr's name and date of death match the specified parameters
            if (martyr.getName().equalsIgnoreCase(name) && martyr.getDateOfDeath().equalsIgnoreCase(dateOfDeath)) {
                return martyr; // Found the matching martyr
            }
        }

        return null; // Martyr not found for the specified name and date of death in the location
    }


}
