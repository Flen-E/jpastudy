package jpabook.start;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Team {
    @Id
    @Column(name = "TEAM_ID")
    @GeneratedValue
    private long id;

    private String name;

    public Team(String id, String name) {

        this.name = name;
    }

/*
    @OneToMany//Member.team을 뜻함
    @JoinColumn(name="TEAM_ID")
    private List<Member> members = new ArrayList<>();

    public void setMembers(Member member) {
        members.add(member);
    }
*/

    @OneToMany(mappedBy = "team")
    /*@JoinColumn(name = "TEAM_ID")// mappedBy = "team" 에서 team 은 Member.team*/
    private List<Member> members = new ArrayList<Member>();

    public void addMember(Member member){
        this.members.add(member);
        if(member.getTeam()!=this){
            member.setTeam(this);
        }
    }

}