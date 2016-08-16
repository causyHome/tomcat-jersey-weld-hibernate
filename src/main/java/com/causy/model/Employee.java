package com.causy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Entity
@Table(name = "Employee",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true, length = 11)
    private int id;

    @Column(name = "NAME", length = 20, nullable = true)
    private String name;

    @Column(name = "ROLE", length = 20, nullable = true)
    private String role;

    @Column(name = "insert_time", nullable = true)
    private long insertTime;

    public int getId() {
        return id;
    }

    public String getName() {

        return name;
    }

    public String getRole() {
        return role;
    }

    public long getInsertTime() {
        return insertTime;
    }

    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }
    public Employee(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Employee() {
        this(null, null);
    }
}
