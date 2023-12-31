package com.ssafy.economius.game.entity.mysql;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PrevIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer prevIssueId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issueId")
    private Issue issue;
    private String foretoken;
}
