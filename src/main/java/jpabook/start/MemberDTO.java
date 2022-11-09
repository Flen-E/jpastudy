package jpabook.start;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
    private String username;
    private Integer age;

    public MemberDTO(String username, Integer age) {
        this.username = username;
        this.age = age;
    }
}
