package com.vgreview.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @GeneratedValue
    @Column(name = "authority_id")
    public Integer id;
    public String name;

    @ManyToMany(targetEntity = User.class)
    @JoinTable(
            name = "User_Authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns =  @JoinColumn(name = "authority_id"))
    public List<User> users;
}