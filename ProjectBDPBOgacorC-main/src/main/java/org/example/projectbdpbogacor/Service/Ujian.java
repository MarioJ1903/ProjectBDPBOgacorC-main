package org.example.projectbdpbogacor.Service;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;

import java.time.LocalDateTime;

public class Ujian {
    private final IntegerProperty ujianId;          // ujian_id (PRIMARY KEY)
    private final StringProperty jenisUjian;       // jenis_ujian
    private final ObjectProperty<LocalDateTime> tanggal; // tanggal (TIMESTAMP)
    private final StringProperty kelasUsersUserId; // Kelas_Users_user_id (Part of FK to Kelas)
    private final IntegerProperty matpelMapelId;    // Matpel_mapel_id (FK to Matpel)
    private final IntegerProperty kelasKelasId;     // Kelas_kelas_id (Part of FK to Kelas)
    // Asumsi ada kolom durasi di Ujian, jika tidak ada di skema Anda bisa abaikan.


    // Constructor (dengan asumsi kolom tambahan)
    public Ujian(int ujianId, String jenisUjian, LocalDateTime tanggal,
                 String kelasUsersUserId, int matpelMapelId, int kelasKelasId) {
        this.ujianId = new SimpleIntegerProperty(ujianId);
        this.jenisUjian = new SimpleStringProperty(jenisUjian);
        this.tanggal = new SimpleObjectProperty<>(tanggal);
        this.kelasUsersUserId = new SimpleStringProperty(kelasUsersUserId);
        this.matpelMapelId = new SimpleIntegerProperty(matpelMapelId);
        this.kelasKelasId = new SimpleIntegerProperty(kelasKelasId);
    }

    public Ujian(int ujianId, String jenisUjian, LocalDateTime tanggal,
                 String kelasUsersUserId,  int kelasKelasId) {
        this.ujianId = new SimpleIntegerProperty(ujianId);
        this.jenisUjian = new SimpleStringProperty(jenisUjian);
        this.tanggal = new SimpleObjectProperty<>(tanggal);
        this.kelasUsersUserId = new SimpleStringProperty(kelasUsersUserId);
        this.matpelMapelId = new SimpleIntegerProperty();
        this.kelasKelasId = new SimpleIntegerProperty(kelasKelasId);
    }


    public Ujian(String jenisUjian, LocalDateTime tanggal) {
        this.ujianId = new SimpleIntegerProperty();
        this.jenisUjian = new SimpleStringProperty(jenisUjian);
        this.tanggal = new SimpleObjectProperty<>(tanggal);
        this.kelasUsersUserId = new SimpleStringProperty();
        this.matpelMapelId = new SimpleIntegerProperty();
        this.kelasKelasId = new SimpleIntegerProperty();
    }

    // Constructor (minimal sesuai skema yang Anda berikan)


    // Getters
    public int getUjianId() { return ujianId.get(); }
    public String getJenisUjian() { return jenisUjian.get(); }
    public LocalDateTime getTanggal() { return tanggal.get(); }
    public String getKelasUsersUserId() { return kelasUsersUserId.get(); }
    public int getMatpelMapelId() { return matpelMapelId.get(); }
    public int getKelasKelasId() { return kelasKelasId.get(); }


    // Property Methods
    public IntegerProperty ujianIdProperty() { return ujianId; }
    public StringProperty jenisUjianProperty() { return jenisUjian; }
    public ObjectProperty<LocalDateTime> tanggalProperty() { return tanggal; }
    public StringProperty kelasUsersUserIdProperty() { return kelasUsersUserId; }
    public IntegerProperty matpelMapelIdProperty() { return matpelMapelId; }
    public IntegerProperty kelasKelasIdProperty() { return kelasKelasId; }


    // Setters
    public void setJenisUjian(String jenisUjian) { this.jenisUjian.set(jenisUjian); }
    public void setTanggal(LocalDateTime tanggal) { this.tanggal.set(tanggal); }
    public void setKelasUsersUserId(String kelasUsersUserId) { this.kelasUsersUserId.set(kelasUsersUserId); }
    public void setMatpelMapelId(int matpelMapelId) { this.matpelMapelId.set(matpelMapelId); }
    public void setKelasKelasId(int kelasKelasId) { this.kelasKelasId.set(kelasKelasId); }

}