package ait.cohort49.hostel_casa_flamingo.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String s3Path;

    @Column(nullable = false)
    private String fileOriginName;

    @Column(nullable = false)
    private String s3BucketName;

    @ManyToOne
    @JoinColumn(name = "bed_id")
    private Bed bed;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Image() {
    }

    public Image(String s3Path, String fileOriginName, String s3BucketName, Bed bed) {
        this.s3Path = s3Path;
        this.fileOriginName = fileOriginName;
        this.s3BucketName = s3BucketName;
        this.bed = bed;
        this.room = null;
    }

    public Image(String s3Path, String fileOriginName, String s3BucketName, Room room) {
        this.s3Path = s3Path;
        this.fileOriginName = fileOriginName;
        this.s3BucketName = s3BucketName;
        this.bed = null;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public String getS3Path() {
        return s3Path;
    }

    public void setS3Path(String s3Path) {
        this.s3Path = s3Path;
    }

    public String getFileOriginName() {
        return fileOriginName;
    }

    public void setFileOriginName(String fileOriginName) {
        this.fileOriginName = fileOriginName;
    }

    public String getS3BucketName() {
        return s3BucketName;
    }

    public void setS3BucketName(String s3BucketName) {
        this.s3BucketName = s3BucketName;
    }

    public Bed getBed() {
        return bed;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", s3Path='" + s3Path + '\'' +
                ", fileOriginName='" + fileOriginName + '\'' +
                ", s3BucketName='" + s3BucketName + '\'' +
                ", bed=" + bed +
                ", room=" + room +
                '}';
    }
}
