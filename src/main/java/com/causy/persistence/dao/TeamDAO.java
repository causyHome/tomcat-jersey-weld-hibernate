package com.causy.persistence.dao;

import com.causy.model.Team;

import java.util.List;

public interface TeamDAO {
    int create(Team newTeam);

    Team get(int teamId);

    void update(Team team);

    List<Team> list();

    int count();
}
