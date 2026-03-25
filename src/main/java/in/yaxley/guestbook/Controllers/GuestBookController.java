package in.yaxley.guestbook.Controllers;

import in.yaxley.guestbook.Models.GuestBookEntry;
import in.yaxley.guestbook.Repositories.GuestBookEntryRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;

@RestController
public class GuestBookController {

    GuestBookEntryRepository guestBookEntryRepository;

    @Autowired
    public GuestBookController(GuestBookEntryRepository guestBookEntryRepository) {
        this.guestBookEntryRepository = guestBookEntryRepository;
    }

    @GetMapping("/")
    public List<GuestBookEntry> index() {
        return guestBookEntryRepository.findAll();
    }

    @PostMapping("/add")
    public GuestBookEntry add(@RequestBody PostDto dto, HttpServletRequest request) {
        GuestBookEntry gbe = new GuestBookEntry();
        gbe.setSubmitter(dto.submitter);
        gbe.setMessage(dto.message);
        gbe.setIp(request.getRemoteAddr());
        gbe.setSubmittedOn(Calendar.getInstance().getTime());
        guestBookEntryRepository.save(gbe);
        return new GuestBookEntry();
    }

     static public class PostDto {
        public String submitter;
        public String message;
    }
}
