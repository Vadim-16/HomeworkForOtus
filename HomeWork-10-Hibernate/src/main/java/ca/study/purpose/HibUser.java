package ca.study.purpose;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class HibUser {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private long age;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Phone> phones = new ArrayList<>();


    public HibUser(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "HibUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HibUser hibUser = (HibUser) o;
        return id == hibUser.id &&
                age == hibUser.age &&
                Objects.equals(name, hibUser.name) &&
                Objects.equals(address, hibUser.address) &&
                Objects.equals(phones, hibUser.phones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, address);
    }
}
