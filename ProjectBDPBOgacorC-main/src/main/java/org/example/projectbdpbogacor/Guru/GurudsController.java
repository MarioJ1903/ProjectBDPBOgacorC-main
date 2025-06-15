// ProjectBDPBOgacor/src/main/java/org/example/projectbdpbogacor/Guru/GurudsController.java
package org.example.projectbdpbogacor.Guru;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.projectbdpbogacor.Aset.AlertClass;
import org.example.projectbdpbogacor.DBSource.DBS;
import org.example.projectbdpbogacor.HelloApplication;
import org.example.projectbdpbogacor.Service.*;
import org.example.projectbdpbogacor.Tabel.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GurudsController {

    @FXML
    private Label welcomeUserLabel;
    @FXML
    private TabPane guruTabPane;

    // Class Schedule
    @FXML
    private ChoiceBox<String> scheduleClassChoiceBox; // Display: "Nama Kelas (Wali: Name)"
    @FXML
    private ChoiceBox<String> scheduleSubjectChoiceBox; // Display: "Nama Mapel"
    @FXML
    private TableView<JadwalEntry> jadwalKelasTable;
    @FXML
    private TableColumn<JadwalEntry, String> hariColumn;
    @FXML
    private TableColumn<JadwalEntry, String> jamMulaiColumn;
    @FXML
    private TableColumn<JadwalEntry, String> jamSelesaiColumn;
    @FXML
    private TableColumn<JadwalEntry, String> namaMapelColumn;
    @FXML
    private TableColumn<JadwalEntry, String> namaKelasJadwalColumn;

    // Input Grades
    @FXML
    private ChoiceBox<String> gradeClassChoiceBox; // Display: "Nama Kelas (Wali: Name)"
    @FXML
    private ChoiceBox<String> gradeSubjectChoiceBox; // Display: "Nama Mapel"
    @FXML
    private ChoiceBox<String> gradeStudentChoiceBox; // Display: "Student Name (NIS/NIP)"
    @FXML
    private ChoiceBox<String> gradeSemesterChoiceBox; // Display: "Tahun Ajaran - Semester"
    @FXML
    private ChoiceBox<String> gradeTypeChoiseBox; // ChoiceBox for jenis nilai
    @FXML
    private TextField scoreField;
    @FXML
    private TableView<NilaiEntry> existingGradesTable;
    @FXML
    private TableColumn<NilaiEntry, String> existingJenisNilaiColumn;
    @FXML
    private TableColumn<NilaiEntry, String> existingNamaMapelColumn;
    @FXML
    private TableColumn<NilaiEntry, Integer> existingNilaiColumn;
    // New Buttons for Grades
    @FXML
    private Button submitGradeButton; // Assuming you have a submit button
    @FXML
    private Button updateGradeButton;
    @FXML
    private Button deleteGradeButton;


    // Manage Assignments (New FXML Elements)
    @FXML
    private ChoiceBox<String> tugasClassChoiceBox;
    @FXML
    private ChoiceBox<String> tugasSubjectChoiceBox;
    @FXML
    private TextArea tugasKeteranganArea;
    @FXML
    private DatePicker tugasDeadlinePicker;
    @FXML
    private Button addTugasButton;
    @FXML
    private Button updateTugasButton; // New Update Button
    @FXML
    private Button deleteTugasButton; // New Delete Button
    @FXML
    private TableView<TugasEntry> tugasTable;
    @FXML
    private TableColumn<TugasEntry, String> tugasKeteranganColumn;
    @FXML
    private TableColumn<TugasEntry, String> tugasDeadlineColumn;
    @FXML
    private TableColumn<TugasEntry, String> tugasTanggalRilisColumn;
    @FXML
    private TableColumn<TugasEntry, String> tugasMapelColumn;
    @FXML
    private TableColumn<TugasEntry, String> tugasKelasColumn;
    // Removed MenuItem as we're adding direct buttons

    // Manage Materials (New FXML Elements)
    @FXML
    private ChoiceBox<String> materiClassChoiceBox;
    @FXML
    private ChoiceBox<String> materiSubjectChoiceBox;
    @FXML
    private TextField materiNamaMateriField;
    @FXML
    private Button addMateriButton;
    @FXML
    private Button updateMateriButton; // New Update Button
    @FXML
    private Button deleteMateriButton; // New Delete Button
    @FXML
    private TableView<MateriEntry> materiTable;
    @FXML
    private TableColumn<MateriEntry, String> materiNamaMateriColumn;
    @FXML
    private TableColumn<MateriEntry, String> materiMapelColumn;
    @FXML
    private TableColumn<MateriEntry, String> materiKelasColumn;
    // Removed MenuItem as we're adding direct buttons

    // Manage Exams (New FXML Elements)
    @FXML
    private ChoiceBox<String> ujianClassChoiceBox;
    @FXML
    private ChoiceBox<String> ujianSubjectChoiceBox;
    @FXML
    private ChoiceBox<String> ujianJenisUjianChoiceBox;
    @FXML
    private DatePicker ujianTanggalPicker;
    @FXML
    private Button addUjianButton;
    @FXML
    private Button updateUjianButton; // New Update Button
    @FXML
    private Button deleteUjianButton; // New Delete Button
    @FXML
    private TableView<UjianEntry> ujianTable;
    @FXML
    private TableColumn<UjianEntry, String> ujianJenisColumn;
    @FXML
    private TableColumn<UjianEntry, String> ujianTanggalColumn;
    @FXML
    private TableColumn<UjianEntry, String> ujianMapelColumn;
    @FXML
    private TableColumn<UjianEntry, String> ujianKelasColumn;
    // Removed MenuItem as we're adding direct buttons

    // Announcements (Updated FXML Elements for Guru to only view announcements)
    @FXML
    private TableView<Pengumuman> guruAnnouncementTable; // Table to view announcements
    @FXML
    private TableColumn<Pengumuman, String> guruAnnouncementWaktuColumn; // Column for announcement time
    @FXML
    private TableColumn<Pengumuman, String> guruAnnouncementContentColumn; // Column for announcement content

    // NEW: Manage Absensi Tab
    @FXML
    private ChoiceBox<String> absensiClassChoiceBox;
    @FXML
    private ChoiceBox<String> absensiSubjectChoiceBox;
    @FXML
    private ChoiceBox<String> absensiStudentChoiceBox;
    @FXML
    private DatePicker absensiDatePicker;
    @FXML
    private ChoiceBox<String> absensiStatusChoiceBox;
    @FXML
    private Button recordAbsensiButton;
    @FXML
    private TableView<AbsensiEntry> absensiTable;
    @FXML
    private TableColumn<AbsensiEntry, String> absensiTanggalColumn;
    @FXML
    private TableColumn<AbsensiEntry, String> absensiStatusColumn;
    @FXML
    private TableColumn<AbsensiEntry, String> absensiMapelColumn;
    @FXML
    private TableColumn<AbsensiEntry, String> absensiKelasColumn;
    @FXML
    private TableColumn<AbsensiEntry, String> absensiJamMulaiColumn;
    @FXML
    private TableColumn<AbsensiEntry, String> absensiJamSelesaiColumn;


    private String loggedInUserId;

    // Maps to store IDs corresponding to displayed names in ChoiceBoxes
    private Map<String, String> classesMap = new HashMap<>(); // Display -> Kelas_Users_user_id-kelas_id
    private Map<String, Integer> subjectsMap = new HashMap<>(); // Display -> mapel_id
    private Map<String, String> studentsMap = new HashMap<>(); // Display -> user_id
    private Map<String, Integer> semestersMap = new HashMap<>(); // Display -> semester_id
    private Map<String, Integer> jadwalMap = new HashMap<>(); // Display -> jadwal_id


    @FXML
    void initialize() {
        loggedInUserId = HelloApplication.getInstance().getLoggedInUserId();
        loadGuruName();

        // Initialize TableView columns
        initJadwalKelasTable();
        initExistingGradesTable();
        initTugasTable();
        initMateriTable();
        initUjianTable();
        initGuruAnnouncementTable(); // Initialize announcement table for viewing
        initAbsensiTable();

        // Load initial data for ChoiceBoxes
        loadClassesTaughtByGuru();
        loadSubjectsTaughtByGuru();
        loadSemesters();

        // Populate common choice boxes for assignments, materials, and exams
        populateCommonChoiceBoxes();

        // Add listeners for grade input
        gradeClassChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> handleGradeClassSelection());
        gradeSubjectChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> handleGradeSubjectSelection());
        gradeStudentChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> loadExistingGrades());
        gradeSemesterChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> loadExistingGrades());

        // Populate fixed choices for ujianJenisUjianChoiceBox and gradeTypeChoiseBox
        ujianJenisUjianChoiceBox.getItems().addAll("UTS", "UAS", "HARIAN");
        ujianJenisUjianChoiceBox.setValue("UTS");

        gradeTypeChoiseBox.getItems().addAll("UTS", "UAS", "TUGAS 1", "TUGAS 2", "TUGAS 3", "TUGAS 4");
        gradeTypeChoiseBox.setValue("TUGAS 1");

        // Add listeners for table selections to enable edit/delete buttons and populate fields
        setupGradeTableSelectionListener();
        setupTugasTableSelectionListener();
        setupMateriTableSelectionListener();
        setupUjianTableSelectionListener();


        // NEW: Absensi ChoiceBox listeners
        absensiClassChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleAbsensiClassSelection();
            loadAbsensiData(); // Reload data when class changes
        });
        absensiSubjectChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadStudentsForAbsensi(absensiClassChoiceBox.getValue(), newValue); // Reload students filtered by subject
            loadAbsensiData(); // Reload data when subject changes
        });
        absensiStudentChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> loadAbsensiData()); // Reload data when student changes
        absensiDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> loadAbsensiData()); // Reload data when date changes

        absensiStatusChoiceBox.getItems().addAll("Hadir", "Alpha", "Ijin","Sakit");
        absensiStatusChoiceBox.setValue("Hadir");

        // Add listeners to tabs to load data when selected
        guruTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                switch (newTab.getText()) {
                    case "Class Schedule":
                        loadClassesTaughtByGuru(); // Refresh classes
                        loadSubjectsTaughtByGuru(); // Refresh subjects
                        loadJadwalKelas(); // Load schedule for selected teacher/class/subject
                        break;
                    case "Input Grades":
                        loadClassesTaughtByGuru(); // Refresh classes
                        loadSubjectsTaughtByGuru(); // Refresh subjects
                        loadSemesters(); // Refresh semesters
                        loadExistingGrades(); // Ensure grades table is loaded
                        break;
                    case "Manage Assignments":
                        populateCommonChoiceBoxes(); // Refresh choices
                        loadTugas(); // Load assignments
                        break;
                    case "Manage Materials":
                        populateCommonChoiceBoxes(); // Refresh choices
                        loadMateri(); // Load materials
                        break;
                    case "Manage Exams":
                        populateCommonChoiceBoxes(); // Refresh choices
                        loadUjian(); // Load exams
                        break;
                    case "Announcements":
                        loadGuruAnnouncements(); // Load announcements (only viewing for Guru)
                        break;
                    case "Manage Absensi": // NEW: Handle "Manage Absensi" tab selection
                        loadAbsensiClasses(); // Refresh classes for absensi
                        loadAbsensiSubjects(); // Refresh subjects for absensi
                        loadAbsensiData(); // Load initial absensi data
                        break;
                }
            }
        });
        loadGuruAnnouncements(); // Load announcements initially when the controller starts
    }

    private void populateCommonChoiceBoxes() {
        // This method was missing. It populates common choice boxes for assignments, materials, and exams
        // by re-using the data loaded by loadClassesTaughtByGuru() and loadSubjectsTaughtByGuru().
        tugasClassChoiceBox.setItems(FXCollections.observableArrayList(classesMap.keySet()));
        tugasSubjectChoiceBox.setItems(FXCollections.observableArrayList(subjectsMap.keySet()));

        materiClassChoiceBox.setItems(FXCollections.observableArrayList(classesMap.keySet()));
        materiSubjectChoiceBox.setItems(FXCollections.observableArrayList(subjectsMap.keySet()));

        ujianClassChoiceBox.setItems(FXCollections.observableArrayList(classesMap.keySet()));
        ujianSubjectChoiceBox.setItems(FXCollections.observableArrayList(subjectsMap.keySet()));
    }

    private void loadGuruName() {
        String sql = "SELECT nama FROM Users WHERE user_id = ?";
        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, loggedInUserId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                welcomeUserLabel.setText("Welcome, " + rs.getString("nama") + "!");
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load user name", e.getMessage());
            e.printStackTrace();
        }
    }

    // --- Class Schedule Methods ---
    private void initJadwalKelasTable() {
        hariColumn.setCellValueFactory(new PropertyValueFactory<>("hari"));
        jamMulaiColumn.setCellValueFactory(new PropertyValueFactory<>("jamMulai"));
        jamSelesaiColumn.setCellValueFactory(new PropertyValueFactory<>("jamSelesai"));
        namaMapelColumn.setCellValueFactory(new PropertyValueFactory<>("namaMapel"));
        namaKelasJadwalColumn.setCellValueFactory(new PropertyValueFactory<>("namaKelas"));
    }

    private void loadClassesTaughtByGuru() {
        classesMap.clear();
        scheduleClassChoiceBox.getItems().clear();
        gradeClassChoiceBox.getItems().clear();
        absensiClassChoiceBox.getItems().clear();


        // Get classes associated with this teacher via Detail_Pengajar
        String sql = "SELECT DISTINCT k.kelas_id, k.nama_kelas, k.Users_user_id AS wali_id, u_wali.nama AS wali_name " +
                "FROM Kelas k " +
                "JOIN Detail_Pengajar dp ON k.kelas_id = dp.Kelas_kelas_id AND k.Users_user_id = dp.Kelas_Users_user_id " +
                "JOIN Users u_wali ON k.Users_user_id = u_wali.user_id " +
                "WHERE dp.Users_user_id = ?";

        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, loggedInUserId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int kelasId = rs.getInt("kelas_id");
                String namaKelas = rs.getString("nama_kelas");
                String waliId = rs.getString("wali_id");
                String waliName = rs.getString("wali_name");
                String display = namaKelas + " (Wali: " + waliName + ")";
                String combinedId = kelasId + "-" + waliId;
                classesMap.put(display, combinedId);
                scheduleClassChoiceBox.getItems().add(display);
                gradeClassChoiceBox.getItems().add(display);
                tugasClassChoiceBox.getItems().add(display); // Populate for Tugas
                materiClassChoiceBox.getItems().add(display); // Populate for Materi
                ujianClassChoiceBox.getItems().add(display); // Populate for Ujian
                absensiClassChoiceBox.getItems().add(display);
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load classes for teacher", e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadSubjectsTaughtByGuru() {
        subjectsMap.clear();
        scheduleSubjectChoiceBox.getItems().clear();
        gradeSubjectChoiceBox.getItems().clear();
        absensiSubjectChoiceBox.getItems().clear();

        // Get subjects associated with this teacher via Detail_Pengajar
        String sql = "SELECT DISTINCT m.mapel_id, m.nama_mapel FROM Matpel m " +
                "JOIN Detail_Pengajar dp ON m.mapel_id = dp.Matpel_mapel_id " +
                "WHERE dp.Users_user_id = ?";

        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, loggedInUserId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int mapelId = rs.getInt("mapel_id");
                String namaMapel = rs.getString("nama_mapel");
                subjectsMap.put(namaMapel, mapelId);
                scheduleSubjectChoiceBox.getItems().add(namaMapel);
                gradeSubjectChoiceBox.getItems().add(namaMapel);
                tugasSubjectChoiceBox.getItems().add(namaMapel); // Populate for Tugas
                materiSubjectChoiceBox.getItems().add(namaMapel); // Populate for Materi
                ujianSubjectChoiceBox.getItems().add(namaMapel); // Populate for Ujian
                absensiSubjectChoiceBox.getItems().add(namaMapel);
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load subjects for teacher", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void loadJadwalKelas() {
        ObservableList<JadwalEntry> jadwalList = FXCollections.observableArrayList();
        String selectedClassDisplay = scheduleClassChoiceBox.getValue();
        String selectedSubjectDisplay = scheduleSubjectChoiceBox.getValue();

        // Base query for schedules taught by this guru
        String sql = "SELECT j.hari, j.jam_mulai, j.jam_selsai, m.nama_mapel, k.nama_kelas " +
                "FROM Jadwal j " +
                "JOIN Matpel m ON j.Matpel_mapel_id = m.mapel_id " +
                "JOIN Kelas k ON j.Kelas_Users_user_id = k.Users_user_id AND j.Kelas_kelas_id = k.kelas_id " +
                "JOIN Detail_Pengajar dp ON dp.Matpel_mapel_id = m.mapel_id AND dp.Kelas_Users_user_id = k.Users_user_id AND dp.Kelas_kelas_id = k.kelas_id " +
                "WHERE dp.Users_user_id = ?";

        if (selectedClassDisplay != null && !selectedClassDisplay.isEmpty()) {
            String combinedClassId = classesMap.get(selectedClassDisplay);
            if (combinedClassId != null) {
                String[] ids = combinedClassId.split("-");
                sql += " AND k.kelas_id = ? AND k.Users_user_id = ?";
            }
        }
        if (selectedSubjectDisplay != null && !selectedSubjectDisplay.isEmpty()) {
            Integer mapelId = subjectsMap.get(selectedSubjectDisplay);
            if (mapelId != null) {
                sql += " AND m.mapel_id = ?";
            }
        }

        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            int paramIndex = 1;
            stmt.setString(paramIndex++, loggedInUserId);

            if (selectedClassDisplay != null && !selectedClassDisplay.isEmpty()) {
                String combinedClassId = classesMap.get(selectedClassDisplay);
                if (combinedClassId != null) {
                    String[] ids = combinedClassId.split("-");
                    stmt.setInt(paramIndex++, Integer.parseInt(ids[0]));
                    stmt.setString(paramIndex++, ids[1]);
                }
            }
            if (selectedSubjectDisplay != null && !selectedSubjectDisplay.isEmpty()) {
                Integer mapelId = subjectsMap.get(selectedSubjectDisplay);
                if (mapelId != null) {
                    stmt.setInt(paramIndex++, mapelId);
                }
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Jadwal jadwal = new Jadwal(
                        rs.getString("hari"),
                        rs.getString("jam_mulai"),
                        rs.getString("jam_selsai")
                );
                Matpel mapel = new Matpel(rs.getString("nama_mapel"));
                Kelas kelas = new Kelas(rs.getString("nama_kelas"));
                jadwalList.add(new JadwalEntry(
                        jadwal.getHari(),
                        jadwal.getJamMulai(),
                        jadwal.getJamSelesai(),
                        mapel.getNamaMapel(),
                        kelas.getNamaKelas(),
                        ""
                ));
            }
            jadwalKelasTable.setItems(jadwalList);
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load class schedule", e.getMessage());
            e.printStackTrace();
        }
    }

    // --- Input Grades Methods ---
    private void initExistingGradesTable() {
        existingJenisNilaiColumn.setCellValueFactory(new PropertyValueFactory<>("jenisNilai"));
        existingNamaMapelColumn.setCellValueFactory(new PropertyValueFactory<>("namaMapel"));
        existingNilaiColumn.setCellValueFactory(new PropertyValueFactory<>("nilai"));
    }

    private void setupGradeTableSelectionListener() {
        existingGradesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Populate fields when a row is selected
                gradeTypeChoiseBox.setValue(newSelection.getJenisNilai());
                // For subject, we need to map mapel_id back to display name or select from existing choices
                // This might be tricky if a teacher teaches the same subject in multiple classes and the mapel_id isn't unique across them
                // For simplicity, we'll assume the subject choice box is already populated with relevant subjects
                gradeSubjectChoiceBox.setValue(newSelection.getNamaMapel());
                scoreField.setText(String.valueOf(newSelection.getNilai()));

                // Enable update/delete buttons
                updateGradeButton.setDisable(false);
                deleteGradeButton.setDisable(false);
                submitGradeButton.setDisable(true); // Disable submit when editing
            } else {
                // Clear fields and disable buttons when no row is selected
                gradeTypeChoiseBox.getSelectionModel().clearSelection();
                scoreField.clear();

                updateGradeButton.setDisable(true);
                deleteGradeButton.setDisable(true);
                submitGradeButton.setDisable(false); // Enable submit when not editing
            }
        });
    }

    private void loadSemesters() {
        semestersMap.clear();
        gradeSemesterChoiceBox.getItems().clear();

        String sql = "SELECT semester_id, tahun_ajaran, semester FROM Semester";
        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Semester semester = new Semester(
                        rs.getInt("semester_id"),
                        rs.getString("tahun_ajaran"),
                        rs.getString("semester")
                );
                int semesterId = rs.getInt("semester_id");
                String tahunAjaran = rs.getString("tahun_ajaran");
                String semesterName = rs.getString("semester");
                String display = semester.getTahunAjaran() + " - " + semester.getNamaSemester();
                semestersMap.put(display, semester.getSemesterId());
                gradeSemesterChoiceBox.getItems().add(display);
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load semesters", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleGradeClassSelection() {
        String selectedClassDisplay = gradeClassChoiceBox.getValue();
        studentsMap.clear();
        gradeStudentChoiceBox.getItems().clear();

        if (selectedClassDisplay == null || selectedClassDisplay.isEmpty()) {
            loadExistingGrades();
            return;
        }

        String combinedClassId = classesMap.get(selectedClassDisplay);
        if (combinedClassId == null) {
            loadExistingGrades();
            return;
        }
        String[] ids = combinedClassId.split("-");
        int kelasId = Integer.parseInt(ids[0]);
        String waliUserId = ids[1];

        // Load students enrolled in the selected class
        String sql = "SELECT u.user_id, u.nama, u.NIS_NIP FROM Users u " +
                "JOIN Student_Class_Enrollment sce ON u.user_id = sce.Users_user_id " +
                "WHERE sce.Kelas_kelas_id = ? AND sce.Kelas_Users_user_id = ? AND u.Role_role_id = 'S'";
        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, kelasId);
            stmt.setString(2, waliUserId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Users student = new Users(rs.getString("user_id"),
                        rs.getString("NIS_NIP"),rs.getString("nama"));
                String userId = rs.getString("user_id");
                String nama = rs.getString("nama");
                String nisNip = rs.getString("NIS_NIP");
                String display = student.getNama() + " (NIS: " + student.getNisNip() + ")";
                studentsMap.put(display, student.getUserId());
                gradeStudentChoiceBox.getItems().add(display);
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load students for class", e.getMessage());
            e.printStackTrace();
        }
        loadExistingGrades();
    }

    @FXML
    void handleGradeSubjectSelection() {
        loadExistingGrades();
    }


    @FXML
    void handleSubmitGrade() {
        String selectedStudentDisplay = gradeStudentChoiceBox.getValue();
        String selectedSubjectDisplay = gradeSubjectChoiceBox.getValue();
        String selectedSemesterDisplay = gradeSemesterChoiceBox.getValue();
        String jenisNilai = gradeTypeChoiseBox.getValue();
        String scoreText = scoreField.getText();

        if (selectedStudentDisplay == null || selectedSubjectDisplay == null || selectedSemesterDisplay == null ||
                jenisNilai == null || scoreText.isEmpty()) {
            AlertClass.WarningAlert("Input Error", "Missing Information", "Please fill in all grade details.");
            return;
        }

        int score;
        try {
            score = Integer.parseInt(scoreText);
            if (score < 0 || score > 100) {
                AlertClass.WarningAlert("Input Error", "Invalid Score", "Score must be between 0 and 100.");
                return;
            }
        } catch (NumberFormatException e) {
            AlertClass.WarningAlert("Input Error", "Invalid Score", "Please enter a valid number for the score.");
            return;
        }

        String studentUserId = studentsMap.get(selectedStudentDisplay);
        Integer mapelId = subjectsMap.get(selectedSubjectDisplay);
        Integer semesterId = semestersMap.get(selectedSemesterDisplay);

        if (studentUserId == null || mapelId == null || semesterId == null) {
            AlertClass.ErrorAlert("Selection Error", "Invalid Selection", "Could not retrieve IDs for selected student, subject, or semester.");
            return;
        }

        try (Connection con = DBS.getConnection()) {
            // Check if this guru is assigned to teach this subject in the student's class
            String checkAssignmentSql = "SELECT COUNT(*) FROM Detail_Pengajar dp " +
                    "JOIN Student_Class_Enrollment sce ON dp.Kelas_kelas_id = sce.Kelas_kelas_id AND dp.Kelas_Users_user_id = sce.Kelas_Users_user_id " +
                    "WHERE dp.Users_user_id = ? AND dp.Matpel_mapel_id = ? AND sce.Users_user_id = ?";
            PreparedStatement checkAssignmentStmt = con.prepareStatement(checkAssignmentSql);
            checkAssignmentStmt.setString(1, loggedInUserId);
            checkAssignmentStmt.setInt(2, mapelId);
            checkAssignmentStmt.setString(3, studentUserId);
            ResultSet checkAssignmentRs = checkAssignmentStmt.executeQuery();
            if (checkAssignmentRs.next() && checkAssignmentRs.getInt(1) == 0) {
                AlertClass.ErrorAlert("Authorization Error", "Not Authorized", "You are not assigned to teach this subject to this student's class.");
                return;
            }


            // 1. Check if Rapor entry exists for this student and semester, create if not
            String raporIdSql = "SELECT rapor_id FROM Rapor WHERE Users_user_id = ? AND Semester_semester_id = ?";
            int raporId = -1;
            try (PreparedStatement raporStmt = con.prepareStatement(raporIdSql)) {
                raporStmt.setString(1, studentUserId);
                raporStmt.setInt(2, semesterId);
                ResultSet rs = raporStmt.executeQuery();
                if (rs.next()) {
                    raporId = rs.getInt("rapor_id");
                } else {
                    // Create new Rapor entry
                    String insertRaporSql = "INSERT INTO Rapor (Users_user_id, Semester_semester_id) VALUES (?, ?) RETURNING rapor_id";
                    try (PreparedStatement insertRaporStmt = con.prepareStatement(insertRaporSql)) {
                        insertRaporStmt.setString(1, studentUserId);
                        insertRaporStmt.setInt(2, semesterId);
                        ResultSet newRaporRs = insertRaporStmt.executeQuery();
                        if (newRaporRs.next()) {
                            raporId = newRaporRs.getInt("rapor_id");
                        }
                    }
                }
            }

            if (raporId == -1) {
                AlertClass.ErrorAlert("Database Error", "Failed to create or retrieve Rapor entry", "Could not associate grade with a report card.");
                return;
            }

            // NEW: Check for duplicate grade type for the same subject and student within the semester
            String checkDuplicateGradeSql = "SELECT COUNT(*) FROM Nilai n " +
                    "JOIN Rapor r ON n.Rapor_rapor_id = r.rapor_id " +
                    "WHERE r.Users_user_id = ? AND r.Semester_semester_id = ? AND n.Matpel_mapel_id = ? AND n.jenis_nilai = ?";
            try (PreparedStatement checkDuplicateStmt = con.prepareStatement(checkDuplicateGradeSql)) {
                checkDuplicateStmt.setString(1, studentUserId);
                checkDuplicateStmt.setInt(2, semesterId);
                checkDuplicateStmt.setInt(3, mapelId);
                checkDuplicateStmt.setString(4, jenisNilai);
                ResultSet duplicateRs = checkDuplicateStmt.executeQuery();
                if (duplicateRs.next() && duplicateRs.getInt(1) > 0) {
                    AlertClass.WarningAlert("Duplicate Grade Type", "Grade Type Already Exists", "A grade of type '" + jenisNilai + "' for subject '" + selectedSubjectDisplay + "' already exists for this student in this semester. Please update the existing grade or select a different type.");
                    return; // Prevent insertion
                }
            }
            // 2. Insert the grade
            String insertNilaiSql = "INSERT INTO Nilai (nilai, jenis_nilai, Matpel_mapel_id, Rapor_rapor_id) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(insertNilaiSql);
            stmt.setInt(1, score);
            stmt.setString(2, jenisNilai);
            stmt.setInt(3, mapelId);
            stmt.setInt(4, raporId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                AlertClass.InformationAlert("Success", "Grade Submitted", "Grade for " + selectedStudentDisplay + " submitted successfully.");
                scoreField.clear();
                loadExistingGrades();
            } else {
                AlertClass.ErrorAlert("Failed", "Grade Not Submitted", "Failed to submit grade to the database.");
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to submit grade", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleUpdateGrade() {
        NilaiEntry selectedGrade = existingGradesTable.getSelectionModel().getSelectedItem();
        if (selectedGrade == null) {
            AlertClass.WarningAlert("Selection Error", "No Grade Selected", "Please select a grade to update.");
            return;
        }

        String jenisNilai = gradeTypeChoiseBox.getValue();
        String scoreText = scoreField.getText();

        if (jenisNilai == null || scoreText.isEmpty()) {
            AlertClass.WarningAlert("Input Error", "Missing Information", "Please fill in all grade details.");
            return;
        }

        int score;
        try {
            score = Integer.parseInt(scoreText);
            if (score < 0 || score > 100) {
                AlertClass.WarningAlert("Input Error", "Invalid Score", "Score must be between 0 and 100.");
                return;
            }
        } catch (NumberFormatException e) {
            AlertClass.WarningAlert("Input Error", "Invalid Score", "Please enter a valid number for the score.");
            return;
        }

        try (Connection con = DBS.getConnection()) {
            String sql = "UPDATE Nilai SET jenis_nilai = ?, nilai = ? WHERE nilai_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, jenisNilai);
            stmt.setInt(2, score);
            stmt.setInt(3, selectedGrade.getNilaiId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                AlertClass.InformationAlert("Success", "Grade Updated", "Grade updated successfully.");
                scoreField.clear();
                existingGradesTable.getSelectionModel().clearSelection(); // Clear selection after update
                loadExistingGrades();
            } else {
                AlertClass.ErrorAlert("Failed", "Grade Not Updated", "Failed to update grade.");
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to update grade", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteGrade() {
        NilaiEntry selectedGrade = existingGradesTable.getSelectionModel().getSelectedItem();
        if (selectedGrade == null) {
            AlertClass.WarningAlert("Selection Error", "No Grade Selected", "Please select a grade to delete.");
            return;
        }

        Optional<javafx.scene.control.ButtonType> result = AlertClass.ConfirmationAlert(
                "Confirm Deletion",
                "Delete Grade",
                "Are you sure you want to delete this grade? This action cannot be undone."
        );

        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            try (Connection con = DBS.getConnection()) {
                String sql = "DELETE FROM Nilai WHERE nilai_id = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, selectedGrade.getNilaiId());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    AlertClass.InformationAlert("Success", "Grade Deleted", "Grade deleted successfully.");
                    scoreField.clear();
                    existingGradesTable.getSelectionModel().clearSelection(); // Clear selection after delete
                    loadExistingGrades();
                } else {
                    AlertClass.ErrorAlert("Failed", "Deletion Failed", "Failed to delete grade.");
                }
            } catch (SQLException e) {
                AlertClass.ErrorAlert("Database Error", "Failed to delete grade", e.getMessage());
                e.printStackTrace();
            }
        }
    }


    private void loadExistingGrades() {
        ObservableList<NilaiEntry> existingGrades = FXCollections.observableArrayList();
        String selectedStudentDisplay = gradeStudentChoiceBox.getValue();
        String selectedSubjectDisplay = gradeSubjectChoiceBox.getValue();
        String selectedSemesterDisplay = gradeSemesterChoiceBox.getValue();

        if (selectedStudentDisplay == null || selectedSubjectDisplay == null || selectedSemesterDisplay == null) {
            existingGradesTable.setItems(FXCollections.emptyObservableList());
            return;
        }

        String studentUserId = studentsMap.get(selectedStudentDisplay);
        Integer mapelId = subjectsMap.get(selectedSubjectDisplay);
        Integer semesterId = semestersMap.get(selectedSemesterDisplay);

        if (studentUserId == null || mapelId == null || semesterId == null) {
            existingGradesTable.setItems(FXCollections.emptyObservableList());
            return;
        }

        String sql = "SELECT n.nilai_id, n.jenis_nilai, m.nama_mapel, n.nilai " + // Select nilai_id
                "FROM Nilai n " +
                "JOIN Matpel m ON n.Matpel_mapel_id = m.mapel_id " +
                "JOIN Rapor r ON n.Rapor_rapor_id = r.rapor_id " +
                "WHERE r.Users_user_id = ? AND r.Semester_semester_id = ? AND n.Matpel_mapel_id = ?";
        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, studentUserId);
            stmt.setInt(2, semesterId);
            stmt.setInt(3, mapelId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Nilai nilai = new Nilai(
                        rs.getInt("nilai_id"),
                        rs.getString("jenis_nilai"),
                        rs.getInt("nilai")
                );
                Matpel mapel = new Matpel(rs.getString("nama_mapel"));

                existingGrades.add(new NilaiEntry(
                        nilai.getNilaiId(), // Pass nilai_id to the constructor
                        nilai.getJenisNilai(),
                        mapel.getNamaMapel(),
                        nilai.getNilaiAngka()
                ));
            }
            existingGradesTable.setItems(existingGrades);
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load existing grades", e.getMessage());
            e.printStackTrace();
        }
    }

    // --- Manage Assignments Methods ---
    private void initTugasTable() {
        tugasKeteranganColumn.setCellValueFactory(new PropertyValueFactory<>("keterangan"));
        tugasDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        tugasTanggalRilisColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalDirelease"));
        tugasMapelColumn.setCellValueFactory(new PropertyValueFactory<>("namaMapel"));
        tugasKelasColumn.setCellValueFactory(new PropertyValueFactory<>("namaKelas"));
    }

    private void setupTugasTableSelectionListener() {
        tugasTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tugasKeteranganArea.setText(newSelection.getKeterangan());
                tugasDeadlinePicker.setValue(LocalDate.parse(newSelection.getDeadline().substring(0, 10))); // Parse only date part
                tugasClassChoiceBox.setValue(newSelection.getNamaKelas()); // This might need mapping if display is complex
                tugasSubjectChoiceBox.setValue(newSelection.getNamaMapel()); // This might need mapping

                updateTugasButton.setDisable(false);
                deleteTugasButton.setDisable(false);
                addTugasButton.setDisable(true);
            } else {
                tugasKeteranganArea.clear();
                tugasDeadlinePicker.setValue(null);
                tugasClassChoiceBox.getSelectionModel().clearSelection();
                tugasSubjectChoiceBox.getSelectionModel().clearSelection();

                updateTugasButton.setDisable(true);
                deleteTugasButton.setDisable(true);
                addTugasButton.setDisable(false);
            }
        });
    }

    @FXML
    void handleAddTugas() {
        String selectedClassDisplay = tugasClassChoiceBox.getValue();
        String selectedSubjectDisplay = tugasSubjectChoiceBox.getValue();
        String keterangan = tugasKeteranganArea.getText();
        LocalDate deadlineDate = tugasDeadlinePicker.getValue();

        if (selectedClassDisplay == null || selectedSubjectDisplay == null || keterangan.isEmpty() || deadlineDate == null) {
            AlertClass.WarningAlert("Input Error", "Missing Information", "Please fill in all assignment details.");
            return;
        }

        String combinedClassId = classesMap.get(selectedClassDisplay);
        Integer mapelId = subjectsMap.get(selectedSubjectDisplay);

        if (combinedClassId == null || mapelId == null) {
            AlertClass.ErrorAlert("Selection Error", "Invalid Selection", "Could not retrieve IDs for selected class or subject.");
            return;
        }

        String[] ids = combinedClassId.split("-");
        int kelasId = Integer.parseInt(ids[0]);
        String waliUserId = ids[1];

        LocalDateTime deadline = deadlineDate.atTime(23, 59, 59);
        LocalDateTime releaseDate = LocalDateTime.now();

        try (Connection con = DBS.getConnection()) {
            // Check if this guru is assigned to teach this subject in this class
            String checkAssignmentSql = "SELECT COUNT(*) FROM Detail_Pengajar WHERE Users_user_id = ? AND Matpel_mapel_id = ? AND Kelas_Users_user_id = ? AND Kelas_kelas_id = ?";
            PreparedStatement checkAssignmentStmt = con.prepareStatement(checkAssignmentSql);
            checkAssignmentStmt.setString(1, loggedInUserId);
            checkAssignmentStmt.setInt(2, mapelId);
            checkAssignmentStmt.setString(3, waliUserId);
            checkAssignmentStmt.setInt(4, kelasId);
            ResultSet checkAssignmentRs = checkAssignmentStmt.executeQuery();
            if (checkAssignmentRs.next() && checkAssignmentRs.getInt(1) == 0) {
                AlertClass.ErrorAlert("Authorization Error", "Not Authorized", "You are not assigned to teach this subject in this class.");
                return;
            }

            String sql = "INSERT INTO Tugas (keterangan, deadline, tanggal_direlease, Kelas_Users_user_id, Matpel_mapel_id, Kelas_kelas_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, keterangan);
            stmt.setTimestamp(2, Timestamp.valueOf(deadline));
            stmt.setTimestamp(3, Timestamp.valueOf(releaseDate));
            stmt.setString(4, waliUserId);
            stmt.setInt(5, mapelId);
            stmt.setInt(6, kelasId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                AlertClass.InformationAlert("Success", "Assignment Added", "New assignment has been added successfully.");
                tugasKeteranganArea.clear();
                tugasDeadlinePicker.setValue(null);
                tugasClassChoiceBox.getSelectionModel().clearSelection();
                tugasSubjectChoiceBox.getSelectionModel().clearSelection();
                loadTugas();
            } else {
                AlertClass.ErrorAlert("Failed", "Assignment Not Added", "Failed to add assignment to the database.");
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to add assignment", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleUpdateTugas() {
        TugasEntry selectedTugas = tugasTable.getSelectionModel().getSelectedItem();
        if (selectedTugas == null) {
            AlertClass.WarningAlert("Selection Error", "No Assignment Selected", "Please select an assignment to update.");
            return;
        }

        String keterangan = tugasKeteranganArea.getText();
        LocalDate deadlineDate = tugasDeadlinePicker.getValue();

        if (keterangan.isEmpty() || deadlineDate == null) {
            AlertClass.WarningAlert("Input Error", "Missing Information", "Please fill in all assignment details.");
            return;
        }

        LocalDateTime deadline = deadlineDate.atTime(23, 59, 59);

        try (Connection con = DBS.getConnection()) {
            String sql = "UPDATE Tugas SET keterangan = ?, deadline = ? WHERE tugas_id = ? AND Kelas_Users_user_id = ? AND Kelas_kelas_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, keterangan);
            stmt.setTimestamp(2, Timestamp.valueOf(deadline));
            stmt.setInt(3, selectedTugas.getTugasId());
            stmt.setString(4, selectedTugas.getKelasWaliId());
            stmt.setInt(5, selectedTugas.getKelasId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                AlertClass.InformationAlert("Success", "Assignment Updated", "Assignment updated successfully.");
                tugasKeteranganArea.clear();
                tugasDeadlinePicker.setValue(null);
                tugasTable.getSelectionModel().clearSelection();
                loadTugas();
            } else {
                AlertClass.ErrorAlert("Failed", "Assignment Not Updated", "Failed to update assignment. It might not exist or you lack permission.");
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to update assignment", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void loadTugas() {
        ObservableList<TugasEntry> tugasList = FXCollections.observableArrayList();
        String sql = "SELECT t.tugas_id, t.keterangan, t.deadline, t.tanggal_direlease, m.nama_mapel, k.nama_kelas, t.Kelas_Users_user_id, t.Kelas_kelas_id " +
                "FROM Tugas t " +
                "JOIN Matpel m ON t.Matpel_mapel_id = m.mapel_id " +
                "JOIN Kelas k ON t.Kelas_Users_user_id = k.Users_user_id AND t.Kelas_kelas_id = k.kelas_id " +
                "JOIN Detail_Pengajar dp ON dp.Matpel_mapel_id = m.mapel_id AND dp.Kelas_Users_user_id = k.Users_user_id AND dp.Kelas_kelas_id = k.kelas_id " +
                "WHERE dp.Users_user_id = ? " +
                "ORDER BY t.deadline DESC";
        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, loggedInUserId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Tugas tugas = new Tugas(
                        rs.getInt("tugas_id"),
                        rs.getString("keterangan"),
                        rs.getTimestamp("deadline").toLocalDateTime(),
                        rs.getTimestamp("tanggal_direlease").toLocalDateTime(),
                        rs.getString("Kelas_Users_user_id"),
                        rs.getInt("Kelas_kelas_id")
                );
                Matpel mapel = new Matpel(rs.getString("nama_mapel"));
                Kelas kelas = new Kelas(rs.getString("nama_kelas"));
                tugasList.add(new TugasEntry(
                        tugas.getTugasId(),
                        tugas.getKeterangan(),
                        tugas.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        tugas.getTanggalDirelease().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        mapel.getNamaMapel(),
                        kelas.getNamaKelas(),
                        tugas.getKelasUsersUserId(),
                        tugas.getKelasKelasId()
                ));
            }
            tugasTable.setItems(tugasList);
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load assignments", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteTugas() {
        TugasEntry selectedTugas = tugasTable.getSelectionModel().getSelectedItem();
        if (selectedTugas == null) {
            AlertClass.WarningAlert("Selection Error", "No Assignment Selected", "Please select an assignment to delete.");
            return;
        }

        Optional<javafx.scene.control.ButtonType> result = AlertClass.ConfirmationAlert(
                "Confirm Deletion",
                "Delete Assignment",
                "Are you sure you want to delete this assignment? This action cannot be undone."
        );

        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            try (Connection con = DBS.getConnection()) {
                String sql = "DELETE FROM Tugas WHERE tugas_id = ? AND Kelas_Users_user_id = ? AND Kelas_kelas_id = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, selectedTugas.getTugasId());
                stmt.setString(2, selectedTugas.getKelasWaliId());
                stmt.setInt(3, selectedTugas.getKelasId());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    AlertClass.InformationAlert("Success", "Assignment Deleted", "Assignment deleted successfully.");
                    tugasKeteranganArea.clear();
                    tugasDeadlinePicker.setValue(null);
                    tugasTable.getSelectionModel().clearSelection();
                    loadTugas();
                } else {
                    AlertClass.ErrorAlert("Failed", "Deletion Failed", "Failed to delete assignment. It might not exist or you lack permission.");
                }
            } catch (SQLException e) {
                AlertClass.ErrorAlert("Database Error", "Failed to delete assignment", e.getMessage());
                e.printStackTrace();
            }
        }
    }


    // --- Manage Materials Methods ---
    private void initMateriTable() {
        materiNamaMateriColumn.setCellValueFactory(new PropertyValueFactory<>("namaMateri"));
        materiMapelColumn.setCellValueFactory(new PropertyValueFactory<>("namaMapel"));
        materiKelasColumn.setCellValueFactory(new PropertyValueFactory<>("namaKelas"));
    }

    private void setupMateriTableSelectionListener() {
        materiTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                materiNamaMateriField.setText(newSelection.getNamaMateri());
                materiClassChoiceBox.setValue(newSelection.getNamaKelas());
                materiSubjectChoiceBox.setValue(newSelection.getNamaMapel());

                updateMateriButton.setDisable(false);
                deleteMateriButton.setDisable(false);
                addMateriButton.setDisable(true);
            } else {
                materiNamaMateriField.clear();
                materiClassChoiceBox.getSelectionModel().clearSelection();
                materiSubjectChoiceBox.getSelectionModel().clearSelection();

                updateMateriButton.setDisable(true);
                deleteMateriButton.setDisable(true);
                addMateriButton.setDisable(false);
            }
        });
    }

    @FXML
    void handleAddMateri() {
        String selectedClassDisplay = materiClassChoiceBox.getValue();
        String selectedSubjectDisplay = materiSubjectChoiceBox.getValue();
        String namaMateri = materiNamaMateriField.getText();

        if (selectedClassDisplay == null || selectedSubjectDisplay == null || namaMateri.isEmpty()) {
            AlertClass.WarningAlert("Input Error", "Missing Information", "Please fill in all material details.");
            return;
        }

        String combinedClassId = classesMap.get(selectedClassDisplay);
        Integer mapelId = subjectsMap.get(selectedSubjectDisplay);

        if (combinedClassId == null || mapelId == null) {
            AlertClass.ErrorAlert("Selection Error", "Invalid Selection", "Could not retrieve IDs for selected class or subject.");
            return;
        }

        String[] ids = combinedClassId.split("-");
        int kelasId = Integer.parseInt(ids[0]);
        String waliUserId = ids[1];

        try (Connection con = DBS.getConnection()) {
            // Check if this guru is assigned to teach this subject in this class
            String checkAssignmentSql = "SELECT COUNT(*) FROM Detail_Pengajar WHERE Users_user_id = ? AND Matpel_mapel_id = ? AND Kelas_Users_user_id = ? AND Kelas_kelas_id = ?";
            PreparedStatement checkAssignmentStmt = con.prepareStatement(checkAssignmentSql);
            checkAssignmentStmt.setString(1, loggedInUserId);
            checkAssignmentStmt.setInt(2, mapelId);
            checkAssignmentStmt.setString(3, waliUserId);
            checkAssignmentStmt.setInt(4, kelasId);
            ResultSet checkAssignmentRs = checkAssignmentStmt.executeQuery();
            if (checkAssignmentRs.next() && checkAssignmentRs.getInt(1) == 0) {
                AlertClass.ErrorAlert("Authorization Error", "Not Authorized", "You are not assigned to teach this subject in this class.");
                return;
            }

            String sql = "INSERT INTO Materi (nama_materi, Kelas_Users_user_id, Matpel_mapel_id, Kelas_kelas_id) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, namaMateri);
            stmt.setString(2, waliUserId);
            stmt.setInt(3, mapelId);
            stmt.setInt(4, kelasId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                AlertClass.InformationAlert("Success", "Material Added", "New material has been added successfully.");
                materiNamaMateriField.clear();
                materiClassChoiceBox.getSelectionModel().clearSelection();
                materiSubjectChoiceBox.getSelectionModel().clearSelection();
                loadMateri();
            } else {
                AlertClass.ErrorAlert("Failed", "Material Not Added", "Failed to add material to the database.");
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to add material", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleUpdateMateri() {
        MateriEntry selectedMateri = materiTable.getSelectionModel().getSelectedItem();
        if (selectedMateri == null) {
            AlertClass.WarningAlert("Selection Error", "No Material Selected", "Please select a material to update.");
            return;
        }

        String namaMateri = materiNamaMateriField.getText();

        if (namaMateri.isEmpty()) {
            AlertClass.WarningAlert("Input Error", "Missing Information", "Please enter material name.");
            return;
        }

        try (Connection con = DBS.getConnection()) {
            String sql = "UPDATE Materi SET nama_materi = ? WHERE materi_id = ? AND Kelas_Users_user_id = ? AND Kelas_kelas_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, namaMateri);
            stmt.setInt(2, selectedMateri.getMateriId());
            stmt.setString(3, selectedMateri.getKelasWaliId());
            stmt.setInt(4, selectedMateri.getKelasId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                AlertClass.InformationAlert("Success", "Material Updated", "Material updated successfully.");
                materiNamaMateriField.clear();
                materiTable.getSelectionModel().clearSelection();
                loadMateri();
            } else {
                AlertClass.ErrorAlert("Failed", "Material Not Updated", "Failed to update material. It might not exist or you lack permission.");
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to update material", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void loadMateri() {
        ObservableList<MateriEntry> materiList = FXCollections.observableArrayList();
        String sql = "SELECT mt.materi_id, mt.nama_materi, m.nama_mapel, k.nama_kelas, mt.Kelas_Users_user_id, mt.Kelas_kelas_id " +
                "FROM Materi mt " +
                "JOIN Matpel m ON mt.Matpel_mapel_id = m.mapel_id " +
                "JOIN Kelas k ON mt.Kelas_Users_user_id = k.Users_user_id AND mt.Kelas_kelas_id = k.kelas_id " +
                "JOIN Detail_Pengajar dp ON dp.Matpel_mapel_id = m.mapel_id AND dp.Kelas_Users_user_id = k.Users_user_id AND dp.Kelas_kelas_id = k.kelas_id " +
                "WHERE dp.Users_user_id = ? " +
                "ORDER BY m.nama_mapel, k.nama_kelas, mt.nama_materi";
        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, loggedInUserId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Materi materi = new Materi(
                        rs.getInt("materi_id"),
                        rs.getString("nama_materi"),
                        rs.getString("Kelas_Users_user_id"),
                        rs.getInt("Kelas_kelas_id")
                );
                Matpel mapel = new Matpel(rs.getString("nama_mapel"));
                Kelas kelas = new Kelas(rs.getString("nama_kelas"));
                materiList.add(new MateriEntry(
                        materi.getMateriId(),
                        materi.getNamaMateri(),
                        mapel.getNamaMapel(),
                        kelas.getNamaKelas(),
                        materi.getKelasUsersUserId(),
                        materi.getKelasKelasId()
                ));
            }
            materiTable.setItems(materiList);
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load materials", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteMateri() {
        MateriEntry selectedMateri = materiTable.getSelectionModel().getSelectedItem();
        if (selectedMateri == null) {
            AlertClass.WarningAlert("Selection Error", "No Material Selected", "Please select a material to delete.");
            return;
        }

        Optional<javafx.scene.control.ButtonType> result = AlertClass.ConfirmationAlert(
                "Confirm Deletion",
                "Delete Material",
                "Are you sure you want to delete this material? This action cannot be undone."
        );

        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            try (Connection con = DBS.getConnection()) {
                String sql = "DELETE FROM Materi WHERE materi_id = ? AND Kelas_Users_user_id = ? AND Kelas_kelas_id = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, selectedMateri.getMateriId());
                stmt.setString(2, selectedMateri.getKelasWaliId());
                stmt.setInt(3, selectedMateri.getKelasId());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    AlertClass.InformationAlert("Success", "Material Deleted", "Material deleted successfully.");
                    materiNamaMateriField.clear();
                    materiTable.getSelectionModel().clearSelection();
                    loadMateri();
                } else {
                    AlertClass.ErrorAlert("Failed", "Deletion Failed", "Failed to delete material. It might not exist or you lack permission.");
                }
            } catch (SQLException e) {
                AlertClass.ErrorAlert("Database Error", "Failed to delete material", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // --- Manage Exams Methods ---
    private void initUjianTable() {
        ujianJenisColumn.setCellValueFactory(new PropertyValueFactory<>("jenisUjian"));
        ujianTanggalColumn.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        ujianMapelColumn.setCellValueFactory(new PropertyValueFactory<>("namaMapel"));
        ujianKelasColumn.setCellValueFactory(new PropertyValueFactory<>("namaKelas"));
    }

    private void setupUjianTableSelectionListener() {
        ujianTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ujianJenisUjianChoiceBox.setValue(newSelection.getJenisUjian());
                ujianTanggalPicker.setValue(LocalDate.parse(newSelection.getTanggal().substring(0, 10)));
                ujianClassChoiceBox.setValue(newSelection.getNamaKelas());
                ujianSubjectChoiceBox.setValue(newSelection.getNamaMapel());

                updateUjianButton.setDisable(false);
                deleteUjianButton.setDisable(false);
                addUjianButton.setDisable(true);
            } else {
                ujianJenisUjianChoiceBox.getSelectionModel().clearSelection();
                ujianTanggalPicker.setValue(null);
                ujianClassChoiceBox.getSelectionModel().clearSelection();
                ujianSubjectChoiceBox.getSelectionModel().clearSelection();

                updateUjianButton.setDisable(true);
                deleteUjianButton.setDisable(true);
                addUjianButton.setDisable(false);
            }
        });
    }

    @FXML
    void handleAddUjian() {
        String selectedClassDisplay = ujianClassChoiceBox.getValue();
        String selectedSubjectDisplay = ujianSubjectChoiceBox.getValue();
        String jenisUjian = ujianJenisUjianChoiceBox.getValue();
        LocalDate tanggalUjianDate = ujianTanggalPicker.getValue();

        if (selectedClassDisplay == null || selectedSubjectDisplay == null || jenisUjian == null || tanggalUjianDate == null) {
            AlertClass.WarningAlert("Input Error", "Missing Information", "Please fill in all exam details.");
            return;
        }

        String combinedClassId = classesMap.get(selectedClassDisplay);
        Integer mapelId = subjectsMap.get(selectedSubjectDisplay);

        if (combinedClassId == null || mapelId == null) {
            AlertClass.ErrorAlert("Selection Error", "Invalid Selection", "Could not retrieve IDs for selected class or subject.");
            return;
        }

        String[] ids = combinedClassId.split("-");
        int kelasId = Integer.parseInt(ids[0]);
        String waliUserId = ids[1];

        LocalDateTime tanggalUjian = tanggalUjianDate.atTime(8, 0, 0);

        try (Connection con = DBS.getConnection()) {
            // Check if this guru is assigned to teach this subject in this class
            String checkAssignmentSql = "SELECT COUNT(*) FROM Detail_Pengajar WHERE Users_user_id = ? AND Matpel_mapel_id = ? AND Kelas_Users_user_id = ? AND Kelas_kelas_id = ?";
            PreparedStatement checkAssignmentStmt = con.prepareStatement(checkAssignmentSql);
            checkAssignmentStmt.setString(1, loggedInUserId);
            checkAssignmentStmt.setInt(2, mapelId);
            checkAssignmentStmt.setString(3, waliUserId);
            checkAssignmentStmt.setInt(4, kelasId);
            ResultSet checkAssignmentRs = checkAssignmentStmt.executeQuery();
            if (checkAssignmentRs.next() && checkAssignmentRs.getInt(1) == 0) {
                AlertClass.ErrorAlert("Authorization Error", "Not Authorized", "You are not assigned to teach this subject in this class.");
                return;
            }

            String sql = "INSERT INTO Ujian (jenis_ujian, tanggal, Kelas_Users_user_id, Matpel_mapel_id, Kelas_kelas_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, jenisUjian);
            stmt.setTimestamp(2, Timestamp.valueOf(tanggalUjian));
            stmt.setString(3, waliUserId);
            stmt.setInt(4, mapelId);
            stmt.setInt(5, kelasId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                AlertClass.InformationAlert("Success", "Exam Added", "New exam has been added successfully.");
                ujianJenisUjianChoiceBox.getSelectionModel().clearSelection();
                ujianTanggalPicker.setValue(null);
                ujianClassChoiceBox.getSelectionModel().clearSelection();
                ujianSubjectChoiceBox.getSelectionModel().clearSelection();
                loadUjian();
            } else {
                AlertClass.ErrorAlert("Failed", "Exam Not Added", "Failed to add exam to the database.");
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to add exam", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleUpdateUjian() {
        UjianEntry selectedUjian = ujianTable.getSelectionModel().getSelectedItem();
        if (selectedUjian == null) {
            AlertClass.WarningAlert("Selection Error", "No Exam Selected", "Please select an exam to update.");
            return;
        }

        String jenisUjian = ujianJenisUjianChoiceBox.getValue();
        LocalDate tanggalUjianDate = ujianTanggalPicker.getValue();

        if (jenisUjian == null || tanggalUjianDate == null) {
            AlertClass.WarningAlert("Input Error", "Missing Information", "Please fill in all exam details.");
            return;
        }

        LocalDateTime tanggalUjian = tanggalUjianDate.atTime(8, 0, 0);

        try (Connection con = DBS.getConnection()) {
            String sql = "UPDATE Ujian SET jenis_ujian = ?, tanggal = ? WHERE ujian_id = ? AND Kelas_Users_user_id = ? AND Kelas_kelas_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, jenisUjian);
            stmt.setTimestamp(2, Timestamp.valueOf(tanggalUjian));
            stmt.setInt(3, selectedUjian.getUjianId());
            stmt.setString(4, selectedUjian.getKelasWaliId());
            stmt.setInt(5, selectedUjian.getKelasId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                AlertClass.InformationAlert("Success", "Exam Updated", "Exam updated successfully.");
                ujianJenisUjianChoiceBox.getSelectionModel().clearSelection();
                ujianTanggalPicker.setValue(null);
                ujianTable.getSelectionModel().clearSelection();
                loadUjian();
            } else {
                AlertClass.ErrorAlert("Failed", "Exam Not Updated", "Failed to update exam. It might not exist or you lack permission.");
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to update exam", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void loadUjian() {
        ObservableList<UjianEntry> ujianList = FXCollections.observableArrayList();
        String sql = "SELECT u.ujian_id, u.jenis_ujian, u.tanggal, m.nama_mapel, k.nama_kelas, u.Kelas_Users_user_id, u.Kelas_kelas_id " +
                "FROM Ujian u " +
                "JOIN Matpel m ON u.Matpel_mapel_id = m.mapel_id " +
                "JOIN Kelas k ON u.Kelas_Users_user_id = k.Users_user_id AND u.Kelas_kelas_id = k.kelas_id " +
                "JOIN Detail_Pengajar dp ON dp.Matpel_mapel_id = m.mapel_id AND dp.Kelas_Users_user_id = k.Users_user_id AND dp.Kelas_kelas_id = k.kelas_id " +
                "WHERE dp.Users_user_id = ? " +
                "ORDER BY u.tanggal DESC";
        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, loggedInUserId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Ujian ujian = new Ujian(rs.getInt("ujian_id"),
                        rs.getString("jenis_ujian"),
                        rs.getTimestamp("tanggal").toLocalDateTime(),
                        rs.getString("Kelas_Users_user_id"),rs.getInt("Kelas_kelas_id"));
                Matpel mapel = new Matpel(rs.getString("nama_mapel"));
                Kelas kelas = new Kelas(rs.getString("nama_kelas"));
                ujianList.add(new UjianEntry(
                        ujian.getUjianId(),
                        ujian.getJenisUjian(),
                        ujian.getTanggal().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        mapel.getNamaMapel(),
                        kelas.getNamaKelas(),
                        ujian.getKelasUsersUserId(),
                        ujian.getKelasKelasId()
                ));
            }
            ujianTable.setItems(ujianList);
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load exams", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteUjian() {
        UjianEntry selectedUjian = ujianTable.getSelectionModel().getSelectedItem();
        if (selectedUjian == null) {
            AlertClass.WarningAlert("Selection Error", "No Exam Selected", "Please select an exam to delete.");
            return;
        }

        Optional<javafx.scene.control.ButtonType> result = AlertClass.ConfirmationAlert(
                "Confirm Deletion",
                "Delete Exam",
                "Are you sure you want to delete this exam? This action cannot be undone."
        );

        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            try (Connection con = DBS.getConnection()) {
                String sql = "DELETE FROM Ujian WHERE ujian_id = ? AND Kelas_Users_user_id = ? AND Kelas_kelas_id = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, selectedUjian.getUjianId());
                stmt.setString(2, selectedUjian.getKelasWaliId());
                stmt.setInt(3, selectedUjian.getKelasId());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    AlertClass.InformationAlert("Success", "Exam Deleted", "Exam deleted successfully.");
                    ujianJenisUjianChoiceBox.getSelectionModel().clearSelection();
                    ujianTanggalPicker.setValue(null);
                    ujianTable.getSelectionModel().clearSelection();
                    loadUjian();
                } else {
                    AlertClass.ErrorAlert("Failed", "Deletion Failed", "Failed to delete exam. It might not exist or you lack permission.");
                }
            } catch (SQLException e) {
                AlertClass.ErrorAlert("Database Error", "Failed to delete exam", e.getMessage());
                e.printStackTrace();
            }
        }
    }


    // --- Announcements Methods for Guru ---
    private void initGuruAnnouncementTable() {
        guruAnnouncementWaktuColumn.setCellValueFactory(new PropertyValueFactory<>("waktu"));
        guruAnnouncementContentColumn.setCellValueFactory(new PropertyValueFactory<>("pengumumanContent"));
    }



    @FXML
    void loadGuruAnnouncements() {
        ObservableList<Pengumuman> announcementList = FXCollections.observableArrayList();
        String sql = "SELECT pengumuman_id, pengumuman, waktu, Users_user_id FROM Pengumuman ORDER BY waktu DESC";
        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("waktu");
                String originalContent = rs.getString("pengumuman");
                String userIdOfPoster = rs.getString("Users_user_id");

                String displayContent = originalContent;

                // [FIX] This part ensures the prefix is only added for display if not already present.
                // It's crucial to NOT modify the originalContent from the DB unless absolutely necessary,
                // just for display purposes. The regex should be robust.
                // Pattern: Starts with '[' (role text) ']' optional spaces (name text) ':' optional spaces (rest of content)
                java.util.regex.Pattern existingPrefixPattern = java.util.regex.Pattern.compile("^\\[[^\\]]+\\]\\s*[^:]+:\\s*");
                java.util.regex.Matcher matcher = existingPrefixPattern.matcher(originalContent);

                if (!matcher.find()) { // If the existing prefix pattern is NOT found at the beginning
                    try {
                        Pair<String, String> posterInfo = getUserRoleAndName(userIdOfPoster);
                        if (posterInfo.getKey() != null && posterInfo.getValue() != null) {
                            displayContent = "[" + posterInfo.getKey().toUpperCase() + "] " + posterInfo.getValue() + ": " + originalContent;
                        }
                    } catch (SQLException e) {
                        System.err.println("Error fetching poster info for announcement ID " + rs.getInt("pengumuman_id") + ": " + e.getMessage());
                        // Fallback to original content if poster info can't be fetched
                        displayContent = originalContent;
                    }
                }
                // Else, if a prefix WAS found by matcher.find(), then displayContent remains originalContent, which is what we want.

                announcementList.add(new Pengumuman(
                        rs.getInt("pengumuman_id"),
                        displayContent, // Use processed content for the pengumumanContent field
                        userIdOfPoster,
                        timestamp != null ? timestamp.toLocalDateTime() : null
                ));
            }
            guruAnnouncementTable.setItems(announcementList);
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load announcements", e.getMessage());
            e.printStackTrace();
        }
    }

    private Pair<String, String> getUserRoleAndName(String userId) throws SQLException {
        String roleName = null;
        String userName = null;
        String sql = "SELECT u.nama, r.role_name FROM Users u JOIN Role r ON u.Role_role_id = r.role_id WHERE u.user_id = ?";
        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Users user = new Users(rs.getString("nama"));
                Role role = new Role(rs.getString("role_name"));
                userName = user.getNama();
                roleName = role.getRoleName();
            }
        }
        return new Pair<>(roleName, userName);
    }

    // NEW: Manage Absensi Methods
    private void initAbsensiTable() {
        absensiTanggalColumn.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        absensiStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        absensiMapelColumn.setCellValueFactory(new PropertyValueFactory<>("namaMapel"));
        absensiKelasColumn.setCellValueFactory(new PropertyValueFactory<>("namaKelas"));
        absensiJamMulaiColumn.setCellValueFactory(new PropertyValueFactory<>("jamMulai"));
        absensiJamSelesaiColumn.setCellValueFactory(new PropertyValueFactory<>("jamSelesai"));
    }
    private void loadAbsensiClasses() {
        // Reuses loadClassesTaughtByGuru as it already populates absensiClassChoiceBox
        loadClassesTaughtByGuru();
    }
    private void loadAbsensiSubjects() {
        // Reuses loadSubjectsTaughtByGuru as it already populates absensiSubjectChoiceBox
        loadSubjectsTaughtByGuru();
    }

    @FXML
    private void handleAbsensiClassSelection() {
        String selectedClassDisplay = absensiClassChoiceBox.getValue();
        absensiStudentChoiceBox.getItems().clear();
        studentsMap.clear();

        if (selectedClassDisplay == null || selectedClassDisplay.isEmpty()) {
            return;
        }

        String combinedClassId = classesMap.get(selectedClassDisplay);
        if (combinedClassId == null) {
            return;
        }
        String[] ids = combinedClassId.split("-");
        int kelasId = Integer.parseInt(ids[0]);
        String waliUserId = ids[1];

        // Load students in the selected class
        String sql = "SELECT u.user_id, u.nama, u.NIS_NIP FROM Users u " +
                "JOIN Student_Class_Enrollment sce ON u.user_id = sce.Users_user_id " +
                "WHERE sce.Kelas_kelas_id = ? AND sce.Kelas_Users_user_id = ? AND u.Role_role_id = 'S'";
        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, kelasId);
            stmt.setString(2, waliUserId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Users user = new Users(rs.getString("user_id"), rs.getString("NIS_NIP"), rs.getString("nama"));
                String userId = rs.getString("user_id");
                String nama = rs.getString("nama");
                String nisNip = rs.getString("NIS_NIP");
                String display = user.getNama() + " (NIS: " + user.getNisNip() + ")";
                studentsMap.put(display, user.getUserId());
                absensiStudentChoiceBox.getItems().add(display);
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load students for attendance", e.getMessage());
            e.printStackTrace();
        }

        // Load subjects relevant to the selected class and current guru to populate absensiSubjectChoiceBox
        loadSubjectsForAbsensi(selectedClassDisplay);
        loadAbsensiData();
    }

    private void loadSubjectsForAbsensi(String selectedClassDisplay) {
        absensiSubjectChoiceBox.getItems().clear();
        subjectsMap.clear();

        if (selectedClassDisplay == null || selectedClassDisplay.isEmpty()) {
            return;
        }

        String combinedClassId = classesMap.get(selectedClassDisplay);
        if (combinedClassId == null) {
            return;
        }
        String[] ids = combinedClassId.split("-");
        int kelasId = Integer.parseInt(ids[0]);
        String waliUserId = ids[1];

        String sql = "SELECT DISTINCT m.mapel_id, m.nama_mapel FROM Matpel m " +
                "JOIN Detail_Pengajar dp ON m.mapel_id = dp.Matpel_mapel_id " +
                "WHERE dp.Users_user_id = ? AND dp.Kelas_kelas_id = ? AND dp.Kelas_Users_user_id = ?";

        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, loggedInUserId);
            stmt.setInt(2, kelasId);
            stmt.setString(3, waliUserId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Matpel mapel = new Matpel(rs.getInt("mapel_id"), rs.getString("nama_mapel"));
                int mapelId = rs.getInt("mapel_id");
                String namaMapel = rs.getString("nama_mapel");
                subjectsMap.put(mapel.getNamaMapel(), mapel.getMapelId());
                absensiSubjectChoiceBox.getItems().add(namaMapel);
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load subjects for absensi", e.getMessage());
            e.printStackTrace();
        }
    }


    private void loadStudentsForAbsensi(String selectedClassDisplay, String selectedSubjectDisplay) {
        absensiStudentChoiceBox.getItems().clear();
        studentsMap.clear();

        if (selectedClassDisplay == null || selectedClassDisplay.isEmpty() || selectedSubjectDisplay == null || selectedSubjectDisplay.isEmpty()) {
            return;
        }

        String combinedClassId = classesMap.get(selectedClassDisplay);
        Integer mapelId = subjectsMap.get(selectedSubjectDisplay);

        if (combinedClassId == null || mapelId == null) {
            return;
        }
        String[] ids = combinedClassId.split("-");
        int kelasId = Integer.parseInt(ids[0]);
        String waliUserId = ids[1];

        String sql = "SELECT u.user_id, u.nama, u.NIS_NIP FROM Users u " +
                "JOIN Student_Class_Enrollment sce ON u.user_id = sce.Users_user_id " +
                "WHERE sce.Kelas_kelas_id = ? AND sce.Kelas_Users_user_id = ? AND u.Role_role_id = 'S' " +
                "AND EXISTS (SELECT 1 FROM Jadwal j WHERE j.Kelas_kelas_id = sce.Kelas_kelas_id AND j.Kelas_Users_user_id = sce.Kelas_Users_user_id AND j.Matpel_mapel_id = ?)";
        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, kelasId);
            stmt.setString(2, waliUserId);
            stmt.setInt(3, mapelId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Users user = new Users(rs.getString("user_id"), rs.getString("NIS_NIP"), rs.getString("nama"));
                String userId = rs.getString("user_id");
                String nama = rs.getString("nama");
                String nisNip = rs.getString("NIS_NIP");
                String display = user.getNama() + " (NIS: " + user.getNisNip() + ")";
                studentsMap.put(display, user.getUserId());
                absensiStudentChoiceBox.getItems().add(display);
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load students for selected class and subject", e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    void recordAbsensi() {
        String selectedClassDisplay = absensiClassChoiceBox.getValue();
        String selectedSubjectDisplay = absensiSubjectChoiceBox.getValue();
        String selectedStudentDisplay = absensiStudentChoiceBox.getValue();
        LocalDate selectedDate = absensiDatePicker.getValue();
        String status = absensiStatusChoiceBox.getValue();

        if (selectedClassDisplay == null || selectedSubjectDisplay == null || selectedStudentDisplay == null || selectedDate == null || status == null) {
            AlertClass.WarningAlert("Input Error", "Missing Information", "Please select a class, subject, student, date, and status.");
            return;
        }

        String combinedClassId = classesMap.get(selectedClassDisplay);
        Integer mapelId = subjectsMap.get(selectedSubjectDisplay);
        String studentUserId = studentsMap.get(selectedStudentDisplay);

        if (combinedClassId == null || mapelId == null || studentUserId == null) {
            AlertClass.ErrorAlert("Selection Error", "Invalid Selection", "Could not retrieve IDs for selected class, subject, or student.");
            return;
        }

        String[] ids = combinedClassId.split("-");
        int kelasId = Integer.parseInt(ids[0]);
        String waliUserId = ids[1];

        // Find the jadwal_id for the selected class, subject, and day.
        // You might need to refine this if a class has multiple schedules for the same subject on different days/times.
        // For example, if "Matematika" is taught on Monday 08:00 and Monday 10:00.
        // For now, we'll assume one unique schedule per day for a given class and subject.
        Integer jadwalId = null;
        String dayOfWeekInIndonesian = convertDayToIndonesian(selectedDate.getDayOfWeek().toString());

        String sqlJadwal = "SELECT jadwal_id FROM Jadwal WHERE Kelas_kelas_id = ? AND Kelas_Users_user_id = ? AND Matpel_mapel_id = ? AND hari = ?";

        try (Connection con = DBS.getConnection();
             PreparedStatement stmtJadwal = con.prepareStatement(sqlJadwal)) {
            stmtJadwal.setInt(1, kelasId);
            stmtJadwal.setString(2, waliUserId);
            stmtJadwal.setInt(3, mapelId);
            stmtJadwal.setString(4, dayOfWeekInIndonesian);
            ResultSet rsJadwal = stmtJadwal.executeQuery();

            if (rsJadwal.next()) {
                jadwalId = rsJadwal.getInt("jadwal_id");
            } else {
                AlertClass.WarningAlert("Schedule Not Found", "No matching schedule found for this class, subject, and selected date's day (" + dayOfWeekInIndonesian + ").", "Please ensure a schedule exists for the selected criteria.");
                return; // Exit if no schedule is found
            }
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to retrieve schedule ID", e.getMessage());
            e.printStackTrace();
            return;
        }

        try (Connection con = DBS.getConnection()) {
            // Check if attendance already exists for this student, date, and jadwal
            String checkAbsensiSql = "SELECT absensi_id FROM Absensi WHERE Users_user_id = ? AND tanggal::date = ? AND Jadwal_jadwal_id = ?";
            PreparedStatement checkAbsensiStmt = con.prepareStatement(checkAbsensiSql);
            checkAbsensiStmt.setString(1, studentUserId);
            checkAbsensiStmt.setDate(2, java.sql.Date.valueOf(selectedDate));
            checkAbsensiStmt.setInt(3, jadwalId);
            ResultSet rsCheck = checkAbsensiStmt.executeQuery();

            if (rsCheck.next()) {
                // Update existing attendance
                int absensiIdToUpdate = rsCheck.getInt("absensi_id");
                String updateSql = "UPDATE Absensi SET status = ? WHERE absensi_id = ?";
                PreparedStatement updateStmt = con.prepareStatement(updateSql);
                updateStmt.setString(1, status);
                updateStmt.setInt(2, absensiIdToUpdate);
                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    AlertClass.InformationAlert("Success", "Attendance Updated", "Attendance for " + selectedStudentDisplay + " on " + selectedDate + " updated to " + status + ".");
                } else {
                    AlertClass.ErrorAlert("Failed", "Attendance Not Updated", "Failed to update attendance.");
                }
            } else {
                // Insert new attendance
                String insertSql = "INSERT INTO Absensi (tanggal, status, Users_user_id, Jadwal_jadwal_id) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = con.prepareStatement(insertSql);
                // The 'tanggal' column is TIMESTAMP NOT NULL. We need to provide a time component.
                // For attendance, a common approach is to use the start time of the class from the schedule.
                // However, Jadwal table only has jam_mulai as VARCHAR, not TIMESTAMP.
                // For simplicity, let's use the start of the selected date. If exact class time is needed,
                // you'd query jam_mulai from Jadwal and combine it with selectedDate.
                insertStmt.setTimestamp(1, Timestamp.valueOf(selectedDate.atStartOfDay()));
                insertStmt.setString(2, status);
                insertStmt.setString(3, studentUserId);
                insertStmt.setInt(4, jadwalId);

                int rowsAffected = insertStmt.executeUpdate();
                if (rowsAffected > 0) {
                    AlertClass.InformationAlert("Success", "Attendance Recorded", "Attendance for " + selectedStudentDisplay + " on " + selectedDate + " recorded as " + status + ".");
                } else {
                    AlertClass.ErrorAlert("Failed", "Attendance Not Recorded", "Failed to record attendance.");
                }
            }
            loadAbsensiData(); // Refresh the table
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to record/update attendance", e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper to convert Java DayOfWeek to Indonesian if your DB uses it
    private String convertDayToIndonesian(String englishDay) {
        switch (englishDay) {
            case "MONDAY": return "Senin";
            case "TUESDAY": return "Selasa";
            case "WEDNESDAY": return "Rabu";
            case "THURSDAY": return "Kamis";
            case "FRIDAY": return "Jumat";
            case "SATURDAY": return "Sabtu";
            case "SUNDAY": return "Minggu";
            default: return englishDay; // Fallback
        }
    }
    @FXML
    void loadAbsensiData() {
        ObservableList<AbsensiEntry> absensiList = FXCollections.observableArrayList();
        String selectedClassDisplay = absensiClassChoiceBox.getValue();
        String selectedSubjectDisplay = absensiSubjectChoiceBox.getValue();
        String selectedStudentDisplay = absensiStudentChoiceBox.getValue();
        LocalDate selectedDate = absensiDatePicker.getValue();

        StringBuilder sqlBuilder = new StringBuilder(
                "SELECT a.tanggal, a.status, m.nama_mapel, k.nama_kelas, j.jam_mulai, j.jam_selsai " +
                        "FROM Absensi a " +
                        "JOIN Users u ON a.Users_user_id = u.user_id " +
                        "JOIN Jadwal j ON a.Jadwal_jadwal_id = j.jadwal_id " +
                        "JOIN Matpel m ON j.Matpel_mapel_id = m.mapel_id " +
                        "JOIN Kelas k ON j.Kelas_Users_user_id = k.Users_user_id AND j.Kelas_kelas_id = k.kelas_id " +
                        "JOIN Detail_Pengajar dp ON dp.Matpel_mapel_id = m.mapel_id AND dp.Kelas_Users_user_id = k.Users_user_id AND dp.Kelas_kelas_id = k.kelas_id " +
                        "WHERE dp.Users_user_id = ? " // Guru's ID is always the first parameter
        );

        ObservableList<Object> params = FXCollections.observableArrayList();
        params.add(loggedInUserId); // Parameter 1: Guru's ID

        // Class filter: Build SQL and collect parameters
        if (selectedClassDisplay != null && !selectedClassDisplay.isEmpty()) {
            String combinedClassId = classesMap.get(selectedClassDisplay);
            if (combinedClassId != null) {
                sqlBuilder.append(" AND k.kelas_id = ? AND k.Users_user_id = ?");
                String[] ids = combinedClassId.split("-");
                params.add(Integer.parseInt(ids[0]));
                params.add(ids[1]);
            } else {
                absensiTable.setItems(FXCollections.emptyObservableList());
                return;
            }
        } else {
            absensiTable.setItems(FXCollections.emptyObservableList());
            return;
        }

        // Subject filter: Build SQL and collect parameters
        if (selectedSubjectDisplay != null && !selectedSubjectDisplay.isEmpty()) {
            Integer mapelId = subjectsMap.get(selectedSubjectDisplay);
            if (mapelId != null) {
                sqlBuilder.append(" AND m.mapel_id = ?");
                params.add(mapelId);
            } else {
                absensiTable.setItems(FXCollections.emptyObservableList());
                return;
            }
        } else {
            absensiTable.setItems(FXCollections.emptyObservableList());
            return;
        }

        // Student filter (optional): Build SQL and collect parameters
        if (selectedStudentDisplay != null && !selectedStudentDisplay.isEmpty()) {
            String studentId = studentsMap.get(selectedStudentDisplay);
            if (studentId != null) {
                sqlBuilder.append(" AND u.user_id = ?");
                params.add(studentId);
            } else {
                absensiTable.setItems(FXCollections.emptyObservableList());
                return;
            }
        }

        // Date filter (optional): Build SQL and collect parameters
        if (selectedDate != null) {
            sqlBuilder.append(" AND a.tanggal::date = ?");
            params.add(java.sql.Date.valueOf(selectedDate));
        }

        sqlBuilder.append(" ORDER BY a.tanggal DESC");

        try (Connection con = DBS.getConnection();
             PreparedStatement stmt = con.prepareStatement(sqlBuilder.toString())) {

            // Bind parameters to the PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    stmt.setString(i + 1, (String) param);
                } else if (param instanceof Integer) {
                    stmt.setInt(i + 1, (Integer) param);
                } else if (param instanceof java.sql.Date) {
                    stmt.setDate(i + 1, (java.sql.Date) param);
                }
                // Add other types if necessary
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Absensi absen = new Absensi(rs.getTimestamp("tanggal").toLocalDateTime()
                        ,rs.getString("status") );
                Matpel mapel = new Matpel(rs.getString("nama_mapel"));
                Kelas kelas = new Kelas(rs.getString("nama_kelas"));
                Jadwal jadwal = new Jadwal(rs.getString("jam_mulai"), rs.getString("jam_selsai"));
                absensiList.add(new AbsensiEntry(
                        absen.getTanggal().toString(),
                        absen.getStatus(),
                        mapel.getNamaMapel(),
                        kelas.getNamaKelas(),
                        jadwal.getJamMulai(),
                        jadwal.getJamSelesai()
                ));
            }
            absensiTable.setItems(absensiList);
        } catch (SQLException e) {
            AlertClass.ErrorAlert("Database Error", "Failed to load attendance data", e.getMessage());
            e.printStackTrace();
            absensiTable.setItems(FXCollections.emptyObservableList());
        }
    }


    @FXML
    void handleLogout() {
        try {
            HelloApplication.getInstance().changeScene("login-view.fxml", "Login Aplikasi", 1300, 700);
        } catch (IOException e) {
            AlertClass.ErrorAlert("Error", "Logout Failed", "Could not return to login screen.");
            e.printStackTrace();
        }
    }
}