package jpabook.start;

import com.mysql.cj.QueryResult;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.querydsl.core.Tuple;

public class JpaMain {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
    //psvm하면 자동완성
    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Member member1 = new Member("kim",20, RoleType.ADMIN);
            Member member2 = new Member("kim",30, RoleType.USER);
            Member member3 = new Member("lee",25,RoleType.USER);
            Member member4 = new Member("lee",15,RoleType.USER);

            Team team1 = new Team();
            Team team2 = new Team();
            team1.setName("teamA");
            team2.setName("teamB");

            member1.setTeam(team1);
            member2.setTeam(team1);
            member3.setTeam(team2);
            member4.setTeam(team2);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);
            em.persist(team1);
            em.persist(team2);

            em.flush();
            em.clear();

            JPAQueryFactory query = new JPAQueryFactory(em);


            QMember qMember = new QMember("m");
            QTeam qTeam =new QTeam("t");
            List<Member> members = query.selectFrom(qMember)
                    .join(qMember.team, qTeam)
                    .on(qTeam.name.eq("teamB"))
                    .fetch();
            members.stream().forEach(m-> System.out.println("members = " + m.getTeam().getName()));


      /*      List<Member> members = query.selectFrom(qMember)
                .where(qMember.username.contains("kim"),
                        qMember.roleType.eq(RoleType.ADMIN),
                        qMember.age.gt(10))
                .fetch();*/
            /*QueryResults<Member> results = query.selectFrom(qMember)
                    .where(qMember.age.gt(10))
                    .orderBy(qMember.username.asc(),qMember.age.desc())
                    .offset(0).limit(2)
                    .fetchResults();

            System.out.println("results.getTotal() = " + results.getTotal());
            List<Member> members = results.getResults();*/
            /*List<Tuple> members = query.select(qMember.username,qMember.age.avg(),qMember.age.sum())
                    .from(qMember)
                    .groupBy(qMember.username)
                    .fetch();
            System.out.println("members = " + members);
            for(Tuple member:members){
                String username = (String) member.toArray()[0];
                Double ageAvg = (Double) member.toArray()[1];
                Integer ageSum = (Integer) member.toArray()[2];
                System.out.printf("%s,%.2f,%d\n",username,ageAvg,ageSum);
            }



            members.stream().forEach(m-> System.out.println("members = " + m));*/


            tx.commit();


       /*     Integer a = 1;
            Integer b = a;
            Integer c = 1;

            System.out.println("(a==b) = " + (a==b));
            System.out.println("a. = " + a.equals(c));
*/
           /* Address address1 = new Address("city", "street", "zipCode");
            Address address2 = new Address("city", "street", "zipCode");
            System.out.println("address1==address2 " + (address1==address2));
            System.out.println("address1.equals(address2) " + address1.equals(address2));

            tx.commit();*/

           /* Member member1 = new Member();
            member1.setUsername("memberA");
            Member member2 = new Member();
            member2.setUsername("memberB");
            Member member3 = new Member();
            member3.setUsername("memberC");

            Team team1 = new Team();
            team1.setName("teamA");
            Team team2 = new Team();
            team2.setName("teamB");

            member1.setTeam(team1);
            member2.setTeam(team1);
            member3.setTeam(team2);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(team1);
            em.persist(team2);

            em.flush();
            em.clear();

            String jpql = "select m from Member m join fetch m.team t";
            List<Team> result = em.createQuery("select distinct t from Team t join fetch t.members", Team.class)
                    .getResultList();
            for (Team team : result) {
                System.out.println("team name=" + team.getName()+", number of members=" + team.getMembers().size());
            }


*/


           /* for (int i = 1; i < 101; i++) {
                Member member = new Member();
                member.setUsername("member"+i);
                member.setAge(i);
                em.persist(member);
            }
            em.flush();
            em.clear();

            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .setFirstResult(10) //select할 처음 위치
                    .setMaxResults(5) //select할 개수
                    .getResultList();
            for (Member member : result) {
                System.out.println(member.getUsername());
            }
            //같은 함수
            result.stream().forEach(m -> System.out.println(m.getUsername()));


            tx.commit();
*/
  /*          Member member1 = new Member();
            member1.setUsername("Kim");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("Kim");
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("Lee");
            em.persist(member3);

            em.flush();
            em.clear();

            List<Member> result = em.createQuery("select distinct m from Member m where m.username like '%Kim%'",Member.class).getResultList();

            for(Member member : result){
                System.out.println(member.getUsername());
            }



            tx.commit();*/
           /* Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();

            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);
            em.persist(child1);
            em.persist(child2);
            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, 1L);
            Child findChild1 = em.find(Child.class, 1L);
            Child findChild2 = em.find(Child.class, 2L);


            findParent.setChildList(null);
*/



            /* Member member1 = new Member();
            em.persist(member1);

            Member member2 = new Member();
            em.persist(member2);

            em.flush();
            em.clear();

            Member find1 = em.find(Member.class, member1.getId());
            //Member find2 = em.find(Member.class, member2.getId());
            Member find2 = em.getReference(Member.class,member2.getId());

            System.out.println("클래스가 같은가?" + ((find1 instanceof Member) == (find2 instanceof Member)));*/

           /* Member member = new Member();
            em.persist(member);
            em.flush();
            em.clear();
            
           *//* Member findMember = em.find(Member.class, member.getId());*//*
            Member findMember = em.getReference(Member.class, member.getId());

            System.out.println("findMember.getClass().getName() = " + findMember.getClass().getName());
            em.persist(findMember);
            System.out.println("findMember.getClass().getName() = " + findMember.getClass().getName());
            tx.commit();*/


          /*  Member member1 = new Member();
            member1.setUsername("memberA");
            Member member2 = new Member();
            member2.setUsername("memberB");
            Member member3 = new Member();
            member3.setUsername("memberC");

            Team team1 = new Team();
            team1.setName("teamA");
            Team team2 = new Team();
            team2.setName("teamB");

            member1.setTeam(team1);
            member2.setTeam(team1);
            member3.setTeam(team2);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(team1);
            em.persist(team2);

            em.flush();
            em.clear();
            List<Team> result = em.createQuery("select t from Team t", Team.class)
                    .setFirstResult(0).setMaxResults(3).getResultList();
            for (Team team : result) {
                System.out.println("team name=" + team.getName());
            }
            for (Team team : result) {
                for (Member member : team.getMembers()) {
                    System.out.println("member.getUsername() = " + member.getUsername());
                }
            }*/


            /*List<Team> result = em.createQuery("select distinct t from Team t join fetch t.members", Team.class)
                    .setFirstResult(0)
                    .setMaxResults(3)
                    .getResultList();
            for (Team team : result) {
                System.out.println("team name=" + team.getName()+", number of members=" + team.getMembers().size());
            }
*/

            /*List<Team> result = em.createQuery("select t from Team t join fetch t.members", Team.class)
                    .getResultList();
            for (Team team : result) {
                System.out.println("team name=" + team.getName()+", number of members=" + team.getMembers().size());
            }*/


            /*String jpql = "SELECT m from Member m join fetch  m.team";
            List<Member> members = em.createQuery(jpql, Member.class)
                    .getResultList();
            members.stream().forEach(m-> System.out.println("username = "
                    + m.getUsername()
                    +","
                    +"teamname = "
                    +m.getTeam().getName()));*/


               /*    for (int i = 1; i < 101; i++) {
                Member member = new Member();
                member.setUsername("member"+i);
                member.setAge(i);
                em.persist(member);            ///paging
            }
            em.flush();
            em.clear();

            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .setFirstResult(0) //select할 처음 위치
                    .setMaxResults(10) //select할 개수
                    .getResultList();
            for (Member member : result) {
                System.out.println(member.getUsername());
            }*/
         /*   Member member1 = new Member();
            member1.setUsername("kim1");
            member1.setAge(20);

            Member member2 = new Member();
            member2.setUsername("kim2");
            member2.setAge(25);

            Member member3 = new Member();
            member3.setUsername("kim3");
            member3.setAge(26);
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();


           List<MemberDTO> resultList = em.createQuery("select new jpabook.start.MemberDTO(m.username,m.age) from Member m "+"where m.username like '%kim%'"
                           ,MemberDTO.class).getResultList();
            MemberDTO memberDTO= resultList.get(0);
            System.out.println("memberDTO = " + memberDTO);
*/



 /*           Address address = new Address("new", "street", "zipCode");

            Member member1 = new Member();
            member1.setId("member1");
            member1.setUsername("member1");
            member1.setHomeAddress(address);
            em.persist(member1);

            Member member2 = new Member();
            member2.setId("member2");
            member2.setUsername("member2");
            member2.setHomeAddress(address);
            em.persist(member2);
            member1.getHomeAddress().setCity("newCity");

            tx.commit();
*/

            /*Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);



            em.persist(parent);

            em.flush();
            em.clear();
            Parent findParent = em.find(Parent.class, 1L);
            em.remove(findParent);
            //findParent.getChildList().remove(0);
            tx.commit();*/


   /*         Team team1 = new Team();
            team1.setId("team1");
            em.persist(team1);

            Team team2 = new Team();
            team2.setId("team2");
            em.persist(team2);

            Member member1 = new Member();
            member1.setId("member1");
            member1.setTeam(team1);
            em.persist(member1);

            Member member2 = new Member();
            member2.setId("member2");
            member2.setTeam(team2);
            em.persist(member2);

            em.flush();
            em.clear();

            System.out.println("=========================");
            List<Member> members = em.createQuery("select m from Member m",Member.class)
                    .getResultList();
            //select * from Member -> Member1, Member2
            tx.commit();*/

/*
            Member member = new Member();
            member.setUsername("회원1");

            Team team1 = new Team();
            team1.setName("팀1");
            em.persist(team1);

            member.setTeam(team1);
            em.persist(member);

            em.flush();
            em.clear();
            Member findMember = em.find(Member.class, member.getId());
            System.out.println("findMember = " + findMember.getTeam().getClass().getName());
            System.out.println("===========================");
            findMember.getTeam().getName();
            System.out.println("===========================");

            tx.commit();*/
/*
            Member member = new Member();
            em.persist(member);
            em.flush();
            em.clear();

            Member m1 = em.getReference(Member.class,member.getId());
            Member m2 = em.find(Member.class,member.getId()); // 리피터블 리드 보장해주려고 find해도 프록시들고옴 find가 먼저였으면 실제 entity들고옴 getreference해도
            System.out.println("m1.getClass().getName() = " + m1.getClass().getName());
            System.out.println("m2.getClass().getName() = " + m2.getClass().getName());
            System.out.println("(m1==m2) = " + (m1==m2));//놀랍게도 jpa에서는 리피터블리드를 지켜서 true임 원래 자바에선 주소값달라서 false임
            *//*em.detach(m1); //이렇게 준영속상태하면 문제생김*//*
            Hibernate.initialize(m1); // 강제 초기화
            m1.getUsername();
            tx.commit();*/

          /*  Movie movie = new Movie();
            movie.setDirector("감독A");
            movie.setActor("배우B");
            movie.setName("바람과 함께 사라지다");
            movie.setPrice(2000);
            em.persist(movie);
            em.flush();
            em.clear();
            em.find(Movie.class,movie.getId());

            tx.commit();*/

    /*        Member member1 = new Member();
            member1.setId("member1");
            member1.setUsername("회원1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setId("member2");
            member2.setUsername("회원2");
            em.persist(member2);

            Team team1 = new Team();
            team1.setId("team1");
            team1.setName("팀1");
            team1.getMembers().add(member1);
            team1.getMembers().add(member2);
            em.persist(team1);
            tx.commit();*/

            /*Team team1 = new Team("team1", "팀1");
            em.persist(team1);

            team1.getMembers().add(member1);
            team1.getMembers().add(member2);*/

           /* Team team1 = new Team("team1", "팀1");
            em.persist(team1);

            Member member1 = new Member();
            member1.setId("member1");
            member1.setUsername("회원1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setId("member2");
            member2.setUsername("회원2");
            em.persist(member2);

            member1.setTeam(team1);
            member2.setTeam(team1);*/

            /*Team findTeam = em.find(Team.class,"team1");
            findTeam.getMembers().stream().forEach(v-> System.out.println("v.getUsername() = " + v.getUsername()));*/


            /*Team team = new Team("team1","팀1");
            em.persist(team);

            Member member1 = new Member("member1","회원1");
            member1.setTeam(team);
            em.persist(member1);
            Member member2 = new Member("member2","회원2");
            member2.setTeam(team);
            em.persist(member2);*/
            //read
            /*Member member = em.find(Member.class, "member1");
            Team findTeam = member.getTeam();
            System.out.println("findTeam.getName() = " + findTeam.getName());*/
            //update
           /* Team team2 = new Team("team2","팀2");
            em.persist(team2);

            Member findMember = em.find(Member.class, "member1");
            findMember.setTeam(team2);*/
            //delete
           /* Member findMember = em.find(Member.class, "member1");
            findMember.setTeam(null);*/
          /*  Team findTeam = em.find(Team.class, "team1");
            em.remove(findTeam);*/



           /* Member member = new Member();
            member.setUsername("member2");
            member.setRoleType(RoleType.USER);
            em.persist(member);

            System.out.println("========================================"); //IDENTITY와 다르게 SEQ는 영속성에 넣고 하기때매 ===이 먼저 출력됨
            tx.commit();*/
//            Member member = createMember("memberC", "회원1");
//            member.setUsername("회원2");
//            mergeMember(member);
            /*Member findMember = em.find(Member.class, "member1");
            em.remove(findMember);
            tx.commit();*/
            /*
            Member findMember = em.find(Member.class, "id2");
            System.out.println(em.contains(findMember));
            findMember.setAge(50);
            tx.commit();*/
          /*  Member member = new Member();
            member.setId("member1");
            member.setUsername("member1");
            member.setAge(20);
            System.out.println("=============Before============");
            System.out.println(em.contains(member));
            em.persist(member);
            System.out.println(em.contains(member));
            System.out.println("=============After============");
            em.flush();
            em.clear();
            Member findMember1 = em.find(Member.class, "member1");
            Member findMember2 = em.find(Member.class, "member1");
            tx.commit();
*/
            /*em.persist(member); //이때 영속되지만 rollback같은 것 예외상황이 있기에 commit할때 db로 전달
            System.out.println("=============Before============");
            tx.commit(); //commit 때 DB에 날라감
            System.out.println("=============After============");*/
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }


    }
    static Member createMember(String id, String username) {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        Member member = new Member();

        try {
            tx.begin();
//            member.setId(id);
//            member.setUsername(username);
//            em.persist(member);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); //create하고 finally를통해 close했기에 준영속상태로 들어가서 저위에 merge로안하고 commit하면 수정안됨
        }
        //emf.close();
        return member; //close해서 member는 준영속 상태
    }
    static void mergeMember(Member member){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
//            Member mergeMember = em.merge(member);
//            System.out.println("member = " + member.getUsername()); //회원명변경
//            System.out.println("mergeMember = " + mergeMember.getUsername()); //회원명변경
//            System.out.println("em.contains(member) = " + em.contains((member))); //false
//            System.out.println("em.contains(mergeMember) = " + em.contains((mergeMember))); //true
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}