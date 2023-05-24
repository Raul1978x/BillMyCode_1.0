import com.BillMyCode.app.entities.Admin;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.services.AdminService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/createAdmin")
    public ResponseEntity<Admin> createAdmin(@RequestParam MultipartFile archivo,
                                             @RequestParam String nombre,
                                             @RequestParam String apellido,
                                             @RequestParam String email,
                                             @RequestParam String password,
                                             @RequestParam Date fechaNac,
                                             @RequestParam String telefono,
                                             ModelMap model) throws MiException {

        try {
            adminService.createAdmin(nombre, apellido, email, password, fechaNac, telefono, archivo);
            return ResponseEntity.ok().build();
        } catch (MiException e) {
            model.put("error", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
