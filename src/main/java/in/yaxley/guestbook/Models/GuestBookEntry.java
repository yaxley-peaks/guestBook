package in.yaxley.guestbook.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
public class GuestBookEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(allocationSize = 1)
    private Long id;

    private String submitter;
    private String message;
    private Date submittedOn;
    private String ip;

    public GuestBookEntry() {
    }
}
