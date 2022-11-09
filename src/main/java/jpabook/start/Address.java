package jpabook.start;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class Address {
    private String city;
    private String street;
    private String zipCode;

    public Address(){
    }

    public Address(String city, String street, String zipCode){
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

}
