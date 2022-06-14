module com.example.pnvstudentmanagement {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires org.kordamp.bootstrapfx.core;
  requires java.sql;
  opens com.example.watchmanagement to javafx.fxml;
  exports com.example.watchmanagement;
  exports com.example;
  opens com.example to javafx.fxml;
}