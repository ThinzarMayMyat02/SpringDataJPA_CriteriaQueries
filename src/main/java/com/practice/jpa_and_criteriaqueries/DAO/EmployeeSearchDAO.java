package com.practice.jpa_and_criteriaqueries.DAO;

import com.practice.jpa_and_criteriaqueries.model.EmployeeSearchRequest;
import lombok.RequiredArgsConstructor;
import com.practice.jpa_and_criteriaqueries.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeSearchDAO {
    @Autowired
    private EntityManager entityManager;

    public List<Employee> findAllBySimpleQuery(
            String firstname, String lastname, String email
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        //select * from Employee
        Root<Employee> root = criteriaQuery.from(Employee.class);

        //prepare Where Clause
        //WHERE firstname like 'may' or lastname like 'may' and email like 'may@gmail.com'

        Predicate firstnamePredicate = criteriaBuilder.like(
                root.get("firstname"), "%" + firstname + "%"
        );
        Predicate lastnamePredicate = criteriaBuilder.like(
                root.get("lastname"), "%" + lastname + "%"
        );
        Predicate emailPredicate = criteriaBuilder.like(
                root.get("email"), "%" + email + "%"
        );

        Predicate firstnameOrLastnamePredicate = criteriaBuilder.or(firstnamePredicate,lastnamePredicate);
        var andEmailPredicate = criteriaBuilder.and(firstnameOrLastnamePredicate);

        criteriaQuery.where(andEmailPredicate);
        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();

    }

    public List<Employee> findAllEmployeeByCriteria(EmployeeSearchRequest request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        List<Predicate> predicates = new ArrayList<>();
        Root<Employee> root = criteriaQuery.from(Employee.class);

       if (request.getFirstname() != null) {
           Predicate firstnamePredicate = criteriaBuilder.like(
                   root.get("firstname"), "%" + request.getFirstname() + "%"
           );
           predicates.add(firstnamePredicate);
       }

        if (request.getLastname() != null) {
            Predicate lastnamePredicate = criteriaBuilder.like(
                    root.get("lastname"), "%" + request.getLastname() + "%"
            );
            predicates.add(lastnamePredicate);
        }

        if (request.getEmail() != null) {
            Predicate emailPredicate = criteriaBuilder.like(
                    root.get("firstname"), "%" + request.getEmail() + "%"
            );
            predicates.add(emailPredicate);
        }
        criteriaQuery.where(
                criteriaBuilder.or(predicates.toArray(new Predicate[0]))
        );
        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();

    }
}
