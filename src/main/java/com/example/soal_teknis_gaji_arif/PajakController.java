package com.example.soal_teknis_gaji_arif;

import com.example.soal_teknis_gaji_arif.service.PajakService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PajakController {
    private final PajakService service;

    public PajakController() {
        service = new PajakService();
    }

    @PostMapping("/hitungpajak")
    @ResponseBody
    public Object hitungPajak(@RequestBody Map<String, Object> dto) {
        return service.hitungPajak(dto);
    }
}
