package in.yaxley.guestbook.Repositories;

import in.yaxley.guestbook.Models.GuestBookEntry;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface GuestBookEntryRepository extends CrudRepository<GuestBookEntry, Long> {
    @NullMarked
    List<GuestBookEntry> findAll();

    List<GuestBookEntry> findByIpAndSubmittedOnAfter(String ip, Date submittedOn);

}
