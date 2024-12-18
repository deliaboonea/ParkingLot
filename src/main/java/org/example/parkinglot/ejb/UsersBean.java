package org.example.parkinglot.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.parkinglot.common.UsersDto;
import org.example.parkinglot.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Stateless
public class UsersBean {
    private static final Logger LOGGER = Logger.getLogger(UsersBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<UsersDto> findAllUsers() {
        LOGGER.info("findAllUsers");
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
            List<User> users = typedQuery.getResultList();
            return copyUserToDto(users);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    private List<UsersDto> copyUserToDto(List<User>users){
        List<UsersDto>list= new ArrayList<>();
        for(User user:users){
            UsersDto temp= new UsersDto(user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail());
            list.add(temp);
        }
        return list;
    }
}