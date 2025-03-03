package com.example.gallery;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BorderPane_1 extends Application {

    private FlowPane imageContainer; // Container to hold image thumbnails
    private List<Image> imageList; // List to hold all images
    private List<File> imageFileList; // List to hold file paths of images
    private List<Image> recycleBin; // List to hold deleted images
    private List<File> recycleBinFiles; // List to hold file paths of deleted images
    private int currentImageIndex; // Index of the currently zoomed image

    private ImageView slideshowImageView; // ImageView for the slideshow
    private Timeline slideshowTimeline; // Timeline for the slideshow animation

    @Override
    public void start(Stage primaryStage) {
        // Initialize the recycle bin
        recycleBin = new ArrayList<>();
        recycleBinFiles = new ArrayList<>();

        // Create the main layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setStyle(
                "-fx-background-color: #0ab1e4;" + // Light gray background
                        "-fx-border-color: #a8d8ea;" + // Light blue border
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 10px;"
        );

        // Create a menu bar with a "Open Folder" option
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle(
                "-fx-background-color: #a8d8ea;" + // Light blue background for the menu bar
                        "-fx-text-fill: #ffffff;" // White text color
        );

        Menu fileMenu = new Menu("File");
        fileMenu.setStyle("-fx-text-fill: #ffffff;"); // White text for the menu

        MenuItem openFolderItem = new MenuItem("Open Folder");
        openFolderItem.setStyle(
                "-fx-background-color: #2196F3;" + // Blue background for menu items
                        "-fx-text-fill: #ffffff;" + // White text color
                        "-fx-font-size: 14px;" + // Larger font size
                        "-fx-padding: 10px 20px;" // Padding to make buttons bigger
        );

        MenuItem viewRecycleBinItem = new MenuItem("View Recycle Bin");
        viewRecycleBinItem.setStyle(
                "-fx-background-color: #2196F3;" + // Blue background for menu items
                        "-fx-text-fill: #ffffff;" + // White text color
                        "-fx-font-size: 14px;" + // Larger font size
                        "-fx-padding: 10px 20px;" // Padding to make buttons bigger
        );

        fileMenu.getItems().addAll(openFolderItem, viewRecycleBinItem);
        menuBar.getMenus().add(fileMenu);

        // Wrap the MenuBar in an HBox to center it
        HBox menuBarContainer = new HBox(menuBar);
        menuBarContainer.setAlignment(Pos.CENTER); // Center the MenuBar

        // Create a container for the images
        imageContainer = new FlowPane();
        imageContainer.setHgap(10);
        imageContainer.setVgap(10);
        imageContainer.setPadding(new Insets(10));
        imageContainer.setAlignment(Pos.CENTER); // Center align the images

        // Apply internal CSS to the image container
        imageContainer.setStyle(
                "-fx-background-color: #14a9e1;" + // White background
                        "-fx-border-color: #a8d8ea;" + // Light blue border
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);" // Add shadow effect
        );

        // Wrap the image container in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(imageContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: #0ab1e4;"); // Match the background color

        // Create a slideshow section
        slideshowImageView = new ImageView();
        slideshowImageView.setFitWidth(200); // Smaller size for the slideshow
        slideshowImageView.setFitHeight(150);
        slideshowImageView.setPreserveRatio(true);

        // Apply internal CSS to the slideshow image view
        slideshowImageView.setStyle(
                "-fx-border-color: #a8d8ea;" + // Light blue border
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);" // Add shadow effect
        );

        // Create a container for the slideshow
        VBox slideshowContainer = new VBox(slideshowImageView);
        slideshowContainer.setAlignment(Pos.TOP_RIGHT); // Position at the top-right
        slideshowContainer.setPadding(new Insets(10));
        slideshowContainer.setStyle(
                "-fx-background-color: #0ab1e4;" + // White background
                        "-fx-border-color: #a8d8ea;" + // Light blue border
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);" // Add shadow effect
        );

        // Add the menu bar container to the top of the BorderPane
        root.setTop(menuBarContainer);

        // Add the slideshow container to the top-right of the BorderPane
        root.setRight(slideshowContainer);

        // Add the scrollable image container to the center of the BorderPane
        root.setCenter(scrollPane);

        // Set up the scene and stage
        Scene scene = new Scene(root, 800, 600);

        // Apply global CSS to the scene
        scene.getRoot().setStyle(
                "-fx-font-family: 'Arial';" + // Set font family
                        "-fx-font-size: 14px;" // Set font size
        );

        primaryStage.setTitle("Image Gallery");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Handle "Open Folder" menu item click
        openFolderItem.setOnAction(event -> {
            // Create a DirectoryChooser
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Image Folder");

            // Add instructions to the DirectoryChooser dialog
            Label instructions = new Label("📁 Please select a folder containing images (JPG, PNG, JPEG).");
            instructions.setStyle(
                    "-fx-text-fill: #ffffff;" + // White text color
                            "-fx-font-size: 16px;" + // Larger font size
                            "-fx-font-weight: bold;" + // Bold text
                            "-fx-background-color: #ff6f61;" + // Coral background color
                            "-fx-padding: 10px;" + // Padding around the text
                            "-fx-border-color: #ffcc00;" + // Yellow border
                            "-fx-border-width: 2px;" + // Border width
                            "-fx-border-radius: 5px;" + // Rounded corners
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 0);" // Shadow effect
            );

            // Create a custom dialog layout
            VBox dialogLayout = new VBox(10, instructions);
            dialogLayout.setAlignment(Pos.CENTER);
            dialogLayout.setPadding(new Insets(20));
            dialogLayout.setStyle(
                    "-fx-background-color: #0ab1e4;" + // Light blue background
                            "-fx-border-color: #14a9e1;" + // Light blue border
                            "-fx-border-width: 2px;" +
                            "-fx-border-radius: 10px;"
            );

            // Show the DirectoryChooser
            File selectedDirectory = directoryChooser.showDialog(primaryStage);

            if (selectedDirectory != null) {
                showAlert("Loading Images", "Please wait while the folder is loading. This may take a few minutes.");
                loadImagesFromDirectory(selectedDirectory);
                startSlideshow(); // Start the slideshow after loading images
            }
        });

        // Handle "View Recycle Bin" menu item click
        viewRecycleBinItem.setOnAction(event -> viewRecycleBin());
    }

    /**
     * Load images from the selected directory and display them as thumbnails.
     *
     * @param directory The directory containing the images.
     */
    private void loadImagesFromDirectory(File directory) {
        // Clear existing images
        imageContainer.getChildren().clear();
        imageList = new ArrayList<>();
        imageFileList = new ArrayList<>();

        // Get all files in the directory
        File[] files = directory.listFiles();

        if (files == null) {
            showError("Invalid directory or no images found.");
            return;
        }

        // Load and display images
        for (File file : files) {
            if (isImageFile(file)) {
                Image image = new Image(file.toURI().toString());
                imageList.add(image);
                imageFileList.add(file);
                ImageView imageView = new ImageView(image);

                // Set thumbnail size
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                imageView.setPreserveRatio(true);

                // Apply internal CSS to the image view
                imageView.setStyle(
                        "-fx-border-color: #a8d8ea;" + // Light blue border
                                "-fx-border-width: 2px;" +
                                "-fx-border-radius: 10px;" +
                                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);" // Add shadow effect
                );

                // Add click event to zoom the image
                imageView.setOnMouseClicked(event -> {
                    currentImageIndex = imageList.indexOf(image);
                    zoomImage(image);
                });

                // Add the image to the container
                imageContainer.getChildren().add(imageView);
            }
        }
    }

    /**
     * Check if a file is an image (supports .jpg, .png, .jpeg).
     *
     * @param file The file to check.
     * @return True if the file is an image, false otherwise.
     */
    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg");
    }

    /**
     * Display an error message.
     *
     * @param message The error message to display.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Apply internal CSS to the alert dialog
        alert.getDialogPane().setStyle(
                "-fx-background-color: #ff6f61;" + // Coral background
                        "-fx-border-color: #ffcc00;" + // Yellow border
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-padding: 20px;" + // Add padding
                        "-fx-font-size: 14px;" // Increase font size
        );

        alert.showAndWait();
    }

    /**
     * Start the slideshow animation.
     */
    private void startSlideshow() {
        if (imageList.isEmpty()) {
            showError("No images found to start the slideshow.");
            return;
        }

        // Create a timeline to change images every 3 seconds
        slideshowTimeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    currentImageIndex = (currentImageIndex + 1) % imageList.size(); // Cycle through images
                    slideshowImageView.setImage(imageList.get(currentImageIndex));
                })
        );
        slideshowTimeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        slideshowTimeline.play();
    }

    /**
     * Zoom an image by displaying it in a new window.
     *
     * @param image The image to zoom.
     */
    private void zoomImage(Image image) {
        Stage zoomStage = new Stage();
        ImageView zoomedImageView = new ImageView(image);

        // Set the zoomed image size
        zoomedImageView.setFitWidth(600);
        zoomedImageView.setFitHeight(400);
        zoomedImageView.setPreserveRatio(true);

        // Apply internal CSS to the zoomed image view
        zoomedImageView.setStyle(
                "-fx-border-color: #a8d8ea;" + // Light blue border
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);" // Add shadow effect
        );

        // Create arrow buttons for navigation
        Button leftArrow = new Button("<");
        Button rightArrow = new Button(">");
        Button deleteButton = new Button("Delete");

        // Apply CSS styles to the buttons
        String buttonStyle = "-fx-background-color: #2196F3; " + // Blue background
                "-fx-text-fill: white; " +           // White text
                "-fx-font-size: 14px; " +            // Larger font size
                "-fx-padding: 10px 20px; " +         // Padding to make buttons bigger
                "-fx-border-radius: 5px; " +         // Rounded corners
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 0);"; // Shadow effect

        leftArrow.setStyle(buttonStyle);
        rightArrow.setStyle(buttonStyle);
        deleteButton.setStyle(buttonStyle);

        // Set minimum width for buttons to ensure text is fully visible
        leftArrow.setMinWidth(50);
        rightArrow.setMinWidth(50);
        deleteButton.setMinWidth(100);

        // Add action handlers for arrow buttons
        leftArrow.setOnAction(event -> {
            if (currentImageIndex > 0) {
                currentImageIndex--;
                zoomedImageView.setImage(imageList.get(currentImageIndex));
            }
        });

        rightArrow.setOnAction(event -> {
            if (currentImageIndex < imageList.size() - 1) {
                currentImageIndex++;
                zoomedImageView.setImage(imageList.get(currentImageIndex));
            }
        });

        // Add action handler for delete button
        deleteButton.setOnAction(event -> {
            // Remove the current image from the image list and add it to the recycle bin
            Image deletedImage = imageList.remove(currentImageIndex);
            File deletedFile = imageFileList.remove(currentImageIndex);
            recycleBin.add(deletedImage);
            recycleBinFiles.add(deletedFile);

            // If there are no more images, close the zoom window
            if (imageList.isEmpty()) {
                zoomStage.close();
                imageContainer.getChildren().clear(); // Clear the image container
                return;
            }

            // Update the current image index to show the next image (or the previous one if at the end)
            if (currentImageIndex >= imageList.size()) {
                currentImageIndex = imageList.size() - 1; // Move to the previous image
            }

            // Update the zoomed image view to show the next/previous image
            zoomedImageView.setImage(imageList.get(currentImageIndex));

            // Remove the deleted image from the image container
            imageContainer.getChildren().remove(currentImageIndex);
        });

        // Create a container for the navigation buttons
        HBox navigationContainer = new HBox(10, leftArrow, deleteButton, rightArrow);
        navigationContainer.setAlignment(Pos.CENTER);

        // Create a container for the zoomed image and arrows
        StackPane zoomRoot = new StackPane(zoomedImageView, navigationContainer);
        zoomRoot.setStyle(
                "-fx-background-color: #f4f4f4;" + // Light gray background
                        "-fx-padding: 10px;" // Add padding
        );

        // Position the arrows at the top of the zoomed image
        StackPane.setAlignment(navigationContainer, Pos.TOP_CENTER);

        // Create the scene and stage
        Scene zoomScene = new Scene(zoomRoot, 600, 450);
        zoomStage.setTitle("Zoomed Image");
        zoomStage.setScene(zoomScene);
        zoomStage.show();

        // Make the arrows disappear after 3 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> navigationContainer.setVisible(false));
        pause.play();

        // Show the arrows when the mouse enters the zoomed image
        zoomRoot.setOnMouseEntered(event -> {
            navigationContainer.setVisible(true);
            pause.playFromStart(); // Restart the timer
        });

        // Hide the arrows when the mouse exits the zoomed image
        zoomRoot.setOnMouseExited(event -> {
            pause.playFromStart(); // Restart the timer
        });
    }

    /**
     * View the recycle bin and allow restoring or permanently deleting images.
     */
    private void viewRecycleBin() {
        Stage recycleBinStage = new Stage();
        BorderPane recycleBinRoot = new BorderPane();
        recycleBinRoot.setPadding(new Insets(10));
        recycleBinRoot.setStyle(
                "-fx-background-color: #a6a7e4;" + // Light gray background
                        "-fx-border-color: #a8d8ea;" + // Light blue border
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 10px;"
        );

        // Create a list to track selected images
        List<Image> selectedImages = new ArrayList<>();
        List<File> selectedFiles = new ArrayList<>();

        // Create buttons for actions
        Button selectAllButton = new Button("Select All");
        Button restoreButton = new Button("Restore Selected");
        Button permanentDeleteButton = new Button("Permanently Delete Selected");
        Button clearBinButton = new Button("Clear Bin");

        // Apply CSS styles to the buttons
        String buttonStyle = "-fx-background-color: #2196F3; " + // Blue background
                "-fx-text-fill: white; " +           // White text
                "-fx-font-size: 14px; " +            // Larger font size
                "-fx-padding: 10px 20px; " +         // Padding to make buttons bigger
                "-fx-border-radius: 5px; " +         // Rounded corners
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 0);"; // Shadow effect

        selectAllButton.setStyle(buttonStyle);
        restoreButton.setStyle(buttonStyle);
        permanentDeleteButton.setStyle(buttonStyle);
        clearBinButton.setStyle(buttonStyle);

        // Set minimum width for buttons to ensure text is fully visible
        selectAllButton.setMinWidth(150);
        restoreButton.setMinWidth(150);
        permanentDeleteButton.setMinWidth(150);
        clearBinButton.setMinWidth(150);

        // Add action handlers for buttons
        selectAllButton.setOnAction(event -> {
            selectedImages.clear();
            selectedFiles.clear();
            selectedImages.addAll(recycleBin); // Select all images in the bin
            selectedFiles.addAll(recycleBinFiles); // Select all files in the bin
            showAlert("Selected All", "All images in the recycle bin have been selected.");
        });

        restoreButton.setOnAction(event -> {
            if (selectedImages.isEmpty()) {
                showAlert("No Selection", "Please select images to restore.");
                return;
            }
            imageList.addAll(selectedImages); // Restore selected images to the gallery
            imageFileList.addAll(selectedFiles); // Restore selected files to the gallery
            recycleBin.removeAll(selectedImages); // Remove them from the recycle bin
            recycleBinFiles.removeAll(selectedFiles); // Remove them from the recycle bin files
            selectedImages.clear(); // Clear the selection
            selectedFiles.clear(); // Clear the selection
            recycleBinStage.close(); // Close the recycle bin window
            viewRecycleBin(); // Refresh the recycle bin view
        });

        permanentDeleteButton.setOnAction(event -> {
            if (selectedImages.isEmpty()) {
                showAlert("No Selection", "Please select images to permanently delete.");
                return;
            }
            // Delete files from disk
            for (File file : selectedFiles) {
                if (file.exists()) {
                    file.delete(); // Permanently delete the file
                }
            }
            recycleBin.removeAll(selectedImages); // Remove them from the recycle bin
            recycleBinFiles.removeAll(selectedFiles); // Remove them from the recycle bin files
            selectedImages.clear(); // Clear the selection
            selectedFiles.clear(); // Clear the selection
            recycleBinStage.close(); // Close the recycle bin window
            viewRecycleBin(); // Refresh the recycle bin view
        });

        clearBinButton.setOnAction(event -> {
            if (recycleBin.isEmpty()) {
                showAlert("Bin Empty", "The recycle bin is already empty.");
                return;
            }
            // Delete all files from disk
            for (File file : recycleBinFiles) {
                if (file.exists()) {
                    file.delete(); // Permanently delete the file
                }
            }
            recycleBin.clear(); // Clear all images from the recycle bin
            recycleBinFiles.clear(); // Clear all files from the recycle bin
            selectedImages.clear(); // Clear the selection
            selectedFiles.clear(); // Clear the selection
            recycleBinStage.close(); // Close the recycle bin window
            viewRecycleBin(); // Refresh the recycle bin view
        });

        // Create a container for the action buttons
        VBox buttonContainer = new VBox(10, selectAllButton, restoreButton, permanentDeleteButton, clearBinButton);
        buttonContainer.setAlignment(Pos.TOP_LEFT); // Position buttons on the left side
        buttonContainer.setPadding(new Insets(10));

        // Create a scrollable container for the images
        ScrollPane scrollPane = new ScrollPane();
        VBox imageContainer = new VBox(10);
        imageContainer.setPadding(new Insets(10));
        imageContainer.setAlignment(Pos.CENTER);

        // Display images in the recycle bin
        for (Image image : recycleBin) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(true);

            CheckBox selectCheckBox = new CheckBox();
            selectCheckBox.setOnAction(event -> {
                if (selectCheckBox.isSelected()) {
                    selectedImages.add(image); // Add image to selected list
                    selectedFiles.add(recycleBinFiles.get(recycleBin.indexOf(image))); // Add file to selected list
                } else {
                    selectedImages.remove(image); // Remove image from selected list
                    selectedFiles.remove(recycleBinFiles.get(recycleBin.indexOf(image))); // Remove file from selected list
                }
            });

            HBox imageBox = new HBox(10, imageView, selectCheckBox);
            imageBox.setAlignment(Pos.CENTER);
            imageContainer.getChildren().add(imageBox);
        }

        scrollPane.setContent(imageContainer);
        scrollPane.setFitToWidth(true);

        // Add the button container to the left of the recycle bin root
        recycleBinRoot.setLeft(buttonContainer);

        // Add the scrollable container to the center of the recycle bin root
        recycleBinRoot.setCenter(scrollPane);

        Scene recycleBinScene = new Scene(recycleBinRoot, 600, 400);
        recycleBinStage.setTitle("Recycle Bin");
        recycleBinStage.setScene(recycleBinScene);
        recycleBinStage.show();
    }

    /**
     * Display an alert dialog.
     *
     * @param title   The title of the alert.
     * @param message The message to display.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Apply internal CSS to the alert dialog
        alert.getDialogPane().setStyle(
                "-fx-background-color: #a8d8ea;" + // Light blue background
                        "-fx-border-color: #ffcc00;" + // Yellow border
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-padding: 20px;" + // Add padding
                        "-fx-font-size: 14px;" // Increase font size
        );

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}