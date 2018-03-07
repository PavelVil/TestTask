package crud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Pavel on 30.06.2017.
 */
@Entity
@Table(name = "user")
@XmlRootElement(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    @Size(min = 4, max = 25, message = "Имя должно содержать от 4 до 25 символов")
    private String name;
    @Column(name = "isAdmin")
    private boolean isAdmin;
    @Column(name = "age")
    @DecimalMin(value = "16", message = "Минимальный возраст - 16 лет")
    private int age;
    @Column(name = "createDate")
    private Date createDate = new Date();
    @Column(name = "city")
    @Size(max = 60, message = "Название города - не больше 60 символов")
    private String city;
    @Column(name = "email")
    private String email;
    @JsonIgnore
    @Column(name = "photo")
    private byte[] photo;

    public User() {
    }

    public User(String name, boolean isAdmin, int age, Date createDate) {
        this.name = name;
        this.isAdmin = isAdmin;
        this.age = age;
        this.createDate = createDate;
    }

    public User(String name, boolean isAdmin, int age, Date createDate, String city, String email, byte[] photo) {
        this.name = name;
        this.isAdmin = isAdmin;
        this.age = age;
        this.createDate = createDate;
        this.city = city;
        this.email = email;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @XmlElement
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @XmlElement
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @XmlElement
    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    @XmlElement
    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPhoto() {
        return photo;
    }

    @XmlTransient
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isAdmin=" + isAdmin +
                ", age=" + age +
                ", createDate=" + createDate +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", photo=" + photo +
                '}';
    }
}
