package com.majumundur.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // Getter
@Setter // Setter
@AllArgsConstructor // Constructor Dengan Parameter / Args
@NoArgsConstructor // Constructor tanpa parameter / args
@Entity // Annotasi Jakarta untuk menandakan ini Entity
@Table(name = "m_customers") // Annotasi untuk memberikan nama table pada database
public class Customers {
    private String id;
    private String name;
}
