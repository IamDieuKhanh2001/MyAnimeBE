package com.hcmute.myanime.controller;

import com.hcmute.myanime.model.CategoryEntity;
import com.hcmute.myanime.repository.CategoryRepository;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
public class testController {

    public class RankClass {
        private int reaction;
        private int memory;
        private int verbal;
        private int visual;
        private String description;
        private String rank;

        public int getReaction() {
            return reaction;
        }

        public void setReaction(int reaction) {
            this.reaction = reaction;
        }

        public int getMemory() {
            return memory;
        }

        public void setMemory(int memory) {
            this.memory = memory;
        }

        public int getVerbal() {
            return verbal;
        }

        public void setVerbal(int verbal) {
            this.verbal = verbal;
        }

        public int getVisual() {
            return visual;
        }

        public void setVisual(int visual) {
            this.visual = visual;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public RankClass(int reaction, int memory, int verbal, int visual, String description, String rank) {
            this.reaction = reaction;
            this.memory = memory;
            this.verbal = verbal;
            this.visual = visual;
            this.description = description;
            this.rank = rank;
        }
    }

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/test/procedure/categories")
    public ResponseEntity<?> cateFindAllTest() {
        List<CategoryEntity> allByStoredProcedures = categoryRepository.findAllByStoredProcedures();
        return ResponseEntity.ok(allByStoredProcedures);
    }

    @GetMapping("/test/procedure/categories/{id}")
    public ResponseEntity<?> cateFindAllTest(@PathVariable int id) {
        CategoryEntity byIdByStoredProcedures = categoryRepository.findByIdByStoredProcedures(id);
        System.out.println(byIdByStoredProcedures.getName());
        System.out.println(byIdByStoredProcedures.getMovieEntityCollection().size());
        return ResponseEntity.ok(byIdByStoredProcedures);
    }

    @GetMapping("/test/demo")
    public ResponseEntity<?> demo() {
         RankClass rankClass = new RankClass(
                 10,
                 10,
                 10,
                 10,
                 "Điểm quá thấp so với 1% người tham gia test",
                 "Bad"
         );

        return ResponseEntity.ok(rankClass);
    }

    //CMND ID:
//    X coordinate: 470
//    y coordinate: 510
//    width: 70
//    height: 430
    //Name:
//    X coordinate: 376
//    y coordinate: 380
//    width: 60
//    height: 900
    //Ngay sinh:
//    X coordinate: 330
//    y coordinate: 740
//    width: 50
//    height: 400
    //Que quan:
//    X coordinate: 180
//    y coordinate: 380
//    width: 55
//    height: 300

    @GetMapping("/test/tesseract")
    public ResponseEntity<?> testTesseract(@RequestParam Map<String, String> requestParams) {
        String marginTop = requestParams.get("marginTop");
        String marginLeft = requestParams.get("marginLeft");
        String width = requestParams.get("width");
        String height = requestParams.get("height");

        File image = new File("C:/Users/admin/Downloads/test/8261a5f3b964633a3a75.jpg");
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/Users/admin/Downloads/lang");
        tesseract.setLanguage("vie");
        tesseract.setPageSegMode(ITessAPI.TessPageSegMode.PSM_AUTO_OSD);
        tesseract.setOcrEngineMode(ITessAPI.TessOcrEngineMode.OEM_TESSERACT_LSTM_COMBINED);
        String result = "Text";
        try {
            result = tesseract.doOCR(
                    image,
                    new Rectangle(
                            Integer.parseInt(marginTop),
                            Integer.parseInt(marginLeft),
                            Integer.parseInt(width),
                            Integer.parseInt(height)
                    )
            );
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }


}