package com.legnatbird.seguimiento16legnatbird;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;


public class PersonManager {
  private static final File file = new File("data.txt");

  public static void writePersons(ObservableList<Person> datos) {
    FileWriter fw = null;
    try {
      if (!file.exists()) {
        if (!file.createNewFile()) {
          throw new RuntimeException("Is not possible to create the file");
        }
      }
      fw = new FileWriter(file, false);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert fw != null;
    BufferedWriter bw = new BufferedWriter(fw);
    PrintWriter pw = new PrintWriter(bw);
    for (Person persona : datos) {
      pw.println(persona.getId() + "," + persona.getName() + "," + persona.getLastName() + "," + persona.getGender());
    }
    pw.close();
  }

  public static ObservableList<Person> readData() {
    FileReader fr = null;
    try {
      if (!file.exists()) {
        if (!file.createNewFile()) {
          throw new RuntimeException("Is not possible to create the file");
        }
      }
      fr = new FileReader(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assert fr != null;
    BufferedReader br = new BufferedReader(fr);
    String line;
    ObservableList<Person> datos = FXCollections.observableArrayList();
    try {
      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");
        datos.add(new Person(data[0], data[1], data[2], data[3]));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return datos;
  }
}
