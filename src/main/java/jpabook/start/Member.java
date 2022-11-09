package jpabook.start;

import com.mysql.cj.xdevapi.AddResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(
        name="MEMBER_SEQ_GENERATPR"
        ,sequenceName="MEMBER_SEQ",
        initialValue = 1,
        allocationSize = 50
)
public class Member extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    private String username;
    private Integer age;

    @Override
    public String toString() {
        return "Member{" +
                "username='" + username + '\'' +
                ", age=" + age +
                ", roleType=" + roleType +
                '}';
    }

    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddress;

    @Enumerated
    private RoleType roleType;

    private LocalDateTime crateDate;

    private LocalDateTime lasModifiedDate;

    @Lob
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TEAM_ID")
    private Team team;

    public Member(String username, Integer age, RoleType roleType){
        this.username = username;
        this.age = age;
        this.roleType = roleType;
    }


    public void setTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }


}