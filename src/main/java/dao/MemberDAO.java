package dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Members;

@Stateless
@LocalBean
public class MemberDAO {

    @PersistenceContext
    private EntityManager em;
    
    public Members getMember(long id) {
        return em.find(Members.class, id);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addCustomers(List<Members> members) {
        for (Members member : members) {
            em.persist(member);
        }
    }
}
    
