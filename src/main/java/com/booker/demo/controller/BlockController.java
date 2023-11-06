package com.booker.demo.controller;

import com.booker.demo.model.Block;
import com.booker.demo.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/blocks")
public class BlockController {
    @Autowired
    private BlockService blockService;

    @PostMapping
    public ResponseEntity<Block> createBlock(@RequestBody @Valid Block block) {
        Block createdBlock = blockService.createBlock(block);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBlock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Block> updateBlock(
            @PathVariable Long id,
            @RequestBody @Valid Block updatedBlock
    ) {
        Block updated = blockService.updateBlock(id, updatedBlock);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlock(@PathVariable Long id) {
        blockService.deleteBlock(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Block> getBlock(@PathVariable Long id) {
        Block block = blockService.getBlockById(id);
        return ResponseEntity.ok(block);
    }

    @GetMapping
    public ResponseEntity<List<Block>> getAllBlocks() {
        List<Block> blocks = blockService.getAllBlocks();
        return ResponseEntity.ok(blocks);
    }
}
