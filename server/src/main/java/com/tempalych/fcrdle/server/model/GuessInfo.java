package com.tempalych.fcrdle.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "guess_info")
public class GuessInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="request_time")
    private LocalDateTime requestTime;

    @Column(name="guess_club")
    private String guessClub;

    @ManyToOne
    @JoinColumn(name = "ip_address_id")
    private IpAddress ipAddress;

    @Column(name="platform")
    private String platform;

    @Column(name="mobile")
    private String mobile;

    @Column(name="language")
    private String language;

    @Column(name="correct")
    private Boolean correct;

    @Column(name="userId")
    private String userId;
}
