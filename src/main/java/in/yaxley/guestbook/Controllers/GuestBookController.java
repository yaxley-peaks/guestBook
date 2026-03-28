package in.yaxley.guestbook.Controllers;

import in.yaxley.guestbook.Models.GuestBookEntry;
import in.yaxley.guestbook.Repositories.GuestBookEntryRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@RestController
@CrossOrigin
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

    @SuppressWarnings("JvmTaintAnalysis")
    @PostMapping("/add")
    public ResponseEntity<GuestBookEntry> add(@RequestBody PostDto dto, HttpServletRequest request) {

        final String remoteIp = request.getRemoteAddr();
        var cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -24);
        final Date oneDayAgo = cal.getTime();

        final int count = guestBookEntryRepository.findByIpAndSubmittedOnAfter(remoteIp, oneDayAgo).size();
        if(count > 0) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }


        String submitter = dto.submitter;
        submitter = StringEscapeUtils.escapeHtml4(submitter);
        String message = dto.message;
        message = StringEscapeUtils.escapeHtml4(message);

        GuestBookEntry gbe = new GuestBookEntry();
        gbe.setSubmitter(submitter);
        gbe.setMessage(message);
        gbe.setIp(remoteIp);
        gbe.setSubmittedOn(Calendar.getInstance().getTime());
        guestBookEntryRepository.save(gbe);
        return ResponseEntity.status(HttpStatus.CREATED).body(gbe);
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
