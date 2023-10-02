package com.alex.zanchenko.web.controller;

import com.alex.zanchenko.web.dto.ClubDTO;
import com.alex.zanchenko.web.model.Club;
import com.alex.zanchenko.web.model.UserEntity;
import com.alex.zanchenko.web.security.SecurityUtil;
import com.alex.zanchenko.web.service.ClubService;
import com.alex.zanchenko.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ClubController {

    private ClubService clubService;
    private UserService userService;
    @Autowired
    public ClubController(ClubService clubService, UserService userService) {
        this.clubService = clubService;
        this.userService = userService;
    }

//    @GetMapping("/clubs") // in video
//    public String list(Model model){
//        UserEntity user = new UserEntity();
//        List<ClubDTO> clubs = clubService.findAllClubs(); // get club list from db
//        String username = SecurityUtil.getSessionUser();
//        if(username!=null){
//            user = userService.findByUsername(username);
//            model.addAttribute("user", user);
//        }
//        model.addAttribute("user", user);
//        model.addAttribute("clubs", clubs);
//        return "clubs-list";
//    }

//    @GetMapping
//    public ResponseEntity<List<ClubDTO>> list() {
//        List<ClubDTO> clubs = clubService.findAllClubs();
//        return ResponseEntity.ok(clubs);
//    }
//    @GetMapping("/clubs")
//    public List<ClubDTO> list(){
//        List<ClubDTO> clubs = clubService.findAllClubs(); // get club list from db
//        return clubs;
//    }

    @GetMapping("/clubs")
    public ResponseEntity<Map<String, Object>> list() {
        List<ClubDTO> clubs = clubService.findAllClubs();

        String username = SecurityUtil.getSessionUser();
        UserEntity user = null;
        if (username != null) {
            user = userService.findByUsername(username);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("clubs", clubs);
        response.put("user", user);

        return ResponseEntity.ok(response);
    }

//    @GetMapping("/clubs/new") // get request - display the form
//    public String createClubForm(Model model){
//        Club club = new Club();
//        model.addAttribute("club", club);
//        return "clubs-create";
//    }

    @GetMapping("/clubs/new") // ?
    public ResponseEntity<String> getCreateClubForm() {
        System.out.println("123");
        return ResponseEntity.ok("Display the form here");
    }


    //    @PostMapping("clubs/new") // post request
//    public String saveClub(@ModelAttribute("club") Club club){
//        clubService.saveClub(club);
//        return "redirect:/clubs";
//    }
    @PostMapping("/clubs/new")
    public ResponseEntity<?> createClub(@Valid @RequestBody ClubDTO clubDTO,
                                           BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        System.out.println("saved");
        Club savedClub = clubService.saveClub(clubDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClub);
    }

//    @GetMapping("clubs/{clubID}/edit")
//    public String editClubForm(@PathVariable("clubID") Long clubID, Model model){
//        ClubDTO club = clubService.findClubById(clubID);
//        model.addAttribute("club", club);
//        return "clubs-edit";
//    }
//
//    @PostMapping("clubs/{clubID}/edit")
//    public String updateClub(@PathVariable("clubID") Long clubID,
//                             @Valid @ModelAttribute("club") ClubDTO club,
//                             BindingResult result){
//            if(result.hasErrors()){
//                return "clubs-edit";
//            }
//        club.setId(clubID);
//        clubService.updateClub(club);
//        return "redirect:/clubs";
//    }

    @GetMapping("/clubs/{clubID}/edit")
    @ResponseBody
    public ClubDTO editClubForm(@PathVariable("clubID") Long clubID) {
        return clubService.findClubById(clubID);
    }

    @PutMapping("/clubs/{clubID}/edit")
    @ResponseBody
    public ResponseEntity<?>  updateClub(@PathVariable("clubID") Long clubID,
                           @Valid @RequestBody ClubDTO club,
                           BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        club.setId(clubID);
        System.out.println("updated");
        clubService.updateClub(club);
        return ResponseEntity.ok("Club updated successfully");
    }

    @GetMapping("clubs/{clubID}")
    public ClubDTO clubDetail(@PathVariable("clubID") Long clubID){
        ClubDTO clubDTO = clubService.findClubById(clubID);
        return clubDTO;
    }

    @DeleteMapping("clubs/{clubID}/delete")
    public void deleteClub(@PathVariable("clubID")Long clubID){
        clubService.delete(clubID);
    }

    @GetMapping("clubs/search")
    public List<ClubDTO> searchClub(@RequestParam(value = "query") String query, Model model){
        List<ClubDTO> clubs = clubService.searchClubs(query);
        return clubs;
    }
}
