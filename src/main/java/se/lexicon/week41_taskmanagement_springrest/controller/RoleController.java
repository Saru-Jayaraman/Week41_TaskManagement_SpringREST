package se.lexicon.week41_taskmanagement_springrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.RoleDTOFormSave;
import se.lexicon.week41_taskmanagement_springrest.domain.dto.RoleDTOFormView;
import se.lexicon.week41_taskmanagement_springrest.service.RoleService;

import java.util.List;

@RequestMapping("/api/v1/roles")
@RestController
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<RoleDTOFormView>> doGetAllRoles() {
        List<RoleDTOFormView> responseBody = roleService.getAll();
//        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/fetch")
    public ResponseEntity<RoleDTOFormView> doGetRoleByName(@RequestParam String roleName) {
        RoleDTOFormView responseBody = roleService.findByName(roleName);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PostMapping("")
    public ResponseEntity<RoleDTOFormView> doRegisterRole(@RequestBody RoleDTOFormSave roleDTO) {
        RoleDTOFormView responseBody = roleService.saveRole(roleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }
}
