package pl.projekt.alekino.domain.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(("/${app.prefix}/${app.version}/users"))
@Tag(name = "User", description = "User management endpoints")
public class AppUserController {



}
