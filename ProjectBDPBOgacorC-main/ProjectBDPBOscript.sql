CREATE TABLE Role
(
    role_id   VARCHAR(1)  NOT NULL,
    role_name VARCHAR(50) NOT NULL,
    CONSTRAINT Role_PK PRIMARY KEY (role_id)
);

CREATE TABLE Users
(
    user_id     VARCHAR(7)   NOT NULL,
    username    VARCHAR(100) NOT NULL,
    password    VARCHAR(200) NOT NULL,
    NIS_NIP     VARCHAR(50)  NOT NULL,
    nama        VARCHAR(100) NOT NULL,
    gender      VARCHAR(1)   NOT NULL,
    alamat      VARCHAR(255) NOT NULL,
    email       VARCHAR(100) NOT NULL,
    nomer_hp    VARCHAR(20)  NOT NULL,
    Role_role_id VARCHAR(50)  NOT NULL,
    CONSTRAINT Users_PK PRIMARY KEY (user_id),
    CONSTRAINT Users_Role_FK FOREIGN KEY (Role_role_id)
        REFERENCES Role (role_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE Semester
(
    semester_id  SERIAL PRIMARY KEY,
    tahun_ajaran VARCHAR(20) NOT NULL,
    semester     VARCHAR(20) NOT NULL,
    tahun        TIMESTAMP   NOT NULL
);

CREATE TABLE Kelas
(
    kelas_id            SERIAL       NOT NULL,
    nama_kelas          VARCHAR(100) NOT NULL,
    keterangan          VARCHAR(255) NOT NULL,
    Users_user_id       VARCHAR(7)   NOT NULL,
    Semester_semester_id SERIAL       NOT NULL,
    CONSTRAINT Kelas_PK PRIMARY KEY (Users_user_id, kelas_id),
    CONSTRAINT Kelas_Semester_FK FOREIGN KEY (Semester_semester_id)
        REFERENCES Semester (semester_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT Kelas_Users_FK FOREIGN KEY (Users_user_id)
        REFERENCES Users (user_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE Matpel
(
    mapel_id   SERIAL PRIMARY KEY,
    nama_mapel VARCHAR(100) NOT NULL,
    category   VARCHAR(50)  NOT NULL
);

CREATE TABLE Jadwal
(
    jadwal_id          SERIAL PRIMARY KEY,
    hari               VARCHAR(20) NOT NULL,
    jam_mulai          VARCHAR(10) NOT NULL,
    jam_selsai         VARCHAR(10) NOT NULL,
    Kelas_Users_user_id VARCHAR(7)  NOT NULL,
    Matpel_mapel_id    SERIAL      NOT NULL,
    Kelas_kelas_id     SERIAL      NOT NULL,
    CONSTRAINT Jadwal_Kelas_FK FOREIGN KEY (Kelas_Users_user_id, Kelas_kelas_id)
        REFERENCES Kelas (Users_user_id, kelas_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT Jadwal_Matpel_FK FOREIGN KEY (Matpel_mapel_id)
        REFERENCES Matpel (mapel_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE Absensi
(
    absensi_id   SERIAL PRIMARY KEY,
    tanggal      TIMESTAMP   NOT NULL,
    status       VARCHAR(50) NOT NULL,
    Users_user_id VARCHAR(7)  NOT NULL,
    Jadwal_jadwal_id SERIAL      NOT NULL,
    CONSTRAINT Absensi_Jadwal_FK FOREIGN KEY (Jadwal_jadwal_id)
        REFERENCES Jadwal (jadwal_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT Absensi_Users_FK FOREIGN KEY (Users_user_id)
        REFERENCES Users (user_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE Detail_Pengajar
(
    Users_user_id        VARCHAR(7) NOT NULL,
    Kelas_Users_user_id VARCHAR(7) NOT NULL,
    Matpel_mapel_id      SERIAL     NOT NULL,
    Kelas_kelas_id       SERIAL     NOT NULL,
    CONSTRAINT Detail_Pengajar_FK PRIMARY KEY (Users_user_id, Kelas_Users_user_id, Matpel_mapel_id, Kelas_kelas_id),
    CONSTRAINT Detail_Pengajar_Kelas_FK FOREIGN KEY (Kelas_Users_user_id, Kelas_kelas_id)
        REFERENCES Kelas (Users_user_id, kelas_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT Detail_Pengajar_Matpel_FK FOREIGN KEY (Matpel_mapel_id)
        REFERENCES Matpel (mapel_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT Detail_Pengajar_Users_FK FOREIGN KEY (Users_user_id)
        REFERENCES Users (user_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE Materi
(
    materi_id           SERIAL PRIMARY KEY,
    nama_materi         VARCHAR(255) NOT NULL,
    Kelas_Users_user_id VARCHAR(7)   NOT NULL,
    Matpel_mapel_id     SERIAL       NOT NULL,
    Kelas_kelas_id      SERIAL       NOT NULL,
    CONSTRAINT Materi_Kelas_FK FOREIGN KEY (Kelas_Users_user_id, Kelas_kelas_id)
        REFERENCES Kelas (Users_user_id, kelas_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT Materi_Matpel_FK FOREIGN KEY (Matpel_mapel_id)
        REFERENCES Matpel (mapel_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE Rapor
(
    rapor_id             SERIAL PRIMARY KEY,
    Users_user_id        VARCHAR(7) NOT NULL,
    Semester_semester_id SERIAL     NOT NULL,
    CONSTRAINT Rapor_Semester_FK FOREIGN KEY (Semester_semester_id)
        REFERENCES Semester (semester_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT Rapor_Users_FK FOREIGN KEY (Users_user_id)
        REFERENCES Users (user_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE Nilai
(
    nilai_id        SERIAL PRIMARY KEY,
    nilai           BIGINT      NOT NULL,
    jenis_nilai     VARCHAR(50) NOT NULL,
    Matpel_mapel_id SERIAL      NOT NULL,
    Rapor_rapor_id  SERIAL      NOT NULL,
    CONSTRAINT Nilai_Matpel_FK FOREIGN KEY (Matpel_mapel_id)
        REFERENCES Matpel (mapel_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT Nilai_Rapor_FK FOREIGN KEY (Rapor_rapor_id)
        REFERENCES Rapor (rapor_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE Pengumuman
(
    pengumuman_id SERIAL PRIMARY KEY,
    pengumuman    VARCHAR(255) NOT NULL,
    Users_user_id VARCHAR(7)   NOT NULL,
    waktu         TIMESTAMP,
    CONSTRAINT Pengumuman_Users_FK FOREIGN KEY (Users_user_id)
        REFERENCES Users (user_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE Tugas
(
    tugas_id            SERIAL PRIMARY KEY,
    keterangan          VARCHAR(255) NOT NULL,
    deadline            TIMESTAMP    NOT NULL,
    tanggal_direlease   TIMESTAMP    NOT NULL,
    Kelas_Users_user_id VARCHAR(7)   NOT NULL,
    Matpel_mapel_id     SERIAL       NOT NULL,
    Kelas_kelas_id      SERIAL       NOT NULL,
    CONSTRAINT Tugas_Kelas_FK FOREIGN KEY (Kelas_Users_user_id, Kelas_kelas_id)
        REFERENCES Kelas (Users_user_id, kelas_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT Tugas_Matpel_FK FOREIGN KEY (Matpel_mapel_id)
        REFERENCES Matpel (mapel_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE Ujian
(
    ujian_id            SERIAL PRIMARY KEY,
    jenis_ujian         VARCHAR(50) NOT NULL,
    tanggal             TIMESTAMP   NOT NULL,
    Kelas_Users_user_id VARCHAR(7)  NOT NULL,
    Matpel_mapel_id     SERIAL      NOT NULL,
    Kelas_kelas_id      SERIAL      NOT NULL,
    CONSTRAINT Ujian_Kelas_FK FOREIGN KEY (Kelas_Users_user_id, Kelas_kelas_id)
        REFERENCES Kelas (Users_user_id, kelas_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT Ujian_Matpel_FK FOREIGN KEY (Matpel_mapel_id)
        REFERENCES Matpel (mapel_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- Renamed from Student_Class_Enrollment to Enrollment
CREATE TABLE Enrollment
(
    Users_user_id        VARCHAR(7) NOT NULL,
    Kelas_kelas_id       SERIAL     NOT NULL,
    Kelas_Users_user_id VARCHAR(7) NOT NULL,
    CONSTRAINT Enrollment_PK PRIMARY KEY (Users_user_id, Kelas_kelas_id, Kelas_Users_user_id),
    CONSTRAINT Enrollment_Users_FK FOREIGN KEY (Users_user_id)
        REFERENCES Users (user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT Enrollment_Kelas_FK FOREIGN KEY (Kelas_Users_user_id, Kelas_kelas_id)
        REFERENCES Kelas (Users_user_id, kelas_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
