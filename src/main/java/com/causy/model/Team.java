package com.causy.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Team",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true, length = 11)
    private int id;

    @Column(name = "NAME", length = 20, nullable = true)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Employee> members;

    public Team(int id, String name, List<Employee> teamMembers) {
        this.id = id;
        this.name = name;
    }

    public Team(String name) {
        this.name = name;
    }

    public Team() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Employee> getMembers() {
        return members;
    }

    public void addMember(Employee employee) {
        if (members == null) {
            members = new ArrayList<>();
        }
        members.add(employee);
    }
}
