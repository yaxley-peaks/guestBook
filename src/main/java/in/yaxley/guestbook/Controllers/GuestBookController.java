package in.yaxley.guestbook.Controllers;

import in.yaxley.guestbook.Models.GuestBookEntry;
import in.yaxley.guestbook.Repositories.GuestBookEntryRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

import static org.springframework.http.HttpStatus.I_AM_A_TEAPOT;


@RestController
public class GuestBookController {
    GuestBookEntryRepository guestBookEntryRepository;

    @Autowired
    public GuestBookController(GuestBookEntryRepository guestBookEntryRepository) {
        this.guestBookEntryRepository = guestBookEntryRepository;
    }

    @GetMapping("/coffee")
    public void getCoffee() throws TeapotException {
        throw new TeapotException();
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

    public static class TeapotException extends Exception {

    }

    static public class PostDto {
        public String submitter;
        public String message;
    }
}


@ControllerAdvice
class TeapotExceptionAdvice {
    @SuppressWarnings("deprecation")
    @ExceptionHandler(GuestBookController.TeapotException.class)
    @ResponseStatus(I_AM_A_TEAPOT)
    public void iAmATeapot() {
        // do nothing
    }
}
