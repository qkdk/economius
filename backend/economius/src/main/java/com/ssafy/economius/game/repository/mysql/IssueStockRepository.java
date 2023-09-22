package com.ssafy.economius.game.repository.mysql;

import com.ssafy.economius.game.entity.mysql.IssueStock;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueStockRepository extends JpaRepository<IssueStock, Integer> {

    @Query("select i from IssueStock i join fetch i.issue where i.issue.issueId = :issueId")
    List<IssueStock> findIssueStockByIssueId(@Param("issueId") Integer issueId);
}
