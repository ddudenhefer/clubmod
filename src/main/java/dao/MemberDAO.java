package dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Member;

@Stateless
@LocalBean
public class MemberDAO {

    @PersistenceContext
    private EntityManager em;
    
    public Member getMember(long id) {
        return em.find(Member.class, id);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addCustomers(List<Member> members) {
        for (Member member : members) {
            em.persist(member);
        }
    }
}
    
