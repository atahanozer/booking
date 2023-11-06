package com.booker.demo.service;

import com.booker.demo.exception.BlockOverlappingException;
import com.booker.demo.exception.InvalidDateRangeException;
import com.booker.demo.exception.NotFoundException;
import com.booker.demo.model.Block;
import com.booker.demo.repository.BlockRepository;
import com.booker.demo.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockService {
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public Block createBlock(Block block) {
        validate(block);
        return blockRepository.save(block);
    }

    private void validate(Block block) {
        if (block.getStartDate().isAfter(block.getEndDate())) {
            throw new InvalidDateRangeException("Start date cannot be after end date");
        }

        if (isBlockOverlapping(block)) {
            throw new BlockOverlappingException("Block overlaps with an existing booking.");
        }
    }

    public Block updateBlock(Long id, Block updatedBlock) {
        Block existingBlock = blockRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Block not found"));

        if (isBlockOverlapping(updatedBlock)) {
            throw new BlockOverlappingException("Block overlaps with an existing block.");
        }

        existingBlock.setStartDate(updatedBlock.getStartDate());
        existingBlock.setEndDate(updatedBlock.getEndDate());

        return blockRepository.save(existingBlock);
    }

    public void deleteBlock(Long id) {
        Block existingBlock = blockRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Block not found"));

        blockRepository.delete(existingBlock);
    }

    public Block getBlockById(Long id) {
        return blockRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Block not found"));
    }

    public List<Block> getAllBlocks() {
        return blockRepository.findAll();
    }

    protected boolean isBlockOverlapping(Block newBlock) {
        boolean bookingOverlapExists = bookingRepository.existsBetweenDates(newBlock.getStartDate(), newBlock.getEndDate(), newBlock.getPropertyId());
        if (bookingOverlapExists) {
            return true;
        }

        return false; // No overlaps found
    }

}
