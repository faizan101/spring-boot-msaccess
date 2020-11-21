package com.msaccess.app.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "statement")
public class StatementEntity {

    @Id
    @Column(name = "ID")
    private Long id;

//    @Column(name = "account_id")
//    private Long accountId;

    @Column(name = "datefield")
    private String dateField;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity accountEntity;
}
