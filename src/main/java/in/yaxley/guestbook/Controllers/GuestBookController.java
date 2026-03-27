package in.yaxley.guestbook.Controllers;

import in.yaxley.guestbook.Models.GuestBookEntry;
import in.yaxley.guestbook.Repositories.GuestBookEntryRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;


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
        var gbes = guestBookEntryRepository.findAll();
        // censor IP
        for (GuestBookEntry gbe : gbes) {
            gbe.setIp("nuh uh");
        }
        return gbes;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
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
    @ExceptionHandler(GuestBookController.TeapotException.class)
    public ResponseEntity<String> iAmATeapot() {
        return ResponseEntity.status(418).body("I am a teapot, short and stout; tip me over and pour me out.");
    }
}
