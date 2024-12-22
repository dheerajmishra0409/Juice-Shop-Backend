package com.example.demo;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:3000") // Adjust for your frontend URL
public class Itemcontroller {

    private final Itemrepo itemRepository;

    public Itemcontroller(Itemrepo itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Add a new item
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<String> addItem(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("cost") double cost,
            @RequestParam("image") MultipartFile image
    ) {
        try {
            // Convert image to byte array
            byte[] imageData = image.getBytes();

            // Create and save item
            Item item = new Item();
            item.setName(name);
            item.setDescription(description);
            item.setCost(cost);
            item.setImage(imageData);

            itemRepository.save(item);

            return ResponseEntity.ok("Item added successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving item: " + e.getMessage());
        }
    }

    // Get all items
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return ResponseEntity.ok(items);
    }

    // Get image by item ID
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"item-image.jpg\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(item.getImage());
    }
}
