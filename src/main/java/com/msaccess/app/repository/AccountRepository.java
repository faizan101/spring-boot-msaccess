package com.msaccess.app.repository;

import com.msaccess.app.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

//    @Query("Select a from AccountEntity a left join StatementEntity s on a.id = s.accountId where a.id=:id and s.dateField IN (:date)")
//    AccountEntity findByIdAndStatementList_DateFieldIn(Long id, List<String> date);

}
