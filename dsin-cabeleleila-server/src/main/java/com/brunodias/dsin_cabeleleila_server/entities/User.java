package com.brunodias.dsin_cabeleleila_server.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class User extends BaseEntity {

    @NotBlank(message = "O campo nome não pode ficar em branco")
    private String name;

    @Column(name = "phone_number")
    @NotBlank(message = "O campo telefone não pode ficar em branco")
    private String phoneNumber;

    @Column(unique = true)
    @NotBlank(message = "O campo de email não pode ficar em branco")
    @Email(message = "Por favor, digite o e-mail no formato correto!")
    private String email;

    @NotBlank(message = "O campo de senha não pode ficar em branco")
    @Size(min = 5, message = "A senha deve ter pelo menos 5 caracteres")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new HashSet<>();

    @Transient
    @OneToMany(mappedBy = "client")
    private List<Appointment> appointments;

}